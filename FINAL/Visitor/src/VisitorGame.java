import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class VisitorGame extends GameEngine {
    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 720;
    private final double FOV = Math.toRadians(66);

    private Player player;
    private MapManager mapManager;
    private Raycaster raycaster;
    private List<InteractiveObject> worldObjects = new ArrayList<>();

    // 输入状态
    private boolean keyW, keyS, keyA, keyD, keyE;
    private String interactionMessage = "";
    private long messageTime = 0;

    // 纹理图片（示例图片需要放到项目根目录或指定路径）
    private Image doorClosedImg, doorOpenImg, tableImg;

    @Override
    public void init() {
        System.out.println("=== Seeking the Impostor - with Interactive Objects ===");

        // 加载图片（实际使用时请替换为真实路径）
        doorClosedImg = loadImage("door_closed.png");
        doorOpenImg   = loadImage("door_open.png");
        tableImg      = loadImage("table.png");

        // 如果图片加载失败，创建纯色替代纹理
        if (doorClosedImg == null) doorClosedImg = createSolidColorImage(64, 64, Color.GRAY);
        if (doorOpenImg == null)   doorOpenImg   = createSolidColorImage(64, 64, Color.LIGHT_GRAY);
        if (tableImg == null)      tableImg      = createSolidColorImage(64, 64, new Color(160, 100, 40));

        player = new Player(4.5, 4.5, 0.0);
        mapManager = new MapManager();

        // 初始化可交互物体（坐标需要避开墙壁）
        // 在 (5.5, 2.5) 位置添加一扇门
        InteractiveObject door = new InteractiveObject(5.5, 2.5, InteractiveObject.TYPE_DOOR, doorClosedImg);
        door.setDoorTextures(doorClosedImg, doorOpenImg);
        worldObjects.add(door);

        // 在 (3.5, 7.5) 位置添加一张桌子
        InteractiveObject table = new InteractiveObject(3.5, 7.5, InteractiveObject.TYPE_TABLE, tableImg);
        worldObjects.add(table);

        // 另一扇门在 (7.5, 5.5)
        InteractiveObject door2 = new InteractiveObject(7.5, 5.5, InteractiveObject.TYPE_DOOR, doorClosedImg);
        door2.setDoorTextures(doorClosedImg, doorOpenImg);
        worldObjects.add(door2);

        raycaster = new Raycaster(mapManager, worldObjects);
    }

    // 辅助方法：生成纯色图片
    private Image createSolidColorImage(int w, int h, Color color) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return img;
    }

    @Override
    public void update(double dt) {
        updatePlayerMovement(dt);
        updateInteraction();
    }

    private void updatePlayerMovement(double dt) {
        double moveStep = player.moveSpeed * dt;
        double turnStep = player.turnSpeed * dt;

        if (keyA) player.angle -= turnStep;
        if (keyD) player.angle += turnStep;

        double moveX = 0, moveY = 0;
        if (keyW) {
            moveX += Math.cos(player.angle) * moveStep;
            moveY += Math.sin(player.angle) * moveStep;
        }
        if (keyS) {
            moveX -= Math.cos(player.angle) * moveStep;
            moveY -= Math.sin(player.angle) * moveStep;
        }

        // 移动检测（墙壁 + 物体碰撞）
        double newX = player.x + moveX;
        double newY = player.y + moveY;

        if (!mapManager.isWall(newX, player.y) && !collidesWithObject(newX, player.y))
            player.x = newX;
        if (!mapManager.isWall(player.x, newY) && !collidesWithObject(player.x, newY))
            player.y = newY;
    }

    // 物体碰撞检测（圆形）
    private boolean collidesWithObject(double x, double y) {
        for (InteractiveObject obj : worldObjects) {
            if (!obj.isSolid()) continue;
            double dx = x - obj.x;
            double dy = y - obj.y;
            double dist = Math.sqrt(dx*dx + dy*dy);
            if (dist < obj.getRadius() + 0.3) return true;
        }
        return false;
    }

    // 交互逻辑：寻找最近的物体并在按 E 时触发
    private void updateInteraction() {
        if (!keyE) return;
        keyE = false; // 一次性触发

        double closestDist = 2.0;
        InteractiveObject closestObj = null;
        for (InteractiveObject obj : worldObjects) {
            double dx = player.x - obj.x;
            double dy = player.y - obj.y;
            double dist = Math.sqrt(dx*dx + dy*dy);
            if (dist < closestDist) {
                closestDist = dist;
                closestObj = obj;
            }
        }
        if (closestObj != null) {
            closestObj.interact();
            if (closestObj.type == InteractiveObject.TYPE_DOOR) {
                interactionMessage = "Door " + (closestObj.isOpen ? "opened" : "closed");
            } else {
                interactionMessage = "You look at the table... nothing special.";
            }
            messageTime = System.currentTimeMillis();
        } else {
            interactionMessage = "Nothing to interact here.";
            messageTime = System.currentTimeMillis();
        }
    }

    @Override
    public void paintComponent() {
        // 清屏
        changeColor(Color.BLACK);
        drawSolidRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // 渲染 3D 墙体视图
        render3DView();

        // 渲染所有物体（billboard 精灵）
        renderSprites();

        // HUD 和调试信息
        drawHUD();
    }

    private void render3DView() {
        // 天花板和地板
        changeColor(new Color(40, 40, 55));
        drawSolidRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 2);
        changeColor(new Color(70, 70, 70));
        drawSolidRectangle(0, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT / 2);

        // 逐列渲染墙体和物体遮挡
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            double cameraX = 2 * x / (double) SCREEN_WIDTH - 1;
            double rayAngle = player.angle + (FOV / 2.0) * cameraX;
            RayHit hit = raycaster.castRayWithObjects(player.x, player.y, rayAngle);

            double correctedDistance = hit.distance * Math.cos(rayAngle - player.angle);
            if (correctedDistance < 0.01) correctedDistance = 0.01;
            int wallHeight = (int) (SCREEN_HEIGHT / correctedDistance);
            int wallTop = Math.max(0, SCREEN_HEIGHT / 2 - wallHeight / 2);
            int wallBottom = Math.min(SCREEN_HEIGHT, SCREEN_HEIGHT / 2 + wallHeight / 2);

            double shade = Math.max(0.2, 1.0 / (1.0 + correctedDistance * 0.2));
            if (hit.wallType == 1 || hit.wallType == 99) {
                // 墙体或物体都使用深色（物体的实际渲染由精灵完成，这里只是简单遮挡）
                changeColor(new Color((int)(100 * shade), (int)(100 * shade), (int)(100 * shade)));
            } else {
                changeColor(new Color((int)(60 * shade), (int)(40 * shade), (int)(20 * shade)));
            }
            drawLine(x, wallTop, x, wallBottom);
        }
    }

    // 使用 billboard 技术绘制所有物体（始终面向相机）
    private void renderSprites() {
        // 按距离排序（从远到近，避免透明穿模）
        worldObjects.sort((a, b) -> {
            double da = distance(player.x, player.y, a.x, a.y);
            double db = distance(player.x, player.y, b.x, b.y);
            return Double.compare(db, da);
        });

        for (InteractiveObject obj : worldObjects) {
            // 计算物体在世界坐标系中的角度和距离
            double dx = obj.x - player.x;
            double dy = obj.y - player.y;
            double dist = Math.sqrt(dx*dx + dy*dy);
            if (dist < 0.1) dist = 0.1;

            // 物体相对于玩家视线的角度
            double angleToObj = Math.atan2(dy, dx);
            double relativeAngle = angleToObj - player.angle;
            // 归一化到 [-PI, PI]
            while (relativeAngle > Math.PI) relativeAngle -= 2*Math.PI;
            while (relativeAngle < -Math.PI) relativeAngle += 2*Math.PI;

            double screenX = (relativeAngle + FOV/2) / FOV * SCREEN_WIDTH;
            if (screenX < 0 || screenX >= SCREEN_WIDTH) continue;

            // 根据距离计算物体在屏幕上的高度和宽度
            double objHeight = SCREEN_HEIGHT / (dist * 1.2);
            double objWidth = objHeight; // 保持比例
            int top = (int)(SCREEN_HEIGHT/2 - objHeight/2);
            int left = (int)(screenX - objWidth/2);

            // 简单裁剪避免绘制到屏幕外
            if (top < 0) top = 0;
            if (left < 0) left = 0;
            if (left + objWidth > SCREEN_WIDTH) objWidth = SCREEN_WIDTH - left;

            // 绘制物体图片（支持半透明）
            if (obj.texture != null) {
                mGraphics.drawImage(obj.texture, left, top, (int)objWidth, (int)objHeight, null);
            } else {
                // 后备绘制：彩色方块
                changeColor(obj.type == InteractiveObject.TYPE_DOOR ? Color.GRAY : Color.ORANGE);
                drawSolidRectangle(left, top, objWidth, objHeight);
            }
        }
    }

    private void drawHUD() {
        changeColor(Color.WHITE);
        drawText(20, 35, "Seeking the Impostor - Interactive Demo (Door/Table)");
        changeColor(Color.CYAN);
        drawText(20, 70, "Position: (" + String.format("%.2f", player.x) + ", " + String.format("%.2f", player.y) + ")");
        drawText(20, 95, "Angle: " + String.format("%.1f", Math.toDegrees(player.angle)) + "°");
        changeColor(Color.YELLOW);
        drawText(20, 130, "W/A/S/D - Move/Turn     E - Interact");

        // 显示交互反馈
        if (System.currentTimeMillis() - messageTime < 2000) {
            changeColor(Color.GREEN);
            drawText(20, 165, interactionMessage);
        }

        // 显示附近可交互物体提示
        for (InteractiveObject obj : worldObjects) {
            double dist = distance(player.x, player.y, obj.x, obj.y);
            if (dist < 2.0) {
                changeColor(new Color(255, 200, 100));
                drawText(20, SCREEN_HEIGHT - 30, "Press E to " + (obj.type == InteractiveObject.TYPE_DOOR ? "open/close door" : "inspect table"));
                break;
            }
        }
    }

    // 工具方法
    @Override
    public double distance(double x1, double y1, double x2, double y2) {
        return Math.hypot(x2-x1, y2-y1);
    }

    // 键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) keyW = true;
        if (code == KeyEvent.VK_S) keyS = true;
        if (code == KeyEvent.VK_A) keyA = true;
        if (code == KeyEvent.VK_D) keyD = true;
        if (code == KeyEvent.VK_E) keyE = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) keyW = false;
        if (code == KeyEvent.VK_S) keyS = false;
        if (code == KeyEvent.VK_A) keyA = false;
        if (code == KeyEvent.VK_D) keyD = false;
        // E 键不重置，只在 update 中一次性处理
    }

    public static void main(String[] args) {
        createGame(new VisitorGame(), 60);
    }
}
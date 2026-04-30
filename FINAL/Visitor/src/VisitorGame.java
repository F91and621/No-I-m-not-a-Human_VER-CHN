import java.awt.*;
import java.awt.event.KeyEvent;

public class VisitorGame extends GameEngine {

    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 720;
    private final double FOV = Math.toRadians(66);   // Field of View

    // Game core objects
    private Player player;
    private MapManager mapManager;
    private Raycaster raycaster;

    // Input states
    private boolean keyW, keyS, keyA, keyD;

    private String backendStatus = "Not Tested (Ping on Day 6)";

    @Override
    public void init() {
        System.out.println("=== Seeking the Impostor - Raycasting Walk Simulator v0.1 (Day 3) ===");

        player = new Player(4.5, 4.5, 0.0);
        mapManager = new MapManager();
        raycaster = new Raycaster(mapManager);


    }

    @Override
    public void update(double dt) {
        updatePlayerMovement(dt);
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

        double newX = player.x + moveX;
        double newY = player.y + moveY;

        if (!mapManager.isWall(newX, player.y)) player.x = newX;
        if (!mapManager.isWall(player.x, newY)) player.y = newY;
    }

    @Override
    public void paintComponent() {
        // Set unified font
        mGraphics.setFont(new Font("Arial", Font.PLAIN, 18));

        // Clear screen
        changeColor(Color.BLACK);
        drawSolidRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Render pseudo-3D scene
        render3DView();

        // === Debug Information Overlay ===
        changeColor(Color.WHITE);
        drawText(20, 35, "Seeking the Impostor - 2.5D Raycasting (Day 3)");

        changeColor(Color.CYAN);
        drawText(20, 75, "X     = " + String.format("%.3f", player.x));
        drawText(20, 100, "Y     = " + String.format("%.3f", player.y));
        drawText(20, 125, "Angle = " + String.format("%.3f", player.angle)
                + " (" + String.format("%.1f", Math.toDegrees(player.angle)) + "°)");

        changeColor(Color.YELLOW);
        drawText(20, 170, "W/S - Move Forward/Backward    A/D - Turn Left/Right");
        drawText(20, 195, "P - Test Backend Connection (Day 6)");

        changeColor(new Color(100, 255, 100));
        drawText(20, 240, "Current Grid: (" + (int)player.x + "," + (int)player.y + ") = "
                + mapManager.getCell((int)player.x, (int)player.y));

        changeColor(Color.GRAY);
        drawText(20, 520, "Day 3 Progress: Pseudo-3D Raycasting Completed");
        drawText(20, 545, "Tomorrow: Improve shading and add minimap");
    }

    /**
     * Core: Render the pseudo-3D view
     */
    private void render3DView() {
        // Draw ceiling and floor
        changeColor(new Color(40, 40, 55));                    // Ceiling
        drawSolidRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 2);

        changeColor(new Color(70, 70, 70));                    // Floor
        drawSolidRectangle(0, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT / 2);

        // Cast ray for each column
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            double cameraX = 2 * x / (double) SCREEN_WIDTH - 1;
            double rayAngle = player.angle + FOV / 2.0 * cameraX;

            RayHit hit = raycaster.castRay(player.x, player.y, rayAngle);

            // Fish-eye correction
            double correctedDistance = hit.distance * Math.cos(rayAngle - player.angle);
            if (correctedDistance < 0.01) correctedDistance = 0.01;

            int wallHeight = (int) (SCREEN_HEIGHT / correctedDistance);

            int wallTop = Math.max(0, SCREEN_HEIGHT / 2 - wallHeight / 2);
            int wallBottom = Math.min(SCREEN_HEIGHT, SCREEN_HEIGHT / 2 + wallHeight / 2);

            // Simple distance shading (darker when farther)
            double shade = Math.max(0.2, 1.0 / (1.0 + correctedDistance * 0.25));

            if (hit.wallType == 1) {
                changeColor(new Color((int)(130 * shade), (int)(130 * shade), (int)(130 * shade)));
            } else {
                changeColor(new Color((int)(100 * shade), (int)(60 * shade), (int)(30 * shade)));
            }

            drawLine(x, wallTop, x, wallBottom);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) keyW = true;
        if (e.getKeyCode() == KeyEvent.VK_S) keyS = true;
        if (e.getKeyCode() == KeyEvent.VK_A) keyA = true;
        if (e.getKeyCode() == KeyEvent.VK_D) keyD = true;

        if (e.getKeyCode() == KeyEvent.VK_P) {
            backendStatus = "Ping requested (Day 6)";
            System.out.println("[P Key] Backend test requested (placeholder)");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) keyW = false;
        if (e.getKeyCode() == KeyEvent.VK_S) keyS = false;
        if (e.getKeyCode() == KeyEvent.VK_A) keyA = false;
        if (e.getKeyCode() == KeyEvent.VK_D) keyD = false;
    }

    public static void main(String[] args) {
        createGame(new VisitorGame(), 60);
    }
}

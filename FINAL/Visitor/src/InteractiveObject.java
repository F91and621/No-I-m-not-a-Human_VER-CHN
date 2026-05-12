import java.awt.Image;
import java.awt.image.BufferedImage;

public class InteractiveObject {
    public static final int TYPE_DOOR = 1;
    public static final int TYPE_TABLE = 2;

    public double x, y;           // 世界坐标（格子中心）
    public int type;              // 类型：门/桌子
    public boolean isOpen = false; // 门的状态
    public Image texture;         // 当前显示的图片

    private Image closedTexture;   // 门关闭时的图片
    private Image openTexture;     // 门打开时的图片
    private Image defaultTexture;  // 桌子的图片

    public InteractiveObject(double x, double y, int type, Image texture) {
        this.x = x;
        this.y = y;
        this.type = type;
        if (type == TYPE_DOOR) {
            this.closedTexture = texture;
            this.openTexture = null;  // 稍后可设置开门的图片
            this.texture = closedTexture;
        } else {
            this.defaultTexture = texture;
            this.texture = defaultTexture;
        }
    }

    // 为门设置开门/关门的不同图片
    public void setDoorTextures(Image closed, Image open) {
        if (type == TYPE_DOOR) {
            this.closedTexture = closed;
            this.openTexture = open;
            this.texture = isOpen ? openTexture : closedTexture;
        }
    }

    // 交互逻辑（由玩家按 E 触发）
    public void interact() {
        if (type == TYPE_DOOR) {
            isOpen = !isOpen;
            texture = isOpen ? openTexture : closedTexture;
            System.out.println("Door is now " + (isOpen ? "OPEN" : "CLOSED"));
        } else if (type == TYPE_TABLE) {
            System.out.println("Table: Just a piece of furniture.");
        }
    }

    // 门关闭时阻挡射线/移动
    public boolean isSolid() {
        if (type == TYPE_DOOR && !isOpen) return true;
        if (type == TYPE_TABLE) return true;  // 桌子始终阻挡移动
        return false;
    }

    // 获取物品的碰撞半径（格子的一半，用于移动和射线检测）
    public double getRadius() {
        return 0.4;  // 比半个格子略小，避免卡墙
    }
}
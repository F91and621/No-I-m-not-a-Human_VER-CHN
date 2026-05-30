public class ClickableArea {

    private String actionId;
    private int x;
    private int y;
    private int width;
    private int height;

    public ClickableArea(String actionId, int x, int y, int width, int height) {
        this.actionId = actionId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getActionId() {
        return actionId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }
}

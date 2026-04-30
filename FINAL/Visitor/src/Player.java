public class Player {
    public double x;
    public double y;
    public double angle;        // 使用弧度制（推荐）

    public double moveSpeed = 4.0;   // 你可以根据手感调整
    public double turnSpeed = 2.5;

    public Player(double startX, double startY, double startAngle) {
        this.x = startX;
        this.y = startY;
        this.angle = startAngle;
    }
}

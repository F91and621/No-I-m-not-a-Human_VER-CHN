import java.util.List;

public class Raycaster {
    private MapManager mapManager;
    private List<InteractiveObject> objects;  // 物体列表引用
    private final double STEP_SIZE = 0.015;
    private final double MAX_DISTANCE = 20.0;

    public Raycaster(MapManager mapManager, List<InteractiveObject> objects) {
        this.mapManager = mapManager;
        this.objects = objects;
    }

    // 原 castRay 保持不变，新增 castRayWithObjects 方法
    public RayHit castRayWithObjects(double startX, double startY, double rayAngle) {
        double rayX = startX, rayY = startY;
        double distance = 0.0;
        RayHit closestHit = null;
        double closestDist = MAX_DISTANCE;

        // 先检测墙体（原逻辑）
        while (distance < MAX_DISTANCE) {
            rayX += Math.cos(rayAngle) * STEP_SIZE;
            rayY += Math.sin(rayAngle) * STEP_SIZE;
            distance += STEP_SIZE;

            int cell = mapManager.getCell((int) rayX, (int) rayY);
            if (cell != 0) {
                closestHit = new RayHit(rayX, rayY, distance, cell);
                closestDist = distance;
                break;
            }
        }

        // 检测所有物体（只检测关闭的门和桌子）
        for (InteractiveObject obj : objects) {
            if (!obj.isSolid()) continue;

            // 计算射线到物体圆心的距离（简化版碰撞检测）
            double dx = startX - obj.x;
            double dy = startY - obj.y;
            double rad = obj.getRadius();

            // 解二次方程求交点 (p + t*d - c)^2 = r^2
            double dirX = Math.cos(rayAngle);
            double dirY = Math.sin(rayAngle);
            double a = dirX*dirX + dirY*dirY;
            double b = 2 * (dx*dirX + dy*dirY);
            double c = dx*dx + dy*dy - rad*rad;
            double disc = b*b - 4*a*c;
            if (disc >= 0) {
                double t = (-b - Math.sqrt(disc)) / (2*a);
                if (t > 0.01 && t < closestDist) {
                    double hitX = startX + dirX * t;
                    double hitY = startY + dirY * t;
                    closestHit = new RayHit(hitX, hitY, t, 99); // wallType=99 表示物体
                    closestDist = t;
                }
            }
        }
        return closestHit != null ? closestHit : new RayHit(rayX, rayY, MAX_DISTANCE, 0);
    }

    // 保留原有简单射线（向后兼容）
    public RayHit castRay(double startX, double startY, double rayAngle) {
        return castRayWithObjects(startX, startY, rayAngle);
    }
}
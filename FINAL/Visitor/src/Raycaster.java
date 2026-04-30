public class Raycaster {

    private MapManager mapManager;
    private final double STEP_SIZE = 0.015;     // 步长，越小越精确但越慢
    private final double MAX_DISTANCE = 20.0;

    public Raycaster(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    /**
     * 发射一根射线，返回命中信息（简单逐步前进法）
     */
    public RayHit castRay(double startX, double startY, double rayAngle) {
        double rayX = startX;
        double rayY = startY;
        double distance = 0.0;

        while (distance < MAX_DISTANCE) {
            rayX += Math.cos(rayAngle) * STEP_SIZE;
            rayY += Math.sin(rayAngle) * STEP_SIZE;
            distance += STEP_SIZE;

            int cell = mapManager.getCell((int) rayX, (int) rayY);

            if (cell != 0) {        // 碰到任何非0的格子都视为墙
                return new RayHit(rayX, rayY, distance, cell);
            }
        }
        return new RayHit(rayX, rayY, MAX_DISTANCE, 0);
    }
}

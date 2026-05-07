
public class MapManager {

    // 10x10的简单测试地图（1=墙，0=空地）
    public int[][] map = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,0,0,1,0,1},
            {1,0,0,1,0,0,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    public boolean isWall(double x, double y) {
        int mapX = (int) x;
        int mapY = (int) y;

        if (mapX < 0 || mapX >= map[0].length || mapY < 0 || mapY >= map.length) {
            return true;
        }
        return map[mapY][mapX] == 1;
    }

    public int getCell(int x, int y) {
        if (x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
            return 1;
        }
        return map[y][x];
    }
}

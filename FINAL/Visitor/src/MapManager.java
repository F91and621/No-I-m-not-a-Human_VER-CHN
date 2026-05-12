<<<<<<< HEAD

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
=======

public class MapManager {

    // 10x10的简单测试地图（1=墙，0=空地）
    public int[][] map = {
            //  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, // 1
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 2
            { 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1 }, // 3
            { 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, // 4
            { 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 }, // 5
            { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 }, // 6
            { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 }, // 7
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 8
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 9
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //10
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //11
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //12
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //13
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }  //14
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
>>>>>>> aeca43d (地图好了)

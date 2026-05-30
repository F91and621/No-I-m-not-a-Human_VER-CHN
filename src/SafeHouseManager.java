public class SafeHouseManager {

    private DayRoomType playerRoom;
    private boolean canSleep;

    public SafeHouseManager() {
        playerRoom = DayRoomType.BEDROOM;
        canSleep = false;
    }

    public DayRoomType getPlayerRoom() {
        return playerRoom;
    }

    public void setPlayerRoom(DayRoomType playerRoom) {
        this.playerRoom = playerRoom;
    }

    public boolean canSleep() {
        return canSleep;
    }

    public void setCanSleep(boolean canSleep) {
        this.canSleep = canSleep;
    }
}

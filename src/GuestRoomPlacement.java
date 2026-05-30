public class GuestRoomPlacement {

    private String guestId;
    private DayRoomType room;
    private String imageKey;

    private int x;
    private int y;
    private int width;
    private int height;

    public GuestRoomPlacement(
            String guestId,
            DayRoomType room,
            String imageKey,
            int x,
            int y,
            int width,
            int height
    ) {
        this.guestId = guestId;
        this.room = room;
        this.imageKey = imageKey;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getGuestId() {
        return guestId;
    }

    public DayRoomType getRoom() {
        return room;
    }

    public String getImageKey() {
        return imageKey;
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
}

import java.util.ArrayList;
import java.util.List;

public class GuestManager {
    // 这就是你的“登记簿”，存放所有的客人
    private List<Guest> guestList;

    public GuestManager() {
        guestList = new ArrayList<>();
        initGuests(); // 只要一 new 这个管理器，就自动把 10 个人准备好
    }

    // 后台初始化：在这里把 10 个 NPC 录入系统
    private void initGuests() {

        Guest g1 = new Guest("老八", "试吃官", false);
        // 正常人不需要额外 set 破绽，默认就是 NORMAL
        guestList.add(g1);

        Guest g2 = new Guest("孙笑川", "日本天皇", true);
        g2.setTeethTrait(SignType.PERFECT_WHITE_TEETH, "teeth_unnaturally_white.png", "我刚做了冷光美白，效果不错吧？");
        guestList.add(g2);


        //...
        System.out.println("后台系统：10名NPC数据已成功载入！");
    }

    // 下一个人
    public Guest getNextGuest(int index) {
        if (index >= 0 && index < guestList.size()) {
            return guestList.get(index);
        }
        return null; // 今晚没有人要来了
    }

    // 获取当天的客人总数
    public int getTotalGuests() {
        return guestList.size();
    }
}
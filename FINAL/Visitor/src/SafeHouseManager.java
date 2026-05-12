import java.util.ArrayList;
import java.util.List;
public class SafeHouseManager {
    // 成功入住的客人名单
    public List<Guest> admittedGuests = new ArrayList<>();

    public void admitGuest(Guest guest) {
        admittedGuests.add(guest);
    }

    // 夜晚结算逻辑
    public void processNightPhase() {
        boolean hasVisitor = false;
        int humanCount = 0;

        // 盘点屋里的人
        for (Guest g : admittedGuests) {
            if (g.isVisitor) hasVisitor = true;
            else humanCount++;
        }

        // 结算生死
        if (hasVisitor && humanCount > 0) {
            System.out.println("闻到一股血腥味，昨晚好像有人死了...");
        } else {
            System.out.println("今夜平安无事。");
        }
    }
}
import java.util.ArrayList;
import java.util.List;
public class PlayerInventory {
    public int maxEnergy = 100;
    public int currentEnergy = 100;

    // 玩家当天的背包
    public List<ItemType> items = new ArrayList<>();

    // 吃东西的后台逻辑
    public void consumeItem(ItemType item) {
        if (item == ItemType.COFFEE) {
            currentEnergy = Math.min(maxEnergy, currentEnergy + 30);
            System.out.println("喝了咖啡，精力恢复。当前精力：" + currentEnergy);
        } else if (item == ItemType.BEER) {
            currentEnergy -= 50;
            System.out.println("喝了酒困得不行，最好去睡觉，当前精力：" + currentEnergy);
        }
        items.remove(item);
    }
}
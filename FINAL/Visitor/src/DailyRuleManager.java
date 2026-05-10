import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DailyRuleManager {
    public int currentDay = 1;
    // 记录今天查询的位置，只有命中这个列表里的 SignType，玩家才能拔枪
    public List<SignType> activeSignsToday = new ArrayList<>();

    private final List<SignType> ruleUnlockSequence = Arrays.asList(
            SignType.PERFECT_WHITE_TEETH,  // Day 1 解锁
            SignType.DIRTY_FINGERNAILS,    // Day 2 追加解锁
            SignType.BLOODSHOT_EYES      // Day 3 追加解锁
    );

    public void startNewDay() {

        currentDay++;
        System.out.println("=== 第 " + currentDay + " 天 ===");
        // ... 依此类推
        activeSignsToday.clear();

        // 防止天数超过我们设定的规则总数，引发越界报错 (IndexOutOfBounds)
        int rulesToApply = Math.min(currentDay, ruleUnlockSequence.size());

        // 根据当前天数，自动从序列里抓取对应数量的规则
        for (int i = 0; i < rulesToApply; i++) {
            activeSignsToday.add(ruleUnlockSequence.get(i));
        }

        System.out.println("今天可以检查 " + activeSignsToday.size() + " 个地方。");
    }

}
package com.yourgame.visitorbackend;

import java.util.Random;
import java.util.UUID;

public class Visitor {

    private String id;                    // 唯一ID
    private String name;                  // 姓名
    private String occupation;            // 职业
    private String appearance;            // 外貌描述
    private String behavior;              // 行为描述
    private boolean isHuman;              // true=人类，false=伪人
    private boolean isFixedIdentity;      // true=身份固定（跨游戏不变），false=每轮随机
    private String dayMood;               // 白天心情（昼夜系统用）
    private String nightMood;             // 夜晚心情（昼夜系统用）

    // ==================== 随机数据池 ====================
    private static final String[] NAMES = {"李明", "王芳", "张伟", "刘娜", "陈杰", "赵敏", "孙磊", "周静"};
    private static final String[] OCCUPATIONS = {"程序员", "教师", "医生", "销售", "司机", "记者", "学生", "厨师"};
    private static final String[] APPEARANCES = {"普通青年", "西装笔挺", "休闲运动装", "戴眼镜文静", "染发潮人", "背双肩包", "穿风衣神秘"};
    private static final String[] BEHAVIORS = {"友好聊天", "安静观察", "频繁看手机", "东张西望", "主动帮忙", "低声自语", "微笑点头"};
    private static final String[] DAY_MOODS = {"精神饱满", "心情愉快", "正常工作", "阳光开朗"};
    private static final String[] NIGHT_MOODS = {"略显疲惫", "眼神诡异", "安静异常", "偶尔闪烁红光"};

    private static final Random RANDOM = new Random();

    // ==================== 构造函数 ====================
    /** 默认构造函数：每轮游戏身份随机 */
    public Visitor() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.isFixedIdentity = false;
        randomizeAll();
    }

    /** 固定身份构造函数（用于特殊角色） */
    public Visitor(boolean fixedIsHuman) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.isFixedIdentity = true;
        this.isHuman = fixedIsHuman;
        randomizeNonIdentity();
    }

    // ==================== 随机生成方法 ====================
    private void randomizeAll() {
        this.isHuman = RANDOM.nextBoolean();           // 随机人类/伪人
        randomizeNonIdentity();
    }

    private void randomizeNonIdentity() {
        this.name = NAMES[RANDOM.nextInt(NAMES.length)];
        this.occupation = OCCUPATIONS[RANDOM.nextInt(OCCUPATIONS.length)];
        this.appearance = APPEARANCES[RANDOM.nextInt(APPEARANCES.length)];
        this.behavior = BEHAVIORS[RANDOM.nextInt(BEHAVIORS.length)];
        this.dayMood = DAY_MOODS[RANDOM.nextInt(DAY_MOODS.length)];
        this.nightMood = NIGHT_MOODS[RANDOM.nextInt(NIGHT_MOODS.length)];
    }

    // ==================== Getter & Setter ====================
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getAppearance() { return appearance; }
    public void setAppearance(String appearance) { this.appearance = appearance; }

    public String getBehavior() { return behavior; }
    public void setBehavior(String behavior) { this.behavior = behavior; }

    public boolean isHuman() { return isHuman; }
    public void setHuman(boolean human) { this.isHuman = human; }

    public boolean isFixedIdentity() { return isFixedIdentity; }
    public void setFixedIdentity(boolean fixedIdentity) { this.isFixedIdentity = fixedIdentity; }

    public String getDayMood() { return dayMood; }
    public void setDayMood(String dayMood) { this.dayMood = dayMood; }

    public String getNightMood() { return nightMood; }
    public void setNightMood(String nightMood) { this.nightMood = nightMood; }

    // ==================== 工具方法 ====================
    /** 重新随机（用于新游戏）——仅对非固定身份生效 */
    public void regenerateForNewGame() {
        if (!isFixedIdentity) {
            randomizeAll();
        }
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                ", isHuman=" + (isHuman ? "人类" : "伪人") +
                ", isFixed=" + isFixedIdentity +
                ", dayMood='" + dayMood + '\'' +
                ", nightMood='" + nightMood + '\'' +
                '}';
    }
}

import java.util.HashMap;

public class Guest {

    private String id;
    private String name;

    private boolean insideHouse;
    private DayRoomType currentRoom;
    private boolean talkedToday;

    private HashMap<String, DialogueNode> dialogueNodes;
    private HashMap<String, DialogueNode> dayDialogueNodes;

    public String startDialogueNodeId;
    public String dayStartDialogueNodeId;

    private GuestIdentity identity;
    private GuestFeatures features;

    private boolean dead;
    private boolean killedByVisitor;

    private String corpseImageKey;


    public Guest(String id, String name) {
        this.id = id;
        this.name = name;

        this.insideHouse = false;
        this.currentRoom = null;
        this.talkedToday = false;

        this.dialogueNodes = new HashMap<String, DialogueNode>();
        this.dayDialogueNodes = new HashMap<String, DialogueNode>();

        this.startDialogueNodeId = null;
        this.dayStartDialogueNodeId = null;
        this.identity = GuestIdentity.HUMAN;
        this.features = null;

        this.dead = false;
        this.killedByVisitor = false;
        this.corpseImageKey = null;
    }

    public String getId() {
        return id;
    }

    public GuestIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(GuestIdentity identity) {
        this.identity = identity;
    }

    public boolean isHuman() {
        return identity == GuestIdentity.HUMAN;
    }

    public boolean isVisitor() {
        return identity == GuestIdentity.VISITOR;
    }

    public GuestFeatures getFeatures() {
        return features;
    }

    public void setFeatures(GuestFeatures features) {
        this.features = features;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isKilledByVisitor() {
        return killedByVisitor;
    }

    public void setKilledByVisitor(boolean killedByVisitor) {
        this.killedByVisitor = killedByVisitor;
    }


    public String getCorpseImageKey() {
        return corpseImageKey;
    }

    public void setCorpseImageKey(String corpseImageKey) {
        this.corpseImageKey = corpseImageKey;
    }


    public String getName() {
        return name;
    }

    public boolean isInsideHouse() {
        return insideHouse;
    }

    public void setInsideHouse(boolean insideHouse) {
        this.insideHouse = insideHouse;
    }

    public DayRoomType getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(DayRoomType currentRoom) {
        this.currentRoom = currentRoom;
    }

    public boolean hasTalkedToday() {
        return talkedToday;
    }

    public void setTalkedToday(boolean talkedToday) {
        this.talkedToday = talkedToday;
    }

    public void addDialogueNode(DialogueNode node) {
        if (node == null) {
            return;
        }

        dialogueNodes.put(node.getId(), node);
    }

    public DialogueNode getDialogueNode(String nodeId) {
        return dialogueNodes.get(nodeId);
    }

    public DialogueNode getStartDialogueNode() {
        if (startDialogueNodeId == null) {
            return null;
        }

        return getDialogueNode(startDialogueNodeId);
    }

    public void addDayDialogueNode(DialogueNode node) {
        if (node == null) {
            return;
        }

        dayDialogueNodes.put(node.getId(), node);
    }

    public DialogueNode getDayDialogueNode(String nodeId) {
        return dayDialogueNodes.get(nodeId);
    }

    public DialogueNode getDayStartDialogueNode() {
        if (dayStartDialogueNodeId == null) {
            return null;
        }

        return getDayDialogueNode(dayStartDialogueNodeId);
    }

    public static Guest createNeighbor() {
        Guest neighbor = new Guest("neighbor", "邻居");

        DialogueNode start = new DialogueNode(
                "start",
                "嘿，好久不见了。最近怎么样？"
        );

        start.addOption(new DialogueOption("凑合。", "A1", false));
        start.addOption(new DialogueOption("挺好，多谢。", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "我就不兜圈子了，我姐他们单位发预警了。",
                "我特地过来看看你。",
                "要出事。",
                "我刚给她打完电话。",
                "他们单位收到天文台通知，说近期太阳活动异常。",
                "因此凭空出现好多怪人，人不人鬼不鬼的。",
                "公安局暂定，叫他们“伪人”。",
                "怪的很，是吧？真希望不是真的......"
        );
        A1.addOption(new DialogueOption("太阳怎么了？", "B1", false));
        A1.addOption(new DialogueOption("伪人？", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "我就不兜圈子了，我姐他们单位发预警了。",
                "我特地过来看看你。",
                "要出事。",
                "我刚给她打完电话。",
                "他们单位收到天文台通知，说近期太阳活动异常。",
                "因此凭空出现好多怪人，人不人鬼不鬼的。",
                "公安局暂定，叫他们“伪人”。",
                "怪的很，是吧？真希望不是真的......"
        );
        A2.addOption(new DialogueOption("太阳怎么了？", "B1", false));
        A2.addOption(new DialogueOption("伪人？", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "要么是太阳要爆炸，要么是有东西在太阳上爆炸。",
                "具体不清楚......反正有问题。",
                "毕竟你也能感觉出来，",
                "今年夏天比哪一年都热，对吧？",
                "我建议做好最坏准备。",
                "......",
                "我姐告诉我，现在一个人在家很危险。",
                "所以我赶快过来看看你，别出了事。"
        );
        B1.addOption(new DialogueOption("好的，多谢。", "B3", false));
        B1.addOption(new DialogueOption("我能照顾好自己的。", "B4", false));
        B1.addOption(new DialogueOption("你们家里人现在怎么样？", "B5", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "对，暂定叫伪人。",
                "人不人鬼不鬼的。",
                "突然就变出来了，是从土里钻出来的？",
                "听我姐说，他们会试图闯入别人家，但也不偷也不抢。",
                "但这就够邪乎的了。",
                "我姐告诉我，现在一个人在家很危险。",
                "所以我赶快过来看看你，别出了事。"
        );
        B2.addOption(new DialogueOption("好的，多谢。", "B3", false));
        B2.addOption(new DialogueOption("我能照顾好自己的。", "B4", false));
        B2.addOption(new DialogueOption("你们家里人现在怎么样？", "B5", false));

        DialogueNode B3 = new DialogueNode(
                "B3",
                "好，我今天先住在你这里。",
                "......",
                "快睡觉吧。",
                "据说明天会有新闻，真希望是辟谣的。"
        );
        B3.addOption(new DialogueOption("......", "decide", true));

        DialogueNode B4 = new DialogueNode(
                "B4",
                "我相信你能自理。",
                "但是你不知道现在一个人多危险。",
                "我就在你这里待一宿，以防万一。",
                "不用担心我家。",
                "我弟弟现在住在我们家，没事。",
                "......",
                "快睡觉吧。",
                "据说明天会有新闻，真希望是辟谣的。"
        );
        B4.addOption(new DialogueOption("......", "decide", true));

        DialogueNode B5 = new DialogueNode(
                "B5",
                "不用担心我家。",
                "我弟弟现在住在我们家，没事。",
                "我今天先住你这儿吧，以防万一。",
                "......",
                "快睡觉吧。",
                "据说明天会有新闻，真希望是辟谣的。"
        );
        B5.addOption(new DialogueOption("......", "decide", true));

        neighbor.addDialogueNode(start);
        neighbor.addDialogueNode(A1);
        neighbor.addDialogueNode(A2);
        neighbor.addDialogueNode(B1);
        neighbor.addDialogueNode(B2);
        neighbor.addDialogueNode(B3);
        neighbor.addDialogueNode(B4);
        neighbor.addDialogueNode(B5);

        neighbor.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "昨晚谢谢你让我留下。",
                "我现在好多了。"
        );
        dayStart.addOption(new DialogueOption("你还好吗？", "day_a1", false));
        dayStart.addOption(new DialogueOption("昨晚你说的那些事，我还在想。", "day_a2", false));
        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));

        DialogueNode dayA1 = new DialogueNode(
                "day_a1",
                "还好，就是没怎么睡着。",
                "现在外面到底变成什么样，我心里也没底。"
        );
        dayA1.addOption(new DialogueOption("先休息吧。", null, true));

        DialogueNode dayA2 = new DialogueNode(
                "day_a2",
                "我也希望只是虚惊一场。",
                "但我姐不会拿这种事开玩笑。",
                "这几天你最好小心一点。"
        );
        dayA2.addOption(new DialogueOption("我知道了。", null, true));

        neighbor.addDayDialogueNode(dayStart);
        neighbor.addDayDialogueNode(dayA1);
        neighbor.addDayDialogueNode(dayA2);

        neighbor.dayStartDialogueNodeId = "day_start";

        return neighbor;
    }

    public static Guest createDaughter() {
        Guest daughter = new Guest("daughter", "nver");

        DialogueNode start = new DialogueNode(
                "start",
                "哥哥！",
                "我爸爸在吗？",
                "你能帮我叫他吗？"
        );

        start.addOption(new DialogueOption("你爸爸叫什么？", "A1", false));
        start.addOption(new DialogueOption("跟我说说你爸爸吧。", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "你忘了他叫什么了？",
                "哦——你在测试我，我不是伪人。",
                "哥哥，你真聪明！",
                "我也要开始问别人了。"
        );
        A1.addOption(new DialogueOption("你是怎么过来的？", "B1", false));
        A1.addOption(new DialogueOption("你害怕伪人吗？", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "我怕黑的时候，他就会和我一起玩。",
                "我爸爸说，什么时候我够勇敢了，什么时候天就亮了。"
        );
        A2.addOption(new DialogueOption("你是怎么过来的？", "B1", false));
        A2.addOption(new DialogueOption("你害怕伪人吗？", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "悄悄告诉你，我是偷偷跑出来的！",
                "现在真奇怪，人们白天睡觉，晚上起床。",
                "我还是怕黑。",
                "爸爸说我都该上学了，不该老是怕黑了。"
        );
        B1.addOption(new DialogueOption("我去叫你爸爸！", "B3", false));
        B1.addOption(new DialogueOption("你真勇敢！", "B4", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "有一点怕。",
                "但是如果我能见一见他们，我就不怕了。",
                "到底什么是伪人呀？",
                "如果我被同学欺负了，",
                "那同学也是伪人吗？",
                "我不知道。",
                "但是有我爸爸在，我就不怕！"
        );
        B2.addOption(new DialogueOption("我去叫你爸爸！", "B3", false));
        B2.addOption(new DialogueOption("你真勇敢！", "B4", false));

        DialogueNode B3 = new DialogueNode(
                "B3",
                "好，我今天先住在你这里。",
                "谢谢哥哥！",
                "我想回家了……外边真吓人……",
                "我要跟我爸爸玩新游戏！",
                "改天我教你怎么玩。",
                "再见！"
        );
        B3.addOption(new DialogueOption("……", null, true));

        DialogueNode B4 = new DialogueNode(
                "B4",
                "谢谢……",
                "我还是有点怕。我想跟我爸爸回家了。"
        );
        B4.addOption(new DialogueOption("……", null, true));

        daughter.addDialogueNode(start);
        daughter.addDialogueNode(A1);
        daughter.addDialogueNode(A2);
        daughter.addDialogueNode(B1);
        daughter.addDialogueNode(B2);
        daughter.addDialogueNode(B3);
        daughter.addDialogueNode(B4);

        daughter.startDialogueNodeId = "start";

        return daughter;
    }


    public static Guest createFirefighter() {
        Guest firefighter = new Guest("firefighter", "xiaofangyuan");

        firefighter.setIdentity(GuestIdentity.HUMAN);

        firefighter.setFeatures(new GuestFeatures(
                true,   // 牙齿整齐
                true,   // 双手干净
                false,  // 眼睛不红
                true,   // 耳朵整洁
                "firefighter_teeth",
                "firefighter_hands",
                "firefighter_eyes",
                "firefighter_ears"
        ));


        DialogueNode start = new DialogueNode(
                "start",
                "哥哥！",
                "我爸爸在吗？",
                "你能帮我叫他吗？",
                ""
        );
        start.addOption(new DialogueOption("让他进来。", "__allow_current_guest__", false));
        start.addOption(new DialogueOption("不让他进来。", "__reject_current_guest__", false));

        firefighter.addDialogueNode(start);
        firefighter.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "昨晚多谢你让我进来。",
                "如果屋里有什么异常，我会帮忙处理。"
        );
        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("先这样。", null, true));

        firefighter.addDayDialogueNode(dayStart);
        firefighter.dayStartDialogueNodeId = "day_start";


        return firefighter;
    }

    public static Guest createTeacher() {
        Guest teacher = new Guest("teacher", "laoshi");

        teacher.setIdentity(GuestIdentity.HUMAN);

        teacher.setFeatures(new GuestFeatures(
                true,   // 牙齿整齐
                true,   // 双手干净
                false,  // 眼睛不红
                true,   // 耳朵整洁
                "teacher_teeth",
                "teacher_hands",
                "teacher_eyes",
                "teacher_ears"
        ));

        DialogueNode start = new DialogueNode(
                "start",
                "哥哥！",
                "我爸爸在吗？",
                "你能帮我叫他吗？",
                ""
        );
        start.addOption(new DialogueOption("让他进来。", "__allow_current_guest__", false));
        start.addOption(new DialogueOption("不让他进来。", "__reject_current_guest__", false));


        teacher.addDialogueNode(start);
        teacher.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "早上好。",
                "昨晚我几乎没有睡着。",
                "外面好像一直有声音。"
        );
        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("先这样。", null, true));

        teacher.addDayDialogueNode(dayStart);
        teacher.dayStartDialogueNodeId = "day_start";



        return teacher;
    }


    public static Guest createCoatperson() {
        Guest coatperson = new Guest("coat_person", "coat_person");

        coatperson.setIdentity(GuestIdentity.VISITOR);

        coatperson.setFeatures(new GuestFeatures(
                false,  // 牙齿不整齐
                false,  // 双手不干净
                true,   // 眼睛发红
                false,  // 耳朵不整洁
                "coat_person_teeth",
                "coat_person_hands",
                "coat_person_eyes",
                "coat_person_ears"
        ));


        DialogueNode start = new DialogueNode(
                "start",
                "哥哥！",
                "我爸爸在吗？",
                "你能帮我叫他吗？",
                ""
        );
        start.addOption(new DialogueOption("让他进来。", "__allow_current_guest__", false));
        start.addOption(new DialogueOption("不让他进来。", "__reject_current_guest__", false));

        coatperson.addDialogueNode(start);
        coatperson.startDialogueNodeId = "start";


        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "早。",
                "屋里比外面暖和。",
                "我昨晚睡得很好。"
        );
        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("先这样。", null, true));

        coatperson.addDayDialogueNode(dayStart);
        coatperson.dayStartDialogueNodeId = "day_start";




        return coatperson;
    }

    public static Guest createJudge() {
        Guest judgeVisitor = new Guest("judge_visitor", "super");

        judgeVisitor.setIdentity(GuestIdentity.VISITOR);



        DialogueNode start = new DialogueNode(
                "start",
                "屋里就你一个人？"
        );
        start.addOption(new DialogueOption("是。", "__judge_answer_yes__", false));
        start.addOption(new DialogueOption("不是。", "__judge_answer_no__", false));

        judgeVisitor.addDialogueNode(start);
        judgeVisitor.startDialogueNodeId = "start";



        return judgeVisitor;
    }

    public static Guest createWidow() {
        Guest widow = new Guest("widow", "guafu");
        widow.setIdentity(GuestIdentity.HUMAN);

        DialogueNode start = new DialogueNode(
                "start",
                "外面太冷了。",
                "能让我进去待一晚吗？"
        );

        start.addOption(new DialogueOption("让她进来。", "__allow_current_guest__", false));
        start.addOption(new DialogueOption("请她离开。", "__reject_current_guest__", false));

        widow.addDialogueNode(start);
        widow.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "谢谢你昨晚让我进来。",
                "外面真的太冷了。"
        );

        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("先这样。", null, true));

        widow.addDayDialogueNode(dayStart);
        widow.dayStartDialogueNodeId = "day_start";

        return widow;
    }


    public static Guest createAuntie(){
        Guest auntie = new Guest("auntie", "dama");
        auntie.setIdentity(GuestIdentity.VISITOR);

        DialogueNode start = new DialogueNode(
                "start",
                "外面太冷了。",
                "能让我进去待一晚吗？"
        );

        start.addOption(new DialogueOption("让她进来。", "__allow_current_guest__", false));
        start.addOption(new DialogueOption("请她离开。", "__reject_current_guest__", false));

        auntie.addDialogueNode(start);
        auntie.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "谢谢你昨晚让我进来。",
                "外面真的太冷了。"
        );

        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("先这样。", null, true));

        auntie.addDayDialogueNode(dayStart);
        auntie.dayStartDialogueNodeId = "day_start";

        return auntie;
    }

    public static Guest createPanicGirl() {
        Guest panic_girl = new Guest("panic_girl", "poor");
        panic_girl.setIdentity(GuestIdentity.HUMAN);

        DialogueNode start = new DialogueNode(
                "start",
                "外面太冷了。",
                "能让我进去待一晚吗？"
        );

        start.addOption(new DialogueOption("让她进来。", "__allow_current_guest__", false));
        start.addOption(new DialogueOption("请她离开。", "__reject_current_guest__", false));

        panic_girl.addDialogueNode(start);
        panic_girl.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "谢谢你昨晚让我进来。",
                "外面真的太冷了。"
        );

        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("先这样。", null, true));

        panic_girl.addDayDialogueNode(dayStart);
        panic_girl.dayStartDialogueNodeId = "day_start";

        return panic_girl;
    }



}

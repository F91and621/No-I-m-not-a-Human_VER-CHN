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
                "听我姐说，他们会试图闯入别人家，",
                "但也不偷也不抢。",
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
                "我天......都是真的......",
                "那我姐说的都是真的。",
                "白天别出门了，晚上别一个人在家。"
        );
        dayStart.addOption(new DialogueOption("非要让人进我家？", "day_a1", false));
        dayStart.addOption(new DialogueOption("让别人进我家？", "day_a1", false));


        DialogueNode dayA1 = new DialogueNode(
                "day_a1",
                "我知道你喜欢独处，但现在没办法。",
                "现在外面到底变成什么样，谁心里都没底。",
                "不过我姐说了，",
                "如果有人敲门，问你是不是一个人在家，",
                "你就说家里有人陪着你。",
                "先忍忍吧。",
                "是不是热的受不了了？",
                "来喝啤酒吧，喝了你就想睡觉。"
        );
        dayA1.addOption(new DialogueOption("哎...好吧。", "day_A2", true));

        neighbor.addDayDialogueNode(dayStart);
        neighbor.addDayDialogueNode(dayA1);


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
                "*咳咳*",
                "你能让我进去吗，兄弟？",
                ""
        );


        start.addOption(new DialogueOption("你是谁？", "A1", false));
        start.addOption(new DialogueOption("你怎么了？", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                       "我是*咳*消防员。",
                       "今天我们扑灭山火时，",
                       "我们站在太阳底下。",
                       "浓烟帮我们避免了被完全烧死，",
                       "但*咳*，情况依然很糟糕。",
                       "我从没见过这样的。",
                       "全是死鸟死动物..."

        );
        A1.addOption(new DialogueOption("你为什么不在医院？", "B1", false));
        A1.addOption(new DialogueOption("你打算怎么办？", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                        "我是小队里唯一剩下的。" ,
                        "剩下的，他们......*咳* 上边就是不让他们走。" ,
                        "他们永远值班了。"
        );
        A2.addOption(new DialogueOption("你为什么不在医院？", "B1", false));
        A2.addOption(new DialogueOption("你打算怎么办？", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "医院？没有医院了，兄弟。" ,
                        "一闹伪人，哪还有什么医院。" ,
                        "现在*咳*，谁也帮不了我了。" ,
                        "我还不如和战友们一起死在那里。" ,
                        "毕竟，" ,
                        "我也没多少时间了。"
        );
        B1.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "我想继续灭火。" ,
                        "我真希望是我死，不是他们死。" ,
                        "我现在唯一能做的，也就是靠好心人帮助了。"
        );
        B2.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        firefighter.addDialogueNode(start);
        firefighter.addDialogueNode(A1);
        firefighter.addDialogueNode(A2);
        firefighter.addDialogueNode(B1);
        firefighter.addDialogueNode(B2);


        firefighter.addDialogueNode(start);
        firefighter.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "够热的..."
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
                "打扰了...你有空吗？" ,
                "我能和你谈谈吗？"
        );


        start.addOption(new DialogueOption("发生了什么？", "A1", false));
        start.addOption(new DialogueOption("你是谁？", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "我只是想给那些孩子们带来一点希望。" ,
                        "我收留了他们称为“伪人”的孩子。" ,
                        "我们住在旧厂房里。" ,
                        "他们只是需要有人照顾他们。去爱他们。",
                        "我已经尽力了..." ,
                        "但这还不够...我没能救他们。"

        );
        A1.addOption(new DialogueOption("孩子们发生了什么？", "B1", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "我是个老师...幼儿园老师。" ,
                        "当世界开始崩溃时，我把那些孩子们也接进来了。" ,
                        "他们各自因各种原因失去了父母。",
                        "有些被隔离，有些人被杀。有些..." ,
                        "有些父母，一旦他们孩子表现出是伪人的迹象,",
                        "他们就会扔下这个孩子。",
                        "但现在...他们都走了。没有一个人在大火中幸存..."
        );
        A2.addOption(new DialogueOption("为什么会发生火灾？", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "他们......" ,
                        "......" ,
                        "他们全都死了......在火焰中。" ,
                        "他们都这么做了。他们其实很害怕孩子们可能是伪人。" ,
                        "他们强迫我看......他们先锁上门，钉死窗户......" ,
                        "然后他们放火烧了那栋楼。" ,
                        "我依然能感受到他们，",
                        "他们那惊恐的小眼睛在注视着我。"
        );
        B1.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "那是......其他镇民。" ,
                        "他们发现我在收留那些有伪人迹象的孩子。" ,
                        "......" ,
                        "我们变成了什么？",
                        "一群无情的暴民，对无助的孩子进行评判？" ,
                        "他们到底..." ,
                        "做了什么才配得上那样的对待？" ,
                        "他们把一切都烧了。还强迫我看。"
        );
        B2.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        teacher.addDialogueNode(start);
        teacher.addDialogueNode(A1);
        teacher.addDialogueNode(A2);
        teacher.addDialogueNode(B1);
        teacher.addDialogueNode(B2);

        teacher.addDialogueNode(start);
        teacher.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "孩子们......"
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
                "你好。" ,
                        "我不热。我只是想找个安静的地方休息。" ,
                        "你家里安静吗？",
                ""
        );
        start.addOption(new DialogueOption("你是谁？", "A1", false));
        start.addOption(new DialogueOption("你到这地方多久了？", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                " 无所谓了。你在想我是不是伪人吗？" ,
                        "我不是。但我确实有个请求。" ,
                        "我待会儿问你。我还不确定能不能信任你。"

        );
        A1.addOption(new DialogueOption("你说你不热？", "B1", false));
        A1.addOption(new DialogueOption("你就不能脱了那个破袄。", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "..." ,
                        "很久。"
        );
        A2.addOption(new DialogueOption("你说你不热？", "B1", false));
        A2.addOption(new DialogueOption("你就不能脱了那个破袄。", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "我总是感到很冷。" ,
                        "我裹着好几层衣服，仍然感觉快冻僵了。" ,
                        "我害怕脱掉任何外套。" ,
                        "如果情况变得更糟怎么办？",
                        "没有死亡来缓解痛苦吗？"
        );
        B1.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "不。我不脱。" ,
                        "你不必让我进去。" ,
                        "我走了。我不会求你。我不会再打扰你了。"
        );
        B2.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        coatperson.addDialogueNode(start);
        coatperson.addDialogueNode(A1);
        coatperson.addDialogueNode(A2);
        coatperson.addDialogueNode(B1);
        coatperson.addDialogueNode(B2);

        coatperson.addDialogueNode(start);
        coatperson.startDialogueNodeId = "start";


        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "早。",
                "屋里比外面暖和。",
                "再暖和点就好了..."
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
                "你好！"
        );
        start.addOption(new DialogueOption("你想要什么？", "A1", false));
        start.addOption(new DialogueOption("你别想进来。", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "你这房子挺大。",
                "我喜欢。"
        );
        A1.addOption(new DialogueOption("快滚蛋。", "B1", false));
        A1.addOption(new DialogueOption("我有枪。", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "你这门结实不？",
                "你永远不知道明天会发生什么。",
                "哈哈哈哈。"
        );
        A2.addOption(new DialogueOption("快滚蛋。", "B1", false));
        A2.addOption(new DialogueOption("我有枪。", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "哈哈！",
                "所以......",
                "你家就你一个人？"
        );
        B1.addOption(new DialogueOption("我家有好多人。", "B3", false));
        B1.addOption(new DialogueOption("就我一个。", "B4", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "哈哈！",
                "所以......",
                "你家就你一个人？"
        );


        B2.addOption(new DialogueOption("我家有好多人。", "B3", false));
        B2.addOption(new DialogueOption("就我一个。", "B4", false));

        DialogueNode B3 = new DialogueNode(
                "B3",
                "......",
                "算你走运。"
        );
        B3.addOption(new DialogueOption("......","__judge_answer_no__",false));

        DialogueNode B4 = new DialogueNode(
                "B4",
                "哈哈！",
                "我知道你一个人。",
                "我就想看看你会不会撒谎。",
                "我自己进去。"
        );
        B4.addOption(new DialogueOption("......","__judge_answer_yes__",false));

        DialogueNode fourthStart = new DialogueNode(
                "fourth_start",
                "又见面了。",
                ".....",
                "你觉得刚才那个戴面具的怎么样？",
                "你屋子里的气味变了。",
                "有人不见了，也有人留下来了。"
        );
        fourthStart.addOption(new DialogueOption("你又想确认什么？", "fourth_A1", false));
        fourthStart.addOption(new DialogueOption("你怎么又回来了？", "fourth_A2", false));

        DialogueNode fourthA1 = new DialogueNode(
                "fourth_A1",
                "哈哈哈哈哈！",
                "那你又到底能做些什么？",
                "你能抵挡死亡吗？",
                "你能阻止洪水吗？",
                "你能阻止火灾？",
                "你唯一能阻止的，",
                "就是你的心跳。",
                "好了......",
                "你家就你一个人？"
        );
        fourthA1.addOption(new DialogueOption("我家有好多人。", "fourthA3", false));
        fourthA1.addOption(new DialogueOption("就我一个。", "fourthA4", false));

        DialogueNode fourthA2 = new DialogueNode(
                "fourth_A2",
                "最近情况很艰难吧。",
                "还在这个新世界里求生吗？",
                "为什么？",
                "你明白我的意思吧？",
                "美好时光，只在昨日。",
                "生活已经改变了。",
                "你现在的节奏很不合适。",
                "好了......",
                "你家就你一个人？"

        );
        fourthA2.addOption(new DialogueOption("我家有好多人。", "fourthA3", false));
        fourthA2.addOption(new DialogueOption("就我一个。", "fourthA4", false));

        DialogueNode fourthA3 = new DialogueNode(
                "fourthA3",
                "......",
                "好。",
                "我们都期待你能撑多久。"
        );
        fourthA3.addOption(new DialogueOption("......","__judge_answer_no__",false));

        DialogueNode fourthA4 = new DialogueNode(
                "fourthA4",
                "哈哈！",
                "我知道你一个人。",
                "最后和谁都不能相处吗？",
                "真是遗憾。",
                "那我就快点溜进去吧。",
                "我有个小礼物要给你。"
        );
        fourthA4.addOption(new DialogueOption("......","__judge_answer_yes__",false));

        judgeVisitor.addDialogueNode(start);
        judgeVisitor.addDialogueNode(A1);
        judgeVisitor.addDialogueNode(A2);
        judgeVisitor.addDialogueNode(B1);
        judgeVisitor.addDialogueNode(B2);
        judgeVisitor.addDialogueNode(B3);
        judgeVisitor.addDialogueNode(B4);

        judgeVisitor.addDialogueNode(fourthStart);
        judgeVisitor.addDialogueNode(fourthA1);
        judgeVisitor.addDialogueNode(fourthA2);
        judgeVisitor.addDialogueNode(fourthA3);
        judgeVisitor.addDialogueNode(fourthA4);
        judgeVisitor.startDialogueNodeId = "start";



        return judgeVisitor;
    }

    public static Guest createWidow() {
        Guest widow = new Guest("widow", "guafu");
        widow.setIdentity(GuestIdentity.HUMAN);

        DialogueNode start = new DialogueNode(
                "start",
                "..." ,
                      "我能进去歇歇吗？"
        );
        start.addOption(new DialogueOption("你从哪来？", "A1", false));
        start.addOption(new DialogueOption("歇够了你会去哪？", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "我不知道了。这不重要。",
                "我太累了。",
                "如果我不休息，我可能立马会晕倒。"

        );
        A1.addOption(new DialogueOption("你的朋友生病了吗？", "B1", false));
        A1.addOption(new DialogueOption("你的朋友需要帮助吗？", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "大概会晕死在哪里，再也起不来。",
                "我不在乎最终会去哪。",
                "反正我也快死了。"
        );
        A2.addOption(new DialogueOption("你的朋友生病了吗？", "B1", false));
        A2.addOption(new DialogueOption("你的朋友需要帮助吗？", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "他是我丈夫。" ,
                        "他死了。" ,
                        "我不能好好把他埋了。现在扔下他也没意义。"
        );
        B1.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "不用了。",
                "他需要帮助的时候，没一个人帮他。",
                "现在他死了。",
                "也没人在乎他。"
        );
        B2.addOption(new DialogueOption("请进。", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        widow.addDialogueNode(start);
        widow.addDialogueNode(A1);
        widow.addDialogueNode(A2);
        widow.addDialogueNode(B1);
        widow.addDialogueNode(B2);


        widow.addDialogueNode(start);
        widow.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "我需要休息一下......"
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
                "哎！" ,
                "有人没有啊？",
                ""
        );
        start.addOption(new DialogueOption("你是谁？", "A1", false));
        start.addOption(new DialogueOption("你在找人吗？", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "俺和俺孩们以前就住在这附近的村。" ,
                        "那些黄衣服王八蛋把俺们赶出了村，但没带俺们走。" ,
                        "没有收容所，也没有帮助。俺们该去哪儿？该咋办？" ,
                        "之后大伙都各走各的了。"

        );
        A1.addOption(new DialogueOption("他们为什么把你赶走？", "B1", false));
        A1.addOption(new DialogueOption("你们有很多人吗？", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "俺只想找个地儿歇歇。" ,
                        "整个村都被清空了，知不道该上哪去。"
        );
        A2.addOption(new DialogueOption("他们为什么把你赶走？", "B1", false));
        A2.addOption(new DialogueOption("你们有很多人吗？", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "说俺们村被严重的太阳能冲击了，或者什么奇怪的事。" ,
                        "黄衣服的冲了进来。把人家赶出去。" ,
                        "还抓了几个人。" ,
                        "真是一群王八蛋。"
        );
        B1.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                        "有的人不走，他们就把这群人抓了出来。" ,
                        "就跟挑牲口似的。" ,
                        "现在俺就想活命..."
        );
        B2.addOption(new DialogueOption("进来吧。", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        auntie.addDialogueNode(start);
        auntie.addDialogueNode(A1);
        auntie.addDialogueNode(A2);
        auntie.addDialogueNode(B1);
        auntie.addDialogueNode(B2);

        auntie.addDialogueNode(start);
        auntie.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "小伙子..." ,
                        "最好别放其他人进来。"
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
                "哥，哥..." ,
                        "让...让我进去吧。",
                ""
        );
        start.addOption(new DialogueOption("怎么这么着急呀？", "A1", false));
        start.addOption(new DialogueOption("怎么了？", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "我不知道该怎么办。" ,
                        "伪人......" ,
                        "杀......" ,
                        "杀了我爸......" ,
                        "我，我，我......我，我的......" ,
                        "爸爸。"

        );
        A1.addOption(new DialogueOption("伪人？", "B1", false));
        A1.addOption(new DialogueOption("他们为什么这么做？", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "呃......" ,
                        "我、我、我爸爸......" ,
                        "伪人......伪人......" ,
                        "杀了我爸爸。"
        );
        A2.addOption(new DialogueOption("伪人？", "B1", false));
        A2.addOption(new DialogueOption("他们为什么这么做？", "B2", false));
        DialogueNode B1 = new DialogueNode(
                "B1",
                "伪人来我们家了。他们和我们坐在一起......" ,
                        "他们正说话呢。然后他们......杀了他。" ,
                        "他......*抽泣*我们的......*抽泣*" ,
                        "我们家还着火了......",
                        "啊！啊啊啊！爸爸！爸爸！"
        );
        B1.addOption(new DialogueOption("快进来。", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "我......我不知道。" ,
                        "爸爸...爸爸就是跟他们说话呢。" ,
                        "然后他们......然后他们放火烧了房子。" ,
                        "我...*抽泣*我听见他们笑......",
                        "啊！啊啊啊！爸爸！爸爸！"
        );
        B2.addOption(new DialogueOption("快进来。", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("你最好赶紧走。", "__reject_current_guest__", false));

        panic_girl.addDialogueNode(start);
        panic_girl.addDialogueNode(A1);
        panic_girl.addDialogueNode(A2);
        panic_girl.addDialogueNode(B1);
        panic_girl.addDialogueNode(B2);

        panic_girl.addDialogueNode(start);
        panic_girl.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "爸爸...妈妈..."
        );

        dayStart.addOption(new DialogueOption("让我检查你。", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("先这样。", null, true));

        panic_girl.addDayDialogueNode(dayStart);
        panic_girl.dayStartDialogueNodeId = "day_start";

        return panic_girl;
    }

    public static Guest createCollector() {
        Guest collector = new Guest("collector", "收集者");

        collector.setIdentity(GuestIdentity.VISITOR);

        DialogueNode start = new DialogueNode(
                "start",
                "晚上好。",
                "我们是上头派来的。多余信息不便透露。"
        );

        start.addOption(new DialogueOption("就你一个？", "A1", false));
        start.addOption(new DialogueOption("我凭什么相信你？", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "不，当然不是。",
                "我们一个小队分散了，以便覆盖更广的区域。",
                "我不会占你太长时间。"
        );
        A1.addOption(new DialogueOption("你要做什么？","B1",false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "这是我的证件。你有义务先听我说话。"
        );
        A2.addOption(new DialogueOption("你要做什么？","B1",false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "我要一个人。",
                "我们会对他做检查和研究",
                "确保这个地方的危机能够尽快解决。",
                "如果他是人类，我们会确保他的安全。",
                "为了大家的安全，请你考虑清楚。"
        );
        B1.addOption(new DialogueOption("……", null, true));
        collector.addDialogueNode(start);
        collector.addDialogueNode(A1);
        collector.addDialogueNode(A2);
        collector.addDialogueNode(B1);
        collector.startDialogueNodeId = "start";
        return collector;
    }





}

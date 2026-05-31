import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NightPhaseManager {

    // 定义夜晚交互的三种状态 / Define the three states of night interaction
    public enum NightState {
        WAITING_FOR_KNOCK, // 等待客人敲门 / Waiting for a guest to knock
        AT_DOOR,           // 玩家正在门洞前对话 / Player is interacting at the peephole
        NIGHT_ENDED        // 今夜结束（没人排队了） / Night ended (no more guests in queue)
    }

    private NightState currentState;           // 当前夜晚状态 / Current night state
    private Queue<Guest> guestsOutside;        // 门外的访客队列 / Queue of visitors outside the door
    private Guest currentVisitor;              // 当前正在门外的访客 / The visitor currently at the door
    private SafeHouseManager safeHouseManager; // 引用白天的收留名单 / Reference to the Safe House list

    // 构造函数 / Constructor
    public NightPhaseManager(SafeHouseManager safeHouseManager) {
        this.safeHouseManager = safeHouseManager;
        this.guestsOutside = new LinkedList<>();
        this.currentState = NightState.NIGHT_ENDED;
    }

    // 开始新的夜晚，把今晚要来的客人排好队 / Start a new night, queue up tonight's guests
    public void startNight(List<Guest> tonightGuests) {
        guestsOutside.clear();
        guestsOutside.addAll(tonightGuests);
        currentState = NightState.WAITING_FOR_KNOCK;
        System.out.println("=== 夜幕降临。请注意门外的动静。 ===");
        System.out.println("=== Night has fallen. Pay attention to the door. ===");
    }

    // 引擎层每帧调用此方法来检测是否触发敲门 / Engine calls this every frame to check for knocks
    public void checkKnockEvent() {
        if (currentState == NightState.WAITING_FOR_KNOCK && !guestsOutside.isEmpty()) {
            // 弹出队列第一个客人 / Poll the first guest from the queue
            currentVisitor = guestsOutside.poll();
            currentState = NightState.AT_DOOR;

            // 这里可以通知引擎播放敲门音效 / Engine can be notified here to play knock.wav
            System.out.println("\n*咚咚咚* (Knock, knock, knock)");
            System.out.println("前台提示 (System): 有人敲门。按 [E] 键查看。 (Someone is at the door. Press [E] to check.)");
        } else if (currentState == NightState.WAITING_FOR_KNOCK && guestsOutside.isEmpty()) {
            // 没人了，夜晚结束 / Queue empty, end the night phase
            currentState = NightState.NIGHT_ENDED;
            System.out.println("今夜不会再有人来了，去睡觉吧。 (No one else is coming tonight. Go to sleep.)");
        }
    }

    // 玩家按E键通过猫眼对话 / Player presses [E] to interact through the peephole
    public void interactAtDoor() {
        if (currentState == NightState.AT_DOOR && currentVisitor != null) {
            System.out.println("玩家 (Player): 谁在外面？ (Who is out there?)");
            System.out.println(currentVisitor.name + ": " + currentVisitor.getDoorGreeting());

            // 抛出选项给玩家 / Present options to the player
            System.out.println("-> 按 [Y] 收留他 (Press [Y] to Shelter) / 按 [N] 拒绝开门 (Press [N] to Reject)");
        } else {
            System.out.println("门外什么也没有... (There is nothing outside...)");
        }
    }

    // 玩家做出决定（按 Y 或 N） / Player makes a decision (Presses Y or N)
    public void makeDecision(boolean letIn) {
        if (currentState != NightState.AT_DOOR || currentVisitor == null) {
            return; // 不在对话状态则忽略输入 / Ignore input if not at the door
        }

        if (letIn) {
            System.out.println("你打开了门，" + currentVisitor.name + " 走了进来。");
            System.out.println("(You opened the door and " + currentVisitor.name + " stepped inside.)");

            // 关键：加入白天的检查名单 / CRITICAL: Add to daytime inspection list
            safeHouseManager.admitGuest(currentVisitor);
        } else {
            System.out.println("你拒绝开门，门外的脚步声逐渐远去...");
            System.out.println("(You refused to open the door. The footsteps fade away...)");
        }

        // 决定做完，清空当前访客，继续等待下一次敲门
        // Decision made, clear current visitor, wait for the next knock
        currentVisitor = null;
        currentState = NightState.WAITING_FOR_KNOCK;
    }

    // 获取当前夜晚状态供引擎判断 / Get current state for the engine to check
    public NightState getCurrentState() {
        return currentState;
    }
    public Guest getCurrentVisitor() {
        return currentVisitor;
    }

}
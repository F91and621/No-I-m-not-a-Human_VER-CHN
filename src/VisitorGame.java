import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;



public class VisitorGame extends GameEngine {

    private HashMap<String, GuestRoomPlacement> guestRoomPlacements;
    private HashMap<String, GuestRoomPlacement> guestCorpseRoomPlacements;

    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1080;

    private boolean introVideoPlaying = false;
    private JFXPanel introVideoPanel;
    private MediaPlayer introMediaPlayer;

    private Random random = new Random();

    private int currentDay;
    private GamePhase currentPhase;
    private SceneType currentScene;

    private GuestManager guestManager;
    private SafeHouseManager safeHouseManager;


    private HashMap<Integer, Image> morningTransitionImages;
    private boolean morningTransitionActive = false;
    private Image currentMorningTransitionImage = null;


    private HashMap<SceneType, Image> sceneImages;
    private HashMap<String, Image> characterImages;

    private Image dialogueOptionSpriteSheet;
    private Image[] dialogueOptionFrames;

    private Image gunIdleSpriteSheet;
    private Image gunShootSpriteSheet;
    private Image[] gunIdleFrames;
    private Image[] gunShootFrames;

    private Image toothSpriteSheet;
    private Image[] toothSpriteFrames;
    private int toothSpriteFrameIndex = 0;
    private long lastToothSpriteFrameTime = 0L;
    private boolean toothSpriteAnimationFinished = false;

    private static final int TOOTH_SPRITE_FRAME_COUNT = 7;
    private static final int TOOTH_SPRITE_FRAME_COLS = 3;
    private static final int TOOTH_SPRITE_FRAME_W = 682;
    private static final int TOOTH_SPRITE_FRAME_H = 232;
    private static final long TOOTH_SPRITE_FRAME_INTERVAL = 100;

    private int gunIdleFrameIndex = 0;
    private int gunShootFrameIndex = 0;
    private long lastGunFrameTime = 0L;

    private boolean gunConfirmActive = false;
    private boolean gunShootActive = false;

    private static final int GUN_FRAME_W = 682;
    private static final int GUN_FRAME_H = 368;

    private static final int GUN_IDLE_FRAME_COUNT = 6;
    private static final int GUN_IDLE_FRAME_COLS = 3;

    private static final int GUN_SHOOT_FRAME_COUNT = 9;
    private static final int GUN_SHOOT_FRAME_COLS = 3;

    private static final long GUN_IDLE_FRAME_INTERVAL = 90;
    private static final long GUN_SHOOT_FRAME_INTERVAL = 70;

    private static final String SPECIAL_PULL_TRIGGER = "__pull_trigger__";
    private static final String SPECIAL_LOWER_GUN = "__lower_gun__";



    private int dialogueOptionFrameIndex = 0;
    private long lastDialogueOptionFrameTime = 0L;

    private static final int DIALOGUE_OPTION_FRAME_COUNT = 6;
    private static final int DIALOGUE_OPTION_FRAME_COLS = 3;
    private static final int DIALOGUE_OPTION_FRAME_ROWS = 2;
    private static final int DIALOGUE_OPTION_FRAME_W = 396;
    private static final int DIALOGUE_OPTION_FRAME_H = 88;

    private static final long DIALOGUE_OPTION_FRAME_INTERVAL = 120;


    private AudioClip knockSound;
    private AudioClip dayMusic;
    private AudioClip night1Music;
    private AudioClip night2Music;
    private AudioClip night3Music;
    private AudioClip night4Music;
    private AudioClip superMusic;
    private AudioClip shootSound;

    private AudioClip currentBackgroundMusic;

    private static final double AUDIO_FADE_SECONDS = 3.0;
    private static final float FADE_START_DB = -40.0f;
    private static final float MUSIC_TARGET_DB = -8.0f;
    private static final float KNOCK_TARGET_DB = 6.0f;

    private boolean musicFadeActive = false;
    private double musicFadeTimer = 0;

    private boolean knockLoopActive = false;
    private boolean knockFadeActive = false;
    private double knockFadeTimer = 0;

    private HashSet<String> flags;

    private String message;
    private String forcedPortraitGuestId;


    private boolean showDialogue;
    private String activeGuestId;
    private boolean activeDialogueIsDay;
    private DialogueNode activeDialogueNode;
    private int activeDialogueLineIndex;
    private String pendingDialogueResult;

    private String outsideGuestId;
    private boolean outsideGuestVisible;

    private boolean flashBlackActive;
    private double flashBlackTimer;
    private double flashBlackDuration;
    private String pendingFlashAction;

    private boolean flashWhiteActive;
    private double flashWhiteTimer;
    private double flashWhiteDuration;
    private String pendingFlashWhiteAction;


    private int maxStamina;
    private int currentStamina;
    private boolean inspectionActive;
    private String inspectedGuestId;
    private InspectionFeatureType currentInspectionFeature;
    private String currentInspectionImageKey;



    private ArrayList<String> nightVisitorQueue;
    private HashMap<String, String> outsideGuestImageKeys;




    private boolean debugClickAreas = true;

    private static final String GUEST_NEIGHBOR = "neighbor";
    private static final String GUEST_DAUGHTER = "daughter";
    private static final String GUEST_FIREFIGHTER = "firefighter";
    private static final String GUEST_TEACHER = "teacher";
    private static final String GUEST_COAT_PERSON = "coat_person";
    private static final String GUEST_JUDGE_VISITOR = "judge_visitor";
    private static final String GUEST_WIDOW = "widow";
    private static final String GUEST_AUNTIE = "auntie";
    private static final String GUEST_PANIC_GIRL = "panic_girl";
    private static final String GUEST_COLLECTOR = "collector";


    private static final String PORTRAIT_NEIGHBOR = "portrait_neighbor";
    private static final String PORTRAIT_FIREFIGHTER = "portrait_firefighter";
    private static final String PORTRAIT_TEACHER = "portrait_teacher";
    private static final String PORTRAIT_COAT_PERSON = "portrait_coat_person";
    private static final String PORTRAIT_WIDOW = "portrait_widow";
    private static final String PORTRAIT_AUNTIE = "portrait_auntie";
    private static final String PORTRAIT_PANIC_GIRL = "portrait_panic_girl";



    private static final String CHARACTER_NEIGHBOR_OUTSIDE = "neighbor_outside";
    private static final String CHARACTER_NEIGHBOR_DAY = "neighbor_day";
    private static final String CHARACTER_DAUGHTER_OUTSIDE = "daughter_outside";
    private static final String CHARACTER_FIREFIGHTER_OUTSIDE = "firefighter_outside";
    private static final String CHARACTER_TEACHER_OUTSIDE = "teacher_outside";
    private static final String CHARACTER_COAT_PERSON_OUTSIDE = "coat_person_outside";
    private static final String CHARACTER_JUDGE_VISITOR_OUTSIDE = "judge_visitor_outside";
    private static final String CHARACTER_WIDOW_OUTSIDE = "widow_outside";
    private static final String CHARACTER_AUNTIE_OUTSIDE = "auntie_outside";
    private static final String CHARACTER_PANIC_GIRL_OUTSIDE = "panic_girl_outside";
    private static final String CHARACTER_COLLECTOR_OUTSIDE = "collector_outside";



    private static final String CHARACTER_FIREFIGHTER_DAY = "firefighter_day";
    private static final String CHARACTER_TEACHER_DAY = "teacher_day";
    private static final String CHARACTER_COAT_PERSON_DAY = "coat_person_day";
    private static final String CHARACTER_TRASH_BAG = "trash_bag";
    private static final String CHARACTER_COAT_PERSON_CORPSE = "coat_person_corpse";
    private static final String CHARACTER_WIDOW_DAY = "widow_day";
    private static final String CHARACTER_AUNTIE_DAY = "auntie_day";
    private static final String CHARACTER_PANIC_GIRL_DAY = "panic_girl_day";

    private static final String CHARACTER_AUNTIE_CORPSE = "auntie_corpse";







    private static final String ACTION_TALK_GUEST_PREFIX = "talk_guest:";


    private static final String FLAG_KNOCK_HEARD = "knock_heard";
    private static final String FLAG_NEIGHBOR_INSIDE_HOUSE = "neighbor_inside_house";
    private static final String FLAG_NO_MORE_VISITORS_CONFIRMED = "no_more_visitors_confirmed";
    private static final String FLAG_NEIGHBOR_TALKED_MORNING = "neighbor_talked_morning";
    private static final String FLAG_NEIGHBOR_LEFT_WITH_DAUGHTER = "neighbor_left_with_daughter";
    private static final String FLAG_SECOND_NIGHT_STARTED = "second_night_started";
    private static final String FLAG_SECOND_NIGHT_VISITORS_DONE = "second_night_visitors_done";

    private static final String FLAG_DAUGHTER_VISITED = "daughter_visited";
    private static final String FLAG_FIREFIGHTER_VISITED = "firefighter_visited";
    private static final String FLAG_TEACHER_VISITED = "teacher_visited";
    private static final String FLAG_COAT_PERSON_VISITED = "coat_person_visited";
    private static final String FLAG_THIRD_NIGHT_STARTED = "third_night_started";
    private static final String FLAG_THIRD_NIGHT_VISITORS_DONE = "third_night_visitors_done";

    private static final String FLAG_JUDGE_VISITOR_VISITED = "judge_visitor_visited";
    private static final String FLAG_WIDOW_VISITED = "widow_visited";
    private static final String FLAG_AUNTIE_VISITED = "auntie_visited";
    private static final String FLAG_PANIC_GIRL_VISITED = "panic_girl_visited";

    private static final String FLAG_GAME_OVER = "game_over";

    private static final String FLAG_FOURTH_NIGHT_STARTED = "fourth_night_started";
    private static final String FLAG_FOURTH_NIGHT_VISITORS_DONE = "fourth_night_visitors_done";

    private static final String FLASH_START_FOURTH_NIGHT = "flash_start_fourth_night";

    private static final String RESULT_COLLECTOR_TAKE_RANDOM = "collector_take_random";

    private static final String MESSAGE_ENDING_WIN = "屋子里已经没有伪人了。你活到了最后。你是胜利者。（空格键重新开始）";
    private static final String MESSAGE_ENDING_LOSE = "屋子里仍然藏着伪人。夜深之后，你被潜伏的伪人杀死了。游戏结束。（空格键重新开始）";




    private static final String ACTION_GO_YARD = "go_yard";
    private static final String ACTION_OPEN_GATE = "open_gate";
    private static final String ACTION_CHECK_EMPTY_GATE = "check_empty_gate";
    private static final String ACTION_GO_BEDROOM_NIGHT = "go_bedroom_night";
    private static final String ACTION_SLEEP = "sleep";
    private static final String ACTION_DAY_SLEEP = "day_sleep";
    private static final String FLASH_START_SECOND_NIGHT = "flash_start_second_night";
    private static final String FLASH_START_THIRD_NIGHT = "flash_start_third_night";
    private static final String FLASH_GAME_OVER_BY_JUDGE = "flash_game_over_by_judge";




    private static final String RESULT_NEIGHBOR_ENTER_HOUSE = "neighbor_enter_house";
    private static final String RESULT_FINISH_DAY_TALK = "finish_day_talk";
    private static final String RESULT_DAUGHTER_TAKE_NEIGHBOR = "daughter_take_neighbor";
    private static final String RESULT_JUDGE_VISITOR_DECISION = "judge_visitor_decision";


    private static final String RESULT_NIGHT_VISITOR_ALLOW_PREFIX = "night_visitor_allow:";
    private static final String RESULT_NIGHT_VISITOR_REJECT_PREFIX = "night_visitor_reject:";



    private static final String FLASH_REVEAL_OUTSIDE_GUEST = "flash_reveal_outside_guest";
    private static final String FLASH_SLEEP_TO_NEXT_MORNING = "flash_sleep_to_next_morning";
    private static final String FLASH_KILL_INSPECTED_GUEST = "flash_kill_inspected_guest";


    private static final String SPECIAL_INSPECT_FEATURES = "__inspect_features__";
    private static final String SPECIAL_INSPECT_HANDS = "__inspect_hands__";
    private static final String SPECIAL_INSPECT_TEETH = "__inspect_teeth__";
    private static final String SPECIAL_INSPECT_KILL = "__inspect_kill__";
    private static final String SPECIAL_INSPECT_SPARE = "__inspect_spare__";
    private static final String SPECIAL_ALLOW_CURRENT_GUEST = "__allow_current_guest__";
    private static final String SPECIAL_REJECT_CURRENT_GUEST = "__reject_current_guest__";
    private static final String SPECIAL_JUDGE_ANSWER_YES = "__judge_answer_yes__";
    private static final String SPECIAL_JUDGE_ANSWER_NO = "__judge_answer_no__";

    private static final String MESSAGE_SOMEONE_DIED = "闻到一股血腥味......好像有人死了。";
    private static final String MESSAGE_GAME_OVER = "伪人破门而入，你被杀死。游戏结束。（空格键重新开始）";




    private static final String ACTION_GO_HALLWAY_DAY = "go_hallway_day";
    private static final String ACTION_GO_BEDROOM_DAY = "go_bedroom_day";
    private static final String ACTION_GO_KITCHEN_DAY = "go_kitchen_day";
    private static final String ACTION_GO_STORAGE_DAY = "go_storage_day";
    private static final String ACTION_GO_LIVING_ROOM_DAY = "go_living_room_day";


    private static final String INSPECT_TEACHER_TEETH = "teacher_teeth";
    private static final String INSPECT_TEACHER_HANDS = "teacher_hands";

    private static final String INSPECT_FIREFIGHTER_TEETH = "firefighter_teeth";
    private static final String INSPECT_FIREFIGHTER_HANDS = "firefighter_hands";

    private static final String INSPECT_COAT_PERSON_TEETH = "coat_person_teeth";
    private static final String INSPECT_COAT_PERSON_HANDS = "coat_person_hands";

    private static final String INSPECT_WIDOW_TEETH = "widow_teeth";
    private static final String INSPECT_WIDOW_HANDS = "widow_hands";

    private static final String INSPECT_AUNTIE_TEETH = "auntie_teeth";
    private static final String INSPECT_AUNTIE_HANDS = "auntie_hands";

    private static final String INSPECT_PANIC_GIRL_TEETH = "panic_girl_teeth";
    private static final String INSPECT_PANIC_GIRL_HANDS = "panic_girl_hands";


    private static final int BACK_BUTTON_X = 40;
    private static final int BACK_BUTTON_Y = 130;
    private static final int BACK_BUTTON_W = 180;
    private static final int BACK_BUTTON_H = 95;

    private static final int DIALOGUE_BOX_X = 1040;
    private static final int DIALOGUE_BOX_Y = 90;
    private static final int DIALOGUE_BOX_W = 760;
    private static final int DIALOGUE_BOX_H = 430;

    private static final int DIALOGUE_TEXT_X = 1090;
    private static final int DIALOGUE_TEXT_Y = 210;

    private static final int OPTION_BUTTON_W = 300;
    private static final int OPTION_BUTTON_H = 56;
    private static final int OPTION_BUTTON_GAP_X = 26;
    private static final int OPTION_BUTTON_GAP_Y = 18;
    private static final int OPTION_START_X = 1090;
    private static final int OPTION_START_Y = 370;
    private static final int OPTION_COLUMNS = 2;

    private String gunTargetGuestId;




    @Override
    public void init() {
        initWindow();
        initGameState();
        initCollections();
        initManagers();
        initImages();
        initOutsideGuestImageKeys();
        initGuestRoomPlacements();
        initGuestCorpseRoomPlacements();
        initAudios();
        initGuests();
        initGunFrames();

        playIntroVideo();

        // 这里不要 startFirstNight()
    }

    @Override
    public void update(double dt) {

        if (introVideoPlaying) {
            return;
        }


        updateDialogue(dt);
        updateMessage(dt);
        updateFlashBlack(dt);
        updateFlashWhite(dt);
        updateGunAnimation();
        updateAudioFades(dt);
        updateKnockLoop();
        updateToothSpriteAnimation();
    }

    @Override
    public void paintComponent() {
        if (morningTransitionActive) {
            drawMorningTransitionLayer();
            return;
        }
        drawBackgroundLayer();
        drawCharacterLayer();
        drawStaminaLayer();
        drawInspectionLayer();
        drawDialogueLayer();
        drawDayDialoguePortrait();
        drawMessageLayer();
        drawGunLayer();
        drawDebugClickAreas();
        drawFlashBlackLayer();
        drawFlashWhiteLayer();
    }

    @Override
    public void keyPressed(KeyEvent event) {

        if (introVideoPlaying && event.getKeyCode() == KeyEvent.VK_ENTER) {
            finishIntroVideo();
            return;
        }

        if (event.getKeyCode() != KeyEvent.VK_SPACE) {
            return;
        }

        if (!hasFlag(FLAG_GAME_OVER)) {
            return;
        }

        restartGame();
    }

    private void restartGame() {
        stopKnockLoop();

        if (currentBackgroundMusic != null) {
            stopAudioLoop(currentBackgroundMusic);
            currentBackgroundMusic = null;
        }

        if (introMediaPlayer != null) {
            introMediaPlayer.stop();
            introMediaPlayer.dispose();
            introMediaPlayer = null;
        }

        introVideoPlaying = false;
        morningTransitionActive = false;
        currentMorningTransitionImage = null;

        showDialogue = false;
        activeGuestId = null;
        activeDialogueIsDay = false;
        activeDialogueNode = null;
        activeDialogueLineIndex = 0;
        pendingDialogueResult = null;

        inspectionActive = false;
        inspectedGuestId = null;
        currentInspectionFeature = null;
        currentInspectionImageKey = null;

        gunConfirmActive = false;
        gunShootActive = false;
        gunTargetGuestId = null;
        forcedPortraitGuestId = null;

        flashBlackActive = false;
        flashBlackTimer = 0;
        flashBlackDuration = 0;
        pendingFlashAction = null;

        flashWhiteActive = false;
        flashWhiteTimer = 0;
        flashWhiteDuration = 0;
        pendingFlashWhiteAction = null;

        message = "";


        flags.clear();
        nightVisitorQueue.clear();

        guestManager = new GuestManager();
        guestManager.initGuests();

        safeHouseManager = new SafeHouseManager();

        currentStamina = 0;
        maxStamina = 0;

        clearOutsideGuest();

        startFirstNight();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        handleMouseClick(event.getX(), event.getY());
    }

    private void initWindow() {
        setWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void initGuestCorpseRoomPlacements() {

        registerGuestCorpseRoomPlacement(
                GUEST_FIREFIGHTER,
                DayRoomType.STORAGE,
                CHARACTER_TRASH_BAG,
                1250, 760, 260, 180
        );

        registerGuestCorpseRoomPlacement(
                GUEST_TEACHER,
                DayRoomType.LIVING_ROOM,
                CHARACTER_TRASH_BAG,
                1450, 700, 280, 190
        );

        registerGuestCorpseRoomPlacement(
                GUEST_COAT_PERSON,
                DayRoomType.LIVING_ROOM,
                CHARACTER_COAT_PERSON_CORPSE,
                360, 580, 300, 430
        );

        registerGuestCorpseRoomPlacement(
                GUEST_WIDOW,
                DayRoomType.LIVING_ROOM,
                CHARACTER_TRASH_BAG,
                1100, 600, 260, 180
        );

        registerGuestCorpseRoomPlacement(
                GUEST_AUNTIE,
                DayRoomType.STORAGE,
                CHARACTER_AUNTIE_CORPSE,
                100, 400, 360, 480
        );

        registerGuestCorpseRoomPlacement(
                GUEST_PANIC_GIRL,
                DayRoomType.KITCHEN,
                CHARACTER_TRASH_BAG,
                1360, 820, 230, 160
        );
    }

    private void registerGuestCorpseRoomPlacement(
            String guestId,
            DayRoomType room,
            String imageKey,
            int x,
            int y,
            int width,
            int height
    ) {
        String key = makeGuestRoomPlacementKey(guestId, room);

        guestCorpseRoomPlacements.put(
                key,
                new GuestRoomPlacement(
                        guestId,
                        room,
                        imageKey,
                        x,
                        y,
                        width,
                        height
                )
        );
    }

    private GuestRoomPlacement getGuestCorpseRoomPlacement(String guestId, DayRoomType room) {
        String key = makeGuestRoomPlacementKey(guestId, room);
        return guestCorpseRoomPlacements.get(key);
    }

    private void initDialogueOptionFrames() {
        dialogueOptionSpriteSheet = loadImage("assets/UI/dialogue_talk.png");
        dialogueOptionFrames = new Image[DIALOGUE_OPTION_FRAME_COUNT];

        for (int i = 0; i < DIALOGUE_OPTION_FRAME_COUNT; i++) {
            int col = i % DIALOGUE_OPTION_FRAME_COLS;
            int row = i / DIALOGUE_OPTION_FRAME_COLS;

            dialogueOptionFrames[i] = subImage(
                    dialogueOptionSpriteSheet,
                    col * DIALOGUE_OPTION_FRAME_W,
                    row * DIALOGUE_OPTION_FRAME_H,
                    DIALOGUE_OPTION_FRAME_W,
                    DIALOGUE_OPTION_FRAME_H
            );
        }
    }

    private void initGameState() {
        maxStamina = 0;
        currentStamina = 0;

        currentDay = 1;
        currentPhase = GamePhase.NIGHT;
        currentScene = SceneType.BEDROOM_NIGHT;

        message = "";

        showDialogue = false;
        activeGuestId = null;
        activeDialogueIsDay = false;
        activeDialogueNode = null;
        activeDialogueLineIndex = 0;
        pendingDialogueResult = null;

        outsideGuestId = null;
        outsideGuestVisible = false;

        flashBlackActive = false;
        flashBlackTimer = 0;
        flashBlackDuration = 0;
        pendingFlashAction = null;
        flashWhiteActive = false;
        flashWhiteTimer = 0;
        flashWhiteDuration = 0;
        pendingFlashWhiteAction = null;



        inspectionActive = false;
        inspectedGuestId = null;
        currentInspectionFeature = null;
        currentInspectionImageKey = null;

    }

    private void initToothSpriteFrames() {
        toothSpriteSheet = loadImage("assets/images/sprite/tooth_sprite.png");
        toothSpriteFrames = new Image[TOOTH_SPRITE_FRAME_COUNT];

        for (int i = 0; i < TOOTH_SPRITE_FRAME_COUNT; i++) {
            int col = i % TOOTH_SPRITE_FRAME_COLS;
            int row = i / TOOTH_SPRITE_FRAME_COLS;

            toothSpriteFrames[i] = subImage(
                    toothSpriteSheet,
                    col * TOOTH_SPRITE_FRAME_W,
                    row * TOOTH_SPRITE_FRAME_H,
                    TOOTH_SPRITE_FRAME_W,
                    TOOTH_SPRITE_FRAME_H
            );
        }
    }

    private void initGunFrames() {
        gunIdleSpriteSheet = loadImage("assets/UI/fake_gun_idle.png");
        gunShootSpriteSheet = loadImage("assets/UI/fake_gun_shoots.png");

        gunIdleFrames = new Image[GUN_IDLE_FRAME_COUNT];
        gunShootFrames = new Image[GUN_SHOOT_FRAME_COUNT];

        for (int i = 0; i < GUN_IDLE_FRAME_COUNT; i++) {
            int col = i % GUN_IDLE_FRAME_COLS;
            int row = i / GUN_IDLE_FRAME_COLS;

            gunIdleFrames[i] = subImage(
                    gunIdleSpriteSheet,
                    col * GUN_FRAME_W,
                    row * GUN_FRAME_H,
                    GUN_FRAME_W,
                    GUN_FRAME_H
            );
        }

        for (int i = 0; i < GUN_SHOOT_FRAME_COUNT; i++) {
            int col = i % GUN_SHOOT_FRAME_COLS;
            int row = i / GUN_SHOOT_FRAME_COLS;

            gunShootFrames[i] = subImage(
                    gunShootSpriteSheet,
                    col * GUN_FRAME_W,
                    row * GUN_FRAME_H,
                    GUN_FRAME_W,
                    GUN_FRAME_H
            );
        }
    }


    private void initCollections() {
        sceneImages = new HashMap<SceneType, Image>();
        characterImages = new HashMap<String, Image>();
        flags = new HashSet<String>();

        guestRoomPlacements = new HashMap<String, GuestRoomPlacement>();
        guestCorpseRoomPlacements = new HashMap<String, GuestRoomPlacement>();

        nightVisitorQueue = new ArrayList<String>();
        outsideGuestImageKeys = new HashMap<String, String>();
        morningTransitionImages = new HashMap<Integer, Image>();
    }



    private void initManagers() {
        guestManager = new GuestManager();
        safeHouseManager = new SafeHouseManager();



    }

    private void initImages() {
        sceneImages.put(SceneType.BEDROOM_NIGHT, loadImage("assets/images/background/bedroom_night.png"));
        sceneImages.put(SceneType.YARD_NIGHT, loadImage("assets/images/background/yard_night.png"));
        sceneImages.put(SceneType.BEDROOM_DAY, loadImage("assets/images/background/bedroom_day.png"));
        sceneImages.put(SceneType.KITCHEN_DAY, loadImage("assets/images/background/kitchen_day.png"));
        sceneImages.put(SceneType.HALLWAY_DAY, loadImage("assets/images/background/hallway_day.png"));
        sceneImages.put(SceneType.STORAGE_DAY, loadImage("assets/images/background/storage_day.png"));
        sceneImages.put(SceneType.LIVING_ROOM_DAY, loadImage("assets/images/background/living_room_day.png"));
        characterImages.put(CHARACTER_NEIGHBOR_OUTSIDE, loadImage("assets/images/visit/neighbor_outside.png"));
        characterImages.put(CHARACTER_NEIGHBOR_DAY, loadImage("assets/images/dayguests/neighbor_day.png"));
        characterImages.put(CHARACTER_DAUGHTER_OUTSIDE, loadImage("assets/images/visit/daughter_outside.png"));
        characterImages.put(CHARACTER_FIREFIGHTER_OUTSIDE, loadImage("assets/images/visit/firefighter_outside.png"));
        characterImages.put(CHARACTER_TEACHER_OUTSIDE, loadImage("assets/images/visit/teacher_outside.png"));
        characterImages.put(CHARACTER_COAT_PERSON_OUTSIDE, loadImage("assets/images/visit/coat_person_outside.png"));
        characterImages.put(CHARACTER_JUDGE_VISITOR_OUTSIDE, loadImage("assets/images/visit/judge_visitor_outside.png"));
        characterImages.put(CHARACTER_WIDOW_OUTSIDE, loadImage("assets/images/visit/widow_outside.png"));
        characterImages.put(CHARACTER_AUNTIE_OUTSIDE, loadImage("assets/images/visit/auntie_outside.png"));
        characterImages.put(CHARACTER_PANIC_GIRL_OUTSIDE, loadImage("assets/images/visit/panic_girl_outside.png"));
        characterImages.put(CHARACTER_COLLECTOR_OUTSIDE, loadImage("assets/images/visit/collector_outside.png"));


        characterImages.put(CHARACTER_FIREFIGHTER_DAY, loadImage("assets/images/dayguests/firefighter_day.png"));
        characterImages.put(CHARACTER_TEACHER_DAY, loadImage("assets/images/dayguests/teacher_day.png"));
        characterImages.put(CHARACTER_COAT_PERSON_DAY, loadImage("assets/images/dayguests/coat_person_day.png"));
        characterImages.put(CHARACTER_WIDOW_DAY, loadImage("assets/images/dayguests/widow_day.png"));
        characterImages.put(CHARACTER_AUNTIE_DAY, loadImage("assets/images/dayguests/auntie_day.png"));
        characterImages.put(CHARACTER_PANIC_GIRL_DAY, loadImage("assets/images/dayguests/panic_girl_day.png"));
        characterImages.put(CHARACTER_TRASH_BAG, loadImage("assets/images/corpse/trash_bag.png"));
        characterImages.put(CHARACTER_COAT_PERSON_CORPSE, loadImage("assets/images/corpse/coat_person_corpse.png"));
        characterImages.put(CHARACTER_AUNTIE_CORPSE, loadImage("assets/images/corpse/auntie_corpse.png"));


        characterImages.put(INSPECT_TEACHER_TEETH, loadImage("assets/images/inspect/teacher_teeth.png"));
        characterImages.put(INSPECT_TEACHER_HANDS, loadImage("assets/images/inspect/teacher_hands.png"));

        characterImages.put(INSPECT_FIREFIGHTER_TEETH, loadImage("assets/images/inspect/firefighter_teeth.png"));
        characterImages.put(INSPECT_FIREFIGHTER_HANDS, loadImage("assets/images/inspect/firefighter_hands.png"));

        characterImages.put(INSPECT_COAT_PERSON_TEETH, loadImage("assets/images/inspect/coat_person_teeth.png"));
        characterImages.put(INSPECT_COAT_PERSON_HANDS, loadImage("assets/images/inspect/coat_person_hands.png"));

        characterImages.put(INSPECT_WIDOW_TEETH, loadImage("assets/images/inspect/widow_teeth.png"));
        characterImages.put(INSPECT_WIDOW_HANDS, loadImage("assets/images/inspect/widow_hands.png"));

        characterImages.put(INSPECT_AUNTIE_TEETH, loadImage("assets/images/inspect/auntie_teeth.png"));
        characterImages.put(INSPECT_AUNTIE_HANDS, loadImage("assets/images/inspect/auntie_hands.png"));

        characterImages.put(INSPECT_PANIC_GIRL_TEETH, loadImage("assets/images/inspect/panic_girl_teeth.png"));
        characterImages.put(INSPECT_PANIC_GIRL_HANDS, loadImage("assets/images/inspect/panic_girl_hands.png"));


        characterImages.put(PORTRAIT_NEIGHBOR, loadImage("assets/images/guests/neighbor.png"));
        characterImages.put(PORTRAIT_FIREFIGHTER, loadImage("assets/images/guests/firefighter.png"));
        characterImages.put(PORTRAIT_TEACHER, loadImage("assets/images/guests/teacher.png"));
        characterImages.put(PORTRAIT_COAT_PERSON, loadImage("assets/images/guests/coat_person.png"));
        characterImages.put(PORTRAIT_WIDOW, loadImage("assets/images/guests/widow.png"));
        characterImages.put(PORTRAIT_AUNTIE, loadImage("assets/images/guests/auntie.png"));
        characterImages.put(PORTRAIT_PANIC_GIRL, loadImage("assets/images/guests/panic_daughter.png"));

        morningTransitionImages.put(1, loadImage("assets/images/background/trans1.png"));
        morningTransitionImages.put(2, loadImage("assets/images/background/trans2.png"));
        morningTransitionImages.put(3, loadImage("assets/images/background/trans3.png"));


        initToothSpriteFrames();
        initDialogueOptionFrames();





    }
    private void initOutsideGuestImageKeys() {
        outsideGuestImageKeys.put(GUEST_NEIGHBOR, CHARACTER_NEIGHBOR_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_DAUGHTER, CHARACTER_DAUGHTER_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_FIREFIGHTER, CHARACTER_FIREFIGHTER_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_TEACHER, CHARACTER_TEACHER_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_COAT_PERSON, CHARACTER_COAT_PERSON_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_JUDGE_VISITOR, CHARACTER_JUDGE_VISITOR_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_WIDOW, CHARACTER_WIDOW_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_AUNTIE, CHARACTER_AUNTIE_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_PANIC_GIRL, CHARACTER_PANIC_GIRL_OUTSIDE);
        outsideGuestImageKeys.put(GUEST_COLLECTOR, CHARACTER_COLLECTOR_OUTSIDE);

    }


    private void initAudios() {
        knockSound = loadAudio("assets/audios/knock.wav");
        dayMusic = loadAudio("assets/audios/day.wav");
        night1Music = loadAudio("assets/audios/night1.wav");
        night2Music = loadAudio("assets/audios/night2.wav");
        night3Music = loadAudio("assets/audios/night3.wav");
        night4Music = loadAudio("assets/audios/night4.wav");
        superMusic = loadAudio("assets/audios/super.wav");
        shootSound = loadAudio("assets/audios/shoot.wav");

    }

    private void initGuests() {
        guestManager.initGuests();
    }

    private void initGuestRoomPlacements() {
        registerGuestRoomPlacement(
                GUEST_NEIGHBOR,
                DayRoomType.KITCHEN,
                CHARACTER_NEIGHBOR_DAY,
                480,
                250,
                420,
                680
        );

        registerGuestRoomPlacement(
                GUEST_FIREFIGHTER,
                DayRoomType.STORAGE,
                CHARACTER_FIREFIGHTER_DAY,
                1250,
                500,
                260,
                500
        );

        registerGuestRoomPlacement(
                GUEST_TEACHER,
                DayRoomType.LIVING_ROOM,
                CHARACTER_TEACHER_DAY,
                1400,
                460,
                380,
                650
        );

        registerGuestRoomPlacement(
                GUEST_COAT_PERSON,
                DayRoomType.LIVING_ROOM,
                CHARACTER_COAT_PERSON_DAY,
                400,
                570,
                230,
                500
        );
        registerGuestRoomPlacement(
                GUEST_WIDOW,
                DayRoomType.LIVING_ROOM,
                CHARACTER_WIDOW_DAY,
                1100,
                300,
                250,
                500
        );

        registerGuestRoomPlacement(
                GUEST_AUNTIE,
                DayRoomType.STORAGE,
                CHARACTER_AUNTIE_DAY,
                100,
                260,
                320,
                650
        );

        registerGuestRoomPlacement(
                GUEST_PANIC_GIRL,
                DayRoomType.KITCHEN,
                CHARACTER_PANIC_GIRL_DAY,
                1380,
                510,
                180,
                470
        );

    }


    private void registerGuestRoomPlacement(
            String guestId,
            DayRoomType room,
            String imageKey,
            int x,
            int y,
            int width,
            int height
    ) {
        String key = makeGuestRoomPlacementKey(guestId, room);

        guestRoomPlacements.put(
                key,
                new GuestRoomPlacement(
                        guestId,
                        room,
                        imageKey,
                        x,
                        y,
                        width,
                        height
                )
        );
    }


    private String makeGuestRoomPlacementKey(String guestId, DayRoomType room) {
        return guestId + "@" + room.name();
    }



    private void startFirstNight() {
        currentDay = 1;
        currentPhase = GamePhase.NIGHT;
        currentScene = SceneType.BEDROOM_NIGHT;
        playNightMusicForCurrentDay();

        safeHouseManager.setPlayerRoom(DayRoomType.BEDROOM);
        safeHouseManager.setCanSleep(false);

        addFlag(FLAG_KNOCK_HEARD);

        setOutsideGuest(GUEST_NEIGHBOR);



    }


    private void startNextMorning() {
        currentDay++;

        boolean someoneDied = false;

        if (currentDay == 3 || currentDay == 4) {
            someoneDied = resolveVisitorNightKill();
        }

        currentPhase = GamePhase.DAY;
        currentScene = SceneType.BEDROOM_DAY;

        safeHouseManager.setPlayerRoom(DayRoomType.BEDROOM);
        safeHouseManager.setCanSleep(false);

        clearOutsideGuest();

        setupMorningStamina();

        assignIndoorGuestsForMorning();

        clearMessage();
        closeDialogue();
        closeInspection();

        if (someoneDied) {
            showMessage(MESSAGE_SOMEONE_DIED);
        }
        playBackgroundMusic(dayMusic);
    }




    private void setupMorningStamina() {
        if (currentDay == 2) {
            maxStamina = 2;
            currentStamina = 2;
            return;
        }

        if (currentDay == 3) {
            maxStamina = 3;
            currentStamina = 3;
            return;
        }

        if (currentDay == 4) {
            maxStamina = 4;
            currentStamina = 4;
            return;
        }

        maxStamina = 0;
        currentStamina = 0;


    }


    private void assignIndoorGuestsForMorning() {
        if (hasFlag(FLAG_NEIGHBOR_INSIDE_HOUSE)
                && !hasFlag(FLAG_NEIGHBOR_LEFT_WITH_DAUGHTER)) {
            placeGuestInRoom(GUEST_NEIGHBOR, DayRoomType.KITCHEN);
        }

        keepAllowedGuestsInHouse();
    }

    private void keepAllowedGuestsInHouse() {
        // 目前先什么都不用写。
        // 因为 resultAllowNightVisitor() 里已经把允许进入的人设置成 insideHouse=true 了。
        // 第三天早上他们会根据自己的 currentRoom 自动显示。
    }



    private void placeGuestInRoom(String guestId, DayRoomType room) {
        Guest guest = guestManager.getGuest(guestId);

        if (guest == null) {
            return;
        }

        guest.setInsideHouse(true);
        guest.setCurrentRoom(room);
    }




    private void updateGunAnimation() {
        if (!gunConfirmActive && !gunShootActive) {
            return;
        }

        long now = getTime();

        if (lastGunFrameTime == 0L) {
            lastGunFrameTime = now;
            return;
        }

        if (gunShootActive) {
            if (now - lastGunFrameTime >= GUN_SHOOT_FRAME_INTERVAL) {
                gunShootFrameIndex++;
                lastGunFrameTime = now;

                if (gunShootFrameIndex >= GUN_SHOOT_FRAME_COUNT) {
                    finishGunShootAnimation();
                }
            }

            return;
        }

        if (gunConfirmActive) {
            if (now - lastGunFrameTime >= GUN_IDLE_FRAME_INTERVAL) {
                gunIdleFrameIndex = (gunIdleFrameIndex + 1) % GUN_IDLE_FRAME_COUNT;
                lastGunFrameTime = now;
            }
        }
    }

    private void finishGunShootAnimation() {
        gunShootActive = false;
        gunConfirmActive = false;
        gunShootFrameIndex = 0;
        lastGunFrameTime = 0L;
        forcedPortraitGuestId = null;

        startFlashWhite(0.35, FLASH_KILL_INSPECTED_GUEST);
    }

    private void updateDialogue(double dt) {
        if (!showDialogue || !shouldDrawDialogueOptions()) {
            return;
        }

        long now = getTime();

        if (lastDialogueOptionFrameTime == 0L) {
            lastDialogueOptionFrameTime = now;
            return;
        }

        if (now - lastDialogueOptionFrameTime >= DIALOGUE_OPTION_FRAME_INTERVAL) {
            dialogueOptionFrameIndex =
                    (dialogueOptionFrameIndex + 1) % DIALOGUE_OPTION_FRAME_COUNT;
            lastDialogueOptionFrameTime = now;
        }
    }

    private void updateMessage(double dt) {
    }

    private void updateFlashBlack(double dt) {
        if (!flashBlackActive) {
            return;
        }

        flashBlackTimer += dt;

        if (flashBlackTimer >= flashBlackDuration) {
            finishFlashBlack();
        }
    }

    private void updateFlashWhite(double dt) {
        if (!flashWhiteActive) {
            return;
        }

        flashWhiteTimer += dt;

        if (flashWhiteTimer >= flashWhiteDuration) {
            finishFlashWhite();
        }
    }

    private void finishFlashWhite() {
        String resultAction = pendingFlashWhiteAction;

        flashWhiteActive = false;
        flashWhiteTimer = 0;
        flashWhiteDuration = 0;
        pendingFlashWhiteAction = null;

        handleFlashWhiteResult(resultAction);
    }

    private void handleFlashWhiteResult(String resultAction) {
        if (FLASH_KILL_INSPECTED_GUEST.equals(resultAction)) {
            killInspectedGuest();
            gunTargetGuestId = null;
        }
    }


    private void drawGunLayer() {
        Image frame = null;

        if (gunShootActive) {
            if (gunShootFrames != null
                    && gunShootFrameIndex >= 0
                    && gunShootFrameIndex < gunShootFrames.length) {
                frame = gunShootFrames[gunShootFrameIndex];
            }
        } else if (gunConfirmActive) {
            if (gunIdleFrames != null
                    && gunIdleFrameIndex >= 0
                    && gunIdleFrameIndex < gunIdleFrames.length) {
                frame = gunIdleFrames[gunIdleFrameIndex];
            }
        }

        if (frame == null) {
            return;
        }

        double scale = 1.25;

        int drawW = (int)(GUN_FRAME_W * scale);
        int drawH = (int)(GUN_FRAME_H * scale);

        int x = (width() - drawW) / 2;
        int y = height() - drawH + 20;

        drawImage(frame, x, y, drawW, drawH);
    }


    private void drawInspectionLayer() {
        if (!inspectionActive) {
            return;
        }

        if (currentInspectionImageKey == null) {
            return;
        }

        changeColor(new Color(0, 0, 0, 145));
        drawSolidRectangle(0, 0, width(), height());

        Image inspectImage = characterImages.get(currentInspectionImageKey);

        if (inspectImage != null) {
            drawImage(inspectImage, 110, 180);

            if (currentInspectionFeature == InspectionFeatureType.TEETH) {
                drawToothSpriteOverlay(110-100, 180-40);
            }
        } else {
            changeColor(white);
            drawText(190, 440, "没有找到对应的检查图片。", "Serif", 36);
        }

        Guest guest = guestManager.getGuest(inspectedGuestId);
        String title = "检查结果";

        if (guest != null) {

        }

        changeColor(white);
        drawText(120, 95, title, "Serif", 38);

    }

    private void drawToothSpriteOverlay(int baseX, int baseY) {
        if (toothSpriteFrames == null) {
            return;
        }

        if (toothSpriteFrameIndex < 0 || toothSpriteFrameIndex >= toothSpriteFrames.length) {
            return;
        }

        Image frame = toothSpriteFrames[toothSpriteFrameIndex];

        if (frame == null) {
            return;
        }

        int x = baseX;
        int y = baseY;

        drawImage(frame, x, y);
    }


    private void drawMorningTransitionLayer() {
        clearBackground(width(), height());

        if (currentMorningTransitionImage == null) {
            return;
        }

        drawImage(
                currentMorningTransitionImage,
                0,
                0,
                width(),
                height()
        );
    }


    private Rectangle getInspectTeethButtonRect() {
        return new Rectangle(280, 820, 260, 60);
    }

    private Rectangle getInspectHandsButtonRect() {
        return new Rectangle(570, 820, 260, 60);
    }

    private Rectangle getInspectSpareButtonRect() {
        return new Rectangle(860, 820, 260, 60);
    }

    private Rectangle getInspectKillButtonRect() {
        return new Rectangle(1150, 820, 260, 60);
    }

    private Rectangle getInspectLeaveButtonRect() {
        return new Rectangle(1440, 820, 260, 60);
    }


    private void drawStaminaLayer() {
        if (currentPhase != GamePhase.DAY) {
            return;
        }

        if (maxStamina <= 0) {
            return;
        }

        int startX = 40;
        int startY = 40;
        int size = 42;
        int gap = 14;

        changeColor(new Color(0, 0, 0, 160));
        drawSolidRectangle(25, 25, 180, 90);

        changeColor(white);
        drawText(40, 58, "体力", "Serif", 26);

        for (int i = 0; i < maxStamina; i++) {
            int x = startX + i * (size + gap);
            int y = startY + 25;

            if (i < currentStamina) {
                changeColor(new Color(255, 230, 120));
                drawSolidRectangle(x, y, size, size);
            } else {
                changeColor(new Color(255, 255, 255, 35));
                drawSolidRectangle(x, y, size, size);
            }

            changeColor(new Color(255, 255, 255, 120));
            drawRectangle(x, y, size, size);
        }
    }


    private void drawBackgroundLayer() {
        clearBackground(width(), height());

        Image image = sceneImages.get(currentScene);

        if (image != null) {
            drawImage(image, 0, 0, width(), height());
        }
    }

    private void drawCharacterLayer() {
        drawOutsideGuestViewLayer();
        drawCurrentRoomGuestsLayer();
    }

    private void drawCurrentRoomGuestsLayer() {
        if (currentPhase != GamePhase.DAY) {
            return;
        }

        DayRoomType currentRoom = getCurrentDayRoomType();

        if (currentRoom == null) {
            return;
        }

        List<Guest> guests = guestManager.getAllGuests();

        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);

            if (guest == null) {
                continue;
            }

            if (!guest.isInsideHouse()) {
                continue;
            }

            if (guest.getCurrentRoom() != currentRoom) {
                continue;
            }

            drawGuestInCurrentRoom(guest, currentRoom);
        }
    }


    private void drawGuestInCurrentRoom(Guest guest, DayRoomType room) {
        GuestRoomPlacement placement = getGuestRoomPlacement(guest.getId(), room);

        if (placement == null) {
            return;
        }

        GuestRoomPlacement drawPlacement = placement;
        String imageKey = placement.getImageKey();

        if (guest.isDead()) {
            GuestRoomPlacement corpsePlacement = getGuestCorpseRoomPlacement(guest.getId(), room);

            if (corpsePlacement != null) {
                drawPlacement = corpsePlacement;
            }

            if (guest.getCorpseImageKey() != null) {
                imageKey = guest.getCorpseImageKey();
            } else if (corpsePlacement != null && corpsePlacement.getImageKey() != null) {
                imageKey = corpsePlacement.getImageKey();
            } else {
                imageKey = CHARACTER_TRASH_BAG;
            }
        }

        Image image = characterImages.get(imageKey);

        if (image == null) {
            return;
        }

        drawImage(
                image,
                drawPlacement.getX(),
                drawPlacement.getY(),
                drawPlacement.getWidth(),
                drawPlacement.getHeight()
        );
    }


    private GuestRoomPlacement getGuestRoomPlacement(String guestId, DayRoomType room) {
        String key = makeGuestRoomPlacementKey(guestId, room);
        return guestRoomPlacements.get(key);
    }

    private DayRoomType getCurrentDayRoomType() {
        if (currentScene == SceneType.BEDROOM_DAY) {
            return DayRoomType.BEDROOM;
        }

        if (currentScene == SceneType.HALLWAY_DAY) {
            return DayRoomType.HALLWAY;
        }

        if (currentScene == SceneType.KITCHEN_DAY) {
            return DayRoomType.KITCHEN;
        }

        if (currentScene == SceneType.STORAGE_DAY) {
            return DayRoomType.STORAGE;
        }

        if (currentScene == SceneType.LIVING_ROOM_DAY) {
            return DayRoomType.LIVING_ROOM;
        }
        return null;
    }



    private void drawOutsideGuestViewLayer() {
        if (currentScene != SceneType.YARD_NIGHT) {
            return;
        }

        if (!hasOutsideGuest()) {
            return;
        }

        if (!outsideGuestVisible) {
            return;
        }

        Image image = getOutsideGuestImage(outsideGuestId);

        if (image != null) {
            drawImage(image, 0, 0, width(), height());
        }
    }


    private void collectVisibleGuestClickAreas(ArrayList<ClickableArea> areas, DayRoomType room) {
        List<Guest> guests = guestManager.getAllGuests();

        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);

            if (guest == null) {
                continue;
            }

            if (!guest.isInsideHouse()) {
                continue;
            }

            if (guest.isDead()) {
                continue;
            }


            if (guest.getCurrentRoom() != room) {
                continue;
            }

            GuestRoomPlacement placement = getGuestRoomPlacement(guest.getId(), room);

            if (placement == null) {
                continue;
            }

            areas.add(new ClickableArea(
                    ACTION_TALK_GUEST_PREFIX + guest.getId(),
                    placement.getX(),
                    placement.getY(),
                    placement.getWidth(),
                    placement.getHeight()
            ));
        }
    }





    private Image getOutsideGuestImage(String guestId) {
        String imageKey = outsideGuestImageKeys.get(guestId);

        if (imageKey == null) {
            return null;
        }

        return characterImages.get(imageKey);
    }



    private void drawDialogueLayer() {
        if (!showDialogue || activeDialogueNode == null) {
            return;
        }


        drawDialogueBox();

        if (shouldDrawCurrentDialogueLine()) {
            drawDialogueLine();
        }

        if (shouldDrawDialogueOptions()) {
            drawDialogueOptions();
        }
    }

    private void drawDayDialoguePortrait() {
        if (currentPhase != GamePhase.DAY) {
            return;
        }

        String portraitGuestId = forcedPortraitGuestId;

        if (portraitGuestId == null && showDialogue && activeDialogueIsDay) {
            portraitGuestId = activeGuestId;
        }

        if (portraitGuestId == null) {
            return;
        }

        if (inspectionActive && currentInspectionImageKey != null && !gunConfirmActive && !gunShootActive) {
            return;
        }

        String imageKey = getDayDialoguePortraitKey(portraitGuestId);

        if (imageKey == null) {
            return;
        }

        Image portrait = characterImages.get(imageKey);

        if (portrait == null) {
            return;
        }

        int targetH = 820;
        int originalW = portrait.getWidth(null);
        int originalH = portrait.getHeight(null);

        if (originalW <= 0 || originalH <= 0) {
            return;
        }

        int targetW = originalW * targetH / originalH;

        int x = 120;
        int y = height() - targetH + 20;

        drawImage(portrait, x, y, targetW, targetH);
    }

    private String getDayDialoguePortraitKey(String guestId) {
        if (GUEST_NEIGHBOR.equals(guestId)) {
            return PORTRAIT_NEIGHBOR;
        }

        if (GUEST_FIREFIGHTER.equals(guestId)) {
            return PORTRAIT_FIREFIGHTER;
        }

        if (GUEST_TEACHER.equals(guestId)) {
            return PORTRAIT_TEACHER;
        }

        if (GUEST_COAT_PERSON.equals(guestId)) {
            return PORTRAIT_COAT_PERSON;
        }

        if (GUEST_WIDOW.equals(guestId)) {
            return PORTRAIT_WIDOW;
        }

        if (GUEST_AUNTIE.equals(guestId)) {
            return PORTRAIT_AUNTIE;
        }

        if (GUEST_PANIC_GIRL.equals(guestId)) {
            return PORTRAIT_PANIC_GIRL;
        }

        return null;
    }

    private boolean shouldDrawCurrentDialogueLine() {
        if (activeDialogueNode == null) {
            return false;
        }

        return activeDialogueLineIndex < activeDialogueNode.getLineCount();
    }

    private boolean shouldDrawDialogueOptions() {
        if (activeDialogueNode == null) {
            return false;
        }

        if (!activeDialogueNode.hasOptions()) {
            return false;
        }

        int lastLineIndex = activeDialogueNode.getLineCount() - 1;

        return activeDialogueLineIndex >= lastLineIndex;
    }



    private void drawDialogueBox() {
        changeColor(new Color(0, 0, 0, 205));
        drawSolidRectangle(
                DIALOGUE_BOX_X,
                DIALOGUE_BOX_Y,
                DIALOGUE_BOX_W,
                DIALOGUE_BOX_H
        );
    }


    private void drawDialogueLine() {
        String line = activeDialogueNode.getLine(activeDialogueLineIndex);

        changeColor(white);
        drawText(
                DIALOGUE_TEXT_X,
                DIALOGUE_TEXT_Y,
                line,
                "Serif",
                34
        );
    }


    private void drawDialogueOptions() {
        List<DialogueOption> options = activeDialogueNode.getOptions();

        for (int i = 0; i < options.size(); i++) {
            DialogueOption option = options.get(i);
            Rectangle rect = getDialogueOptionRect(i);

            Image frame = dialogueOptionFrames[dialogueOptionFrameIndex];

            if (frame != null) {
                drawImage(frame, rect.x, rect.y, rect.width, rect.height);
            }

            changeColor(white);
            drawText(
                    rect.x + 24,
                    rect.y + 40,
                    option.getText(),
                    "Serif",
                    26
            );
        }
    }

    private Rectangle getDialogueOptionRect(int optionIndex) {
        int col = optionIndex % OPTION_COLUMNS;
        int row = optionIndex / OPTION_COLUMNS;

        int x = OPTION_START_X + col * (OPTION_BUTTON_W + OPTION_BUTTON_GAP_X);
        int y = OPTION_START_Y + row * (OPTION_BUTTON_H + OPTION_BUTTON_GAP_Y);

        return new Rectangle(
                x,
                y,
                OPTION_BUTTON_W,
                OPTION_BUTTON_H
        );
    }



    private void drawMessageLayer() {
        if (!hasMessage()) {
            return;
        }

        changeColor(new Color(0, 0, 0, 190));
        drawSolidRectangle(620, 430, 720, 150);

        changeColor(white);
        drawText(680, 520, message, "Serif", 34);
    }

    private void drawFlashBlackLayer() {
        if (!flashBlackActive) {
            return;
        }

        int alpha = getFlashBlackAlpha();

        changeColor(new Color(0, 0, 0, alpha));
        drawSolidRectangle(0, 0, width(), height());
    }

    private int getFlashBlackAlpha() {
        if (flashBlackDuration <= 0) {
            return 255;
        }

        double half = flashBlackDuration / 2.0;
        double progress;

        if (flashBlackTimer <= half) {
            progress = flashBlackTimer / half;
        } else {
            progress = 1.0 - ((flashBlackTimer - half) / half);
        }

        if (progress < 0) {
            progress = 0;
        }

        if (progress > 1) {
            progress = 1;
        }

        return (int)(progress * 255);
    }

    private void drawFlashWhiteLayer() {
        if (!flashWhiteActive) {
            return;
        }

        int alpha = getFlashWhiteAlpha();

        changeColor(new Color(255, 255, 255, alpha));
        drawSolidRectangle(0, 0, width(), height());
    }

    private int getFlashWhiteAlpha() {
        if (flashWhiteDuration <= 0) {
            return 255;
        }

        double half = flashWhiteDuration / 2.0;
        double progress;

        if (flashWhiteTimer <= half) {
            progress = flashWhiteTimer / half;
        } else {
            progress = 1.0 - ((flashWhiteTimer - half) / half);
        }

        if (progress < 0) {
            progress = 0;
        }

        if (progress > 1) {
            progress = 1;
        }

        return (int)(progress * 255);
    }


    private void drawDebugClickAreas() {
        if (!debugClickAreas) {
            return;
        }
        if (showDialogue || hasMessage() || flashBlackActive || flashWhiteActive || inspectionActive) {
            return;
        }

        ArrayList<ClickableArea> areas = getActiveClickAreas();
        for (ClickableArea area : areas) {
            int cx = area.getX() + area.getWidth() / 2;
            int cy = area.getY() + area.getHeight() / 2;

            int radius = Math.min(area.getWidth(), area.getHeight()) / 4;
            if (radius < 20) radius = 20;


            changeColor(new Color(200, 200, 200, 80));
            drawSolidCircle(cx, cy, radius - 2);
        }
    }
    private boolean shouldDrawDebugClickArea(ClickableArea area) {
        String actionId = area.getActionId();

        if (actionId == null) {
            return false;
        }

        return isReturnAction(actionId);
    }

    private boolean isReturnAction(String actionId) {
        if (ACTION_GO_HALLWAY_DAY.equals(actionId)) {
            return currentScene == SceneType.KITCHEN_DAY
                    || currentScene == SceneType.STORAGE_DAY
                    || currentScene == SceneType.LIVING_ROOM_DAY;
        }

        if (ACTION_GO_BEDROOM_NIGHT.equals(actionId)) {
            return true;
        }

        return false;
    }

    private void playBackgroundMusic(AudioClip music) {
        if (music == null) {
            return;
        }

        if (currentBackgroundMusic == music) {
            return;
        }

        if (currentBackgroundMusic != null) {
            stopAudioLoop(currentBackgroundMusic);
        }

        currentBackgroundMusic = music;
        startAudioLoop(currentBackgroundMusic, FADE_START_DB);

        musicFadeActive = true;
        musicFadeTimer = 0;
    }

    private void playIntroVideo() {
        introVideoPlaying = true;

        while (mPanel == null) {
            sleep(10);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                introVideoPanel = new JFXPanel();
                introVideoPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

                mPanel.setLayout(null);
                mPanel.add(introVideoPanel);
                mPanel.setComponentZOrder(introVideoPanel, 0);
                mPanel.revalidate();
                mPanel.repaint();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File videoFile = new File("assets/videos/intro.mp4");

                            if (!videoFile.exists()) {
                                System.out.println("Intro video missing: " + videoFile.getAbsolutePath());
                                finishIntroVideo();
                                return;
                            }

                            Media media = new Media(videoFile.toURI().toString());

                            media.setOnError(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Intro media error: " + media.getError());
                                    finishIntroVideo();
                                }
                            });

                            introMediaPlayer = new MediaPlayer(media);

                            introMediaPlayer.setOnError(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Intro player error: " + introMediaPlayer.getError());
                                    finishIntroVideo();
                                }
                            });

                            MediaView mediaView = new MediaView(introMediaPlayer);
                            mediaView.setFitWidth(WINDOW_WIDTH);
                            mediaView.setFitHeight(WINDOW_HEIGHT);
                            mediaView.setPreserveRatio(false);

                            javafx.scene.Group root = new javafx.scene.Group(mediaView);
                            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

                            introVideoPanel.setScene(scene);

                            introMediaPlayer.setOnReady(new Runnable() {
                                @Override
                                public void run() {
                                    introMediaPlayer.play();
                                }
                            });

                            introMediaPlayer.setOnEndOfMedia(new Runnable() {
                                @Override
                                public void run() {
                                    finishIntroVideo();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            finishIntroVideo();
                        }
                    }
                });
            }
        });
    }

    private void finishIntroVideo() {
        if (!introVideoPlaying) {
            return;
        }

        introVideoPlaying = false;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (introMediaPlayer != null) {
                    introMediaPlayer.stop();
                    introMediaPlayer.dispose();
                    introMediaPlayer = null;
                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (introVideoPanel != null) {
                    mPanel.remove(introVideoPanel);
                    introVideoPanel = null;
                    mPanel.revalidate();
                    mPanel.repaint();
                }

                startFirstNight();
            }
        });
    }


    private void updateKnockLoop() {
        if (currentPhase != GamePhase.NIGHT) {
            stopKnockLoop();
            return;
        }

        if (hasOutsideGuest() && !showDialogue) {
            startKnockLoopIfWaitingOutside();
            return;
        }

        stopKnockLoop();
    }

    private void startKnockLoopIfWaitingOutside() {
        if (!hasOutsideGuest()) {
            stopKnockLoop();
            return;
        }

        if (showDialogue) {
            stopKnockLoop();
            return;
        }

        if (knockLoopActive) {
            return;
        }

        startAudioLoop(knockSound, FADE_START_DB);

        knockLoopActive = true;
        knockFadeActive = true;
        knockFadeTimer = 0;
    }

    private void stopKnockLoop() {
        if (!knockLoopActive) {
            return;
        }

        stopAudioLoop(knockSound);

        knockLoopActive = false;
        knockFadeActive = false;
        knockFadeTimer = 0;
    }

    private void updateToothSpriteAnimation() {
        if (!inspectionActive) {
            return;
        }

        if (currentInspectionFeature != InspectionFeatureType.TEETH) {
            return;
        }

        if (currentInspectionImageKey == null) {
            return;
        }

        if (toothSpriteAnimationFinished) {
            return;
        }

        long now = getTime();

        if (lastToothSpriteFrameTime == 0L) {
            lastToothSpriteFrameTime = now;
            return;
        }

        if (now - lastToothSpriteFrameTime >= TOOTH_SPRITE_FRAME_INTERVAL) {
            toothSpriteFrameIndex++;
            lastToothSpriteFrameTime = now;

            if (toothSpriteFrameIndex >= TOOTH_SPRITE_FRAME_COUNT - 1) {
                toothSpriteFrameIndex = TOOTH_SPRITE_FRAME_COUNT - 1;
                toothSpriteAnimationFinished = true;
            }
        }
    }
    private void updateAudioFades(double dt) {
        if (musicFadeActive && currentBackgroundMusic != null) {
            musicFadeTimer += dt;

            float volume = calculateFadeVolume(
                    FADE_START_DB,
                    MUSIC_TARGET_DB,
                    musicFadeTimer,
                    AUDIO_FADE_SECONDS
            );

            setLoopVolume(currentBackgroundMusic, volume);

            if (musicFadeTimer >= AUDIO_FADE_SECONDS) {
                musicFadeActive = false;
            }
        }

        if (knockFadeActive && knockSound != null) {
            knockFadeTimer += dt;

            float volume = calculateFadeVolume(
                    FADE_START_DB,
                    KNOCK_TARGET_DB,
                    knockFadeTimer,
                    AUDIO_FADE_SECONDS
            );

            setLoopVolume(knockSound, volume);

            if (knockFadeTimer >= AUDIO_FADE_SECONDS) {
                knockFadeActive = false;
            }
        }
    }

    private float calculateFadeVolume(float startDb, float targetDb, double timer, double duration) {
        if (duration <= 0) {
            return targetDb;
        }

        double progress = timer / duration;

        if (progress < 0) {
            progress = 0;
        }

        if (progress > 1) {
            progress = 1;
        }

        return (float)(startDb + (targetDb - startDb) * progress);
    }

    private void setLoopVolume(AudioClip audioClip, float volumeDb) {
        if (audioClip == null) {
            return;
        }

        Clip clip = audioClip.getLoopClip();

        if (clip == null) {
            return;
        }

        try {
            FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);

            float min = control.getMinimum();
            float max = control.getMaximum();

            if (volumeDb < min) {
                volumeDb = min;
            }

            if (volumeDb > max) {
                volumeDb = max;
            }

            control.setValue(volumeDb);
        } catch (Exception e) {
            // 有些音频设备可能不支持音量控制，直接忽略。
        }
    }


    private void playNightMusicForCurrentDay() {
        if (currentDay == 1) {
            playBackgroundMusic(night1Music);
            return;
        }

        if (currentDay == 2) {
            playBackgroundMusic(night2Music);
            return;
        }

        if (currentDay == 3) {
            playBackgroundMusic(night3Music);
            return;
        }

        if (currentDay == 4) {
            playBackgroundMusic(night4Music);
        }
    }



    private void handleMouseClick(int mx, int my) {
        if (introVideoPlaying) {
            return;
        }
        if (morningTransitionActive) {
            finishMorningTransition();
            return;
        }

        if (hasFlag(FLAG_GAME_OVER)) {
            return;
        }


        if (flashBlackActive || flashWhiteActive || gunShootActive) {
            return;
        }

        if (showDialogue) {
            handleDialogueClick(mx, my);
            return;
        }

        if (inspectionActive) {
            handleInspectionClick(mx, my);
            return;
        }

        if (hasMessage()) {
            clearMessage();
            return;
        }

        handleSceneClick(mx, my);
    }
    private void finishMorningTransition() {
        morningTransitionActive = false;
        currentMorningTransitionImage = null;

        startNextMorning();
    }

    private void handleInspectionClick(int mx, int my) {
        if (isInsideRect(mx, my,
                getInspectTeethButtonRect().x,
                getInspectTeethButtonRect().y,
                getInspectTeethButtonRect().width,
                getInspectTeethButtonRect().height)) {
            chooseInspectionFeature(InspectionFeatureType.TEETH);
            return;
        }

        if (isInsideRect(mx, my,
                getInspectHandsButtonRect().x,
                getInspectHandsButtonRect().y,
                getInspectHandsButtonRect().width,
                getInspectHandsButtonRect().height)) {
            chooseInspectionFeature(InspectionFeatureType.HANDS);
            return;
        }

        if (isInsideRect(mx, my,
                getInspectKillButtonRect().x,
                getInspectKillButtonRect().y,
                getInspectKillButtonRect().width,
                getInspectKillButtonRect().height)) {
            killInspectedGuestWithFlash();
            return;
        }

        if (isInsideRect(mx, my,
                getInspectLeaveButtonRect().x,
                getInspectLeaveButtonRect().y,
                getInspectLeaveButtonRect().width,
                getInspectLeaveButtonRect().height)) {
            closeInspection();
        }
    }

    private void inspectCurrentGuestFeature(InspectionFeatureType featureType) {
        chooseInspectionFeature(featureType);
    }

    private void chooseInspectionFeature(InspectionFeatureType featureType) {
        if (!inspectionActive) {
            return;
        }

        if (currentStamina <= 0) {
            setInspectionInfoDialogue("你已经没有体力继续检查了。");
            return;
        }

        Guest guest = guestManager.getGuest(inspectedGuestId);

        if (guest == null) {
            closeInspection();
            closeDialogue();
            return;
        }

        if (guest.isDead()) {
            setInspectionInfoDialogue("他已经死了。");
            closeInspection();
            return;
        }

        GuestFeatures features = guest.getFeatures();

        if (features == null) {
            setInspectionInfoDialogue("没有可检查的特征。");
            return;
        }

        currentStamina--;

        currentInspectionFeature = featureType;

        if (featureType == InspectionFeatureType.TEETH) {
            toothSpriteFrameIndex = 0;
            lastToothSpriteFrameTime = 0L;
            toothSpriteAnimationFinished = false;
        }

        if (featureType == InspectionFeatureType.TEETH) {
            currentInspectionImageKey = features.getTeethImageKey();
        } else if (featureType == InspectionFeatureType.HANDS) {
            currentInspectionImageKey = features.getHandsImageKey();
        } else if (featureType == InspectionFeatureType.EYES) {
            currentInspectionImageKey = features.getEyesImageKey();
        } else if (featureType == InspectionFeatureType.EARS) {
            currentInspectionImageKey = features.getEarsImageKey();
        }

        activeDialogueNode = createInspectionDecisionNode(featureType);
        activeDialogueLineIndex = 0;
        pendingDialogueResult = null;
        showDialogue = true;

        toothSpriteFrameIndex = 0;
        lastToothSpriteFrameTime = 0L;
        toothSpriteAnimationFinished = false;
    }

    private void closeInspection() {
        inspectionActive = false;
        inspectedGuestId = null;
        currentInspectionFeature = null;
        currentInspectionImageKey = null;
    }

    private void killInspectedGuestWithFlash() {
        if (!inspectionActive) {
            return;
        }

        Guest guest = guestManager.getGuest(inspectedGuestId);

        if (guest == null) {
            closeInspection();
            return;
        }

        if (guest.isDead()) {
            closeInspection();
            return;
        }

        startFlashWhite(0.35, FLASH_KILL_INSPECTED_GUEST);
    }

    private void killInspectedGuest() {
        Guest guest = guestManager.getGuest(inspectedGuestId);

        if (guest == null) {
            closeInspection();
            return;
        }

        guest.setDead(true);

        if (guest.isHuman()) {
            guest.setCorpseImageKey(CHARACTER_TRASH_BAG);
        } else if (guest.isVisitor()) {
            guest.setCorpseImageKey(getVisitorCorpseImageKey(guest.getId()));
        } else {
            guest.setCorpseImageKey(CHARACTER_TRASH_BAG);
        }

        closeInspection();
    }

    private String getVisitorCorpseImageKey(String guestId) {
        if (GUEST_COAT_PERSON.equals(guestId)) {
            return CHARACTER_COAT_PERSON_CORPSE;
        }

        if (GUEST_AUNTIE.equals(guestId)) {
            return CHARACTER_AUNTIE_CORPSE;
        }

        return CHARACTER_TRASH_BAG;
    }


    private void startFlashWhite(double duration, String resultAction) {
        flashWhiteActive = true;
        flashWhiteTimer = 0;
        flashWhiteDuration = duration;
        pendingFlashWhiteAction = resultAction;
    }


    private void handleSceneClick(int mx, int my) {
        ArrayList<ClickableArea> areas = getActiveClickAreas();

        for (ClickableArea area : areas) {
            if (area.contains(mx, my)) {
                handleAction(area.getActionId());
                return;
            }
        }
    }

    private void handleAction(String actionId) {

        if (actionId.startsWith(ACTION_TALK_GUEST_PREFIX)) {
            String guestId = actionId.substring(ACTION_TALK_GUEST_PREFIX.length());
            actionTalkGuestDay(guestId);
            return;
        }


        if (ACTION_GO_YARD.equals(actionId)) {
            actionGoYard();
            return;
        }

        if (ACTION_OPEN_GATE.equals(actionId)) {
            actionOpenGate();
            return;
        }

        if (ACTION_CHECK_EMPTY_GATE.equals(actionId)) {
            actionCheckEmptyGate();
            return;
        }

        if (ACTION_GO_BEDROOM_NIGHT.equals(actionId)) {
            actionGoBedroomNight();
            return;
        }

        if (ACTION_SLEEP.equals(actionId)) {
            actionSleep();
            return;
        }

        if (ACTION_GO_HALLWAY_DAY.equals(actionId)) {
            actionGoHallwayDay();
            return;
        }

        if (ACTION_GO_BEDROOM_DAY.equals(actionId)) {
            actionGoBedroomDay();
            return;
        }

        if (ACTION_GO_LIVING_ROOM_DAY.equals(actionId)) {
            actionGoLivingRoomDay();
            return;
        }


        if (ACTION_GO_KITCHEN_DAY.equals(actionId)) {
            actionGoKitchenDay();
            return;
        }

        if (ACTION_GO_STORAGE_DAY.equals(actionId)) {
            actionGoStorageDay();
            return;
        }

        if (ACTION_DAY_SLEEP.equals(actionId)) {
            actionDaySleep();
            return;
        }



    }

    private void actionDaySleep() {
        if (currentPhase != GamePhase.DAY) {
            return;
        }

        if (currentScene != SceneType.BEDROOM_DAY) {
            return;
        }

        if (!isStaminaEmpty()) {
            showMessage("你还不困。但你决定提前睡下。");
            //return;
        }

        if (currentDay == 2) {
            startFlashBlack(2.5, FLASH_START_SECOND_NIGHT);
            return;
        }

        if (currentDay == 3) {
            startFlashBlack(2.5, FLASH_START_THIRD_NIGHT);
            return;
        }
        if (currentDay == 4) {
            startFlashBlack(2.5, FLASH_START_FOURTH_NIGHT);
            return;
        }

        //showMessage("现在还不能睡。");
    }




    private void actionTalkGuestDay(String guestId) {
        Guest guest = guestManager.getGuest(guestId);

        if (guest == null) {
            return;
        }

        guest.setTalkedToday(true);

        startGuestDialogue(
                guestId,
                true,
                getDayDialogueResultForGuest(guestId)
        );
    }

    private String getDayDialogueResultForGuest(String guestId) {
        if (GUEST_NEIGHBOR.equals(guestId)) {
            return RESULT_FINISH_DAY_TALK;
        }

        return null;
    }


    private void actionGoLivingRoomDay() {
        currentScene = SceneType.LIVING_ROOM_DAY;
        safeHouseManager.setPlayerRoom(DayRoomType.LIVING_ROOM);
    }

    private void actionGoHallwayDay() {
        currentScene = SceneType.HALLWAY_DAY;
        safeHouseManager.setPlayerRoom(DayRoomType.HALLWAY);
    }

    private void actionGoBedroomDay() {
        currentScene = SceneType.BEDROOM_DAY;
        safeHouseManager.setPlayerRoom(DayRoomType.BEDROOM);
    }

    private void actionGoKitchenDay() {
        currentScene = SceneType.KITCHEN_DAY;
        safeHouseManager.setPlayerRoom(DayRoomType.KITCHEN);
    }

    private void actionGoStorageDay() {
        currentScene = SceneType.STORAGE_DAY;
        safeHouseManager.setPlayerRoom(DayRoomType.STORAGE);
    }


    private void actionGoYard() {
        currentScene = SceneType.YARD_NIGHT;
        safeHouseManager.setPlayerRoom(DayRoomType.YARD);
    }


    private void actionOpenGate() {
        if (!hasOutsideGuest()) {
            actionCheckEmptyGate();
            return;
        }

        stopKnockLoop();

        startFlashBlack(1, FLASH_REVEAL_OUTSIDE_GUEST);
    }


    private void actionCheckEmptyGate() {
        addFlag(FLAG_NO_MORE_VISITORS_CONFIRMED);
    }

    private void actionGoBedroomNight() {
        currentScene = SceneType.BEDROOM_NIGHT;
        safeHouseManager.setPlayerRoom(DayRoomType.BEDROOM);
    }

    private void actionSleep() {
        if (!canSleepNow()) {
            return;
        }

        startFlashBlack(2.5, FLASH_SLEEP_TO_NEXT_MORNING);
    }

    private void actionGoKitchen() {
        currentScene = SceneType.KITCHEN_DAY;
        safeHouseManager.setPlayerRoom(DayRoomType.KITCHEN);
    }



    private void startFlashBlack(double duration, String resultAction) {
        flashBlackActive = true;
        flashBlackTimer = 0;
        flashBlackDuration = duration;
        pendingFlashAction = resultAction;
    }

    private void finishFlashBlack() {
        String resultAction = pendingFlashAction;

        flashBlackActive = false;
        flashBlackTimer = 0;
        flashBlackDuration = 0;
        pendingFlashAction = null;

        handleFlashBlackResult(resultAction);
    }

    private void handleFlashBlackResult(String resultAction) {
        if (FLASH_REVEAL_OUTSIDE_GUEST.equals(resultAction)) {
            revealOutsideGuestAndStartDialogue();
            return;
        }

        if (FLASH_SLEEP_TO_NEXT_MORNING.equals(resultAction)) {
            startMorningTransition();
            return;
        }

        if (FLASH_START_SECOND_NIGHT.equals(resultAction)) {
            startSecondNight();
            return;
        }

        if (FLASH_START_THIRD_NIGHT.equals(resultAction)) {
            startThirdNight();
            return;
        }

        if (FLASH_GAME_OVER_BY_JUDGE.equals(resultAction)) {
            triggerGameOver();
        }

        if (FLASH_START_FOURTH_NIGHT.equals(resultAction)) {
            startFourthNight();
            return;
        }

    }
    private void startMorningTransition() {
        currentMorningTransitionImage = morningTransitionImages.get(currentDay);

        if (currentMorningTransitionImage == null) {
            startNextMorning();
            return;
        }

        morningTransitionActive = true;

        closeDialogue();
        closeInspection();
        clearMessage();
    }
    private void triggerGameOver() {
        addFlag(FLAG_GAME_OVER);
        showMessage(MESSAGE_GAME_OVER);
    }

    private void triggerFinalEnding() {
        clearOutsideGuest();
        closeDialogue();
        closeInspection();

        addFlag(FLAG_GAME_OVER);

        if (hasLivingVisitorInsideHouse()) {
            showMessage(MESSAGE_ENDING_LOSE);
        } else {
            showMessage(MESSAGE_ENDING_WIN);
        }
    }

    private boolean hasLivingVisitorInsideHouse() {
        for (Guest guest : guestManager.getAllGuests()) {
            if (guest == null) {
                continue;
            }

            if (!guest.isInsideHouse()) {
                continue;
            }

            if (guest.isDead()) {
                continue;
            }

            if (guest.isVisitor()) {
                return true;
            }
        }

        return false;
    }



    private void startSecondNight() {
        currentDay = 2;
        currentPhase = GamePhase.NIGHT;
        currentScene = SceneType.BEDROOM_NIGHT;
        playNightMusicForCurrentDay();
        safeHouseManager.setPlayerRoom(DayRoomType.BEDROOM);
        safeHouseManager.setCanSleep(false);

        maxStamina = 0;
        currentStamina = 0;

        clearMessage();
        closeDialogue();

        addFlag(FLAG_SECOND_NIGHT_STARTED);

        setupSecondNightVisitors();
    }

    private void setupSecondNightVisitors() {
        nightVisitorQueue.clear();

        nightVisitorQueue.add(GUEST_DAUGHTER);
        nightVisitorQueue.add(GUEST_FIREFIGHTER);
        nightVisitorQueue.add(GUEST_TEACHER);
        nightVisitorQueue.add(GUEST_COAT_PERSON);

        advanceToNextNightVisitor();
    }

    private void setupThirdNightVisitors() {
        nightVisitorQueue.clear();

        nightVisitorQueue.add(GUEST_JUDGE_VISITOR);
        nightVisitorQueue.add(GUEST_WIDOW);
        nightVisitorQueue.add(GUEST_AUNTIE);
        nightVisitorQueue.add(GUEST_PANIC_GIRL);

        advanceToNextNightVisitor();
    }


    private void advanceToNextThirdNightVisitor() {
        clearOutsideGuest();

        if (nightVisitorQueue.isEmpty()) {
            addFlag(FLAG_THIRD_NIGHT_VISITORS_DONE);
            return;
        }

        String nextGuestId = nightVisitorQueue.remove(0);

        setOutsideGuest(nextGuestId);
        addFlag(FLAG_KNOCK_HEARD);

        updateKnockLoop();
        startKnockLoopIfWaitingOutside();
        stopKnockLoop();
    }

    private void startFourthNight() {
        currentDay = 4;
        currentPhase = GamePhase.NIGHT;
        currentScene = SceneType.BEDROOM_NIGHT;

        playNightMusicForCurrentDay();

        safeHouseManager.setPlayerRoom(DayRoomType.BEDROOM);
        safeHouseManager.setCanSleep(false);

        maxStamina = 0;
        currentStamina = 0;

        clearMessage();
        closeDialogue();
        closeInspection();

        addFlag(FLAG_FOURTH_NIGHT_STARTED);

        setupFourthNightVisitors();
    }

    private void setupFourthNightVisitors() {
        nightVisitorQueue.clear();

        nightVisitorQueue.add(GUEST_COLLECTOR);
        nightVisitorQueue.add(GUEST_JUDGE_VISITOR);

        advanceToNextNightVisitor();
    }

    private void startThirdNight() {
        currentDay = 3;
        currentPhase = GamePhase.NIGHT;
        currentScene = SceneType.BEDROOM_NIGHT;

        playNightMusicForCurrentDay();
        safeHouseManager.setPlayerRoom(DayRoomType.BEDROOM);
        safeHouseManager.setCanSleep(false);

        maxStamina = 0;
        currentStamina = 0;

        clearMessage();
        closeDialogue();
        closeInspection();

        addFlag(FLAG_THIRD_NIGHT_STARTED);

        setupThirdNightVisitors();
    }



    private void advanceToNextNightVisitor() {
        clearOutsideGuest();

        if (nightVisitorQueue.isEmpty()) {
            if (currentDay == 2 && currentPhase == GamePhase.NIGHT) {
                addFlag(FLAG_SECOND_NIGHT_VISITORS_DONE);
            }

            if (currentDay == 3 && currentPhase == GamePhase.NIGHT) {
                addFlag(FLAG_THIRD_NIGHT_VISITORS_DONE);
            }

            if (currentDay == 4 && currentPhase == GamePhase.NIGHT) {
                addFlag(FLAG_FOURTH_NIGHT_VISITORS_DONE);
                triggerFinalEnding();
            }

            return;
        }

        String nextGuestId = nightVisitorQueue.remove(0);

        setOutsideGuest(nextGuestId);
        addFlag(FLAG_KNOCK_HEARD);

    }




    private void revealOutsideGuestAndStartDialogue() {
        if (!hasOutsideGuest()) {
            return;
        }

        stopKnockLoop();

        outsideGuestVisible = true;

        outsideGuestVisible = true;

        if (GUEST_NEIGHBOR.equals(outsideGuestId)) {
            startGuestDialogue(
                    GUEST_NEIGHBOR,
                    false,
                    RESULT_NEIGHBOR_ENTER_HOUSE
            );
            return;
        }

        if (GUEST_DAUGHTER.equals(outsideGuestId)) {
            startGuestDialogue(
                    GUEST_DAUGHTER,
                    false,
                    RESULT_DAUGHTER_TAKE_NEIGHBOR
            );
            return;
        }

        if (GUEST_COLLECTOR.equals(outsideGuestId)) {
            startGuestDialogue(
                    GUEST_COLLECTOR,
                    false,
                    RESULT_COLLECTOR_TAKE_RANDOM
            );
            return;
        }

        if (GUEST_JUDGE_VISITOR.equals(outsideGuestId)) {
            playBackgroundMusic(superMusic);

            if (currentDay == 4) {
                startGuestDialogueFromNode(
                        GUEST_JUDGE_VISITOR,
                        "fourth_start",
                        false,
                        RESULT_JUDGE_VISITOR_DECISION
                );
            } else {
                startGuestDialogue(
                        GUEST_JUDGE_VISITOR,
                        false,
                        RESULT_JUDGE_VISITOR_DECISION
                );
            }

            return;
        }

        startGuestDialogue(
                outsideGuestId,
                false,
                null
        );
    }




    private void resultDaughterTakeNeighbor() {
        addFlag(FLAG_DAUGHTER_VISITED);
        addFlag(FLAG_NEIGHBOR_LEFT_WITH_DAUGHTER);

        Guest neighbor = guestManager.getGuest(GUEST_NEIGHBOR);

        if (neighbor != null) {
            neighbor.setInsideHouse(false);
            neighbor.setCurrentRoom(null);
        }

        Guest daughter = guestManager.getGuest(GUEST_DAUGHTER);

        if (daughter != null) {
            daughter.setInsideHouse(false);
            daughter.setCurrentRoom(null);
        }

        clearOutsideGuest();

        currentScene = SceneType.YARD_NIGHT;

        advanceToNextNightVisitor();
    }

    private void resultAllowNightVisitor(String guestId) {
        markNightVisitorVisited(guestId);

        Guest guest = guestManager.getGuest(guestId);

        if (guest != null) {
            guest.setInsideHouse(true);
            guest.setCurrentRoom(getDefaultRoomForNightVisitor(guestId));
        }

        clearOutsideGuest();

        currentScene = SceneType.YARD_NIGHT;

        advanceToNextNightVisitor();
    }

    private void resultRejectNightVisitor(String guestId) {
        markNightVisitorVisited(guestId);

        Guest guest = guestManager.getGuest(guestId);

        if (guest != null) {
            guest.setInsideHouse(false);
            guest.setCurrentRoom(null);
        }

        clearOutsideGuest();

        currentScene = SceneType.YARD_NIGHT;

        advanceToNextNightVisitor();
    }

    private void markNightVisitorVisited(String guestId) {
        if (GUEST_FIREFIGHTER.equals(guestId)) {
            addFlag(FLAG_FIREFIGHTER_VISITED);
            return;
        }

        if (GUEST_TEACHER.equals(guestId)) {
            addFlag(FLAG_TEACHER_VISITED);
            return;
        }

        if (GUEST_COAT_PERSON.equals(guestId)) {
            addFlag(FLAG_COAT_PERSON_VISITED);
            return;
        }

        if (GUEST_WIDOW.equals(guestId)) {
            addFlag(FLAG_WIDOW_VISITED);
            return;
        }

        if (GUEST_AUNTIE.equals(guestId)) {
            addFlag(FLAG_AUNTIE_VISITED);
            return;
        }

        if (GUEST_PANIC_GIRL.equals(guestId)) {
            addFlag(FLAG_PANIC_GIRL_VISITED);
        }
    }


    private DayRoomType getDefaultRoomForNightVisitor(String guestId) {
        if (GUEST_FIREFIGHTER.equals(guestId)) {
            return DayRoomType.STORAGE;
        }

        if (GUEST_TEACHER.equals(guestId)) {
            return DayRoomType.LIVING_ROOM;
        }

        if (GUEST_COAT_PERSON.equals(guestId)) {
            return DayRoomType.LIVING_ROOM;
        }

        if (GUEST_WIDOW.equals(guestId)) {
            return DayRoomType.LIVING_ROOM;
        }

        if (GUEST_AUNTIE.equals(guestId)) {
            return DayRoomType.STORAGE;
        }

        if (GUEST_PANIC_GIRL.equals(guestId)) {
            return DayRoomType.KITCHEN;
        }

        return DayRoomType.LIVING_ROOM;
    }




    private void startGuestDialogue(String guestId, boolean dayDialogue, String resultId) {
        Guest guest = guestManager.getGuest(guestId);

        if (guest == null) {
            return;
        }

        DialogueNode startNode;

        if (dayDialogue) {
            startNode = guest.getDayStartDialogueNode();
        } else {
            startNode = guest.getStartDialogueNode();
        }

        if (startNode == null) {
            return;
        }

        activeGuestId = guestId;
        activeDialogueIsDay = dayDialogue;
        activeDialogueNode = startNode;
        activeDialogueLineIndex = 0;
        pendingDialogueResult = resultId;
        showDialogue = true;
    }

    private void startGuestDialogueFromNode(
            String guestId,
            String startNodeId,
            boolean dayDialogue,
            String resultId
    ) {
        Guest guest = guestManager.getGuest(guestId);

        if (guest == null) {
            return;
        }

        DialogueNode startNode;

        if (dayDialogue) {
            startNode = guest.getDayDialogueNode(startNodeId);
        } else {
            startNode = guest.getDialogueNode(startNodeId);
        }

        if (startNode == null) {
            return;
        }

        activeGuestId = guestId;
        activeDialogueIsDay = dayDialogue;
        activeDialogueNode = startNode;
        activeDialogueLineIndex = 0;
        pendingDialogueResult = resultId;
        showDialogue = true;
    }

    private void handleDialogueClick(int mx, int my) {
        if (activeDialogueNode == null) {
            closeDialogue();
            return;
        }

        if (shouldDrawDialogueOptions()) {
            if (handleDialogueOptionClick(mx, my)) {
                return;
            }
        }

        if (shouldDrawCurrentDialogueLine()) {
            activeDialogueLineIndex++;
            return;
        }

        if (activeDialogueNode.hasOptions()) {
            return;
        }

        finishDialogue();
    }

    private boolean resolveVisitorNightKill() {
        ArrayList<Guest> visitorsInside = new ArrayList<Guest>();
        ArrayList<Guest> humansInside = new ArrayList<Guest>();

        for (Guest guest : guestManager.getAllGuests()) {
            if (guest == null) {
                continue;
            }

            if (!guest.isInsideHouse()) {
                continue;
            }

            if (guest.isDead()) {
                continue;
            }

            if (guest.isVisitor()) {
                visitorsInside.add(guest);
            } else if (guest.isHuman()) {
                humansInside.add(guest);
            }
        }

        if (visitorsInside.isEmpty()) {
            return false;
        }

        if (humansInside.isEmpty()) {
            return false;
        }

        Guest victim = humansInside.get(random.nextInt(humansInside.size()));

        victim.setDead(true);
        victim.setKilledByVisitor(true);
        victim.setCorpseImageKey(CHARACTER_TRASH_BAG);

        return true;
    }





    private boolean isDialogueShowingLine() {
        if (activeDialogueNode == null) {
            return false;
        }

        return activeDialogueLineIndex < activeDialogueNode.getLineCount();
    }

    private boolean handleDialogueOptionClick(int mx, int my) {
        List<DialogueOption> options = activeDialogueNode.getOptions();

        for (int i = 0; i < options.size(); i++) {
            Rectangle rect = getDialogueOptionRect(i);

            if (isInsideRect(mx, my, rect.x, rect.y, rect.width, rect.height)) {
                chooseDialogueOption(options.get(i));
                return true;
            }
        }

        return false;
    }




    private void chooseDialogueOption(DialogueOption option) {
        if (option == null) {
            return;
        }

        String nextNodeId = option.getNextNodeId();

        if (SPECIAL_INSPECT_FEATURES.equals(nextNodeId)) {
            String guestId = activeGuestId;
            startInspection(guestId);
            return;
        }
        if (SPECIAL_PULL_TRIGGER.equals(nextNodeId)) {
            closeDialogue();
            playAudio(shootSound,6.0f);
            startGunShootAnimation();
            return;
        }

        if (SPECIAL_LOWER_GUN.equals(nextNodeId)) {
            lowerGunAndReturnToGuestDialogue();
            return;
        }

        if (SPECIAL_INSPECT_HANDS.equals(nextNodeId)) {
            chooseInspectionFeature(InspectionFeatureType.HANDS);
            return;
        }

        if (SPECIAL_INSPECT_TEETH.equals(nextNodeId)) {
            chooseInspectionFeature(InspectionFeatureType.TEETH);
            return;
        }

        if (SPECIAL_INSPECT_KILL.equals(nextNodeId)) {
            forcedPortraitGuestId = inspectedGuestId;
            currentInspectionImageKey = null;
            startGunConfirmDialogue();
            return;
        }

        if (SPECIAL_INSPECT_SPARE.equals(nextNodeId)) {
            closeDialogue();
            closeInspection();
            return;
        }


        if (SPECIAL_ALLOW_CURRENT_GUEST.equals(nextNodeId)) {
            pendingDialogueResult = RESULT_NIGHT_VISITOR_ALLOW_PREFIX + activeGuestId;
            finishDialogue();
            return;
        }
        if (SPECIAL_JUDGE_ANSWER_YES.equals(nextNodeId)) {
            pendingDialogueResult = RESULT_JUDGE_VISITOR_DECISION + ":yes";
            finishDialogue();
            return;
        }

        if (SPECIAL_JUDGE_ANSWER_NO.equals(nextNodeId)) {
            pendingDialogueResult = RESULT_JUDGE_VISITOR_DECISION + ":no";
            finishDialogue();
            return;
        }

        if (SPECIAL_JUDGE_ANSWER_YES.equals(nextNodeId)) {
            pendingDialogueResult = RESULT_JUDGE_VISITOR_DECISION + ":yes";
            finishDialogue();
            return;
        }

        if (SPECIAL_JUDGE_ANSWER_NO.equals(nextNodeId)) {
            pendingDialogueResult = RESULT_JUDGE_VISITOR_DECISION + ":no";
            finishDialogue();
            return;
        }


        if (SPECIAL_REJECT_CURRENT_GUEST.equals(nextNodeId)) {
            pendingDialogueResult = RESULT_NIGHT_VISITOR_REJECT_PREFIX + activeGuestId;
            finishDialogue();
            return;
        }

        if (option.isEndDialogue()) {
            finishDialogue();
            return;
        }

        if (nextNodeId == null) {
            finishDialogue();
            return;
        }

        goToDialogueNode(nextNodeId);
    }

    private void startGunConfirmDialogue() {
        gunTargetGuestId = inspectedGuestId;
        gunConfirmActive = true;
        gunShootActive = false;
        gunIdleFrameIndex = 0;
        gunShootFrameIndex = 0;
        lastGunFrameTime = 0L;

        activeDialogueNode = createGunConfirmNode();
        activeDialogueLineIndex = 0;
        pendingDialogueResult = null;
        showDialogue = true;

    }

    private DialogueNode createGunConfirmNode() {
        DialogueNode node = new DialogueNode(
                "__gun_confirm__",
                getGunConfirmLine(inspectedGuestId)
        );

        node.addOption(new DialogueOption("扣动扳机", SPECIAL_PULL_TRIGGER, false));
        node.addOption(new DialogueOption("放下枪", SPECIAL_LOWER_GUN, false));

        return node;
    }

    private void startGunShootAnimation() {
        gunConfirmActive = false;
        gunShootActive = true;
        gunShootFrameIndex = 0;
        lastGunFrameTime = 0L;
    }

    private void lowerGunAndReturnToGuestDialogue() {
        String guestId = inspectedGuestId;

        gunConfirmActive = false;
        gunShootActive = false;
        gunIdleFrameIndex = 0;
        gunShootFrameIndex = 0;
        lastGunFrameTime = 0L;

        closeDialogue();
        closeInspection();

        if (guestId != null) {
            startGuestDialogue(
                    guestId,
                    true,
                    getDayDialogueResultForGuest(guestId)
            );
        }
        forcedPortraitGuestId = null;
    }

    private String getGunConfirmLine(String guestId) {
        if (GUEST_NEIGHBOR.equals(guestId)) {
            return "早死晚死都是死...让我体面点。";
        }

        if (GUEST_FIREFIGHTER.equals(guestId)) {
            return "冷静点。你现在需要的是判断，不是恐惧。";
        }

        if (GUEST_TEACHER.equals(guestId)) {
            return "孩子们...请原谅我...";
        }

        if (GUEST_COAT_PERSON.equals(guestId)) {
            return "我想我明白为什么我这么冷了。";
        }

        if (GUEST_WIDOW.equals(guestId)) {
            return "动手。快点。";
        }

        if (GUEST_AUNTIE.equals(guestId)) {
            return "孩子！你别让人骗了！";
        }

        if (GUEST_PANIC_GIRL.equals(guestId)) {
            return "爸爸...妈妈...。";
        }

        return "你举起了枪。";
    }


    private void startInspection(String guestId) {
        if (currentPhase != GamePhase.DAY) {
            setInspectionInfoDialogue("现在不能检查。");
            return;
        }

        if (currentDay != 3 && currentDay != 4) {
            setInspectionInfoDialogue("现在还不能检查。");
            return;
        }

        Guest guest = guestManager.getGuest(guestId);

        if (guest == null) {
            setInspectionInfoDialogue("没有可检查的对象。");
            return;
        }

        if (guest.isDead()) {
            setInspectionInfoDialogue("他已经死了。");
            return;
        }

        inspectionActive = true;
        inspectedGuestId = guestId;
        currentInspectionFeature = null;
        currentInspectionImageKey = null;

        activeGuestId = guestId;
        activeDialogueIsDay = true;
        activeDialogueNode = createInspectionStartNode(guestId);
        activeDialogueLineIndex = 0;
        pendingDialogueResult = null;
        showDialogue = true;
    }

    private DialogueNode createInspectionStartNode(String guestId) {
        DialogueNode node = new DialogueNode(
                "__inspection_start__",
                getInspectionStartLine(guestId)
        );

        if (currentDay == 3) {
            node.addOption(new DialogueOption("检查牙齿", SPECIAL_INSPECT_TEETH, false));
        }

        if (currentDay == 4) {
            node.addOption(new DialogueOption("检查牙齿", SPECIAL_INSPECT_TEETH, false));
            node.addOption(new DialogueOption("检查双手", SPECIAL_INSPECT_HANDS, false));
        }

        node.addOption(new DialogueOption("先不检查", SPECIAL_INSPECT_SPARE, false));

        return node;
    }

    private DialogueNode createInspectionDecisionNode(InspectionFeatureType featureType) {
        DialogueNode node = new DialogueNode(
                "__inspection_decision__",
                getInspectionDecisionLine(featureType)
        );

        node.addOption(new DialogueOption("杀死他", SPECIAL_INSPECT_KILL, false));
        node.addOption(new DialogueOption("不杀死他", SPECIAL_INSPECT_SPARE, false));

        return node;
    }

    private void setInspectionInfoDialogue(String text) {
        DialogueNode node = new DialogueNode("__inspection_info__", text);
        node.addOption(new DialogueOption("先这样。", SPECIAL_INSPECT_SPARE, false));

        activeDialogueNode = node;
        activeDialogueLineIndex = 0;
        pendingDialogueResult = null;
        showDialogue = true;
    }

    private String getInspectionStartLine(String guestId) {
        if (GUEST_NEIGHBOR.equals(guestId)) {
            return "好吧。你看吧。";
        }

        if (GUEST_FIREFIGHTER.equals(guestId)) {
            return "好。*咳嗽*";
        }

        if (GUEST_TEACHER.equals(guestId)) {
            return "来吧。我没什么可隐瞒的。";
        }

        if (GUEST_COAT_PERSON.equals(guestId)) {
            return "你想检查什么？";
        }

        if (GUEST_WIDOW.equals(guestId)) {
            return "快点。";
        }

        if (GUEST_AUNTIE.equals(guestId)) {
            return "你要查啥？";
        }

        if (GUEST_PANIC_GIRL.equals(guestId)) {
            return "你想检查什么？";
        }

        return "可以。你检查吧。";
    }

    private String getInspectionDecisionLine(InspectionFeatureType featureType) {
        if (featureType == InspectionFeatureType.HANDS) {
            return "";
        }

        if (featureType == InspectionFeatureType.TEETH) {
            return "";
        }

        return "";
    }


    private void goToDialogueNode(String nodeId) {
        Guest guest = guestManager.getGuest(activeGuestId);

        if (guest == null) {
            closeDialogue();
            return;
        }

        DialogueNode nextNode;

        if (activeDialogueIsDay) {
            nextNode = guest.getDayDialogueNode(nodeId);
        } else {
            nextNode = guest.getDialogueNode(nodeId);
        }

        if (nextNode == null) {
            finishDialogue();
            return;
        }

        activeDialogueNode = nextNode;
        activeDialogueLineIndex = 0;
    }

    private void finishDialogue() {
        String resultId = pendingDialogueResult;
        String finishedGuestId = activeGuestId;

        closeDialogue();

        handleDialogueResult(resultId, finishedGuestId);
    }


    private void closeDialogue() {
        showDialogue = false;
        activeGuestId = null;
        activeDialogueIsDay = false;
        activeDialogueNode = null;
        activeDialogueLineIndex = 0;
        pendingDialogueResult = null;
    }

    private void handleDialogueResult(String resultId, String guestId) {
        if (resultId == null) {
            return;
        }

        if (RESULT_NEIGHBOR_ENTER_HOUSE.equals(resultId)) {
            resultNeighborEnterHouse();
            return;
        }

        if (RESULT_FINISH_DAY_TALK.equals(resultId)) {
            resultFinishDayTalk(guestId);
            return;
        }

        if (RESULT_DAUGHTER_TAKE_NEIGHBOR.equals(resultId)) {
            resultDaughterTakeNeighbor();
            return;
        }

        if (resultId.startsWith(RESULT_NIGHT_VISITOR_ALLOW_PREFIX)) {
            String allowedGuestId = resultId.substring(RESULT_NIGHT_VISITOR_ALLOW_PREFIX.length());
            resultAllowNightVisitor(allowedGuestId);
            return;
        }

        if (resultId.startsWith(RESULT_NIGHT_VISITOR_REJECT_PREFIX)) {
            String rejectedGuestId = resultId.substring(RESULT_NIGHT_VISITOR_REJECT_PREFIX.length());
            resultRejectNightVisitor(rejectedGuestId);
        }
        if (resultId.startsWith(RESULT_JUDGE_VISITOR_DECISION)) {
            handleJudgeVisitorDecision(resultId);
            return;
        }

        if (RESULT_COLLECTOR_TAKE_RANDOM.equals(resultId)) {
            resultCollectorTakeRandom();
            return;
        }

    }
    private void resultCollectorTakeRandom() {
        Guest takenGuest = takeRandomLivingGuestInsideHouse();

        clearOutsideGuest();
        currentScene = SceneType.YARD_NIGHT;

        if (takenGuest != null) {
            showMessage("他带走了某人。");
        } else {
            showMessage("他似乎没有找到可以带走的人。");
        }

        advanceToNextNightVisitor();
    }

    private Guest takeRandomLivingGuestInsideHouse() {
        ArrayList<Guest> candidates = new ArrayList<Guest>();

        for (Guest guest : guestManager.getAllGuests()) {
            if (guest == null) {
                continue;
            }

            if (!guest.isInsideHouse()) {
                continue;
            }

            if (guest.isDead()) {
                continue;
            }

            candidates.add(guest);
        }

        if (candidates.isEmpty()) {
            return null;
        }

        Guest takenGuest = candidates.get(random.nextInt(candidates.size()));
        takenGuest.setInsideHouse(false);
        takenGuest.setCurrentRoom(null);

        return takenGuest;
    }

    private void handleJudgeVisitorDecision(String resultId) {
        addFlag(FLAG_JUDGE_VISITOR_VISITED);

        boolean answeredYes = resultId.endsWith(":yes");
        boolean answeredNo = resultId.endsWith(":no");

        int humanCountInside = countHumansInsideHouseExcludingPlayer();
        boolean playerIsOnlyHuman = (humanCountInside == 0);

        if (answeredYes) {
            startFlashBlack(0.8, FLASH_GAME_OVER_BY_JUDGE);
            return;
        }

        if (answeredNo) {
            if (playerIsOnlyHuman) {
                startFlashBlack(0.8, FLASH_GAME_OVER_BY_JUDGE);
                return;
            }

            clearOutsideGuest();
            currentScene = SceneType.YARD_NIGHT;
            playNightMusicForCurrentDay();
            advanceToNextNightVisitor();
        }
    }

    private int countHumansInsideHouseExcludingPlayer() {
        int count = 0;

        List<Guest> guests = guestManager.getAllGuests();

        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);

            if (guest == null) {
                continue;
            }

            if (!guest.isInsideHouse()) {
                continue;
            }

            if (guest.isDead()) {
                continue;
            }

            if (!guest.isHuman()) {
                continue;
            }

            count++;
        }

        return count;
    }


    private void resultFinishDayTalk(String guestId) {
        if (currentDay == 2 && GUEST_NEIGHBOR.equals(guestId)) {
            addFlag(FLAG_NEIGHBOR_TALKED_MORNING);
            clearStamina();
        }
    }

    private void clearStamina() {
        currentStamina = 0;
    }

    private boolean isStaminaEmpty() {
        return currentStamina <= 0;
    }



    private void resultNeighborEnterHouse() {
        Guest neighbor = guestManager.getGuest(GUEST_NEIGHBOR);

        if (neighbor != null) {
            neighbor.setInsideHouse(true);
            neighbor.setCurrentRoom(DayRoomType.KITCHEN);
        }

        clearOutsideGuest();

        addFlag(FLAG_NEIGHBOR_INSIDE_HOUSE);

        currentScene = SceneType.YARD_NIGHT;
    }


    private void resultFinishDayTalk() {
        if (currentDay == 2 && GUEST_NEIGHBOR.equals(activeGuestId)) {
            addFlag(FLAG_NEIGHBOR_TALKED_MORNING);
            clearStamina();
        }
    }


    private ArrayList<ClickableArea> getActiveClickAreas() {
        ArrayList<ClickableArea> areas = new ArrayList<ClickableArea>();

        if (currentPhase == GamePhase.NIGHT) {
            collectNightClickAreas(areas);
        } else if (currentPhase == GamePhase.DAY) {
            collectDayClickAreas(areas);
        }

        return areas;
    }

    private void collectNightClickAreas(ArrayList<ClickableArea> areas) {
        if (currentScene == SceneType.BEDROOM_NIGHT) {
            collectBedroomNightClickAreas(areas);
            return;
        }

        if (currentScene == SceneType.YARD_NIGHT) {
            collectYardNightClickAreas(areas);
        }
    }

    private void collectBedroomNightClickAreas(ArrayList<ClickableArea> areas) {
        if (hasOutsideGuest()) {
            areas.add(new ClickableArea(
                    ACTION_GO_YARD,
                    250,
                    250,
                    300,
                    560
            ));
        }

        if (canSleepNow()) {
            areas.add(new ClickableArea(
                    ACTION_SLEEP,
                    1250,
                    650,
                    760,
                    330
            ));
        }
    }


    private void collectYardNightClickAreas(ArrayList<ClickableArea> areas) {
        areas.add(new ClickableArea(
                ACTION_OPEN_GATE,
                760,
                220,
                400,
                650
        ));

        if (!hasOutsideGuest()) {
            areas.add(new ClickableArea(
                    ACTION_GO_BEDROOM_NIGHT,
                    60,
                    120,
                    280,
                    80
            ));
        }
    }

    private void collectDayClickAreas(ArrayList<ClickableArea> areas) {
        if (currentScene == SceneType.BEDROOM_DAY) {
            collectBedroomDayClickAreas(areas);
            return;
        }

        if (currentScene == SceneType.HALLWAY_DAY) {
            collectHallwayDayClickAreas(areas);
            return;
        }

        if (currentScene == SceneType.KITCHEN_DAY) {
            collectKitchenDayClickAreas(areas);
            return;
        }

        if (currentScene == SceneType.STORAGE_DAY) {
            collectStorageDayClickAreas(areas);
            return;
        }

        if (currentScene == SceneType.LIVING_ROOM_DAY) {
            collectLivingRoomDayClickAreas(areas);
        }
    }



    private void collectBedroomDayClickAreas(ArrayList<ClickableArea> areas) {
        areas.add(new ClickableArea(
                ACTION_GO_HALLWAY_DAY,
                250,
                250,
                300,
                560
        ));

        areas.add(new ClickableArea(
                ACTION_DAY_SLEEP,
                1250,
                650,
                760,
                330
        ));

        collectVisibleGuestClickAreas(areas, DayRoomType.BEDROOM);
    }


    private void collectHallwayDayClickAreas(ArrayList<ClickableArea> areas) {
        areas.add(new ClickableArea(
                ACTION_GO_KITCHEN_DAY,
                80,
                240,
                320,
                620
        ));

        areas.add(new ClickableArea(
                ACTION_GO_LIVING_ROOM_DAY,
                520,
                220,
                320,
                640
        ));

        areas.add(new ClickableArea(
                ACTION_GO_BEDROOM_DAY,
                BACK_BUTTON_X,
                BACK_BUTTON_Y,
                BACK_BUTTON_W,
                BACK_BUTTON_H
                /*1200,
                320,
                320,
                400*/
        ));

        areas.add(new ClickableArea(
                ACTION_GO_STORAGE_DAY,
                1600,
                240,
                320,
                720
        ));
    }




    private void collectKitchenDayClickAreas(ArrayList<ClickableArea> areas) {
        areas.add(new ClickableArea(
                ACTION_GO_HALLWAY_DAY,
                BACK_BUTTON_X,
                BACK_BUTTON_Y,
                BACK_BUTTON_W,
                BACK_BUTTON_H
        ));

        collectVisibleGuestClickAreas(areas, DayRoomType.KITCHEN);
    }

    private void collectStorageDayClickAreas(ArrayList<ClickableArea> areas) {
        areas.add(new ClickableArea(
                ACTION_GO_HALLWAY_DAY,
                BACK_BUTTON_X,
                BACK_BUTTON_Y,
                BACK_BUTTON_W,
                BACK_BUTTON_H
        ));

        collectVisibleGuestClickAreas(areas, DayRoomType.STORAGE);
    }

    private void collectLivingRoomDayClickAreas(ArrayList<ClickableArea> areas) {
        areas.add(new ClickableArea(
                ACTION_GO_HALLWAY_DAY,
                BACK_BUTTON_X,
                BACK_BUTTON_Y,
                BACK_BUTTON_W,
                BACK_BUTTON_H
        ));

        collectVisibleGuestClickAreas(areas, DayRoomType.LIVING_ROOM);
    }




    private void setOutsideGuest(String guestId) {
        outsideGuestId = guestId;
        outsideGuestVisible = false;
    }

    private void clearOutsideGuest() {
        outsideGuestId = null;
        outsideGuestVisible = false;
    }


    private boolean hasOutsideGuest() {
        return outsideGuestId != null;
    }

    private boolean canSleepNow() {
        if (currentPhase != GamePhase.NIGHT) {
            return false;
        }

        if (hasOutsideGuest()) {
            return false;
        }

        if (currentDay == 2 && !hasFlag(FLAG_SECOND_NIGHT_VISITORS_DONE)) {
            return false;
        }

        if (currentDay == 3 && !hasFlag(FLAG_THIRD_NIGHT_VISITORS_DONE)) {
            return false;
        }

        return true;
    }



    private void addFlag(String flag) {
        flags.add(flag);
    }

    private void removeFlag(String flag) {
        flags.remove(flag);
    }

    private boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    private void showMessage(String text) {
        message = text;
    }

    private void clearMessage() {
        message = "";
    }

    private boolean hasMessage() {
        return message != null && !message.isEmpty();
    }

    private boolean isInsideRect(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public static void main(String[] args) {
        createGame(new VisitorGame());
    }
}

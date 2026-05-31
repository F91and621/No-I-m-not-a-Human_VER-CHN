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
        Guest neighbor = new Guest("neighbor", "Neighbor");

        DialogueNode start = new DialogueNode(
                "start",
                "Hey, long time no see. How are you?"
        );

        start.addOption(new DialogueOption("I'm okay.", "A1", false));
        start.addOption(new DialogueOption("Good, thanks.", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "I'll get to it. My sister's work sent an alert.",
                "I came to check on you.",
                "Something bad is coming.",
                "I just got off the phone with her.",
                "They heard from the observatory. Solar activity is weird.",
                "Now strange people are showing up out of nowhere.",
                "The police are calling them visitors for now.",
                "Creepy, right? I hope it is fake..."
        );
        A1.addOption(new DialogueOption("What's wrong with the sun?", "B1", false));
        A1.addOption(new DialogueOption("Visitors?", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "I'll get to it. My sister's work sent an alert.",
                "I came to check on you.",
                "Something bad is coming.",
                "I just got off the phone with her.",
                "They heard from the observatory. Solar activity is weird.",
                "Now strange people are showing up out of nowhere.",
                "The police are calling them visitors for now.",
                "Creepy, right? I hope it is fake..."
        );
        A2.addOption(new DialogueOption("What's wrong with the sun?", "B1", false));
        A2.addOption(new DialogueOption("Visitors?", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "Either the sun is blowing up, or something on it is.",
                "I don't know... but something is wrong.",
                "You can feel it too.",
                "This summer is hotter than ever, right?",
                "Get ready for the worst.",
                "......",
                "My sister said being home alone is dangerous now.",
                "So I came fast to check on you."
        );
        B1.addOption(new DialogueOption("Okay, thanks.", "B3", false));
        B1.addOption(new DialogueOption("I can handle myself.", "B4", false));
        B1.addOption(new DialogueOption("How is your family?", "B5", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "Yeah, visitors, for now.",
                "They barely look human.",
                "They just appear. Did they crawl out of the dirt?",
                "My sister says they try to break into homes,",
                "but they don't steal or rob.",
                "Still creepy enough.",
                "My sister said being home alone is dangerous now.",
                "So I came fast to check on you."
        );
        B2.addOption(new DialogueOption("Okay, thanks.", "B3", false));
        B2.addOption(new DialogueOption("I can handle myself.", "B4", false));
        B2.addOption(new DialogueOption("How is your family?", "B5", false));

        DialogueNode B3 = new DialogueNode(
                "B3",
                "Okay. I'll stay here tonight.",
                "......",
                "Go to sleep.",
                "They say there will be news tomorrow. Hope it debunks this."
        );
        B3.addOption(new DialogueOption("......", "decide", true));

        DialogueNode B4 = new DialogueNode(
                "B4",
                "I know you can take care of yourself.",
                "But you don't know how dangerous it is alone.",
                "I'll stay here tonight, just in case.",
                "Don't worry about my place.",
                "My brother is staying with us. We're fine.",
                "......",
                "Go to sleep.",
                "They say there will be news tomorrow. Hope it debunks this."
        );
        B4.addOption(new DialogueOption("......", "decide", true));

        DialogueNode B5 = new DialogueNode(
                "B5",
                "Don't worry about my place.",
                "My brother is staying with us. We're fine.",
                "I'll stay here tonight, just in case.",
                "......",
                "Go to sleep.",
                "They say there will be news tomorrow. Hope it debunks this."
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
                "Damn... it is all real...",
                "My sister was right.",
                "Don't go out by day. Don't be alone at night."
        );
        dayStart.addOption(new DialogueOption("Let people into my house?", "day_a1", false));
        dayStart.addOption(new DialogueOption("Bring others in?", "day_a1", false));


        DialogueNode dayA1 = new DialogueNode(
                "day_a1",
                "I know you like being alone, but not now.",
                "No one knows what it is like out there.",
                "But my sister said,",
                "if someone knocks and asks if you're alone,",
                "say someone is here with you.",
                "Just hang in there.",
                "Too hot to stand it?",
                "Have a beer. It'll help you sleep."
        );
        dayA1.addOption(new DialogueOption("Ugh... fine.", "day_A2", true));

        neighbor.addDayDialogueNode(dayStart);
        neighbor.addDayDialogueNode(dayA1);


        neighbor.dayStartDialogueNodeId = "day_start";

        return neighbor;
    }

    public static Guest createDaughter() {
        Guest daughter = new Guest("daughter", "nver");

        DialogueNode start = new DialogueNode(
                "start",
                "Mister!",
                "Is my dad here?",
                "Can you call him for me?"
        );

        start.addOption(new DialogueOption("What's your dad's name?", "A1", false));
        start.addOption(new DialogueOption("Tell me about your dad.", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "You forgot his name?",
                "Oh--you're testing me. I'm not an visitor.",
                "Mister, you're smart!",
                "I'll start asking people too."
        );
        A1.addOption(new DialogueOption("How did you get here?", "B1", false));
        A1.addOption(new DialogueOption("Are you scared of visitors?", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "When I'm scared of the dark, he plays with me.",
                "Dad says when I'm brave enough, the sun will come up."
        );
        A2.addOption(new DialogueOption("How did you get here?", "B1", false));
        A2.addOption(new DialogueOption("Are you scared of visitors?", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "Secret: I snuck out!",
                "Everything is weird now. " ,
                        "People sleep by day and wake at night.",
                "I'm still scared of the dark.",
                "Dad says I'm old enough for school, so I shouldn't be."
        );
        B1.addOption(new DialogueOption("I'll call your dad!", "B3", false));
        B1.addOption(new DialogueOption("You're brave!", "B4", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "A little.",
                "But if I see them, I won't be scared.",
                "What are visitor, really?",
                "If a classmate bullies me,",
                "are they an visitor too?",
                "I don't know.",
                "But if Dad is there, I'm not scared!"
        );
        B2.addOption(new DialogueOption("I'll call your dad!", "B3", false));
        B2.addOption(new DialogueOption("You're brave!", "B4", false));

        DialogueNode B3 = new DialogueNode(
                "B3",
                "Thanks, mister!",
                "I want to go home... It's scary outside...",
                "I'm going to play a new game with Dad!",
                "I'll teach you someday.",
                "Bye!"
        );
        B3.addOption(new DialogueOption("...", null, true));

        DialogueNode B4 = new DialogueNode(
                "B4",
                "Thanks...",
                "I'm still a little scared. I want to go home with Dad."
        );
        B4.addOption(new DialogueOption("...", null, true));

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
                true,   // straight teeth
                true,   // clean hands
                false,  // eyes not red
                true,   // clean ears
                "firefighter_teeth",
                "firefighter_hands",
                "firefighter_eyes",
                "firefighter_ears"
        ));


        DialogueNode start = new DialogueNode(
                "start",
                "*cough cough*",
                "Can you let me in, brother?",
                ""
        );


        start.addOption(new DialogueOption("Who are you?", "A1", false));
        start.addOption(new DialogueOption("What happened to you?", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                       "I'm a *cough* firefighter.",
                       "We were fighting a forest fire today.",
                       "We stood under the sun.",
                       "The smoke kept us from burning up completely,",
                       "but *cough* it was still bad.",
                       "I've never seen anything like it.",
                       "Dead birds and animals everywhere..."

        );
        A1.addOption(new DialogueOption("Why aren't you at a hospital?", "B1", false));
        A1.addOption(new DialogueOption("What will you do?", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                        "I'm the only one left in my crew." ,
                        "The others... *cough* command wouldn't let them leave." ,
                        "They're on duty forever now."
        );
        A2.addOption(new DialogueOption("Why aren't you at a hospital?", "B1", false));
        A2.addOption(new DialogueOption("What will you do?", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "Hospital? There is no hospital, brother." ,
                        "With visitors around, hospitals are gone." ,
                        "Now *cough* no one can help me." ,
                        "I should have died there with my crew." ,
                        "After all," ,
                        "I don't have much time left."
        );
        B1.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "I want to keep fighting fires." ,
                        "I wish I had died, not them." ,
                        "All I can do now is count on kind people."
        );
        B2.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        firefighter.addDialogueNode(start);
        firefighter.addDialogueNode(A1);
        firefighter.addDialogueNode(A2);
        firefighter.addDialogueNode(B1);
        firefighter.addDialogueNode(B2);


        firefighter.addDialogueNode(start);
        firefighter.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "Hot enough..."
        );
        dayStart.addOption(new DialogueOption("Let me check you.", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("That's all.", null, true));

        firefighter.addDayDialogueNode(dayStart);
        firefighter.dayStartDialogueNodeId = "day_start";


        return firefighter;
    }

    public static Guest createTeacher() {
        Guest teacher = new Guest("teacher", "laoshi");

        teacher.setIdentity(GuestIdentity.HUMAN);

        teacher.setFeatures(new GuestFeatures(
                true,   // straight teeth
                true,   // clean hands
                false,  // eyes not red
                true,   // clean ears
                "teacher_teeth",
                "teacher_hands",
                "teacher_eyes",
                "teacher_ears"
        ));

        DialogueNode start = new DialogueNode(
                "start",
                "Sorry... do you have a minute?" ,
                "Can I talk to you?"
        );


        start.addOption(new DialogueOption("What happened?", "A1", false));
        start.addOption(new DialogueOption("Who are you?", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "I just wanted to give those kids some hope." ,
                        "I took in kids they called visitors." ,
                        "We lived in an old factory." ,
                        "They only needed care. Love.",
                        "I tried my best..." ,
                        "But it wasn't enough... I couldn't save them."

        );
        A1.addOption(new DialogueOption("What happened to the kids?", "B1", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "I'm a teacher... a kindergarten teacher." ,
                        "When the world fell apart, I took those kids in." ,
                        "They had lost their parents for different reasons.",
                        "Some were quarantined. Some were killed. Some..." ,
                        "Some parents left their child",
                        "once the child showed signs of being an visitor.",
                        "But now... they're all gone. None survived the fire..."
        );
        A2.addOption(new DialogueOption("Why was there a fire?", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "They..." ,
                        "......" ,
                        "They all died... in the fire." ,
                        "They did it. They were scared the kids might be visitors." ,
                        "They made me watch... locked the doors, nailed the windows..." ,
                        "Then they set the building on fire." ,
                        "I can still feel them,",
                        "their scared little eyes watching me."
        );
        B1.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "It was... the other townspeople." ,
                        "They found out I was sheltering kids with visitor signs." ,
                        "......" ,
                        "What have we become?",
                        "A cruel mob judging helpless kids?" ,
                        "What did they..." ,
                        "do to deserve that?" ,
                        "They burned it all. They made me watch."
        );
        B2.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        teacher.addDialogueNode(start);
        teacher.addDialogueNode(A1);
        teacher.addDialogueNode(A2);
        teacher.addDialogueNode(B1);
        teacher.addDialogueNode(B2);

        teacher.addDialogueNode(start);
        teacher.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "The children..."
        );
        dayStart.addOption(new DialogueOption("Let me check you.", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("That's all.", null, true));

        teacher.addDayDialogueNode(dayStart);
        teacher.dayStartDialogueNodeId = "day_start";

        return teacher;
    }


    public static Guest createCoatperson() {
        Guest coatperson = new Guest("coat_person", "coat_person");

        coatperson.setIdentity(GuestIdentity.VISITOR);

        coatperson.setFeatures(new GuestFeatures(
                false,  // crooked teeth
                false,  // dirty hands
                true,   // red eyes
                false,  // dirty ears
                "coat_person_teeth",
                "coat_person_hands",
                "coat_person_eyes",
                "coat_person_ears"
        ));


        DialogueNode start = new DialogueNode(
                "start",
                "Hello." ,
                        "I'm not hot. I just want a quiet place to rest." ,
                        "Is your house quiet?",
                ""
        );
        start.addOption(new DialogueOption("Who are you?", "A1", false));
        start.addOption(new DialogueOption("How long have you been here?", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                " Doesn't matter. Are you wondering if I'm an visitor?" ,
                        "I'm not. But I do have a request." ,
                        "I'll ask later. I'm not sure I can trust you yet."

        );
        A1.addOption(new DialogueOption("You said you're not hot?", "B1", false));
        A1.addOption(new DialogueOption("Can't you take off that coat?", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "..." ,
                        "A long time."
        );
        A2.addOption(new DialogueOption("You said you're not hot?", "B1", false));
        A2.addOption(new DialogueOption("Can't you take off that coat?", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "I'm always cold." ,
                        "I wear layers, but I still feel frozen." ,
                        "I'm scared to take anything off." ,
                        "What if it gets worse?",
                        "Won't death ease the pain?"
        );
        B1.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "No. I won't." ,
                        "You don't have to let me in." ,
                        "I'll go. I won't beg. I won't bother you again."
        );
        B2.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        coatperson.addDialogueNode(start);
        coatperson.addDialogueNode(A1);
        coatperson.addDialogueNode(A2);
        coatperson.addDialogueNode(B1);
        coatperson.addDialogueNode(B2);

        coatperson.addDialogueNode(start);
        coatperson.startDialogueNodeId = "start";


        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "Morning.",
                "It's warmer inside than outside.",
                "A bit warmer would be nice..."
        );
        dayStart.addOption(new DialogueOption("Let me check you.", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("That's all.", null, true));

        coatperson.addDayDialogueNode(dayStart);
        coatperson.dayStartDialogueNodeId = "day_start";




        return coatperson;
    }

    public static Guest createJudge() {
        Guest judgeVisitor = new Guest("judge_visitor", "super");

        judgeVisitor.setIdentity(GuestIdentity.VISITOR);



        DialogueNode start = new DialogueNode(
                "start",
                "Hello!"
        );
        start.addOption(new DialogueOption("What do you want?", "A1", false));
        start.addOption(new DialogueOption("You're not coming in.", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "Your house pretty big.",
                "I like it."
        );
        A1.addOption(new DialogueOption("Get lost.", "B1", false));
        A1.addOption(new DialogueOption("I have a gun.", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "Is this door sturdy?",
                "You never know what tomorrow brings.",
                "Hahaha."
        );
        A2.addOption(new DialogueOption("Get lost.", "B1", false));
        A2.addOption(new DialogueOption("I have a gun.", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "Haha!",
                "So...",
                "Are you home alone?"
        );
        B1.addOption(new DialogueOption("Lots of people are here.", "B3", false));
        B1.addOption(new DialogueOption("Just me.", "B4", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "Haha!",
                "So...",
                "Are you home alone?"
        );


        B2.addOption(new DialogueOption("Lots of people are here.", "B3", false));
        B2.addOption(new DialogueOption("Just me.", "B4", false));

        DialogueNode B3 = new DialogueNode(
                "B3",
                "......",
                "Lucky you."
        );
        B3.addOption(new DialogueOption("......","__judge_answer_no__",false));

        DialogueNode B4 = new DialogueNode(
                "B4",
                "Haha!",
                "I know you're alone.",
                "I just wanted to see if you'd lie.",
                "I'll let myself in."
        );
        B4.addOption(new DialogueOption("......","__judge_answer_yes__",false));

        DialogueNode fourthStart = new DialogueNode(
                "fourth_start",
                "We meet again.",
                ".....",
                "What did you think of that masked one?",
                "The smell in your house changed.",
                "Someone is gone, and someone stayed."
        );
        fourthStart.addOption(new DialogueOption("What are you checking now?", "fourth_A1", false));
        fourthStart.addOption(new DialogueOption("Why are you back?", "fourth_A2", false));

        DialogueNode fourthA1 = new DialogueNode(
                "fourth_A1",
                "Hahahaha!",
                "What can you really do?",
                "Can you stop death?",
                "Can you stop a flood?",
                "Can you stop a fire?",
                "The only thing you can stop,",
                "is your heartbeat.",
                "Alright...",
                "Are you home alone?"
        );
        fourthA1.addOption(new DialogueOption("Lots of people are here.", "fourthA3", false));
        fourthA1.addOption(new DialogueOption("Just me.", "fourthA4", false));

        DialogueNode fourthA2 = new DialogueNode(
                "fourth_A2",
                "Things have been hard lately, right?",
                "Still trying to survive in this new world?",
                "Why?",
                "You know what I mean.",
                "The good times were yesterday.",
                "Life has changed.",
                "Your pace is all wrong now.",
                "Alright...",
                "Are you home alone?"

        );
        fourthA2.addOption(new DialogueOption("Lots of people are here.", "fourthA3", false));
        fourthA2.addOption(new DialogueOption("Just me.", "fourthA4", false));

        DialogueNode fourthA3 = new DialogueNode(
                "fourthA3",
                "......",
                "Good.",
                "We all wonder how long you'll last."
        );
        fourthA3.addOption(new DialogueOption("......","__judge_answer_no__",false));

        DialogueNode fourthA4 = new DialogueNode(
                "fourthA4",
                "Haha!",
                "I know you're alone.",
                "Couldn't get along with anyone in the end?",
                "What a shame.",
                "Then I'll slip in quickly.",
                "I have a little gift for you."
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
                      "Can I come in and rest?"
        );
        start.addOption(new DialogueOption("Where are you from?", "A1", false));
        start.addOption(new DialogueOption("Where will you go after?", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "I don't know anymore. It doesn't matter.",
                "I'm so tired.",
                "If I don't rest, I might pass out."

        );
        A1.addOption(new DialogueOption("Is your friend sick?", "B1", false));
        A1.addOption(new DialogueOption("Does your friend need help?", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "Probably pass out somewhere and never get up.",
                "I don't care where I end up.",
                "I'm dying anyway."
        );
        A2.addOption(new DialogueOption("Is your friend sick?", "B1", false));
        A2.addOption(new DialogueOption("Does your friend need help?", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "He is my husband." ,
                        "He's dead." ,
                        "I couldn't bury him right. No point leaving him now."
        );
        B1.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "No.",
                "When he needed help, no one helped.",
                "Now he's dead.",
                "And no one cares."
        );
        B2.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        widow.addDialogueNode(start);
        widow.addDialogueNode(A1);
        widow.addDialogueNode(A2);
        widow.addDialogueNode(B1);
        widow.addDialogueNode(B2);


        widow.addDialogueNode(start);
        widow.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "I need to rest..."
        );

        dayStart.addOption(new DialogueOption("Let me check you.", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("That's all.", null, true));

        widow.addDayDialogueNode(dayStart);
        widow.dayStartDialogueNodeId = "day_start";

        return widow;
    }


    public static Guest createAuntie(){
        Guest auntie = new Guest("auntie", "dama");
        auntie.setIdentity(GuestIdentity.VISITOR);

        DialogueNode start = new DialogueNode(
                "start",
                "Hey!" ,
                "Anybody there?",
                ""
        );
        start.addOption(new DialogueOption("Who are you?", "A1", false));
        start.addOption(new DialogueOption("Looking for someone?", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "Me and my kids used to live in a village nearby." ,
                        "Those bastards in yellow drove us out, " ,
                                "but  they didn't take us anywhere." ,
                        "No shelter, no help. Where are we supposed to go?" ,
                        "After that, everyone sp]lit up."

        );
        A1.addOption(new DialogueOption("Why did they drive you out?", "B1", false));
        A1.addOption(new DialogueOption("Were there many of you?", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "I just want somewhere to rest." ,
                        "The whole village got cleared. Don't know where to go."
        );
        A2.addOption(new DialogueOption("Why did they drive you out?", "B1", false));
        A2.addOption(new DialogueOption("Were there many of you?", "B2", false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "They said our village got hit by some solar blast, or whatever." ,
                        "The yellow suits rushed in and threw people out." ,
                        "They grabbed a few people too." ,
                        "Bunch of bastards."
        );
        B1.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                        "Some wouldn't leave, so they dragged them out." ,
                        "Like picking livestock." ,
                        "Now I just want to live..."
        );
        B2.addOption(new DialogueOption("Come in.", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        auntie.addDialogueNode(start);
        auntie.addDialogueNode(A1);
        auntie.addDialogueNode(A2);
        auntie.addDialogueNode(B1);
        auntie.addDialogueNode(B2);

        auntie.addDialogueNode(start);
        auntie.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "Young man..." ,
                        "Better not let anyone else in."
        );

        dayStart.addOption(new DialogueOption("Let me check you.", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("That's all.", null, true));

        auntie.addDayDialogueNode(dayStart);
        auntie.dayStartDialogueNodeId = "day_start";

        return auntie;
    }

    public static Guest createPanicGirl() {
        Guest panic_girl = new Guest("panic_girl", "poor");
        panic_girl.setIdentity(GuestIdentity.HUMAN);

        DialogueNode start = new DialogueNode(
                "start",
                "Mister, mister..." ,
                        "Let... let me in.",
                ""
        );
        start.addOption(new DialogueOption("Why the rush?", "A1", false));
        start.addOption(new DialogueOption("What happened?", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",

                "I don't know what to do." ,
                        "Visitor..." ,
                        "Killed..." ,
                        "Killed my dad..." ,
                        "I, I, I... my..." ,
                        "Dad."

        );
        A1.addOption(new DialogueOption("Visitor?", "B1", false));
        A1.addOption(new DialogueOption("Why did they do that?", "B2", false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "Uh..." ,
                        "M-m-my dad..." ,
                        "Visitor... visitor..." ,
                        "They killed my dad."
        );
        A2.addOption(new DialogueOption("Visitor?", "B1", false));
        A2.addOption(new DialogueOption("Why did they do that?", "B2", false));
        DialogueNode B1 = new DialogueNode(
                "B1",
                "Visitor came to our house. They sat with us..." ,
                        "They were talking. Then they... killed him." ,
                        "He... *sob* our... *sob*" ,
                        "Our house was on fire too...",
                        "Ah! Aaaah! Dad! Dad!"
        );
        B1.addOption(new DialogueOption("Come in, quick.", "__allow_current_guest__", false));
        B1.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        DialogueNode B2 = new DialogueNode(
                "B2",
                "I... I don't know." ,
                        "Dad... Dad was just talking to them." ,
                        "Then they... then they set the house on fire." ,
                        "I... *sob* I heard them laugh...",
                        "Ah! Aaaah! Dad! Dad!"
        );
        B2.addOption(new DialogueOption("Come in, quick.", "__allow_current_guest__", false));
        B2.addOption(new DialogueOption("You should leave now.", "__reject_current_guest__", false));

        panic_girl.addDialogueNode(start);
        panic_girl.addDialogueNode(A1);
        panic_girl.addDialogueNode(A2);
        panic_girl.addDialogueNode(B1);
        panic_girl.addDialogueNode(B2);

        panic_girl.addDialogueNode(start);
        panic_girl.startDialogueNodeId = "start";

        DialogueNode dayStart = new DialogueNode(
                "day_start",
                "Dad... Mom..."
        );

        dayStart.addOption(new DialogueOption("Let me check you.", "__inspect_features__", false));
        dayStart.addOption(new DialogueOption("That's all.", null, true));

        panic_girl.addDayDialogueNode(dayStart);
        panic_girl.dayStartDialogueNodeId = "day_start";

        return panic_girl;
    }

    public static Guest createCollector() {
        Guest collector = new Guest("collector", "Collector");

        collector.setIdentity(GuestIdentity.VISITOR);

        DialogueNode start = new DialogueNode(
                "start",
                "Good evening.",
                "We were sent by higher-ups. I can't say more."
        );

        start.addOption(new DialogueOption("Just you?", "A1", false));
        start.addOption(new DialogueOption("Why should I trust you?", "A2", false));

        DialogueNode A1 = new DialogueNode(
                "A1",
                "No, of course not.",
                "Our team split up to cover more ground.",
                "I won't take much of your time."
        );
        A1.addOption(new DialogueOption("What do you want?","B1",false));

        DialogueNode A2 = new DialogueNode(
                "A2",
                "Here is my ID. You are required to hear me out."
        );
        A2.addOption(new DialogueOption("What do you want?","B1",false));

        DialogueNode B1 = new DialogueNode(
                "B1",
                "I need one person.",
                "We will examine and study them.",
                "to resolve this crisis as soon as possible.",
                "If they are human, we will keep them safe.",
                "For everyone's safety, think carefully."
        );
        B1.addOption(new DialogueOption("...", null, true));
        collector.addDialogueNode(start);
        collector.addDialogueNode(A1);
        collector.addDialogueNode(A2);
        collector.addDialogueNode(B1);
        collector.startDialogueNodeId = "start";
        return collector;
    }





}

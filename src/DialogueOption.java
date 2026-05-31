public class DialogueOption {

    private String text;
    private String nextNodeId;
    private boolean endDialogue;

    public DialogueOption(String text, String nextNodeId, boolean endDialogue) {
        this.text = text;
        this.nextNodeId = nextNodeId;
        this.endDialogue = endDialogue;
    }

    public String getText() {
        return text;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public boolean isEndDialogue() {
        return endDialogue;
    }
}

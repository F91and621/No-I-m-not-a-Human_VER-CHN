import java.util.ArrayList;
import java.util.List;

public class DialogueNode {

    private String id;
    private String[] lines;
    private ArrayList<DialogueOption> options;

    public DialogueNode(String id, String... lines) {
        this.id = id;
        this.lines = lines;
        this.options = new ArrayList<DialogueOption>();
    }

    public String getId() {
        return id;
    }

    public String[] getLines() {
        return lines;
    }

    public String getLine(int index) {
        if (lines == null || index < 0 || index >= lines.length) {
            return "";
        }

        return lines[index];
    }

    public int getLineCount() {
        if (lines == null) {
            return 0;
        }

        return lines.length;
    }

    public void addOption(DialogueOption option) {
        if (option == null) {
            return;
        }

        options.add(option);
    }

    public List<DialogueOption> getOptions() {
        return options;
    }

    public DialogueOption getOption(int index) {
        if (index < 0 || index >= options.size()) {
            return null;
        }

        return options.get(index);
    }

    public boolean hasOptions() {
        return options != null && !options.isEmpty();
    }
}

package playduin.newsfeed.models.news;

public class SourceItem {
    private final String displayName;
    private final String source;

    public SourceItem(String displayName, String source) {
        this.displayName = displayName;
        this.source = source;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSource() {
        return source;
    }
}

package playduin.newsfeed.ui.selectsource.adapter;

public class SelectSourceEvent {
    public static final int OPEN_NEWS_SOURCE_EVENT = 1;

    private final int event;
    private final String source;

    public SelectSourceEvent(int event, String source) {
        this.event = event;
        this.source = source;
    }

    public static SelectSourceEvent createOpenNewsSourceEvent(String source) {
        return new SelectSourceEvent(OPEN_NEWS_SOURCE_EVENT, source);
    }

    public int getEvent() {
        return event;
    }

    public String getSource() {
        return source;
    }
}

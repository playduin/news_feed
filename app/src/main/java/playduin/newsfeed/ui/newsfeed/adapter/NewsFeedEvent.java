package playduin.newsfeed.ui.newsfeed.adapter;

public class NewsFeedEvent {
    public static final int SAVE_NEWS_ITEM_EVENT = 1;
    public static final int UNSAVE_NEWS_ITEM_EVENT = 2;
    public static final int OPEN_NEWS_ITEM_EVENT = 3;

    private final int event;
    private final String url;

    public NewsFeedEvent(int event, String url) {
        this.event = event;
        this.url = url;
    }

    public static NewsFeedEvent createSaveNewsItemEvent(String url) {
        return new NewsFeedEvent(SAVE_NEWS_ITEM_EVENT, url);
    }

    public static NewsFeedEvent createUnsaveNewsItemEvent(String url) {
        return new NewsFeedEvent(UNSAVE_NEWS_ITEM_EVENT, url);
    }

    public static NewsFeedEvent createOpenNewsItemEvent(String url) {
        return new NewsFeedEvent(OPEN_NEWS_ITEM_EVENT, url);
    }

    public int getEvent() {
        return event;
    }

    public String getUrl() {
        return url;
    }
}

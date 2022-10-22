package playduin.newsfeed.ui.newsfeed.state;

import android.util.Log;

import java.util.List;

import playduin.newsfeed.models.news.NewsItem;
import playduin.newsfeed.ui.mvi.ScreenState;
import playduin.newsfeed.ui.newsfeed.NewsFeedContract;

public class NewsFeedScreenState extends ScreenState<NewsFeedContract.View> {
    private static final int SET_ITEMS = 1;

    private final int action;
    private final List<NewsItem> itemList;

    public NewsFeedScreenState(int action, List<NewsItem> itemList) {
        this.action = action;
        this.itemList = itemList;
    }

    public static NewsFeedScreenState createSetItemsState(List<NewsItem> itemList) {
        return new NewsFeedScreenState(SET_ITEMS, itemList);
    }

    public void visit(NewsFeedContract.View newsFeedView) {
        Log.d("NewsFeedScreenState", "visit");
        if (SET_ITEMS == action) {
            newsFeedView.setItems(itemList);
        }
    }
}

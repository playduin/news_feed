package playduin.newsfeed.ui.newsfeed.state;

import playduin.newsfeed.ui.mvi.AbstractEffect;
import playduin.newsfeed.ui.newsfeed.NewsFeedContract;

public class NewsFeedScreenEffect extends AbstractEffect<NewsFeedContract.View> {
    private static final int SET_NEWS_ITEM_FRAGMENT = 1;
    private static final int SHOW_NETWORK_ERROR_DIALOG = 2;

    private final int action;
    private final String url;

    public NewsFeedScreenEffect(int action) {
        this.action = action;
        url = null;
    }

    public NewsFeedScreenEffect(int action, String url) {
        this.action = action;
        this.url = url;
    }

    public static NewsFeedScreenEffect createSetNewsItemFragmentAction(String url) {
        return new NewsFeedScreenEffect(SET_NEWS_ITEM_FRAGMENT, url);
    }

    public static NewsFeedScreenEffect createShowNetworkErrorDialogAction() {
        return new NewsFeedScreenEffect(SHOW_NETWORK_ERROR_DIALOG);
    }

    public void handle(NewsFeedContract.View newsFeedView) {
        if (SET_NEWS_ITEM_FRAGMENT == action) {
            newsFeedView.setNewsItemFragment(url);
        } else if (SHOW_NETWORK_ERROR_DIALOG == action) {
            newsFeedView.showNetworkErrorDialog();
        }
    }
}

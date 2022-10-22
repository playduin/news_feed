package playduin.newsfeed.ui.selectsource.state;

import playduin.newsfeed.ui.mvi.AbstractEffect;
import playduin.newsfeed.ui.selectsource.SelectSourceContract;

public class SelectSourceScreenEffect extends AbstractEffect<SelectSourceContract.View> {
    private static final int SET_NEWS_FEED_FRAGMENT = 1;

    private final int action;
    private final String source;

    public SelectSourceScreenEffect(int action, String source) {
        this.action = action;
        this.source = source;
    }

    public static SelectSourceScreenEffect createSetNewsFeedFragmentAction(String source) {
        return new SelectSourceScreenEffect(SET_NEWS_FEED_FRAGMENT, source);
    }

    public void handle(SelectSourceContract.View selectSourceView) {
        if (SET_NEWS_FEED_FRAGMENT == action) {
            selectSourceView.setNewsFeedFragment(source);
        }
    }
}

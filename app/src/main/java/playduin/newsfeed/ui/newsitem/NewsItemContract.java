package playduin.newsfeed.ui.newsitem;

import playduin.newsfeed.ui.mvi.FragmentContract;
import playduin.newsfeed.ui.newsitem.state.NewsItemScreenEffect;
import playduin.newsfeed.ui.newsitem.state.NewsItemScreenState;

public class NewsItemContract {
    public interface ViewModel extends FragmentContract.ViewModel<NewsItemScreenState, NewsItemScreenEffect> {

    }

    public interface View extends FragmentContract.View {

    }

    public interface Host extends FragmentContract.Host {

    }
}

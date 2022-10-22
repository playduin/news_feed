package playduin.newsfeed.ui.newsfeed;

import java.util.List;

import playduin.newsfeed.models.news.NewsItem;
import playduin.newsfeed.ui.mvi.FragmentContract;
import playduin.newsfeed.ui.newsfeed.state.NewsFeedScreenEffect;
import playduin.newsfeed.ui.newsfeed.state.NewsFeedScreenState;

public class NewsFeedContract {
    public interface ViewModel extends FragmentContract.ViewModel<NewsFeedScreenState, NewsFeedScreenEffect> {
        void saveItem(String url);

        void removeItem(String url);

        void refresh();

        void reload();
    }

    public interface View extends FragmentContract.View {
        void setNewsItemFragment(String url);

        void setItems(List<NewsItem> list);

        void showNetworkErrorDialog();
    }

    public interface Host extends FragmentContract.Host {
        void proceedNewsFeedScreenToNewsItemScreen(String url);

        void showNetworkErrorDialog();
    }
}

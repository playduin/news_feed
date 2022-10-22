package playduin.newsfeed.ui.selectsource;

import java.util.List;

import playduin.newsfeed.ui.mvi.FragmentContract;
import playduin.newsfeed.models.news.SourceItem;
import playduin.newsfeed.ui.selectsource.state.SelectSourceScreenEffect;
import playduin.newsfeed.ui.selectsource.state.SelectSourceScreenState;

public class SelectSourceContract {
    public interface ViewModel extends FragmentContract.ViewModel<SelectSourceScreenState, SelectSourceScreenEffect> {

    }

    public interface View extends FragmentContract.View {
        void setNewsFeedFragment(String source);

        void setItems(List<SourceItem> list);
    }

    public interface Host extends FragmentContract.Host {
        void proceedSelectSourceScreenToNewsFeedScreen(String source);
    }
}

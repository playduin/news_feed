package playduin.newsfeed.ui.selectsource;

import androidx.lifecycle.Lifecycle;

import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.ui.mvi.BaseViewModel;
import playduin.newsfeed.ui.selectsource.state.SelectSourceScreenEffect;
import playduin.newsfeed.ui.selectsource.state.SelectSourceScreenState;

public class SelectSourceViewModel extends BaseViewModel<SelectSourceScreenState, SelectSourceScreenEffect> implements SelectSourceContract.ViewModel {
    private final NewsFeedRepo newsFeedRepo;

    public SelectSourceViewModel(NewsFeedRepo newsFeedRepo) {
        this.newsFeedRepo = newsFeedRepo;
    }

    @Override
    public void onStateChanged(Lifecycle.Event event) {
        super.onStateChanged(event);
        if (event == Lifecycle.Event.ON_CREATE) {
            setState(SelectSourceScreenState.createSetItemsState(newsFeedRepo.getSources()));
        }
    }
}

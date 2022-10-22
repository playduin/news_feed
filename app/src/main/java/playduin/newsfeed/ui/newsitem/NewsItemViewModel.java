package playduin.newsfeed.ui.newsitem;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import playduin.newsfeed.ui.mvi.BaseViewModel;
import playduin.newsfeed.ui.newsitem.state.NewsItemScreenEffect;
import playduin.newsfeed.ui.newsitem.state.NewsItemScreenState;

@HiltViewModel
public class NewsItemViewModel extends BaseViewModel<NewsItemScreenState, NewsItemScreenEffect> implements NewsItemContract.ViewModel {
    @Inject
    public NewsItemViewModel() {

    }
}

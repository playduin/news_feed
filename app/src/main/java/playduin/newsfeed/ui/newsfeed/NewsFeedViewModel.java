package playduin.newsfeed.ui.newsfeed;

import androidx.lifecycle.Lifecycle;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.ui.mvi.BaseViewModel;
import playduin.newsfeed.ui.newsfeed.state.NewsFeedScreenEffect;
import playduin.newsfeed.ui.newsfeed.state.NewsFeedScreenState;

public class NewsFeedViewModel extends BaseViewModel<NewsFeedScreenState, NewsFeedScreenEffect>
        implements NewsFeedContract.ViewModel {
    private final NewsFeedRepo newsFeedRepo;
    private final String source;

    public NewsFeedViewModel(NewsFeedRepo newsFeedRepo, String source) {
        this.newsFeedRepo = newsFeedRepo;
        this.source = source;
    }

    @Override
    public void onStateChanged(Lifecycle.Event event) {
        super.onStateChanged(event);
        if (event == Lifecycle.Event.ON_CREATE && disposables.size() == 0) {
            refresh();
        }
    }

    @Override
    public void saveItem(String url) {
        disposables.add(newsFeedRepo.saveNewsItem(url)
                .subscribeOn(Schedulers.io())
                .doOnComplete(this::reload)
                .subscribe());
    }

    @Override
    public void removeItem(String url) {
        disposables.add(newsFeedRepo.unsaveNewsItem(url)
                .subscribeOn(Schedulers.io())
                .doOnComplete(this::reload)
                .subscribe());
    }

    @Override
    public void refresh() {
        disposables.add(newsFeedRepo.sync()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(news -> reload())
                .doOnError(error -> reload())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                }, error -> setAction(NewsFeedScreenEffect.createShowNetworkErrorDialogAction())));
    }

    @Override
    public void reload() {
        disposables.add(newsFeedRepo.getNews(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> setState(NewsFeedScreenState.createSetItemsState(news)), Throwable::printStackTrace));
    }
}

package playduin.newsfeed.ui.newsfeed;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import playduin.newsfeed.models.news.NewsFeedRepo;

public class NewsFeedViewModelFactory implements ViewModelProvider.Factory {
    private final NewsFeedRepo newsFeedRepo;
    private String source = NewsFeedRepo.ALL;

    @Inject
    public NewsFeedViewModelFactory(NewsFeedRepo newsFeedRepo) {
        this.newsFeedRepo = newsFeedRepo;
    }

    public NewsFeedViewModelFactory setSource(String source) {
        this.source = source;
        return this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsFeedViewModel.class)) {
            return (T) new NewsFeedViewModel(newsFeedRepo, source);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

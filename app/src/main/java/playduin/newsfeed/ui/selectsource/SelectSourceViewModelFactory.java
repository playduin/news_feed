package playduin.newsfeed.ui.selectsource;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import playduin.newsfeed.models.news.NewsFeedRepo;

public class SelectSourceViewModelFactory implements ViewModelProvider.Factory {
    private final NewsFeedRepo newsFeedRepo;

    @Inject
    public SelectSourceViewModelFactory(NewsFeedRepo newsFeedRepo) {
        this.newsFeedRepo = newsFeedRepo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SelectSourceViewModel.class)) {
            return (T) new SelectSourceViewModel(newsFeedRepo);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

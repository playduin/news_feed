package playduin.newsfeed.ui.newsfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;
import playduin.newsfeed.databinding.NewsfeedFragmentBinding;
import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.models.news.NewsItem;
import playduin.newsfeed.ui.mvi.HostedFragment;
import playduin.newsfeed.ui.newsfeed.adapter.NewsFeedAdapter;
import playduin.newsfeed.ui.newsfeed.adapter.NewsFeedEvent;
import playduin.newsfeed.ui.newsfeed.state.NewsFeedScreenEffect;
import playduin.newsfeed.ui.newsfeed.state.NewsFeedScreenState;

@AndroidEntryPoint
public class NewsFeedFragment extends HostedFragment<
        NewsFeedContract.View, NewsFeedScreenState, NewsFeedScreenEffect, NewsFeedContract.ViewModel,
        NewsFeedContract.Host> implements NewsFeedContract.View, SwipeRefreshLayout.OnRefreshListener {
    private static final String NEWS_SOURCE = "NewsSource";

    private final NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter();
    @Inject
    NewsFeedViewModelFactory factory;
    private Disposable disposable;
    private NewsfeedFragmentBinding binding;

    @Override
    protected NewsFeedContract.ViewModel createModel() {
        final String source;
        if (getArguments() == null) {
            source = NewsFeedRepo.ALL;
        } else {
            source = getArguments().getString(NEWS_SOURCE);
        }
        return new ViewModelProvider(this, factory.setSource(source)).get(NewsFeedViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        disposable = newsFeedAdapter.getEventsSubject().subscribe(newsFeedEvent -> {
            if (NewsFeedEvent.OPEN_NEWS_ITEM_EVENT == newsFeedEvent.getEvent()) {
                setNewsItemFragment(newsFeedEvent.getUrl());
            } else if (NewsFeedEvent.SAVE_NEWS_ITEM_EVENT == newsFeedEvent.getEvent()) {
                getModel().saveItem(newsFeedEvent.getUrl());
            } else if (NewsFeedEvent.UNSAVE_NEWS_ITEM_EVENT == newsFeedEvent.getEvent()) {
                getModel().removeItem(newsFeedEvent.getUrl());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewsfeedFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.getRoot().setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerView.setAdapter(newsFeedAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getModel().reload();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    public void onRefresh() {
        getModel().refresh();
    }

    @Override
    public void setNewsItemFragment(String url) {
        if (hasHost()) {
            getFragmentHost().proceedNewsFeedScreenToNewsItemScreen(url);
        }
    }

    @Override
    public void setItems(List<NewsItem> list) {
        binding.getRoot().setRefreshing(false);
        newsFeedAdapter.submitList(list);
    }

    @Override
    public void showNetworkErrorDialog() {
        if (hasHost()) {
            getFragmentHost().showNetworkErrorDialog();
        }
    }
}

package playduin.newsfeed.ui.selectsource;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;
import playduin.newsfeed.databinding.SelectSourceFragmentBinding;
import playduin.newsfeed.ui.mvi.HostedFragment;
import playduin.newsfeed.ui.selectsource.adapter.SelectSourceAdapter;
import playduin.newsfeed.ui.selectsource.adapter.SelectSourceEvent;
import playduin.newsfeed.models.news.SourceItem;
import playduin.newsfeed.ui.selectsource.state.SelectSourceScreenEffect;
import playduin.newsfeed.ui.selectsource.state.SelectSourceScreenState;

@AndroidEntryPoint
public class SelectSourceFragment extends HostedFragment<SelectSourceContract.View, SelectSourceScreenState, SelectSourceScreenEffect, SelectSourceContract.ViewModel, SelectSourceContract.Host> implements SelectSourceContract.View {
    private final SelectSourceAdapter selectSourceAdapter = new SelectSourceAdapter();
    @Inject
    SelectSourceViewModelFactory factory;
    private Disposable disposable;
    private SelectSourceFragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        disposable = selectSourceAdapter.getEventsSubject().subscribe(selectSourceEvent -> {
            if (SelectSourceEvent.OPEN_NEWS_SOURCE_EVENT == selectSourceEvent.getEvent()) {
                setNewsFeedFragment(selectSourceEvent.getSource());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SelectSourceFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerView.setAdapter(selectSourceAdapter);
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
    protected SelectSourceContract.ViewModel createModel() {
        return new ViewModelProvider(this, factory).get(SelectSourceViewModel.class);
    }

    @Override
    public void setNewsFeedFragment(String source) {
        if (hasHost()) {
            getFragmentHost().proceedSelectSourceScreenToNewsFeedScreen(source);
        }
    }

    @Override
    public void setItems(List<SourceItem> list) {
        selectSourceAdapter.submitList(list);
    }
}

package playduin.newsfeed.ui.newsitem;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import playduin.newsfeed.R;
import playduin.newsfeed.databinding.NewsitemFragmentBinding;
import playduin.newsfeed.ui.CustomWebViewClient;
import playduin.newsfeed.ui.mvi.HostedFragment;
import playduin.newsfeed.ui.newsitem.state.NewsItemScreenEffect;
import playduin.newsfeed.ui.newsitem.state.NewsItemScreenState;

@AndroidEntryPoint
public class NewsItemFragment extends HostedFragment<NewsItemContract.View, NewsItemScreenState, NewsItemScreenEffect, NewsItemContract.ViewModel, NewsItemContract.Host> implements NewsItemContract.View {
    private static final String NEWS_ITEM_URL = "NewsItemURL";

    private NewsitemFragmentBinding binding;

    private String url = "";

    @Override
    protected NewsItemContract.ViewModel createModel() {
        return new ViewModelProvider(this).get(NewsItemViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewsitemFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.getRoot().setWebViewClient(new CustomWebViewClient());
        final WebSettings webSettings = binding.getRoot().getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (getArguments() != null) {
            url = getArguments().getString(NEWS_ITEM_URL);
            binding.getRoot().loadUrl(url);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.newsitem, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.open_in_browser_btn == item.getItemId()) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                Intent chooser = Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(url)), getString(R.string.choose_application));
                try {
                    startActivity(chooser);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), R.string.open_in_browser_error, Toast.LENGTH_LONG).show();
                }
            }
        } else if (R.id.share_newsitem_btn == item.getItemId()) {
            try {
                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_application)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

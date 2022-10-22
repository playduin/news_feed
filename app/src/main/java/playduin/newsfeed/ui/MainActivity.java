package playduin.newsfeed.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import playduin.newsfeed.R;
import playduin.newsfeed.databinding.MainActivityBinding;
import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.ui.network.NetworkErrorDialogFragment;
import playduin.newsfeed.ui.newsfeed.NewsFeedContract;
import playduin.newsfeed.ui.newsitem.NewsItemContract;
import playduin.newsfeed.ui.selectsource.SelectSourceContract;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NewsFeedContract.Host, SelectSourceContract.Host, NewsItemContract.Host, NavigationBarView.OnItemSelectedListener {
    private static final String NEWS_ITEM_URL = "NewsItemURL";
    private static final String NEWS_SOURCE = "NewsSource";

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void proceedNewsFeedScreenToNewsItemScreen(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ITEM_URL, url);
        getNavController().navigate(R.id.action_newsFeedFragment_to_newsItemFragment, bundle);
    }

    public void proceedNewsFeedScreenToSelectSourceScreen() {
        getNavController().navigate(R.id.action_newsFeedFragment_to_selectSourceFragment);
    }

    public void proceedNewsItemScreenToNewsFeedScreen(String source) {
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_SOURCE, source);
        getNavController().navigate(R.id.action_newsItemFragment_to_newsFeedFragment, bundle);
    }

    public void proceedNewsItemScreenToSelectSourceScreen() {
        getNavController().navigate(R.id.action_newsItemFragment_to_selectSourceFragment);
    }

    public void proceedSelectSourceScreenToNewsFeedScreen(String source) {
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_SOURCE, source);
        getNavController().navigate(R.id.action_selectSourceFragment_to_newsFeedFragment, bundle);
    }

    public void proceedNewsFeedScreenToNewsFeedScreen(String source) {
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_SOURCE, source);
        getNavController().navigate(R.id.action_newsFeedFragment_to_newsFeedFragment, bundle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            int screen = Objects.requireNonNull(getNavController().getCurrentDestination()).getId();
            if (R.id.action_all == item.getItemId()) {
                if (R.id.newsItemFragment == screen) {
                    proceedNewsItemScreenToNewsFeedScreen(NewsFeedRepo.ALL);
                } else if (R.id.selectSourceFragment == screen) {
                    proceedSelectSourceScreenToNewsFeedScreen(NewsFeedRepo.ALL);
                } else if (R.id.newsFeedFragment == screen) {
                    proceedNewsFeedScreenToNewsFeedScreen(NewsFeedRepo.ALL);
                }
            } else if (R.id.action_groups == item.getItemId()) {
                if (R.id.newsItemFragment == screen) {
                    proceedNewsItemScreenToSelectSourceScreen();
                } else if (R.id.newsFeedFragment == screen) {
                    proceedNewsFeedScreenToSelectSourceScreen();
                }
            } else if (R.id.action_saved == item.getItemId()) {
                if (R.id.newsItemFragment == screen) {
                    proceedNewsItemScreenToNewsFeedScreen(NewsFeedRepo.SAVED);
                } else if (R.id.selectSourceFragment == screen) {
                    proceedSelectSourceScreenToNewsFeedScreen(NewsFeedRepo.SAVED);
                } else if (R.id.newsFeedFragment == screen) {
                    proceedNewsFeedScreenToNewsFeedScreen(NewsFeedRepo.SAVED);
                }
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public void showNetworkErrorDialog() {
        new NetworkErrorDialogFragment().show(getSupportFragmentManager(), NetworkErrorDialogFragment.TAG);
    }

    private NavController getNavController() {
        return Navigation.findNavController(this, R.id.nav_host_fragment);
    }
}

package playduin.newsfeed.models.news.network.first;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import playduin.newsfeed.models.news.NewsItem;

public interface FirstSourceNetwork {
    Single<List<NewsItem>> getNews();
}

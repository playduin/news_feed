package playduin.newsfeed.models.news.network.second;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import playduin.newsfeed.models.news.NewsItem;

public interface SecondSourceNetwork {
    Single<List<NewsItem>> getNews();
}

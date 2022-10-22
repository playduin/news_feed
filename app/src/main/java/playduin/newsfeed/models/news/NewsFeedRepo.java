package playduin.newsfeed.models.news;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface NewsFeedRepo {
    String ALL = "*";
    String SAVED = "saved";
    String FIRST_SOURCE = "First source";
    String SECOND_SOURCE = "Second source";

    List<SourceItem> getSources();

    Single<List<NewsItem>> sync();

    Single<List<NewsItem>> getNews(String source);

    Completable saveNewsItem(String url);

    Completable unsaveNewsItem(String url);
}

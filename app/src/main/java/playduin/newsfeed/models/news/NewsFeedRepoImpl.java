package playduin.newsfeed.models.news;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import playduin.newsfeed.models.news.dao.NewsItemDao;
import playduin.newsfeed.models.news.network.first.FirstSourceNetwork;
import playduin.newsfeed.models.news.network.second.SecondSourceNetwork;

public class NewsFeedRepoImpl implements NewsFeedRepo {
    private final FirstSourceNetwork firstSourceNetwork;
    private final SecondSourceNetwork secondSourceNetwork;
    private final NewsItemDao newsItemDao;

    public NewsFeedRepoImpl(FirstSourceNetwork firstSourceNetwork, SecondSourceNetwork secondSourceNetwork, NewsItemDao newsItemDao) {
        this.firstSourceNetwork = firstSourceNetwork;
        this.secondSourceNetwork = secondSourceNetwork;
        this.newsItemDao = newsItemDao;
    }

    @Override
    public List<SourceItem> getSources() {
        final List<SourceItem> sourceItems = new ArrayList<>();
        sourceItems.add(new SourceItem(NewsFeedRepo.FIRST_SOURCE, NewsFeedRepo.FIRST_SOURCE));
        sourceItems.add(new SourceItem(NewsFeedRepo.SECOND_SOURCE, NewsFeedRepo.SECOND_SOURCE));
        return sourceItems;
    }

    @Override
    public Single<List<NewsItem>> sync() {
        return Single.zip(firstSourceNetwork.getNews().map(news -> {
            newsItemDao.insert(news);
            return news;
        }), secondSourceNetwork.getNews().map(news -> {
            newsItemDao.insert(news);
            return news;
        }), (a, b) -> {
            List<NewsItem> c = new ArrayList<>();
            c.addAll(a);
            c.addAll(b);
            return c;
        });
    }

    @Override
    public Single<List<NewsItem>> getNews(String source) {
        return Single.fromCallable(() -> {
            if (NewsFeedRepo.ALL.equals(source)) {
                return newsItemDao.getAll();
            } else if (NewsFeedRepo.SAVED.equals(source)) {
                return newsItemDao.getSaved();
            } else {
                return newsItemDao.getAllFromSource(source);
            }
        });
    }

    @Override
    public Completable saveNewsItem(String url) {
        return Completable.fromCallable(() -> {
            newsItemDao.updateSaved(url, true);
            return Completable.complete();
        });
    }

    @Override
    public Completable unsaveNewsItem(String url) {
        return Completable.fromCallable(() -> {
            newsItemDao.updateSaved(url, false);
            return Completable.complete();
        });
    }
}

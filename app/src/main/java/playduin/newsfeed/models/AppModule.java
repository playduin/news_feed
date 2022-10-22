package playduin.newsfeed.models;

import android.content.Context;

import dagger.hilt.android.qualifiers.ApplicationContext;
import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.models.news.dao.NewsItemDao;
import playduin.newsfeed.models.news.network.first.FirstSourceNetwork;
import playduin.newsfeed.models.news.network.second.SecondSourceNetwork;

public interface AppModule {
    FirstSourceNetwork getFirstSourceNetwork();

    SecondSourceNetwork getSecondSourceNetwork();

    NewsItemDao getNewsItemDao(@ApplicationContext Context ctx);

    NewsFeedRepo getNewsFeedRepo(FirstSourceNetwork firstSourceNetwork, SecondSourceNetwork secondSourceNetwork, NewsItemDao newsItemDao);
}

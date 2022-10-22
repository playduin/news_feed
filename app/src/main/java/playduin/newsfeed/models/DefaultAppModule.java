package playduin.newsfeed.models;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.models.news.NewsFeedRepoImpl;
import playduin.newsfeed.models.news.dao.AppDatabase;
import playduin.newsfeed.models.news.dao.NewsItemDao;
import playduin.newsfeed.models.news.network.first.FirstSourceNetwork;
import playduin.newsfeed.models.news.network.first.FirstSourceNetworkImpl;
import playduin.newsfeed.models.news.network.second.SecondSourceNetwork;
import playduin.newsfeed.models.news.network.second.SecondSourceNetworkImpl;

@Module
@InstallIn(SingletonComponent.class)
public class DefaultAppModule implements AppModule {

    public DefaultAppModule() {

    }

    @Provides
    @Singleton
    public FirstSourceNetwork getFirstSourceNetwork() {
        return new FirstSourceNetworkImpl();
    }

    @Provides
    @Singleton
    public SecondSourceNetwork getSecondSourceNetwork() {
        return new SecondSourceNetworkImpl();
    }

    @Provides
    @Singleton
    public NewsItemDao getNewsItemDao(@ApplicationContext Context ctx) {
        return Room.databaseBuilder(ctx, AppDatabase.class, "news_items")
                .build().newsItemDao();
    }

    @Provides
    @Singleton
    public NewsFeedRepo getNewsFeedRepo(FirstSourceNetwork firstSourceNetwork, SecondSourceNetwork secondSourceNetwork, NewsItemDao newsItemDao) {
        return new NewsFeedRepoImpl(firstSourceNetwork, secondSourceNetwork, newsItemDao);
    }
}

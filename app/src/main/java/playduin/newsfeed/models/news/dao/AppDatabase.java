package playduin.newsfeed.models.news.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import playduin.newsfeed.models.news.NewsItem;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsItemDao newsItemDao();
}

package playduin.newsfeed.models.news.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import playduin.newsfeed.models.news.NewsItem;

@Dao
public interface NewsItemDao {
    @Query("SELECT * FROM newsitem ORDER BY time DESC")
    List<NewsItem> getAll();

    @Query("SELECT * FROM newsitem WHERE saved = 1 ORDER BY time DESC")
    List<NewsItem> getSaved();

    @Query("SELECT * FROM newsitem WHERE source = :source ORDER BY time DESC")
    List<NewsItem> getAllFromSource(String source);

    @Query("SELECT *, MAX(time) FROM newsitem")
    List<NewsItem> getLastNewsItem();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    void insert(NewsItem newsItem);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    void insert(List<NewsItem> newsItemList);

    @Query("UPDATE newsitem SET saved = :saved WHERE url = :url")
    void updateSaved(String url, boolean saved);

    @Transaction
    @Query("delete from newsitem")
    void deleteAll();
}

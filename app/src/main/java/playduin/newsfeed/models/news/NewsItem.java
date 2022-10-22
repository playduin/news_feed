package playduin.newsfeed.models.news;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsItem {

    @ColumnInfo(name = "time")
    public final long time;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "source")
    public final String source;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "url")
    public final String url;

    @ColumnInfo(name = "saved")
    public final boolean saved;

    public NewsItem(long time, String title, String source, @NonNull String url, boolean saved) {
        this.time = time;
        this.title = title;
        this.source = source;
        this.url = url;
        this.saved = saved;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        NewsItem newsItem = (NewsItem) obj;
        return newsItem.url.equals(url);
    }
}

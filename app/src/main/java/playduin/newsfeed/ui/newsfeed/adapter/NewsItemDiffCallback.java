package playduin.newsfeed.ui.newsfeed.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import playduin.newsfeed.models.news.NewsItem;

public class NewsItemDiffCallback extends DiffUtil.ItemCallback<NewsItem> {
    public boolean areItemsTheSame(NewsItem oldItem, @NonNull NewsItem newItem) {
        return oldItem.url.equals(newItem.url);
    }

    @SuppressLint("DiffUtilEquals")
    public boolean areContentsTheSame(NewsItem oldItem, @NonNull NewsItem newItem) {
        return oldItem.equals(newItem);
    }
}

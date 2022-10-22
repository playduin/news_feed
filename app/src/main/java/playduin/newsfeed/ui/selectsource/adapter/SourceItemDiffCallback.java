package playduin.newsfeed.ui.selectsource.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import playduin.newsfeed.models.news.SourceItem;

public class SourceItemDiffCallback extends DiffUtil.ItemCallback<SourceItem> {

    @Override
    public boolean areItemsTheSame(@NonNull SourceItem oldItem, @NonNull SourceItem newItem) {
        return oldItem.getSource().equals(newItem.getSource());
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull SourceItem oldItem, @NonNull SourceItem newItem) {
        return oldItem.equals(newItem);
    }
}

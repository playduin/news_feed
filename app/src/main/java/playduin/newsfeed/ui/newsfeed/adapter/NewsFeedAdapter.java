package playduin.newsfeed.ui.newsfeed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.rxjava3.subjects.PublishSubject;
import playduin.newsfeed.R;
import playduin.newsfeed.databinding.NewsfeedItemBinding;
import playduin.newsfeed.models.news.NewsItem;
import playduin.newsfeed.util.UserDateTime;

public class NewsFeedAdapter extends ListAdapter<NewsItem, NewsFeedAdapter.ViewHolderItem> {
    private final PublishSubject<NewsFeedEvent> eventsSubject = PublishSubject.create();

    public NewsFeedAdapter() {
        super(new NewsItemDiffCallback());
    }

    @Override
    @NonNull
    public NewsFeedAdapter.ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfeed_item, parent, false), eventsSubject);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        holder.bindItem(getItem(position));
    }

    public PublishSubject<NewsFeedEvent> getEventsSubject() {
        return eventsSubject;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final NewsfeedItemBinding binding;
        private final PublishSubject<NewsFeedEvent> eventsSubject;

        private NewsItem newsItem;

        public ViewHolderItem(View v, PublishSubject<NewsFeedEvent> eventsSubject) {
            super(v);
            binding = NewsfeedItemBinding.bind(v);
            this.eventsSubject = eventsSubject;

            binding.getRoot().setOnClickListener(this);
            binding.newsItemSaveBtn.setOnClickListener(this);
        }

        public void bindItem(NewsItem item) {
            newsItem = item;
            binding.newsItemSource.setText(item.source);
            binding.newsItemText.setText(item.title);
            binding.newsItemTime.setText(UserDateTime.getUserDateTime(item.time));
            binding.newsItemSaveBtn.setBackground(AppCompatResources.getDrawable(binding.getRoot().getContext(), item.saved ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_border));
        }

        @Override
        public void onClick(View v) {
            if (R.id.news_item_save_btn == v.getId()) {
                if (newsItem.saved) {
                    binding.newsItemSaveBtn.setBackground(AppCompatResources.getDrawable(binding.getRoot().getContext(), R.drawable.ic_bookmark_border));
                    eventsSubject.onNext(NewsFeedEvent.createUnsaveNewsItemEvent(newsItem.url));
                } else {
                    binding.newsItemSaveBtn.setBackground(AppCompatResources.getDrawable(binding.getRoot().getContext(), R.drawable.ic_bookmark));
                    eventsSubject.onNext(NewsFeedEvent.createSaveNewsItemEvent(newsItem.url));
                }
            } else {
                eventsSubject.onNext(NewsFeedEvent.createOpenNewsItemEvent(newsItem.url));
            }
        }
    }
}

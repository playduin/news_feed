package playduin.newsfeed.ui.selectsource.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.rxjava3.subjects.PublishSubject;
import playduin.newsfeed.R;
import playduin.newsfeed.databinding.SourceItemBinding;
import playduin.newsfeed.models.news.SourceItem;

public class SelectSourceAdapter extends ListAdapter<SourceItem, SelectSourceAdapter.ViewHolderItem> {
    private final PublishSubject<SelectSourceEvent> eventsSubject = PublishSubject.create();

    public SelectSourceAdapter() {
        super(new SourceItemDiffCallback());
    }

    @Override
    @NonNull
    public SelectSourceAdapter.ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectSourceAdapter.ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.source_item, parent, false), eventsSubject);
    }

    @Override
    public void onBindViewHolder(SelectSourceAdapter.ViewHolderItem holder, int position) {
        holder.bindItem(getItem(position));
    }

    public PublishSubject<SelectSourceEvent> getEventsSubject() {
        return eventsSubject;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final SourceItemBinding binding;
        private final PublishSubject<SelectSourceEvent> eventsSubject;

        private SourceItem sourceItem;

        public ViewHolderItem(View v, PublishSubject<SelectSourceEvent> eventsSubject) {
            super(v);
            binding = SourceItemBinding.bind(v);
            this.eventsSubject = eventsSubject;

            binding.getRoot().setOnClickListener(this);
        }

        public void bindItem(SourceItem item) {
            sourceItem = item;
            binding.sourceItemText.setText(item.getDisplayName());
        }

        @Override
        public void onClick(View v) {
            eventsSubject.onNext(SelectSourceEvent.createOpenNewsSourceEvent(sourceItem.getSource()));
        }
    }
}

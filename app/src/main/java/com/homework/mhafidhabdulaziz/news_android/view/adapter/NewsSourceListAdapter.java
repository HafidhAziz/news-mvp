package com.homework.mhafidhabdulaziz.news_android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homework.mhafidhabdulaziz.news_android.R;
import com.homework.mhafidhabdulaziz.news_android.model.entity.SourcesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public class NewsSourceListAdapter extends RecyclerView.Adapter<NewsSourceListAdapter.ViewHolder> {

    private NewsSourceListListener newsSourceListListener;
    private List<SourcesItem> mData;
    private Context mContext;


    public NewsSourceListAdapter(Context ctx) {
        mData = new ArrayList<>();
        mContext = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleText;
        SourcesItem newsSourceItem;

        public void setNewsSourceItem(SourcesItem newsSourceItem) {
            this.newsSourceItem = newsSourceItem;
        }

        public ViewHolder(View v, final NewsSourceListListener newsSourceListListener) {
            super(v);

            mTitleText = v.findViewById(R.id.news_source_title);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsSourceListListener.onNewsClicked(getAdapterPosition(), newsSourceItem.getId());
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_source_item_layout, parent, false);
        return new ViewHolder(v, newsSourceListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        SourcesItem newsSourceItem = mData.get(position);

        viewHolder.setNewsSourceItem(newsSourceItem);

        viewHolder.mTitleText.setText(newsSourceItem.getName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(NewsSourceListListener listener) {
        newsSourceListListener = listener;
    }

    public void setData(List<SourcesItem> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public interface NewsSourceListListener {
        void onNewsClicked(int position, String url);
    }
}

package com.homework.mhafidhabdulaziz.news_android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.homework.mhafidhabdulaziz.news_android.R;
import com.homework.mhafidhabdulaziz.news_android.model.entity.ArticlesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public class NewsArticlesListAdapter extends RecyclerView.Adapter<NewsArticlesListAdapter.ViewHolder>  {
    private NewsListListener newsListListener;
    private List<ArticlesItem> mData;
    private Context mContext;


    public NewsArticlesListAdapter(Context ctx) {
        mData = new ArrayList<>();
        mContext = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleText, mDescriptionText;
        ImageView mImage;
        ArticlesItem mArticlesItem;

        public void setmArticlesItem(ArticlesItem articlesItem) {
            this.mArticlesItem = articlesItem;
        }

        public ViewHolder(View v, final NewsListListener newsListListener) {
            super(v);

            mImage = v.findViewById(R.id.news_image);
            mTitleText = v.findViewById(R.id.news_title);
            mDescriptionText = v.findViewById(R.id.news_desc);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsListListener.onNewsClicked(getAdapterPosition(), mArticlesItem.getUrl());
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(v, newsListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        ArticlesItem articlesItem = mData.get(position);

        viewHolder.setmArticlesItem(articlesItem);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(mContext).load(articlesItem.getUrlToImage()).apply(options).into(viewHolder.mImage);

        viewHolder.mTitleText.setText(articlesItem.getTitle());
        viewHolder.mDescriptionText.setText(articlesItem.getDescription());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(NewsListListener listener) {
        newsListListener = listener;
    }

    public void setData(List<ArticlesItem> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public interface NewsListListener {
        void onNewsClicked(int position, String url);
    }
}

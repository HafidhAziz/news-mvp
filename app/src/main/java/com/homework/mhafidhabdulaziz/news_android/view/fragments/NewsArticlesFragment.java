package com.homework.mhafidhabdulaziz.news_android.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.homework.mhafidhabdulaziz.news_android.R;
import com.homework.mhafidhabdulaziz.news_android.common.Constants;
import com.homework.mhafidhabdulaziz.news_android.model.entity.NewsItem;
import com.homework.mhafidhabdulaziz.news_android.presenter.main.NewsArticlePresenter;
import com.homework.mhafidhabdulaziz.news_android.presenter.main.NewsArticleView;
import com.homework.mhafidhabdulaziz.news_android.view.adapter.NewsArticlesListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public class NewsArticlesFragment extends Fragment implements NewsArticleView {

    private NewsArticlePresenter presenter;
    @BindView(R.id.news_article_list)
    public RecyclerView mList;
    @BindView(R.id.shimmer_article_view_container)
    public ShimmerFrameLayout mShimmerFrameLayout;
    public NewsArticlesListAdapter mAdapter;
    public Bundle bundle;

    public static Fragment newInstance() {
        Fragment frag = new NewsArticlesFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_article_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getActivity());

        presenter = new NewsArticlePresenter(getActivity().getApplication());
        onAttachView();


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                mLinearLayoutManager.getOrientation());
        mList.addItemDecoration(dividerItemDecoration);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new NewsArticlesListAdapter(getActivity());
        mList.setLayoutManager(mLinearLayoutManager);
        mList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NewsArticlesListAdapter.NewsListListener() {
            @Override
            public void onNewsClicked(int position, String url) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.URL, url);

                Fragment fragment = WebViewFragment.newInstance();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_news_article_main_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public void onAttachView() {
        mShimmerFrameLayout.startShimmer();
        mShimmerFrameLayout.setVisibility(View.VISIBLE);
        mList.setVisibility(View.GONE);
        presenter.onAttach(this);
        presenter.requestNewsArticleData(bundle.getString(Constants.ID));
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void onNewsArticlesReceived(String response) {
        mShimmerFrameLayout.stopShimmer();
        mShimmerFrameLayout.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
        NewsItem newsItem = (new Gson()).fromJson(response, NewsItem.class);
        mAdapter.setData(newsItem.getArticles());
    }

    @Override
    public void onDestroy() {
        mShimmerFrameLayout.stopShimmer();
        onDetachView();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerFrameLayout.stopShimmer();
    }
}

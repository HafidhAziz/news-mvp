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
import com.homework.mhafidhabdulaziz.news_android.model.entity.NewsSources;
import com.homework.mhafidhabdulaziz.news_android.model.entity.SourcesItem;
import com.homework.mhafidhabdulaziz.news_android.presenter.main.NewsSourcePresenter;
import com.homework.mhafidhabdulaziz.news_android.presenter.main.NewsSourceView;
import com.homework.mhafidhabdulaziz.news_android.view.adapter.NewsSourceListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public class NewsSourceFragment extends Fragment implements NewsSourceView{

    private NewsSourcePresenter presenter;
    @BindView(R.id.news_source_list)
    public RecyclerView mList;
    @BindView(R.id.shimmer_view_container)
    public ShimmerFrameLayout mShimmerFrameLayout;
    public NewsSourceListAdapter mAdapter;


    public static Fragment newInstance() {
        Fragment frag = new NewsSourceFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_source_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getActivity());

        presenter = new NewsSourcePresenter(getActivity().getApplication());
        onAttachView();


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                mLinearLayoutManager.getOrientation());
        mList.addItemDecoration(dividerItemDecoration);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new NewsSourceListAdapter(getActivity());
        mList.setLayoutManager(mLinearLayoutManager);
        mList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NewsSourceListAdapter.NewsSourceListListener() {
            @Override
            public void onNewsClicked(int position, String id) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, id);

                Fragment fragment = NewsArticlesFragment.newInstance();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_news_source_main_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttachView() {
        mShimmerFrameLayout.startShimmer();
        mShimmerFrameLayout.setVisibility(View.VISIBLE);
        mList.setVisibility(View.GONE);
        presenter.onAttach(this);
        presenter.requestNewsSources();

    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
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

    @Override
    public void onNewsSourceReceived(String response) {
        mShimmerFrameLayout.stopShimmer();
        mShimmerFrameLayout.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
        NewsSources newsSource = (new Gson()).fromJson(response, NewsSources.class);
        mAdapter.setData(newsSource.getSources());
    }
}

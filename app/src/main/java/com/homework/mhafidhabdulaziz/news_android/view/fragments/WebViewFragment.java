package com.homework.mhafidhabdulaziz.news_android.view.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.homework.mhafidhabdulaziz.news_android.R;
import com.homework.mhafidhabdulaziz.news_android.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public class WebViewFragment extends Fragment{

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.shimmer_view_container_article)
    public ShimmerFrameLayout mShimmerFrameLayout;
    private Bundle bundle;

    public static Fragment newInstance() {
        Fragment frag = new WebViewFragment();
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
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getActivity());
        loadWeb();
    }

    private void loadWeb() {
        String articleUrl =  bundle.getString(Constants.URL);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mShimmerFrameLayout.startShimmer();
                mShimmerFrameLayout.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                view.setVisibility(View.VISIBLE);
                mShimmerFrameLayout.stopShimmer();
                mShimmerFrameLayout.setVisibility(View.GONE);            }
        });

        mWebView.loadUrl(articleUrl);
    }

    @Override
    public void onDestroy() {
        mShimmerFrameLayout.stopShimmer();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerFrameLayout.stopShimmer();
    }

}

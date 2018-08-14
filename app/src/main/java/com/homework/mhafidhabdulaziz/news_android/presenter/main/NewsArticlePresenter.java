package com.homework.mhafidhabdulaziz.news_android.presenter.main;

import android.app.Application;
import android.util.Log;

import com.homework.mhafidhabdulaziz.news_android.common.Constants;
import com.homework.mhafidhabdulaziz.news_android.common.NewsApplication;
import com.homework.mhafidhabdulaziz.news_android.model.service.NewsService;
import com.homework.mhafidhabdulaziz.news_android.presenter.base.Presenter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public class NewsArticlePresenter implements Presenter<NewsArticleView> {
    @Inject
    Retrofit retrofit;
    private NewsArticleView newsArticleView;

    public NewsArticlePresenter(Application application){
        ((NewsApplication) application).getNetComponent().inject(this);
    }

    @Override
    public void onAttach(NewsArticleView view) {
        newsArticleView = view;
    }

    @Override
    public void onDetach() {
        newsArticleView = null;
    }

    public void requestNewsArticleData(String id){
        makeAPICall(id);
    }

    private void makeAPICall(String id) {
        Call<String> request = retrofit.create(NewsService.class).newsArticlesData(id,Constants.API_KEY);

        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("TOKOPEDIA_TEST", response.body());
                if(newsArticleView != null){
                    newsArticleView.onNewsArticlesReceived(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("TOKOPEDIA_TEST", t.getLocalizedMessage());
            }
        });
    }
}

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

public class NewsSourcePresenter implements Presenter<NewsSourceView> {
    @Inject
    Retrofit retrofit;
    private NewsSourceView newsSourceView;

    public NewsSourcePresenter(Application application) {
        ((NewsApplication) application).getNetComponent().inject(this);
    }

    @Override
    public void onAttach(final NewsSourceView view) {
        newsSourceView = view;
    }

    @Override
    public void onDetach() {
        newsSourceView = null;
    }

    public void requestNewsSources() {
        makeAPICall();
    }

    private void makeAPICall() {
        Call<String> request = retrofit.create(NewsService.class).sourcesNewsData(Constants.API_KEY);

        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("TOKOPEDIA_TEST", response.body());
                if(newsSourceView != null){
                    newsSourceView.onNewsSourceReceived(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("TOKOPEDIA_TEST", t.getLocalizedMessage());
            }
        });
    }
}

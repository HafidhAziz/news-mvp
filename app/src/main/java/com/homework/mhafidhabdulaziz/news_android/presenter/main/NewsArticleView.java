package com.homework.mhafidhabdulaziz.news_android.presenter.main;

import com.homework.mhafidhabdulaziz.news_android.presenter.base.View;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public interface NewsArticleView extends View {
    void onNewsArticlesReceived(String response);
}

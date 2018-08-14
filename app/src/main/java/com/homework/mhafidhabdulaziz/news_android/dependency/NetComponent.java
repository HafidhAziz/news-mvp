package com.homework.mhafidhabdulaziz.news_android.dependency;

import com.homework.mhafidhabdulaziz.news_android.presenter.main.NewsArticlePresenter;
import com.homework.mhafidhabdulaziz.news_android.presenter.main.NewsSourcePresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(NewsSourcePresenter newsSourcePresenter);
    void inject(NewsArticlePresenter newsArticlePresenter);
}

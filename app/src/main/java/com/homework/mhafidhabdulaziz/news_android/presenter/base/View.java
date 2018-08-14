package com.homework.mhafidhabdulaziz.news_android.presenter.base;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public interface View<T extends Presenter> {
    void onAttachView();

    void onDetachView();
}

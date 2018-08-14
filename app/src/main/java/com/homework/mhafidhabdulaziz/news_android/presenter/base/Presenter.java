package com.homework.mhafidhabdulaziz.news_android.presenter.base;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public interface Presenter<T extends View>  {
    void onAttach(T view);

    void onDetach();
}

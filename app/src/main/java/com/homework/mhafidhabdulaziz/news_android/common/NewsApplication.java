package com.homework.mhafidhabdulaziz.news_android.common;

import android.app.Application;

import com.homework.mhafidhabdulaziz.news_android.dependency.AppModule;
import com.homework.mhafidhabdulaziz.news_android.dependency.DaggerNetComponent;
import com.homework.mhafidhabdulaziz.news_android.dependency.NetComponent;
import com.homework.mhafidhabdulaziz.news_android.dependency.NetModule;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public class NewsApplication extends Application {

    private static NewsApplication instance;
    public static NewsApplication get() { return instance; }
    private NetComponent netComponent;

    @Override public void onCreate() {
        super.onCreate();
        instance = this;

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Constants.SERVICE_BASE_URL))
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}

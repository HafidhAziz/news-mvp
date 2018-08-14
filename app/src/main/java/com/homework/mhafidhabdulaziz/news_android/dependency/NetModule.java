package com.homework.mhafidhabdulaziz.news_android.dependency;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

@Module
public class NetModule {
    String mBaseUrl;

    public NetModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {

        return new Retrofit
                .Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(mBaseUrl)
                .build();
    }
}

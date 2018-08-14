package com.homework.mhafidhabdulaziz.news_android.model.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

public interface NewsService {

    @GET("sources")
    Call<String> sourcesNewsData(@Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<String> newsArticlesData(@Query("sources") String sources,
                          @Query("apiKey") String apiKey);
}

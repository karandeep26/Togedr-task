package com.proj.togedr_task;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karan on 23/4/17.
 */

public class RestClient {
    private static Retrofit retrofit;
    private static RestClient restClient;
    private RestClient(){
        retrofit= new Retrofit.Builder().baseUrl("http://api.androidhive.info")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static RestClient getRestClient(){
        if (restClient==null){
            restClient=new RestClient();
        }
        return restClient;
    }
    public Retrofit getRetrofitInstance(){
        return retrofit;
    }
}

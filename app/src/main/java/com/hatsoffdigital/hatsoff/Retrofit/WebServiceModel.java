package com.hatsoffdigital.hatsoff.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceModel {

    public static WebServices getRestApi() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hatsoffdigital.in/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        WebServices restApis = retrofit.create(WebServices.class);
        return restApis;

    }
}

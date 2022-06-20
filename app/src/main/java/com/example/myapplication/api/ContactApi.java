package com.example.myapplication.api;

import com.example.myapplication.AppDb;
import com.example.myapplication.MyApplication;
import com.example.myapplication.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi {
    public WesServiceAPI getWesServiceAPI() {
        return wesServiceAPI;
    }

    WesServiceAPI wesServiceAPI;
    Retrofit retrofit;
    String contactBaseUrl;

    public ContactApi(String ContactBaseUrl) {
        contactBaseUrl = ContactBaseUrl;
        retrofit = new Retrofit.Builder().baseUrl(contactBaseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        wesServiceAPI = retrofit.create(WesServiceAPI.class);
    }
}

package com.example.myapplication.api;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi extends AppCompatActivity {
    WesServiceAPI wesServiceAPI;
    Retrofit retrofit;
    String contactBaseUrl;

    public ContactApi(String ContactBaseUrl) {
        contactBaseUrl = ContactBaseUrl;
        retrofit = new Retrofit.Builder().baseUrl(contactBaseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        wesServiceAPI = retrofit.create(WesServiceAPI.class);
    }

    public WesServiceAPI getWesServiceAPI() {
        return wesServiceAPI;
    }
}

package com.example.myapplication.api;


import com.example.myapplication.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WesServiceAPI {

    @GET("contacts/{user}")
    Call<List<Contact>> getContacts(@Path("user") String user);

    @POST("contacts")
    Call<Void> createContact(@Body Contact contact);

    @DELETE("contacts/{id}")
    Call<Void> deleteContact(@Path("id") int id);
}

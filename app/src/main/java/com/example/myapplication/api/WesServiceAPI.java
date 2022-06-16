package com.example.myapplication.api;


import com.example.myapplication.Contact;
import com.example.myapplication.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WesServiceAPI {

    @GET("contacts")
    Call<List<Contact>> getContacts(@Query("user") String user);

    @POST("contacts")
    Call<Void> createContact(@Body Contact contact);

    @DELETE("contacts/{id}")
    Call<Void> deleteContact(@Path("id") int id);

    @POST("login")
    Call<User> postActiveUser(@Query("userName") String userName, @Query("password") String password);

    @POST("register")
    Call<User> postActiveUserFromRegister(@Query("userName") String userName, @Query("nickName") String nickName, @Query("pwd") String pwd);
}

package com.example.myapplication.api;


import com.example.myapplication.Contact;
import com.example.myapplication.Message;
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

    @POST("contacts")
    Call<Void> postNewContact(@Query("user") String user, @Query("id") String id, @Query("name") String name, @Query("server") String server);

    @GET("contacts/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Query("user") String user);

    @POST("contacts/{user}/{id}/messages")
    Call<Void> postNewMessage(@Path("user") String user, @Path("id") String id, @Query("content") String content);
}

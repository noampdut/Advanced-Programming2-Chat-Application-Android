package com.example.myapplication.api;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Contact;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {


    private MutableLiveData<List<Contact>> contactListData;
    // private ContactDao contactDao;
    Retrofit retrofit;
    WesServiceAPI wesServiceAPI;
    private User activeUser;


    public ContactAPI(User activeUser) {
        this.activeUser = activeUser;
        retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        wesServiceAPI = retrofit.create(WesServiceAPI.class);
    }


    public void get() {
        Call<List<Contact>> call = wesServiceAPI.getContacts(activeUser.getId());
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> updateContacts = response.body();
                System.out.println(updateContacts.size());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                System.out.println("Fail to request");

            }
        });


    }

}

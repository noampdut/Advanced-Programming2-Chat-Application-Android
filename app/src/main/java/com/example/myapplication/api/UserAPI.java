package com.example.myapplication.api;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AppDb;
import com.example.myapplication.Contact;
import com.example.myapplication.ContactDao;
import com.example.myapplication.Message;
import com.example.myapplication.MessagesDao;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.User;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI extends AppCompatActivity implements Serializable {
    AppDb db;
    ContactDao contactDao;
    MessagesDao messagesDao;
    Retrofit retrofit;

    public WesServiceAPI getWesServiceAPI() {
        return wesServiceAPI;
    }

    WesServiceAPI wesServiceAPI;
    private User activeUser;

    public UserAPI() {
        //Context context = this.getApplicationContext();
        db = AppDb.getDb(this);
        this.contactDao = db.contactDao();
        this.messagesDao = db.messagesDao();
        //this.activeUser = activeUser;
        retrofit = new Retrofit.Builder().baseUrl(MyApplication.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create()).build();
        wesServiceAPI = retrofit.create(WesServiceAPI.class);
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public void getContacts() {
        Call<List<Contact>> call = wesServiceAPI.getContacts(activeUser.getUserName());
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> updateContacts = response.body();
                updatedContactsFun(updateContacts);
                //System.out.println(updateContacts.size());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                //System.out.println("Fail to request");
            }
        });
    }
    public void updatedMessagedFun(List<Message> updateMessages, String contactID) {
        int size = messagesDao.get(contactID).size();
        for (int i = size; i < updateMessages.size(); i++) {
            Message message = updateMessages.get(i);
            messagesDao.insert(message);
        }
    }

    public void updatedContactsFun(List<Contact> updateContacts) {
        //int size = contactDao.index().size();
        List<Contact> temp = contactDao.index();
        for (int j = 0; j < temp.size(); j++) {
            Contact contact = temp.get(j);
            if (!updateContacts.contains(contact)) {
                contactDao.delete(contact);
            }
        }
        for(int i = 0; i < updateContacts.size(); i++){
            Contact contact = updateContacts.get(i);
            if (contactDao.get(contact.getId()) == null){
                contactDao.insert(contact);
            }
        }

    }

  /*  public void postActiveUser(String userName, String password){
        //LoginRequest loginRequest = new LoginRequest(userName, password);
        Call<User> call = wesServiceAPI.postActiveUser(userName, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int returnValue = response.code();
                //String returnType = response.headers().get("content-type");
                if (returnValue != 404) {
                    User user = response.body();
                    setActiveUser(new User(user.getUserName(), user.getNickName(),user.getContacts()));
                  //  activeUser = new User(user.getUserName(), user.getNickName(),user.getContacts());
                    updatedContactsFun(activeUser.getContacts());
                } else {
                    activeUser = null;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //System.out.println("Fail to request");
                //activeUser = null;
            }
        });
    }*/
}

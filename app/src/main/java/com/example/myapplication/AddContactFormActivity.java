package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.ContactApi;
import com.example.myapplication.api.UserAPI;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactFormActivity extends AppCompatActivity {

    private AppDb db;
    private ContactDao contactDao;
    private User activeUser;
    private UserAPI userAPI;
    private ContactApi contactApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_form);

        db = AppDb.getDb(this);
//        db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "contactsDB")
//                .allowMainThreadQueries().build();
        contactDao = db.contactDao();

        userAPI = new UserAPI();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (User)intent.getSerializableExtra("activeUser");
            //contacts = activeUser.getContacts();
            //userAPI.updatedContactsFun(contacts);
        }


        Button btnSaveNewContact = findViewById(R.id.btnSaveNewContact);
        btnSaveNewContact.setOnClickListener(v -> {
            EditText et_userName = findViewById(R.id.et_userName);
            EditText et_nickName = findViewById(R.id.et_nickName);
            EditText et_server = findViewById(R.id.et_server);
            String userName = et_userName.getText().toString();
            String nickName = et_nickName.getText().toString();
            String server = et_server.getText().toString();
            contactApi = new ContactApi("http://10.0.2.2:" + server + "/api/");


            Call<Void> call = userAPI.getWesServiceAPI().postNewContact(activeUser.getUserName(), userName, nickName, server);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    int returnValue = response.code();
                    if (returnValue == 201) {
                        //System.out.println("from response");
                        Call<List<Contact>> second_call = userAPI.getWesServiceAPI().getContacts(activeUser.getUserName());
                        second_call.enqueue(new Callback<List<Contact>>() {
                            @Override
                            public void onResponse(Call<List<Contact>> second_call, Response<List<Contact>> response) {
                                List<Contact> updateContacts = response.body();
                                userAPI.updatedContactsFun(updateContacts);
                                Call<Void> call2 = contactApi.getWesServiceAPI().postInvitations(activeUser.getUserName(),userName, server);
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call2, Response<Void> response) {
                                        int returnValue = response.code();
                                        if (returnValue != 404) {
                                            finish();
                                        }
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call2, Throwable t) {
                                        System.out.println("from faiulre");
                                    }
                                });


                            }

                            @Override
                            public void onFailure(Call<List<Contact>> second_call, Throwable t) {
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("from failure");
                    System.out.println(t);
                }
            });

        });
    }
}
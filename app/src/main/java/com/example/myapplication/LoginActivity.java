package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.UserAPI;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppDb db;
    UserAPI userAPI;
    String token;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDb.getDb(this);
        userAPI = new UserAPI();

        binding.btnToRegister.setOnClickListener(v-> {
            Intent i = new Intent(this,RegisterActivity.class);
            startActivity(i);
        });

        binding.btnLogin.setOnClickListener(v -> {
            EditText et_userName = findViewById(R.id.et_userName_login);
            EditText et_password = findViewById(R.id.et_password_login);
            String userName = et_userName.getText().toString();
            String password = et_password.getText().toString();

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, instanceIdResult -> {
                token = instanceIdResult.getToken();

            });


            Call<User> call = userAPI.getWesServiceAPI().postActiveUser(userName, password);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    int returnValue = response.code();
                    if (returnValue != 404) {
                        User user = response.body();

                        for (int i = 0; i< user.getContacts().size(); i++) {




                            List<Message> messageList = user.getContacts().get(i).getMessageList();
                            Contact contact = db.contactDao().get(user.getContacts().get(i).getId());
                            contact.setMessageList(messageList);
                            db.contactDao().update(user.getContacts().get(i));
                        }

                        Call<Void> call2 = userAPI.getWesServiceAPI().postToken(userName, token);
                        call2.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call2, Response<Void> response) {
                                Intent i = new Intent(LoginActivity.this, ContactsListActivity.class);
                                i.putExtra("activeUser", user);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<Void> call2, Throwable t) {
                            }
                        });
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });

        });
}
}

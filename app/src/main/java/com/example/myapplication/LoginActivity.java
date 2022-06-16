package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.UserAPI;
import com.example.myapplication.databinding.ActivityLoginBinding;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppDb db;
    UserAPI userAPI;
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


            Call<User> call = userAPI.getWesServiceAPI().postActiveUser(userName, password);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    int returnValue = response.code();
                    if (returnValue != 404) {
                        User user = response.body();
                        Intent i = new Intent(LoginActivity.this, ContactsListActivity.class);
                        i.putExtra("activeUser", user);
                        startActivity(i);
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

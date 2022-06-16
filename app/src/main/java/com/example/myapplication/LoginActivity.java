package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.UserAPI;
import com.example.myapplication.databinding.ActivityLoginBinding;

import java.io.Serializable;

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

        binding.btnLogin.setOnClickListener(v ->{
            EditText et_userName = findViewById(R.id.et_userName_login);
            EditText et_password = findViewById(R.id.et_password_login);
            String userName = et_userName.getText().toString();
            String password = et_password.getText().toString();
            //userAPI = new UserAPI();
            userAPI.postActiveUser(userName, password);
            User temp = userAPI.getActiveUser();
            if (userAPI.getActiveUser() != null){
                Intent i = new Intent(this,ContactsListActivity.class);
                i.putExtra("userApi", userAPI);
                startActivity(i);
            }
        });

    }
}

package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.UserAPI;
import com.example.myapplication.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    AppDb db;
    UserAPI userAPI;

    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDb.getDb(this);
        userAPI = new UserAPI();

        binding.registerButton.setOnClickListener(v -> {
            EditText et_userName = findViewById(R.id.et_userName_register);
            EditText et_nickName = findViewById(R.id.et_nickName_register);
            EditText et_password = findViewById(R.id.et_password_register);
            EditText et_validation_password = findViewById(R.id.et_validation_password_register);
            String userName = et_userName.getText().toString();
            String nickName = et_nickName.getText().toString();
            String password = et_password.getText().toString();
            String password2 = et_validation_password.getText().toString();
            if (password.equals(password2)) {

                Call<User> call = userAPI.getWesServiceAPI().postActiveUserFromRegister(userName, nickName, password);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        int returnValue = response.code();
                        if (returnValue != 404) {
                            User user = response.body();
                            Intent i = new Intent(RegisterActivity.this, ContactsListActivity.class);
                            i.putExtra("activeUser", user);
                            startActivity(i);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });
            }

        });
    }
}

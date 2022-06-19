package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnChangeServer.setOnClickListener(v -> {
            EditText et_new_default_server = findViewById(R.id.et_change_server);
            String newUrl = et_new_default_server.getText().toString();
            MyApplication.setBaseUrl(newUrl);
            finish();
        });

    }
}
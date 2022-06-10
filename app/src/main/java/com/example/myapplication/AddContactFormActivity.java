package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddContactFormActivity extends AppCompatActivity {

    private AppDb db;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_form);

        db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "contactsDB")
                .allowMainThreadQueries().build();
        contactDao = db.contactDao();

        Button btnSaveNewContact = findViewById(R.id.btnSaveNewContact);
        btnSaveNewContact.setOnClickListener(v -> {
            EditText et_userName = findViewById(R.id.et_userName);
            EditText et_nickName = findViewById(R.id.et_nickName);
            EditText et_server = findViewById(R.id.et_server);
            Contact contact = new Contact(et_userName.getText().toString(),
                    et_nickName.getText().toString(), et_server.getText().toString());
            contactDao.insert(contact);
            finish();
        });
    }
}
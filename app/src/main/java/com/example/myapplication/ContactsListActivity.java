package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity {
    private AppDb db;
    private ContactDao contactDao;
    private List<Contact> contacts;
    private ArrayAdapter<Contact> adapter;
    private ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

       db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "contactsDB")
              .allowMainThreadQueries().build();
       contactDao = db.contactDao();

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            Intent i = new Intent(this, AddContactFormActivity.class);
            startActivity(i);
        });

        contacts = new ArrayList<>();
        adapter =  new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, contacts);
        lvContacts = findViewById(R.id.lvContacts); ///????
        lvContacts.setAdapter(adapter);
    }
    @Override
    protected void onResume(){
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index());
        //contacts = contactDao.index();
       adapter.notifyDataSetChanged();
    }
}

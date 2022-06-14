package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity implements ContactAdapter.SelectedContact{
    private AppDb db;
    private ContactDao contactDao;
    private List<Contact> contacts = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        db = AppDb.getDb(this);
//       db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "contactsDB")
//              .allowMainThreadQueries().build();
       contactDao = db.contactDao();

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            Intent i = new Intent(this, AddContactFormActivity.class);
            startActivity(i);
        });

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        contactAdapter = new ContactAdapter(contacts,this);
        recyclerView.setAdapter(contactAdapter);

    }
    @Override
    protected void onResume(){
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index());
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void selectedContact(Contact contact) {
        startActivity(new Intent(ContactsListActivity.this, ChatActivity.class).putExtra("data",contact));
    }
}

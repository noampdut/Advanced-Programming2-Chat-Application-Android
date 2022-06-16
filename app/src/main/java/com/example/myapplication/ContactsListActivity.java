package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.api.UserAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity implements ContactAdapter.SelectedContact{
    private AppDb db;
    private ContactDao contactDao;
    private List<Contact> contacts = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;
    UserAPI userAPI;
    User activeUser;
    //private User activeUser = new User("admin","admin",contacts);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        db = AppDb.getDb(this);
//       db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "contactsDB")
//              .allowMainThreadQueries().build();
       contactDao = db.contactDao();
        Intent intent = getIntent();
        userAPI = new UserAPI();

        if (intent.getExtras() != null) {
            activeUser = (User)intent.getSerializableExtra("activeUser");
            contacts = activeUser.getContacts();
            userAPI.updatedContactsFun(contacts);
        }
        //userAPI.getContacts();
        //contacts = contactDao.index();


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

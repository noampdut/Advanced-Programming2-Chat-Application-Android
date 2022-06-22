package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
    BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        db = AppDb.getDb(this);
        contactDao = db.contactDao();
        Intent intent = getIntent();
        userAPI = new UserAPI();
        contactBroadcastReceive();

        if (intent.getExtras() != null) {
            activeUser = (User)intent.getSerializableExtra("activeUser");
            contacts = activeUser.getContacts();

            for (int i =0;i < contacts.size(); i++ ){
                if (contacts.get(i).getServer() != null && contacts.get(i).getServer().contains("localhost:")) {
                    contacts.get(i).setServer(contacts.get(i).getServer().replace("localhost:", ""));
                }
            }
            userAPI.updatedContactsFun(contacts);
        }


        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            Intent i = new Intent(this, AddContactFormActivity.class);
            i.putExtra("activeUser", activeUser);
            startActivity(i);
        });

        FloatingActionButton btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(view ->{
            Intent i = new Intent(this, SettingActivity.class);
            //i.putExtra("activeUser", activeUser);
            startActivity(i);
        });

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        contactAdapter = new ContactAdapter(contacts,this);
        recyclerView.setAdapter(contactAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index());
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void selectedContact(Contact contact) {
        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra("putExtraObject", new putExtraObject(activeUser, contact));
        startActivity(i);
        //startActivity(new Intent(ContactsListActivity.this, ChatActivity.class).putExtra("data",contact));
    }

    private void contactBroadcastReceive() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Contact newContact = (Contact) intent.getSerializableExtra("contact");
                contacts.clear();
                contacts.addAll(contactDao.index());
                recyclerView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();

            }
        };
        LocalBroadcastManager.getInstance(MyApplication.context).registerReceiver(broadcastReceiver, new IntentFilter("newContact"));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        LocalBroadcastManager.getInstance(MyApplication.context).unregisterReceiver(broadcastReceiver); ///check
    }
}

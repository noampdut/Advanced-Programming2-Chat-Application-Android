package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private AppDb db;
    private MessagesDao messagesDao;
    private List<com.example.myapplication.Message> messages;
    private ArrayAdapter<com.example.myapplication.Message> adapter;
    private ListView lvMessages;
    private TextView tvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tvContact = findViewById(R.id.selected_contact);
        Intent intent = getIntent();

        if(intent.getExtras() != null){
            Contact contact = (Contact) intent.getSerializableExtra("data");
            tvContact.setText(contact.getId());

        }

        db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "messagesDB")
                .allowMainThreadQueries().build();
        messagesDao = db.messagesDao();

        /*FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            Intent i = new Intent(this, AddContactFormActivity.class);
            startActivity(i);
        });*/

       // messages = new ArrayList<>();
        //adapter =  new ArrayAdapter<com.example.myapplication.Message>(this, android.R.layout.simple_list_item_1, messages);
        //lvMessages = findViewById(R.id.lvMessages); ///????
        //lvMessages.setAdapter(adapter);
    }
/*    @Override
    protected void onResume(){
        super.onResume();
        messages.clear();
        messages.addAll(messagesDao.get("ofek"));
        adapter.notifyDataSetChanged();
    }*/
}

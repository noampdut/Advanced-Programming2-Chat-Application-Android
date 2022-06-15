package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private AppDb db;
    private MessagesDao messagesDao;
    private ContactDao contactDao;
    private List<Message> messages = new ArrayList<>();
    private MessagesAdapter adapter;
    private RecyclerView rcMessages;
    private TextView tvContact;
    private ActivityChatBinding binding;
    private Contact currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tvContact = findViewById(R.id.selected_contact);
        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            Contact contact = (Contact) intent.getSerializableExtra("data");
            tvContact.setText(contact.getId());
            currentContact = contact;
        }
        db = AppDb.getDb(this);
//        db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "messagesDB")
//                .allowMainThreadQueries().build();
        messagesDao = db.messagesDao();
        contactDao = db.contactDao();
        messages = messagesDao.get(currentContact.getId());

        adapter = new MessagesAdapter(this, messages);
        rcMessages = findViewById(R.id.recycler_view1);
        rcMessages.setLayoutManager(new LinearLayoutManager(this));
        rcMessages.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rcMessages.setAdapter(adapter);

        Button btnSaveNewMessage = findViewById(R.id.btnSendMessage);
        btnSaveNewMessage.setOnClickListener(v -> {
            EditText newMessage = findViewById(R.id.message_box);
            String content = newMessage.getText().toString();
            Message message = new Message( content, currentContact.getId());
            messagesDao.insert(message);
            binding.messageBox.setText("");
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            onResume();
            currentContact.setLast(content);
            currentContact.setLastDate(message.getCreated());
            contactDao.update(currentContact);
        });

    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume(){
        super.onResume();
        messages.clear();
        messages.addAll(messagesDao.get(currentContact.getId()));
        adapter.notifyDataSetChanged();
        rcMessages.setAdapter(adapter);
        //setContentView(R.layout.activity_chat);
        binding.recyclerView1.setVisibility(View.VISIBLE);
    }

}

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

import com.example.myapplication.api.ContactApi;
import com.example.myapplication.api.UserAPI;
import com.example.myapplication.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private AppDb db;
    private MessagesDao messagesDao;
    private ContactDao contactDao;
    private List<Message> messages = new ArrayList<>();
    private MessagesAdapter adapter;
    private RecyclerView rcMessages;
    private TextView tvContact;
    private ActivityChatBinding binding;
    private UserAPI userAPI;
    private Contact currentContact;
    private User activeUser;
    private ContactApi contactApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tvContact = findViewById(R.id.selected_contact);
        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            //User activeUser
            putExtraObject data = (putExtraObject) intent.getSerializableExtra("putExtraObject");
            currentContact = data.currentContact;
            activeUser = data.activeUser;
            //Contact contact = (Contact) intent.getSerializableExtra("putExtraObject");
            tvContact.setText(currentContact.getId());

        }
        db = AppDb.getDb(this);
        userAPI = new UserAPI();
        messagesDao = db.messagesDao();
        contactDao = db.contactDao();
        contactApi = new ContactApi("http://10.0.2.2:" + currentContact.getServer() + "/api/");


        adapter = new MessagesAdapter(this, messages);
        rcMessages = findViewById(R.id.recycler_view1);
        rcMessages.setLayoutManager(new LinearLayoutManager(this));
        rcMessages.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rcMessages.setAdapter(adapter);

        Button btnSaveNewMessage = findViewById(R.id.btnSendMessage);
        btnSaveNewMessage.setOnClickListener(v -> {
            EditText newMessage = findViewById(R.id.message_box);
            String content = newMessage.getText().toString();
            Message message = new Message(content, currentContact.getId());
            messagesDao.insert(message);
            binding.messageBox.setText("");
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


        Call<Void> call = userAPI.getWesServiceAPI().postNewMessage(activeUser.getUserName(),currentContact.getId(), content);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int returnValue = response.code();
                if (returnValue != 404) {
                    currentContact.setLast(content);
                    currentContact.setLastDate(message.getCreated());
                    contactDao.update(currentContact);
                    Call<Void> call2 = contactApi.getWesServiceAPI().postTransfer(activeUser.getUserName(),currentContact.getId(), content);
                    call2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call2, Response<Void> response) {
                            int returnValue = response.code();
                            if (returnValue != 404) {
                                //currentContact.setLast(content);
                                //currentContact.setLastDate(message.getCreated());
                                //contactDao.update(currentContact);
                                onResume();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call2, Throwable t) {
                            System.out.println("from faiulre");

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
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

/*    void updateCurrentContact(List<Message> updateMessages, String contactID) {
        for (int i = 0; i< updateMessages.size(); i++) {
            updateMessages.get(i).setContactId(contactID);
        }
    }*/
/*
    int getIndexOfCurrentContact(List<Contact> contactsList, Contact contact) {
        for (int i =0 ;i < contactsList.size(); i++) {
            if (contactsList.get(i).getId().equals(contact.getId())) {
                return i;
            }
        }
        return 0;
    }*/
}

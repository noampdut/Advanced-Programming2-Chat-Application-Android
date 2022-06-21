package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String content;
    private String created;
    private boolean sent;
    private String contactId;

//    public Message(String content, String created, boolean sent, String contactId) {
//        this.content = content;
//        this.created = created;
//        this.sent = sent;
//        this.contactId = contactId;
//    }

    public Message(String content, String contactId) {
        this.content = content;
        this.created = new Date().toLocaleString();
       // this.created = new Date().toString();
        this.sent = true;
        this.contactId = contactId;
    }

/*    public Message(int id, String content, String created, boolean sent, String contactId) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
        this.contactId = contactId;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean getSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
}

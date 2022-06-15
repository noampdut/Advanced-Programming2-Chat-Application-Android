package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @PrimaryKey
    @NonNull
    private String Id;
    private String NickName;
    private List<Contact> contacts;

    public User(@NonNull String id, String nickName, List<Contact> contacts) {
        Id = id;
        NickName = nickName;
        this.contacts = contacts;
    }

    @NonNull
    public String getId() {
        return Id;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}

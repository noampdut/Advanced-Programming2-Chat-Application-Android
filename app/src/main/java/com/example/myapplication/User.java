package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @PrimaryKey
    @NonNull
    private String userName;
    private String nickName;
    private String picture;
    private List<Contact> contacts;

    public User(@NonNull String id, String nickName, List<Contact> contacts) {
        userName = id;
        this.nickName = nickName;
        this.contacts = contacts;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Contact implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name, server, last, lastDate;
    private static List<Message> messagesList;


    public Contact(String id, String name, String server, String last, String lastDate, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastDate = lastDate;
        this.messagesList = messages;
    }

    public Contact(String id, String name, String server) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = "New Chat!";
        this.lastDate = "";
    }

    public List<Message> getMessageList() {
        return messagesList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messagesList = messageList;
    }



    public Contact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
    @Override
    public String toString() {
        return "contact{ id " + id + ", name= " + name +", server= " + server + " }";
    }
}

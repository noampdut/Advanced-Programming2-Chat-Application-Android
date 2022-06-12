package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class, Message.class}, version = 3)
public abstract class AppDb extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessagesDao messagesDao();
}

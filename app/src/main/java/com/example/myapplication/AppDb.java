package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class, Message.class}, version = 3)
public abstract class AppDb extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessagesDao messagesDao();
    private static AppDb db;

     public static AppDb getDb(Context context) {
        if(db == null) {
           db = Room.databaseBuilder(context.getApplicationContext(),AppDb.class,"mainDB")
                   .allowMainThreadQueries()
                   .build();
        }
        return db;
    }
}

package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    public abstract ContactDao contactDao();
}

package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessagesDao {
    @Query("SELECT * FROM message")
    List<Message> index();

    @Query("SELECT * FROM message WHERE contactId = :contactId")
    List<Message> get(String contactId);

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Insert
    void insert(Message...Messages);

    @Update
    void update(Message...Messages);

    @Delete
    void delete(Message...Messages);
}

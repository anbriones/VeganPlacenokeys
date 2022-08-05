package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.veganplace.data.modelusuario.ChatMessage;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface DaoChats {
    @Insert(onConflict = REPLACE)
    void insertarchat(ChatMessage chats);

    @Query("SELECT * FROM ChatMessage")
    int deleteall();

    @Query("SELECT * FROM ChatMessage  where  (receptor like :receptor and emisor like :emisor) or " +
            "(receptor like :emisor and emisor like :receptor) order by messageTime")
    LiveData<List<ChatMessage>> getchats(String emisor, String receptor);


    @Query("SELECT * FROM ChatMessage  where receptor like :receptor")
    LiveData<List<ChatMessage>> getchatsreeceive(String receptor);


    @Query("SELECT * FROM ChatMessage where emisor like :emisor ")
    LiveData<List<ChatMessage>> getchatssend(String emisor);

    @Query("delete FROM location ")
    int eliminarLocalizaciones();
}

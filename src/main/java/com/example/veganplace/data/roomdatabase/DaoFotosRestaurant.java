package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.veganplace.data.modelmapas.Photo;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoFotosRestaurant {
    @Insert(onConflict = REPLACE)
    void insertarfoto(List<Photo> restaurantes);

    @Query("SELECT  * FROM photo WHERE adress_rest  LIKE :address")
    LiveData<Photo> getfotobyres(String address);

    @Query("SELECT * FROM photo")
    LiveData<List<Photo>> getfotos();

    @Query("delete FROM photo ")
    int eliinarfotos();
}

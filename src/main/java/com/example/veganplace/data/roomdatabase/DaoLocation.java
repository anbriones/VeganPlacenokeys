package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.veganplace.data.modelmapas.Location;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoLocation {
    @Insert(onConflict = REPLACE)
    void insertarLocation(List<Location> localizaciones);




    @Query("SELECT * FROM Location")
    LiveData<List<Location>> getlocalizaciones();



    @Query("delete FROM location ")
    int eliminarLocalizaciones();
}


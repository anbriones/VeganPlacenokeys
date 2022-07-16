package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.veganplace.data.modelmapas.Result;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface DaoResult {
    @Insert(onConflict = REPLACE)
    void insertarRestaurante(List<Result> restaurantes);




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveRecipe(Result restaurante);


    @Query("SELECT * FROM result WHERE address  LIKE :name")
    LiveData<Result> getrestaurantesbyname(String name);

    @Query("SELECT * FROM result")
    LiveData<List<Result>> getrestaurantes();



    @Query("delete FROM result ")
    int eliminarrestaurantes();
}


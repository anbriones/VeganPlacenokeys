package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.veganplace.data.modelrecetas.Ingredient;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface DaoIngrediente {
    @Insert(onConflict = REPLACE)
    void insertarIngrediente(List<Ingredient> ingredientes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveIngrediente(Ingredient ingrediente);


    @Query("SELECT * FROM ingrediente")
    LiveData<List<Ingredient>> getIngredientes();


     @Query("SELECT * FROM ingrediente WHERE labelreceta LIKE  '%' || :id_receta || '%' ")
     LiveData<List<Ingredient>> getingredientesbyid_receta(String id_receta);

    @Query("delete FROM ingrediente ")
    int eliminaringredientes();
}

package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


    @Dao
    public interface DaoReceta {
        @Insert(onConflict = REPLACE)
        void insertarReceta(List<Recipe> recetas);


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void saveRecipe(Recipe recipe);


        @Query("SELECT * FROM recipe")
        LiveData<List<Recipe>> getrecetas();
/*
        @Query("SELECT * FROM alimentojson WHERE tipo LIKE  '%' || :tipocomida || '%' ")
        LiveData<List<AlimentosFinales>> getalimentosbytipo(String tipocomida);
*/
        @Query("delete FROM recipe ")
        int eliminarrecetas();
    }


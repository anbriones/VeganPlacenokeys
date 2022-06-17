package com.example.veganplace.data.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;


@Database(entities = {Recipe.class, Ingredient.class}, version = 1, exportSchema = false)
@TypeConverters(ListConverter.class)
public abstract class Recetasbasedatos extends RoomDatabase {
    private static Recetasbasedatos instance;

    public static Recetasbasedatos getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context, Recetasbasedatos.class, "databaserecetas.db").build();
        }
        return instance;
    }
    public  abstract DaoReceta daoReceta();
    public  abstract DaoIngrediente daoIngrediente();



}
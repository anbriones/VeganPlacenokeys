package com.example.veganplace.data.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.veganplace.data.Roomlogin.DaoUsuario;
import com.example.veganplace.data.modelusuario.User;
import com.example.veganplace.data.modelnoticias.Article;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;


@Database(entities = {Recipe.class, Ingredient.class, Article.class, User.class}, version = 1, exportSchema = false)
@TypeConverters(ListConverter.class)
public abstract class veganPlacebasedatos extends RoomDatabase {
    private static veganPlacebasedatos instance;

    public static veganPlacebasedatos getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context, veganPlacebasedatos.class, "databaserecetas.db").build();
        }
        return instance;
    }
    public  abstract DaoReceta daoReceta();
    public  abstract DaoIngrediente daoIngrediente();
    public abstract  DaoNoticia daoNoticia();
    public abstract DaoUsuario daoUsuarios();




}
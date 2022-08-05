package com.example.veganplace.data.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.veganplace.data.modelmapas.ListConverter;
import com.example.veganplace.data.modelmapas.Location;
import com.example.veganplace.data.modelmapas.Result;
import com.example.veganplace.data.modelnoticias.Article;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;
import com.example.veganplace.data.modelusuario.ChatMessage;


@Database(entities = {Recipe.class, Ingredient.class, Article.class,  Result.class, Location.class, ChatMessage.class}, version = 1, exportSchema = false)
@TypeConverters(ListConverter.class)
public abstract class veganPlacebasedatos extends RoomDatabase {
    private static veganPlacebasedatos instance;

    public static veganPlacebasedatos getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context, veganPlacebasedatos.class, "databaseveganplace.db").build();
        }
        return instance;
    }
    public  abstract DaoReceta daoReceta();
    public  abstract DaoIngrediente daoIngrediente();
    public abstract  DaoNoticia daoNoticia();
    public abstract DaoResult daoResult();//restaurantes
    public abstract DaoLocation daoLocation();//localización de los restaurantes
    public abstract DaoChats daoChat();

}
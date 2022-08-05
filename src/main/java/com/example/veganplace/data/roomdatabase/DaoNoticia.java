package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.veganplace.data.modelnoticias.Article;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface DaoNoticia {
    @Insert(onConflict = REPLACE)
    void insertarNoticia(List<Article> noticias);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveRecipe(Article noticia);


    @Query("SELECT * FROM articulo")
    LiveData<List<Article>> getnoticias();

    /*
    Método creado para comprobar que se insertan las noticias
     */
    @Query("SELECT * FROM articulo")
    List<Article> getnoticiastest();


    @Query("delete FROM articulo ")
    int eliminarnoticias();
}




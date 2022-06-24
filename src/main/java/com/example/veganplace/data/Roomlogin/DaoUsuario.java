package com.example.veganplace.data.Roomlogin;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.veganplace.data.modelusuario.User;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoUsuario {
    @Insert(onConflict = REPLACE)
    void insertarUsuario(User usuarios);



     @Query("SELECT * FROM User where displayName LIKE :nombre and password LIKE :password  ")
   LiveData<User> obtenerusuario(String nombre, String password);

    @Query("SELECT * FROM USER WHERE displayName LIKE :nombre  ")
            LiveData<User> existe(String nombre);

    @Query("SELECT  * FROM User")
    LiveData<List<User>> getusers();


    @Query("delete FROM User ")
    int eliminarusuarios();

}

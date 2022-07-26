package com.example.veganplace.data.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.veganplace.data.modelusuario.Resenia;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface DaoResenia {
    @Insert(onConflict = REPLACE)
    void insertresenia(Resenia resenia);

    @Query("SELECT * FROM resenia where name_user LIKE :nombre  ")
    LiveData<List<Resenia>> obtenerresenia(String nombre);


    @Query("SELECT  * FROM resenia where valor>=4 ")
    LiveData<List<Resenia>> getresenias();

    @Query("delete FROM Resenia ")
    int eliminarresenias();
}

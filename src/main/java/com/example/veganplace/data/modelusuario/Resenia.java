package com.example.veganplace.data.modelusuario;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "resenia")
public class Resenia {
    @PrimaryKey
    @NonNull
    @SerializedName("id_resenia")
    @Expose
    @ColumnInfo(name = "id_resenia")
    private String id_resenia;
    @NonNull
    @ColumnInfo(name = "name_user")
    @SerializedName("name_user")
    @Expose
    private String name_user;
    @NonNull
    @ColumnInfo(name = "name_res")
    @SerializedName("name_res")
    @Expose
    private String name_res;
    @NonNull
    @ColumnInfo(name = "dir_res")
    @SerializedName("dir_res")
    @Expose
    private String dir_res;
    @NonNull
    @ColumnInfo(name = "descripcion")
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @NonNull
    @ColumnInfo(name = "valor")
    @SerializedName("valor")
    @Expose
    private double valor;


    public Resenia(@NonNull String id_resenia, @NonNull String name_user, @NonNull String name_res, @NonNull String dir_res, @NonNull String descripcion, double valor) {
        this.id_resenia = id_resenia;
        this.name_user = name_user;
        this.name_res = name_res;
        this.dir_res = dir_res;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public Resenia() {
    }

    public String getDir_res() {
        return dir_res;
    }

    public void setDir_res(String dir_res) {
        this.dir_res = dir_res;
    }

    @NonNull
    public String getId_resenia() {
        return id_resenia;
    }

    public void setId_resenia(@NonNull String id_resenia) {
        this.id_resenia = id_resenia;
    }

    @NonNull
    public String getName_user() {
        return name_user;
    }

    public void setName_user(@NonNull String name_user) {
        this.name_user = name_user;
    }

    @NonNull
    public String getName_res() {
        return name_res;
    }

    public void setName_res(@NonNull String name_res) {
        this.name_res = name_res;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    public double getValor() {
        return valor;
    }

    public void setValor(@NonNull double valor) {
        this.valor = valor;
    }
}

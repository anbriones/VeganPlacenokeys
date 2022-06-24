package com.example.veganplace.data.modelusuario;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity(tableName = "user")
public class User implements Serializable {
    @PrimaryKey
    @NonNull
     @SerializedName("displayName")
    @Expose
    @ColumnInfo(name = "displayName")
    private String displayName;
    @NonNull
    @ColumnInfo(name = "password")
    @SerializedName("password")
    @Expose
    private String password;





    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName){this.displayName=displayName;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
}
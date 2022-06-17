package com.example.veganplace.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity
public class LoggedInUser {
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private String userId;
    @SerializedName("label")
    @Expose
    @ColumnInfo(name = "label")
    private String displayName;


    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
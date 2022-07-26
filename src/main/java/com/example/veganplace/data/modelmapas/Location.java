
package com.example.veganplace.data.modelmapas;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "Location")
public class Location {

    @NonNull
    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    @NonNull
    @ColumnInfo(name = "lng")
    private Double lng;
    @PrimaryKey
    @SerializedName("adress_rest")
    @Expose
    @NonNull
    @ColumnInfo(name = "adress_rest")
    private String adress_rest;


    public String getAdress_rest() {
        return adress_rest;
    }

    public void setAdress_rest(String adress_rest) {
        this.adress_rest = adress_rest;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}

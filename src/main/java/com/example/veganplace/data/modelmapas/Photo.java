
package com.example.veganplace.data.modelmapas;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "Photo")
public class Photo implements Serializable {
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("html_attributions")
    @Expose
   // @ColumnInfo(name = "htmlAttributions")
    @TypeConverters(ListConverter.class)
    private List<String> htmlAttributions = null;
    @SerializedName("photo_reference")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "photoReference")
    private String photoReference;

    public void setWidth(Integer width) {
        this.width = width;
    }

    @SerializedName("width")
    @Expose
    private Integer width;
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




    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Integer getWidth() {
        return width;
    }


}

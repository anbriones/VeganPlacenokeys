
package com.example.veganplace.data.modelrecetas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Images implements Serializable
{

    @SerializedName("THUMBNAIL")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("SMALL")
    @Expose
    private Small small;
    @SerializedName("REGULAR")
    @Expose
    private Regular regular;
    @SerializedName("LARGE")
    @Expose
    private Large large;
    private final static long serialVersionUID = -5432776114133969113L;

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Small getSmall() {
        return small;
    }

    public void setSmall(Small small) {
        this.small = small;
    }

    public Regular getRegular() {
        return regular;
    }

    public void setRegular(Regular regular) {
        this.regular = regular;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }

}

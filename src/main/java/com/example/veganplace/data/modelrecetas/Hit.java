
package com.example.veganplace.data.modelrecetas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hit implements Serializable
{

    @SerializedName("recipe")
    @Expose
    private Recipe recipe;
    @SerializedName("_links")
    @Expose
    private Links__1 links;
    private final static long serialVersionUID = 5914878546775789372L;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Links__1 getLinks() {
        return links;
    }

    public void setLinks(Links__1 links) {
        this.links = links;
    }

}

package com.example.veganplace.data.lecturaapi;

import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;

public interface OnRecipeLoadedListener {
    public void onRecipeLoaded(List<Recipe> recetas);
}

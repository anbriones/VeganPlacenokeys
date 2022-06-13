package com.example.veganplace.data.lecturaapi;

import com.example.veganplace.data.modelrecetas.Ingredient;

import java.util.List;

public interface OnIngredientLoadedListener {
    public void onIngredientLoaded(List<Ingredient> ingredientes);
}

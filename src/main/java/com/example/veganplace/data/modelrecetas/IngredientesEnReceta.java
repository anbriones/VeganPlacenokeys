package com.example.veganplace.data.modelrecetas;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public  class IngredientesEnReceta {
 @Embedded public Recipe recipe;
@Relation(
        parentColumn = "label",
        entityColumn = "label_creator"
)
public List<Ingredient> ingredientes;

        }




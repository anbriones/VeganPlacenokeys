package com.example.veganplace.ui.recetas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;
import com.example.veganplace.data.roomdatabase.RecetasRepository;

import java.util.List;

public class RecetasViewModel extends ViewModel  {

    private final RecetasRepository mrecetasrepository;
    private final LiveData<List<Recipe>> mrecetas;
    private final LiveData<List<Ingredient>> mingredientes;


    public RecetasViewModel(RecetasRepository recetasrepository) {
        mrecetasrepository=recetasrepository;
        mrecetas=mrecetasrepository.getrecetas();
        mingredientes=mrecetasrepository.getIngredientes();

    }

    public LiveData<List<Recipe>> getRecetas() {
        return mrecetas;
    }
    public LiveData<List<Ingredient>> getMingredientes() {
        return mingredientes;
    }




}



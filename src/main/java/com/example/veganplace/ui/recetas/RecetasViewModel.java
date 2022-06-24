package com.example.veganplace.ui.recetas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;

public class RecetasViewModel extends ViewModel  {
    private final VeganPlaceRepository mrecetasrepository;
    private final LiveData<List<Recipe>> mrecetas;



    public RecetasViewModel(VeganPlaceRepository recetasrepository) {
        mrecetasrepository=recetasrepository;
        mrecetas=mrecetasrepository.getrecetas();
    }

    public LiveData<List<Recipe>> getRecetas() {
        return mrecetas;
    }
}



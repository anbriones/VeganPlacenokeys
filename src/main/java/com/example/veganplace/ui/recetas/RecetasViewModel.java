package com.example.veganplace.ui.recetas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.RecetasRepository;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;

public class RecetasViewModel extends ViewModel  {

    private final RecetasRepository mrecetasrepository;
    private final LiveData<List<Recipe>> mrecetas;
    private final LiveData<List<Ingredient>> mingredientes;
   // private final LiveData<List<Ingredient>> mingredientesbusqueda;
    private final LiveData<List<Ingredient>> mingredientesagrupados;



    public RecetasViewModel(RecetasRepository recetasrepository) {
        mrecetasrepository=recetasrepository;
        mingredientesagrupados = mrecetasrepository.getingredientesagrupados();
        mrecetas=mrecetasrepository.getrecetas();
        mingredientes=mrecetasrepository.getIngredientes();
      // mingredientesbusqueda=mrecetasrepository.getalgetingredientesbyIdReceta();
    }

    public LiveData<List<Recipe>> getRecetas() {
        return mrecetas;
    }
    public LiveData<List<Ingredient>> getMingredientes() {
        return mingredientes;
    }

    //public LiveData<List<Ingredient>> getingredientes(String id_label) {return mingredientesbusqueda;}
        public LiveData<List<Ingredient>> getingredientesagrupados( ) {return mrecetasrepository.getingredientesagrupados();    }





}



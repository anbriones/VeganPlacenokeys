package com.example.veganplace.ui.detallesreceta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.RecetasRepository;
import com.example.veganplace.data.modelrecetas.Ingredient;

import java.util.List;

public class DetallesRecetasViewModel extends ViewModel {

        private final RecetasRepository mrecetasrepository;
        private final LiveData<List<Ingredient>> mingredientesbusqueda;
        private final LiveData<List<Ingredient>> mingredientes;


        public DetallesRecetasViewModel(RecetasRepository recetasrepository) {
            mrecetasrepository=recetasrepository;
            mingredientes=mrecetasrepository.getIngredientes();
            mingredientesbusqueda=mrecetasrepository.getalgetingredientesbyIdReceta();    }


    public void setid(String id) {        mrecetasrepository.setid(id);}
        public LiveData<List<Ingredient>> getingredientes() {return mingredientesbusqueda;}
    public LiveData<List<Ingredient>> getMingredientes() {
        return mingredientes;
    }





    }

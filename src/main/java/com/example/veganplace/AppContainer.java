package com.example.veganplace;


import android.content.Context;

import com.example.veganplace.data.lecturaapi.IngredienteNetworkDataSource;
import com.example.veganplace.data.lecturaapi.RecetasNetworkDataSource;
import com.example.veganplace.data.roomdatabase.Recetasbasedatos;
import com.example.veganplace.ui.detallesreceta.DetallesrecetasViewModelFactory;
import com.example.veganplace.ui.recetas.RecetasViewModelFactory;


public class AppContainer {

    private Recetasbasedatos database;
    private RecetasNetworkDataSource networkDataSource;
    private IngredienteNetworkDataSource networkDataSourceI;

    //Objetos que se necesitan para la ejecución
    public RecetasRepository repository;

    public RecetasViewModelFactory factoryrecetas;

    public DetallesrecetasViewModelFactory factorydetalles;

    public AppContainer(Context context){
        database = Recetasbasedatos.getInstance(context);
        networkDataSource = RecetasNetworkDataSource.getInstance();
        networkDataSourceI=IngredienteNetworkDataSource.getInstance();

        repository = RecetasRepository.getInstance(networkDataSource,networkDataSourceI, database.daoReceta(), database.daoIngrediente());
        factoryrecetas = new RecetasViewModelFactory(repository);
        factorydetalles = new DetallesrecetasViewModelFactory(repository);
    }
}



package com.example.veganplace;


import android.content.Context;

import com.example.veganplace.data.lecturaapi.RecetasNetworkDataSource;
import com.example.veganplace.data.roomdatabase.RecetasRepository;
import com.example.veganplace.data.roomdatabase.Recetasbasedatos;
import com.example.veganplace.ui.recetas.RecetasViewModelFactory;


public class AppContainer {

    private Recetasbasedatos database;
    private RecetasNetworkDataSource networkDataSource;
    //Objetos que se necesitan para la ejecución
    public RecetasRepository repository;

    public RecetasViewModelFactory factoryrecetas;

    public AppContainer(Context context){
        database = Recetasbasedatos.getInstance(context);
        networkDataSource = RecetasNetworkDataSource.getInstance();
        repository = RecetasRepository.getInstance(networkDataSource,database.daoReceta());
        factoryrecetas = new RecetasViewModelFactory(repository);

    }
}



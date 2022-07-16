package com.example.veganplace;


import android.content.Context;

import com.example.veganplace.data.lecturamapas.CoordenadasNetWorkDatasource;
import com.example.veganplace.data.lecturamapas.RestaurantesNetWorkDatasource;
import com.example.veganplace.data.lecturarectas.IngredienteNetworkDataSource;
import com.example.veganplace.data.lecturarectas.RecetasNetworkDataSource;
import com.example.veganplace.data.lecturaapinoticias.NoticiasNetWorkDatasource;
import com.example.veganplace.data.modelusuario.User;
import com.example.veganplace.data.roomdatabase.veganPlacebasedatos;
import com.example.veganplace.ui.detallesreceta.DetallesrecetasViewModelFactory;
import com.example.veganplace.ui.home.HomeViewModelFactory;
import com.example.veganplace.ui.login.LoginViewModelFactory;
import com.example.veganplace.ui.noticias.NoticiasViewModelFactory;
import com.example.veganplace.ui.recetas.RecetasViewModelFactory;


public class AppContainer {

    private veganPlacebasedatos database;
    private RecetasNetworkDataSource networkDataSource;
    private IngredienteNetworkDataSource networkDataSourceI;
    private NoticiasNetWorkDatasource netWorkDatasourceN;
    private RestaurantesNetWorkDatasource netWorkDatasourceR;
    private CoordenadasNetWorkDatasource netWorkDatasourceL;

    //Objetos que se necesitan para la ejecución
    public VeganPlaceRepository repository;

    public RecetasViewModelFactory factoryrecetas;
    public DetallesrecetasViewModelFactory factorydetalles;
    public NoticiasViewModelFactory factorynoticias;
    public LoginViewModelFactory factoryusers;
    public HomeViewModelFactory factoryrestaurantes;
    public User usuario;

    public AppContainer(Context context){
        database = veganPlacebasedatos.getInstance(context);
        networkDataSource = RecetasNetworkDataSource.getInstance();
        networkDataSourceI=IngredienteNetworkDataSource.getInstance();
        netWorkDatasourceN=NoticiasNetWorkDatasource.getInstance();
        netWorkDatasourceR = RestaurantesNetWorkDatasource.getInstance();
        netWorkDatasourceL = CoordenadasNetWorkDatasource.getInstance();

        repository =  VeganPlaceRepository.getInstance(networkDataSource, networkDataSourceI, netWorkDatasourceN,netWorkDatasourceR,netWorkDatasourceL, database.daoReceta(), database.daoIngrediente(), database.daoNoticia(), database.daoUsuarios(),database.daoResult(), database.daoLocation());

       factoryrecetas = new RecetasViewModelFactory(repository);
        factorydetalles = new DetallesrecetasViewModelFactory(repository);
        factorynoticias = new NoticiasViewModelFactory(repository);
        factoryusers = new LoginViewModelFactory(repository);
        factoryrestaurantes = new HomeViewModelFactory(repository);
        usuario=new User();
    }
}



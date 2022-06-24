package com.example.veganplace;


import android.content.Context;

import com.example.veganplace.data.lecturaapi.IngredienteNetworkDataSource;
import com.example.veganplace.data.lecturaapi.RecetasNetworkDataSource;
import com.example.veganplace.data.lecturaapinoticias.NoticiasNetWorkDatasource;
import com.example.veganplace.data.roomdatabase.veganPlacebasedatos;
import com.example.veganplace.ui.detallesreceta.DetallesrecetasViewModelFactory;
import com.example.veganplace.ui.login.LoginViewModelFactory;
import com.example.veganplace.ui.noticias.NoticiasViewModelFactory;
import com.example.veganplace.ui.recetas.RecetasViewModelFactory;


/**
 * Provides static methods to inject the various classes needed for the app
 */
public class InjectorUtils {


    public static VeganPlaceRepository provideRepository(Context context) {
        veganPlacebasedatos database = veganPlacebasedatos.getInstance(context.getApplicationContext());//Base de datos
        RecetasNetworkDataSource networkDataSource = RecetasNetworkDataSource.getInstance();//Fuente de datos remota
        IngredienteNetworkDataSource networkDataSourceI = IngredienteNetworkDataSource.getInstance();//Fuente de datos remota
        NoticiasNetWorkDatasource netWorkDatasourceN = NoticiasNetWorkDatasource.getInstance();

        return VeganPlaceRepository.getInstance(networkDataSource, networkDataSourceI, netWorkDatasourceN, database.daoReceta(), database.daoIngrediente(), database.daoNoticia(), database.daoUsuarios());
    }

    public static RecetasViewModelFactory provideMainActivityViewModelFactoryhome(Context context) {
        VeganPlaceRepository repository = provideRepository(context.getApplicationContext());
        return new RecetasViewModelFactory(repository);
    }

    public static DetallesrecetasViewModelFactory provideMainActivityViewModelFactorydetalles(Context context) {
        VeganPlaceRepository repository = provideRepository(context.getApplicationContext());
        return new DetallesrecetasViewModelFactory(repository);
    }


    public static NoticiasViewModelFactory provideMainActivityViewModelFactorynoticias(Context context) {
        VeganPlaceRepository repository = provideRepository(context.getApplicationContext());
        return new NoticiasViewModelFactory(repository);
    }

    public static LoginViewModelFactory provideMainActivityViewModelFactorylogin(Context context) {
        VeganPlaceRepository repository = provideRepository(context.getApplicationContext());
        return new LoginViewModelFactory(repository);

    }
}

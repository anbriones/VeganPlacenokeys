package com.example.veganplace;



import android.content.Context;

import com.example.veganplace.data.lecturaapi.IngredienteNetworkDataSource;
import com.example.veganplace.data.lecturaapi.RecetasNetworkDataSource;
import com.example.veganplace.data.roomdatabase.RecetasRepository;
import com.example.veganplace.data.roomdatabase.Recetasbasedatos;
import com.example.veganplace.ui.recetas.RecetasViewModelFactory;


/**
 * Provides static methods to inject the various classes needed for the app
 */
public class InjectorUtils {


    public static RecetasRepository provideRepository(Context context) {
       Recetasbasedatos database = Recetasbasedatos.getInstance(context.getApplicationContext());//Base de datos
        RecetasNetworkDataSource networkDataSource = RecetasNetworkDataSource.getInstance();//Fuente de datos remota
        IngredienteNetworkDataSource networkDataSourceI = IngredienteNetworkDataSource.getInstance();//Fuente de datos remota
        return RecetasRepository.getInstance(networkDataSource,networkDataSourceI,database.daoReceta(),database.daoIngrediente());
    }

    public static RecetasViewModelFactory provideMainActivityViewModelFactoryhome(Context context) {
        RecetasRepository repository = provideRepository(context.getApplicationContext());
        return new RecetasViewModelFactory(repository);
    }


    }

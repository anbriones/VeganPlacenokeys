package com.example.veganplace;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.veganplace.data.lecturaapi.IngredienteNetworkDataSource;
import com.example.veganplace.data.lecturaapi.RecetasNetworkDataSource;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;
import com.example.veganplace.data.roomdatabase.DaoIngrediente;
import com.example.veganplace.data.roomdatabase.DaoReceta;

import java.util.Arrays;
import java.util.List;

public class RecetasRepository {
    private static final String LOG_TAG = RecetasRepository.class.getSimpleName();

    // For Singleton instantiation
    private static RecetasRepository sInstance;
    private final DaoReceta mrecetasdao;
    private final DaoIngrediente mingredientesdao;

    private final RecetasNetworkDataSource mRecetaNetworkDataSource;
    private final IngredienteNetworkDataSource mIngretaNetworkDataSource;

    private final AppExecutors mExecutors = AppExecutors.getInstance();

    private final MutableLiveData<String> tipoFilterLiveData = new MutableLiveData<>();


    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private RecetasRepository(DaoReceta mrecetasdao, DaoIngrediente mingredientesdao, IngredienteNetworkDataSource mIngretaNetworkDataSource, RecetasNetworkDataSource mRecetaNetworkDataSource1) {
        this.mrecetasdao = mrecetasdao;
        this.mingredientesdao = mingredientesdao;

        this.mRecetaNetworkDataSource = mRecetaNetworkDataSource1;
        this.mIngretaNetworkDataSource = mIngretaNetworkDataSource;


        doFetchRecetas();
        // LiveData that fetches alimentosjson from network
        LiveData<Recipe[]> networkData = this.mRecetaNetworkDataSource.getCurrentrecetas();
        LiveData<Ingredient[]> networkDataing = this.mIngretaNetworkDataSource.getcurrentingredientes();
//        LiveData<IngredientesEnReceta[]> ingredientesEnRecetaLiveData= (LiveData<IngredientesEnReceta[]>) this.mIngreRecetataNetworkDataSource.getcurrentingredientesreceta();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newRecetasFromNetwork -> {
            mExecutors.diskIO().execute(() -> {

                mrecetasdao.insertarReceta(Arrays.asList(newRecetasFromNetwork));

                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });

        networkDataing.observeForever(newIngreFromNetwork -> {
            mExecutors.diskIO().execute(() -> {

                mingredientesdao.insertarIngrediente(Arrays.asList(newIngreFromNetwork));
                Log.d(LOG_TAG, "New values ingredients inserted in Room");
            });
        });


    }

    public synchronized static RecetasRepository getInstance(RecetasNetworkDataSource nds, IngredienteNetworkDataSource ndsi, DaoReceta daoReceta, DaoIngrediente daoIngrediente) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new RecetasRepository(daoReceta, daoIngrediente, ndsi, nds);//Pasa las dependencias por constructor
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    public void doFetchRecetas() {
        Log.d(LOG_TAG, "Fetching recetas from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mrecetasdao.eliminarrecetas();
            mRecetaNetworkDataSource.fetchRecetas();
        });
        Log.d(LOG_TAG, "Fetching ingredientes from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mingredientesdao.eliminaringredientes();
            mIngretaNetworkDataSource.fetchingredientes();
        });

    }

    //Devuelve Todos las recetas que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Recipe>> getrecetas() {
        return mrecetasdao.getrecetas();
    }

    //Devuelve Todos los ingredientes que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Ingredient>> getIngredientes() {
        return mingredientesdao.getIngredientes();
    }

    //Devuelve Todos los ingredientes que nos da la API agrupados por label de recipe, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Ingredient>> getingredientesagrupados() {
        return mingredientesdao.getIngredientes();
    }

/*
    public LiveData<List<Ingredient>> getalgetingredientesbyIdReceta(String id) {
      return  mingredientesdao.getingredientesbyid_receta(id);
    }
*/
    public LiveData<List<Ingredient>> getalgetingredientesbyIdReceta() {
        return Transformations.switchMap(tipoFilterLiveData, id_receta -> {
            return mingredientesdao.getingredientesbyid_receta(id_receta);
        });
    }
    //Método para poner las fechas, sirve para todos los fragments donde se le llame
    public void setid(String id) {
        tipoFilterLiveData.setValue(id);

    }


}










package com.example.veganplace.data.roomdatabase;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.lecturaapi.RecetasNetworkDataSource;
import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.Arrays;
import java.util.List;

public class RecetasRepository {
    private static final String LOG_TAG = RecetasRepository.class.getSimpleName();

    // For Singleton instantiation
    private static RecetasRepository sInstance;
    private final DaoReceta mrecetasdao;
    private final RecetasNetworkDataSource mRecetaNetworkDataSource;

    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<Long> userFilterLiveData1 = new MutableLiveData<>();
    private final MutableLiveData<Long> userFilterLiveData2 = new MutableLiveData<>();
    private final MutableLiveData<String > tipoFilterLiveData = new MutableLiveData<>();

    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private RecetasRepository(DaoReceta mrecetasdao, RecetasNetworkDataSource mRecetaNetworkDataSource1) {
        this.mrecetasdao = mrecetasdao;
        this.mRecetaNetworkDataSource = mRecetaNetworkDataSource1;


        doFetchRecetas();
        // LiveData that fetches alimentosjson from network
        LiveData<Recipe[]> networkData = this.mRecetaNetworkDataSource.getCurrentrecetas();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newRecetasFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Insert our new alimentos into local database
                mrecetasdao.insertarReceta(Arrays.asList(newRecetasFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static RecetasRepository getInstance(RecetasNetworkDataSource nds, DaoReceta dao) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new RecetasRepository(dao, nds);//Pasa las dependencias por constructor
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
    }

    //Devuelve Todos los alimentos que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Recipe>> getrecetas() {
        return mrecetasdao.getrecetas();
    }




    }








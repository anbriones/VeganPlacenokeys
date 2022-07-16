package com.example.veganplace.data.lecturarectas;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelrecetas.Ingredient;



public class IngredienteNetworkDataSource {

    private static final String LOG_TAG =IngredienteNetworkDataSource.class.getSimpleName();
    private static IngredienteNetworkDataSource sInstance;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Ingredient[]> mDownloadedIngredientes;


    private  IngredienteNetworkDataSource() {
        mDownloadedIngredientes = new MutableLiveData<>();
    }

    public synchronized static IngredienteNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new IngredienteNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }


    public LiveData<Ingredient[]> getcurrentingredientes() {
        return mDownloadedIngredientes;
    }

    public void fetchingredientes() {
        Log.d(LOG_TAG, "Fetch ingredientes started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new IngredientNetworkRunnable(ingredientes ->
                mDownloadedIngredientes.postValue(ingredientes.toArray(new Ingredient[0]))));
    }

}


package com.example.veganplace.data.lecturarectas;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;


public class RecetasNetworkDataSource {
    private static final String LOG_TAG =RecetasNetworkDataSource.class.getSimpleName();
    private static RecetasNetworkDataSource sInstance;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Recipe[]> mDownloadedrecetas;

    private  RecetasNetworkDataSource() {
        mDownloadedrecetas = new MutableLiveData<>();
    }

    public synchronized static RecetasNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new RecetasNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }


    public LiveData<Recipe[]> getCurrentrecetas() {
        return mDownloadedrecetas;
    }

    public void fetchRecetas() {
        Log.d(LOG_TAG, "Fetch recetas started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new RecetasNetworkRunnable(this::onRecipeLoaded));


    }

    private void onRecipeLoaded(List<Recipe> recetas) {
        mDownloadedrecetas.postValue(recetas.toArray(new Recipe[0]));
    }
}


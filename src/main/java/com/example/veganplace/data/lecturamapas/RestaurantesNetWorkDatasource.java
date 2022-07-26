package com.example.veganplace.data.lecturamapas;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelmapas.Result;


public class RestaurantesNetWorkDatasource {
    private static final String LOG_TAG = RestaurantesNetWorkDatasource.class.getSimpleName();
    private static RestaurantesNetWorkDatasource sInstance;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Result[]> mDownloadedRestaurantes;

    private RestaurantesNetWorkDatasource() {
        mDownloadedRestaurantes = new MutableLiveData<>();
    }

    public synchronized static RestaurantesNetWorkDatasource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new RestaurantesNetWorkDatasource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }


    public LiveData<Result[]> getcurrentrestaurantes() {
        return mDownloadedRestaurantes;
    }

    public void fetchrestaurantes(String busqueda) {
        Log.d(LOG_TAG, "Fetch restaurantes started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new RestaurantesNetworkRunnable(results ->
                mDownloadedRestaurantes.postValue(results.toArray(new Result[0])), busqueda));

    }

}


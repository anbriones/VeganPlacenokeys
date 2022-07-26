package com.example.veganplace.data.lecturamapas;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelmapas.Location;


public class CoordenadasNetWorkDatasource {
    private static final String LOG_TAG = CoordenadasNetWorkDatasource.class.getSimpleName();
    private static CoordenadasNetWorkDatasource sInstance;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Location[]> mDownloadedLocalizacion;

    private CoordenadasNetWorkDatasource() {
        mDownloadedLocalizacion = new MutableLiveData<>();
    }

    public synchronized static CoordenadasNetWorkDatasource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new CoordenadasNetWorkDatasource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }


    public LiveData<Location[]> getcurrentlocalizaciones() {
        return mDownloadedLocalizacion;
    }

    public void fetchlocation(String busqueda) {
        Log.d(LOG_TAG, "Fetch noticias started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new LocationNetworkRunnable(localizacion ->
                mDownloadedLocalizacion.postValue(localizacion.toArray(new Location[0])), busqueda));

    }

}


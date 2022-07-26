package com.example.veganplace.data.lecturamapas;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelmapas.Photo;


public class FotosresNetWorkDatasource {
    private static final String LOG_TAG = FotosresNetWorkDatasource.class.getSimpleName();
    private static FotosresNetWorkDatasource sInstance;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Photo[]> mDownloadedFoto;

    private FotosresNetWorkDatasource() {
        mDownloadedFoto = new MutableLiveData<>();
    }

    public synchronized static FotosresNetWorkDatasource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new FotosresNetWorkDatasource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }


    public LiveData<Photo[]> getcurrentfotos() {
        return mDownloadedFoto;
    }

    public void fetchfotos(String busqueda) {
        Log.d(LOG_TAG, "Fetch fotos started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new FotosResNetworkRunnable(foto ->
                mDownloadedFoto.postValue(foto.toArray(new Photo[0])), busqueda));

    }

}


package com.example.veganplace.data.lecturaapinoticias;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelnoticias.Article;


public class NoticiasNetWorkDatasource {
    private static final String LOG_TAG = NoticiasNetWorkDatasource.class.getSimpleName();
    private static NoticiasNetWorkDatasource sInstance;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Article[]> mDownloadedNoticias;

    private NoticiasNetWorkDatasource() {
        mDownloadedNoticias = new MutableLiveData<>();
    }

    public synchronized static NoticiasNetWorkDatasource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new NoticiasNetWorkDatasource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }


    public LiveData<Article[]> getcurrentnoticias() {
        return mDownloadedNoticias;
    }

    public void fetcnoticias() {
        Log.d(LOG_TAG, "Fetch noticias started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new NoticiasNetworkRunnable(noticias ->
                mDownloadedNoticias.postValue(noticias.toArray(new Article[0]))));


    }

}


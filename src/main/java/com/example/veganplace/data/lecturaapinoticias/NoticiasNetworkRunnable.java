package com.example.veganplace.data.lecturaapinoticias;

import android.util.Log;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelnoticias.Principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NoticiasNetworkRunnable implements Runnable {
    private static final String LOG_TAG = NoticiasNetworkRunnable.class.getSimpleName();
    private final OnNoticiaLoadedListener mOnNoticiaLoadedListener;

    private List<Runnable> runList = Collections.synchronizedList(
            new ArrayList<Runnable>());



    public NoticiasNetworkRunnable(OnNoticiaLoadedListener mOnNoticiaLoadedListener) {
        this.mOnNoticiaLoadedListener = mOnNoticiaLoadedListener;

    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NoticiasService service = retrofit.create(NoticiasService.class);
        Call<Principal> call = service.getArticulos();


            try {

                Response<Principal> response = call.execute();
                Principal listadonoticias = service.getArticulos().execute().body();


                AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "tamaño noticias:" + listadonoticias.getArticles().size()));
                AppExecutors.getInstance().mainThread().execute(() -> {

                            AppExecutors.getInstance().mainThread().execute(() -> mOnNoticiaLoadedListener.noNoticiaLoaded(listadonoticias.getArticles()));

                        }
                );

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }




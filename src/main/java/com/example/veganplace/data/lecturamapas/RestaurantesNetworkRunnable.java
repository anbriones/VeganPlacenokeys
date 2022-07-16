package com.example.veganplace.data.lecturamapas;

import android.util.Log;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelmapas.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestaurantesNetworkRunnable implements Runnable {
    private static final String LOG_TAG = RestaurantesNetworkRunnable.class.getSimpleName();
    private final OnRestaurantesLoadedListener mOnRestauranteLoadedListener;


    private List<Runnable> runList = Collections.synchronizedList(
            new ArrayList<Runnable>());



    public RestaurantesNetworkRunnable(OnRestaurantesLoadedListener mOnRestauranteLoadedListener) {
        this.mOnRestauranteLoadedListener = mOnRestauranteLoadedListener;


    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestaurantesService service = retrofit.create(RestaurantesService.class);
        Call<Main> call = service.getResults();


            try {

                Response<Main> response = call.execute();
                Main listadoresults = service.getResults().execute().body();
                AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "tamaño restaurantes:" + listadoresults.getResults().size()));
                AppExecutors.getInstance().mainThread().execute(() -> {
             AppExecutors.getInstance().mainThread().execute(() -> mOnRestauranteLoadedListener.mOnRestaurant(listadoresults.getResults()));

                        }
                );



            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }




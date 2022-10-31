package com.example.veganplace.data.lecturamapas;

import android.util.Log;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelmapas.Main;
import com.example.veganplace.data.modelmapas.Result;

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
    private final String busqueda;

    private List<Runnable> runList = Collections.synchronizedList(
            new ArrayList<Runnable>());

    public RestaurantesNetworkRunnable(OnRestaurantesLoadedListener mOnRestauranteLoadedListener, String busqueda) {
        this.mOnRestauranteLoadedListener = mOnRestauranteLoadedListener;
        this.busqueda = busqueda;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestaurantesService service = retrofit.create(RestaurantesService.class);
        String query="vegan restaurants in Spain ";
        if(!busqueda.equals("")){
            query = query+" "+busqueda;
            Log.d(LOG_TAG, "busqueda no es vacio :" + query);
        }

        Call<Main> call = service.getResults(query, "PON AQUÍ LA API KEY ");
        try {
            Response<Main> response = call.execute();
            List<Result> listadoresults = response.body() == null ? new ArrayList<>() : response.body().getResults();

                          AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "tamaño restaurantes:" + busqueda + listadoresults.size()));

                AppExecutors.getInstance().mainThread().execute(() -> {
                    AppExecutors.getInstance().mainThread().execute(() -> mOnRestauranteLoadedListener.mOnRestaurant(listadoresults));

                        }
                );


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }




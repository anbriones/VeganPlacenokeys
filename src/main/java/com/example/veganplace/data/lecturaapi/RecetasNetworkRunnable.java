package com.example.veganplace.data.lecturaapi;

import android.util.Log;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelrecetas.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecetasNetworkRunnable implements Runnable{
    private static final String LOG_TAG = RecetasNetworkRunnable.class.getSimpleName();
    private final OnRecipeLoadedListener mOnRecipeLoadedListener;

    public RecetasNetworkRunnable(OnRecipeLoadedListener onRecipeLoadedListener){
        mOnRecipeLoadedListener = onRecipeLoadedListener;
    }

    @Override
    public void run()  {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://drive.google.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasService service = retrofit.create(RecetasService.class);
        Call<List<Recipe>> call = service.getrecetas();

        try {

            Response<List<Recipe>> response = call.execute();
            List<Recipe> recetas = response.body() == null ? new ArrayList<>() : response.body();
            AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "Cargados"+recetas.size()));
            AppExecutors.getInstance().mainThread().execute(() -> mOnRecipeLoadedListener.onRecipeLoaded(recetas));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



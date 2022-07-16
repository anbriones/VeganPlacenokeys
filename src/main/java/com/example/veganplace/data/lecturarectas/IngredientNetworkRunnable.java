package com.example.veganplace.data.lecturarectas;


import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelrecetas.Example;
import com.example.veganplace.data.modelrecetas.Hit;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IngredientNetworkRunnable implements Runnable {
    private static final String LOG_TAG = RecetasNetworkRunnable.class.getSimpleName();

    private final OnIngredientLoadedListener mOnIngredientLoadedListener;

    private List<Runnable> runList = Collections.synchronizedList(
            new ArrayList<Runnable>());


    public IngredientNetworkRunnable(OnIngredientLoadedListener mOnIngredientLoadedListener ) {
        this.mOnIngredientLoadedListener = mOnIngredientLoadedListener;

    }


    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/api/recipes/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasService service = retrofit.create(RecetasService.class);
        Call<Example> call = service.getbase();
        try {

            Response<Example> response = call.execute();
            Example listarecetas = service.getbase().execute().body();
            AppExecutors.getInstance().mainThread().execute(() -> {
                List<Recipe> recetas = new ArrayList<>();
                List<Ingredient> ingredientes = new ArrayList<>();
                for (Hit hit : listarecetas.getHits())
                    for (int i = 0; i < hit.getRecipe().getIngredients().size(); i++) {
                        Ingredient ing = hit.getRecipe().getIngredients().get(i);
                        ing.setLabel_creator(hit.getRecipe().getLabel());
                        ingredientes.add(ing);
                    }
                  mOnIngredientLoadedListener.onIngredientLoaded(ingredientes);
                    }
            );


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}



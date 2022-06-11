package com.example.veganplace.data.lecturaapi;


import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecetasService {
    @GET("uc?export=download&id=1WHYrOKz_eeoTDZbSzPzfyzeTeYvSLlqw")
    Call<List<Recipe>> getrecetas();
}

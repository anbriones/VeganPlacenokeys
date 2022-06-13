package com.example.veganplace.data.lecturaapi;


import com.example.veganplace.data.modelrecetas.Example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecetasService {
    @GET("?type=public&q=vegan&app_id=3befe740&app_key=93312507ffcee7930a9d4fff9023e979")

    Call<Example> getbase();
}

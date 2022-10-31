package com.example.veganplace.data.lecturarectas;


import com.example.veganplace.data.modelrecetas.Example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecetasService {
    @GET("?type=public&q=vegan&app_id=PON AQUI EL IDapp_key=PON AQUÍ LA API KEY")

    Call<Example> getbase();
}

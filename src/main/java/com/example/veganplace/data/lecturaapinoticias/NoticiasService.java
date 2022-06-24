package com.example.veganplace.data.lecturaapinoticias;


import com.example.veganplace.data.modelnoticias.Principal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticiasService {
    @GET("everything?q=Vegan&sortBy=popularity&apiKey=d28447470df549f7ae26560af094327d")
    Call<Principal> getArticulos();
}

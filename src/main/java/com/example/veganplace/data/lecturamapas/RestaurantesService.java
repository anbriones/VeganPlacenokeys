package com.example.veganplace.data.lecturamapas;

import com.example.veganplace.data.modelmapas.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantesService {
      @GET("maps/api/place/textsearch/json?")
        public Call<Main> getResults( @Query("query") String query,@Query("key") String key);


    }



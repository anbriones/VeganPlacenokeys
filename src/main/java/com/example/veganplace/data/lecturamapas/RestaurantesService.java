package com.example.veganplace.data.lecturamapas;


import com.example.veganplace.data.modelmapas.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantesService {


    @GET("maps/api/place/textsearch/json?query=restaurants%20in%20Spain%20vegan&key=AIzaSyB4UmqONpL-6Y7Q1ar4BW9_CJbmkti6HFE")
          Call<Main> getResults(@Query("busqueda") String  busqueda);

}

package com.example.veganplace.data.lecturamapas;

import com.example.veganplace.AppExecutors;
import com.example.veganplace.data.modelmapas.Location;
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


public class LocationNetworkRunnable implements Runnable {
    private static final String LOG_TAG = LocationNetworkRunnable.class.getSimpleName();

    private final OnLocationLoadedListener mOnLocationLoadedListener;
    private  final String busqueda;

    private List<Runnable> runList = Collections.synchronizedList(
            new ArrayList<Runnable>());



    public LocationNetworkRunnable(OnLocationLoadedListener mOnLocationLoadedListener, String busqueda) {
              this.mOnLocationLoadedListener=mOnLocationLoadedListener;
        this.busqueda = busqueda;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Restaurantes2 service = retrofit.create(Restaurantes2.class);
        String query="restaurants%20in%20Spain%20vegan";
        if(!busqueda.equals("")){
            query = query+"%20"+busqueda;
        }

        Call<Main> call = service.getResults(query, "AIzaSyB4UmqONpL-6Y7Q1ar4BW9_CJbmkti6HFE");


        try {
            Response<Main> response = call.execute();
            List<Result> listadoresults = response.body() == null ? new ArrayList<>() : response.body().getResults();

            AppExecutors.getInstance().mainThread().execute(() -> {
                            List<Location> localizaciones = new ArrayList<>();
                                 for (Result res : listadoresults){
                                     res.getGeometry().getLocation().setAdress_rest(res.getFormattedAddress());
                                    localizaciones.add(res.getGeometry().getLocation());
                                }
                    AppExecutors.getInstance().mainThread().execute(() -> mOnLocationLoadedListener.onLocationlistener(localizaciones));

                        }
                );

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }




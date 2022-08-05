package com.example.veganplace.ui.restaurantes;

import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;

public class RestaurantesViewModel extends ViewModel {
    private final VeganPlaceRepository mveganrepository;




    public RestaurantesViewModel(VeganPlaceRepository repositoriovegano) {
        mveganrepository=repositoriovegano;


    }

}
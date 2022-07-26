package com.example.veganplace.ui.restaurantes;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.VeganPlaceRepository;


public class RestauranteViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final VeganPlaceRepository mRepository;

    public RestauranteViewModelFactory(VeganPlaceRepository repository) {
        this.mRepository = repository;
    }

    //Se sobreescribe el método oncreate
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RestaurantesViewModel(mRepository);
    }
}
package com.example.veganplace.ui.noticias;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.VeganPlaceRepository;

public class NoticiasViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final VeganPlaceRepository mRepository;

    public NoticiasViewModelFactory(VeganPlaceRepository repository) {
        this.mRepository = repository;
    }

    //Se sobreescribe el método oncreate
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new NoticiasViewModel(mRepository);
    }
}
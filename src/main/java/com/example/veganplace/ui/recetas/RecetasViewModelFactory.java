package com.example.veganplace.ui.recetas;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.data.roomdatabase.RecetasRepository;

public class RecetasViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final RecetasRepository mRepository;

    public RecetasViewModelFactory(RecetasRepository repository) {
        this.mRepository = repository;
    }

    //Se sobreescribe el método oncreate
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RecetasViewModel(mRepository);
    }
}
package com.example.veganplace.ui.detallesreceta;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.RecetasRepository;

public class DetallesrecetasViewModelFactory extends ViewModelProvider.NewInstanceFactory{

        private final RecetasRepository mRepository;

        public DetallesrecetasViewModelFactory(RecetasRepository repository) {
            this.mRepository = repository;
        }

        //Se sobreescribe el método oncreate
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DetallesRecetasViewModel(mRepository);
        }
    }
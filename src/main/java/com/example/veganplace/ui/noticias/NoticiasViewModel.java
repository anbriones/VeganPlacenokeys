package com.example.veganplace.ui.noticias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelnoticias.Article;

import java.util.List;

public class NoticiasViewModel extends ViewModel {

    private final VeganPlaceRepository mnoticiasrepository;
    private final LiveData<List<Article>> mnoticias;



    public NoticiasViewModel(VeganPlaceRepository noticiasrepository) {
        mnoticiasrepository=noticiasrepository;
        mnoticias=mnoticiasrepository.getnoticias();
    }

    public LiveData<List<Article>> getnoticias() {
        return mnoticias;
    }
}


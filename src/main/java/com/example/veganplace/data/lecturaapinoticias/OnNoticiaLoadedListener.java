package com.example.veganplace.data.lecturaapinoticias;

import com.example.veganplace.data.modelnoticias.Article;

import java.util.List;

public interface OnNoticiaLoadedListener {
    public void noNoticiaLoaded(List<Article> noticias);

}

package com.example.veganplace.data.roomdatabase;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.veganplace.data.modelmapas.Result;
import com.example.veganplace.data.modelnoticias.Article;
import com.example.veganplace.data.modelrecetas.Recipe;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class veganPlacebasedatosTest {
    private DaoReceta daoreceta;
    private DaoNoticia daonoticia;
    private DaoResult daoresult;
    private veganPlacebasedatos db;
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, veganPlacebasedatos.class).build();
        daoreceta= db.daoReceta();
        daonoticia=db.daoNoticia();
        daoresult=db.daoResult();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }


    @Test
    public void writeAlimentoAndReadreceta() throws Exception {
        List<Recipe> recetas= new ArrayList<Recipe>() ;
        Recipe receta=new Recipe();
        receta.setLabel("macarrones con tomate");
        recetas.add(receta);
        daoreceta.insertarReceta(recetas);
        List<Recipe> recetasobtenidas= daoreceta.getrecetaslist();
        assertEquals(recetasobtenidas.size(), 1);
        daoreceta.eliminarrecetas();
        List<Recipe> recetasobtenidas2= daoreceta.getrecetaslist();
        Assert.assertTrue(recetasobtenidas2.isEmpty());

    }

    @Test
    public void writeNoticiaoAndReadNoticia() throws Exception {
        List<Article> noticias= new ArrayList<Article>() ;
        Article noticia=new Article();
        noticia.setTitle("Nueva ecofinca vegena");
        noticias.add(noticia);
        daonoticia.insertarNoticia(noticias);
        List<Article> noticiasobtenidas= daonoticia.getnoticiastest();
        assertEquals(noticiasobtenidas.size(), 1);
        daonoticia.eliminarnoticias();
        List<Article> noticiasobtenidas2= daonoticia.getnoticiastest();
        Assert.assertTrue(noticiasobtenidas2.isEmpty());

    }

    @Test
    public void writeRestauranteoAndReadRestauarante() throws Exception {
        List<Result> restaurantes= new ArrayList<Result>() ;
        Result restaunante=new Result();
        restaunante.setFormattedAddress("dir de b13");
        restaunante.setName("B13");
        restaurantes.add(restaunante);
        daoresult.insertarRestaurante(restaurantes);
        List<Result> resobtenidos= daoresult.getrestauranteslist();
        assertEquals(resobtenidos.size(), 1);
        daoresult.eliminarrestaurantes();
        List<Result> resobtenidos2= daoresult.getrestauranteslist();
        Assert.assertTrue(resobtenidos2.isEmpty());

    }
}
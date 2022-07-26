package com.example.veganplace.ui.restaurantes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelusuario.Resenia;

import java.util.List;

public class RestaurantesViewModel extends ViewModel {
    private final VeganPlaceRepository mveganrepository;
    private final LiveData<List<Resenia>> resenias;
   // private final List<Resenia> reseniasfire;


    public RestaurantesViewModel(VeganPlaceRepository repositoriovegano) {
        mveganrepository=repositoriovegano;
        resenias = mveganrepository.getreseniastotales();
       // reseniasfire=mveganrepository.getreseniasfirebase();

    }

    public LiveData<List<Resenia>> getresenias() { return       mveganrepository.getreseniastotales();}
    public void insertarresenia(Resenia resenia){ mveganrepository.insertarresenia(resenia);}
    public List<Resenia> getreseniasfirebase(){return  mveganrepository.getreseniasfirebase();}


}
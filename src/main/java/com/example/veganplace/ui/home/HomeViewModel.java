package com.example.veganplace.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelmapas.Location;
import com.example.veganplace.data.modelmapas.Result;
import com.example.veganplace.data.modelusuario.Resenia;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final VeganPlaceRepository mveganrepository;
    private final LiveData<Result> restaurante;
    private String busqueda= " ";


    public HomeViewModel(VeganPlaceRepository repositoriovegano) {
        mveganrepository=repositoriovegano;

        restaurante=mveganrepository.getrestaurantesbyname();

    }

    public void setbusqueda(String busqueda){
        this.busqueda = busqueda;
        mveganrepository.setbusqueda(busqueda);
    }

    public void onRefresh() {
        mveganrepository.dofectchdatosmapa(busqueda);
    }
    public void setfiltrores(String address) {       mveganrepository.setfiltrores(address);}
    public LiveData<List<Location>> getlocalizaciones() {      return mveganrepository.getlocalizaciones();  }
    public LiveData<List<Result>> getrestaurantes() {      return mveganrepository.getrestaurantes(); }
    public LiveData<Result> getRestaurante(){return restaurante;}
    public void insertarreseniafirebase(Resenia resenia){ mveganrepository.insertarreseniafirebase(resenia);}

}




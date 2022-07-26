package com.example.veganplace.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelmapas.Location;
import com.example.veganplace.data.modelmapas.Photo;
import com.example.veganplace.data.modelmapas.Result;
import com.example.veganplace.data.modelusuario.Resenia;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final VeganPlaceRepository mveganrepository;
    private final LiveData<List<Location>> localizaciones;
    private final LiveData<List<Result>> restaurantes;
    private final LiveData<Result> restaurante;
    private final LiveData<Photo> foto;
    private String busqueda= " ";





    public HomeViewModel(VeganPlaceRepository repositoriovegano) {
        mveganrepository=repositoriovegano;
        localizaciones=mveganrepository.getlocalizaciones();
        restaurantes=mveganrepository.getrestaurantes();
        restaurante=mveganrepository.getrestaurantesbyname();
        foto = mveganrepository.getfotobyaddress();
    }

    public void setbusqueda(String busqueda){
        this.busqueda = busqueda;
        mveganrepository.setbusqueda(busqueda);
    }

    public void onRefresh() {
        mveganrepository.dofectchdatosmapa(busqueda);
    }
    public void setfiltrores(String address) {       mveganrepository.setfiltrores(address);}
    public LiveData<List<Location>> getlocalizaciones() {      return localizaciones;  }
    public LiveData<List<Result>> getrestaurantes() {      return restaurantes;  }
    public LiveData<Result> getRestaurante(){return restaurante;}
    public LiveData<Photo> getFoto(){return  foto;}
    public void insertarresenia(Resenia resenia){ mveganrepository.insertarresenia(resenia);}
    public void insertarreseniafirebase(Resenia resenia){ mveganrepository.insertarreseniafirebase(resenia);}
}




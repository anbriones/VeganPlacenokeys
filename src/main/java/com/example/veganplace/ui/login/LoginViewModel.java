package com.example.veganplace.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelusuario.User;

public class LoginViewModel extends ViewModel  {
    private final VeganPlaceRepository mveganrepository;
    private final LiveData<User> usuario;
    private final LiveData<User> usuarionombre;


    public LoginViewModel(VeganPlaceRepository repositoryvegan) {
        mveganrepository=repositoryvegan;
        usuario=mveganrepository.getusers();
        usuarionombre=mveganrepository.getuser();
    }
    public void setnombreypassword(String nombre,String password) {       mveganrepository.setnombreypas(nombre,password);}
    public void setnombre(String nombre){  mveganrepository.setnombre(nombre);
    }
    public LiveData<User> getusuarios() {      return usuario;  }
    public LiveData<User> getusuario() {      return usuarionombre;  }

    public void insertarusuario(User usuario){mveganrepository.insertarusuarior(usuario);
    }
}



package com.example.veganplace;

import android.app.Application;

import com.example.veganplace.data.modelusuario.User;

public class MyApplication extends Application {
       public static User usuario=null;
    public AppContainer appContainer;
    @Override
    public void onCreate() {
        super.onCreate();

        //Se  crea aquí porque es necesario pasar el contexto para las bases de datos de room
        appContainer = new AppContainer(this);
        ThemeSetup.applyTheme(this);
    }


    public User getUsuario(){return usuario;}
    public void setUsuario(User usuario){  this.usuario=usuario;
    }
}

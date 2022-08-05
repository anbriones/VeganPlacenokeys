package com.example.veganplace.data.modelusuario;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

public class User implements Serializable {
    public User() {
    }



    private String nombre;

    private String password;

    private String email;
    private String dirimagen;

    public String getDirimagen() {
        return dirimagen;
    }

    public void setDirimagen(String dirimagen) {
        this.dirimagen = dirimagen;
    }

    public User(String nombre, String password, String email,String imagenseleccionada) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.dirimagen=imagenseleccionada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public String getPassword(){return password;}

    public void setPassword(String password){this.password=password;}
}
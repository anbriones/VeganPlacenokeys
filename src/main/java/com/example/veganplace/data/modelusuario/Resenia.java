package com.example.veganplace.data.modelusuario;

public class Resenia {

    private String id_resenia;
    private String name_user;
    private String name_res;
    private String dir_res;
    private String descripcion;
    private double valor;


    public Resenia( String id_resenia,  String name_user,  String name_res,  String dir_res,  String descripcion, double valor) {
        this.id_resenia = id_resenia;
        this.name_user = name_user;
        this.name_res = name_res;
        this.dir_res = dir_res;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public Resenia() {
    }

    public String getDir_res() {
        return dir_res;
    }

    public void setDir_res(String dir_res) {
        this.dir_res = dir_res;
    }


    public String getId_resenia() {
        return id_resenia;
    }

    public void setId_resenia( String id_resenia) {
        this.id_resenia = id_resenia;
    }


    public String getName_user() {
        return name_user;
    }

    public void setName_user( String name_user) {
        this.name_user = name_user;
    }


    public String getName_res() {
        return name_res;
    }

    public void setName_res( String name_res) {
        this.name_res = name_res;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion( String descripcion) {
        this.descripcion = descripcion;
    }


    public double getValor() {
        return valor;
    }

    public void setValor( double valor) {
        this.valor = valor;
    }
}

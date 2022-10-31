package com.example.veganplace.data.modelusuario;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity
public class ChatMessage implements Serializable {
    @SerializedName("key")
    @Expose
    @ColumnInfo(name = "key")
    private String key;
    @SerializedName("key")
    @Expose
    @ColumnInfo(name = "emisor")
    private String emisor;
    @SerializedName("receptor")
    @Expose
    @ColumnInfo(name = "receptor")
    private String receptor;
    @SerializedName("texto")
    @Expose
    @ColumnInfo(name = "texto")
    private String texto;
    @SerializedName("messageTime")
    @Expose
    @ColumnInfo(name = "messageTime")
    @PrimaryKey
    @NonNull
    private String messageTime;

    public ChatMessage(String emisor, String receptor, String texto, String messageTime) {

        this.emisor = emisor;
        this.receptor = receptor;
        this.texto = texto;
        this. messageTime = messageTime;
    }

    public ChatMessage(String key,String emisor, String receptor, String texto, String messageTime) {
       this.key=key;
        this.emisor = emisor;
        this.receptor = receptor;
        this.texto = texto;
       this. messageTime = messageTime;
    }
    public ChatMessage(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
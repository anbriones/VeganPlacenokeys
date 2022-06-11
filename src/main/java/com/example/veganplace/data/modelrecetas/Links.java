
package com.example.veganplace.data.modelrecetas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Links implements Serializable
{

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("next")
    @Expose
    private Next next;
    private final static long serialVersionUID = 4445138146800272431L;

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

}

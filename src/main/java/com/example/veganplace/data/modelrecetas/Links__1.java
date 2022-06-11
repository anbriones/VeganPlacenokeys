
package com.example.veganplace.data.modelrecetas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Links__1 implements Serializable
{

    @SerializedName("self")
    @Expose
    private Self__1 self;
    @SerializedName("next")
    @Expose
    private Next__1 next;
    private final static long serialVersionUID = 2460550511993042419L;

    public Self__1 getSelf() {
        return self;
    }

    public void setSelf(Self__1 self) {
        this.self = self;
    }

    public Next__1 getNext() {
        return next;
    }

    public void setNext(Next__1 next) {
        this.next = next;
    }

}

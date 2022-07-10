
package com.example.veganplace.data.modelrecetas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ingredient")
public class Ingredient {
    @SerializedName("id_ingredient")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private long id_ingrediente;
    @SerializedName("label_creator")
    @Expose
    @ColumnInfo(name = "label_creator")
    private String label_creator;
    @SerializedName("text")
    @Expose
    @ColumnInfo(name = "text")
    private String text;
    @SerializedName("quantity")
    @Expose
    @ColumnInfo(name = "quantity")
    private double quantity;
    @SerializedName("measure")
    @Expose
    @ColumnInfo(name = "measure")
    private String measure;
    @SerializedName("food")
    @Expose
    private String food;
    @SerializedName("weight")
    @Expose
    private double weight;
    @SerializedName("foodId")
    @Expose
    private String foodId;

    public long getId_ingrediente() {return id_ingrediente;    }

    public void setId_ingrediente(long id) { this.id_ingrediente = id;   }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getLabel_creator() {return label_creator;    }

    public void setLabel_creator(String label_creator) {
        this.label_creator = label_creator;
    }
}

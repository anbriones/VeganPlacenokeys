
package com.example.veganplace.data.modelrecetas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ingrediente")
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "labelreceta")
    private String labelreceta;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "quantity")
    private double quantity;
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

    public long getId() {return id;    }

    public void setId(long id) { this.id = id;   }

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

    public String getLabelreceta() {return labelreceta;    }

    public void setLabelreceta(String labelreceta) {
        this.labelreceta = labelreceta;
    }
}

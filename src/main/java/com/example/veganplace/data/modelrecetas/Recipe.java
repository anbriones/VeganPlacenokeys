
package com.example.veganplace.data.modelrecetas;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.veganplace.data.roomdatabase.ListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
@Entity(tableName = "recipe")
public class Recipe implements Serializable {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("label")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "label")
    private String label;
    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    private String image;
    @SerializedName("images")
    @Expose
    @Ignore
    private Images images;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("shareAs")
    @Expose
    private String shareAs;
    @SerializedName("yield")
    @Expose
    private double yield;
    @SerializedName("dietLabels")
    @Expose
    @Ignore
    private List<String> dietLabels = null;
    @SerializedName("healthLabels")
    @Expose
    @Ignore
    private List<String> healthLabels = null;
    @SerializedName("cautions")
    @Expose
    @Ignore
    private List<String> cautions = null;
    @SerializedName("ingredientLines")
    @Expose
    @ColumnInfo(name = "ingredientLines")
    @TypeConverters(ListConverter.class)
    private List<String> ingredientLines = null;
    @SerializedName("ingredients")
    @Expose
    @Ignore
    private List<Ingredient> ingredients = null;
    @SerializedName("calories")
    @Expose
    @ColumnInfo(name = "calories")
    private String calories;
    @SerializedName("glycemicIndex")
    @Expose
    private double glycemicIndex;
    @SerializedName("totalCO2Emissions")
    @Expose
    private double totalCO2Emissions;
    @SerializedName("co2EmissionsClass")
    @Expose
    private String co2EmissionsClass;
    @SerializedName("totalWeight")
    @Expose
    private double totalWeight;
    @SerializedName("cuisineType")
    @Expose
    @Ignore
    private List<String> cuisineType = null;
    @SerializedName("mealType")
    @Expose
    @Ignore
    private List<String> mealType = null;
    @SerializedName("dishType")
    @Expose
    @Ignore
    private List<String> dishType = null;
    @SerializedName("totalNutrients")
    @Expose
    @Ignore
    private TotalNutrients totalNutrients;
    @SerializedName("totalDaily")
    @Expose
    @Ignore
    private TotalDaily totalDaily;
    @SerializedName("digest")
    @Expose
    @Ignore
    private List<Digest> digest = null;




    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public List<String> getCautions() {
        return cautions;
    }

    public void setCautions(List<String> cautions) {
        this.cautions = cautions;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public double getGlycemicIndex() {
        return glycemicIndex;
    }

    public void setGlycemicIndex(double glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }

    public double getTotalCO2Emissions() {
        return totalCO2Emissions;
    }

    public void setTotalCO2Emissions(double totalCO2Emissions) {
        this.totalCO2Emissions = totalCO2Emissions;
    }

    public String getCo2EmissionsClass() {
        return co2EmissionsClass;
    }

    public void setCo2EmissionsClass(String co2EmissionsClass) {
        this.co2EmissionsClass = co2EmissionsClass;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<String> getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(List<String> cuisineType) {
        this.cuisineType = cuisineType;
    }

    public List<String> getMealType() {
        return mealType;
    }

    public void setMealType(List<String> mealType) {
        this.mealType = mealType;
    }

    public List<String> getDishType() {
        return dishType;
    }

    public void setDishType(List<String> dishType) {
        this.dishType = dishType;
    }

    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(TotalNutrients totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public TotalDaily getTotalDaily() {
        return totalDaily;
    }

    public void setTotalDaily(TotalDaily totalDaily) {
        this.totalDaily = totalDaily;
    }

    public List<Digest> getDigest() {
        return digest;
    }

    public void setDigest(List<Digest> digest) {
        this.digest = digest;
    }

}

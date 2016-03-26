package com.abcd.speechifyapptask.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int recipe_id;
    private String recipe_name;
    private ArrayList<Ingredient> recipe_ingredients;
    private int numberOfIngredients;

    public Recipe() {
        numberOfIngredients = 0;
        recipe_ingredients = new ArrayList<>();
        recipe_ingredients.clear();
    }

    public Recipe(int recipe_id,
                      String recipe_name,
                      ArrayList<Ingredient> recipe_ing) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_ingredients = recipe_ing;
    }

    protected Recipe(Parcel in) {
        recipe_id = in.readInt();
        recipe_name = in.readString();
        recipe_ingredients = new ArrayList<Ingredient>();
        recipe_ingredients = in.readArrayList(Ingredient.class.getClassLoader());
        numberOfIngredients = in.readInt();
        //in.readTypedList(recipe_ingredients, Ingredient.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipe_id);
        dest.writeString(recipe_name);
        dest.writeList(recipe_ingredients);
        dest.writeInt(numberOfIngredients);
    }

    public void addIngredient(Ingredient newIng) {
        this.recipe_ingredients.add(newIng);
        this.numberOfIngredients++;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public ArrayList<Ingredient> getRecipe_ingredients() {
        return recipe_ingredients;
    }

    public void setRecipe_ingredients(ArrayList<Ingredient> recipe_ingredients) {
        this.recipe_ingredients = recipe_ingredients;
    }

    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

    public void setNumberOfIngredients(int numberOfIngredients) {
        this.numberOfIngredients = numberOfIngredients;
    }
}

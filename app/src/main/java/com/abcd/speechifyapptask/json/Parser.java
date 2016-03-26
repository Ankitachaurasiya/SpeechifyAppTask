package com.abcd.speechifyapptask.json;

import android.util.Log;

import com.abcd.speechifyapptask.pojo.Ingredient;
import com.abcd.speechifyapptask.pojo.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.abcd.speechifyapptask.json.Utils.contains;
import static com.abcd.speechifyapptask.extras.Keys.RecipeKeys.*;

public class Parser {


    public static ArrayList<Recipe> parseJSONResponse(JSONObject response) {

        ArrayList<Recipe> listRecipe = new ArrayList<Recipe>();

        String NA = "NA";


        if (response != null && response.length() > 0) {
            int id;
            String name;
            ArrayList<Ingredient> ingredients;

            try {
                Log.d("Parser", "Checking SUCCESS");
                if (response.getString(KEY_STATUS).toString().contentEquals("success")) {
                    Log.d("Parser", "Got SUCCESS");
                    JSONArray arrayRecipes = response.getJSONArray(KEY_RECIPE_DATA);

                    for (int i = 0; i < arrayRecipes.length(); i++) {
                        id = -1;
                        name = NA;

                        JSONObject currentRecipe = arrayRecipes.getJSONObject(i);

                        if (contains(currentRecipe, KEY_RECIPE_ID)) {
                            id = currentRecipe.getInt(KEY_RECIPE_ID);
                        }
                        if (contains(currentRecipe, KEY_RECIPE_NAME)) {
                            name = currentRecipe.getString(KEY_RECIPE_NAME);
                        }

                        JSONArray arrayIngredients = new JSONArray();

                        if (contains(currentRecipe, KEY_RECIPE_INGREDIENT_DATA)) {
                            arrayIngredients = currentRecipe.getJSONArray(KEY_RECIPE_INGREDIENT_DATA);
                        }

                        Recipe recipe = new Recipe();
                        recipe.setRecipe_id(id);
                        recipe.setRecipe_name(name);


                        ingredients = new ArrayList<Ingredient>();
                        for (int j = 0; j < arrayIngredients.length(); j++){
                            id = -1;
                            name = NA;

                            JSONObject currentIngredient = arrayIngredients.getJSONObject(j);
                            if (contains(currentIngredient, KEY_INGREDIENT_ID)) {
                                id = currentIngredient.getInt(KEY_INGREDIENT_ID);
                            }
                            if (contains(currentIngredient, KEY_INGREDIENT_NAME)) {
                                name = currentIngredient.getString(KEY_INGREDIENT_NAME);
                            }

                            Ingredient ingredient = new Ingredient(id, name);
                            recipe.addIngredient(ingredient);
                        }

                            listRecipe.add(recipe);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listRecipe;
    }
}

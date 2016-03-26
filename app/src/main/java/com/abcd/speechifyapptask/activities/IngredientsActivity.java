package com.abcd.speechifyapptask.activities;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abcd.speechifyapptask.R;
import com.abcd.speechifyapptask.adapter.AdapterRecipeList;
import com.abcd.speechifyapptask.network.VolleySingleton;
import com.abcd.speechifyapptask.pojo.Ingredient;
import com.abcd.speechifyapptask.pojo.Recipe;
import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class IngredientsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Recipe currentRecipe;
    private ArrayList<Ingredient> listIngredients = new ArrayList<>();
    private String[] listItems;
    private ListView ingList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        //currentRecipe = new Recipe();
        currentRecipe = getIntent().getExtras().getParcelable("CURRENT_RECIPE");
        listIngredients = currentRecipe.getRecipe_ingredients();
        int n = currentRecipe.getNumberOfIngredients();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(currentRecipe.getRecipe_name());
        getSupportActionBar().setSubtitle(n + " ingredients");

        if (Build.VERSION.SDK_INT >= 21)
            toolbar.setElevation(Float.parseFloat("4"));

        listItems = new String[n];

        for (int i = 0; i < n; i++){
            listItems[i] = listIngredients.get(i).getName();
        }

            ingList = (ListView) findViewById(R.id.ingredient_list);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        ingList.setAdapter(adapter);
        /*ingList.setLayoutManager(new LinearLayoutManager(this));
        adapterIngredientList = new AdapterIngredientList(this);
        ingList.setAdapter(adapterIngredientList);
*/

    }
}

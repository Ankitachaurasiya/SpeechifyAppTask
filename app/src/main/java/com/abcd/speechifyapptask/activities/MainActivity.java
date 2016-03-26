package com.abcd.speechifyapptask.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.abcd.speechifyapptask.adapter.AdapterRecipeList;
import com.abcd.speechifyapptask.R;
import com.abcd.speechifyapptask.pojo.Recipe;
import com.abcd.speechifyapptask.extras.L;
import com.abcd.speechifyapptask.network.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.abcd.speechifyapptask.json.EndPoints.getRequestUrl;
import static com.abcd.speechifyapptask.json.Parser.parseJSONResponse;

public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<Recipe> listRecipes = new ArrayList<>();
    private AdapterRecipeList adapterRecipeList;
    private RecyclerView recipeList;
    private TextView textVolleyError;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String STATE_RECIPES = "state_recipes";

    private int flagSwipeReminder;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_RECIPES, listRecipes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        flagSwipeReminder = 0;

        if (Build.VERSION.SDK_INT >= 21)
            toolbar.setElevation(Float.parseFloat("4"));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRecipeList);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryLight);

        swipeRefreshLayout.setOnRefreshListener(this);

        textVolleyError = (TextView) findViewById(R.id.textVolleyError);
        recipeList = (RecyclerView) findViewById(R.id.recipe_list);
        recipeList.setLayoutManager(new LinearLayoutManager(this));
        adapterRecipeList = new AdapterRecipeList(this);
        recipeList.setAdapter(adapterRecipeList);

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();

        if (savedInstanceState != null) {
            listRecipes = savedInstanceState.getParcelableArrayList(STATE_RECIPES);
            adapterRecipeList.setRecipeList(listRecipes);
        } else {
            if (listRecipes.isEmpty()) {
                sendJsonRequest();
            } else {
                adapterRecipeList.setRecipeList(listRecipes);
            }
        }
    }

    private void sendJsonRequest() {
        JSONObject jsonObject = null;

        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(),
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        textVolleyError.setVisibility(View.GONE);
                        listRecipes = parseJSONResponse(response);
                        adapterRecipeList.setRecipeList(listRecipes);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error);
                    }
                });

        requestQueue.add(request);

    }

    private void handleVolleyError(VolleyError error) {

        if (flagSwipeReminder == 0) {
            if (listRecipes.isEmpty()) {
                L.t(MainActivity.this, "Swipe Down to Refresh");
                flagSwipeReminder++;
            }
        }

        textVolleyError.setVisibility(View.VISIBLE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if (error instanceof NoConnectionError) {
            textVolleyError.setText(R.string.error_internet);
        } else if (error instanceof TimeoutError) {
            textVolleyError.setText(R.string.error_timeout);
        } else if (error instanceof AuthFailureError) {
            textVolleyError.setText(R.string.error_server);
        } else if (error instanceof ServerError) {
            textVolleyError.setText(R.string.error_server);
        } else if (error instanceof NetworkError) {
            textVolleyError.setText(R.string.error_network);
        } else if (error instanceof ParseError) {
            textVolleyError.setText(R.string.error_parser);
        }
    }

    @Override
    public void onRefresh() {
        textVolleyError.setVisibility(View.GONE);
        sendJsonRequest();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     /*   if (id == R.id.action_settings) {
            return true;
        }
*/
        if (id == R.id.dev_info) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String devInfo = "Name: Vishal Sharma\n\nPhone: 8860907823 , 9609449679\n\nMail: vishalsh94@yahoo.in";
            builder.setMessage(devInfo)
                    .setTitle("Developer Info")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and show it
            builder.create();
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
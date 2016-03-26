package com.abcd.speechifyapptask.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.abcd.speechifyapptask.activities.IngredientsActivity;
import com.abcd.speechifyapptask.R;
import com.abcd.speechifyapptask.anim.AnimationUtils;
import com.abcd.speechifyapptask.extras.L;
import com.abcd.speechifyapptask.pojo.Recipe;

import java.util.ArrayList;

public class AdapterRecipeList extends RecyclerView.Adapter<AdapterRecipeList.ViewHolderRecipe> {

    private ArrayList<Recipe> listRecipes = new ArrayList<Recipe>();
    private LayoutInflater layoutInflater; 
    private int previousPosition = 0;
    private Context c;

    public AdapterRecipeList(Context context) {
        layoutInflater = LayoutInflater.from(context);
        c = context;
    }

    public void setRecipeList(ArrayList<Recipe> listR) {
        this.listRecipes = listR;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderRecipe onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_recipe_list, parent, false);
        ViewHolderRecipe viewHolder = new ViewHolderRecipe(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderRecipe holder, int position) {

        final Recipe currentRecipe = listRecipes.get(position);

        holder.recipeName.setText(currentRecipe.getRecipe_name());
        holder.recipeIngNum.setText(String.valueOf( currentRecipe.getNumberOfIngredients())+" ingredients" );

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent ourIntent = new Intent(c, IngredientsActivity.class).putExtra("CURRENT_RECIPE", currentRecipe);
                    c.startActivity(ourIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    L.t(c, "Activity Not Found!");
                }
            }
        });


        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;
    }

    @Override
    public int getItemCount() {
        return listRecipes.size();
    }

    static class ViewHolderRecipe extends RecyclerView.ViewHolder {

        private TextView recipeName;
        private TextView recipeIngNum;
        private CardView card;

        public ViewHolderRecipe(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_title);
            recipeIngNum = (TextView) itemView.findViewById(R.id.recipe_ing_num);
            card = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
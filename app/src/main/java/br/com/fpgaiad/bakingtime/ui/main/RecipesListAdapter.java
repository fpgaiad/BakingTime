package br.com.fpgaiad.bakingtime.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;

public class RecipesListAdapter extends
        RecyclerView.Adapter<RecipesListAdapter.RecipesListViewHolder>{

    private Context context;
    private List<Recipe> recipesList;
    private final RecipeListItemClickListener mOnClickListener;

    public RecipesListAdapter(Context context,
                              List<Recipe> recipesList, RecipeListItemClickListener listener) {
        this.context = context;
        this.recipesList = recipesList;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public RecipesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_list_item, parent, false);
        return new RecipesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesListViewHolder holder, int position) {

        Recipe recipe = recipesList.get(position);
        String recipeId = String.valueOf(recipe.getId()) + context.getString(R.string.simple_dot);
        String servings = context.getString(R.string.servings_label) + recipe.getServings();

        holder.tvRecipeId.setText(recipeId);
        holder.tvRecipeName.setText(recipe.getName());
        holder.tvRecipeServings.setText(servings);
    }

    @Override
    public int getItemCount() {
        return recipesList == null ? 0 : recipesList.size();
    }


    class RecipesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvRecipeName;
        TextView tvRecipeServings;
        TextView tvRecipeId;

        public RecipesListViewHolder(View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvRecipeServings = itemView.findViewById(R.id.tv_recipe_servings);
            tvRecipeId = itemView.findViewById(R.id.tv_recipe_id);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public interface RecipeListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}

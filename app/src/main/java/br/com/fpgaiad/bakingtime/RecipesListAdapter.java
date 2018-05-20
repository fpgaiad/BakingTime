package br.com.fpgaiad.bakingtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesListViewHolder>{

    private Context context;

    public RecipesListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecipesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipes_list_item, parent, false);
        return new RecipesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesListViewHolder holder, int position) {
        holder.tvRecipeName.setText("Receitas Caseiras");
        holder.tvRecipeServings.setText("servings: 5");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class RecipesListViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecipeName;
        TextView tvRecipeServings;

        public RecipesListViewHolder(View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvRecipeServings = itemView.findViewById(R.id.tv_recipe_servings);
        }
    }
}

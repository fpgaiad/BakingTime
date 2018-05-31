package br.com.fpgaiad.bakingtime.ui.recipe_detail.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Ingredient;

public class IngredientsListAdapter extends
        RecyclerView.Adapter<IngredientsListAdapter.IngredientsListViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientsListAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.mIngredientName.setText(ingredient.getIngredient());
        holder.mQuantity.setText(String.valueOf(ingredient.getQuantity()));
        holder.mMeasure.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredientList == null ? 0 : ingredientList.size();
    }

    class IngredientsListViewHolder extends RecyclerView.ViewHolder {

        private TextView mIngredientName;
        private TextView mQuantity;
        private TextView mMeasure;

        public IngredientsListViewHolder(View itemView) {
            super(itemView);

            mIngredientName = itemView.findViewById(R.id.tv_ingredient);
            mQuantity = itemView.findViewById(R.id.tv_quantity);
            mMeasure = itemView.findViewById(R.id.tv_measure);

        }
    }
}

package br.com.fpgaiad.bakingtime.ui.recipe_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;

public class RecipeDetailFragment extends Fragment
        implements StepsListAdapter.StepsListItemClickListener {

    private Recipe mRecipe;
    private Toast mToast;

    // Empty constructor required
    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater
                .inflate(R.layout.fragment_recipe_detail, container, false);

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(getString(R.string.current_recipe_key));
            return rootView;
        }


        rootView.findViewById(R.id.tv_ingredients_label);
        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_recycler_view);
        RecyclerView stepsRecyclerView = rootView.findViewById(R.id.steps_recycler_view);

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientsRecyclerView.setAdapter(new IngredientsListAdapter(mRecipe.getIngredients()));

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecyclerView.setAdapter(new StepsListAdapter(mRecipe.getSteps(), this));

        return rootView;
    }

    public void setRecipe (Recipe recipe) {
        mRecipe = recipe;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.current_recipe_key), mRecipe);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked";
        mToast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT);
        mToast.show();
    }
}


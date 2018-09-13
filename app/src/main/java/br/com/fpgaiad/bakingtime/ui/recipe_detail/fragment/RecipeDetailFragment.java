package br.com.fpgaiad.bakingtime.ui.recipe_detail.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;
import br.com.fpgaiad.bakingtime.ui.recipe_detail.adapters.IngredientsListAdapter;
import br.com.fpgaiad.bakingtime.ui.recipe_detail.adapters.StepsListAdapter;

public class RecipeDetailFragment extends Fragment
        implements StepsListAdapter.StepsListItemClickListener {

    private Recipe mRecipe;
    OnStepClickListener mCallback;

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
            return null;
        }

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
        mCallback.onStepSelected(clickedItemIndex);
    }

    //Passing clicked item from fragment to it's host activity:
    //1. Define an interface;
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }
    //2. Make sure that the host activity has implemented the callback interface
    //If not implemented, it throws an exception
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }
}


package br.com.fpgaiad.bakingtime.ui.recipe_detail.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Step;
import br.com.fpgaiad.bakingtime.ui.BaseStepActivity;
import br.com.fpgaiad.bakingtime.ui.recipe_detail.fragment.RecipeDetailFragment;
import br.com.fpgaiad.bakingtime.ui.step_detail.RecipeStepsActivity;

public class RecipeDetailActivity extends BaseStepActivity
        implements RecipeDetailFragment.OnStepClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe = getIntent().getParcelableExtra(getString(R.string.recipe_extra));
        stepIndex = getIntent().getIntExtra(getString(R.string.step_index_extra), 0);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipe.getName());
        }

        if (findViewById(R.id.two_pane_mode) != null) {
            mTwoPane = true;
            showStepFragment(recipe, stepIndex);
        } else {
            mTwoPane = false;
        }

        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        detailFragment.setRecipe(recipe);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, detailFragment)
                .commit();
    }

    @Override
    public void onStepSelected(int position) {
        Step step = recipe.getSteps().get(position);

        if (!mTwoPane) {
            Intent intent = new Intent(this, RecipeStepsActivity.class);
            intent.putExtra(getString(R.string.step_extra), step);
            intent.putExtra(getString(R.string.recipe_extra), recipe);
            intent.putExtra(getString(R.string.step_index_extra), position);
            startActivity(intent);
        } else {
            showStepFragment(recipe, position);
        }
    }
}

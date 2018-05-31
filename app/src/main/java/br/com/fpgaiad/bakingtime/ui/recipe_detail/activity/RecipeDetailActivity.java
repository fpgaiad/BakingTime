package br.com.fpgaiad.bakingtime.ui.recipe_detail.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;
import br.com.fpgaiad.bakingtime.entities.Step;
import br.com.fpgaiad.bakingtime.ui.recipe_detail.fragment.RecipeDetailFragment;
import br.com.fpgaiad.bakingtime.ui.step_detail.RecipeStepsActivity;
import br.com.fpgaiad.bakingtime.ui.step_detail.RecipeStepsFragment;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailFragment.OnStepClickListener {

    private Recipe recipe;
    public boolean mTwoPane;
    private Step step;
    List<Step> stepList;
    private int stepIndex;
    private RecipeStepsFragment stepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        stepIndex = getIntent().getIntExtra(getString(R.string.step_index_extra), 0);
        recipe = getIntent().getParcelableExtra(getString(R.string.recipe_extra));
        stepList = recipe.getSteps();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipe.getName());
        }

        if (findViewById(R.id.two_pane_mode) != null) {
            mTwoPane = true;

            stepsFragment = new RecipeStepsFragment();
            step = recipe.getSteps().get(0);
            stepIndex = 0;

            stepsFragment.setStep(step);
            stepsFragment.setRecipe(recipe);
            stepsFragment.setStepIndex(stepIndex);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_container, stepsFragment)
                    .commit();

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
//        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT)
//                .show();

        step = recipe.getSteps().get(position);
//        Log.d("###", step.getDescription());

        if (!mTwoPane) {

            Intent intent = new Intent(this, RecipeStepsActivity.class);
            intent.putExtra(getString(R.string.step_extra), step);
            intent.putExtra(getString(R.string.recipe_extra), recipe);
            intent.putExtra(getString(R.string.step_index_extra), position);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.current_recipe_key), recipe);
    }

    public void goToPreviousStep(View view) {

        if (stepIndex > 0) {
            stepIndex -= 1;
            refreshFragment();
        }
    }

    public void goToNextStep(View view) {

        if (stepIndex < (stepList.size() - 1)) {
            stepIndex += 1;
            refreshFragment();
        }
    }

    private void refreshFragment() {
        step = stepList.get(stepIndex);
        stepsFragment.setStep(step);
        stepsFragment.setStepIndex(stepIndex);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(stepsFragment);
        fragmentTransaction.attach(stepsFragment);
        fragmentTransaction.commit();
    }
}
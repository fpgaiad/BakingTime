package br.com.fpgaiad.bakingtime.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;
import br.com.fpgaiad.bakingtime.entities.Step;
import br.com.fpgaiad.bakingtime.ui.step_detail.RecipeStepsFragment;

public abstract class BaseStepActivity extends AppCompatActivity {

    public int stepIndex;
    public static Recipe recipe;
    public static boolean mTwoPane;

    public void goToPreviousStep(View view) {
        if (stepIndex > 0) {
            showStepFragment(recipe, --stepIndex);
        }
    }

    public void goToNextStep(View view) {
        if (stepIndex < (recipe.getSteps().size() - 1)) {
            showStepFragment(recipe, ++stepIndex);
        }
    }

    public void showStepFragment(Recipe recipe, int index) {
        if (mTwoPane) {
            //Redundant for OnePane mode but necessary for TwoPane mode
            stepIndex = index;
        }
        Step step = recipe.getSteps().get(stepIndex);
        RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipe, step, index);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.step_container, recipeStepsFragment);
        fragmentTransaction.commit();
    }
}

package br.com.fpgaiad.bakingtime.ui.step_detail;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;
import br.com.fpgaiad.bakingtime.entities.Step;

public class RecipeStepsActivity extends AppCompatActivity {

    int stepIndex;
    Recipe recipe;
    Step step;
    List<Step> stepList;
    RecipeStepsFragment stepsFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        step = (Step) getIntent().getSerializableExtra(getString(R.string.step_extra));
        recipe = getIntent().getParcelableExtra(getString(R.string.recipe_extra));
        stepIndex = getIntent().getIntExtra(getString(R.string.step_index_extra), 0);

        stepList = recipe.getSteps();
//        Log.d("###", step.getDescription());
//        Log.d("###", recipe.getName());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipe.getName());
        }

        stepsFragment = new RecipeStepsFragment();
        stepsFragment.setStep(step);
        stepsFragment.setRecipe(recipe);
        stepsFragment.setStepIndex(stepIndex);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.step_container, stepsFragment)
                .commit();
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

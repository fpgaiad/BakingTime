package br.com.fpgaiad.bakingtime.ui.step_detail;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.ui.BaseStepActivity;

public class RecipeStepsActivity extends BaseStepActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        recipe = getIntent().getParcelableExtra(getString(R.string.recipe_extra));
        stepIndex = getIntent().getIntExtra(getString(R.string.step_index_extra), 0);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipe.getName());
        }

        showStepFragment(recipe, stepIndex);
    }
}

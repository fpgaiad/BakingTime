package br.com.fpgaiad.bakingtime.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe recipe = getIntent().getParcelableExtra(getString(R.string.recipe_index_extra));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipe.getName());
        }

        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        detailFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.detail_container, detailFragment)
                .commit();
    }

}

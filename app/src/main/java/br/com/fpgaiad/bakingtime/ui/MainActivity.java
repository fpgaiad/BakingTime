package br.com.fpgaiad.bakingtime.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.RecipesResponse;

public class MainActivity extends AppCompatActivity implements RecipesListAdapter.ListItemClickListener {

    private Toast mToast;
    private RecyclerView mainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.main_recycler_view);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Ion.with(this)
                .load("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                // It must be "asJsonArray()" to treat callback results - Json file initialized as
                // array, not object
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray webResult) {
                        if (e == null) {
                            setResponse(webResult);
                        } else {
                            Log.d(MainActivity.class.getSimpleName(),
                                    getString(R.string.callback_error));
                        }
                    }
                });

    }

    private void setResponse(JsonArray webResult) {
        //Adjusting json file retrieved from the web
        String resultStringWithBrackets = "{\"recipes\":" + String.valueOf(webResult) + "}";
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(resultStringWithBrackets);

        Gson gson = new Gson();
        RecipesResponse recipeList = gson.fromJson(jsonObject, RecipesResponse.class);

        mainRecyclerView.setAdapter(new RecipesListAdapter(this, recipeList.getRecipes(), this));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        mToast.show();
    }
}

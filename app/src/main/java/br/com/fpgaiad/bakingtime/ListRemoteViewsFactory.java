package br.com.fpgaiad.bakingtime;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.fpgaiad.bakingtime.entities.Ingredient;
import br.com.fpgaiad.bakingtime.entities.Recipe;
import br.com.fpgaiad.bakingtime.entities.RecipesResponse;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> listIngredientsWidget;
    private Context mContext;

    public ListRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    private void setResponse(JsonArray webResult) {
        //Adjusting json file retrieved from the web
        String resultStringWithBrackets = "{\"recipes\":" + String.valueOf(webResult) + "}";
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(resultStringWithBrackets);

        Gson gson = new Gson();
        RecipesResponse recipesResponse = gson.fromJson(jsonObject, RecipesResponse.class);

        Recipe recipeId = recipesResponse.getRecipes().get(0);
        listIngredientsWidget = recipeId.getIngredients();
    }

    @Override
    public void onDataSetChanged() {
        try {
            JsonArray jsonArray = Ion.with(mContext)
                    .load("https://d17h27t6h515a5.cloudfront.net" +
                            "/topher/2017/May/59121517_baking/baking.json")
                    // It must be "asJsonArray()" to treat callback results - Json file initialized as
                    // array, not object
                    .asJsonArray()
                    .get();
            setResponse(jsonArray);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        listIngredientsWidget.clear();
    }

    @Override
    public int getCount() {
        return listIngredientsWidget == null ? 0 : listIngredientsWidget.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Ingredient ingredient = listIngredientsWidget.get(position);
        remoteViews.setTextViewText(R.id.tv_ingredient, ingredient.getIngredient());
        remoteViews.setTextViewText(R.id.tv_count, Integer.toString(ingredient.getQuantity()));
        remoteViews.setTextViewText(R.id.tv_measure, ingredient.getMeasure());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

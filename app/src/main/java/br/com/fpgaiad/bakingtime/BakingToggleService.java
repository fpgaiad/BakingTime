package br.com.fpgaiad.bakingtime;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class BakingToggleService extends IntentService {

    public static final String ACTION_TOGGLE_RECIPE = "br.com.fpgaiad.bakingtime.action.toggle_recipe";

    public static void startActionToggleRecipe(Context context) {
        Intent intent = new Intent(context, BakingToggleService.class);
        intent.setAction(ACTION_TOGGLE_RECIPE);
        context.startService(intent);
    }



    public BakingToggleService() {
        super("BakingToggleService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TOGGLE_RECIPE.equals(action)) {
                handleActionToggleRecipe();
            }
        }
    }

    private void handleActionToggleRecipe() {
        Log.d("###", "Called handleActionToggleRecipe");
    }
}

package br.com.fpgaiad.bakingtime;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingIngredientsService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

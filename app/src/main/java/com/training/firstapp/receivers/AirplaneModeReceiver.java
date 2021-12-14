package com.training.firstapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        var state = intent.getExtras().getBoolean("state");

        if(state)
            Toast.makeText(context, "Airplane mode enable", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Airplane mode disable", Toast.LENGTH_LONG).show();
    }
}
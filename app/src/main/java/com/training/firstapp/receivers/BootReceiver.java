package com.training.firstapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BOOOOOOOOOOOOT !!!!!!!!", Toast.LENGTH_LONG).show();
    }
}
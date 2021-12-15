package com.training.firstapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SampleRPCService extends Service {

    private SampleRPCImpl srvImpl = new SampleRPCImpl();
    @Override
    public IBinder onBind(Intent intent) {
        //return new SampleRPCImpl();
        return srvImpl;
    }



}

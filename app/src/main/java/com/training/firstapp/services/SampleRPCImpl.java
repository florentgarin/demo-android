package com.training.firstapp.services;

import android.util.Log;

import java.util.List;

public class SampleRPCImpl extends SampleRPCInterface.Stub {



    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {
        Log.i("SampleRPC", "basicTypes: " + anInt + ", " + aLong + ", " + aBoolean + ", " + aFloat + ", " + aDouble + ", " + aString);
    }

    @Override
    public void test(List lst) {
        //
        Log.i("SampleRPC", "test: " + lst);
    }
}

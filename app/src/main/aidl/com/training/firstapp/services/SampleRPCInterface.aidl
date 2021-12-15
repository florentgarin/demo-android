package com.training.firstapp.services;
import com.training.firstapp.services.Profile;

interface SampleRPCInterface {

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    oneway void test(in List lst);

    Profile getProfile();

}
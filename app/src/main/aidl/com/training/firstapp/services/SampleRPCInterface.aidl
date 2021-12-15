package com.training.firstapp.services;

interface SampleRPCInterface {

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    oneway void test(in List lst);

}
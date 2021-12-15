package com.training.firstapp.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class LongRunningService extends JobService {


    private static ExecutorService singleWorker = Executors.newFixedThreadPool(3);
    private Handler mainHandler =  new Handler(Looper.getMainLooper());

    public LongRunningService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        singleWorker.submit(() -> doWork(params));

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(getApplicationContext(), "Stop long task", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void showToast(String message) {
        mainHandler.post(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }

    private void doWork(JobParameters params) {
        showToast("Start long task");
        Log.i("LongRunningService", "Task is currently done");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showToast("Finish long task");
        jobFinished(params, false);
    }



}
package com.training.firstapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.JobIntentService;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.firstapp.R;
import com.training.firstapp.receivers.AirplaneModeReceiver;
import com.training.firstapp.services.LongRunningService;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button loginBtn;
    private EditText loginTxt;
    private EditText passwordTxt;

    private Button jobBtn;

    private int jobId = 0;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);


    private BroadcastReceiver airplaneReceiver = new AirplaneModeReceiver();
    private final static int NUMBER_REQ = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);
        loginBtn = findViewById(R.id.loginBtn);
        loginTxt = findViewById(R.id.loginTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        jobBtn = findViewById(R.id.jobBtn);
        loginBtn.setOnClickListener(this);
        jobBtn.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        var filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneReceiver);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                var login = loginTxt.getText().toString();
                var password = passwordTxt.getText().toString();

                if(Objects.equals(login, password)) {
                    navigateNextScreen();
                }
                break;

            case R.id.jobBtn:
                JobScheduler jobScheduler = getSystemService(JobScheduler.class);

                //var service = new ComponentName("com.training.firstapp", "com.training.firstapp.services.LongRunningService");
                var service = new ComponentName(getApplicationContext(), LongRunningService.class);

                JobInfo jobInfo = new JobInfo.Builder(jobId++, service)
                        .build();
                jobScheduler.schedule(jobInfo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NUMBER_REQ:
                if (resultCode == RESULT_OK) {
                    var number = data.getIntExtra("number", -1);
                    Toast.makeText(this, "number: " + number, Toast.LENGTH_LONG).show();
                }
        }
    }

    private void navigateNextScreen() {
        //var intent = new Intent(getApplicationContext(), ChooseNumberActivity.class);
        //intent.putExtra("data", "blabla");
        //startActivityForResult(intent, NUMBER_REQ);

        var intent = new Intent();
        intent.setClassName("com.training.firstapp", "com.training.firstapp.ui.ChronoActivity");
        startActivity(intent);
    }
}
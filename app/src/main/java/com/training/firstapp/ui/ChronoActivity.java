package com.training.firstapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.training.firstapp.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChronoActivity extends AppCompatActivity {

    private Button resumeBtn;
    private Button pauseBtn;
    private Button resetBtn;
    private TextView chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);
        pauseBtn = findViewById(R.id.pause);
        resumeBtn = findViewById(R.id.resume);
        resetBtn = findViewById(R.id.reset);
        chronometer = findViewById(R.id.chronometer);

        var worker = new Thread(() -> {
            long startT = System.currentTimeMillis();
            while(true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long currentT = System.currentTimeMillis();
                Log.i("ChronoActivity", (currentT-startT) + "");
            }
        });

        resumeBtn.setOnClickListener(btn -> {
            worker.start();
        });

    }
}
package com.training.firstapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.training.firstapp.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ChronoActivity extends AppCompatActivity {

    private Button resumeBtn;
    private Button resetBtn;
    private TextView chronometer;

    private Thread worker;
    private double sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);
        resumeBtn = findViewById(R.id.resume);
        resetBtn = findViewById(R.id.reset);
        chronometer = findViewById(R.id.chronometer);


        resumeBtn.setOnClickListener(btn -> {
            if(worker == null || !worker.isAlive()) {
                startChrono();
                resumeBtn.setText("||");
            }
            else {
                worker.interrupt();
                resumeBtn.setText(">");
            }
        });

        resetBtn.setOnClickListener(b -> {
            worker.interrupt();
            sum = 0;
            chronometer.setText(String.format(Locale.ENGLISH, "%.2f", sum));
        });

    }

    private void startChrono() {
        worker = new Thread(() -> {
            double startT = System.currentTimeMillis();
            double currentT = startT;
            while(!Thread.interrupted()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    break;
                }
                currentT = System.currentTimeMillis();
                double chronoValue = ((sum + currentT - startT)/1000);
                runOnUiThread( () -> chronometer.setText(String.format(Locale.ENGLISH, "%.2f", chronoValue)) );
            }
            sum += currentT - startT;
        });
        worker.start();
    }

}
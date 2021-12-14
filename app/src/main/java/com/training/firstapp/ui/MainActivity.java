package com.training.firstapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.JobIntentService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.firstapp.R;
import com.training.firstapp.receivers.AirplaneModeReceiver;

import java.util.Objects;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button loginBtn;
    private EditText loginTxt;
    private EditText passwordTxt;

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

        loginBtn.setOnClickListener(this);
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
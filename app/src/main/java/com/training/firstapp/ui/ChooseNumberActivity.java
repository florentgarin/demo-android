package com.training.firstapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.training.firstapp.R;

public class ChooseNumberActivity extends AppCompatActivity {

    private EditText numberTxt;
    private Button validBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_number);
        var intent = getIntent();
        var data = intent.getStringExtra("data");
        numberTxt = findViewById(R.id.numberTxt);
        validBtn = findViewById(R.id.validNumber);

        validBtn.setOnClickListener(v -> {
            var number = Integer.parseInt(numberTxt.getText().toString());
            intent.putExtra("number", number);
            setResult(RESULT_OK, intent);
            finish();

        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
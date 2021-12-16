package com.training.firstapp.ui;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.firstapp.R;
import com.training.firstapp.database.Bottle;
import com.training.firstapp.database.WineDBHelper;
import com.training.firstapp.database.WineRoomDatabase;
import com.training.firstapp.receivers.AirplaneModeReceiver;
import com.training.firstapp.services.LongRunningService;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Room;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button loginBtn;
    private EditText loginTxt;
    private EditText passwordTxt;

    private Button jobBtn;
    private Button dbBtn;
    private WineDBHelper dbHelper;
    private SQLiteDatabase database;
    private WineRoomDatabase roomDatabase;

    private int jobId = 0;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private ExecutorService roomThread = Executors.newSingleThreadExecutor();

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
        dbBtn = findViewById(R.id.dbBtn);

        jobBtn = findViewById(R.id.jobBtn);
        loginBtn.setOnClickListener(this);
        jobBtn.setOnClickListener(this);
        dbBtn.setOnClickListener(this);
        dbHelper = new WineDBHelper(getApplicationContext());

        roomDatabase = Room.databaseBuilder(this, WineRoomDatabase.class, "room-wine.db").build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        var filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneReceiver, filter);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneReceiver);
        database.close();
        database = null;
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
                        .setRequiresCharging(true)
                        .build();
                jobScheduler.schedule(jobInfo);
                break;

            case R.id.dbBtn:
                roomThread.submit(() -> deleteBottle("Beaujolais"));
        }
    }
    private void fetchData() {
        var dao = roomDatabase.bottleDAO();
        var lst = dao.findBottlesByYearAndPrice(2004, 10);
        Log.i("Room", lst + "");
    }

    private void updateBottle() {
        var dao = roomDatabase.bottleDAO();
        dao.updateBottle(new Bottle(3, "Mont Bazihac", 2003, 44));

    }
    private void deleteBottle(String title) {
        var dao = roomDatabase.bottleDAO();

        dao.deleteBottle(new Bottle.Title(title));
    }
    private void deleteBottle(int id) {
        var dao = roomDatabase.bottleDAO();

        dao.deleteBottle(new Bottle(id));
    }

    private void insertIntoRoomDB() {
        var dao = roomDatabase.bottleDAO();
        dao.persistBottle(
                new Bottle("chateau bla", 2020, 12),
                new Bottle("Beaujolais", 2021, 2.50),
                new Bottle("Mont Bazihac", 2003, 25));
    }

    private void insertAndQuerySQLiteDB() {
        ContentValues values = new ContentValues();
        values.put("title", "Romanée Conti");
        values.put("year", 2001);
        values.put("price", 5000.90);
        database.insert("bottle", null, values);

        String[] columns = {"id", "title", "year", "price"};
        String selection = "year > ? and price > ?";
        String[] selectionArgs = {"1995", "2000"};
        //select * from bottle where year > ? and price > ?
        String order = "year asc";
        Cursor cursor = database.query("bottle", columns, selection, selectionArgs, null, null, order);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            int year = cursor.getInt(2);
            double price = cursor.getDouble(3);
            Log.i("db", id + ", " + title + ", " + year + ", " + price);
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
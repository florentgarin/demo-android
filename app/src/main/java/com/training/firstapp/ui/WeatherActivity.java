package com.training.firstapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.training.firstapp.R;

import org.json.JSONException;

import static java.lang.Math.*;

import static com.android.volley.Request.Method.*;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue httpWorker;
    private Button searchBtn;
    private EditText cityEdit;
    private TextView temperature;
    private ImageView iconView;

    private final static String OPEN_WEATHER_API_KEY = "5c84d0fd5f8f5f45c1ae129305e8d352";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weither);
        httpWorker = Volley.newRequestQueue(getApplicationContext());

        searchBtn = findViewById(R.id.search);
        cityEdit = findViewById(R.id.city);
        temperature = findViewById(R.id.temperature);
        iconView = findViewById(R.id.icon);

        searchBtn.setOnClickListener(this);

        double result = PI * 43 * cos(5.4f) - abs(-453);
    }

    @Override
    public void onClick(View v) {
        String city = cityEdit.getText().toString();

        var url = new Uri.Builder().scheme("https")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("weather")
                .appendQueryParameter("q", city)
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("appid", OPEN_WEATHER_API_KEY)
                .build();


        var req = new JsonObjectRequest(url.toString(),
                json -> {
                    double temp = 0;
                    try {
                        temp = json.getJSONObject("main").getDouble("temp");
                        temperature.setText(temp + "");

                        var icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");
                        loadWeatherImage(icon);
                    } catch (JSONException e) {
                        temperature.setText("error");
                    }
                },
                e -> Log.e("Weather", "error", e));

        httpWorker.add(req);

    }

    private void loadWeatherImage(String icon) {
        var url = new Uri.Builder().scheme("https")
                .authority("openweathermap.org")
                .appendPath("img")
                .appendPath("w")
                .appendPath(icon + ".png")
                .build();

        var req = new ImageRequest(url.toString(),
                bitmap -> iconView.setImageBitmap(bitmap),
                0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                e -> Log.e("Weather", "error", e));

        httpWorker.add(req);
    }
}
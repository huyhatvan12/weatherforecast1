package com.dev.weather_forecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.weather_forecast.adapter.WeatherItemAdapter;
import com.dev.weather_forecast.methods.Formatter;
import com.dev.weather_forecast.models.Forecast;
import com.dev.weather_forecast.models.Weather;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static int cityId = -1;
    private FirebaseFirestore db;
    private static ImageButton searchViewButton;
    private static TextView currentDate, degree, name, tempMax,
    tempMin, humidity, cloud, pressure, windDeg,
    windSpeed, weatherName, weatherDescription;
    private static ImageView weatherIcon, backgroundImage;
    private static RecyclerView forecastList;
    private static Weather currentWeather;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.current_weather);
        initState();

        searchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(MainActivity.this, SearchView.class);
            }
        });

        getCityId();
        getCurrentWeather(cityId);
    }

    private void getCityId() {
        // Thai Nguyen id
        int defaultCityId = 1566319;
        int id = getIntent().getIntExtra("CITY_ID", defaultCityId);
        cityId = id == -1 ? defaultCityId : id;
    }

    private void initState() {
        db = FirebaseFirestore.getInstance();
        name = (TextView) findViewById(R.id.name);
        cloud = (TextView) findViewById(R.id.cloud);
        degree = (TextView) findViewById(R.id.degree);
        tempMax = (TextView) findViewById(R.id.tempMax);
        tempMin = (TextView) findViewById(R.id.tempMin);
        windDeg = (TextView) findViewById(R.id.windDeg);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        currentDate = (TextView) findViewById(R.id.currentDate);
        weatherName = (TextView) findViewById(R.id.weatherName);
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        forecastList = (RecyclerView) findViewById(R.id.forecastList);
        backgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        searchViewButton = (ImageButton) findViewById(R.id.search_view_btn);
        weatherDescription = (TextView) findViewById(R.id.weatherDescription);
    }

    public static void renderCurrentWeather(Weather weather, boolean changeName, boolean isNow) {
        setWeatherIcon(weather.weatherName);
        setBackgroundImage(weather.weatherName);
        weatherName.setText(weather.weatherName);
        degree.setText(Formatter.kToC(weather.temp));
        tempMax.setText(Formatter.kToC(weather.tempMax));
        tempMin.setText(Formatter.kToC(weather.tempMin));
        cloud.setText(Formatter.percentage(weather.cloud));
        windDeg.setText(Integer.toString(weather.windDeg));
        pressure.setText(Formatter.pressure(weather.pressure));
        weatherDescription.setText(weather.weatherDescription);
        humidity.setText(Formatter.percentage(weather.humidity));
        windSpeed.setText(Formatter.windSpeed(weather.windSpeed));


        if (changeName) {
            name.setText(weather.name);
        }

        if (isNow) {
            currentDate.setText(Formatter.msToDate(weather.dt * 1000) + " now");
        } else {
            currentDate.setText(Formatter.msToDateTime(weather.dt * 1000));
        }
    }

    private static Map<String, Integer> iconMap() {
        Map<String, Integer> icon = new HashMap<>();
        icon.put("Clear", R.drawable.sun);
        icon.put("Clouds", R.drawable.cloud);
        icon.put("Rain", R.drawable.cloud_showers);

        return icon;
    }

    private static Map<String, Integer> imageMap() {
        Map<String, Integer> image = new HashMap<>();
        image.put("Clear", R.drawable.sunny);
        image.put("Clouds", R.drawable.cloudy);
        image.put("Rain", R.drawable.rainy);

        return image;
    }

    private static void setBackgroundImage(String weatherName) {
        Map<String, Integer> image = imageMap();

        int resource = image.containsKey(weatherName) ?
                image.get(weatherName) :
                R.drawable.cloudy;

        backgroundImage.setImageResource(resource);
    }

    private static void setWeatherIcon(String weatherName) {
        Map<String, Integer> icon = iconMap();

        int resource = icon.containsKey(weatherName) ?
                icon.get(weatherName) :
                R.drawable.cloud_sun_rain;

        weatherIcon.setImageResource(resource);
    }

    private void getCurrentWeather(final int cityId) {
        final CollectionReference weathers = db.collection("weathers");
        Query cityQuery = weathers.whereEqualTo("id", cityId);
        cityQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    DocumentSnapshot weatherSnapshot = documentSnapshots.getDocuments().get(0);

                    currentWeather = new Weather(weatherSnapshot);

                    renderCurrentWeather(currentWeather, true, true);

                    getCurrentForecast(cityId);
                }
            }
        });
    }

    private void getCurrentForecast(int cityId) {
        final CollectionReference forecasts = db.collection("forecasts");
        Query forecastQuery = forecasts.whereEqualTo("id", cityId);
        forecastQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    ArrayList<Weather> weathers = new ArrayList<>();
                    List<DocumentSnapshot> docs = documentSnapshots.getDocuments();

                    for (DocumentSnapshot doc: docs) {
                        weathers = doc.toObject(Forecast.class).weathers;
                    }

                    weathers.add(0, currentWeather);

                    renderForecasts(weathers);
                }
            }
        });
    }

    private void renderForecasts(ArrayList<Weather> weathers) {
        forecastList.setAdapter(new WeatherItemAdapter(weathers));
    }

    private void navigate(Context context, Class page) {
        // Thai Nguyen id
        int defaultCityId = 1566319;
        int id = cityId == -1 ? defaultCityId : cityId;
        Intent intent = new Intent(context, page);

        intent.putExtra("CITY_ID", id);
        startActivity(intent);
        finish();
    }
}

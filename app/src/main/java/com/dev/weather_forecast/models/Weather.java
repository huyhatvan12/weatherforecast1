package com.dev.weather_forecast.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class Weather {
    public long dt;
    public double temp, tempMax, tempMin, windSpeed;
    public String name, weatherDescription, weatherName;
    public int id, weatherId, cloud, humidity, pressure, windDeg;

    public Weather() {}

    public Weather(long date,
            String name, String weatherDescription, String weatherName,
            double temp, double tempMax, double tempMin, double windSpeed,
            int id, int weatherId, int cloud, int humidity, int pressure, int windDeg) {
        this.id = id;
        this.dt = date;
        this.name = name;
        this.temp = temp;
        this.cloud = cloud;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.windDeg = windDeg;
        this.humidity = humidity;
        this.pressure = pressure;
        this.weatherId = weatherId;
        this.windSpeed = windSpeed;
        this.weatherName = weatherName;
        this.weatherDescription = weatherDescription;
    }

    public Weather(DocumentSnapshot weatherSnapshot) {
        this.dt = weatherSnapshot.getLong("dt");
        this.name = weatherSnapshot.getString("name");
        this.weatherDescription = weatherSnapshot.getString("weatherDescription");
        this.weatherName = weatherSnapshot.getString("weatherName");
        this.temp = weatherSnapshot.getDouble("temp");
        this.tempMax = weatherSnapshot.getDouble("tempMax");
        this.tempMin = weatherSnapshot.getDouble("tempMin");
        this.windSpeed = weatherSnapshot.getDouble("windSpeed");
        this.id = weatherSnapshot.getLong("id").intValue();
        this.weatherId = weatherSnapshot.getLong("weatherId").intValue();
        this.cloud = weatherSnapshot.getLong("cloud").intValue();
        this.humidity = weatherSnapshot.getLong("humidity").intValue();
        this.pressure = weatherSnapshot.getLong("pressure").intValue();
        this.windDeg = weatherSnapshot.getLong("windDeg").intValue();
    }
}

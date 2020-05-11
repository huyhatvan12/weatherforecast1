package com.dev.weather_forecast.models;

import java.util.ArrayList;

public class Forecast {
    public int id;
    public String name;
    public ArrayList<Weather> weathers;

    public Forecast() {}

    public Forecast(int id, String name, ArrayList<Weather> weathers) {
        this.id = id;
        this.name = name;
        this.weathers = weathers;
    }
}


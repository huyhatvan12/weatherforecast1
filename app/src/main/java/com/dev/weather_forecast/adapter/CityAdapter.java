package com.dev.weather_forecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dev.weather_forecast.R;
import com.dev.weather_forecast.models.City;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter<City> {
    private Context context;
    private ArrayList<City> cities;
    private int resource;

    public CityAdapter(@NonNull Context context, int resource, ArrayList<City> cities) {
        super(context, resource, cities);
        this.context = context;
        this.resource = resource;
        this.cities = cities;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, null);
        TextView cityName = (TextView) view.findViewById(R.id.city_name);
        City city = cities.get(position);

        cityName.setText(city.name);

        return view;
    }
}
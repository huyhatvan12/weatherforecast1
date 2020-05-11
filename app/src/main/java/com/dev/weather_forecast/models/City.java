package com.dev.weather_forecast.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class City {
    public int id;
    public String name;

    public City() {}

    public City(DocumentSnapshot citySnapshot) {
        this.id = citySnapshot.getLong("id").intValue();
        this.name = citySnapshot.getString("name");
    }
}

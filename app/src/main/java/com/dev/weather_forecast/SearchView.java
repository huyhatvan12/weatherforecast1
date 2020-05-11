package com.dev.weather_forecast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.weather_forecast.adapter.CityAdapter;
import com.dev.weather_forecast.models.City;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchView extends AppCompatActivity {
    private int cityId = -1;
    private EditText searchBar;
    private ListView searchResult;
    private ImageButton searchBtn, backBtn;
    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<City> matchedCities = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.search_view);
        getCityId();
        initState();
        getCities();

        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(SearchView.this, MainActivity.class);
            }
        });

        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityId = matchedCities.get(position).id;
                navigate(SearchView.this, MainActivity.class);
            }
        });
    }

    private void getCityId() {
        cityId = getIntent().getIntExtra("CITY_ID", -1);
    }

    private void initState() {
        backBtn = (ImageButton) findViewById(R.id.back_btn);
        searchBar = (EditText) findViewById(R.id.search_bar);
        searchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchResult = (ListView) findViewById(R.id.search_result);
    }

    private void search() {
        String keyword = searchBar.getText().toString().toLowerCase();

        if (!keyword.isEmpty()) {
            matchedCities = new ArrayList<>();

            for (City city: cities) {
                if (city.name.toLowerCase().contains(keyword)) {
                    matchedCities.add(city);
                }
            }

            renderListResult(matchedCities);
        }
    }

    private void renderListResult(ArrayList<City> cities) {
        CityAdapter adapter = new CityAdapter(SearchView.this, R.layout.city_item, cities);
        searchResult.setAdapter(adapter);
    }

    private void getCities() {
        final CollectionReference citiesCollection = db.collection("cities");

        citiesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> docs = documentSnapshots.getDocuments();

                    for (DocumentSnapshot doc: docs) {
                        cities.add(new City(doc));
                    }

                    matchedCities = cities;
                    renderListResult(matchedCities);
                }
            }
        });
    }

    private void navigate(Context context, Class page) {
        Intent intent = new Intent(context, page);

        intent.putExtra("CITY_ID", cityId);
        startActivity(intent);
        finish();
    }
}

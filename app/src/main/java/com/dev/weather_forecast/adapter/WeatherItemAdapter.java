package com.dev.weather_forecast.adapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.weather_forecast.MainActivity;
import com.dev.weather_forecast.R;
import com.dev.weather_forecast.methods.Formatter;
import com.dev.weather_forecast.models.Weather;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.MyViewHolder> {
    private ArrayList<Weather> weathers;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        LinearLayout weatherItem;
        TextView time, temp, date;

        MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            temp = (TextView) view.findViewById(R.id.temp);
            icon = (ImageView) view.findViewById(R.id.icon);
            weatherItem = (LinearLayout) view.findViewById(R.id.weather_item);
        }
    }

    public WeatherItemAdapter(ArrayList<Weather> weathers) {
        this.weathers = weathers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Weather weather = weathers.get(position);

        HashMap<String, Integer> iconMap = iconMap();

        holder.time.setText(position == 0 ? "now" : Formatter.msToTime(weather.dt * 1000));
        holder.temp.setText(Formatter.kToC(weather.temp));
        holder.date.setText(Formatter.msToDay(weather.dt * 1000));
        holder.weatherItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity.renderCurrentWeather(weather, false, position == 0);

                return false;
            }
        });

        if (iconMap.containsKey(weather.weatherName)) {
            holder.icon.setImageResource(iconMap.get(weather.weatherName));
        } else {
            holder.icon.setImageResource(R.drawable.cloud);
        }
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    private HashMap<String, Integer> iconMap() {
        HashMap<String, Integer> icon = new HashMap<>();
        icon.put("Clear", R.drawable.sun);
        icon.put("Clouds", R.drawable.cloud);
        icon.put("Rain", R.drawable.cloud_showers);

        return icon;
    }
}

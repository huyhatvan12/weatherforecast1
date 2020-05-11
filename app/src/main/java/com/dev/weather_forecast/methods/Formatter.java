package com.dev.weather_forecast.methods;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Formatter {
    public static String msToDate(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        return formatter.format(calendar.getTime());
    }

    public static String msToDateTime(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        return formatter.format(calendar.getTime());
    }

    public static String msToTime(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        return formatter.format(calendar.getTime());
    }

    public static String msToDay(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        return formatter.format(calendar.getTime());
    }

    public static String kToC(double k) {
        return String.format("%.0f", k - 273.15) + "°C";
    }

    public static String pressure(double pressure) {
        return Double.toString(pressure) + " hPa";
    }

    public static String windSpeed(double speed) {
        return Double.toString(speed) + " km/h";
    }

    public static String percentage(int value) {
        return Integer.toString(value) + "%";
    }
}

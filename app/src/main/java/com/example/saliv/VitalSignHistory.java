package com.example.saliv;

public class VitalSignHistory {
    private String date;
    private int heartRate;
    private int spO2;
    private float temperature;

    public VitalSignHistory(String date, int heartRate, int spO2, float temperature) {
        this.date = date;
        this.heartRate = heartRate;
        this.spO2 = spO2;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getSpO2() {
        return spO2;
    }

    public float getTemperature() {
        return temperature;
    }
}

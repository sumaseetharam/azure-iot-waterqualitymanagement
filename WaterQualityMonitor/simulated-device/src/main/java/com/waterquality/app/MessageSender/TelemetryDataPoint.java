package com.waterquality.app.MessageSender;

import com.google.gson.Gson;


public class TelemetryDataPoint {
    public String deviceId;
    public String localDateTime;
    public double temperature;
    public double ph;
	public double dissolvedoxygen;

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

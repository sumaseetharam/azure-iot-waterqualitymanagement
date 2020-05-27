package com.waterquality.app;

/**
 * Analyzes the water quality parameters
 */
public class Analyser {
    private double tempTrigger=65;
    private double pHTrigger=8.5;

private double dissolvedOxygenTrigger=8;


    public Analyser (double tempTrigger,double pHTrigger,double dissolvedOxygenTrigger) {
        humidTrigger = pHumidTrigger;
    }

    public boolean checkWaterQuality(String s) {
        String[] tokens = s.split(",");
Double temp;
Double ph;
Double do;
        for (String token : tokens) {
            if (token.contains("temp")||(token.contains("ph"))||(token.contains("dissolvedoxygen"))) {

                token = token.substring(token.indexOf(":")+1, token.length()-1);
                 temp = Double.valueOf(token);
                System.out.println("Temp" + temp);
                System.out.println("T temprigger: " + tempTrigger);

                if ((temp < tempTrigger)|| (ph < pHTrigger)||(do < dissolvedOxygenTrigger)) {
                    System.out.println("Water quality log. Send Notification!");
                    DeviceMessenger.sendDeviceMethod(App.iotHubConnectionString, App.deviceId,"water", App.responseTimeout, App.connectTimeout);
                }
            }
        }


        return false;
    }
}

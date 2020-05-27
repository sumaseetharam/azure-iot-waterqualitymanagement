package com.waterquality.app.DeviceManagement;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;
import com.waterquality.app.App;
import com.waterquality.app.SensorSimulation;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jan on 17.07.2017.
 */
public class AlertThread implements Runnable{

    public void run() {
        try {
            System.out.println("Alerting ...");
            Thread.sleep(5000);
            Property property = new Property("lastAlert", LocalDateTime.now());
            Set<Property> properties = new HashSet<Property>();
            properties.add(property);
            App.getDeviceClient().sendReportedProperties(properties);

            SensorSimulation sm = App.getSm();
            sm.waterPlant();

            System.out.println("Alerted");
        }
        catch (Exception ex) {
            System.out.println("Exception in reboot thread: " + ex.getMessage());
        }
    }
}

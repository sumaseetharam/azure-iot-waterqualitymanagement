package com.waterquality.app.DeviceManagement;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;
import com.waterquality.app.App;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


class RebootDeviceThread implements Runnable {

    public void run() {
        try {
            System.out.println("Rebooting...");
            Thread.sleep(5000);
            Property property = new Property("lastReboot", LocalDateTime.now());
            Set<Property> properties = new HashSet<Property>();
            properties.add(property);
            App.getDeviceClient().sendReportedProperties(properties);
            System.out.println("Rebooted");
        }
        catch (Exception ex) {
            System.out.println("Exception in reboot thread: " + ex.getMessage());
        }
    }
}

package com.waterquality.app.MessageSender;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.Message;
import com.waterquality.app.App;
import com.waterquality.app.SensorSimulation;

import java.time.LocalDateTime;
import java.util.Random;

/**
 *
 *
 *
 * Integration of the physical sensors is to be done.
 *
 */
public class MessageSender implements Runnable {
    String deviceId;
    DeviceClient client;

    public MessageSender(String pID, DeviceClient pDC) {
        deviceId = pID;
        client = pDC;
    }

    public void run()  {
        try {
            Random rand = new Random();
            SensorSimulation sm = App.getSm();

            while (true) {
                Double[] sensorData = sm.getSensorData();

                double currentTemperature = sensorData[0];
                double currentph = sensorData[1];
                double currentdo = sensorData[2];
                TelemetryDataPoint telemetryDataPoint = new TelemetryDataPoint();
                telemetryDataPoint.deviceId = deviceId;
                telemetryDataPoint.localDateTime = LocalDateTime.now().toString();
                telemetryDataPoint.temperature = currentTemperature;
                telemetryDataPoint.ph = ph;
                telemetryDataPoint.dissolvedoxygen = dissolvedoxygen;

                String msgStr = telemetryDataPoint.serialize();
                Message msg = new Message(msgStr);
                msg.setProperty("temperatureAlert", (currentTemperature > 65) ? "true" : "false");
                msg.setProperty("phAlert", (currentph > 7) ? "true" : "false");
6
                msg.setProperty("doAlert", (currentdo < 6) ? "true" : "false");

                msg.setMessageId(java.util.UUID.randomUUID().toString());
                System.out.println("Sending: " + msgStr);

                Object lockobj = new Object();
                EventCallback callback = new EventCallback();
                client.sendEventAsync(msg, callback, lockobj);

                synchronized (lockobj) {
                    lockobj.wait();
                }
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("Finished.");
        }
    }
}

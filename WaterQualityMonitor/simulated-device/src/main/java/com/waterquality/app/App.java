package com.waterquality.app;

import com.microsoft.azure.sdk.iot.device.*;
import com.waterquality.app.DeviceManagement.DeviceTwinStatusCallback;
import com.waterquality.app.DeviceManagement.DirectMethodCallback;
import com.waterquality.app.DeviceManagement.DirectMethodStatusCallback;
import com.waterquality.app.DeviceManagement.PropertyCallback;
import com.waterquality.app.MessageSender.MessageSender;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class App
{
    // TODO: Adjust for your IoT Hub
    private static String connString = "Your Connection String";
    private static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
    private static String deviceId = "YourDeviceID";
    private static DeviceClient client;
    private static SensorSimulation sm;

    public static void main( String[] args ) throws IOException, URISyntaxException {

        sm = new SensorSimulation(15.0, 15.0);

        client = new DeviceClient(connString, protocol);
        client.open();

        MessageSender sender = new MessageSender(deviceId, client);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(sender);

        System.out.println("Starting device client sample...");


        try
        {
            System.out.println("Starting device client sample...");
            client.subscribeToDeviceMethod(new DirectMethodCallback(), null, new DirectMethodStatusCallback(), null);
            client.startDeviceTwin(new DeviceTwinStatusCallback(), null, new PropertyCallback(), null);
            System.out.println("Subscribed to direct methods and polling for reported properties. Waiting...");
        }
        catch (Exception e)
        {
            System.out.println("On exception, shutting down \n" + " Cause: " + e.getCause() + " \n" +  e.getMessage());
            client.close();
            System.out.println("Shutting down...");
        }


        System.out.println("Press ENTER to exit.");
        System.in.read();
        executor.shutdownNow();
        client.closeNow();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }

    public static DeviceClient getDeviceClient() {
        return client;
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static SensorSimulation getSm() {
        return sm;
    }

}

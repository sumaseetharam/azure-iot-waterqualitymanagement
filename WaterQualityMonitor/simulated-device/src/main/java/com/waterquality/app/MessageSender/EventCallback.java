package com.waterquality.app.MessageSender;

import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;


public class EventCallback implements IotHubEventCallback {
    public void execute(IotHubStatusCode status, Object context) {
        System.out.println("IoT Hub responded to message with status: " + status.name());

        if (context != null) {
            synchronized (context) {
                context.notify();
            }
        }
    }
}

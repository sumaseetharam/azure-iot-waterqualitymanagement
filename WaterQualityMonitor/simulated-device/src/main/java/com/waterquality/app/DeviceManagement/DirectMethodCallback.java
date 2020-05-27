package com.waterquality.app.DeviceManagement;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodData;


public class DirectMethodCallback implements com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodCallback
{
    private static final int METHOD_SUCCESS = 200;
    private static final int METHOD_NOT_DEFINED = 404;

    @Override
    public DeviceMethodData call(String methodName, Object methodData, Object context)
    {
        DeviceMethodData deviceMethodData;
        switch (methodName)
        {
            case "reboot" :
            {
                int status = METHOD_SUCCESS;
                System.out.println("Received reboot request");
                deviceMethodData = new DeviceMethodData(status, "Started reboot");
                RebootDeviceThread rebootThread = new RebootDeviceThread();
                Thread t = new Thread(rebootThread);
                t.start();
                break;
            }
            case "water" :
            {
                int status = METHOD_SUCCESS;
                System.out.println("Received Alert request");
                deviceMethodData = new DeviceMethodData(status, "Sent Alert");

                AlertThread alertThread = new AlertThread();
                Thread t = new Thread(alertThread);
                t.start();

                break;
            }
            case "test" :
            {
                int status = METHOD_SUCCESS;
                System.out.println("Test successful!");
                deviceMethodData = new DeviceMethodData(status, "Tested ...");
                RebootDeviceThread rebootThread = new RebootDeviceThread();
                Thread t = new Thread(rebootThread);
                t.start();
                break;
            }
            default:
            {
                int status = METHOD_NOT_DEFINED;
                deviceMethodData = new DeviceMethodData(status, "Not defined direct method " + methodName);
            }
        }
        return deviceMethodData;
    }
}

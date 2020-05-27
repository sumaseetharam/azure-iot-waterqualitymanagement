package com.waterquality.app.DeviceManagement;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.PropertyCallBack;


public class PropertyCallback implements PropertyCallBack<String, String>
{
    public void PropertyCall(String propertyKey, String propertyValue, Object context)
    {
        System.out.println("PropertyKey:     " + propertyKey);
        System.out.println("PropertyKvalue:  " + propertyKey);
    }
}

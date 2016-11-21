package com.ihealth.ihealthlibrary;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.ihealth.communication.control.Bp7sControl;
import com.ihealth.communication.control.BpProfile;
import com.ihealth.communication.manager.iHealthDevicesManager;

/**
 * Created by zhangxu on 16/11/20.
 */

public class BP7SModule extends ReactContextBaseJavaModule {

    private String moduleName = "BP7SModule";
    public BP7SModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return moduleName;
    }
    @ReactMethod
    public void getBattery(String mac) {
        Bp7sControl bp7sControl = iHealthDevicesManager.getInstance().getBp7sControl(mac);
        if (bp7sControl != null) {
            bp7sControl.getBattery();
        }else {
            WritableMap params = Arguments.createMap();
            params.putInt("errorid",400);
            iHealthDeviceManagerModule.sendEvent("Error", params);
        }
    }

    @ReactMethod
    public void getOffLineNum(String mac) {
        Bp7sControl bp7sControl = iHealthDevicesManager.getInstance().getBp7sControl(mac);
        if (bp7sControl != null) {
            bp7sControl.getOfflineNum();
        }else {
            WritableMap params = Arguments.createMap();
            params.putInt("errorid",400);
            iHealthDeviceManagerModule.sendEvent("Error", params);
        }
    }
    @ReactMethod
    public void getOffLineData(String mac) {
        Bp7sControl bp7sControl = iHealthDevicesManager.getInstance().getBp7sControl(mac);
        if (bp7sControl != null) {
            bp7sControl.getOfflineData();
        }else {
            WritableMap params = Arguments.createMap();
            params.putInt("errorid",400);
            iHealthDeviceManagerModule.sendEvent("Error", params);
        }
    }
    @ReactMethod
    public void setUnit(String mac, int unit) {
        Bp7sControl bp7sControl = iHealthDevicesManager.getInstance().getBp7sControl(mac);
        if (bp7sControl != null) {
            bp7sControl.setUnit(unit);
        }else {
            WritableMap params = Arguments.createMap();
            params.putInt("errorid",400);
            iHealthDeviceManagerModule.sendEvent("Error", params);
        }
    }
    @ReactMethod
    public void angleSet(String mac, int leftUpper,int leftLow, int rightUpper, int rightLow) {
        Bp7sControl bp7sControl = iHealthDevicesManager.getInstance().getBp7sControl(mac);
        if (bp7sControl != null) {
            bp7sControl.angleSet((byte)leftUpper,(byte)leftLow,(byte)rightUpper,(byte)rightLow);
        }else {
            WritableMap params = Arguments.createMap();
            params.putInt("errorid",400);
            iHealthDeviceManagerModule.sendEvent("Error", params);
        }
    }
    @ReactMethod
    public void getFunctionInfo(String mac) {
        Bp7sControl bp7sControl = iHealthDevicesManager.getInstance().getBp7sControl(mac);
        if (bp7sControl != null) {
            bp7sControl.getFunctionInfo();
        }else {
            WritableMap params = Arguments.createMap();
            params.putInt("errorid",400);
            iHealthDeviceManagerModule.sendEvent("Error", params);
        }
    }

    @ReactMethod
    public void disconnect(String mac) {
        Bp7sControl bp7sControl = iHealthDevicesManager.getInstance().getBp7sControl(mac);
        if (bp7sControl != null) {
            bp7sControl.disconnect();
        }else {
            WritableMap params = Arguments.createMap();
            params.putInt("errorid",400);
            iHealthDeviceManagerModule.sendEvent("Error",params);
        }
    }


    public static WritableMap handleNotify(String mac, String deviceType, String action, String message) {
        switch (action) {
            case BpProfile.ACTION_BATTERY_BP:

                break;
            case BpProfile.ACTION_ZOREING_BP:

                break;
            case BpProfile.ACTION_ZOREOVER_BP:

                break;
            case BpProfile.ACTION_ONLINE_PRESSURE_BP:

                break;
            case BpProfile.ACTION_ONLINE_PULSEWAVE_BP:

                break;
            case BpProfile.ACTION_ONLINE_RESULT_BP:

                break;
            default:
                break;
        }

        if (message == "") {
            WritableMap params = Arguments.createMap();
            params.putString("mac", mac);
            params.putString("type", deviceType);
            return params;
        }else {
            WritableMap params = Arguments.createMap();
            params.putString("mac", mac);
            params.putString("type", deviceType);
            Utils.jsonToMap(message, params);

            return params;
        }

    }

}

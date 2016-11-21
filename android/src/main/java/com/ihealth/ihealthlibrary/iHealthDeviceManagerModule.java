package com.ihealth.ihealthlibrary;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.ihealth.communication.manager.iHealthDevicesCallback;
import com.ihealth.communication.manager.iHealthDevicesManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by jing on 16/10/20.
 */

public class iHealthDeviceManagerModule extends iHealthBaseModule implements LifecycleEventListener{

    private static final String modelName = "iHealthDeviceManagerModule";
    private static final String TAG = "iHealthModel";


    private final static String  AM3S = "AM3S";
    private final static String  AM4 = "AM4";
    private final static String  PO3 = "PO3";
    private final static String  BP5 = "BP5";
    private final static String  BP3L = "BP3L";
    private final static String  BP7S = "BP7S";
    private final static String  KN550 = "KN550";
    private final static String  HS4S = "HS4S";
    private final static String  HS6 = "HS6";
    private final static String  BG1 = "BG1";
    private final static String  BG5 = "BG5";
    private final static String  BG5L = "BG5L";



    private final static String ScanDevice = "ScanDevice";
    private final static String ScanFinish = "ScanFinish";
    private final static String DeviceConnected = "DeviceConnected";
    private final static String DeviceConnectFailed = "DeviceConnectFailed";
    private final static String DeviceDisconnect = "DeviceDisconnect";


    private int callbackId;

    public iHealthDeviceManagerModule(ReactApplicationContext reactContext) {
        super(TAG, reactContext);
        reactContext.addLifecycleEventListener(this);
        iHealthDevicesManager.getInstance().init(reactContext);
        callbackId = iHealthDevicesManager.getInstance().registerClientCallback(miHealthDevicesCallback);
    }

    private iHealthDevicesCallback miHealthDevicesCallback = new iHealthDevicesCallback() {

        @Override
        public void onScanDevice(String mac, String deviceType, int rssi, Map manufactorData) {
            WritableMap params = Arguments.createMap();
            params.putString("mac", mac);
            params.putString("type",deviceType);
            params.putInt("rssi",rssi);
            sendEvent(ScanDevice, params);
        }

        @Override
        public void onDeviceConnectionStateChange(String mac, String deviceType, int status, int errorID, Map manufactorData) {
            String eventName = null;
            if (status == iHealthDevicesManager.DEVICE_STATE_CONNECTED) {
                eventName = DeviceConnected;
            } else if (status == iHealthDevicesManager.DEVICE_STATE_CONNECTIONFAIL) {
                eventName = DeviceConnectFailed;
            } else if (status == iHealthDevicesManager.DEVICE_STATE_DISCONNECTED) {
                eventName = DeviceDisconnect;
            }
            if (eventName != null) {
                WritableMap params = Arguments.createMap();
                params.putString("mac", mac);
                params.putString("type",deviceType);
                params.putInt("errorid",errorID);
                sendEvent(eventName, params);
            }
        }

        @Override
        public void onUserStatus(String username, int userStatus) {

        }

        @Override
        public void onDeviceNotify(String mac, String deviceType, String action, String message) {
            commandHandleDeviceNotify(mac, deviceType, action, message);
        }

        @Override
        public void onScanFinish() {
            sendEvent(ScanFinish, null);
        }

    };

    private void commandHandleDeviceNotify(String mac, String deviceType, String action, String message) {
        //为了与iOS返回值保持一致，需要进行二次加工
        iHealthBaseModule module = null;
        switch (deviceType) {
            case iHealthDevicesManager.TYPE_BP5:
                module = getReactApplicationContext().getNativeModule(BP5Module.class);
                break;
            case iHealthDevicesManager.TYPE_BP3L:
                module = getReactApplicationContext().getNativeModule(BP3LModule.class);
                break;
            case iHealthDevicesManager.TYPE_550BT:
                module = getReactApplicationContext().getNativeModule(BP550BTModule.class);
                break;
            case iHealthDevicesManager.TYPE_BP7S:
                module = getReactApplicationContext().getNativeModule(BP7SModule.class);
                break;
            case iHealthDevicesManager.TYPE_AM3S:
                break;
            case iHealthDevicesManager.TYPE_AM4:
                module = getReactApplicationContext().getNativeModule(AM4Module.class);
                break;
            case iHealthDevicesManager.TYPE_PO3:
                break;
            case iHealthDevicesManager.TYPE_HS4S:
                break;
            case iHealthDevicesManager.TYPE_HS6:
                break;
            case iHealthDevicesManager.TYPE_BG1:
                break;
            case iHealthDevicesManager.TYPE_BG5:
                break;
            case iHealthDevicesManager.TYPE_BG5l:
                break;
            default:
                module = null;
                break;
        }
        if (module != null) {
            module.handleNotify(mac, deviceType, action, message);
        } else {
            Log.e(TAG, "We do not support this type: " + deviceType);
        }
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(AM3S,iHealthDevicesManager.DISCOVERY_AM3S);
        constants.put(AM4, iHealthDevicesManager.DISCOVERY_AM4);
        constants.put(PO3, iHealthDevicesManager.DISCOVERY_PO3);
        constants.put(BP5, iHealthDevicesManager.DISCOVERY_BP5);
        constants.put(BP3L,iHealthDevicesManager.DISCOVERY_BP3L);
        constants.put(BP7S, iHealthDevicesManager.DISCOVERY_BP7S);
        constants.put(KN550, iHealthDevicesManager.DISCOVERY_BP550BT);
        constants.put(HS4S, iHealthDevicesManager.DISCOVERY_HS4S);
//        constants.put(HS6,);
//        constants.put(BG1, );
        constants.put(BG5, iHealthDevicesManager.DISCOVERY_BG5);
        constants.put(BG5L, iHealthDevicesManager.DISCOVERY_BG5l);


        constants.put(ScanDevice,"ScanDevice");
        constants.put(ScanFinish,"ScanFinish");
        constants.put(DeviceConnected,"DeviceConnected");
        constants.put(DeviceConnectFailed,"DeviceConnectFailed");
        constants.put(DeviceDisconnect,"DeviceDisconnect");

        return constants;
    }

    @Override
    public String getName() {
        return modelName;
    }

    @Override
    public void onHostResume() {
        Log.i(TAG,"onHostResume");
    }

    @Override
    public void onHostPause() {
        Log.i(TAG,"onHostPause");
    }

    @Override
    public void onHostDestroy() {
        iHealthDevicesManager.getInstance().destroy();
        Log.e(TAG,"onHostDestroy");
    }

    @ReactMethod
    public void startDiscovery(double type) {
        iHealthDevicesManager.getInstance().startDiscovery((long)type);
    }

    @ReactMethod
    public void stopDiscovery() {
        iHealthDevicesManager.getInstance().stopDiscovery();
    }

    @ReactMethod
    public void connectDevice(String mac, String type) {
        iHealthDevicesManager.getInstance().connectDevice("", mac, type);
    }

    @ReactMethod
    public void getDevicesIDPS(String mac, Callback callback) {
        String idpsInfo = iHealthDevicesManager.getInstance().getDevicesIDPS(mac);
        WritableMap writableMap = Arguments.createMap();
        Utils.jsonToMap(idpsInfo, writableMap);
        callback.invoke(writableMap);
    }

    @Override
    public void handleNotify(String mac, String deviceType, String action, String message) {
        //Do nothing
    }
}

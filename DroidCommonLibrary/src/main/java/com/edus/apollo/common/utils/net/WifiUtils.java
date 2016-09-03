package com.edus.apollo.common.utils.net;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * utils for wifi and wifiAp
 * Created by panyongqiang on 2016/6/22.
 */
public class WifiUtils {
    public static final String HOT_SPOT_IP_ADDRESS = "192.168.43.1";
    public static int WIFI_AP_STATE_DISABLING = 0;
    public static int WIFI_AP_STATE_DISABLED = 1;
    public static int WIFI_AP_STATE_ENABLING = 2;
    public static int WIFI_AP_STATE_ENABLED = 3;
    public static int WIFI_AP_STATE_FAILED = 4;

    static {
        try {
            Field apField = WifiManager.class
                    .getField("WIFI_AP_STATE_DISABLING");
            Integer apValue = (Integer) apField.get(WifiManager.class);
            WIFI_AP_STATE_DISABLING = apValue.intValue();

            apField = WifiManager.class.getField("WIFI_AP_STATE_DISABLED");
            apValue = (Integer) apField.get(WifiManager.class);
            WIFI_AP_STATE_DISABLED = apValue.intValue();

            apField = WifiManager.class.getField("WIFI_AP_STATE_ENABLING");
            apValue = (Integer) apField.get(WifiManager.class);
            WIFI_AP_STATE_ENABLING = apValue.intValue();

            apField = WifiManager.class.getField("WIFI_AP_STATE_ENABLED");
            apValue = (Integer) apField.get(WifiManager.class);
            WIFI_AP_STATE_ENABLED = apValue.intValue();

            apField = WifiManager.class.getField("WIFI_AP_STATE_FAILED");
            apValue = (Integer) apField.get(WifiManager.class);
            WIFI_AP_STATE_FAILED = apValue.intValue();
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                WIFI_AP_STATE_DISABLING = 0;
                WIFI_AP_STATE_DISABLED = 1;
                WIFI_AP_STATE_ENABLING = 2;
                WIFI_AP_STATE_ENABLED = 3;
                WIFI_AP_STATE_FAILED = 4;
            } else {
                WIFI_AP_STATE_DISABLING = 10;
                WIFI_AP_STATE_DISABLED = 11;
                WIFI_AP_STATE_ENABLING = 12;
                WIFI_AP_STATE_ENABLED = 13;
                WIFI_AP_STATE_FAILED = 14;
            }
            e.printStackTrace();
        }
    }

    public static String getCurrentSsid(Context context) {
        WifiInfo wifiInfo = getWifiManager(context).getConnectionInfo();
        return parseSsidFromWifiInfo(wifiInfo);
    }

    public static String parseSsidFromWifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo != null) {
            return parseValidSsidFromRawSsid(wifiInfo.getSSID());
        }
        return null;
    }

    public static String parseValidSsidFromRawSsid(String ssid){
        if (ssid != null && ssid.length() > 2) {
            if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
        }
        return ssid;
    }


    public static int getWifiState(Context context) {
        return getWifiManager(context).getWifiState();
    }

    public static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static boolean isWifiEnabled(Context context) {
        return getWifiManager(context).isWifiEnabled();
    }

    public static boolean isWifiEnabledOrEnabling(Context context){
        WifiManager wifiManager = getWifiManager(context);
        return wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING || wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }

    public static  boolean isWifiDisableOrDisabling(Context context){
        WifiManager wifiManager = getWifiManager(context);
        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING || wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED);
    }

    public static void openWifi(Context context) {
        WifiManager wifiManager = getWifiManager(context);
        if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING && wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
            wifiManager.setWifiEnabled(true);
        }
    }

    public static void closeWifi(Context context) {
        WifiManager wifiManager = getWifiManager(context);
        if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_DISABLING && wifiManager.getWifiState() != WifiManager.WIFI_STATE_DISABLED) {
            wifiManager.setWifiEnabled(false);
        }
    }

    public static boolean isWifiApEnabled(Context context) {
        try {
            WifiManager wifiManager = getWifiManager(context);
            Method method = wifiManager.getClass().getMethod("isWifiApEnabled");
            return (Boolean) method.invoke(wifiManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean setWifiApEnabled(Context context, WifiConfiguration configuration,
                                           boolean enabled) {
        try {
            WifiManager wifiManager = getWifiManager(context);
            if (enabled) {
                wifiManager.setWifiEnabled(false);
            }

            Method method = wifiManager.getClass().getMethod("setWifiApEnabled",
                    WifiConfiguration.class, boolean.class);
            return (Boolean) method.invoke(wifiManager, configuration, enabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getWifiApState(Context context) {
        int state = WIFI_AP_STATE_DISABLED;
        try {
            WifiManager wifiManager = getWifiManager(context);
            final Method getWifiApState = wifiManager.getClass()
                    .getMethod("getWifiApState");
            state = ((Integer) getWifiApState.invoke(wifiManager));
        } catch (Exception e) {
        }
        return state;
    }

    public static boolean isWifiApDisableOrDisabling(Context context){
        int wifiApState = getWifiApState(context);
        return wifiApState == WIFI_AP_STATE_DISABLING || wifiApState == WIFI_AP_STATE_DISABLED
                || wifiApState == WIFI_AP_STATE_FAILED;
    }

    public static boolean isWifiApDisabled(Context context){
        int wifiApState = getWifiApState(context);
        return wifiApState == WIFI_AP_STATE_DISABLED || wifiApState == WIFI_AP_STATE_FAILED;
    }

    public static boolean isWifiApEnableOrEnabling(Context context){
        int wifiApState = getWifiApState(context);
        return wifiApState == WIFI_AP_STATE_ENABLED || wifiApState == WIFI_AP_STATE_ENABLING;
    }

    public static WifiConfiguration getWifiApConfiguration(Context context) {
        try {
            WifiManager wifiManager = getWifiManager(context);
            Method method = wifiManager.getClass().getMethod(
                    "getWifiApConfiguration");
            WifiConfiguration config = (WifiConfiguration) method
                    .invoke(wifiManager);
            return config;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setWifiApConfiguration(Context context, WifiConfiguration configuration) {
        try {
            WifiManager wifiManager = getWifiManager(context);
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApConfiguration", WifiConfiguration.class);
            return (Boolean) method.invoke(wifiManager, configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void removeNetwork(Context context, String ssid) {
        WifiManager wifiMamager = getWifiManager(context);
        List<WifiConfiguration> configs = wifiMamager.getConfiguredNetworks();
        if (configs != null) {
            for (WifiConfiguration config : configs) {
                if (config.SSID.contains(ssid)) {
                    wifiMamager.removeNetwork(config.networkId);
                    break;
                }
            }
        }
        wifiMamager.saveConfiguration();
    }
}

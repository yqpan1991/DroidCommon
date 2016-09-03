package com.edus.apollo.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * Created by PandaPan on 2016/9/3.
 */
public class BluetoothUtils {

    public static void send(Context context, String path){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        PackageManager pm = context.getPackageManager();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        List<ResolveInfo> list = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean flag = false;
        for (ResolveInfo info : list) {
            if (info.activityInfo.packageName.toLowerCase().contains("bluetooth") || info.activityInfo.name.toLowerCase().contains("bluetooth")) {
                ApplicationInfo appInfo = null;
                try {
                    appInfo = pm.getApplicationInfo(
                            info.activityInfo.packageName,
                            PackageManager.GET_META_DATA);
                } catch (PackageManager.NameNotFoundException e) {

                }
                if (appInfo != null
                        && (appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0
                        && (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    intent.setClassName(info.activityInfo.packageName,
                            info.activityInfo.name);
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            return;
        }
        context.startActivity(intent);
    }
}

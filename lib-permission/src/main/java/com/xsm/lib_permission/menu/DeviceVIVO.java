package com.xsm.lib_permission.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Author: 夏胜明
 * Date: 2018/7/16 0016
 * Email: xiasem@163.com
 * Description:
 */
public class DeviceVIVO extends DeviceDefault {

    @Override
    public void goToDeviceSetting(Context context) {
        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        if (appIntent != null && Build.VERSION.SDK_INT < 23) {
            context.startActivity(appIntent);
        } else {
            super.goToDeviceSetting(context);
        }
    }

}

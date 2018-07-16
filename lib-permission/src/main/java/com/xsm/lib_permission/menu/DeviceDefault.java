package com.xsm.lib_permission.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Author: 夏胜明
 * Date: 2018/7/16 0016
 * Email: xiasem@163.com
 * Description:
 */
public class DeviceDefault implements IDeviceSetting {

    @Override
    public void goToDeviceSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

}

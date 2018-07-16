package com.xsm.lib_permission.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Author: 夏胜明
 * Date: 2018/7/16 0016
 * Email: xiasem@163.com
 * Description:
 */
public class DeviceHUAWEI extends DeviceDefault {

    @Override
    public void goToDeviceSetting(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //华为权限管理
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            super.goToDeviceSetting(context);
        }
    }
}

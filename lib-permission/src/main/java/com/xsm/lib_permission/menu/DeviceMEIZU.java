package com.xsm.lib_permission.menu;

import android.content.Context;
import android.content.Intent;

/**
 * Author: 夏胜明
 * Date: 2018/7/16 0016
 * Email: xiasem@163.com
 * Description:
 */
public class DeviceMEIZU extends DeviceDefault {

    @Override
    public void goToDeviceSetting(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            super.goToDeviceSetting(context);
        }
    }

}

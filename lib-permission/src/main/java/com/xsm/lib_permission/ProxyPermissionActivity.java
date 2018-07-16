package com.xsm.lib_permission;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xsm.lib_permission.core.IPermission;

public class ProxyPermissionActivity extends Activity {
    private static final String PARAM_PERMISSIONS = "param_permissions";
    private static final String PARAM_REQUEST_CODE = "param_request_code";
    private static IPermission permissionListener;

    public static void requestPermissions(Context context, String[] permissions, int requestCode, IPermission iPermission) {
        permissionListener = iPermission;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_permission);
    }
}

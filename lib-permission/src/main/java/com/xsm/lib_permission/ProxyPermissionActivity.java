package com.xsm.lib_permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xsm.lib_permission.core.IPermission;

import java.util.ArrayList;

public class ProxyPermissionActivity extends Activity {
    private static final String TAG = "ProxyPermissionActivity";
    private static final String PARAM_PERMISSIONS = "param_permissions";
    private static final String PARAM_REQUEST_CODE = "param_request_code";
    private static IPermission permissionListener;
    private String[] mPermissions;
    private int mRequestCode;

    public static void requestUserPermission(Context context, String[] permissions, int requestCode, IPermission listener) {
        permissionListener = listener;
        Intent intent = new Intent(context, ProxyPermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PARAM_PERMISSIONS, permissions);
        bundle.putInt(PARAM_REQUEST_CODE, requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_permission);
        mPermissions = getIntent().getStringArrayExtra(PARAM_PERMISSIONS);
        mRequestCode = getIntent().getIntExtra(PARAM_REQUEST_CODE, -1);
        if (mPermissions == null || mPermissions.length == 0 || mRequestCode < 0 || permissionListener == null) {
            finish();
            return;
        }

        //检查是否已授权
        if (PermissionUtils.hasPermission(ProxyPermissionActivity.this, mPermissions)) {
            permissionListener.ganted(mRequestCode);
            finish();
            return;
        }

        ActivityCompat.requestPermissions(ProxyPermissionActivity.this, mPermissions, mRequestCode);
    }


    /**
     * grantResults对应于申请的结果，这里的数组对应于申请时的第二个权限字符串数组。
     * 如果你同时申请两个权限，那么grantResults的length就为2，分别记录你两个权限的申请结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //请求权限成功
        if (PermissionUtils.verifyPermission(ProxyPermissionActivity.this, grantResults)) {
            permissionListener.ganted(mRequestCode);
            finish();
            return;
        }
        ArrayList<String> refusedPermissions = new ArrayList<>();

        if (grantResults == null || permissions == null) {
            permissionListener.denied(mRequestCode, refusedPermissions);
        }

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                refusedPermissions.add(permissions[i]);
            }
        }
        permissionListener.denied(mRequestCode, refusedPermissions);
        finish();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}

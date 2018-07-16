package com.xsm.lib_permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xsm.lib_permission.core.IPermission;

public class ProxyPermissionActivity extends Activity {
    private static final String PARAM_PERMISSIONS = "param_permissions";
    private static final String PARAM_REQUEST_CODE = "param_request_code";
    private static IPermission permissionListener;
    private String[] mPermissions;
    private int mRequestCode;

    public static void requestPermissions(Context context, String[] permissions, int requestCode, IPermission listener) {
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
//        mPermissions == null || mPermissions.length == 0 || mRequestCode < 0 || permissionListener == null
    }
}

package com.xsm.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xsm.lib_permission.PermissionUtils;
import com.xsm.lib_permission.annotation.PermissionDenied;
import com.xsm.lib_permission.annotation.PermissionRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @PermissionRequest(value = Manifest.permission.CAMERA, requestCode = 5)
    public void requestCameraPermissions(View view) {
    }

    @PermissionRequest(value = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION})
    public void requestLocationAndWritePermissions(View view) {
    }

    @PermissionRequest(value = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION})
    public void requestMorePermissions(View view) {
    }

    @PermissionDenied()
    public void denied(ArrayList<String> strings) {
        Log.d(TAG, "denied: " + strings.toString());
    }

}

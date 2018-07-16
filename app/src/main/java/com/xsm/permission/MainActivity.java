package com.xsm.permission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xsm.lib_permission.PermissionUtils;
import com.xsm.lib_permission.annotation.PermissionDenied;
import com.xsm.lib_permission.annotation.PermissionRequest;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @PermissionRequest(value = Manifest.permission.CAMERA)
    public void requestCameraPermissions(View view) {
        Toast.makeText(this, "申请相机权限成功", Toast.LENGTH_SHORT).show();
        // do other thing
    }
    
    

    @PermissionRequest(value = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void requestLocationAndWritePermissions(View view) {
        Toast.makeText(this, "申请定位和写存储卡权限成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionRequest(value = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS})
    public void requestMorePermissions(View view) {
        Toast.makeText(this, "申请多个权限成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied()
    public void denied(ArrayList<String> permissions) {
        StringBuilder builder = new StringBuilder();
        for (String permission : permissions) {
            builder.append(permission);
        }
        Toast.makeText(this, builder.toString(), Toast.LENGTH_SHORT).show();

        for (String permission : permissions) {
            if (Manifest.permission.CAMERA.equals(permission)) {
                PermissionUtils.goToDeviceSetting(MainActivity.this);
            }
        }
    }

}

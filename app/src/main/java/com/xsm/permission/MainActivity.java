package com.xsm.permission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xsm.lib_permission.annotation.PermissionCanceled;
import com.xsm.lib_permission.annotation.PermissionDenied;
import com.xsm.lib_permission.annotation.PermissionRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void requestPermissions(View view) {
        test();
    }

    @PermissionRequest(value = {Manifest.permission.CAMERA})
    public void test() {
        Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied()
    public void denied() {
        Toast.makeText(this, "用户拒绝了权限的申请", Toast.LENGTH_SHORT).show();
    }

    @PermissionCanceled()
    public void cancel() {
        Toast.makeText(this, "用户取消了权限的申请", Toast.LENGTH_SHORT).show();

    }

}

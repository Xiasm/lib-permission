package com.xsm.lib_permission.core;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.xsm.lib_permission.PermissionUtils;
import com.xsm.lib_permission.ProxyPermissionActivity;
import com.xsm.lib_permission.annotation.PermissionDenied;
import com.xsm.lib_permission.annotation.PermissionRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;

/**
 * Author: 夏胜明
 * Date: 2018/7/16 0016
 * Email: xiasem@163.com
 * Description:
 */

@Aspect
public class PermissionAspect {
    private static final String TAG = "PermissionAspect";

    @Pointcut("execution(@com.xsm.lib_permission.annotation.PermissionRequest * *(..)) && @annotation(permissionRequest)")
    public void requestPermission(PermissionRequest permissionRequest) {

    }

    @Around("requestPermission(permissionRequest)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, PermissionRequest permissionRequest)  throws Throwable {
        Context context = null;
        final Object o = joinPoint.getThis();
        if (o instanceof Context) {
            context = (Context) o;
        } else if (o instanceof android.support.v4.app.Fragment) {
            Fragment fragment = (Fragment) o;
            context = fragment.getContext();
        } else if (o instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) o;
            context = fragment.getActivity();
        }

        if (context == null) {
            Log.d(TAG, "aroundJoinPoint: error context is null");
            return;
        }

        if (permissionRequest == null) {
            Log.d(TAG, "aroundJoinPoint: error not PermissionRequest");
            return;
        }

        ProxyPermissionActivity.requestUserPermission(context, permissionRequest.value(), permissionRequest.requestCode(), new IPermission() {
            @Override
            public void ganted(int requestCode) {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void denied(int requestCode, ArrayList<String> refusedPermissions) {
                try {
                    PermissionUtils.invokAnnotation(o, PermissionDenied.class, requestCode, refusedPermissions);
                } catch (Exception e) {}
            }
        });

    }
}

## lib-permission 简单易用的安卓权限请求库

### 简介

&nbsp;&nbsp;&nbsp;&nbsp;lib-permission采用了aop的思想，极大的简化了Android运行时权限处理。使用时只需要在权限申请的方法上加入一行注解，就可简单方便的处理权限的申请。目前可支持在Activity和Fragment的方法里申请权限。

### 优势

&nbsp;&nbsp;&nbsp;&nbsp;lib-permission最大的优势就是使用简单方便，对业务代码无侵入的运行时权限请求。废话不多说，直接看代码对比传统的运行时权限请求和使用lib-permission库的权限请求。

  * 传统的权限请求方式

  ```
    public void openCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_COARSE_LOCATION);
            } else {
                //打开相机逻辑
            }
        } else {
            //打开相机逻辑

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 用户授权，继续执行打开相机逻辑

                } else {
                    //用户拒绝授权，给用户提示

                }
                break;
        }
    }
  ```

  * lib-permission的权限请求方式

  ```
    @PermissionRequest(value = Manifest.permission.CAMERA)
    public void openCamera(View view) {
        //编写打开相机逻辑
        android.hardware.Camera camera = android.hardware.Camera.open();
        android.hardware.Camera.Parameters parameters = camera.getParameters();
        //...

    }

    @PermissionDenied()
    public void denied(ArrayList<String> permissions) {
        for (String permission : permissions) {
            if (Manifest.permission.CAMERA.equals(permission)) {
                //TODO：用户拒绝了权限申请

            }
        }
    }
  ```
可以看得出，采用传统的方法申请运行时权限的逻辑和打开相机的代码逻辑严重藕合在了一起，代码看起来很混乱，也提高了调试和测试的成本。<br/>
采用lib-permission的方式，只需要在打开相机的方法上加上@PermissionRequest注解，然后再写一个方法加上@PermissionDenied()注解，用来接收用户拒绝授权的响应（注意方法需要接收ArrayList<String> permissions参数），一切就ok了。
当调用到openCamera方法时，lib-permission库会根据@PermissionRequest注解自动检测是否授权，如果用户没有授予过此权限，会弹窗提示用户授权，用户点了允许后会立即执行openCamera()方法，如果用户点击拒绝或不再提示，将自动调用denied()方法。

### 原理

&nbsp;&nbsp;&nbsp;&nbsp;lib-permission的核心思想就是通过aop在编译期扫描加入@PermissionRequest注解的方法，在有此注解的方法里插入特定的代码片段，当执行此方法的时候，会启动一个全透明的代理Activity（用户无感）去申请权限，如果权限申请通过，则会继续执行此方法，否则会终止方法体的执行。

### 如何使用

* 插件引用

lib-permission使用aspectj做为aop编程的依赖，因此需在项目根目录的build.gradle里依赖**AspectJX**

```
 dependencies {
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.0'
        }
```

* 在app项目的build.gradle里应用插件

```
apply plugin: 'android-aspectjx'
//或者这样也可以
apply plugin: 'com.hujiang.android-aspectjx'
```

* 导入lib-permission库

直接下载源码通过import module引入lib-permission库（稍后我会提供gradle引入方式）


* 申请权限

```
@PermissionRequest(value = Manifest.permission.CAMERA)
public void openCamera(View view) {
  //编写打开相机逻辑
  Camera camera = Camera.open();
  Camera.Parameters parameters = camera.getParameters();
  //...

}
```

* 如果需要申请多个权限，如直接在进入Activity时候申请此页面需要用到的所有权限

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    requestPermission();
}

@PermissionRequest(value = {
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION})
public void requestPermission() {}
```

* 如果需要得到用户拒绝了哪些权限的申请

```
@PermissionDenied()
public void denied(ArrayList<String> permissions) {
    for (String permission : permissions) {
        if (Manifest.permission.CAMERA.equals(permission)) {

        }
    }
}
```

* 如果用户拒绝了权限的申请，需要让用户到设置界面打开权限

```
/**
 * PermissionUtils.goToDeviceSetting(context);
 * 目前已经适配了（华为、魅族、vivo、小米等第三方的权限管理页）
 * @param permissions
 */
 @PermissionDenied()
 public void denied(ArrayList<String> permissions) {
     for (String permission : permissions) {
         if (Manifest.permission.CAMERA.equals(permission)) {
             PermissionUtils.goToDeviceSetting(MainActivity.this);
         }
     }
 }
```

### 支持环境

AspectJX是基于 gradle android插件1.5及以上版本设计使用的，如果你还在用1.3或者更低版本，请把版本升上去。
已知bug：在gradle4.4环境下可能会遇到编译报错，原因可能是与其他插件冲突，我会尽快修复。


### 感谢

&nbsp;&nbsp;&nbsp;&nbsp;本库中用到了[HujiangTechnology](https://github.com/HujiangTechnology) 提供的开源AspectJX插件[gradle_plugin_android_aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)，需要对aspectjx做更多配置，请前往此处查看更详细的操作，在此对HujiangTechnology深表感谢！！！

### 联系方式
email：xiasem@163.com

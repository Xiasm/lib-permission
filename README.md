# lib-permission 简单易用的安卓权限请求库

## 简介

  lib-permission采用了aop的思想，利用注解简化了android权限请求的操作。
  
  #### 传统的权限请求方式
  
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
  可以看得出，采用传统的方法申请权限逻辑和打开相机的代码逻辑藕合在了一起，代码看起来很混乱，也提高了调试和测试的成本。
  
  ### lib-permission的权限请求方式
  
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
  采用lib-permission的方式，只需要在打开相机的方法上加上@PermissionRequest注解，然后再写一个方法加上@PermissionDenied()注解，用来接收用户拒绝授权的响应（注意方法需要接收ArrayList<String> permissions参数），一切就ok了。
  当调用到openCamera方法时，lib-permission库会根据@PermissionRequest注解自动检测是否授权，如果用户没有授予过此权限，会弹窗提示用户授权，用户点了允许后会立即执行openCamera()方法，如果用户点击拒绝或不再提示，将自动调用denied()方法。
  

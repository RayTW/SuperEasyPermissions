# SuperEasyPermissions


相容Android 4.0以上

build.gradle 必須要加上以下設定
```xml
dependencies {
    compile 'com.android.support:appcompat-v7:23.4.0'
}
```

AndroidManifest.xml 必須要加上以下設定
```xml
<uses-sdk
        android:targetSdkVersion="23" />

<activity
     android:theme="@android:style/Theme.Translucent.NoTitleBar" 
     android:name="ray.library.android.permissions.PermissionsRequestActivity" >
</activity>
```

要請求單一權限，讀取聯絡人"READ_CONTACTS"時範例如下：

```code
 PermissionsHelper.request(MainActivity.this,
                        Manifest.permission.READ_CONTACTS, new PermissionsHelper.PermissionsHelperListener() {

                            @Override
                            public void onPermissionsResult(String permissions, boolean isGrant,
                                                            boolean hasShowedRequestPermissionDialog) {
                                if (isGrant) {// 請求權限已被允許
                                									// TODO 可使用READ_CONTACTS權限
                                								} else {// 請求權限已被拒絕
                                									if (hasShowedRequestPermissionDialog) {// 使用者拒絕當次請求，未來app會再彈出系統請求權限dialog。
                                										// 不做任何事
                                									} else {// 使用者拒絕當次請求，未來app不再彈出系統請求權限dialog。
                                											// TODO 提示使用者到app設定開啟
                                									}
                                								}
                            }
                        });

		});
```


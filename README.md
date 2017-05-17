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
public class MainActivity extends Activity {
 private PermissionsHelper mPermissionsHelper = new PermissionsHelper();

 @Override
 protected void onCreate(Bundle savedInstanceState) {
   mPermissionsHelper.request(MainActivity.this,
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
}

@Override //required
   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      mPermissionsHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
   }
}
```


MIT License
```code
Copyright (C) <year> <copyright holders>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

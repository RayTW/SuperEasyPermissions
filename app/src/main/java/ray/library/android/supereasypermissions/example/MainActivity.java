package ray.library.android.supereasypermissions.example;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ray.library.android.supereasypermissions.PermissionsHelper;
import ray.library.android.supereasypermissions.PermissionsRequestListenerSimple;
import ray.library.android.supereasypermissions.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 重要!! true:每次問，按過不再提醒才不再次提示, false:只請求第一次，有按過拒絕後不再次提示
                // important!! true: every time, false:first time

                PermissionsHelper.getInstance().request(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionsRequestListenerSimple() {

                            @Override
                            public void onPermissionsResult(String permissions, boolean isGrant,
                                                            boolean hasShowedRequestPermissionDialog) {

                                showRequestResultToast(permissions, isGrant, hasShowedRequestPermissionDialog);
                            }
                        });
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PermissionsHelper.getInstance().request(MainActivity.this,
                        Manifest.permission.READ_CONTACTS, new PermissionsRequestListenerSimple() {

                            @Override
                            public void onPermissionsResult(String permissions, boolean isGrant,
                                                            boolean hasShowedRequestPermissionDialog) {
                                showRequestResultToast(permissions, isGrant, hasShowedRequestPermissionDialog);
                            }
                        });
            }
        });

    }

    private void showRequestResultToast(String permissions, boolean isGrant, boolean hasShowedRequestPermissionDialog) {
        if (isGrant) {// 請求權限已被允許
            Toast.makeText(getApplicationContext(), "權限[" + permissions + "]已允許", Toast.LENGTH_SHORT).show();
        } else {// 請求權限已被拒絕
            if (hasShowedRequestPermissionDialog) {// 有透過系統彈出提示請求權限
                Toast.makeText(getApplicationContext(), "權限[" + permissions + "]已被拒絕", Toast.LENGTH_SHORT).show();
            } else {// 使用者不允許彈出提示，也不開啟權限
                // TODO 提示使用者到app設定開啟
                Toast.makeText(getApplicationContext(), "請至app設定開啟權限[" + permissions + "]", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

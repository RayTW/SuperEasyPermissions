package ray.library.android.supereasypermissions.example;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ray.library.android.supereasypermissions.PermissionsHelper;
import ray.library.android.supereasypermissions.R;

public class MainActivity extends Activity {
    private final static String TAG = RayUtility.getCurrentClassSimpleName();
    private PermissionsHelper mPermissionsHelper = new PermissionsHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "checkPermission,WRITE_EXTERNAL_STORAGE[" +
                PermissionsHelper.hasGrantedPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) + "]");
        Log.d(TAG, "checkPermission,READ_CONTACTS[" +
                PermissionsHelper.hasGrantedPermission(this, Manifest.permission.READ_CONTACTS) + "]");

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 重要!! true:每次問，按過不再提醒才不再次提示, false:只請求第一次，有按過拒絕後不再次提示
                // important!! true: every time, false:first time

                mPermissionsHelper.request(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionsHelper.PermissionsHelperListener() {

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
                mPermissionsHelper.request(MainActivity.this,
                        Manifest.permission.READ_CONTACTS, new PermissionsHelper.PermissionsHelperListener() {

                            @Override
                            public void onPermissionsResult(String permissions, boolean isGrant,
                                                            boolean hasShowedRequestPermissionDialog) {
                                showRequestResultToast(permissions, isGrant, hasShowedRequestPermissionDialog);
                            }
                        });
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PermissionsHelper.startActivityAppDetails(MainActivity.this);
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

    @Override //required
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mPermissionsHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}

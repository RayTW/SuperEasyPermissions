package ray.library.android.supereasypermissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionsHelper {
    private final static int REQUEST = 100;

    private PermissionsHelperListener mPermissionsHelperListener;
    private boolean mHasShowedRequestPermissionDialog;// 請求權限前

    public PermissionsHelper() {
    }

    /**
     * 檢查是否有取得允許權限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasGrantedPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * to app settings
     *
     * @param context
     */
    @TargetApi(9)
    public static void startActivityAppDetails(Context context) {
        String apppackage = context.getApplicationContext().getPackageName();

        Intent intent = new Intent();
        int i = Build.VERSION.SDK_INT;
        if (i >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", apppackage, null));
        } else {
            String str2;
            if (i == 8) {
                str2 = "pkg";
            } else {
                str2 = "com.android.settings.ApplicationPkgName";
            }
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(str2, apppackage);
        }
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 請求權限
     *
     * @param activity
     * @param permission
     * @param listener
     */
    public void request(@NonNull Activity activity, @NonNull String permission,
                        @NonNull PermissionsHelperListener listener) {
        if (permission == null) {
            throw new IllegalArgumentException("permission == null");
        }
        mPermissionsHelperListener = listener;
        mHasShowedRequestPermissionDialog = hasShowedRequestPermissionDialog(activity, permission);
        requestLocationPermission(activity, permission);
    }

    private void requestLocationPermission(Activity activity, String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = ContextCompat.checkSelfPermission(activity, permissions);

            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{permissions}, REQUEST);
                return;
            }
        }

        onPermissionsResult(activity, permissions, true);
    }

    //TODO
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST) {
            onPermissionsResult(activity, permissions[0], grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    private void onPermissionsResult(Activity activity, String permissions, boolean isGrant) {
        if (mPermissionsHelperListener != null) {
            // 記錄是否有彈出過詢問使用者是否允許的dialog
            boolean hasShowedRequestPermissionDialog = hasShowedRequestPermissionDialog(activity, permissions)
                    | mHasShowedRequestPermissionDialog;

            mPermissionsHelperListener.onPermissionsResult(permissions, isGrant, hasShowedRequestPermissionDialog);
        }
        mPermissionsHelperListener = null;
    }

    private boolean hasShowedRequestPermissionDialog(Activity activity, String permissions) {
        boolean hasShowedRequestPermissionDialog = false;

        hasShowedRequestPermissionDialog = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions);

        return hasShowedRequestPermissionDialog;
    }

    public static interface PermissionsHelperListener {
        public void onPermissionsResult(String permissions, boolean isGrant, boolean hasShowedRequestPermissionDialog);
    }
}

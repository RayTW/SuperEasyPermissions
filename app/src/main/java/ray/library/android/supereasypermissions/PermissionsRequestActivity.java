package ray.library.android.supereasypermissions;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

@TargetApi(23)
public class PermissionsRequestActivity extends Activity {
	public final static String PERMISSIONS_NAME = "permissionsName";

	private final static int REQUEST = 100;
	private static PermissionsRequestListener sPermissionsRequestListener;

	private boolean mHasShowedRequestPermissionDialog;// 請求權限前

	public static void setPermissionsRequestListener(PermissionsRequestListener listener) {
		sPermissionsRequestListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String permissions = null;

		if (intent != null) {
			permissions = intent.getStringExtra(PERMISSIONS_NAME);

			if (permissions != null) {
				mHasShowedRequestPermissionDialog = hasShowedRequestPermissionDialog(permissions);
				requestLocationPermission(permissions);
				return;
			}
		}

		if (sPermissionsRequestListener != null) {
			sPermissionsRequestListener.onUnknowPermission(permissions);
		}
		finish();
		overridePendingTransition(0, 0);
	}

	private void requestLocationPermission(String permissions) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			int hasPermission = ContextCompat.checkSelfPermission(this, permissions);

			if (hasPermission != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[] { permissions }, REQUEST);
				return;
			}
		}

		onPermissionsResult(permissions, true);
		finish();
		overridePendingTransition(0, 0);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == REQUEST) {
			onPermissionsResult(permissions[0], grantResults[0] == PackageManager.PERMISSION_GRANTED);
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
		finish();
		overridePendingTransition(0, 0);
	}

	private void onPermissionsResult(String permissions, boolean isGrant) {
		if (sPermissionsRequestListener != null) {
			// 記錄是否有彈出過詢問使用者是否允許的dialog
			boolean hasShowedRequestPermissionDialog = hasShowedRequestPermissionDialog(permissions)
					| mHasShowedRequestPermissionDialog;

			sPermissionsRequestListener.onPermissionsResult(permissions, isGrant, hasShowedRequestPermissionDialog);
		}
	}

	private boolean hasShowedRequestPermissionDialog(String permissions) {
		boolean hasShowedRequestPermissionDialog = false;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			hasShowedRequestPermissionDialog = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions);
		}

		return hasShowedRequestPermissionDialog;
	}

}

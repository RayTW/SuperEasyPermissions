package ray.library.android.supereasypermissions;

import android.app.Activity;
import android.content.Intent;

public class PermissionsHelper {
	private static PermissionsHelper sInstance = new PermissionsHelper();
	private PermissionsRequestProxy mPermissionsRequestProxy;

	private PermissionsHelper() {
		mPermissionsRequestProxy = new PermissionsRequestProxy();
		PermissionsRequestActivity.setPermissionsRequestListener(mPermissionsRequestProxy);
	}

	public static PermissionsHelper getInstance() {
		return sInstance;
	}

	public void request(Activity activity, String permission, PermissionsRequestListener listener) {
		if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
		
		mPermissionsRequestProxy.setPermissionsRequestListener(listener);

		Intent intent = new Intent(activity, PermissionsRequestActivity.class);

		intent.putExtra(PermissionsRequestActivity.PERMISSIONS_NAME, permission);
		activity.startActivity(intent);
		activity.overridePendingTransition(0, 0);
	}

	private class PermissionsRequestProxy implements PermissionsRequestListener {
		private PermissionsRequestListener mPermissionsRequestListener;

		PermissionsRequestProxy() {
		}

		private void setPermissionsRequestListener(PermissionsRequestListener listener) {
			mPermissionsRequestListener = listener;
		}

		@Override
		public void onPermissionsResult(String permissions, boolean isGrant, boolean hasShowedRequestPermissionDialog) {

			if (mPermissionsRequestListener != null) {
				mPermissionsRequestListener.onPermissionsResult(permissions, isGrant, hasShowedRequestPermissionDialog);
			}

			mPermissionsRequestListener = null;
		}

		@Override
		public void onUnknowPermission(String permissions) {
			if (mPermissionsRequestListener != null) {
				mPermissionsRequestListener.onUnknowPermission(permissions);
			}
			mPermissionsRequestListener = null;
		}
	}
}

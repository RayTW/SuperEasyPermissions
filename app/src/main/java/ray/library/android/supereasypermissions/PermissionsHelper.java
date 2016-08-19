package ray.library.android.supereasypermissions;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

public class PermissionsHelper {
    private static PermissionsHelper sInstance = new PermissionsHelper();
    private PermissionsRequestProxy mPermissionsRequestProxy;

    private PermissionsHelper() {
        mPermissionsRequestProxy = new PermissionsRequestProxy();
        PermissionsRequestActivity.setPermissionsHelperListener(mPermissionsRequestProxy);
    }

    public static void request(@NonNull Activity activity, @NonNull String permission, @NonNull PermissionsHelperListener listener) {
        sInstance.doRequest(activity, permission, listener);
    }

    private void doRequest(Activity activity, String permission, PermissionsHelperListener listener) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        mPermissionsRequestProxy.setPermissionsHelperListener(listener);

        Intent intent = new Intent(activity, PermissionsRequestActivity.class);

        intent.putExtra(PermissionsRequestActivity.PERMISSIONS_NAME, permission);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    public static interface PermissionsHelperListener {
        public void onPermissionsResult(String permissions, boolean isGrant, boolean hasShowedRequestPermissionDialog);
    }

    private class PermissionsRequestProxy implements PermissionsHelperListener {
        private PermissionsHelperListener mPermissionsHelperListener;

        PermissionsRequestProxy() {
        }

        private void setPermissionsHelperListener(PermissionsHelperListener listener) {
            mPermissionsHelperListener = listener;
        }

        @Override
        public void onPermissionsResult(String permissions, boolean isGrant, boolean hasShowedRequestPermissionDialog) {

            if (mPermissionsHelperListener != null) {
                mPermissionsHelperListener.onPermissionsResult(permissions, isGrant, hasShowedRequestPermissionDialog);
            }

            mPermissionsHelperListener = null;
        }
    }
}

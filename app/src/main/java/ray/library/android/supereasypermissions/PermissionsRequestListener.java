package ray.library.android.supereasypermissions;

public interface PermissionsRequestListener {
	public void onPermissionsResult(String permissions, boolean isGrant, boolean hasShowedRequestPermissionDialog);

	public void onUnknowPermission(String permissions);
}

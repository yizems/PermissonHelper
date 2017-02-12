package cn.yzl.permissionhelper.library;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


/**
 * Created by YZL on 2017/2/12.
 */

public class PermissionHelper {

    private Object obj;

    private String[] permissions;

    private int requestCode;

    public PermissionHelper(Object obj) {
        if (checkTagert(obj)) {
            this.obj = obj;
        } else {
            throw new IllegalArgumentException(obj.getClass().getName() + " 不支持的类型(必须为activity或者fragment)");
        }
    }

    public static PermissionHelper init(Activity obj) {
        return new PermissionHelper(obj);
    }

    public static PermissionHelper init(Fragment obj) {
        return new PermissionHelper(obj);
    }

    public static PermissionHelper init(android.app.Fragment obj) {
        return new PermissionHelper(obj);
    }

    public PermissionHelper request(int requestCode, String... permissions) {
        this.permissions = permissions;
        this.requestCode = requestCode;
        return this;
    }

    public void excute() {
        if (!checkSysVersion()) {
            PermissionUtil.invokeAgree(obj, requestCode);
            return;
        } else {
            boolean flag = true;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                requestPermissions();
            } else {
                PermissionUtil.invokeAgree(obj, requestCode);
            }
        }
        recycle();
    }

    /**
     * 真正申请权限的方法
     */
    private void requestPermissions() {
        if (obj instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) obj,
                    permissions,
                    requestCode);
        } else if (obj instanceof Fragment) {
            ((Fragment) obj).requestPermissions(permissions, requestCode);
        } else if (obj instanceof android.app.Fragment) {
            if (checkSysVersion()) {
                ((android.app.Fragment) obj).requestPermissions(permissions, requestCode);
            }
        }
    }

    /**
     * 根据类型获取activity
     *
     * @return
     */
    private Activity getActivity() {
        if (obj instanceof Activity) {
            return (Activity) obj;
        }
        if (obj instanceof Fragment) {
            return ((Fragment) obj).getActivity();
        }
        if (obj instanceof android.app.Fragment) {
            return ((android.app.Fragment) obj).getActivity();
        }
        return null;
    }

    /**
     * 清除activity或者fragment的强应用
     */
    private void recycle() {
        obj = null;
        permissions = null;
        requestCode = -1;
    }

    /**
     * 检查版本
     *
     * @return true:6.0系统以上
     */
    private boolean checkSysVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        } else {
            return false;
        }
    }

    public static void onRequestPermissionsResult(Object obj, int requestCode, String[] permissions, int[] grantResults) {
        if (!checkTagert(obj)) {
            throw new IllegalArgumentException(obj.getClass().getName() + " 不支持的类型(必须为activity或者fragment)");
        }

        boolean flag = true;

        for (int temp : grantResults) {
            if (temp != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }

        if (flag) {
            PermissionUtil.invokeAgree(obj, requestCode);
        } else {
            PermissionUtil.invokeRefuse(obj, requestCode);
        }
    }

    /**
     * 检查 目标类是否是一个Activity或者一个fragment
     *
     * @return
     */
    private static boolean checkTagert(Object tagert) {
        if (tagert instanceof Activity ||
                tagert instanceof Fragment ||
                tagert instanceof android.app.Fragment) {
            return true;
        }
        return false;
    }
}

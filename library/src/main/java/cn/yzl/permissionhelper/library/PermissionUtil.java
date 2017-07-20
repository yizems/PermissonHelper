package cn.yzl.permissionhelper.library;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import cn.yzl.permissionhelper.BuildConfig;
import cn.yzl.permissionhelper.anotation.PermissionAgree;
import cn.yzl.permissionhelper.anotation.PermissionNoAsk;
import cn.yzl.permissionhelper.anotation.PermissionRefuse;

/**
 * Created by YZL on 2017/2/12.
 */

class PermissionUtil {
    /**
     * 执行 允许的方法
     *
     * @param object
     * @param requestCode
     */
    static void invokeAgree(Object object, int requestCode) {
        Method[] declaredMethods = object.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(PermissionAgree.class)) {
                int code = method.getAnnotation(PermissionAgree.class).value();
                if (code == requestCode) {
                    try {
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(object, new Object[]{});
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 执行 拒绝的方法
     *
     * @param object
     * @param requestCode
     */
    static void invokeRefuse(Object object, int requestCode) {
        Method[] declaredMethods = object.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(PermissionRefuse.class)) {
                int code = method.getAnnotation(PermissionRefuse.class).value();
                if (code == requestCode) {
                    try {
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(object, new Object[]{});
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 执行 不再询问的方法
     *
     * @param obj
     * @param requestCode
     * @param noAsk       不再询问的权限列表
     */
    public static void invokeNoAsk(Object obj, int requestCode, List<String> noAsk) {
        Method[] declaredMethods = obj.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(PermissionNoAsk.class)) {
                int code = method.getAnnotation(PermissionNoAsk.class).value();
                if (code == requestCode) {
                    try {
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        if (method.getParameterTypes().length > 0) {
                            method.invoke(obj, new Object[]{noAsk});
                        } else {
                            method.invoke(obj, new Object[]{});
                        }
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void startPermissionEditAct(Activity activity) {
        gotoMiuiPermission(activity);
    }


    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Activity activity) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", activity.getPackageName());
        try {
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission(activity);
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission(activity);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);

            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            activity.startActivity(getAppDetailSettingIntent(activity));
        }
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private static Intent getAppDetailSettingIntent(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        return localIntent;
    }
}

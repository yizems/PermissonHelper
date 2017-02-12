package cn.yzl.permissionhelper.library;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.yzl.permissionhelper.anotation.PermssionAgree;
import cn.yzl.permissionhelper.anotation.PermssionRefuse;

/**
 * Created by YZL on 2017/2/12.
 */

class PermissionUtil {
    /**
     * 执行 允许的方法
     * @param object
     * @param requestCode
     */
    public static void invokeAgree(Object object, int requestCode) {
        Method[] declaredMethods = object.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(PermssionAgree.class)) {
                int code = method.getAnnotation(PermssionAgree.class).value();
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
     * @param object
     * @param requestCode
     */
    public static void invokeRefuse(Object object, int requestCode) {
        Method[] declaredMethods = object.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(PermssionRefuse.class)) {
                int code = method.getAnnotation(PermssionRefuse.class).value();
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
}

package com.lsh.lib.android.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.lsh.lib.android.permission.annotation.PermissionDeny;
import com.lsh.lib.android.permission.annotation.PermisstionAgree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:liush
 * Date:2016/8/12
 */
public class PermissionUtils {
    /**
     * 判断版本是否高于23
     *
     * @return
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 获取activity实例
     *
     * @param object
     * @return
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    /**
     * 判断目前为允许的权限
     *
     * @param activity
     * @param permission
     * @return
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String[] permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                Log.e("未授权的权限", value);
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 注解 查找方法
     *
     * @param clazz
     * @param annotation  注解类
     * @param requestCode 请求code
     * @param <A>
     * @return
     */
    public static <A extends Annotation> Method findMethodWithRequestCode(Class clazz, Class<A> annotation, int requestCode) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                if (isEqualRequestCodeFromAnnotation(method, annotation, requestCode)) {
                    return method;
                }
            }
        }
        return null;
    }

    public static boolean isEqualRequestCodeFromAnnotation(Method m, Class clazz, int requestCode) {
        if (clazz.equals(PermisstionAgree.class)) {
            return requestCode == m.getAnnotation(PermisstionAgree.class).value();
        } else if (clazz.equals(PermissionDeny.class)) {
            return requestCode == m.getAnnotation(PermissionDeny.class).value();
        } else {
            return false;
        }
    }
}

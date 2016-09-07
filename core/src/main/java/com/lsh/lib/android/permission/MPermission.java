package com.lsh.lib.android.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.lsh.lib.android.permission.annotation.PermissionDeny;
import com.lsh.lib.android.permission.annotation.PermisstionAgree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理
 * Author:liush
 * Date:2016/7/25
 */
public class MPermission {
    private String[] permissions;
    private int requestCode;
    private Object object; // activity or fragment
//    private static PermissionCallBack callBack;

    private MPermission(Object object) {
        this.object = object;
    }

    public static MPermission with(Activity activity) {
        return new MPermission(activity);
    }

    public static MPermission with(Fragment fragment) {
        return new MPermission(fragment);
    }

    public MPermission permissions(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    public MPermission addRequestCode(int requestCode) {
//        if (this.requestCode != requestCode) {
//            callBack = null;
        this.requestCode = requestCode;
//        }
        return this;
    }
//
//    public MPermission addCallBack(PermissionCallBack callBack) {
//        this.callBack = callBack;
//        return this;
//    }


    /**
     * 发出权限请求
     */

    @TargetApi(Build.VERSION_CODES.M)
    public void request() {
        requestPermissions(object, requestCode, permissions);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(Object object, int requestCode, String[] permissions) {
        if (!PermissionUtils.isOverMarshmallow()) {//判断版本
            doExecuteSuccess(object, requestCode);//执行成功方法
            Log.e("--->", "低版本");
            return;
        }
        List<String> deniedPermissions = PermissionUtils.findDeniedPermissions(PermissionUtils.getActivity(object),
                permissions);
        if (deniedPermissions.size() > 0) {//获取尚未允许的请求
            Log.e("--->", "获取尚未允许的请求");
            if (object instanceof Activity) {//发出权限请求
                Log.e("--->", "Activity");
                ((Activity) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (object instanceof Fragment) {
                Log.e("--->", "Fragment");
                ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }
        } else {
            Log.e("--->", "已全部获取请求");
            doExecuteSuccess(object, requestCode);//执行成功方法
        }


    }

    /**
     * ********************* 权限请求结果 *********************
     */

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions, int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    private static void requestResult(Object obj, int requestCode, String[] permissions, int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size() > 0) {
            doExecuteFail(obj, requestCode);
            Log.e("--->", "不允许");
        } else {
            doExecuteSuccess(obj, requestCode);
            Log.e("--->", "允许");
        }
    }

    /**
     * ********************* 分发请求结果 *********************
     */

    private static void doExecuteSuccess(Object activity, int requestCode) {
        executeMethod(activity, PermissionUtils.findMethodWithRequestCode(activity.getClass(), PermisstionAgree.class, requestCode));
    }

    private static void doExecuteFail(Object activity, int requestCode) {
        executeMethod(activity, PermissionUtils.findMethodWithRequestCode(activity.getClass(), PermissionDeny.class, requestCode));
    }

//
//    public interface PermissionCallBack {
//        void onGranted(int requestCode);
//
//        void onDenied(int requestCode);
//    }

    /**
     * ********************* reflect execute method *********************
     */

    private static void executeMethod(Object activity, Method executeMethod) {
        executeMethodWithParam(activity, executeMethod, new Object[]{});
    }

    private static void executeMethodWithParam(Object activity, Method executeMethod, Object... args) {
        if (executeMethod != null) {
            try {
                if (!executeMethod.isAccessible()) {
                    executeMethod.setAccessible(true);
                }
                executeMethod.invoke(activity, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

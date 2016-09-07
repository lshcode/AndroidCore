package com.lsh.lib.android.utils.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lsh.lib.android.widget.divider.RecycleViewDivider;

/**
 * Author:liush
 * Date:2016/8/4
 */
public class ViewUtil {
    public static <T extends View> T findView(Activity mActivity, int resId) {
        return (T) (mActivity.findViewById(resId));
    }

    public static <T extends View> T findView(Context context, int resId) {
        return (T) (((Activity) context).findViewById(resId));
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * RecyclerView设置
     * 水平线  竖直方向
     *
     * @param recycleView
     */
    public static void setRecyclerView(Context context, RecyclerView recycleView) {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //垂直方向
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        recycleView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }

    /**
     * RecyclerView设置
     * 水平线  竖直方向
     *
     * @param recycleView
     */
    public static void setRecyclerViewNoline(Context context, RecyclerView recycleView) {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //垂直方向
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
    }

    /**
     * 设置swipeRefreshLayout  颜色
     *
     * @param swipeRefreshLayout
     */
    public static void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    /**
     * 设置swipeRefreshLayout开始出现等待框
     *
     * @param swipeRefreshLayout
     */
    public static void startRefresh(Context mContext, SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, mContext.getResources()
                        .getDisplayMetrics()));
    }


    /**
     * 获取输入框字符
     *
     * @param editText
     * @return
     */
    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 动态设置view能否点击
     *
     * @param button
     * @param enable
     */
    public static void enable(Button button, boolean enable) {
        button.setEnabled(enable);
//        if (enable) {
//            button.setBackgroundResource(R.drawable.shape_corner_blue);
//        } else
//            button.setBackgroundResource(R.drawable.shape_corner_gray);
    }

    /**
     * view转换成btimap
     *
     * @param addViewContent
     * @return
     */
    public static Bitmap getViewBitmap(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }

    /**
     * // 方法1 Android获得屏幕的宽和高
     * WindowManager windowManager = getWindowManager();
     * Display display = windowManager.getDefaultDisplay();
     * int screenWidth = screenWidth = display.getWidth();
     * int screenHeight = screenHeight = display.getHeight();
     * <p/>
     * // 方法2
     * DisplayMetrics dm = new DisplayMetrics();
     * getWindowManager().getDefaultDisplay().getMetrics(dm);
     * TextView tv = (TextView)this.findViewById(R.id.tv);
     * float width=dm.widthPixels*dm.density;
     * float height=dm.heightPixels*dm.density;
     */
    public static int screenWidth(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }
}

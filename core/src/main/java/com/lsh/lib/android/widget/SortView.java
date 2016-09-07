package com.lsh.lib.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 索引
 * author:liush
 * version: 2016/5/12  9:37
 */
public class SortView extends View {
    /**
     * 侧边栏显示字母
     */
    private String[] words = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    Paint paint = new Paint();
    private boolean isDown = false;
    private int choose = -1;
    private TextView textViewDialog = null;
    private SortListener sortListener;

    public SortView(Context context) {
        super(context);
    }

    public SortView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextViewDialog(TextView textViewDialog) {
        this.textViewDialog = textViewDialog;
    }

    public void setSortListener(SortListener sortListener) {
        this.sortListener = sortListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int singleHeight = height / words.length;

        for (int i = 0; i < words.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20f);
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(words[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(words[i], xPos, yPos, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        float y = event.getY();
        int oldChoose = choose;
        int c = (int) (y / getHeight() * words.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                isDown = false;
                setBackgroundResource(android.R.color.transparent);
                choose = -1;//
                invalidate();
                if (textViewDialog != null) {
                    textViewDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                isDown = true;
                setBackgroundResource(android.R.color.darker_gray);
                if (oldChoose != c) {
                    if (c >= 0 && c < words.length) {
                        if (textViewDialog != null) {
                            textViewDialog.setText(words[c]);
                            textViewDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        if (sortListener != null) {
                            sortListener.onSortListSelect(words[c]);
                        }
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public interface SortListener {
        void onSortListSelect(String latter);
    }

}

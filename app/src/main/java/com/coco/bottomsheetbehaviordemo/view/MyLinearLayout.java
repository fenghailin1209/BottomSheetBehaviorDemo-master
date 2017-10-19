package com.coco.bottomsheetbehaviordemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/10/19.
 */

public class MyLinearLayout extends LinearLayout{

    private boolean isFatherClick = false;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setIsChildClick(boolean isChildClick){
        this.isFatherClick = isChildClick;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!isFatherClick){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}

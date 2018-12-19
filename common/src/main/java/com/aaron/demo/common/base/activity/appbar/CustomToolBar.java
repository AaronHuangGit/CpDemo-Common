package com.aaron.demo.common.base.activity.appbar;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 *
 */
public class CustomToolBar extends Toolbar {
    boolean fitsSystemWindows;

    public CustomToolBar(Context context) {
        super(context);
        initView();
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        fitsSystemWindows = getFitsSystemWindows();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!fitsSystemWindows) {
            int paddingBottom = getPaddingBottom();
            int paddingTop = getPaddingTop();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            setPadding(paddingLeft, paddingTop + getStatusBarHeight(), paddingRight, paddingBottom);
        }
    }

    private int getStatusBarHeight() {
        Resources resources = getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}

package com.aaron.demo.common.base.activity.immersive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.aaron.demo.common.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author hua.yin
 * @version 1.0.0
 * @since: 16/7/6 下午4:24
 */
public class StatusBarManager {
    private SystemBarTintManager mTintManager;
    private Activity mActivity;
    private boolean mIsChangeStatusFontColor;

    public StatusBarManager(Activity activity) {
        mActivity = activity;
        init(activity);
    }

    private void init(Activity activity) {
        mTintManager = new SystemBarTintManager(activity);
        setStatusBarColorResource(R.color.colorTransparent);
    }

    /**
     * @param statusBarTintEnabled statusBarTintEnabled
     */
    public void setStatusBarTintEnabled(boolean statusBarTintEnabled) {
        mTintManager.setStatusBarTintEnabled(statusBarTintEnabled);
    }

    /**
     * 设置状态栏字体为黑色
     * @param activity activity
     * @param isDark  isDark
     */
    public void setStatusFontColorForDark(Activity activity, boolean isDark) {
        statusBarLightMode(activity.getWindow(), isDark);
    }

    /**
     * @param colorRes colorRes
     */
    public void setStatusBarColorResource(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!mIsChangeStatusFontColor) {
                colorRes = R.color.statusBar;
            }
            mActivity.getWindow().setStatusBarColor(ContextCompat.getColor(mActivity, colorRes));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(colorRes);
        }
        statusBarTranslucent(mActivity.getWindow());
    }


    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param window window
     * @param isDark 是否是黑色
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public boolean statusBarLightMode(Window window, boolean isDark) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (miuiSetStatusBarLightMode(window, isDark)) {
                result = true;
            } else if (flymeSetStatusBarLightMode(window, isDark)) {
                result = true;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = true;
            }
        }
        return result;
    }

    /**
     * 修改状态栏为全透明
     *
     * @param window window
     */
    @TargetApi(19)
    public void statusBarTranslucent(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mIsChangeStatusFontColor) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param window
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private boolean miuiSetStatusBarLightMode(Window window, boolean dark) {
        dark = false;
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag); //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag); //清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
}

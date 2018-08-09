package com.example.xlc.monkey.screen;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * @className: DensityUtils
 * @Author: xlc
 * @Date : 2018/8/9  12:03
 * @Description : 今日头条的适配方案
 */
public class DensityUtils {

    private static float appDensity;
    private static float appScaleDensity;
    private static DisplayMetrics sAppDisplayMetrics;
    private static int sBarHeight;
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";

    public static void setDensity(@NonNull final Application application) {

        sAppDisplayMetrics = application.getResources().getDisplayMetrics();
        sBarHeight = getStatusBarHeight(application);
        //        可以直接在application中调用
        //        registerActivityLifecycleCallbacks(application);
        if (appDensity == 0) {
            //初始化时赋值
            appDensity = sAppDisplayMetrics.density;
            appScaleDensity = sAppDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration configuration) {
                    //字体改变后，将appScaleDensity重新赋值

                    if (configuration != null && configuration.fontScale > 0) {
                        appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
    }

    /**
     * 此方法在BaseActivity中做初始化
     * 在 setContentView()之前设置
     *
     * @param activity
     */
    public static void setDefault(Activity activity) {
        setAppOrientation(activity, WIDTH);
    }

    /**
     * 此方法用于某一个Activity里面更改适配的方向
     * 在setContentView()之前设置
     *
     * @param activity
     * @param orientation
     */
    public static void setOrientation(Activity activity, String orientation) {
        setAppOrientation(activity, orientation);
    }


    private static void setAppOrientation(@NonNull Activity activity, String orientation) {
        float targetDensity;

        if (orientation.equals("height")) {
            targetDensity = (sAppDisplayMetrics.heightPixels - sBarHeight) / 667f; //设计图的高度，根据设计图修改，单位dp
        } else {
            targetDensity = sAppDisplayMetrics.widthPixels / 360f;//设计图纸的宽度，根据设计图修改 单位dp
        }

        float targetScaledDensity = targetDensity * (appScaleDensity / appDensity);
        int targetDensityDpi = ((int) (160 * targetDensity));

        /**
         * 将修改后的值赋值给系统参数
         * 只修改Activity的density的值
         */
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelOffset(resourceId);
        }
        return result;

    }

    //监听Activity的生命周期
    private static void registerActivityLifecycleCallbacks(Application application) {

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                setDefault(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}

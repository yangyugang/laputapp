/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import java.lang.reflect.Method;

public final class DeviceScreenUtils {

  private static DisplayMetrics metrics;

  public static DisplayMetrics getDisplayMetrics(Context context) {
    if( metrics == null ) {
      metrics = context.getResources().getDisplayMetrics();
    }

    return metrics;
  }

  public static float getDensity(Context context) {
    if( metrics == null ) {
      getDisplayMetrics(context);
    }
    return metrics.density;
  }

  public static float getScaledDensity(Context context) {
    if( metrics == null ) {
      getDisplayMetrics(context);
    }
    return metrics.scaledDensity;
  }

  public static int getScreenWidth(Context context) {
    if( metrics == null ) {
      getDisplayMetrics(context);
    }
    return metrics.widthPixels;
  }

  public static int getScreenHeight(Context context) {
    if( metrics == null ) {
      getDisplayMetrics(context);
    }
    return metrics.heightPixels;
  }

  public static int px2dp(float pxValue, Activity activity) {
    if( metrics == null ) {
      getDisplayMetrics(activity);
    }
    return (int) (pxValue / metrics.density + 0.5f);
  }

  public static int dp2px(float dipValue, Activity activity) {
    if( metrics == null ) {
      getDisplayMetrics(activity);
    }
    return (int) (dipValue * metrics.density + 0.5f);
  }

  public static int dp2px(float dpValue, Context context) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static int px2sp(float pxValue, Activity activity) {
    if( metrics == null ) {
      getDisplayMetrics(activity);
    }
    return (int) (pxValue / metrics.scaledDensity + 0.5f);
  }

  public static int sp2px(float spValue, Activity activity) {
    if( metrics == null ) {
      getDisplayMetrics(activity);
    }
    return (int) (spValue * metrics.scaledDensity + 0.5f);
  }

  public static float getTextLength(float textSize, String text) {
    Paint paint = new Paint();
    paint.setTextSize(textSize);
    return paint.measureText(text);
  }

  /**
   * 获取实际屏幕高度
   * 如 1920 * 1080
   * @param activity
   * @return
   */
  public static int[] getRealMetrics(Activity activity) {
    int[] dpi = new int[2];
    Display display = activity.getWindowManager().getDefaultDisplay();
    DisplayMetrics dm = new DisplayMetrics();
    @SuppressWarnings("rawtypes") Class c;
    try {
      c = Class.forName("android.view.Display");
      @SuppressWarnings("unchecked") Method method =
          c.getMethod("getRealMetrics", DisplayMetrics.class);
      method.invoke(display, dm);
      dpi[0] = dm.widthPixels;
      dpi[1] = dm.heightPixels;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dpi;
  }

  /**
   *
   * @param activity
   * @return
   */
  public static int getStatusHeight(Activity activity) {
    Rect rect = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    return rect.top;
  }
}

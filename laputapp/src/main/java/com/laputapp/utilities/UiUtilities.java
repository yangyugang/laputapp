/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

public final class UiUtilities {

  public static boolean isTablet(Context context) {
    return (context.getResources().getConfiguration().screenLayout
        & Configuration.SCREENLAYOUT_SIZE_MASK)
        >= Configuration.SCREENLAYOUT_SIZE_LARGE;
  }

  private static final int[] RES_IDS_ACTION_BAR_SIZE = { android.R.attr.actionBarSize };

  /** Calculates the Action Bar height in pixels. */
  public static int calculateActionBarSize(Context context) {
    if (context == null) {
      return 0;
    }

    Resources.Theme curTheme = context.getTheme();
    if (curTheme == null) {
      return 0;
    }

    TypedArray att = curTheme.obtainStyledAttributes(RES_IDS_ACTION_BAR_SIZE);
    if (att == null) {
      return 0;
    }

    float size = att.getDimension(0, 0);
    att.recycle();
    return (int) size;
  }

  public static boolean hasICS() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  public static boolean hasHoneycomb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  public static boolean hasHoneycombMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }

  public static boolean hasJellyBeanMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
  }

  public static boolean hasLollipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  /** Generics version of {@link android.app.Activity#findViewById} */
  @SuppressWarnings("unchecked")
  public static <T extends View> T getViewOrNull(Activity parent, int viewId) {
    return (T) parent.findViewById(viewId);
  }

  /** Generics version of {@link android.view.View#findViewById} */
  @SuppressWarnings("unchecked")
  public static <T extends View> T getViewOrNull(View parent, int viewId) {
    return (T) parent.findViewById(viewId);
  }

  /**
   * Same as {@link android.app.Activity#findViewById}, but crashes if there's no view.
   */
  @SuppressWarnings("unchecked")
  public static <T extends View> T getView(Activity parent, int viewId) {
    return (T) checkView(parent.findViewById(viewId));
  }

  /**
   * Same as {@link android.view.View#findViewById}, but crashes if there's no view.
   */
  @SuppressWarnings("unchecked")
  public static <T extends View> T getView(View parent, int viewId) {
    return (T) checkView(parent.findViewById(viewId));
  }

  private static View checkView(View v) {
    if (v == null) {
      throw new IllegalArgumentException("View doesn't exist");
    }
    return v;
  }

  /**
   * Same as {@link android.view.View#setVisibility(int)}, but doesn't crash even if {@code view} is null.
   */
  public static void setVisibilitySafe(View v, int visibility) {
    if (v != null) {
      v.setVisibility(visibility);
    }
  }

  /**
   * Same as {@link android.view.View#setVisibility(int)}, but doesn't crash even if {@code view} is null.
   */
  public static void setVisibilitySafe(Activity parent, int viewId, int visibility) {
    setVisibilitySafe(parent.findViewById(viewId), visibility);
  }

  /**
   * Same as {@link android.view.View#setVisibility(int)}, but doesn't crash even if {@code view} is null.
   */
  public static void setVisibilitySafe(View parent, int viewId, int visibility) {
    setVisibilitySafe(parent.findViewById(viewId), visibility);
  }

  @SuppressWarnings("deprecation")
  @SuppressLint("NewApi")
  public static void setBackgroundCompat(View view, Drawable drawable) {
    if (Build.VERSION.SDK_INT >= 16) {
      view.setBackground(drawable);
    } else {
      view.setBackgroundDrawable(drawable);
    }
  }

  /**
   * Resolves the given attribute to the resource id for the given theme.
   */
  public static int resolveAttributeToResourceId(Resources.Theme theme, int attributeResId) {
    TypedValue outValue = new TypedValue();
    theme.resolveAttribute(attributeResId, outValue, true);
    return outValue.resourceId;
  }

}

/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Static methods related to device orientation.
 */
public final class OrientationUtil {

  /**
   * @return if the context is in landscape orientation.
   */
  public static boolean isLandscape(Context context) {
    return context.getResources().getConfiguration().orientation
        == Configuration.ORIENTATION_LANDSCAPE;
  }
}

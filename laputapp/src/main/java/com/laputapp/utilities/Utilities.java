/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public final class Utilities {

  /**
   * 获取AndroidManifest中配置的meta-data
   * @param context
   * @param key
   * @return
   */
  public static String getMetaData(Context context, String key) {
    Bundle metaData = null;
    String value = null;
    if (context == null || key == null) {
      return null;
    }
    try {
      ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
          context.getPackageName(), PackageManager.GET_META_DATA);
      if (null != ai) {
        metaData = ai.metaData;
      }
      if (null != metaData) {
        value = metaData.getString(key);
      }
    } catch (PackageManager.NameNotFoundException e) {

    }
    return value;
  }

}

/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import com.laputapp.utilities.AppInfo;

public class Laputapp extends Application {

  private static Context sAppContext;
  private static Laputapp mInstance;
  private AppInfo mAppInfo;

  @Override public void onCreate() {
    super.onCreate();

    mInstance = this;
    sAppContext = getApplicationContext();
  }

  public static Context getAppContext() {
    return sAppContext;
  }

  public static Resources getAppResources() {
    return getAppContext().getResources();
  }

  /**
   *
   * @return current application instance
   */
  public static Laputapp getInstance() {
    return mInstance;
  }

  public static AppInfo getAppInfo() {
    if (getInstance().mAppInfo == null) {
      getInstance().mAppInfo = new AppInfo(getAppContext());
    }
    return getInstance().mAppInfo;
  }

}

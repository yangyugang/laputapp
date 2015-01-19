/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public final class AppInfo {

  public String os;
  public String deviceName;
  public String deviceId;
  public String version;
  public String versionCode;
  public String channel;
  public int screenWidth;
  public int screenHeight;

  private void initOs() {
    this.os = android.os.Build.MODEL + "," + android.os.Build.VERSION.SDK + "," + android.os.Build.VERSION.RELEASE;
  }

  private void initMetrics(Context context) {
    this.screenWidth = DeviceScreenUtils.getScreenWidth(context);
    this.screenHeight = DeviceScreenUtils.getScreenHeight(context);
  }

  private void initDeviceId(Context context) {
    this.deviceId = IdDevice.getDeviceID(context);
  }

  private void initVersion(Context context) {
    PackageManager packageManager = context.getPackageManager();
    PackageInfo packInfo = null;
    try {
      packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    String version = "";
    String code = "";
    if (packInfo != null) {
      version = packInfo.versionName;
      code = Integer.valueOf(packInfo.versionCode).toString();
    }
    this.version = version;
    this.versionCode = code;
  }

  private void initChannel(Context context) {
    this.channel = Utilities.getMetaData(context, "UMENG_CHANNEL");
    if (this.channel == null) {
      this.channel = "loopeer";
    }
  }

  private void initDeviceName() {
    this.deviceName = android.os.Build.DEVICE;
  }

  public AppInfo(Context context) {
    initDeviceId(context);
    initVersion(context);
    initChannel(context);
    initOs();
    initDeviceName();
    initMetrics(context);
  }

}

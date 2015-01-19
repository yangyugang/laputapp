/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.laputapp.analytics.AnalyticsActivity;

public class BaseActivity extends AnalyticsActivity {


  /**
   * Converts an intent into a {@link Bundle} suitable for use as fragment arguments.
   */
  public static Bundle intentToFragmentArguments(Intent intent) {
    Bundle arguments = new Bundle();
    if (intent == null) {
      return arguments;
    }

    final Uri data = intent.getData();
    if (data != null) {
      arguments.putParcelable("_uri", data);
    }

    final Bundle extras = intent.getExtras();
    if (extras != null) {
      arguments.putAll(intent.getExtras());
    }

    return arguments;
  }

  /**
   * Converts a fragment arguments bundle into an intent.
   */
  public static Intent fragmentArgumentsToIntent(Bundle arguments) {
    Intent intent = new Intent();
    if (arguments == null) {
      return intent;
    }

    final Uri data = arguments.getParcelable("_uri");
    if (data != null) {
      intent.setData(data);
    }

    intent.putExtras(arguments);
    intent.removeExtra("_uri");
    return intent;
  }
}

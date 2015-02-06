/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import com.laputapp.R;
import com.laputapp.analytics.AnalyticsActivity;
import com.laputapp.utilities.InputMethodUtils;
import com.laputapp.widget.ProgressLoading;

public class BaseActivity extends AnalyticsActivity {

  private ProgressLoading mProgressLoading;
  private ProgressLoading mUnBackProgressLoading;
  private boolean progressShow;
  public void showProgressLoading(int resId) {
    showProgressLoading(getString(resId));
  }

  public void showProgressLoading(String message) {
    if (mProgressLoading == null) {
      mProgressLoading = new ProgressLoading(this, R.style.ProgressLoadingTheme);
      mProgressLoading.setCanceledOnTouchOutside(true);
      mProgressLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
          progressShow = false;
        }
      });
    }
    if (!TextUtils.isEmpty(message)) {
      mProgressLoading.setMessage(message);
    } else {
      mProgressLoading.setMessage(null);
    }
    progressShow = true;
    mProgressLoading.show();
  }

  public boolean isProgressShow() {
    return progressShow;
  }

  public void dismissProgressLoading() {
    if (mProgressLoading != null) {
      progressShow = false;
      mProgressLoading.dismiss();
    }
  }

  public void showUnBackProgressLoading(int resId) {
    showUnBackProgressLoading(getString(resId));
  }

  // 按返回键不可撤销的
  public void showUnBackProgressLoading(String message) {
    if (mUnBackProgressLoading == null) {
      mUnBackProgressLoading = new ProgressLoading(this, R.style.ProgressLoadingTheme) {
        @Override
        public void onBackPressed() {
        }
      };
    }
    if (!TextUtils.isEmpty(message)) {
      mUnBackProgressLoading.setMessage(message);
    } else {
      mUnBackProgressLoading.setMessage(null);
    }
    mUnBackProgressLoading.show();
  }

  public void dismissUnBackProgressLoading() {
    if (mUnBackProgressLoading != null) {
      mUnBackProgressLoading.dismiss();
    }
  }

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

  public void hideSoftInputMethod() {
    try {
      InputMethodUtils.hideSoftInputMethod(this, getCurrentFocus().getWindowToken());
    } catch (Exception e) {
    }
  }

  @Override
  public void finish() {
    hideSoftInputMethod();
    super.finish();
  }

  public void showSoftInputMethod() {
    try {
      InputMethodUtils.showSoftInputMethod(this);
    } catch (Exception e) {
    }
  }

  public boolean isImmActive() {
    try {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      return imm.isActive();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}

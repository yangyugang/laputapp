/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.app.Activity;
import com.laputapp.analytics.AnalyticsFragment;

public class BaseFragment extends AnalyticsFragment {

  protected BaseActivity mActivity;

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof BaseActivity) {
      mActivity = (BaseActivity) activity;
    }
  }

  public void showProgressLoading(int resId) {
    showProgressLoading(getString(resId));
  }

  public void showProgressLoading(String message) {
    if (mActivity != null) {
      mActivity.showProgressLoading(message);
    }
  }

  public boolean isProgressShow() {
    if (mActivity != null) {
      mActivity.isProgressShow();
    }
    return false;
  }

  public void dismissProgressLoading() {
    if (mActivity != null) {
      mActivity.dismissProgressLoading();
    }
  }

  public void showUnBackProgressLoading(int resId) {
    showUnBackProgressLoading(getString(resId));
  }

  // 按返回键不可撤销的
  public void showUnBackProgressLoading(String message) {
    if (mActivity != null) {
      mActivity.showUnBackProgressLoading(message);
    }
  }

  public void dismissUnBackProgressLoading() {
    if (mActivity != null) {
      mActivity.dismissUnBackProgressLoading();
    }
  }

}

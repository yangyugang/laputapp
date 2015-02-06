/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.laputapp.analytics.AnalyticsFragment;

public abstract class BaseFragment extends AnalyticsFragment {

  protected BaseActivity mActivity;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(getFragmentLayout(), container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injectViews(view);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    ButterKnife.reset(this);
  }

  /**
   * Replace every field annotated with ButterKnife annotations like @InjectView with the proper
   * value.
   *
   * @param view to extract each widget injected in the fragment.
   */
  private void injectViews(final View view) {
    ButterKnife.inject(this, view);
  }

  /**
   * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
   * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
   * inflate in this method when extends BaseFragment.
   */
  protected int getFragmentLayout() {
    return 0;
  }

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

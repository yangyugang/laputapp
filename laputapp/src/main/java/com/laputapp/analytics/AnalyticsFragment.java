/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.analytics;

import android.support.v4.app.Fragment;

public abstract class AnalyticsFragment extends Fragment implements AnalyticsInterface {
  @Override public void sendHitEvent(String categoryId, String actionId, String labelId) {
  }

  @Override public void sendScreenView() {
  }
}

/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.analytics;

public interface AnalyticsInterface {
  void sendHitEvent(String categoryId, String actionId, String labelId);

  void sendScreenView();
}

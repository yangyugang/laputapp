/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Background {@link android.app.Service} that is used to keep our process alive long enough
 * for background threads to finish. Started and stopped directly by specific
 * background tasks when needed.
 */
public class EmptyService extends Service {
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}

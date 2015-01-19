/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import android.util.SparseArray;
import android.view.View;

/**
 * http://www.piwai.info/android-adapter-good-practices/
 */
public final class ViewHolder {

  // I added a generic return type to reduce the casting noise in client code
  @SuppressWarnings("unchecked") public static <T extends View> T get(View view, int id) {
    SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
    if (viewHolder == null) {
      viewHolder = new SparseArray<View>();
      view.setTag(viewHolder);
    }
    View childView = viewHolder.get(id);
    if (childView == null) {
      childView = view.findViewById(id);
      viewHolder.put(id, childView);
    }
    return (T) childView;
  }

}

/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Provides static methods for creating mutable {@code Maps} instances easily.
 */
public final class Maps {
  /**
   * Creates a {@code HashMap} instance.
   *
   * @return a newly-created, initially-empty {@code HashMap}
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<K, V>();
  }

  /**
   * Creates a {@code LinkedHashMap} instance.
   *
   * @return a newly-created, initially-empty {@code LinkedHashMap}
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
    return new LinkedHashMap<K, V>();
  }

}
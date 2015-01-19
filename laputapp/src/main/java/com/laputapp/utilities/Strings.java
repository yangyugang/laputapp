/**
 * Created by YuGang Yang on January 18, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.utilities;

/**
 * 字符串工具类
 */
public final class Strings {

  private Strings() {
    // No instances.
  }

  public static boolean isBlank(CharSequence string){
    return (string == null || string.toString().trim().length() == 0);
  }

  public static String valueOrDefault(String string, String defaultString) {
    return isBlank(string) ? defaultString : string;
  }

  public static String truncateAt(String string, int length) {
    return string.length() > length ? string.substring(0, length) : string;
  }

}

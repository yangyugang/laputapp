/**
 * Created by YuGang Yang on February 06, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import java.io.Serializable;
import java.lang.reflect.Field;

public class ExtendedObject implements Serializable {

  @Override public String toString() {
    Class<?> clazz = getClass();
    Field fields[] = clazz.getDeclaredFields();

    StringBuilder sb = new StringBuilder(clazz.getName());
    sb.append("{");

    int len = fields.length;
    for (int index=0; index < len; index++) {
      try {
        Field field = fields[index];
        sb.append(field.getName()).append("=").append(field.get(this).toString());
        if (index < len - 1) {
          sb.append(", ");
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    sb.append("}");
    return sb.toString();
  }

}

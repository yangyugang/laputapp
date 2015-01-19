/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EmptyCallback<T> implements Callback<T> {

  @Override public void success(T t, Response response) {
  }

  @Override public void failure(RetrofitError error) {
  }
}

/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import retrofit.Callback;
import retrofit.RetrofitError;

public class EmptyCallback<T> implements Callback<Response<T>> {

  @Override public void success(Response<T> tResponse,
      retrofit.client.Response response) {
  }

  @Override public void failure(RetrofitError error) {
  }
}

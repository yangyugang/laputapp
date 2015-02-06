/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import java.util.ArrayList;

public abstract class PageCallback<T> extends CallbackDecorator<ArrayList<T>> {

  public PageCallback(Callbacks.RequestCallback<ArrayList<T>> requestCallback) {
    super(requestCallback);
  }

  @Override public void success(Response<ArrayList<T>> result, retrofit.client.Response response) {
    if (result.isSuccessed()) {
      handleData(result);
    } else {
      onErrorCode(result);
    }
    super.success(result, response);
  }

  protected abstract void onErrorCode(Response<ArrayList<T>> response);

  protected abstract void handleData(Response<ArrayList<T>> response);

}

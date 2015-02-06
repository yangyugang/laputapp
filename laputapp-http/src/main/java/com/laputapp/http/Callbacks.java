/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import retrofit.RetrofitError;

public class Callbacks {

  public static interface RequestCallback<T> {
    public void onRequestComplete(Response<T> response);
    public void onRequestFailure(Response<T> response);
    public void onRequestFailure(RetrofitError error);
    public void onRequestNetworkError();
    public void onFinish();

    // cache
    public boolean canCache();
    public void handleLocalCache(T data);
    public void onCacheLoaded(T localData);
  }

  public static class ApiBaseCallback<T> implements RequestCallback<T> {
    @Override public void onRequestComplete(Response<T> response) {}
    @Override public void onRequestFailure(Response<T> response) {}
    @Override public void onRequestFailure(RetrofitError error) {}

    @Override public void onRequestNetworkError() {}
    @Override public void onFinish() {}

    @Override public boolean canCache() {
      return false;
    }
    @Override public void handleLocalCache(T data) {}
    @Override public void onCacheLoaded(T localData) {}
  }
}

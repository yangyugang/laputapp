/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import retrofit.RetrofitError;

public class CallbackDecorator<T> implements retrofit.Callback<Response<T>> {

  protected final Callbacks.RequestCallback<T> mRequestCallback;

  public CallbackDecorator(Callbacks.RequestCallback<T> requestCallback) {
    mRequestCallback = requestCallback;
  }

  @Override public void success(Response<T> result, retrofit.client.Response response) {
    if (mRequestCallback == null) return;

    // response value
    result.mResponse = response;
    result.mStatus = response.getStatus();
    result.mReason = response.getReason();
    result.mHeaders = response.getHeaders();

    if (result.isSuccessed()) {
      if (mRequestCallback.canCache() && result.mData != null) {
        mRequestCallback.handleLocalCache(result.mData);
      }
      mRequestCallback.onRequestComplete(result);
    } else {
      mRequestCallback.onRequestFailure(result);
    }

    mRequestCallback.onFinish();
  }

  @Override public void failure(RetrofitError error) {
    if (mRequestCallback == null) return;

    if (error.getKind() == RetrofitError.Kind.NETWORK) {
      mRequestCallback.onRequestNetworkError();
    } else {
      mRequestCallback.onRequestFailure(error);
    }

    mRequestCallback.onFinish();
  }
}

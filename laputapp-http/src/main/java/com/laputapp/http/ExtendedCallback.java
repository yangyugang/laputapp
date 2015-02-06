/**
 * Created by YuGang Yang on February 06, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import retrofit.Callback;
import retrofit.RetrofitError;

public class ExtendedCallback<T> extends Callbacks.ApiBaseCallback<T> implements Callback<Response<T>> {

  @Override public void success(Response<T> tResponse, retrofit.client.Response response) {
    // response value
    tResponse.mResponse = response;
    tResponse.mStatus = response.getStatus();
    tResponse.mReason = response.getReason();
    tResponse.mHeaders = response.getHeaders();

    if (tResponse.isSuccessed()) {
      onRequestComplete(tResponse);
    } else {
      onRequestFailure(tResponse);
    }

    onFinish();
  }

  @Override public void failure(RetrofitError error) {
    if (error.getKind() == RetrofitError.Kind.NETWORK) {
      onRequestNetworkError();
    } else {
      onRequestFailure(error);
    }
    onFinish();
  }
}

/**
 * Created by YuGang Yang on February 06, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import com.google.gson.annotations.SerializedName;

public class AccountParams extends RequestParams {

  @SerializedName("account_id") public int mAccountId;
  @SerializedName("token") public String mToken;

  public AccountParams() {}

  public AccountParams(int accountId) {
    this.mAccountId = accountId;
  }

  public AccountParams(int accountId, String token) {
    mAccountId = accountId;
    mToken = token;
  }

}

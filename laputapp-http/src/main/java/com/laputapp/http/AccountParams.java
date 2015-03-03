/**
 * Created by YuGang Yang on February 06, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import com.google.gson.annotations.SerializedName;

public class AccountParams extends RequestParams {

  @SerializedName("account_id") public String mAccountId;
  @SerializedName("token") public String mToken;

  public AccountParams() {}

  public AccountParams(String accountId) {
    mAccountId = accountId;
  }

  public AccountParams(String accountId, String token) {
    mAccountId = accountId;
    mToken = token;
  }

}

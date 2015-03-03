/**
 * Created by YuGang Yang on February 07, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import com.google.gson.annotations.SerializedName;

public class PageParams extends AccountParams {

  private static final String DEFAULT_PAGE = "1";
  private static final String DEFAULT_PAGE_SIZE = "30";

  @SerializedName("page") public String mPage;
  @SerializedName("page_size") public String mPageSize;

  @SerializedName("account_id") public String mAccountId;
  @SerializedName("token") public String mToken;

  public PageParams() {
    this(null);
  }

  public PageParams(String accountId) {
    this(accountId, null, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
  }

  public PageParams(String accountId, String token) {
    this(accountId, token, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
  }

  public PageParams(String accountId, String token,
      String page, String pageSize) {
    super(accountId, token);
    mPage = page;
    mPageSize = pageSize;
  }
}

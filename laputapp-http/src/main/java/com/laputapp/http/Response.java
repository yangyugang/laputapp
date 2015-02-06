/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import retrofit.client.Header;

/**
 * http请求返回数据
 * @param <T>
 */
public class Response<T> extends ExtendedObject implements Serializable {

  int mStatus;
  String mReason;
  List<Header> mHeaders;
  retrofit.client.Response mResponse;

  /**
   * 返回的错误码、0代表成功
   */
  @SerializedName("code") public int mCode;

  /**
   * 提示信息
   */
  @SerializedName("message") public String mMsg;

  /**
   * 当前页面
   */
  @SerializedName("page") public long mPage;

  /**
   * 总页码
   */
  @SerializedName("page_size") public long mPageSize;

  /**
   * 总数据条数
   */
  @SerializedName("total_size") public long mTotalSize;

  /**
   * 返回的数据 ArrayList<T> T
   */
  @SerializedName("data") public T mData;

  /**
   * 请求是否成功、0代表成功
   * @return
   */
  public boolean isSuccessed() {
    return mCode == 0;
  }

  /** Status line code. */
  public int getStatus() {
    return mStatus;
  }

  /** Status line reason phrase. */
  public String getReason() {
    return mReason;
  }

  /** An unmodifiable collection of headers. */
  public List<Header> getHeaders() {
    return mHeaders;
  }

  public retrofit.client.Response getResponse() {
    return mResponse;
  }
}

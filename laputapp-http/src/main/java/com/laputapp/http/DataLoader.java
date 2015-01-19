/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.http;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import retrofit.RetrofitError;

public class DataLoader<T> extends PageCallback<T> {

  public static final long PAGE_SIZE_INT = 30;
  public static final long FIRST_PAGE_INT = 1;
  public static final boolean IS_REVERSE = false;

  /**
   * All resources retrieved
   */
  protected final LinkedHashMap<Object, T> mResources = new LinkedHashMap<Object, T>();
  private final Loader<T> mLoader;
  private final KeyExtractor<T> mKeyExtractor;
  private ArrayLoadStyle mLoadStyle;
  private final long mFirstPage;
  private long mPage;
  private long mPageSize;
  private long mTotalSize = 0;

  private final boolean mIsReverse;

  private boolean mIsLoading = false;
  private boolean mHashMore;

  public DataLoader(Loader<T> loader, Callbacks.RequestCallback<ArrayList<T>> requestCallback, KeyExtractor<T> keyExtractor) {
    this(loader, requestCallback, keyExtractor, FIRST_PAGE_INT, PAGE_SIZE_INT);
  }

  public DataLoader(Loader<T> loader, Callbacks.RequestCallback<ArrayList<T>> requestCallback, KeyExtractor<T> keyExtractor, long page, long pageSize) {
    this(loader, requestCallback, keyExtractor, IS_REVERSE, page, pageSize);
  }

  public DataLoader(Loader<T> loader, Callbacks.RequestCallback<ArrayList<T>> requestCallback, KeyExtractor<T> keyExtractor, boolean isReverse, long page, long pageSize) {
    super(requestCallback);
    mLoader = loader;
    mKeyExtractor = keyExtractor;
    mIsReverse = isReverse;
    mPage = page;
    mFirstPage = page;
    mPageSize = pageSize;
    mHashMore = false;
    mTotalSize = 0;
    mLoadStyle = ArrayLoadStyle.REFRESH;
    mIsLoading = false;
  }

  @Override protected void handleData(Response<ArrayList<T>> response) {
    if (response != null && response.isSuccessed()) {
      ArrayList<T> resourcePage = response.mData;
      mPage = response.mPage;
      mPageSize = response.mPageSize;
      mTotalSize = response.mTotalSize;

      if (isRefresh()) {
        mResources.clear();
      }

      mHashMore = mTotalSize > mPageSize * mPage;
      if (mHashMore) {
        mPage++;
      }

      for (T resource : resourcePage) {
        resource = mLoader.register(resource);
        if (resource == null)
          continue;
        mResources.put(mKeyExtractor.getKeyForData(resource), resource);
      }

    } else {
      mHashMore = false;
    }
    mIsLoading = false;
  }

  @Override protected void onErrorCode(Response<ArrayList<T>> result) {
    mHashMore = false;
    mIsLoading = false;
  }

  @Override
  public void failure(RetrofitError error) {
    mHashMore = false;
    mIsLoading = false;
    super.failure(error);
  }

  public static interface Loader<T> {
    void requestData(String page, String pageSize);

    void beforeRefresh();

    void beforeLoadMore();

    /**
     * Callback to register a fetched resource before it is stored in this pager
     *
     * @param resource model
     * @return resource
     */
    T register(final T resource);
  }

  public interface KeyExtractor<T> {
    public Object getKeyForData(T data);
  }

  public static enum AppendPosition {
    HEAD,
    TAIL
  }

  public static enum ArrayLoadStyle {
    REFRESH,
    LOAD_MORE,
  }

  protected AppendPosition getAppendPosition() {
    if (isRefresh()) {
      return (!mIsReverse) ? AppendPosition.HEAD : AppendPosition.TAIL;
    } else { // load more
      return (!mIsReverse) ? AppendPosition.TAIL : AppendPosition.HEAD;
    }
  }

  public boolean isRefresh() {
    return (mLoadStyle == ArrayLoadStyle.REFRESH);
  }

  public boolean isLoadMore() {
    return (mLoadStyle == ArrayLoadStyle.LOAD_MORE);
  }

  public boolean isLoading() {
    return mIsLoading;
  }

  public void refresh() {
    if (mIsLoading) return;

    mIsLoading = true;
    mLoadStyle = ArrayLoadStyle.REFRESH;
    mLoader.beforeRefresh();
    mLoader.requestData(String.valueOf(mFirstPage), String.valueOf(mPageSize));
  }

  public void loadMore() {
    if (!mHashMore || mIsLoading) return;

    mIsLoading = true;
    mLoadStyle = ArrayLoadStyle.LOAD_MORE;
    mLoader.beforeLoadMore();
    mLoader.requestData(String.valueOf(mPage), String.valueOf(mPageSize));
  }

  public ArrayList<T> getResources() {
    return new ArrayList<T>(mResources.values());
  }

  public long size() {
    return mResources.size();
  }

  public boolean canLoadMore() {
    return !mIsLoading && hasMore();
  }

  public boolean hasMore() {
    return mHashMore;
  }
}

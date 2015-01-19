/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.laputapp.http.Callbacks;
import com.laputapp.http.DataLoader;
import com.laputapp.http.Response;
import com.laputapp.utilities.Lists;
import com.laputapp.widget.CollectionView;
import com.laputapp.widget.CollectionViewCallbacks;
import java.util.ArrayList;
import retrofit.RetrofitError;

public abstract class BaseCollectionFragment<T> extends CollectionFragment
    implements Callbacks.RequestCallback<ArrayList<T>>, CollectionViewCallbacks,
    DataLoader.Loader<T>,
    DataLoader.KeyExtractor<T> {

  protected static final int GROUP_ID_NORMAL = 10001;
  protected ArrayList<T> mItems = Lists.newArrayList();
  private DataLoader<T> mDataLoader;

  private boolean mDataIsFullReload;
  protected boolean hasWarned = false;
  private boolean mDataHasLoaded = false;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mDataLoader = new DataLoader<>(this, this, this);
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (!mItems.isEmpty()) {
      updateCollectionView();
    }

    if (!mDataHasLoaded) {
      mDataLoader.refresh();
    }
  }

  protected void updateCollectionView() {
    if (getView() == null) return;

    int itemCount = mItems.size();

    CollectionView.Inventory inv;
    if (itemCount == 0) {
      inv = prepareNoDataInventory();
    } else {
      inv = newInventory();
    }

    Parcelable state = null;
    if (!mDataIsFullReload) {
      // it's not a full reload, so we want to keep scroll position, etc
      state = getListView().onSaveInstanceState();
    }
    getListView().setCollectionAdapter(this);
    getListView().updateInventory(this, inv, mDataIsFullReload);
    if (state != null) {
      getListView().onRestoreInstanceState(state);
    }
    mDataIsFullReload = false;
    hasWarned = false;
  }

  protected CollectionView.Inventory prepareNoDataInventory() {
    return new CollectionView.Inventory();
  }

  protected boolean showCollectionViewHeader() {
    return false;
  }

  protected CollectionView.Inventory prepareInventory() {
    return new CollectionView.Inventory();
  }

  public DataLoader<T> getDataLoader() {
    return mDataLoader;
  }

  /**
   *
   * @return
   */
  protected int getCollectionViewDisplayCols() {
    return 1;
  }

  /**
   * normal inventory
   */
  protected CollectionView.Inventory newInventory() {
    CollectionView.InventoryGroup curGroup = null;
    CollectionView.Inventory inventory = prepareInventory();

    int normalColumns = getCollectionViewDisplayCols();
    final int size = mItems.size();
    for (int dataIndex = 0; dataIndex < size; dataIndex++) {
      if (curGroup == null) {
        curGroup = new CollectionView.InventoryGroup(GROUP_ID_NORMAL).setDataIndexStart(dataIndex)
            .setShowHeader(showCollectionViewHeader())
            .setDisplayCols(normalColumns)
            .setItemCount(1);
      } else {
        curGroup.incrementItemCount();
      }
    }

    if (curGroup != null) {
      inventory.addGroup(curGroup);
    }

    return inventory;
  }

  @Override public View newCollectionHeaderView(Context context, ViewGroup parent) {
    return null;
  }

  @Override public void bindCollectionHeaderView(Context context, View view, int groupId,
      String headerLabel) {

  }

  @Override public void beforeRefresh() {
  }

  @Override public void beforeLoadMore() {
  }

  @Override public T register(T resource) {
    return resource;
  }

  @Override public void onRequestComplete(Response<ArrayList<T>> response) {
    mItems = mDataLoader.getResources();
    if (mItems.isEmpty()) {
      mDataHasLoaded = false;
    } else {
      mDataHasLoaded = true;
    }
  }

  @Override public void onRequestFailure(Response<ArrayList<T>> response) {
    mDataHasLoaded = false;
  }

  @Override public void onRequestFailure(RetrofitError error) {
    mDataHasLoaded = false;
  }

  @Override public void onRequestNetworkError() {
    mDataHasLoaded = false;
  }

  @Override public void onFinish() {
    updateCollectionView();
  }

  @Override public boolean canCache() {
    return false;
  }

  @Override public void handleLocalCache(ArrayList<T> data) {

  }

  @Override public void onCacheLoaded(ArrayList<T> localData) {
    if (localData == null || localData.isEmpty()) return;
    for (T data : localData) {
      register(data);
    }
    mItems.addAll(localData);
    if (!mItems.isEmpty()) {
      updateCollectionView();
    }
  }
}


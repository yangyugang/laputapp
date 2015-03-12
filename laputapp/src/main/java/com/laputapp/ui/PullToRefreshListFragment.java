/**
 * Created by YuGang Yang on March 12, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.laputapp.widget.CollectionView;
import com.laputapp.widget.PullToRefreshCollectionView;

public abstract class PullToRefreshListFragment<T> extends BasePagedCollectionFragment<T>
  implements PullToRefreshBase.OnRefreshListener {

  private PullToRefreshCollectionView mPullToRefreshListView;

  @Override
  public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View layout = super.onCreateView(inflater, container, savedInstanceState);

    ListView lv = (ListView) layout.findViewById(android.R.id.list);
    ViewGroup parent = (ViewGroup) lv.getParent();

    // Remove ListView and add PullToRefreshListView in its place
    int lvIndex = parent.indexOfChild(lv);
    parent.removeViewAt(lvIndex);
    mPullToRefreshListView = onCreatePullToRefreshListView(inflater, savedInstanceState);
    parent.addView(mPullToRefreshListView, lvIndex, lv.getLayoutParams());

    return layout;
  }

  public final PullToRefreshCollectionView getPullToRefreshListView() {
    return mPullToRefreshListView;
  }

  @Override public CollectionView getListView() {
    return getPullToRefreshListView().getRefreshableView();
  }

  protected PullToRefreshCollectionView onCreatePullToRefreshListView(LayoutInflater inflater, Bundle savedInstanceState) {
    return new PullToRefreshCollectionView(getActivity());
  }

  @Override public void onRefresh(PullToRefreshBase pullToRefreshBase) {
    getDataLoader().refresh();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getPullToRefreshListView().setOnRefreshListener(this);
  }

  @Override public void onFinish() {
    super.onFinish();
    if (getDataLoader().isRefresh()) {
      getPullToRefreshListView().onRefreshComplete();
    }
  }
}

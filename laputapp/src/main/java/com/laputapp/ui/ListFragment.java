/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.laputapp.R;

public class ListFragment extends BaseFragment {
  final private Handler mHandler = new Handler();

  final private Runnable mRequestFocus = new Runnable() {
    public void run() {
      mList.focusableViewAvailable(mList);
    }
  };

  final private AdapterView.OnItemClickListener mOnClickListener
      = new AdapterView.OnItemClickListener() {
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
      onListItemClick((ListView)parent, v, position, id);
    }
  };

  ListAdapter mAdapter;
  ListView mList;
  View mEmptyView;
  View mProgressContainer;
  View mListContainer;
  CharSequence mEmptyText;
  boolean mListShown;

  public ListFragment() {
  }

  /**
   * Provide default implementation to return a simple list view.  Subclasses
   * can override to replace with their own layout.  If doing so, the
   * returned view hierarchy <em>must</em> have a ListView whose id
   * is {@link android.R.id#list android.R.id.list} and can optionally
   * have a sibling view id {@link android.R.id#empty android.R.id.empty}
   * that is to be shown when the list is empty.
   *
   * <p>If you are overriding this method with your own custom content,
   * consider including the standard layout {@link android.R.layout#list_content}
   * in your layout file, so that you continue to retain all of the standard
   * behavior of ListFragment.  In particular, this is currently the only
   * way to have the built-in indeterminant progress state be shown.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.list_content, container, false);
  }

  /**
   * Attach to list view once the view hierarchy has been created.
   */
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ensureList();
  }

  /**
   * Detach from list view.
   */
  @Override
  public void onDestroyView() {
    mHandler.removeCallbacks(mRequestFocus);
    mList = null;
    mListShown = false;
    mEmptyView = mProgressContainer = mListContainer = null;
    super.onDestroyView();
  }

  /**
   * This method will be called when an item in the list is selected.
   * Subclasses should override. Subclasses can call
   * getListView().getItemAtPosition(position) if they need to access the
   * data associated with the selected item.
   *
   * @param l The ListView where the click happened
   * @param v The view that was clicked within the ListView
   * @param position The position of the view in the list
   * @param id The row id of the item that was clicked
   */
  public void onListItemClick(ListView l, View v, int position, long id) {
  }

  /**
   * Provide the cursor for the list view.
   */
  public void setListAdapter(ListAdapter adapter) {
    boolean hadAdapter = mAdapter != null;
    mAdapter = adapter;
    if (mList != null) {
      mList.setAdapter(adapter);
      if (!mListShown && !hadAdapter) {
        // The list was hidden, and previously didn't have an
        // adapter.  It is now time to show it.
        setListShown(true, getView().getWindowToken() != null);
      }
    }
  }

  /**
   * Set the currently selected list item to the specified
   * position with the adapter's data
   *
   * @param position
   */
  public void setSelection(int position) {
    ensureList();
    mList.setSelection(position);
  }

  /**
   * Get the position of the currently selected list item.
   */
  public int getSelectedItemPosition() {
    ensureList();
    return mList.getSelectedItemPosition();
  }

  /**
   * Get the cursor row ID of the currently selected list item.
   */
  public long getSelectedItemId() {
    ensureList();
    return mList.getSelectedItemId();
  }

  /**
   * Get the activity's list view widget.
   */
  public ListView getListView() {
    ensureList();
    return mList;
  }

  /**
   * The default content for a ListFragment has a TextView that can
   * be shown when the list is empty.  If you would like to have it
   * shown, call this method to supply the text it should use.
   */
  public void setEmptyText(CharSequence text) {
    ensureList();
    if (!TextUtils.isEmpty(text)) {
      ((TextView)mEmptyView).setText(text.toString());
    }
    mEmptyText = text;
  }

  /**
   * Control whether the list is being displayed.  You can make it not
   * displayed if you are waiting for the initial data to show in it.  During
   * this time an indeterminant progress indicator will be shown instead.
   *
   * <p>Applications do not normally need to use this themselves.  The default
   * behavior of ListFragment is to start with the list not being shown, only
   * showing it once an adapter is given with {@link #setListAdapter(android.widget.ListAdapter)}.
   * If the list at that point had not been shown, when it does get shown
   * it will be do without the user ever seeing the hidden state.
   *
   * @param shown If true, the list view is shown; if false, the progress
   * indicator.  The initial value is true.
   */
  public void setListShown(boolean shown) {
    setListShown(shown, true);
  }

  /**
   * Like {@link #setListShown(boolean)}, but no animation is used when
   * transitioning from the previous state.
   */
  public void setListShownNoAnimation(boolean shown) {
    setListShown(shown, false);
  }

  /**
   * Control whether the list is being displayed.  You can make it not
   * displayed if you are waiting for the initial data to show in it.  During
   * this time an indeterminant progress indicator will be shown instead.
   *
   * @param shown If true, the list view is shown; if false, the progress
   * indicator.  The initial value is true.
   * @param animate If true, an animation will be used to transition to the
   * new state.
   */
  private void setListShown(boolean shown, boolean animate) {
    ensureList();
    if (mProgressContainer == null) {
      throw new IllegalStateException("Can't be used with a custom content view");
    }
    if (mListShown == shown) {
      return;
    }
    mListShown = shown;
    if (shown) {
      if (animate) {
        mProgressContainer.startAnimation(
            AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
        mListContainer.startAnimation(AnimationUtils.loadAnimation(
            getActivity(), android.R.anim.fade_in));
      } else {
        mProgressContainer.clearAnimation();
        mListContainer.clearAnimation();
      }
      mProgressContainer.setVisibility(View.GONE);
      mListContainer.setVisibility(View.VISIBLE);
    } else {
      if (animate) {
        mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
            getActivity(), android.R.anim.fade_in));
        mListContainer.startAnimation(AnimationUtils.loadAnimation(
            getActivity(), android.R.anim.fade_out));
      } else {
        mProgressContainer.clearAnimation();
        mListContainer.clearAnimation();
      }
      mProgressContainer.setVisibility(View.VISIBLE);
      mListContainer.setVisibility(View.GONE);
    }
  }

  /**
   * Get the ListAdapter associated with this activity's ListView.
   */
  public ListAdapter getListAdapter() {
    return mAdapter;
  }

  private void ensureList() {
    if (mList != null) {
      return;
    }
    View root = getView();
    if (root == null) {
      throw new IllegalStateException("Content view not yet created");
    }
    if (root instanceof ListView) {
      mList = (ListView)root;
    } else {
      mEmptyView = root.findViewById(android.R.id.empty);
      mProgressContainer = root.findViewById(R.id.progressContainer);
      mListContainer = root.findViewById(R.id.listContainer);
      View rawListView = root.findViewById(android.R.id.list);
      if (!(rawListView instanceof ListView)) {
        if (rawListView == null) {
          throw new RuntimeException(
              "Your content must have a ListView whose id attribute is " +
                  "'android.R.id.list'");
        }
        throw new RuntimeException(
            "Content has view with id attribute 'android.R.id.list' "
                + "that is not a ListView class");
      }
      mList = (ListView)rawListView;
      if (mEmptyView != null) {
        mList.setEmptyView(mEmptyView);
      }
    }
    mListShown = true;
    mList.setOnItemClickListener(mOnClickListener);
    if (mAdapter != null) {
      ListAdapter adapter = mAdapter;
      mAdapter = null;
      setListAdapter(adapter);
    } else {
      // We are starting without an adapter, so assume we won't
      // have our data right away and start with the progress indicator.
      if (mProgressContainer != null) {
        setListShown(false, false);
      }
    }
    mHandler.post(mRequestFocus);
  }
}


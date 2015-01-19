/**
 * Created by YuGang Yang on January 19, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.laputapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.laputapp.R;
import com.laputapp.widget.CollectionView;

public class CollectionFragment extends ListFragment {

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.collection_content, container, false);
  }

  @Override
  public CollectionView getListView() {
    return (CollectionView) super.getListView();
  }

}

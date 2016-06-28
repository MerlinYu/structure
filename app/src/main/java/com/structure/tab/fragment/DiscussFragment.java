package com.structure.tab.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.structure.R;
import com.structure.base.fragment.BaseFragment;
import com.structure.base.fragment.BaseFragmentPresenter;

/**
 * Created by yuchao on 6/15/16.
 */
public class DiscussFragment extends BaseFragment<BaseFragmentPresenter> {
  @Override
  protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_layout,container, false);

  }

  @Override
  protected BaseFragmentPresenter createPresenter() {
    return null;
  }
}

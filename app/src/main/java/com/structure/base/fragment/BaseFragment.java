package com.structure.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by yuchao.
 */
public abstract class BaseFragment<P extends BaseFragmentPresenter>
        extends Fragment
        implements BaseFragmentDisplay{

    private P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createContentView(inflater, container, savedInstanceState);
        if (null != view) {
            ButterKnife.inject(this,view);
        }
        onViewCreated();
        return view;
    }

    //    protected  void onViewCreated();
    protected void onViewCreated() {

    }

    protected abstract View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    protected abstract P createPresenter();


    @Override
    public void onDetach() {
        mPresenter = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
}

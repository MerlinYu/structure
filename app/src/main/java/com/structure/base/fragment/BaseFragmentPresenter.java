package com.structure.base.fragment;

import com.structure.base.BaseModule;
import com.structure.base.BasePresenter;


/**
 * Created by yuchao.
 * fragment MVP中的P
 */
public class BaseFragmentPresenter<V extends BaseFragment, M extends BaseModule> implements BasePresenter{

    protected M mModule;
    protected V mDisplay;

    public BaseFragmentPresenter(V v, M m) {
        this.mModule    = m;
        this.mDisplay   = v;
    }

    @Override
    public V getDisplay() {
        return mDisplay;
    }

    @Override
    public M getModule() {
        return mModule;
    }

    public boolean isDestroyed() {
        if (null == mDisplay) {
            return true;
        }
        return false;
    }

}

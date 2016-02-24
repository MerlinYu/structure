package com.structure.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yuchao.
 *
 */
public abstract class BaseActivity<P extends ActivityPresenter> extends AppCompatActivity implements ActivityDisplay{

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        onPreCreate();
        super.onCreate(savedInstanceState);
        int resID = getContentViewId();
        setContentView(resID);
        mPresenter = createPresenter();
        onViewCreated();
    }

    public abstract  P createPresenter();

    public abstract int getContentViewId();

    public abstract void onPreCreate();

    public abstract void onViewCreated();


    @Override
    protected void onDestroy() {
        if (null != mPresenter) {
            mPresenter = null;
        }
        super.onDestroy();
    }
}

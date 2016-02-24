package com.structure.main;

import android.os.Bundle;
import com.structure.R;
import com.structure.base.BaseActivity;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThread.start();
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPreCreate() {

    }

    @Override
    public void onViewCreated() {

    }

    public void refreshUI() {

    }

    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            mPresenter.getInfo();
        }
    });




}

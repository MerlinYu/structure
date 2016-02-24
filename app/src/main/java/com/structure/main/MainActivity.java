package com.structure.main;

import android.os.Bundle;
import android.widget.TextView;

import com.structure.R;
import com.structure.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.InjectView;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay  {

    @InjectView(R.id.text)
    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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






}

package com.structure.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.person.PersonActivity;

import org.w3c.dom.Text;

import butterknife.InjectView;
import butterknife.OnClick;


/***/
public class MainActivity extends BaseActivity<MainPresenter> implements MainDisplay  {

    @InjectView(R.id.text)
    public TextView mTextView;
    @InjectView(R.id.btn)
    public Button mBtn;


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

    @OnClick(R.id.btn)
    void btnClick(View v) {
        this.startActivity(PersonActivity.buildIntent(this, "yucaho"));
    }






}

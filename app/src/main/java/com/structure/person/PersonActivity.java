package com.structure.person;

import com.structure.R;
import com.structure.base.BaseActivity;

/**
 * Created by yuchao.
 */
public class PersonActivity extends BaseActivity<PersonPresenter> {
    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.person_activity;
    }




}

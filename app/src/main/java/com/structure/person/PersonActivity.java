package com.structure.person;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.person.fragment.PersonFragment;
import com.structure.person.fragment.PersonFragmentPresenter;

import butterknife.InjectView;

/**
 * Created by yuchao.
 */
public class PersonActivity extends BaseActivity<PersonPresenter> {

    @InjectView(R.id.person_toolbar)
    Toolbar mToolBar;
    @InjectView(R.id.person_bar_coll)
    CollapsingToolbarLayout mCollBar;

    private String userID;


    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter(this);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setSupportActionBar(mToolBar);
        mCollBar.setTitle("My Zone");
        PersonFragment fragment = PersonFragment.newInstance(getUserId(), false);
    //    getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    @Override
    public int getContentViewId() {
        return R.layout.person_activity;
    }

    public static Intent buildIntent(Context context, String userID) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(PersonFragment.USER_ID,userID);
        return intent;
    }

    private String getUserId() {
        userID = getIntent().getStringExtra(PersonFragment.USER_ID);
        return userID;
    }


}

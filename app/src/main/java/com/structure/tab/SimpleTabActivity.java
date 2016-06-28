package com.structure.tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.tab.fragment.DiscussFragment;
import com.structure.tab.fragment.HomeFragment;
import com.structure.tab.fragment.OtherFragment;

import butterknife.InjectView;

/**
 * Created by yuchao on 6/15/16.
 */
public class SimpleTabActivity extends BaseActivity<SimplePresenter> {

  @InjectView(R.id.tab_layout)
  TabLayout mTabLayout;
  @InjectView(R.id.viewpager)
  ViewPager mViewPager;

  FragmentTabAdapter fragmentTabAdapter;

  public static Intent buildIntent(Context context) {
    Intent intent = new Intent(context, SimpleTabActivity.class);
    return intent;
  }


  @Override
  public SimplePresenter createPresenter() {
    return new SimplePresenter(this);
  }

  @Override
  public int getContentViewId() {
    return R.layout.tabbar_layout;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initTabFragment();
  }

  private void initTabFragment() {
    fragmentTabAdapter = new FragmentTabAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(fragmentTabAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition(),true);
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }


  private static class FragmentTabAdapter extends FragmentStatePagerAdapter {

    private static final String[] tabTitle = {"首页", "讨论区","其他"};

    public FragmentTabAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return new HomeFragment();
        case 1:
          return new DiscussFragment();
        case 2:
          return new OtherFragment();
      }
      return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return tabTitle[position];
    }

    @Override
    public int getCount() {
      return tabTitle.length;
    }
  }

}

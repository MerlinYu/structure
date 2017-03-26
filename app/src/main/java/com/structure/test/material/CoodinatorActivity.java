package com.structure.test.material;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import com.structure.R;
import com.structure.base.ActivityPresenter;
import com.structure.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by yuchao on 10/18/16.
 */

public class CoodinatorActivity extends BaseActivity {

  @InjectView(R.id.main_frame)
  FrameLayout mFrame;
  @InjectView(R.id.tabs)
  TabLayout mTabs;


  @InjectView(R.id.recycler_view)
  RecyclerView mRecyclerView;

  TabsRecyclerViewAdapter mAdapter;
  GridLayoutManager mLayoutManager;
  TabChangeListener mTabChangeListener;
  ArrayList<Item> items;
  ArrayList<TabItem> tabItems;



  @Override
  public ActivityPresenter createPresenter() {
    return null;
  }

  @Override
  public void onViewCreated() {
    super.onViewCreated();
    initTabAndRecyclerView();
  }

  private void initTabAndRecyclerView() {
    mAdapter = new TabsRecyclerViewAdapter();
    mLayoutManager = new GridLayoutManager(this,2);
    mRecyclerView.setLayoutManager(mLayoutManager);
    // mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        Log.v("===tag===","position:" + firstVisibleItem + " , "+lastVisibleItem);

        if (mTabChangeListener != null) {
          mTabChangeListener.setTab(firstVisibleItem);
        }
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
      }
    });


    mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        mRecyclerView.scrollToPosition(5);
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
        mRecyclerView.scrollToPosition(15);
      }
    });

    TabLayout.Tab tab1 = mTabs.newTab();
    tab1.setText("hello");

    tabItems = (ArrayList<TabItem>) getItemTab();

    for (int i = 0; i < tabItems.size(); i++) {
      ArrayList<Item> array  = (ArrayList<Item>) getItemList();
      TabItem tabItem = tabItems.get(i);
      tabItem.count = array.size();
      if (i == 0) {
        tabItem.startPosition = 0;
      } else {
        tabItem.startPosition = tabItems.get(i-1).count + tabItems.get(i-1).startPosition + 1;
      }
      items.addAll(array);
    }
    mAdapter.addTabItemArray(tabItems);
    mAdapter.addItemArray(items);

    mTabs.addTab(tab1);
//    tab1.setTag();
    mTabs.addTab(mTabs.newTab().setText("hello"));
    mTabs.addTab(mTabs.newTab().setText("hello"));

    mTabChangeListener = new TabChangeListener() {
      @Override
      public void setTab(int position) {
        mTabs.getTabCount();


      }
    };

  }




  @Override
  public int getContentViewId() {
    return R.layout.coordinator_layout;
  }


  interface TabChangeListener {
    void setTab(int position);
  }


  public List<Item> getItemList() {
    return new ArrayList<>(10);
  }

  public List<TabItem> getItemTab() {
    return new ArrayList<>(3);
  }





}

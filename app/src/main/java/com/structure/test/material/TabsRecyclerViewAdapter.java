package com.structure.test.material;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.structure.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuchao on 10/19/16.
 */

public class TabsRecyclerViewAdapter extends RecyclerView.Adapter {

  private final int VIEW_TYPE_TAB = 0x10;
  private final int VIEW_TYPE_ITEM =0x20;

  ArrayList<TabItem> mTabItems;
  ArrayList<Item> mItems;

  TypeMapper mTypeMapper;

  public void addTabItem(TabItem tabItem) {
    if (mTabItems == null) {
      mTabItems = new ArrayList<>();
    }
    mTabItems.add(tabItem);
  }

  public void addTabItemArray(List<TabItem> array) {
    if (mTabItems == null) {
      mTabItems = new ArrayList<>();
    }
    mTabItems.addAll(array);
  }

  // 添加recycler view 显示的商品信息
  public void addShowItem(Item item) {
    if (mItems == null) {
      mItems = new ArrayList<>();
    }
    mItems.add(item);
  }

  public void addItemArray(List<Item> array) {
    if (array == null) {
      return;
    }
    if (mItems == null) {
      mItems = new ArrayList<>();
    }
    mItems.addAll(array);
  }


  public void buildTypeMapper() {
    mTypeMapper = new TypeMapper(mTabItems.size() + mItems.size());
    for (TabItem tabItem : mTabItems) {
      mTypeMapper.putType(tabItem.startPosition,VIEW_TYPE_TAB);
      for (int i = tabItem.startPosition +1; i <= tabItem.endPosition; i++) {
        mTypeMapper.putType(i,VIEW_TYPE_ITEM);
      }
    }
    notifyDataSetChanged();
  }




  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    switch (viewType) {

      default:break;
    }

    return new ImageViewHolder(parent);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    String str = " position : " + position;
    if (holder instanceof ImageViewHolder) {
      ((ImageViewHolder)holder).setViewText(str);
      holder.itemView.setTag(str);
    }


  }

  @Override
  public int getItemCount() {
    return mTypeMapper == null ? 0 : mTypeMapper.size();
  }

  @Override
  public int getItemViewType(int position) {
    return mTypeMapper == null ? 0 : mTypeMapper.getType(position);
  }



  class ImageViewHolder extends RecyclerView.ViewHolder {
    TextView mText;
    public ImageViewHolder(ViewGroup parent) {
      super(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_viewholder,parent, false));
      mText = (TextView) itemView.findViewById(R.id.text);
    }

    public void setViewText(String str) {
      mText.setText(str);
    }
  }


  static class TypeMapper {
    public SparseArray<Integer> sparse;

    public void putType(int index, int type) {
      sparse.put(index, type);
    }

    public TypeMapper(int size) {
      sparse = new SparseArray<>(size);
    }

    public int indexOfType(int type) {
      final int size = sparse.size();
      for (int i = 0; i < size; i++) {
        if (sparse.valueAt(i) == type) {
          return sparse.keyAt(i);
        }
      }
      return -1;
    }

    public int getType(int index) {
      if (index > sparse.size()) {
        return -1;
      }
      return sparse.get(index);
    }

    public int size() {
      return sparse.size();
    }
  }


}

package com.structure.person.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.structure.person.fragment.viewholder.HeaderPlaceViewHolder;
import com.structure.person.fragment.viewholder.ItemShowViewHolder;
import com.structure.utils.TypeMap;

/**
 * Created by yuchao.
 */
public class PersonAdapter extends RecyclerView.Adapter {

  private final static int ITEMSHOW_HEAD = 0x100;

  private final static int ITEMSHOW_CONTENT = 0x200;

  private TypeMap typeMap;
  private boolean isHeaderShow = false;

  public PersonAdapter() {
    super();
    buildTypeMap();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ITEMSHOW_HEAD:
        return new HeaderPlaceViewHolder(parent);
      case ITEMSHOW_CONTENT:
        return new ItemShowViewHolder(parent);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof HeaderPlaceViewHolder) {

    } else if (holder instanceof ItemShowViewHolder) {
      ((ItemShowViewHolder) holder).bind();
    }
  }

  @Override
  public int getItemCount() {
    if (null == typeMap) {
      return 0;
    }
    return typeMap.getCount();
  }


  @Override
  public int getItemViewType(int position) {
    if (null == typeMap) {
      return 0;
    }
    return typeMap.getType(position);
  }

  public void updateItemShow() {
    buildTypeMap();
    notifyDataSetChanged();

  }

  public void setHeaderPlace(boolean isHeaderShow) {
    this.isHeaderShow = isHeaderShow;
    buildTypeMap();
    notifyDataSetChanged();
  }

  public void setUpdateItemShow() {

  }

  private void buildTypeMap() {
    typeMap = new TypeMap();
    int index = 0;
    if (isHeaderShow == true) {
      typeMap.putType(index++, ITEMSHOW_HEAD);
    }
    for (int i = 0; i < 10; i++) {
      typeMap.putType(index++, ITEMSHOW_CONTENT);
    }
  }
}

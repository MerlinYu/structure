package com.structure.person.fragment.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.structure.R;

/**
 * Created by yuchao.
 */
public class HeaderPlaceViewHolder extends RecyclerView.ViewHolder {
    int height = 0;
    RecyclerView.LayoutParams layoutParams;

    public HeaderPlaceViewHolder(View itemView) {
        super(itemView);
        height = (int)itemView.getContext().getResources().getDimension(R.dimen.person_header_height);
        layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height);
        itemView.setLayoutParams(layoutParams);
    }

    public HeaderPlaceViewHolder(View itemview, int height){
        super(itemview);
        this.height = height;
        layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height);
        itemView.setLayoutParams(layoutParams);
    }



}

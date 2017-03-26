package com.structure.tab.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.structure.R;
import com.structure.base.fragment.BaseFragment;
import com.structure.base.fragment.BaseFragmentPresenter;
import com.structure.tab.fragment.data.HomeTab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;


/**
 * Created by yuchao on 6/15/16.
 */
public class OtherFragment extends BaseFragment<BaseFragmentPresenter> {

  @InjectView(R.id.json_text)
  TextView jsonView;

  @Override
  protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.other_fragment_layout,container, false);
  }

  @Override
  protected void onViewCreated() {
    super.onViewCreated();
    String jsonData = this.getContext().getResources().getString(R.string.json_array);
    Log.v("===tag=== ", jsonData);

    JSONObject jsonObject;
    try {
      jsonObject = new JSONObject(jsonData);
      JSONArray jsonArray = jsonObject.optJSONArray("tabs");
      Log.v("===tag=== ",jsonArray.toString());
      Gson gson = new GsonBuilder().create();
      HomeTab[] tabList = gson.fromJson(jsonArray.toString(), HomeTab[].class);
      StringBuffer buffer = new StringBuffer(512);
      buffer.append("str convert to object\n");
      for (int i = 0; i < tabList.length; i++) {
        HomeTab tab = tabList[i];
        buffer.append( "index :" + i + "\n");
        buffer.append(tab.toString());
        buffer.append("\n");
      }
      String str = gson.toJson(tabList);
      buffer.append("\n object convert to json:\n");
      buffer.append(str);
      jsonView.setText(buffer.toString());

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected BaseFragmentPresenter createPresenter() {
    return null;
  }
}

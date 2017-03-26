package com.structure.tab.fragment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao on 6/16/16.
 */
public class HomeTab {

  @SerializedName("title")
  public String title;
  @SerializedName("path")
  public String path;
  @SerializedName("method")
  public String method;

  @Override
  public String toString() {
    return "title = " + title + "\npath = " + path + "\nmethod = " + method;
  }
}

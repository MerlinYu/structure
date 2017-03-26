package com.structure.base;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by yuchao on 16/5/7.
 *
 */
public class RetrofitCallManager {

  private HashMap<String, Call> retrofitCall = new HashMap<>();

  public void addRetrofitCall(Call call, String key) {


  }

  public boolean removeRetrofitCall(String key) {
      return true;
  }

  public boolean cancelRetrofitCall(String key) {
    return true;
  }

  public boolean cancelAllCall() {

    return false;
  }







}

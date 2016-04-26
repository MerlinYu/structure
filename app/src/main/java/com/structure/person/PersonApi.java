package com.structure.person;

import com.structure.person.userdata.User;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by yuchao.
 */
public interface PersonApi {

  @GET("/user/{user}")
  void getUserInfo(@Path("user") String user, Callback<User> callback);
}

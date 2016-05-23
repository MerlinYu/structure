package com.structure.person;

import com.structure.person.userdata.User;

import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by yuchao.
 */
public interface PersonApi {

  @GET("/user/{user}")
  void getUserInfo(@Path("user") String user);
}

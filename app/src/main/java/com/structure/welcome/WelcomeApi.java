package com.structure.welcome;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yuchao on 7/13/16.
 */
public interface WelcomeApi {

  @GET("/user/{user}")
  //http://pic.ffpic.com/files/2014/0220/psd0219pbrslt.jpg
  void getUserInfo(@Path("user") String user);


  @GET("/user/{user}")
    //http://pic.ffpic.com/files/2014/0220/psd0219pbrslt.jpg
  Observable getWelcome();




}

package com.structure.test;

import android.app.Activity;
import android.os.Bundle;

import com.structure.R;
import com.structure.widget.view.GifMovieImageView;
import com.structure.widget.view.GifSurfaceView;

import static com.structure.R.id.GifImageView;

/**
 * Created by yuchao on 3/9/17.
 */

public class GifActivity extends Activity {

  private GifMovieImageView gifImageView;


  private GifSurfaceView gifSurfaceView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gif);


    gifImageView = (GifMovieImageView) findViewById(GifImageView);
    gifImageView.setGifImageResource(R.mipmap.launch);

//    gifSurfaceView = (GifSurfaceView) findViewById(R.id.gif_surface_view);
//    gifSurfaceView.setAnimFrameCount(75);
//    gifSurfaceView.setAnimDuration(3500);
//
//    gifSurfaceView.setResourcePref("start_0000");
//    if (gifSurfaceView != null) {
//      gifSurfaceView.startGifAnim();
//    }

  }


  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);


  }
}

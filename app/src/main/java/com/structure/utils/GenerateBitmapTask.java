package com.structure.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.structure.person.event.GenerateCardEvent;
import com.structure.person.userdata.UserCard;
import com.structure.widget.IdCardView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yuchao on 7/5/16.
 */
public class GenerateBitmapTask extends AsyncTask<UserCard,Void,String> {
  private String TAG = "===GenerateBitmapTask=== ";
  Context context;
  Long startTime;
  Long endTime;
  UserCard userCard;
  public GenerateBitmapTask(Context ctx) {
    context = ctx;
  }

  @Override
  protected String doInBackground(UserCard... params) {
    if (null == params) {
      return null;
    }
    userCard = params[0];
    if (null == userCard) {
      return null;
    }
    IdCardView  idCardView = new IdCardView(context, params[0])
        .setDensity(DisplayMetrics.DENSITY_XHIGH);
    Bitmap bitmap = idCardView.generateIdCard();
    return saveBitmap(bitmap);
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    startTime = System.currentTimeMillis();
  }

  @Override
  protected void onPostExecute(String path) {
//    super.onPostExecute(s);
    if (TextUtils.isEmpty(path)) {
      EventBus.getDefault().post(new GenerateCardEvent(userCard, null, endTime-startTime));
      return;
    }
    Uri pathUri = Uri.fromFile(new File(path));
    endTime = System.currentTimeMillis();
    EventBus.getDefault().post(new GenerateCardEvent(userCard, pathUri, endTime-startTime));
  }

  private String saveBitmap(Bitmap bitmap) {
    if (null == bitmap) {
      return null;
    }
    File dir = FileUtils.getTempDir();
    LogV("dir path "+dir.getAbsolutePath());
    String fileName = "id_card_" + System.currentTimeMillis() + ".png";
    File file = new File(dir, fileName);
    LogV(file.getAbsolutePath());
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return file.getAbsolutePath();
  }

  private void LogV(String log) {
    Log.v(TAG, log);
  }
}

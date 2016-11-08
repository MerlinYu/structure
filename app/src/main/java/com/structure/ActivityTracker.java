package com.structure;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yuchao on 10/8/16.
 * 跟踪activity的存活状态
 */

public class ActivityTracker {

  private static volatile ActivityTracker tracker;
  private ArrayList<WeakReference<Activity>> activityList;


  private ActivityTracker() {

  }

  public static ActivityTracker getInstance() {
    if (tracker == null) {
      synchronized (ActivityTracker.class) {
        if (tracker == null) {
          tracker = new ActivityTracker();
        }
      }
    }
    return tracker;
  }

  public void push(Activity activity) {
    if (activityList == null) {
      activityList = new ArrayList<>(16);
    }
    Iterator<WeakReference<Activity>> it = activityList.iterator();
    boolean contain = false;
    while (it.hasNext()) {
      WeakReference<Activity> weakReference = it.next();
      Activity act = weakReference.get();
      if (act == null) {
        it.remove();
        continue;
      }

      if (act.equals(activity)) {
        contain = true;
        break;
      }
    }
    if (!contain) {
      WeakReference<Activity> item = new WeakReference<>(activity);
      activityList.add(item);
    }
  }

  public void remove(Activity activity) {


  }

  public boolean isRoot() {

    return false;
  }


  public void finishAll() {
    if (activityList == null) {
      return;
    }
    Iterator<WeakReference<Activity>> iterator = activityList.iterator();
    while (iterator.hasNext()) {
      WeakReference<Activity> weakReference = iterator.next();
      Activity activity = weakReference.get();
      activity.finish();
    }
  }

}

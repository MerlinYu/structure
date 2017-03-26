package com.structure.person.event;

import android.net.Uri;

import com.structure.person.userdata.UserCard;

/**
 * Created by yuchao on 7/7/16.
 */
public class GenerateCardEvent {
  public static final int SUCCESS = 0;
  public static final int FAILED = -1;
  public UserCard userCard;
  public Uri cardUri;
  public Long time;
  public int result = SUCCESS;

  public GenerateCardEvent(UserCard source, Uri cardUri,Long time) {
    if (null == source || null == cardUri) {
      result = FAILED;
    }
    this.cardUri = cardUri;
    this.userCard = source;
    this.time = time;
  }

//  public
}

package com.structure.share;

import android.os.Bundle;

import com.structure.share.command.QQCommand;
import com.structure.share.command.ShareCommand;
import com.structure.share.command.ShareType;
import com.structure.share.command.WechatCommand;

/**
 * Created by yuchao.
 */
public class ShareItemFactory extends ItemFactory {

  ShareCommand shareCommand;

  private static ShareItemFactory __instance;
  private Bundle bundle;

  private ShareItemFactory() {

  }

  public static ShareItemFactory getInstance() {
    if (null == __instance) {
      __instance = new ShareItemFactory();
    }
    return __instance;
  }


  @Override
  void createCommand(ShareType shareType) {
    switch (shareType) {
      case qq:
        shareCommand = new QQCommand();
        break;
      case wechat:
        shareCommand = new WechatCommand();
        break;
      default:
        break;
    }
  }

  @Override
  void share() {
    shareCommand.execute();
  }

}

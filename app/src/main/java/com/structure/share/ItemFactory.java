package com.structure.share;

import com.structure.share.command.ShareType;

/**
 * Created by yuchao.
 */
public abstract class ItemFactory {

  abstract void createCommand(ShareType shareType);

  abstract void share();

}

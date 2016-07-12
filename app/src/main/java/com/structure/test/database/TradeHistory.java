package com.structure.test.database;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by yuchao on 7/12/16.
 */
@StorIOSQLiteType(table = TradeHistoryTable.TABLE)
public class TradeHistory {
  @StorIOSQLiteColumn(name = TradeHistoryTable.COLUMN_TIME)
  public String trade_time;

  @StorIOSQLiteColumn(name = TradeHistoryTable.COLUMN_CONTENT)
  public String content;

  @StorIOSQLiteColumn(name = TradeHistoryTable.COLUMN_TRADE_ID,key = true)
  public String trade_id;

  public TradeHistory() {

  }

  public TradeHistory(@NonNull String tradeId, String time, String content) {
    this.trade_id = tradeId;
    this.trade_time = time;
    this.content = content;
  }


  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("trade history \ntrade_id: " + trade_id);
    buffer.append("\ntrade_time: " + trade_time);
    buffer.append("\ntrade_content: " + content);
    return buffer.toString();
  }
}

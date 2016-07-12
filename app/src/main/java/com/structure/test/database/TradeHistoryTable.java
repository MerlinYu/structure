package com.structure.test.database;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.queries.Query;


/**
 * Created by yuchao on 7/12/16.
 */
public class TradeHistoryTable {

  public static final String TABLE = "trade_history";
  public static final String COLUMN_TRADE_ID = "trade_id";
  public static final String COLUMN_TIME = "trade_time";
  public static final String COLUMN_CONTENT = "content";


 public static final String CREATE = new QueryBuilder().createTable(TABLE)
     .addTextColumn(COLUMN_TRADE_ID)
     .addTextColumn(COLUMN_TIME)
     .addTextColumn(COLUMN_CONTENT)
     .build();


  public static Query queryByName(final String name) {
    return Query.builder()
        .table(TradeHistoryTable.TABLE)
        .where(TradeHistoryTable.COLUMN_TRADE_ID + " = ?")
        .whereArgs(name)
        .build();
  }
}

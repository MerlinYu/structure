package com.structure.test.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yuchao on 7/12/16.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

  private static final String DBName = "database.db";
  private static final int DATABASE_VERSION = 1;



  public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  public DatabaseOpenHelper(Context context) {
    super(context, DBName, null, DATABASE_VERSION);
  }


  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(TradeHistoryTable.CREATE);


  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //TODO:upgrade

  }
}

package com.structure.test.database;

/**
 * Created by yuchao on 7/12/16.
 */
public class QueryBuilder {
  private StringBuilder mbuilder;

  public QueryBuilder() {
    mbuilder = new StringBuilder();
  }

  public QueryBuilder createTable(String tableName) {
    mbuilder.append("CREATE TABLE ")
        .append(tableName)
        .append(" (")
        .append("_id INTEGER AUTO INCREMENT ")
        .append(")");
    return this;
  }

  public QueryBuilder addIntColumn(String columnName) {
    addIntColumn(columnName,false,false);
    return this;
  }

  public QueryBuilder addIntColumn(String columnName,boolean isPrimaryKey, boolean isNotNull) {
    StringBuilder builder = new StringBuilder();
    builder.append(",")
        .append(columnName)
        .append(" INTEGER");
    if (isPrimaryKey) {
      builder.append(" PRIMARY KEY ");
    }
    if (isNotNull) {
      builder.append(" NOT NULL");
    }
    mbuilder.insert(getQueryIndexCloseSymbol(),builder.toString());
    return this;
  }


  public QueryBuilder addTextColumn(String columnName) {
    addTextColumn(columnName,false,false);
    return this;
  }

  public QueryBuilder addTextColumn(String columnName, boolean isPrimaryKey, boolean isNotNull) {
    StringBuilder builder = new StringBuilder();
    builder.append(",")
        .append(columnName)
        .append(" TEXT");
    if (isPrimaryKey) {
      builder.append(" PRIMARY KEY ");
    }
    if (isNotNull) {
      builder.append(" NOT NULL");
    }
    mbuilder.insert(getQueryIndexCloseSymbol(),builder.toString());
    return this;
  }


  private int getQueryIndexCloseSymbol() {
    return mbuilder.lastIndexOf(")");
  }

  public String build() {
    return mbuilder.toString();
  }



}

package com.structure.test.database;

import android.app.Application;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import java.util.Collection;
import java.util.List;
import rx.Observable;

/**
 * Created by yuchao on 7/12/16.
 */
public final class DbManager {


  private volatile static DbManager instance;

  private DefaultStorIOSQLite mDefaultSQLite;

  private DbManager() {}

  public static DbManager getInstance() {
    if (instance == null) {
      synchronized (DbManager.class) {
        if (null == instance) {
          instance = new DbManager();
        }
      }
    }
    return instance;
  }

  public void data() {
  }

  public void init(Application application) {
    mDefaultSQLite = DefaultStorIOSQLite.builder()
        .sqliteOpenHelper(new DatabaseOpenHelper(application))
        .addTypeMapping(TradeHistory.class, SQLiteTypeMapping.<TradeHistory>builder()
            .putResolver(new TradeHistoryStorIOSQLitePutResolver())
            .getResolver(new TradeHistoryStorIOSQLiteGetResolver())
            .deleteResolver(new TradeHistoryStorIOSQLiteDeleteResolver())
            .build())
        .build();
  }

  public <T> PutResult Put(@NonNull T t) {
    return getDefaultSQLite().put()
        .object(t)
        .prepare()
        .executeAsBlocking();
  }

  public <T> PutResults<T> Put(@NonNull Collection<T> list) {
    return getDefaultSQLite().put()
        .objects(list)
        .prepare()
        .executeAsBlocking();
  }

  public <T> T Get(@NonNull Class<T> object, @NonNull Query query) {
    List<T> result = getDefaultSQLite().get()
        .listOfObjects(object)
        .withQuery(query)
        .prepare()
        .executeAsBlocking();
    if (result.size() == 0) {
      return null;
    }
    return  result.get(0);
  }

  public <T> List<T> List(@NonNull Class<T> object, @NonNull Query query) {
    return getDefaultSQLite().get()
        .listOfObjects(object)
        .withQuery(query)
        .prepare()
        .executeAsBlocking();
  }

  public <T> DeleteResult Delete(@NonNull T object) {
    return getDefaultSQLite().delete()
        .object(object)
        .prepare()
        .executeAsBlocking();
  }
  public DeleteResult Delete(@NonNull DeleteQuery query) {
    return getDefaultSQLite().delete()
        .byQuery(query)
        .prepare()
        .executeAsBlocking();
  }

  //TODO: Observable support RX test
  @NonNull
  @CheckResult
  public <T> Observable<List<T>> observable(@NonNull Class<T> clazz, @NonNull Query query) {
    return getDefaultSQLite().get()
        .listOfObjects(clazz)
        .withQuery(query)
        .prepare()
        .createObservable()
        .asObservable();
  }

  //TODO: cursor



  private DefaultStorIOSQLite getDefaultSQLite() {
    if (null == mDefaultSQLite) {
      throw new NullPointerException("null StorIOSQLite");
    }
    return mDefaultSQLite;
  }

}

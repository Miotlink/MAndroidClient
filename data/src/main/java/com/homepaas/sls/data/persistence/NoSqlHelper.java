package com.homepaas.sls.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.homepaas.sls.data.BuildConfig;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import javax.inject.Singleton;

/**
 * Date: 2016/10/4.
 * Author: fmisser
 * Description:
 */

@Singleton
public class NoSqlHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = "NoSqlHelper";
    private Gson gson = new Gson();

    public NoSqlHelper(Context context) {
        super(context, BuildConfig.NO_SQL_DB_NAME, null, BuildConfig.NO_SQL_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, NoSqlEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "create table failed:" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //升级策略,可根据实际情况处理
    }

    @SuppressWarnings("unchecked")
    public int save(NoSqlEntity entity) {
        try {
            Dao dao = getDao(NoSqlEntity.class);
            entity.setBytes(gson.toJson(entity.getValue()).getBytes());
            Dao.CreateOrUpdateStatus status = dao.createOrUpdate(entity);
            return status.getNumLinesChanged();
        } catch (SQLException e) {
            Log.e(TAG, "save entity failed:" + e.getMessage());
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public int delete(NoSqlEntity entity) {
        try {
            Dao dao = getDao(NoSqlEntity.class);
            return dao.delete(entity);
        } catch (SQLException e) {
            Log.e(TAG, "delete entity failed:" + e.getMessage());
            return 0;
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> tClass) {
        try {
            Dao dao = getDao(NoSqlEntity.class);
            NoSqlEntity entity = (NoSqlEntity) dao.queryForId(key);
            return gson.fromJson(new String(entity.getBytes()), tClass);
        } catch (SQLException e) {
            Log.e(TAG, "get entity failed:" + e.getMessage());
            return null;
        }
    }
}

package com.homepaas.sls.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.homepaas.sls.data.entity.App;
import com.homepaas.sls.data.entity.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * on 2016/2/24 0024
 *
 * @author zhudongjie .
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    public DBHelper(Context context) {
        super(context, "test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, App.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}

package com.homepaas.sls.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.entity.AccountEntity;
import com.homepaas.sls.data.entity.BusinessEntity;
import com.homepaas.sls.data.entity.CallLogEntity;
import com.homepaas.sls.data.entity.CollectionConfig;
import com.homepaas.sls.data.entity.LifeServiceEntity;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.data.entity.PersonalInfoEntity;
import com.homepaas.sls.data.entity.WorkerEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2015/12/21.
 * OrmLite 数据库帮助类
 */

@Singleton
public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "OrmLiteHelper";

    @Inject
    public OrmLiteHelper(Context context) {
        super(context, BuildConfig.DB_NAME, null, BuildConfig.DB_VERSION);
    }

    /**
     * What to do when your database needs to be created. Usually this entails creating the tables and loading any
     * initial data.
     * <p/>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being created.
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, LifeServiceEntity.class);
            TableUtils.createTable(connectionSource, PersonalInfoEntity.class);
            TableUtils.createTable(connectionSource, CallLogEntity.class);

            TableUtils.createTable(connectionSource, WorkerEntity.class);
            TableUtils.createTable(connectionSource, BusinessEntity.class);
            TableUtils.createTable(connectionSource, BusinessEntity.Photo.class);
            TableUtils.createTable(connectionSource, WorkerEntity.Photo.class);
            TableUtils.createTable(connectionSource, WorkerEntity.SystemCertification.class);
            TableUtils.createTable(connectionSource, BusinessEntity.SystemCertification.class);
            TableUtils.createTable(connectionSource, CollectionConfig.class);
            TableUtils.createTable(connectionSource,AccountEntity.class);
//            TableUtils.createTable(connectionSource,OrderEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    /**
     * What to do when your database needs to be updated. This could mean careful migration of old data to new data.
     * Maybe adding or deleting database columns, etc..
     * <p/>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being upgraded.
     * @param connectionSource To use get connections to the database to be updated.
     * @param oldVersion       The version of the current database so we can know what to do to the database.
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.clearTable(connectionSource, LifeServiceEntity.class);
            TableUtils.clearTable(connectionSource, PersonalInfoEntity.class);
            TableUtils.clearTable(connectionSource, CallLogEntity.class);

            TableUtils.clearTable(connectionSource, WorkerEntity.class);
            TableUtils.clearTable(connectionSource, BusinessEntity.class);
            TableUtils.clearTable(connectionSource, BusinessEntity.Photo.class);
            TableUtils.clearTable(connectionSource, WorkerEntity.Photo.class);
            TableUtils.clearTable(connectionSource, WorkerEntity.SystemCertification.class);
            TableUtils.clearTable(connectionSource, BusinessEntity.SystemCertification.class);

            TableUtils.clearTable(connectionSource, CollectionConfig.class);

            TableUtils.clearTable(connectionSource, AccountEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return LifeServiceEntity的Dao
     */
    public Dao<LifeServiceEntity, String> getLifeServiceDao() {
        try {
            return getDao(LifeServiceEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return PersonalInfoEntity的Dao
     */
    public Dao<PersonalInfoEntity, String> getPersonalInfoDao() {
        try {
            return getDao(PersonalInfoEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return CallLogEntity的Dao
     */
    public Dao<CallLogEntity,Integer> getCallLogDao(){
        try {
            return getDao(CallLogEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "getCallLogDao: ", e);
        }
        return null;
    }

    public Dao<WorkerEntity,String> getWorkerDao(){
        try {
            return getDao(WorkerEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "getWorkerDao: ", e);
        }
        return null;
    }

    public Dao<BusinessEntity,String> getBusinessDao(){
        try {
            return getDao(BusinessEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "getBusinessDao: ", e);
        }
        return null;
    }

    public Dao<CollectionConfig,Integer> getCollectionConfigDao(){
        try {
            return getDao(CollectionConfig.class);
        }catch (SQLException e){
            Log.e(TAG, "getCollectionConfigDao: ",e );
        }
        return null;
    }

    public Dao<WorkerEntity.SystemCertification,String> getWorkerSystemCertificationDao(){
        try {
            return getDao(WorkerEntity.SystemCertification.class);
        } catch (SQLException e) {
            Log.e(TAG, "getWorkerSystemCertificationDao: ", e);
        }
        return null;
    }

    public Dao<BusinessEntity.SystemCertification,String> getBusinessSystemCertificationDao(){
        try {
            return getDao(BusinessEntity.SystemCertification.class);
        } catch (SQLException e) {
            Log.e(TAG, "getBusinessSystemCertificationDao: ", e);
        }
        return null;
    }

    public Dao<WorkerEntity.Photo,String> getWorkerPhotoDao(){
        try {
            return getDao(WorkerEntity.Photo.class);
        } catch (SQLException e) {
            Log.e(TAG, "getWorkerPhotoDao: ", e);
        }
        return null;
    }

    public Dao<BusinessEntity.Photo,String> getBusinessPhotoDao(){
        try {
            return getDao(BusinessEntity.Photo.class);
        } catch (SQLException e) {
            Log.e(TAG, "getBusinessPhotoDao: ", e);
        }
        return null;
    }

    public Dao<AccountEntity,String> getAccountDao(){
        try {
            return getDao(AccountEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "getAccountDao: ", e);
        }
        return null;
    }
}

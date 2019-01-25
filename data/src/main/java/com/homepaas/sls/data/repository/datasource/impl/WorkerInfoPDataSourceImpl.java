package com.homepaas.sls.data.repository.datasource.impl;

import android.support.annotation.Nullable;

import com.homepaas.sls.data.entity.WorkerEntity;
import com.homepaas.sls.data.entity.mapper.WorkerDataMapper;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.repository.datasource.WorkerInfoPDataSource;
import com.homepaas.sls.data.validator.Validator;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/2/23 0023
 *
 * @author zhudongjie .
 */
@Singleton
public class WorkerInfoPDataSourceImpl implements WorkerInfoPDataSource {

    private Dao<WorkerEntity, String> dao;

    private Dao<WorkerEntity.Photo,String> photoDao;

    private Dao<WorkerEntity.SystemCertification,String> systemCertificationDao;

    private Validator<WorkerEntity> validator;

    private WorkerDataMapper mapper;

    @Inject
    public WorkerInfoPDataSourceImpl(@Nullable Dao<WorkerEntity, String> dao, @Nullable Dao<WorkerEntity.Photo, String> photoDao,
                                     @Nullable Dao<WorkerEntity.SystemCertification, String> systemCertificationDao,
                                     Validator<WorkerEntity> validator, WorkerDataMapper mapper) {
        this.dao = dao;
        this.photoDao = photoDao;
        this.systemCertificationDao = systemCertificationDao;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public WorkerInfo get(String workerId) throws GetPersistenceDataException, InvalidPersistenceDataException {
        try {
            WorkerEntity entity = dao.queryForId(workerId);
            if (entity == null || !validator.isValid(entity)) {
                throw new InvalidPersistenceDataException();
            }
            return mapper.transform(entity);
        } catch (SQLException e) {
            throw new GetPersistenceDataException(e);
        }
    }

    @Override
    public List<WorkerInfo> getCollectedWorkers() throws GetPersistenceDataException, InvalidPersistenceDataException {
        return null;
    }

    @Override
    public List<WorkerInfo> getSearchedWorkers() throws GetPersistenceDataException, InvalidPersistenceDataException {
        try {
            List<WorkerEntity> entities = dao.queryForEq("dataFrom", WorkerEntity.FROM_SEARCH);
            return mapper.transform(entities);
        } catch (SQLException e) {
            throw new GetPersistenceDataException(e);
        }
    }

    @Override
    public void save(List<WorkerInfo> workers,int dataFrom) throws PersistDataException {
        //暂时先这么处理
        for (WorkerInfo worker : workers) {
            save(worker,dataFrom);
        }
    }

    @Override
    public void save(WorkerInfo worker,int dataFrom) throws PersistDataException {
        WorkerEntity entity = mapper.transform(worker, System.currentTimeMillis());
        entity.setDataFrom(dataFrom);
        try {
            dao.createOrUpdate(entity);
//            photoDao2.createOrUpdate(entity.getPhoto());
//            for (WorkerEntity.Photo photo : entity.getPhotos()) {
//                photoDao.createOrUpdate(photo);
//            }
            for (WorkerEntity.SystemCertification certification : entity.getSystemCertification()) {
                systemCertificationDao.createOrUpdate(certification);
            }
        } catch (SQLException e) {
            throw new PersistDataException(e);
        }
    }



}

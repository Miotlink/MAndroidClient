package com.homepaas.sls.data.repository.datasource.impl;

import android.support.annotation.Nullable;

import com.homepaas.sls.data.entity.BusinessEntity;
import com.homepaas.sls.data.entity.mapper.BusinessDataMapper;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.repository.datasource.BusinessInfoPDataSource;
import com.homepaas.sls.data.validator.Validator;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/2/24 0024
 *
 * @author zhudongjie .
 */
@Singleton
public class BusinessInfoPDataSourceImpl implements BusinessInfoPDataSource {


    private Dao<BusinessEntity, String> dao;

    private Dao<BusinessEntity.Photo, String> photoDao;
    private Dao<BusinessEntity.SystemCertification, String> systemCertificationDao;

    private Validator<BusinessEntity> validator;

    private BusinessDataMapper mapper;

    @Inject
    public BusinessInfoPDataSourceImpl(Dao<BusinessEntity, String> dao, @Nullable Dao<BusinessEntity.Photo, String> photoDao, Dao<BusinessEntity.SystemCertification,
            String> systemCertificationDao, Validator<BusinessEntity> validator, BusinessDataMapper mapper) {
        this.dao = dao;
        this.photoDao = photoDao;
        this.systemCertificationDao = systemCertificationDao;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public BusinessInfo get(String businessId) throws GetPersistenceDataException, InvalidPersistenceDataException {
        try {
            BusinessEntity entity = dao.queryForId(businessId);

            if (entity == null || !validator.isValid(entity)) {
                throw new InvalidPersistenceDataException();
            }
            return mapper.transform(entity);
        } catch (SQLException e) {
            throw new GetPersistenceDataException(e);
        }
    }

    @Override
    public List<BusinessInfo> getCollectedBusinesses() throws GetPersistenceDataException, InvalidPersistenceDataException {
        return null;
    }

    @Override
    public List<BusinessInfo> getSearchedBusinesses() throws GetPersistenceDataException, InvalidPersistenceDataException {
        try {
            List<BusinessEntity> entities = dao.queryForEq("dataFrom", BusinessEntity.FROM_SEARCH);
            return mapper.transform(entities);
        } catch (SQLException e) {
            throw new GetPersistenceDataException(e);
        }
    }

    @Override
    public void save(BusinessInfo business, int dataFrom) throws PersistDataException {
        BusinessEntity entity = mapper.transform(business, System.currentTimeMillis());
        entity.setDataFrom(dataFrom);
        try {
            dao.createOrUpdate(entity);
//            photoDao2.createOrUpdate(entity.getPhoto());
            for (BusinessEntity.Photo photo : entity.getPhotos()) {
                photoDao.createOrUpdate(photo);
            }
            for (BusinessEntity.SystemCertification certification : entity.getSystemCertifications()) {
                systemCertificationDao.createOrUpdate(certification);
            }
        } catch (SQLException e) {
            throw new PersistDataException(e);
        }
    }

    @Override
    public void save(List<BusinessInfo> businesses, int dataFrom) throws PersistDataException {
        //暂时这么处理
        for (BusinessInfo business : businesses) {
            save(business, dataFrom);
        }
    }
}

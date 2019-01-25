package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.entity.CallLogEntity;
import com.homepaas.sls.data.entity.mapper.CallLogEntityMapper;
import com.homepaas.sls.data.repository.datasource.CallLogPDataSource;
import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@Singleton
public class CallLogPDataSourceImpl implements CallLogPDataSource {

    private Dao<CallLogEntity, Integer> dao;

    private CallLogEntityMapper mapper;

    @Inject
    public CallLogPDataSourceImpl(Dao<CallLogEntity, Integer> dao, CallLogEntityMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<CallLog> getCallLogs() throws GetDataException {
        try {
            return mapper.transform(dao.queryForAll());
        } catch (SQLException e) {
            throw new GetDataException(e);
        }
    }

    @Override
    public int deleteCallLogs(String phoneNumber) throws SaveDataException {
        try {
            DeleteBuilder<CallLogEntity, Integer> builder = dao.deleteBuilder();
            builder.where().eq("phoneNumber", phoneNumber);
            PreparedDelete<CallLogEntity> prepare = builder.prepare();
            return dao.delete(prepare);
        } catch (SQLException e) {
            throw new SaveDataException(e);
        }
    }

    @Override
    public List<CallLog> getCallLogs(String phoneNumber) throws GetDataException {
        try {
            return mapper.transform(dao.queryForEq("phoneNumber", phoneNumber));
        } catch (SQLException e) {
            throw new GetDataException(e);
        }
    }

    @Override
    public boolean saveCallLog(CallLog callLog) throws SaveDataException {
        try {
            CallLogEntity entity = mapper.transform(callLog);
            return dao.create(entity) == 1;
        } catch (SQLException e) {
            throw new SaveDataException(e);
        }
    }
}

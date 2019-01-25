package com.homepaas.sls.data.entity;

import com.homepaas.sls.data.validator.ttl.TtlObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * on 2016/2/23 0023
 *
 * @author zhudongjie .
 */
@DatabaseTable(tableName = "tb_collection")
public class CollectionConfig implements TtlObject{

    public static final int BUSINESS = 1;

    public static final int WORKER = 2;

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int type;

    @DatabaseField
    private long persistedTime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
    }

    @Override
    public long getPersistedTime() {
        return persistedTime;
    }
}

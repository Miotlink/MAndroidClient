package com.homepaas.sls.data.persistence;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Date: 2016/10/4.
 * Author: fmisser
 * Description: key-value 存储
 */

@DatabaseTable(tableName = "tb_no_sql_entity")
public class NoSqlEntity<T> {

    /**
     * key值,唯一,否则会失败
     */
    @DatabaseField(id = true)
    private String key;

    private T value;

    /**
     * 存储的对象,和 value 对应
     */
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] bytes;

//    @DatabaseField
//    private String clazz;

    /**
     * 创建时间,如果创建该对象的时候不指定创建时间,则默认设置为该对象的创建时间
     */
    @DatabaseField
    private long createTime;


    public NoSqlEntity(String key, T value) {
        this.key = key;
        this.value = value;
        this.createTime = System.currentTimeMillis();
    }

    public NoSqlEntity(String key, T value, long createTime) {
        this.key = key;
        this.value = value;
        this.createTime = createTime;
    }

    public T getValue() {
        return value;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}

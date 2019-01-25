package com.homepaas.sls.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * on 2016/2/24 0024
 *
 * @author zhudongjie .
 */
@DatabaseTable(tableName = "tb_user")
public class User {
    @DatabaseField(id = true)
    public String id;

    @DatabaseField
    public String name;

    @ForeignCollectionField(eager = true)
    public Collection<App> apps;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", apps=" + apps +
                '}';
    }
}

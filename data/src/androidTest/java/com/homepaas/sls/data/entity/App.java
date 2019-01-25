package com.homepaas.sls.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * on 2016/2/24 0024
 *
 * @author zhudongjie .
 */
@DatabaseTable(tableName = "tb_app")
public class App {

    @DatabaseField
    public String name;

    @DatabaseField(foreign = true, foreignColumnName = "id")
    public User user;

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                '}';
    }
}

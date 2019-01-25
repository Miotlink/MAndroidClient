package com.homepaas.sls.entity;

/**
 * on 2016/3/1 0001
 *
 * @author zhudongjie .
 */
public class User {

    public String name;

    public int id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}

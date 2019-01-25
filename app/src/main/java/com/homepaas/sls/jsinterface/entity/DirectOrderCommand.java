package com.homepaas.sls.jsinterface.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CJJ on 2016/9/19.
 *
 */

public class DirectOrderCommand implements Serializable{

    @SerializedName("TypeId")
    private String TypeId;
    @SerializedName("TypeName")
    private String TypeName;
    @SerializedName("Token")
    private String Token;
    @SerializedName("Children")
    private List<DirectOrderCommand> Children;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public List<DirectOrderCommand> getChildren() {
        return Children;
    }

    public void setChildren(List<DirectOrderCommand> children) {
        Children = children;
    }
}

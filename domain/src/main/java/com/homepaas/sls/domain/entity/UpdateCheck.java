package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/5/24 0024
 *
 * @author zhudongjie
 */
public class UpdateCheck {

    @SerializedName("apk_version")
    private int version;

    @SerializedName("apk_path")
    private String url;

    /**
     * 0可选更新，1强制更新
     */
    @SerializedName("apk_isforceupdate")
    private int forceUpdate;

    @SerializedName("apk_md5")
    private String md5;

    /**
     * 文件大小
     */
    @SerializedName("apk_size")
    private long size;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateCheck{");
        sb.append("version=").append(version);
        sb.append(", url='").append(url).append('\'');
        sb.append(", forceUpdate=").append(forceUpdate);
        sb.append(", md5='").append(md5).append('\'');
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}

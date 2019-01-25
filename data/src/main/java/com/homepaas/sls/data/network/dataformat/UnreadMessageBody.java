package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/3 0003
 *
 * @author zhudongjie .
 */
public class UnreadMessageBody {

    @SerializedName("UnreadTotal")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

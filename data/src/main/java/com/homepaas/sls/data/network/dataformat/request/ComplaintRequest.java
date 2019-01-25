package com.homepaas.sls.data.network.dataformat.request;

/**
 * on 2016/2/26 0026
 *
 * @author zhudongjie .
 */
public class ComplaintRequest {

    public ComplaintRequest(String token, String orderId, String indexs, String titles, String reason) {
        Token = token;
        OrderId = orderId;
        Indexs = indexs;
        Titles = titles;
        Reason = reason;
    }

    /**
     * Token：必填；
     OrderId：必填；
     Indexs：必填；多个则以逗号隔开
     Titles：必填； 多个则以逗号隔开
     Reason：原因描述，选填；
     可以复制下面的数据结构进行测试，application/json，如下：
     *
     * Token : xxxxxx
     * OrderId : xxxxxx
     * Indexs : 0,1,2
     * Titles : 上门不及时,服务质量差,服务态度差
     * Reason : xxxxxx
     */

    private String Token;
    private String OrderId;
    private String Indexs;
    private String Titles;
    private String Reason;

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getIndexs() {
        return Indexs;
    }

    public void setIndexs(String Indexs) {
        this.Indexs = Indexs;
    }

    public String getTitles() {
        return Titles;
    }

    public void setTitles(String Titles) {
        this.Titles = Titles;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }
}

package com.homepaas.sls.data.entity;

import java.util.List;

/**
 * Created by JWC on 2017/8/29.
 */

public class ComplaintListEntity {

    /**
     * Index : 0
     * Title : 上门不及时
     */

    private List<ListBean> List;

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        private boolean isCheck;
        private String Index;
        private String Title;

        public String getIndex() {
            return Index;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public void setIndex(String Index) {
            this.Index = Index;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }
    }
}

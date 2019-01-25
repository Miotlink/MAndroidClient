package com.homepaas.sls.event;

/**
 * Created by mhy on 2017/11/21.
 */

public class DemandRemarkEvent {
    private String demandRemarkContent;

    public String getDemandRemarkContent() {
        return demandRemarkContent;
    }

    public void setDemandRemarkContent(String demandRemarkContent) {
        this.demandRemarkContent = demandRemarkContent;
    }

    public DemandRemarkEvent(String demandRemarkContent) {
        this.demandRemarkContent = demandRemarkContent;
    }
}

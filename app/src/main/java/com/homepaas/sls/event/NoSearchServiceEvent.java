package com.homepaas.sls.event;

/**
 * Created by CJJ on 2016/11/10.
 *
 */

public class NoSearchServiceEvent {

    public String searchContent;

    public NoSearchServiceEvent(String searchContent) {
        this.searchContent = searchContent;
    }
}

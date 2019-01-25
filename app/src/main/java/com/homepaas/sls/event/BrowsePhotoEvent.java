package com.homepaas.sls.event;

import java.util.List;

/**
 * Created by CJJ on 2016/9/22.
 *
 */

public class BrowsePhotoEvent {

    public List<String> filePaths;
    public int index;

    public BrowsePhotoEvent() {
    }

    public BrowsePhotoEvent(List<String> filePaths, int index) {
        this.filePaths = filePaths;
        this.index = index;
    }
}

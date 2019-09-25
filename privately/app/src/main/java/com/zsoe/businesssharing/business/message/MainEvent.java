package com.zsoe.businesssharing.business.message;

import java.util.List;

import indi.liyi.viewer.ViewData;

/**
 * Created by alex on 2017/6/9.
 */

public class MainEvent {

    public int position;
    public List<ViewData> mVdList;

    public MainEvent(int position, List<ViewData> mVdList) {

        this.position = position;
        this.mVdList = mVdList;
    }

}

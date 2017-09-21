package com.tdme.android.musicalbums.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 * EventBus的bean类
 */

public class ListEvent {

    private List<String>mSelected;

    public ListEvent(List<String>mSelected){
        this.mSelected = mSelected;
    }

    public List<String> getmSelected() {
        return mSelected;
    }
}

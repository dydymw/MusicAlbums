package com.tdme.android.musicalbums.adapter;

import com.tdme.android.musicalbums.bean.Photos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/4.
 */

public class Presenter {

    private Presenter(){}

    public static Presenter getInstance() {
        return Holder.sInstance;
    }

    private static class Holder {
        private static final Presenter sInstance = new Presenter();
    }

    HashMap<AddTextAdapter.ViewHolder, Photos> mMap = new LinkedHashMap<>();
    HashMap<Photos, AddTextAdapter.ViewHolder> mReverseMap = new LinkedHashMap<>();

    public void register(AddTextAdapter.ViewHolder viewHolder, Photos photos) {
        mMap.put(viewHolder, photos);
        mReverseMap.put(photos, viewHolder);
    }

    public void modifyTextContent(AddTextAdapter.ViewHolder viewHolder, String text) {
        Photos photos = mMap.get(viewHolder);
        if (null != photos) {
            photos.setText(text);
        }
    }

    public List<String> getContentText(){
        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<AddTextAdapter.ViewHolder, Photos>> iterator = mMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<AddTextAdapter.ViewHolder, Photos> entry = iterator.next();
            // 1
            list.add(entry.getKey().getContentText());
            // 2
//            list.add(entry.getValue().getText());
        }
        return list;
    }

//    public void test() {
//        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
//        for (int i = 0; i < 10; i++) {
//            map.put(i+"", i);
//        }
//        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
//        while(it.hasNext()) {
//            Map.Entry<String, Integer> entry = it.next();
//            System.out.println(entry.getValue());
//        }
//
//    }


}

package com.tdme.android.musicalbums;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tdme.android.musicalbums.adapter.AddTextAdapter;
import com.tdme.android.musicalbums.adapter.PhotoNetAdapter;
import com.tdme.android.musicalbums.adapter.Presenter;
import com.tdme.android.musicalbums.bean.ListEvent;
import com.tdme.android.musicalbums.bean.Photos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 *
 * 添加字幕的Fragment
 */

public class AddTextFragment extends Fragment{

    private static final String TAG = "AddTextFragment";

    private LinearLayoutManager layoutManager;
    private RecyclerView mRecyclerView;
    private AddTextAdapter mAddTextAdapter;
    private List<Photos>photosList;
    private List<String>mSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_text,container,false);

        EventBus.getDefault().register(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.add_text_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);


//        photosList = new ArrayList<>();
//        mSelected = new ArrayList<>();
//        Log.i(TAG, "onCreateView: ");
//        for (int i = 0; i < mSelected.size(); i++) {
//            Photos photos = new Photos(mSelected.get(i));
//            photosList.add(photos);
//        }
//
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.add_text_recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(Myapplication.getContext()));
//
//        mAddTextAdapter = new AddTextAdapter(photosList);
//        mRecyclerView.setAdapter(mAddTextAdapter);

        return view;
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(ListEvent event) {

        mSelected = event.getmSelected();
        photosList = new ArrayList<>();
        for (int j = 0; j < mSelected.size(); j++) {
            Log.i(TAG, mSelected.get(j));
            Photos photos = new Photos(mSelected.get(j));
            photosList.add(photos);
        }


        mAddTextAdapter = new AddTextAdapter(photosList);
        mRecyclerView.setAdapter(mAddTextAdapter);


    }

    public List<String> getContentText(){
        return Presenter.getInstance().getContentText();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

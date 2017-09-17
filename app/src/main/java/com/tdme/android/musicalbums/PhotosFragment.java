package com.tdme.android.musicalbums;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tdme.android.musicalbums.activity.MainActivity;
import com.tdme.android.musicalbums.adapter.PhotoNetAdapter;
import com.tdme.android.musicalbums.bean.ListEvent;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class PhotosFragment extends Fragment {

    private static final int REQUEST_CODE = 732;


    private RecyclerView mRecyclerView;

    private static final String TAG = "AddTextFragmen";
    private List<String> list = new ArrayList<>();
    private List<Uri> mSelected;
    private PhotoNetAdapter photoNetAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_photo, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.photo_net);
        mRecyclerView.setLayoutManager(new GridLayoutManager(Myapplication.getContext(), 4));

        photoNetAdapter = new PhotoNetAdapter(list);
        photoNetAdapter.setOnRecyclerViewBottom(new PhotoNetAdapter.OnRecyClickerListener() {
            @Override
            public void click(View view, int position) {
                choose();
            }
        });

        mRecyclerView.setAdapter(photoNetAdapter);
        setFooterView(mRecyclerView);
        return view;
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(Myapplication.getContext()).inflate(R.layout.footer, view, false);
        photoNetAdapter.setmFooterView(footer);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mSelected = Matisse.obtainResult(data);
                for (int i = 0; i < mSelected.size(); i++) {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor imageCursor = getActivity().managedQuery(mSelected.get(i), proj, null, null, null);
                    int index = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    imageCursor.moveToFirst();
                    list.add(imageCursor.getString(index));
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        photoNetAdapter.notifyDataSetChanged();
        EventBus.getDefault().postSticky(new ListEvent(list));

    }


    private void choose() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(20)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE);
    }

    public List<String> getList() {
        return list;
    }
}

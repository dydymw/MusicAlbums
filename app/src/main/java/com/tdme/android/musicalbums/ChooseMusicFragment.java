package com.tdme.android.musicalbums;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tdme.android.musicalbums.activity.MapActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 *
 * 属性选择的Fragment
 */

public class ChooseMusicFragment extends Fragment {


    private Spinner mSpinner;
    private ArrayAdapter<String> arr_adapter;
    //    private String[] data_list = {"默认主题","匆匆那年","旅行的意义","风景如画","诗和远方","在路上"};
    private List<String> data_list;
    private String selectedMode;
    private TextView selectedLocate;
    private EditText albumsName;
    private ImageView coverImg;
    private String coverPath;
    private String locationCoordinate;
    private String locationName;
    private boolean permission;
    private TextView permissionTv;
    private Switch aSwitch;

    private static final int REQUEST_CODE = 731;
    private static final int REQUEST_LOCATION = 730;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_music, container, false);

        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        selectedLocate = (TextView) view.findViewById(R.id.choose_loacte);
        albumsName = (EditText) view.findViewById(R.id.albums_name);
        coverImg = (ImageView) view.findViewById(R.id.choose_cover);

        coverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });

        selectedLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivityForResult(intent, REQUEST_LOCATION);
            }
        });

//        arr_adapter = new ArrayAdapter<String>(Myapplication.getContext(),android.R.layout.simple_spinner_item,data_list);
//        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinner.setAdapter(arr_adapter);
//        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        data_list = new ArrayList<>();
        data_list.add("默认主题");
        data_list.add("诗和远方");
        data_list.add("风景如画");


        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arr_adapter);
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        permissionTv = (TextView) view.findViewById(R.id.permission);
        aSwitch = (Switch) view.findViewById(R.id.switch_btn);
        permissionTv.setText("私有");
        permission = aSwitch.isChecked();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    permissionTv.setText("公开");
                    permission = isChecked;
                }else {
                    permissionTv.setText("私有");
                    permission = isChecked;
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Glide.with(Myapplication.getContext())
                        .load(Matisse.obtainResult(data).get(0))
                        .override(100, 100)
                        .centerCrop()
                        .into(coverImg);
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor imageCursor = getActivity().managedQuery(Matisse.obtainResult(data).get(0), proj, null, null, null);
                int index = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                imageCursor.moveToFirst();
                coverPath = imageCursor.getString(index);
                return;
            }


        }
        if (resultCode == REQUEST_LOCATION) {
            locationName = data.getStringExtra("locationName");
            locationCoordinate = data.getStringExtra("locationCoordinate");
            selectedLocate.setText(locationName);
            return;

        }


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


    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            selectedMode = String.valueOf(arg2 + 1);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public String getAlbumsName() {
        return albumsName.getText().toString();
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationCoordinate() {
        return locationCoordinate;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public boolean getPermission(){
        return permission;
    }
}

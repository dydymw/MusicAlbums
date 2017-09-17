package com.tdme.android.musicalbums.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tdme.android.musicalbums.AddTextFragment;
import com.tdme.android.musicalbums.ChooseMusicFragment;
import com.tdme.android.musicalbums.PhotosFragment;
import com.tdme.android.musicalbums.R;
import com.tdme.android.musicalbums.adapter.AddTextAdapter;
import com.tdme.android.musicalbums.adapter.Presenter;
import com.tdme.android.musicalbums.bean.ListEvent;
import com.tdme.android.musicalbums.bean.Photos;
import com.tdme.android.musicalbums.utils.CompressHelper;
import com.tdme.android.musicalbums.utils.Crop;
import com.tdme.android.musicalbums.utils.FileUtil;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private int mCurrentFragment;
    private ViewPager vpContainer;
    private RadioGroup rgTabButtons;
    private ImageView showBtn;
    private ProgressBar progressBar;

    private List<String> mSelected;
    private List<String> filepath;

    private AVObject object;
    private AVFile file;
    final List<AVFile> photos = new ArrayList<>();
    private String textArray[];
    private List<String> photosUrl = new ArrayList<>();
    private KickerFragmentAdapter adapter;
    private ChooseMusicFragment chooseMusicFragment;


    public static final int UPDATE_TEXT = 1;
    private int i = 0;

    private String photosId;

    private String musicKey;

    private List<File> loadFile;

    private String[] fragments = new String[]{
            PhotosFragment.class.getName(),
            AddTextFragment.class.getName(),
            ChooseMusicFragment.class.getName(),
    };


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    progressBar.setVisibility(View.INVISIBLE);
//                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViews();

    }

    public void init() {
        PermissionGen.with(MainActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();

    }


    public void initViews() {
        vpContainer = (ViewPager) findViewById(R.id.vpContainer);
        rgTabButtons = (RadioGroup) findViewById(R.id.photo_library);
        adapter = new KickerFragmentAdapter(getSupportFragmentManager(), this);
        vpContainer.setAdapter(adapter);
        vpContainer.setOnPageChangeListener(onPageChangeListener);
        vpContainer.setCurrentItem(mCurrentFragment);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        rgTabButtons.setOnCheckedChangeListener(onCheckedChangeListener);
        ((RadioButton) rgTabButtons.getChildAt(0)).setChecked(true);
        showBtn = (ImageView) findViewById(R.id.show);
        showBtn.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            mCurrentFragment = arg0;
            ((RadioButton) rgTabButtons.getChildAt(arg0)).setChecked(true);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int checkedItem = 0;
            switch (checkedId) {
                case R.id.selected_photo:
                    checkedItem = 0;
                    break;
                case R.id.add_text:
                    checkedItem = 1;
                    break;
                case R.id.selected_music:
                    checkedItem = 2;
                    break;
            }
            vpContainer.setCurrentItem(checkedItem);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                show();
                break;
        }
    }

    private void show() {
        progressBar.setVisibility(View.VISIBLE);
        final AddTextFragment fragment = (AddTextFragment) adapter.getItem(1);
        List<String> list = fragment.getContentText();
        Log.i(TAG, "show:");

        chooseMusicFragment = (ChooseMusicFragment) adapter.getItem(2);
        playMode = chooseMusicFragment.getSelectedMode();

        new Thread(new Runnable() {
            @Override
            public void run() {
                PhotosFragment photosFragment = (PhotosFragment) adapter.getItem(0);
                mSelected = photosFragment.getList();
                Crop crop = new Crop(mSelected);
                switch (playMode) {
                    case "1":
                        filepath = crop.cropmr();
                        break;
                    case "2":
                        filepath = crop.cropshyf();
                        break;
//                    case "3":
//                        filepath = crop.cropfjrh();
//                    case "4":
//                        filepath = crop();
//                        break;
//                    case "5":
//                        filepath = crop();
//                        break;
//                    case "6":
//                        filepath = crop();
//                        break;
                    default:

                        break;
                }
                Log.i(TAG, mSelected.get(0));
                Log.i(TAG, filepath.get(0));

                loadFile = new ArrayList<File>();
                for (int j = 0; j < filepath.size(); j++) {
                    loadFile.add(CompressHelper.getDefault(getApplicationContext()).compressToFile(new File(mSelected.get(j))));
                }


                upLoad(loadFile);


            }
        }).start();


    }

    class KickerFragmentAdapter extends FragmentPagerAdapter {
        private Context mContext;

        private List<Fragment> list;

        public KickerFragmentAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
            list = new ArrayList<>();
            for (int i = 0; i < fragments.length; i++) {
                list.add(Fragment.instantiate(mContext, fragments[i]));
            }
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.length;
        }

    }


    private void upLoad(final List<File> mfile) {


        object = new AVObject("Photos");
        for (i = 0; i < mfile.size(); i++) {
            Log.i(TAG, "upLoad: ");

            try {
                file = AVFile.withFile(mfile.get(i).getName(), mfile.get(i));
                Log.i(TAG, "upLoad: ok2");
                photos.add(file);


                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
//                        if (i == mSelected.size()) {
//
//                            handler.post(upload);
//
//
//                        }
                        counter(mfile);

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private int count = 0;

    private String playMode;

    public synchronized void counter(List<File> mFile) {
        count++;


        if (count == mFile.size()) {
            Log.i("tdmee", "counter: 1");
            List<String> temp = Presenter.getInstance().getContentText();
            textArray = temp.toArray(new String[temp.size()]);
            Log.i("tdmee", "counter: 2");
            for (int j = 0; j < photos.size(); j++) {
                photosUrl.add(photos.get(j).getUrl().split("m/")[1]);
            }
            Log.i("tdmee", "counter: 3");


            Log.i("tdmee", "0");
            AVQuery<AVObject> query = new AVQuery<>("Music");
            query.whereEqualTo("playMode", getMusicName(playMode));
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    musicKey = list.get(0).getString("musicKey");
                    object.put("playMode", playMode);
                    object.put("Music", musicKey);
                    object.put("PhotosList", photosUrl);
                    object.put("TextList", textArray);
                    object.put("AlbumsName", chooseMusicFragment.getAlbumsName());
                    object.put("Loaction", chooseMusicFragment.getLocationString());
                    object.put("Cover", chooseMusicFragment.getCoverPath());
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                photosId = object.getObjectId();


                                Message message = new Message();
                                message.what = UPDATE_TEXT;
                                handler.sendMessage(message);
                                System.out.println(TAG + object.getObjectId());
                                System.out.println(TAG + photosId);
                            } else {
                                System.out.println(TAG + "1111111111111");
                            }
                        }
                    });
                }
            });


            Log.i("tdmee", "6");


        }
    }


    public String getMusicName(String s) {
        String musicName;
        switch (s) {
            case "1":
                musicName = "默认主题";
                break;
            case "2":
                musicName = "诗和远方";
                break;
            case "3":
                musicName = "风景如画";
                break;
            default:
                musicName = null;
        }
        return musicName;
    }
}

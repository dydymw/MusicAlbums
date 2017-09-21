package com.tdme.android.musicalbums;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2017/8/23.
 * getContext
 */

public class Myapplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"x0rWknk8MFeKytkW7etKkssL-gzGzoHsz","9pbhbgpwavexpxGnQuyVKw3m");
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}

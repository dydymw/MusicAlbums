package com.tdme.android.musicalbums.bean;

/**
 * Created by Administrator on 2017/8/27.
 * 添加字幕的item Bean
 */

public class Photos {
    private String text;
    private String img;

    public Photos(String img) {

        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}


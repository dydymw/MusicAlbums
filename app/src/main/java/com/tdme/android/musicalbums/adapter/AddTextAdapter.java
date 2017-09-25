package com.tdme.android.musicalbums.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tdme.android.musicalbums.Myapplication;
import com.tdme.android.musicalbums.R;
import com.tdme.android.musicalbums.bean.Photos;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27.
 *添加字幕的RecyclerView的Adapter
 */

public class AddTextAdapter extends RecyclerView.Adapter<AddTextAdapter.ViewHolder> {

    // 数据双向绑定
    private Presenter presenter;

    private List<Photos> mPhotos;

    public AddTextAdapter(List<Photos> mPhotos){
        this.mPhotos = mPhotos;
        presenter = Presenter.getInstance();
    }

  /*  public AddTextAdapter(List<Photos> mPhotos, Presenter presenter) {
        this.presenter = presenter;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_text_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        presenter.register(holder, mPhotos.get(position));

        Photos photos = mPhotos.get(position);

        Glide.with(Myapplication.getContext())
                .load(photos.getImg())
                .override(100,100)
                .centerCrop()
                .into(holder.photosImage);
        Log.i("tag", "onBindViewHolder: " + position);

        holder.editText.setText(mPhotos.get(position).getText());

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("输入过程中执行该方法", "文字变化"+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                presenter.modifyTextContent(holder, s.toString());
                Log.i("输入过程中执行该方法", position+"----------"+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("输入过程中执行该方法", "输入结束");
            }
        });
    }


    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photosImage;
        EditText editText;

        public ViewHolder(View itemView) {
            super(itemView);

            photosImage = (ImageView) itemView.findViewById(R.id.img);
            editText = (EditText) itemView.findViewById(R.id.text);

        }

        public String getContentText() {
            return editText.getText().toString();
        }
    }
}

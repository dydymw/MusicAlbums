package com.tdme.android.musicalbums.adapter;

import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdme.android.musicalbums.Myapplication;
import com.tdme.android.musicalbums.R;
import com.tdme.android.musicalbums.activity.MainActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 * 添加图片的Adapter
 */

public class PhotoNetAdapter extends RecyclerView.Adapter<PhotoNetAdapter.MyHolder> {

    public static final int TYPE_FOOTER = 0;
    public static final int TYPE_NORMAL = 1;

    private List<String> mDatas;
    private View mFooterView;

    private OnRecyClickerListener m;

    public interface OnRecyClickerListener{
        void click(View view,int position);
    }

    public void setOnRecyclerViewBottom(OnRecyClickerListener onRecyclerViewBottom){
        m = onRecyclerViewBottom;
    }

    public PhotoNetAdapter(List<String> list) {
        mDatas = list;
    }

    public void setmFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
    }

    public View getmFooterView() {
        return mFooterView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new MyHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof MyHolder) {
                Glide.with(Myapplication.getContext())
                        .load(mDatas.get(position))
                        .override(100,100)
                        .centerCrop()
                        .into(holder.iv);
                return;

            }
        }else {
            if (m != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.click(holder.itemView,holder.getLayoutPosition());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mFooterView == null) {
            return mDatas.size();
        } else {
            return mDatas.size() + 1;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView iv;

        public MyHolder(View itemView) {
            super(itemView);

            if (itemView == mFooterView) {
                return;
            }

            iv = (ImageView) itemView.findViewById(R.id.net_img);
        }
    }


}

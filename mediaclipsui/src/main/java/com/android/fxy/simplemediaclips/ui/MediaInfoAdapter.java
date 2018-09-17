package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

public abstract class MediaInfoAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    protected ItemSelectedListener mItemSelectedListener = new ItemSelectedListener() {
        @Override
        public void onItemSellectk(int position) {
        }
    };
    private final LayoutInflater mInflater;
    private List<T> datas;
    protected int mSellectPosition = -1;

    public MediaInfoAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemSelected(ItemSelectedListener listener) {
        this.mItemSelectedListener = listener;
    }

    public void sellect(int pisition) {
        mSellectPosition = pisition;
    }

    public interface ItemSelectedListener {
        void onItemSellectk(int position);
    }

    public void setData(List<T> items) {
        this.datas = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (datas != null) {
            return datas.size();

        } else return 0;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public List<T> getDatas() {
        return datas;
    }
}

package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.android.fxy.simplemediaclips.model.MediaInfo;

import java.util.List;

public abstract class MediaInfoAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected ItemSelectedListener mItemSelectedListener = new ItemSelectedListener() {
        @Override
        public void onItemSellectk(int position) {
        }
    };
    private final LayoutInflater mInflater;
    private List<MediaInfo> mediaInfos;
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

    public void setData(List<MediaInfo> items) {
        this.mediaInfos = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mediaInfos != null) {
            return mediaInfos.size();

        } else return 0;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public List<MediaInfo> getMediaInfos() {
        return mediaInfos;
    }
}

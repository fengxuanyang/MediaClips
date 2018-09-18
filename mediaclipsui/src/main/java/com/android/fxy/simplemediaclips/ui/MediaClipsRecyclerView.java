package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.List;

public class MediaClipsRecyclerView extends RecyclerView {
    private static final String TAG = "MediaClipsRecyclerView";
    private MediaInfoAdapter mediaAdapter;
    private int lastDataPosition = -1;
    private int mediaType;
    public static final int MEDIATYPE_IMAGE = -1;
    public static final int MEDIATYPE_VIDEO = -2;


    private CenterChangeListener mCenterChangeListener = new CenterChangeListener() {
        @Override
        public void onCenterChange(int position) {

        }
    };

    public void setCenterChangeListener(CenterChangeListener listener) {
        this.mCenterChangeListener = listener;
    }

    public void bindSource(List<String> sourceLise, int startIndex) {
        Log.d(TAG, "bindSource");
        mediaAdapter.setData(sourceLise, startIndex);
        smoothScrollToPosition(startIndex);
    }

    public MediaClipsRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.MediaClipsRecyclerView);
        mediaType = attr.getInt(R.styleable.MediaClipsRecyclerView_mediaType, MEDIATYPE_IMAGE);
        attr.recycle();
        initView(context);

    }

    public void seekTo(int position) {
        smoothScrollToPosition(position);
    }

    private void initView(Context context) {
        if (mediaType == MEDIATYPE_VIDEO) {
            mediaAdapter = new MediaInfoVideoAdapter(context);
        } else {
            mediaAdapter = new MediaInfoImgAdapter(context);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(layoutManager);
        CenterSnapHelper mCenterSnapHelper = new CenterSnapHelper();
        mCenterSnapHelper.setCenterChangeListener(new CenterSnapHelper.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
                if (lastDataPosition != position) {
                    mediaAdapter.sellect(position);
                    mCenterChangeListener.onCenterChange(position);
                }
                lastDataPosition = position;
            }
        });
        this.setAdapter(mediaAdapter);
        mCenterSnapHelper.attachToRecyclerView(this);
    }

    public interface CenterChangeListener {
        void onCenterChange(int position);
    }

}

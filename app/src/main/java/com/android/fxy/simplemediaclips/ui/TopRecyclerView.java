package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.android.fxy.simplemediaclips.commom.LogUtils;

import java.util.List;

public class TopRecyclerView extends RecyclerView {
    private static final String TAG = "TopRecyclerView";
    private MediaInfoVideoAdapter videoAdapter;
    private int dataSize = -1;
    private int lastDataPosition = -1;


    private CenterChangeListener mCenterChangeListener = new CenterChangeListener() {
        @Override
        public void onCenterChange(int position) {

        }
    };

    public void setCenterChangeListener(CenterChangeListener listener) {
        this.mCenterChangeListener = listener;
    }


    public TopRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public void bindSource(List<String> sourceLise) {
        LogUtils.d(TAG, "bindSource" + sourceLise.size());
        videoAdapter.setData(sourceLise);
        dataSize = sourceLise.size();
    }

    public void seekTo(int position) {
        smoothScrollToPosition(position);
    }

    private void initView(Context context) {
        LogUtils.d(TAG, "initView");
        videoAdapter = new MediaInfoVideoAdapter(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(layoutManager);
        this.setAdapter(videoAdapter);
        CenterSnapHelper mCenterSnapHelper = new CenterSnapHelper();

        mCenterSnapHelper.setCenterChangeListener(new CenterSnapHelper.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
                if (lastDataPosition != position) {
                    mCenterChangeListener.onCenterChange(position);
                }
                lastDataPosition = position;
            }
        });
        mCenterSnapHelper.attachToRecyclerView(this);
    }

    public interface CenterChangeListener {
        void onCenterChange(int position);
    }

}

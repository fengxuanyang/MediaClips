package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.android.fxy.simplemediaclips.commom.LogUtils;

import java.util.List;

public class BottomRecyclerView extends RecyclerView {
    private static final String TAG = "BottomRecyclerView";
    private MediaInfoImgAdapter imgAdapter;
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


    public BottomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public void bindSource(List<String> sourceLise) {
        LogUtils.d(TAG, "bindSource" + sourceLise.size());

        imgAdapter.setData(sourceLise);
        dataSize = sourceLise.size();
        smoothScrollToPosition(MediaInfoImgAdapter.START_VACANCY);
    }

    public void seekTo(int position) {
        seekToDataPosition(position);
    }

    private void initView(Context context) {
        LogUtils.d(TAG, "initView");
        imgAdapter = new MediaInfoImgAdapter(context);
        LinearLayoutManager layoutManagerBottom = new LinearLayoutManager(context);
        layoutManagerBottom.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(layoutManagerBottom);
        this.setAdapter(imgAdapter);
        CenterSnapHelper mBottomCenterSnapHelper = new CenterSnapHelper();

        mBottomCenterSnapHelper.setCenterChangeListener(new CenterSnapHelper.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
                LogUtils.d(TAG, "onCenterChange  start :" + position);

                imgAdapter.sellect(position);
                position = position - MediaInfoImgAdapter.START_VACANCY;
                if (position <= 0) {
                    smoothScrollToPosition(MediaInfoImgAdapter.START_VACANCY);
                    position = 0;
                } else if (position > dataSize) {
                    smoothScrollToPosition(dataSize + MediaInfoImgAdapter.START_VACANCY - 1);
                    position = dataSize + MediaInfoImgAdapter.START_VACANCY - 1;
                }
                LogUtils.d(TAG, "onCenterChange end :" + position);

                if (lastDataPosition != position) {
                    mCenterChangeListener.onCenterChange(position);
                    lastDataPosition = position;

                }
            }
        });
        mBottomCenterSnapHelper.attachToRecyclerView(this);
    }


    private void seekToDataPosition(int index) {
        LogUtils.d(TAG, "getDataPosition index：" + index);
        smoothScrollToPosition(MediaInfoImgAdapter.START_VACANCY + index);


    }


    public interface CenterChangeListener {
        void onCenterChange(int position);
    }

}

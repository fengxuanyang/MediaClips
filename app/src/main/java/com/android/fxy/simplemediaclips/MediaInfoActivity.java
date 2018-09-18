package com.android.fxy.simplemediaclips;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.fxy.simplemediaclips.commom.LogUtils;
import com.android.fxy.simplemediaclips.model.MediaInfo;
import com.android.fxy.simplemediaclips.ui.MediaClipsRecyclerView;
import com.android.fxy.simplemediaclips.viewmodels.MediaInfoViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaInfoActivity extends AppCompatActivity {
    private static final String TAG = "MediaInfoActivity";

    @BindView(R.id.recyclerview_top)
    MediaClipsRecyclerView topRecyclerview;
    @BindView(R.id.recyclerview_bottom)
    MediaClipsRecyclerView bottomRecyclerview;

    private MediaInfoViewModel mMediaInfoViewModel;
    private LiveData<List<MediaInfo>> mediaInfObserver;


    public static final int STATE_TOP_SNAP = 1;
    public static final int STATE_BOTTOM_SNAP = 2;
    public static final int STATE_SNAP_ALL = STATE_TOP_SNAP | STATE_BOTTOM_SNAP;
    private int snapState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initModules();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initModules() {
        mMediaInfoViewModel = ViewModelProviders.of(this).get(MediaInfoViewModel.class);
        mediaInfObserver = mMediaInfoViewModel.getAllMediaInfo();
        mediaInfObserver.observe(this, new Observer<List<MediaInfo>>() {
            @Override
            public void onChanged(@Nullable final List<MediaInfo> datas) {
                ArrayList<String> imagsUrls = new ArrayList<>();
                List<String> videoUrls = new ArrayList<>();
                for (MediaInfo info : datas) {
                    imagsUrls.add(info.getImageUrl());
                    videoUrls.add(info.getVideoUrl());
                }
                bottomRecyclerview.bindSource(imagsUrls, 0);
                topRecyclerview.bindSource(videoUrls, 0);
            }
        });

        //TODO for video play
        mMediaInfoViewModel.getCurrentMediaInfo().observe(this, new Observer<MediaInfo>() {
            @Override
            public void onChanged(@Nullable MediaInfo mediaInfo) {
                LogUtils.d(TAG, "MediaInfo onChanged:" + mediaInfo.toString());

            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_mediainfo);
        ButterKnife.bind(this);
        topRecyclerview.setCenterChangeListener(new MediaClipsRecyclerView.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
                mMediaInfoViewModel.getCurrentMediaInfo().setValue(mediaInfObserver.getValue().get(position));
                snapState = snapState | STATE_TOP_SNAP;
                if (!((snapState & STATE_SNAP_ALL) == STATE_BOTTOM_SNAP)) {
                    bottomRecyclerview.seekTo(position);
                    return;
                }
                snapState = 0;
            }
        });
        bottomRecyclerview.setCenterChangeListener(new MediaClipsRecyclerView.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
                snapState = snapState | STATE_BOTTOM_SNAP;
                if (!((snapState & STATE_SNAP_ALL) == STATE_TOP_SNAP)) {
                    topRecyclerview.seekTo(position);
                    return;
                }
                snapState = 0;
            }
        });

    }


}

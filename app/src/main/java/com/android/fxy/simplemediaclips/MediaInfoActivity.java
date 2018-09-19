package com.android.fxy.simplemediaclips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.fxy.simplemediaclips.data.model.MediaInfo;
import com.android.fxy.simplemediaclips.ui.MediaClipsRecyclerView;

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


    private MediaClipsDataSync mMediaClipsDataSync;

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
        mMediaClipsDataSync = MediaClipsDataSync.getInstance();
        mMediaClipsDataSync.watchAllMediaInfo(this, new MediaClipsDataSync.DataChangeListener() {
            @Override
            public void OnDataGain(List<MediaInfo> datas) {
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

    }

    private void initView() {
        setContentView(R.layout.activity_mediainfo);
        ButterKnife.bind(this);
        topRecyclerview.setCenterChangeListener(new MediaClipsRecyclerView.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
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

package com.android.fxy.simplemediaclips;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.fxy.simplemediaclips.commom.LogUtils;
import com.android.fxy.simplemediaclips.model.MediaInfo;
import com.android.fxy.simplemediaclips.ui.CenterSnapHelper;
import com.android.fxy.simplemediaclips.ui.MediaInfoImgAdapter;
import com.android.fxy.simplemediaclips.ui.MediaInfoVideoAdapter;
import com.android.fxy.simplemediaclips.viewmodels.MediaInfoViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaInfoActivity extends AppCompatActivity {
    private static final String TAG = "MediaInfoActivity";

    @BindView(R.id.recyclerview_top)
    RecyclerView topRecyclerview;
    @BindView(R.id.recyclerview_bottom)
    RecyclerView bottomRecyclerview;

    private MediaInfoViewModel mMediaInfoViewModel;
    private MediaInfoVideoAdapter videoAdapter;
    private MediaInfoImgAdapter imgAdapter;
    private int itemCount;
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

    private void initModules() {
        mMediaInfoViewModel = ViewModelProviders.of(this).get(MediaInfoViewModel.class);
        LiveData<List<MediaInfo>> mediaInfObserver = mMediaInfoViewModel.getAllMediaInfo();
        mediaInfObserver.observe(this, new Observer<List<MediaInfo>>() {
            @Override
            public void onChanged(@Nullable final List<MediaInfo> datas) {
                videoAdapter.setData(datas);
                imgAdapter.setData(datas);
                itemCount = datas.size();
                bottomRecyclerview.smoothScrollToPosition(MediaInfoImgAdapter.START_VACANCY);
            }
        });

        //TODO for video play
        mMediaInfoViewModel.getCurrentMediaInfo().observe(this, new Observer<MediaInfo>() {
            @Override
            public void onChanged(@Nullable MediaInfo mediaInfo) {

            }
        });

    }


    private void initView() {
        setContentView(R.layout.activity_mediainfo);
        ButterKnife.bind(this);
        videoAdapter = new MediaInfoVideoAdapter(this);
        LinearLayoutManager layoutManagerTop = new LinearLayoutManager(this);
        layoutManagerTop.setOrientation(LinearLayoutManager.HORIZONTAL);
        topRecyclerview.setLayoutManager(layoutManagerTop);
        topRecyclerview.setAdapter(videoAdapter);

        imgAdapter = new MediaInfoImgAdapter(this);
        LinearLayoutManager layoutManagerBottom = new LinearLayoutManager(this);
        layoutManagerBottom.setOrientation(LinearLayoutManager.HORIZONTAL);
        bottomRecyclerview.setLayoutManager(layoutManagerBottom);
        bottomRecyclerview.setAdapter(imgAdapter);

        ((DefaultItemAnimator) bottomRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        CenterSnapHelper mTopCenterSnapHelper = new CenterSnapHelper();
        mTopCenterSnapHelper.setCenterChangeListener(new CenterSnapHelper.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
                videoAdapter.sellect(position);
                snapState = snapState | STATE_TOP_SNAP;
                if (!((snapState & STATE_SNAP_ALL) == STATE_BOTTOM_SNAP)) {
                    bottomRecyclerview.smoothScrollToPosition(position + MediaInfoImgAdapter.START_VACANCY);
                    return;
                }
                snapState = 0;
            }
        });
        mTopCenterSnapHelper.attachToRecyclerView(topRecyclerview);
        CenterSnapHelper mBottomCenterSnapHelper = new CenterSnapHelper();
        mBottomCenterSnapHelper.setCenterChangeListener(new CenterSnapHelper.CenterChangeListener() {
            @Override
            public void onCenterChange(int position) {
                imgAdapter.sellect(position);
                if (position < MediaInfoImgAdapter.START_VACANCY) {
                    bottomRecyclerview.smoothScrollToPosition(MediaInfoImgAdapter.START_VACANCY);
                    return;
                }
                if (position >= itemCount + MediaInfoImgAdapter.START_VACANCY) {
                    bottomRecyclerview.smoothScrollToPosition(itemCount + MediaInfoImgAdapter.START_VACANCY - 1);
                    return;
                }
                snapState = snapState | STATE_BOTTOM_SNAP;
                if (!((snapState & STATE_SNAP_ALL) == STATE_TOP_SNAP)) {
                    topRecyclerview.smoothScrollToPosition(position - MediaInfoImgAdapter.START_VACANCY);
                    return;
                }
                snapState = 0;
            }
        });
        mBottomCenterSnapHelper.attachToRecyclerView(bottomRecyclerview);
    }
}

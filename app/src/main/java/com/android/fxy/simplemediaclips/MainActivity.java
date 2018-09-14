package com.android.fxy.simplemediaclips;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.fxy.simplemediaclips.commom.LogUtils;
import com.android.fxy.simplemediaclips.model.MediaInfo;
import com.android.fxy.simplemediaclips.ui.MediaInfoAdapter;
import com.android.fxy.simplemediaclips.viewmodels.MediaInfoViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_top)
    RecyclerView topRecyclerview;

    private MediaInfoViewModel mMediaInfoViewModel;
    private MediaInfoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initModels();
    }

    private void initModels() {
         mMediaInfoViewModel = ViewModelProviders.of(this).get(MediaInfoViewModel.class);
        LiveData<List<MediaInfo>> mediaInfObserver = mMediaInfoViewModel.getAllMediaInfo();
        mediaInfObserver.observe(this, new Observer<List<MediaInfo>>() {
            @Override
            public void onChanged(@Nullable final List<MediaInfo> datas) {
                LogUtils.d("onChanged");
                videoAdapter.setData(datas);
            }
        });
        mMediaInfoViewModel.deleteAll();
     }

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        videoAdapter = new MediaInfoAdapter(this);
        topRecyclerview.setAdapter(videoAdapter);
        topRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}

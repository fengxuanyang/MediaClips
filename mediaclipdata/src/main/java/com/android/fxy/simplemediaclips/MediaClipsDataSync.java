package com.android.fxy.simplemediaclips;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.android.fxy.simplemediaclips.data.model.MediaInfo;
import com.android.fxy.simplemediaclips.data.viewmodels.MediaInfoViewModel;

import java.util.List;

public class MediaClipsDataSync {
    private static MediaClipsDataSync INSTANCE;

    private MediaClipsDataSync() {

    }

    public static MediaClipsDataSync getInstance() {
        if (INSTANCE == null) {
            synchronized (MediaClipsDataSync.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MediaClipsDataSync();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * TODO separate FragmentActivity from MediaClipsDataSync
     * MediaInfoViewModel is bind to Lifecycle of app
     *
     * @param app
     * @param listener
     */
    public void watchAllMediaInfo(FragmentActivity app, final DataChangeListener listener) {
        MediaInfoViewModel mMediaInfoViewModel = ViewModelProviders.of(app).get(MediaInfoViewModel.class);
        LiveData<List<MediaInfo>> mediaInfObserver = mMediaInfoViewModel.getAllMediaInfo();
        mediaInfObserver.observe(app, new Observer<List<MediaInfo>>() {
            @Override
            public void onChanged(@Nullable final List<MediaInfo> datas) {
                listener.OnDataGain(datas);
            }
        });
    }


    public interface DataChangeListener {

        void OnDataGain(List<MediaInfo> datas);
    }
}
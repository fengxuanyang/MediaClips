package com.android.fxy.simplemediaclips.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.android.fxy.simplemediaclips.model.MediaInfo;
import com.android.fxy.simplemediaclips.repository.MediaInfoRepository;

import java.util.List;


public class MediaInfoViewModel extends AndroidViewModel {

    private MediaInfoRepository mediaInfoRepository;
    private MutableLiveData<MediaInfo> currentMediaInfo;


    public MediaInfoViewModel(@NonNull Application application) {
        super(application);
        mediaInfoRepository = new MediaInfoRepository(application);
    }

    public LiveData<List<MediaInfo>> getAllMediaInfo() {
        return mediaInfoRepository.getAllMediaInfo();
    }

    public void insert(MediaInfo mediainfo) {
        mediaInfoRepository.insert(mediainfo);
    }

    public void deleteAll() {
        mediaInfoRepository.deleteAll();
    }

    public MutableLiveData<MediaInfo> getCurrentMediaInfo() {
        if (currentMediaInfo == null) {
            currentMediaInfo = new MutableLiveData<>();
        }
        return currentMediaInfo;
    }

}
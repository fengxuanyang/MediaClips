package com.android.fxy.simplemediaclips.data.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.android.fxy.simplemediaclips.data.model.MediaInfo;
import com.android.fxy.simplemediaclips.data.repository.MediaInfoRepository;

import java.util.List;


/**
 * MediaInfoDatabase.getDatabase(application) for Room ,  need the application
 * it necessary to put  Room init in the application ?
 */
public class MediaInfoViewModel extends AndroidViewModel {

    private MediaInfoRepository mediaInfoRepository;
    private MutableLiveData<MediaInfo> currentMediaInfo;

    public MediaInfoViewModel(@NonNull Application application) {
        super(application);
        mediaInfoRepository = MediaInfoRepository.getInstance(application);
    }

    public LiveData<List<MediaInfo>> getAllMediaInfo() {
        return mediaInfoRepository.getAllMediaInfo();
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
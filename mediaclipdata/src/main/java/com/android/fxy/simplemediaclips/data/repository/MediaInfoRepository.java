package com.android.fxy.simplemediaclips.data.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;


import com.android.fxy.simplemediaclips.data.commom.LogUtils;
import com.android.fxy.simplemediaclips.data.db.MediaInfoDao;
import com.android.fxy.simplemediaclips.data.db.MediaInfoDatabase;
import com.android.fxy.simplemediaclips.data.model.MediaInfo;
import com.android.fxy.simplemediaclips.data.web.HttpRetrofitManager;
import com.android.fxy.simplemediaclips.data.web.MediainfoRequestApi;
import com.android.fxy.simplemediaclips.data.web.MediainfoResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaInfoRepository {
    public static final String TAG = "MediaInfoRepository";

    private MediaInfoDao meidaInfoDao;
    private MediainfoRequestApi getPicApi;
    private ExecutorService singleThreadPool;
    private LiveData<List<MediaInfo>> mLiveData;


    public MediaInfoRepository(Application application) {
        singleThreadPool = Executors.newSingleThreadExecutor();
        getPicApi = HttpRetrofitManager.getInstance().creatHttpApi(MediainfoRequestApi.class);
        meidaInfoDao = MediaInfoDatabase.getDatabase(application).mediaInfoDao();
    }


    private void getWebData() {
        Call<List<MediainfoResponse.Mediainfo>> call = getPicApi.pictures();
        call.enqueue(new Callback<List<MediainfoResponse.Mediainfo>>() {
            @Override
            public void onResponse(Call<List<MediainfoResponse.Mediainfo>> call, Response<List<MediainfoResponse.Mediainfo>> response) {
                if (response.isSuccessful()) {
                    List<MediainfoResponse.Mediainfo> mSubmitResponse = response.body();
                    MediaInfo[] infos = new MediaInfo[mSubmitResponse.size()];
                    for (int i = 0; i < mSubmitResponse.size(); i++) {
                        MediainfoResponse.Mediainfo info = mSubmitResponse.get(i);
                        infos[i] = new MediaInfo(info.getId(), info.getImageUrl(), info.getVideoUrl());
                    }
                    insertAll(infos);
                }
            }

            //TODO  call back for ui
            @Override
            public void onFailure(Call<List<MediainfoResponse.Mediainfo>> call, Throwable t) {
                LogUtils.e("onFailure" + t.getMessage());

            }
        });
    }


    //TODO cache List<MediaInfo>
    public LiveData<List<MediaInfo>> getAllMediaInfo() {
        if (mLiveData == null) {
            getWebData();
            mLiveData = meidaInfoDao.getAllMediaInfo();
        }
        return mLiveData;
    }

    public void insertAll(final MediaInfo... mediainfos) {
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                meidaInfoDao.insertAll(mediainfos);
            }
        });
    }

    public void insert(final MediaInfo mediainfo) {
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                meidaInfoDao.insert(mediainfo);
            }
        });
    }

    public void deleteAll() {
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                meidaInfoDao.deleteAll();
            }
        });
    }
}

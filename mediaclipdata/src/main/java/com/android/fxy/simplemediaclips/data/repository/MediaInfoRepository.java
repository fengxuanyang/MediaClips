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

    private YunSouceHandle mYunSouceHandle;
    private LocalSouceHandle mLocalSouceHandle;
    private static MediaInfoRepository mMediaInfoRepository;

    private MediaInfoRepository(Application application) {
        mYunSouceHandle = new YunSouceHandle();
        mLocalSouceHandle = new LocalSouceHandle(application);
    }

    public static MediaInfoRepository getInstance(Application application) {
        if (mMediaInfoRepository == null) {
            synchronized (MediaInfoRepository.class) {
                if (mMediaInfoRepository == null) {
                    mMediaInfoRepository = new MediaInfoRepository(application);
                }
            }
        }
        return mMediaInfoRepository;
    }

    //TODO cache List<MediaInfo>
    public LiveData<List<MediaInfo>> getAllMediaInfo() {
        LiveData<List<MediaInfo>> livedata = mLocalSouceHandle.getAllMediaInfo();
        if (livedata == null) {
            mYunSouceHandle.getWebData();
        }
        return livedata;
    }

    public void deleteAll() {
        mLocalSouceHandle.deleteAll();
    }

    private class LocalSouceHandle {
        private MediaInfoDao meidaInfoDao;
        private LiveData<List<MediaInfo>> mLiveData;
        private ExecutorService singleThreadPool;

        private LocalSouceHandle(Application application) {
            meidaInfoDao = MediaInfoDatabase.getDatabase(application).mediaInfoDao();
            singleThreadPool = Executors.newSingleThreadExecutor();
        }

        //TODO cache List<MediaInfo>
        private LiveData<List<MediaInfo>> getAllMediaInfo() {
            if (mLiveData == null) {
                mLiveData = meidaInfoDao.getAllMediaInfo();
            }
            return mLiveData;
        }

        private void insert(final MediaInfo mediainfo) {
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    meidaInfoDao.insert(mediainfo);
                }
            });
        }

        private void deleteAll() {
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    meidaInfoDao.deleteAll();
                }
            });
        }

        private void insertAll(final MediaInfo... mediainfos) {
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    meidaInfoDao.insertAll(mediainfos);
                }
            });
        }
    }

    private class YunSouceHandle {
        private MediainfoRequestApi getPicApi;

        YunSouceHandle() {
            getPicApi = HttpRetrofitManager.getInstance().creatHttpApi(MediainfoRequestApi.class);
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
                        mLocalSouceHandle.insertAll(infos);
                    }
                }

                //TODO  call back for ui
                @Override
                public void onFailure(Call<List<MediainfoResponse.Mediainfo>> call, Throwable t) {
                    LogUtils.e("onFailure" + t.getMessage());
                }
            });
        }
    }

}

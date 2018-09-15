package com.android.fxy.simplemediaclips.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.android.fxy.simplemediaclips.commom.LogUtils;
import com.android.fxy.simplemediaclips.model.MediaInfo;
import com.android.fxy.simplemediaclips.db.MediaInfoDao;
import com.android.fxy.simplemediaclips.db.MediaInfoDatabase;
import com.android.fxy.simplemediaclips.web.HttpRetrofitManager;
import com.android.fxy.simplemediaclips.web.MediainfoRequestApi;
import com.android.fxy.simplemediaclips.web.MediainfoResponse;

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

    public MediaInfoRepository(Application application) {
        singleThreadPool = Executors.newSingleThreadExecutor();
        getPicApi = HttpRetrofitManager.getInstance().creatHttpApi(MediainfoRequestApi.class);
        meidaInfoDao = MediaInfoDatabase.getDatabase(application).mediaInfoDao();
        init();
    }

    private void init() {
        Call<List<MediainfoResponse.Mediainfo>> call = getPicApi.pictures();
        call.enqueue(new Callback<List<MediainfoResponse.Mediainfo>>() {
            @Override
            public void onResponse(Call<List<MediainfoResponse.Mediainfo>> call, Response<List<MediainfoResponse.Mediainfo>> response) {
                if (response.isSuccessful()) {
                    List<MediainfoResponse.Mediainfo> mSubmitResponse = response.body();
                    for (MediainfoResponse.Mediainfo info : mSubmitResponse) {
                        LogUtils.d("submit success msg:" + info.toString());
                        insert(new MediaInfo(info.getId(), info.getImageUrl(), info.getVideoUrl()));
                    }
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
        LogUtils.d(TAG, "getAllMediaInfo");
        return meidaInfoDao.getAllMediaInfo();
    }

    public void insert(final MediaInfo mediainfo) {
        LogUtils.d(TAG, "insert");
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                meidaInfoDao.insert(mediainfo);
            }
        });
    }

    public void deleteAll() {
        LogUtils.d(TAG, "deleteAll");
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                meidaInfoDao.deleteAll();
            }
        });
    }
}

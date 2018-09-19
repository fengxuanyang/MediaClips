package com.android.fxy.simplemediaclips.data.web;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 */
public class HttpRetrofitManager {
    private static HttpRetrofitManager mAudioCenterHttpManager;
    private static boolean DEBUG = true;
    private Retrofit retrofitAudio;
    private String BASE_URL = "http://private-04a55-videoplayer1.apiary-mock.com";
    private static final String TAG = "HttpRetrofitManager";


    private HttpRetrofitManager() {
    }

    public static final HttpRetrofitManager getInstance() {
        if (mAudioCenterHttpManager == null) {
            synchronized (HttpRetrofitManager.class) {
                if (mAudioCenterHttpManager == null) {
                    mAudioCenterHttpManager = new HttpRetrofitManager();
                }
            }
        }
        return mAudioCenterHttpManager;
    }


    public void changeBaseUrl(String url) {
        BASE_URL = url;
     }

    public <T> T creatHttpApi(Class<T> httpApiClass) {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor());
        OkHttpClient okHttpClient = mBuilder.build();
        Gson gson = new Gson();

        retrofitAudio = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofitAudio.create(httpApiClass);
    }

    private static HttpLoggingInterceptor getLoggingInterceptor() {
        if (DEBUG) {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
    }

}

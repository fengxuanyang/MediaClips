package com.android.fxy.simplemediaclips.data.web;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *  http://private-04a55-videoplayer1.apiary-mock.com/pictures
 */
public interface MediainfoRequestApi {
    @GET("pictures")
    Call<List<MediainfoResponse.Mediainfo>> pictures();
}

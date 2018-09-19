package com.android.fxy.simplemediaclips.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * {   "id":1,
 * "imageUrl": "https://wpclipart.com/education/animal_numbers/animal_number_1.jpg",
 * "videoUrl": "https://media.giphy.com/media/l0ExncehJzexFpRHq/giphy.mp4"
 * },
 */

@Entity(tableName = "media_info")
public class MediaInfo {
    @PrimaryKey
    @NonNull
    private int id;
    private String imageUrl;
    private String videoUrl;

    public MediaInfo(@NonNull int id, String imageUrl, String videoUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {

        return "id:" + id + ",imageUrl:" + imageUrl + ",videoUrl:" + videoUrl;
    }
}


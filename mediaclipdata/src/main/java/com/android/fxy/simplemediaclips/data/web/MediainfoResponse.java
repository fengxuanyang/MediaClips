package com.android.fxy.simplemediaclips.data.web;


public class MediainfoResponse {


    /**
     * nothing but the   Mediainfo[]
     */
    public class Mediainfo {
        private int id;
        private String imageUrl;
        private String videoUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
            return "id:" + id + ",videoUrl:" + videoUrl + ",imageUrl:" + imageUrl;
        }
    }

}

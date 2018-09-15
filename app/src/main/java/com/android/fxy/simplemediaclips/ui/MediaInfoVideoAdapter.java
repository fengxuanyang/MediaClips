package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.android.fxy.simplemediaclips.R;
import com.android.fxy.simplemediaclips.commom.LogUtils;
import com.android.fxy.simplemediaclips.model.MediaInfo;


public class MediaInfoVideoAdapter extends MediaInfoAdapter<MediaInfoVideoAdapter.MediaInfoVideoHolder> {


    public MediaInfoVideoAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public MediaInfoVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getInflater().inflate(R.layout.recyclerview_item_top, parent, false);
        return new MediaInfoVideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaInfoVideoHolder holder, int position) {
        LogUtils.d("MediaInfoVideoAdapter mediaInfos:" + getMediaInfos());
        if (getMediaInfos() != null) {
            MediaInfo current = getMediaInfos().get(position);
            Uri uri = Uri.parse(current.getVideoUrl());
            holder.videoView.setVideoURI(uri);
            holder.videoView.requestFocus();
            holder.videoView.start();
        }
    }


    /**
     * {   "id":1,
     * "imageUrl": "https://wpclipart.com/education/animal_numbers/animal_number_1.jpg",
     * "videoUrl": "https://media.giphy.com/media/l0ExncehJzexFpRHq/giphy.mp4"
     * },
     */
    class MediaInfoVideoHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;

        private MediaInfoVideoHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoview_top);
        }
    }
}

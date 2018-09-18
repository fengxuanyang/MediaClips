package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;


public class MediaInfoVideoAdapter extends MediaInfoAdapter<MediaInfoVideoAdapter.MediaInfoVideoHolder, String> {


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
    public void sellect(int pisition) {
        super.sellect(pisition);
        notifyItemChanged(pisition);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaInfoVideoHolder holder, int position) {
        if (getDatas() != null) {
            Uri uri = Uri.parse(getDatas().get(position));
            holder.videoView.setVideoURI(uri);
            holder.videoView.setOnPreparedListener(mPlayer);
        }
    }

    private MediaPlayer.OnPreparedListener mPlayer = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            mp.setLooping(true);
        }
    };

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

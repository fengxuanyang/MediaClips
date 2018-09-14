package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fxy.simplemediaclips.R;
import com.android.fxy.simplemediaclips.commom.LogUtils;
import com.android.fxy.simplemediaclips.model.MediaInfo;

import java.util.List;

public class MediaInfoAdapter extends RecyclerView.Adapter<MediaInfoAdapter.MediaInfoViewHolder> {

    private final LayoutInflater mInflater;
    private List<MediaInfo> mediaInfos;

    public MediaInfoAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MediaInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_top, parent, false);
        return new MediaInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaInfoViewHolder holder, int position) {
        LogUtils.d("onBindViewHolder mediaInfos:" + mediaInfos);
        if (mediaInfos != null) {
            MediaInfo current = mediaInfos.get(position);
         } else {
         }
    }

    public void setData(List<MediaInfo> items) {
        this.mediaInfos = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mediaInfos != null)
            return mediaInfos.size();
        else return 0;
    }

    /**
     * {   "id":1,
     * "imageUrl": "https://wpclipart.com/education/animal_numbers/animal_number_1.jpg",
     * "videoUrl": "https://media.giphy.com/media/l0ExncehJzexFpRHq/giphy.mp4"
     * },
     */
    class MediaInfoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mediaVideoImg;

        private MediaInfoViewHolder(View itemView) {
            super(itemView);
            mediaVideoImg = itemView.findViewById(R.id.imageview_top);
        }
    }
}

package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.fxy.simplemediaclips.R;
import com.android.fxy.simplemediaclips.model.MediaInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class MediaInfoImgAdapter extends MediaInfoAdapter<MediaInfoImgAdapter.MediaInfoImgHolder> {
    //TODO
    public static final int START_VACANCY = 1;
    public static final int END_VACANCY = 1;
    public static final int MAX_IN_SCREEN = 1;
    public static final float SCAL_VALUE = 1.2f;
    private int parentWidth;
    private int parentHeight;

    public MediaInfoImgAdapter(Context context) {
        super(context);
    }

    @Override
    public void sellect(int pisition) {
        notifyItemChanged(mSellectPosition);
        notifyItemChanged(pisition);
        super.sellect(pisition);
    }

    @NonNull
    @Override
    public MediaInfoImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getInflater().inflate(R.layout.recyclerview_item_bottom, parent, false);
        parentWidth = parent.getWidth();
        parentHeight = parent.getHeight();
        return new MediaInfoImgHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaInfoImgHolder holder, int position) {
        if (getMediaInfos() != null) {
            if (position < START_VACANCY || position > getMediaInfos().size()) {
                /**
                 *  switch item  Visibility
                 */
                holder.imageViewMediaInfo.setVisibility(View.INVISIBLE);
            } else {
                holder.imageViewMediaInfo.setVisibility(View.VISIBLE);
                MediaInfo current = getMediaInfos().get(position - START_VACANCY);
                Picasso.get()
                        .load(current.getImageUrl())
                        .fit()
                        .transform(new ScaleTransformation())
                        .into(holder.imageViewMediaInfo);
                if (position == mSellectPosition) {
                    holder.imageViewMediaInfo.scaleUp();
                } else {
                    holder.imageViewMediaInfo.scaleDown();
                }
            }
        }
    }


    public class ScaleTransformation implements Transformation {


        @Override
        public Bitmap transform(Bitmap source) {
            int targetHeight = parentHeight;
            //TODO source.getWidth()  is bigger than  source.getHeight()  ?
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetWidth = (int) (targetHeight * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "ScaleTransformation";
        }
    }


    @Override
    public int getItemCount() {
        return super.getItemCount() + START_VACANCY + END_VACANCY;
    }

    /**
     * {
     * "id":1,
     * "imageUrl": "https://wpclipart.com/education/animal_numbers/animal_number_1.jpg",
     * "videoUrl": "https://media.giphy.com/media/l0ExncehJzexFpRHq/giphy.mp4"
     * },
     */
    class MediaInfoImgHolder extends RecyclerView.ViewHolder {
        public ScaleImageView imageViewMediaInfo;

        private MediaInfoImgHolder(View itemView) {
            super(itemView);
            imageViewMediaInfo = itemView.findViewById(R.id.imageview_bottom);
            imageViewMediaInfo.getLayoutParams().width = parentWidth / (MAX_IN_SCREEN + 1);
            int padding = parentWidth / ((MAX_IN_SCREEN + 1) * 4);
            imageViewMediaInfo.setPadding(padding, 0, padding, 0);
        }
    }
}

package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class MediaInfoImgAdapter extends MediaInfoAdapter<MediaInfoImgAdapter.MediaInfoImgHolder, String> {
    private static final String TAG = "MediaInfoImgAdapter";

    public static final int MAX_CONTEIN_IN_SCREEN = 2;
    private int parentHeight;
    private int itemWidth;
    private ViewGroup.MarginLayoutParams marginParams = null;

    public MediaInfoImgAdapter(Context context) {
        super(context);
    }

    @Override
    public void sellect(int pisition) {
//        notifyItemChanged(mSellectPosition);
        //        notifyItemChanged(mSellectPosition);
        super.sellect(pisition);
        notifyDataSetChanged();
//                notifyItemChanged(mSellectPosition);


    }

    @NonNull
    @Override
    public MediaInfoImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getInflater().inflate(R.layout.recyclerview_item_bottom, parent, false);
        itemWidth = parent.getWidth() / MAX_CONTEIN_IN_SCREEN;
        parentHeight = parent.getHeight();
        return new MediaInfoImgHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaInfoImgHolder holder, int position) {
        if (getDatas() != null) {
            if (position < START_VACANCY || position > getDatas().size()) {
                /**
                 *  switch item  Visibility
                 */
                holder.imageViewMediaInfo.setVisibility(View.INVISIBLE);
            } else {
                holder.imageViewMediaInfo.setVisibility(View.VISIBLE);
                String uri = getDatas().get(position - START_VACANCY);
                Picasso.get()
                        .load(uri)
                        .fit()
                        .transform(new ScaleTransformation())
                        .into(holder.imageViewMediaInfo);
                marginParams = (ViewGroup.MarginLayoutParams) holder.imageViewMediaInfo.getLayoutParams();
                marginParams.setMargins((position == 0) ? itemWidth / 2 : 0, 0, (position == getItemCount() - 1) ? itemWidth / 2 : 0, 0);
                holder.imageViewMediaInfo.setLayoutParams(marginParams);
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
            //TODO source.getWidth()  is bigger than  source.getHeight()  ?
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetWidth = (int) (parentHeight * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, parentHeight, false);
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
        ScaleImageView imageViewMediaInfo;

        private MediaInfoImgHolder(View itemView) {
            super(itemView);
            imageViewMediaInfo = itemView.findViewById(R.id.imageview_bottom);
            imageViewMediaInfo.getLayoutParams().width = itemWidth;
            int padding = itemWidth / 4;
            imageViewMediaInfo.setPadding(padding, 0, padding, 0);
        }
    }
}

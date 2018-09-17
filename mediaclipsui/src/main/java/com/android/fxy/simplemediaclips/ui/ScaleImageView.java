package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ScaleImageView extends AppCompatImageView {
    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void scaleUp() {
        ViewCompat.animate(this)
                .setDuration(200)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .start();
    }

    public void scaleDown() {
        ViewCompat.animate(this)
                .setDuration(200)
                .scaleX(1f)
                .scaleY(1f)
                .start();
    }
}

package com.android.fxy.simplemediaclips.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class FocuseImageView extends AppCompatImageView {
    public FocuseImageView(Context context) {
        super(context);
    }

    public FocuseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocuseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            scaleUp();
        } else {
            scaleDown();
        }
    }

    private void scaleUp() {
        ViewCompat.animate(this)
                .setDuration(200)
                .scaleX(1.08f)
                .scaleY(1.08f)
                .start();
    }

    private void scaleDown() {
        ViewCompat.animate(this)
                .setDuration(200)
                .scaleX(1f)
                .scaleY(1f)
                .start();
    }

}

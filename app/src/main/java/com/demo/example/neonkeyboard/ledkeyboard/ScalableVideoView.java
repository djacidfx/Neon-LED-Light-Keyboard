package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;


public class ScalableVideoView extends VideoView {
    private DisplayMode displayMode;
    private int mVideoHeight;
    private int mVideoWidth;

    
    public enum DisplayMode {
        ORIGINAL,
        FULL_SCREEN,
        ZOOM
    }

    public ScalableVideoView(Context context) {
        super(context);
        this.displayMode = DisplayMode.FULL_SCREEN;
    }

    public ScalableVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.displayMode = DisplayMode.FULL_SCREEN;
    }

    public ScalableVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.displayMode = DisplayMode.FULL_SCREEN;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }

    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        int i3;
        int defaultSize = getDefaultSize(0, widthMeasureSpec);
        int defaultSize2 = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
        if (this.displayMode == DisplayMode.ORIGINAL) {
            int i4 = this.mVideoWidth;
            if (i4 > 0 && (i3 = this.mVideoHeight) > 0) {
                if (i4 * defaultSize2 > defaultSize * i3) {
                    defaultSize2 = (i3 * defaultSize) / i4;
                } else if (i4 * defaultSize2 < defaultSize * i3) {
                    defaultSize = (i4 * defaultSize2) / i3;
                }
            }
        } else if (this.displayMode != DisplayMode.FULL_SCREEN && this.displayMode == DisplayMode.ZOOM && (i = this.mVideoWidth) > 0 && (i2 = this.mVideoHeight) > 0 && i < defaultSize) {
            defaultSize2 = (i2 * defaultSize) / i;
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    public void changeVideoSize(int width, int height) {
        this.mVideoWidth = width;
        this.mVideoHeight = height;
        getHolder().setFixedSize(width, height);
        requestLayout();
        invalidate();
    }

    public void setDisplayMode(DisplayMode mode) {
        this.displayMode = mode;
    }
}

package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;


public class RepeatListener implements View.OnTouchListener {
    public final View.OnClickListener mClickListener;
    public View mDownView;
    private int mInitialInterval;
    public final int mNormalInterval;
    private Runnable handlerRunnable = new Runnable() { 
        @Override 
        public void run() {
            if (RepeatListener.this.mDownView != null) {
                RepeatListener.this.mHandler.removeCallbacksAndMessages(RepeatListener.this.mDownView);
                RepeatListener.this.mHandler.postAtTime(this, RepeatListener.this.mDownView, SystemClock.uptimeMillis() + RepeatListener.this.mNormalInterval);
                RepeatListener.this.mClickListener.onClick(RepeatListener.this.mDownView);
            }
        }
    };
    public Handler mHandler = new Handler();

    public RepeatListener(int i, int i2, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            this.mInitialInterval = i;
            this.mNormalInterval = i2;
            this.mClickListener = onClickListener;
        }
    }

    @Override 
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownView = view;
            this.mHandler.removeCallbacks(this.handlerRunnable);
            this.mHandler.postAtTime(this.handlerRunnable, this.mDownView, SystemClock.uptimeMillis() + this.mInitialInterval);
            this.mClickListener.onClick(view);
            return true;
        } else if (action != 1 && action != 3 && action != 4) {
            return false;
        } else {
            this.mHandler.removeCallbacksAndMessages(this.mDownView);
            this.mDownView = null;
            return true;
        }
    }
}

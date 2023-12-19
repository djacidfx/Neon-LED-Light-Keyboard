package com.demo.example.neonkeyboard.ledkeyboard.MyGif.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;


public class GiphyImageView extends AppCompatImageView {
    public GiphyImageView(Context context) {
        super(context);
    }

    public GiphyImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GiphyImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override 
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth * 0.5625f));
    }
}

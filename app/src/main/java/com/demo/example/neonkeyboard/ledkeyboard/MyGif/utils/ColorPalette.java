package com.demo.example.neonkeyboard.ledkeyboard.MyGif.utils;


import com.demo.example.neonkeyboard.R;

public class ColorPalette {
    private static final ColorPalette COLOR_PALETTE;
    private static final int[] COLOR_RESOURCE_IDS;
    private final int[] mColorPalette;
    private final int mCount;
    private int[] mRandomizedPalette;

    static {
        int[] iArr = {R.color.random1, R.color.random2, R.color.random3, R.color.random4, R.color.random5, R.color.random6, R.color.random7, R.color.random8, R.color.random9, R.color.random10, R.color.random11, R.color.random12, R.color.random13, R.color.random14};
        COLOR_RESOURCE_IDS = iArr;
        COLOR_PALETTE = new ColorPalette(iArr);
    }

    public ColorPalette(int[] iArr) {
        int length = iArr.length;
        this.mCount = length;
        if (length > 0) {
            this.mColorPalette = iArr;
            shuffle();
            return;
        }
        throw new IllegalArgumentException("length of input color resource ids cannot be less than 1");
    }

    public void shuffle() {
        this.mRandomizedPalette = SdkListUtils.shuffle(this.mColorPalette);
    }

    public int get(int i) {
        if (i < 0) {
            i = Math.abs(i);
        }
        return this.mColorPalette[i % this.mCount];
    }

    public int random(int i) {
        if (i < 0) {
            i = Math.abs(i);
        }
        return this.mRandomizedPalette[i % this.mCount];
    }

    public static int getRandomColorResId(int i) {
        return COLOR_PALETTE.random(i);
    }
}

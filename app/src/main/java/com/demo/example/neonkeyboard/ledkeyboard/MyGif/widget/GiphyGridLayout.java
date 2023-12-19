package com.demo.example.neonkeyboard.ledkeyboard.MyGif.widget;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class GiphyGridLayout extends StaggeredGridLayoutManager {
    public GiphyGridLayout(int i, int i2) {
        super(i, i2);
        setItemPrefetchEnabled(false);
    }
}

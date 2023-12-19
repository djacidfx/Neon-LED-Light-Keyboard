package com.demo.example.neonkeyboard.ledkeyboard.MyGif.holder;

import com.tenor.android.core.widget.adapter.AbstractRVItem;


public class GifRVItem<IGif> extends AbstractRVItem {
    private com.tenor.android.core.model.IGif mGif;

    public GifRVItem(int i, com.tenor.android.core.model.IGif tddd) {
        super(i, tddd.getId());
        this.mGif = tddd;
    }

    public com.tenor.android.core.model.IGif get() {
        return this.mGif;
    }
}

package com.demo.example.neonkeyboard.ledkeyboard.MyGif.holder;

import android.view.View;
import android.widget.TextView;


import com.demo.example.neonkeyboard.R;
import com.tenor.android.core.constant.StringConstant;
import com.tenor.android.core.view.IBaseView;
import com.tenor.android.core.widget.viewholder.StaggeredGridLayoutItemViewHolder;


public class GifNoResultsVH<CTX extends IBaseView> extends StaggeredGridLayoutItemViewHolder<CTX> {
    private final TextView mNoResults;

    public GifNoResultsVH(View view, CTX ctx) {
        super(view, ctx);
        this.mNoResults = (TextView) view.findViewById(R.id.no_results);
    }

    public void setNoResultsMessage(String str) {
        this.mNoResults.setText(StringConstant.getOrEmpty(str));
    }
}

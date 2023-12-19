package com.demo.example.neonkeyboard.ledkeyboard.MyGif.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.tenor.android.core.widget.adapter.ListRVAdapter;


public class GifSearchItemDecoration extends RecyclerView.ItemDecoration {
    private int mBottom;
    private int mLeft;
    private int mRight;
    private int mTop;

    public GifSearchItemDecoration(int space) {
        this(space, space);
    }

    public GifSearchItemDecoration(int horizontal, int vertical) {
        this(horizontal, vertical, horizontal, vertical);
    }

    public GifSearchItemDecoration(int left, int top, int right, int bottom) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
    }

    @Override 
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        int viewAdapterPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        int itemViewType = ((ListRVAdapter) recyclerView.getAdapter()).getItemViewType(viewAdapterPosition);
        if (itemViewType == 0) {
            rect.top = this.mTop * 3;
            rect.bottom = this.mBottom;
        } else if (itemViewType != 1) {
            if (itemViewType != 2) {
                rect.left = this.mLeft;
                rect.right = this.mRight;
                rect.bottom = this.mBottom;
                return;
            }
            if (((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex() == 0) {
                rect.left = this.mLeft;
                rect.right = this.mRight / 2;
            } else {
                int i = this.mRight;
                rect.left = i - (i / 2);
                rect.right = i;
            }
            if (viewAdapterPosition == 0) {
                rect.top = this.mTop;
            }
            rect.bottom = this.mBottom / 2;
        } else if (view.getLayoutParams() != null && view.getLayoutParams().height == -2) {
            rect.left = 0;
            rect.right = 0;
            rect.top = this.mTop;
            rect.bottom = this.mBottom / 2;
        }
    }
}

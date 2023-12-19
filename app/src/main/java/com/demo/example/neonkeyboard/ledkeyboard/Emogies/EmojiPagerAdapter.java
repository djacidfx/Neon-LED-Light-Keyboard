package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;


public class EmojiPagerAdapter extends PagerAdapter {
    private List<EmojiGridView> mViews;

    @Override 
    public boolean isViewFromObject(View view, Object obj) {
        return obj == view;
    }

    public EmojiPagerAdapter(List<EmojiGridView> list) {
        this.mViews = list;
    }

    public _EmojiRecentGridView getRecentFragment() {
        for (EmojiGridView sWRX_EmojiGridView : this.mViews) {
            if (sWRX_EmojiGridView instanceof _EmojiRecentGridView) {
                return (_EmojiRecentGridView) sWRX_EmojiGridView;
            }
        }
        return null;
    }

    @Override 
    public int getCount() {
        return this.mViews.size();
    }

    @Override 
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View view = this.mViews.get(i).mRootView;
        viewGroup.addView(view, 0);
        return view;
    }

    @Override 
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}

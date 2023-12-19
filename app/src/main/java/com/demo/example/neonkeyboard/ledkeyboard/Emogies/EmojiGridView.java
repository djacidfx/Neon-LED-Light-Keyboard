package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;

import java.util.Arrays;


public class EmojiGridView {
    private EmojiData[] mData;
    public EmojiRecent mRecent;
    View mRootView;

    
    public interface OnEmojiClickedListener {
        void onEmojiClicked(EmojiData emoji);
    }

    public EmojiGridView(Context context, EmojiData[] emojiArr, EmojiRecent emojiRecent) {
        this.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_emoji_grid, (ViewGroup) null);
        setRecent(emojiRecent);
        if (emojiArr == null) {
            this.mData = EmojiData.EmojiPlacesData;
        } else {
            this.mData = (EmojiData[]) Arrays.asList(emojiArr).toArray(new EmojiData[emojiArr.length]);
        }
        EmojiAdapter sWRX_EmojiAdapter = new EmojiAdapter(this.mRootView.getContext(), this.mData);
        sWRX_EmojiAdapter.setEmojiClickListener(new OnEmojiClickedListener() { 
            @Override 
            public void onEmojiClicked(EmojiData emoji) {
                if (emoji != null && SoftKeyboard.Instance != null) {
                    SoftKeyboard.Instance.getCurrentInputConnection().commitText(emoji.getEmoji(), 1);
                    SoftKeyboard.Instance.updateShiftKeyState();
                    if (SoftKeyboard.SettingsVibration) {
                        SoftKeyboard.mVibrator.vibrate(15L);
                    }
                    if (EmojiGridView.this.mRecent != null) {
                        EmojiGridView.this.mRecent.addRecentEmoji(EmojiGridView.this.mRootView.getContext(), emoji);
                    }
                }
            }
        });
        ((GridView) this.mRootView.findViewById(R.id.viewEmojiGrid)).setAdapter((ListAdapter) sWRX_EmojiAdapter);
    }

    private void setRecent(EmojiRecent emojiRecent) {
        this.mRecent = emojiRecent;
    }
}

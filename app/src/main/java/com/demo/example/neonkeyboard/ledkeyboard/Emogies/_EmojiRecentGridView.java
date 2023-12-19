package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.content.Context;
import android.widget.GridView;
import android.widget.ListAdapter;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;


public class _EmojiRecentGridView extends EmojiGridView implements EmojiRecent {
    private EmojiAdapter mAdapter;

    public _EmojiRecentGridView(Context context, EmojiData[] emojiArr, EmojiRecent emojiRecent) {
        super(context, emojiArr, emojiRecent);
        EmojiAdapter sWRX_EmojiAdapter = new EmojiAdapter(this.mRootView.getContext(), EmojiRecentManager.getInstance(this.mRootView.getContext()));
        this.mAdapter = sWRX_EmojiAdapter;
        sWRX_EmojiAdapter.setEmojiClickListener(new OnEmojiClickedListener() { 
            @Override 
            public void onEmojiClicked(EmojiData emoji) {
                if (emoji != null && SoftKeyboard.Instance != null) {
                    SoftKeyboard.Instance.getCurrentInputConnection().commitText(emoji.getEmoji(), 1);
                    SoftKeyboard.Instance.updateShiftKeyState();
                    if (SoftKeyboard.SettingsVibration) {
                        SoftKeyboard.mVibrator.vibrate(15L);
                    }
                }
            }
        });
        ((GridView) this.mRootView.findViewById(R.id.viewEmojiGrid)).setAdapter((ListAdapter) this.mAdapter);
    }

    @Override 
    public void addRecentEmoji(Context context, EmojiData emoji) {
        EmojiRecentManager.getInstance(context).push(emoji);
        EmojiAdapter sWRX_EmojiAdapter = this.mAdapter;
        if (sWRX_EmojiAdapter != null) {
            sWRX_EmojiAdapter.notifyDataSetChanged();
        }
    }
}

package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.viewpager.widget.ViewPager;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;

import java.util.Arrays;


public class EmojiPopup extends PopupWindow implements ViewPager.OnPageChangeListener, EmojiRecent {
    public static boolean mVisible = false;
    private final Context mContext;
    public ViewPager mEmojiPager;
    private int mEmojiTabLastSelectedIndex = -1;
    private View[] mEmojiTabs;
    private final View mEmojiView;
    private EmojiRecentManager mRecentManager;
    private final View mRootView;

    @Override 
    public void onPageScrollStateChanged(int i) {
    }

    @Override 
    public void onPageScrolled(int i, float f, int i2) {
    }

    public EmojiPopup(View view, Context context) {
        super(context);
        this.mContext = context;
        this.mRootView = view;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_emoji, (ViewGroup) null, false);
        this.mEmojiView = inflate;
        ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.viewEmojiPager);
        this.mEmojiPager = viewPager;
        viewPager.setOnPageChangeListener(this);
        int i = 1;
        this.mEmojiPager.setAdapter(new EmojiPagerAdapter(Arrays.asList(new _EmojiRecentGridView(context, null, null), new EmojiGridView(context, EmojiData.EmojiPeopleData, this), new EmojiGridView(context, EmojiData.EmojiNatureData, this), new EmojiGridView(context, EmojiData.EmojiObjectsData, this), new EmojiGridView(context, EmojiData.EmojiPlacesData, this), new EmojiGridView(context, EmojiData.EmojiSymbolsData, this))));
        View[] viewArr = new View[6];
        this.mEmojiTabs = viewArr;
        viewArr[0] = inflate.findViewById(R.id.btnEmojiTab0Recent);
        this.mEmojiTabs[1] = inflate.findViewById(R.id.btnEmojiTab1People);
        this.mEmojiTabs[2] = inflate.findViewById(R.id.btnEmojiTab2Nature);
        this.mEmojiTabs[3] = inflate.findViewById(R.id.btnEmojiTab3Objects);
        this.mEmojiTabs[4] = inflate.findViewById(R.id.btnEmojiTab4Cars);
        this.mEmojiTabs[5] = inflate.findViewById(R.id.btnEmojiTab5Punctuation);
        int i2 = 0;
        while (true) {
            View[] viewArr2 = this.mEmojiTabs;
            if (i2 >= viewArr2.length) {
                break;
            }
            int aaa = i2;
            viewArr2[i2].setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view2) {
                    EmojiPopup.this.mEmojiPager.setCurrentItem(aaa);
                }
            });
            i2++;
        }
        ((Button) this.mEmojiView.findViewById(R.id.btnEmojiReturnToKeyboard)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view2) {
                EmojiPopup.this.dismiss();
            }
        });
        ((ImageButton) this.mEmojiView.findViewById(R.id.btnEmojiBackspace)).setOnTouchListener(new RepeatListener(1000, 50, new View.OnClickListener() {
            @Override 
            public void onClick(View view2) {
                if (SoftKeyboard.Instance != null) {
                    InputConnection currentInputConnection = SoftKeyboard.Instance.getCurrentInputConnection();
                    if (SoftKeyboard.Instance.mComposing.length() > 1) {
                        SoftKeyboard.Instance.mComposing.delete(SoftKeyboard.Instance.mComposing.length() - 1, SoftKeyboard.Instance.mComposing.length());
                        currentInputConnection.setComposingText(SoftKeyboard.Instance.mComposing, 1);
                        SoftKeyboard.Instance.updateCandidates();
                    } else if (SoftKeyboard.Instance.mComposing.length() > 0) {
                        SoftKeyboard.Instance.mComposing.setLength(0);
                        currentInputConnection.commitText("", 0);
                        SoftKeyboard.Instance.updateCandidates();
                    } else {
                        currentInputConnection.sendKeyEvent(new KeyEvent(0, 67));
                        currentInputConnection.sendKeyEvent(new KeyEvent(1, 67));
                    }
                    SoftKeyboard.Instance.updateShiftKeyState();
                    SoftKeyboard.playClick(-5);
                }
            }
        }));
        EmojiRecentManager sWRX_EmojiRecentManager = EmojiRecentManager.getInstance(this.mEmojiView.getContext());
        this.mRecentManager = sWRX_EmojiRecentManager;
        int recentPage = sWRX_EmojiRecentManager.getRecentPage();
        i = (recentPage == 0 && this.mRecentManager.size() == 0) ? i : recentPage;
        if (i == 0) {
            onPageSelected(i);
        } else {
            this.mEmojiPager.setCurrentItem(i, false);
        }
        setContentView(this.mEmojiView);
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public void showAtBottom() {
        showAtLocation(this.mRootView, 80, 0, 0);
        mVisible = true;
        if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
            SoftKeyboard.Instance.mInputView.invalidate();
        }
    }

    @Override 
    public void dismiss() {
        super.dismiss();
        EmojiRecentManager.getInstance(this.mContext).saveRecent();
        mVisible = false;
        if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
            SoftKeyboard.Instance.mInputView.invalidate();
        }
    }

    @Override 
    public void addRecentEmoji(Context context, EmojiData emoji) {
        ((EmojiPagerAdapter) this.mEmojiPager.getAdapter()).getRecentFragment().addRecentEmoji(context, emoji);
    }

    @Override 
    public void onPageSelected(int i) {
        int i2 = this.mEmojiTabLastSelectedIndex;
        if (i2 == i) {
            return;
        }
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            if (i2 >= 0) {
                View[] viewArr = this.mEmojiTabs;
                if (i2 < viewArr.length) {
                    viewArr[i2].setSelected(false);
                }
            }
            this.mEmojiTabs[i].setSelected(true);
            this.mEmojiTabLastSelectedIndex = i;
            this.mRecentManager.setRecentPage(i);
        }
    }
}

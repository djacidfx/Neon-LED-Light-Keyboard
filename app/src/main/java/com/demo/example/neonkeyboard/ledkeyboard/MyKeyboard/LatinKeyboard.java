package com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

import com.demo.example.neonkeyboard.R;


public class LatinKeyboard extends Keyboard {
    public static boolean mKeyLanguageSwitchVisible = true;
    private int height;
    private LatinKey mKeyEnter = null;
    private LatinKey mKeySavedLanguageSwitch = null;
    private LatinKey mKeySavedMode = null;
    private LatinKey mLanguageSwitchKey = null;
    private LatinKey mModeChangeKey = null;

    
    public LatinKeyboard(Context context, int i, double height_modifier) {
        super(context, i);
        int i2 = 0;
        for (Key key : getKeys()) {
            double d = key.height;
            Double.isNaN(d);
            key.height = (int) (d * height_modifier);
            double d2 = key.y;
            Double.isNaN(d2);
            key.y = (int) (d2 * height_modifier);
            i2 = key.height;
        }
        setKeyHeight(i2);
        getNearestKeys(0, 0);
    }

    @Override 
    public void setKeyHeight(int height) {
        super.setKeyHeight(height);
    }

    @Override 
    public int getHeight() {
        return (getKeyHeight() * 4) + 40;
    }

    public void changeKeyHeight(double height_modifier) {
        int i = 0;
        for (Key key : getKeys()) {
            double d = key.height;
            Double.isNaN(d);
            key.height = (int) (d * height_modifier);
            double d2 = key.y;
            Double.isNaN(d2);
            key.y = (int) (d2 * height_modifier);
            i = key.height;
        }
        setKeyHeight(i);
        getNearestKeys(0, 0);
    }

    @Override 
    public Key createKeyFromXml(Resources resources, Row row, int i, int i2, XmlResourceParser xmlResourceParser) {
        LatinKey latinKey = new LatinKey(resources, row, i, i2, xmlResourceParser);
        if (latinKey.codes[0] == 10) {
            this.mKeyEnter = latinKey;
        } else if (latinKey.codes[0] == -2) {
            this.mModeChangeKey = latinKey;
            this.mKeySavedMode = new LatinKey(resources, row, i, i2, xmlResourceParser);
        } else if (latinKey.codes[0] == -101) {
            this.mLanguageSwitchKey = latinKey;
            this.mKeySavedLanguageSwitch = new LatinKey(resources, row, i, i2, xmlResourceParser);
        }
        return latinKey;
    }

    public void setLanguageSwitchKeyVisibility(boolean z) {
        LatinKey latinKey = this.mModeChangeKey;
        if (latinKey != null && this.mLanguageSwitchKey != null) {
            mKeyLanguageSwitchVisible = true;
            latinKey.width = this.mKeySavedMode.width;
            this.mModeChangeKey.x = this.mKeySavedMode.x;
            this.mLanguageSwitchKey.width = this.mKeySavedLanguageSwitch.width;
            this.mLanguageSwitchKey.icon = this.mKeySavedLanguageSwitch.icon;
            this.mLanguageSwitchKey.iconPreview = this.mKeySavedLanguageSwitch.iconPreview;
        }
    }

    public void setImeOptions(Resources resources, int i) {
        LatinKey latinKey = this.mKeyEnter;
        if (latinKey != null) {
            int i2 = i & 1073742079;
            if (i2 == 2) {
                latinKey.iconPreview = null;
                this.mKeyEnter.icon = null;
                this.mKeyEnter.label = resources.getText(R.string.label_go_key);
                LatinKeyboardView.mImeAction = 2;
            } else if (i2 == 3) {
                latinKey.icon = resources.getDrawable(R.drawable.ic_search);
                this.mKeyEnter.label = null;
                LatinKeyboardView.mImeAction = 3;
            } else if (i2 == 4) {
                latinKey.iconPreview = null;
                this.mKeyEnter.icon = null;
                this.mKeyEnter.label = resources.getText(R.string.label_send_key);
                LatinKeyboardView.mImeAction = 4;
            } else if (i2 != 5) {
                latinKey.icon = resources.getDrawable(R.drawable.ic_backspace);
                this.mKeyEnter.label = null;
                LatinKeyboardView.mImeAction = 0;
            } else {
                latinKey.iconPreview = null;
                this.mKeyEnter.icon = null;
                this.mKeyEnter.label = resources.getText(R.string.label_next_key);
                LatinKeyboardView.mImeAction = 5;
            }
        }
    }
}

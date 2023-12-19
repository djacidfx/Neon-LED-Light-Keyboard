package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;


public class SettingActivity extends AppCompatActivity {
    boolean iAmFromInside = true;
    ImageView img_popupOnKey;
    ImageView img_vibration;
    LinearLayout l_languages;
    SeekBar seek_fontSize;
    SeekBar seek_keyboardSize;
    SeekBar seek_roundBtn;
    SharedPreferences sp;
    TextView txt_fontSize;
    TextView txt_keyboardSize;
    TextView txt_roundBtn;

    
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_setting);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd(this);


        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                SettingActivity.this.onBackPressed();
            }
        });
        HelperResize.setSize(imageView, 90, 90, true);
        this.iAmFromInside = getIntent().getBooleanExtra("iAmFromInside", true);
        this.seek_fontSize = (SeekBar) findViewById(R.id.seek_fontSize);
        this.txt_fontSize = (TextView) findViewById(R.id.txt_fontSize);
        this.img_popupOnKey = (ImageView) findViewById(R.id.img_popupOnKey);
        this.img_vibration = (ImageView) findViewById(R.id.img_vibration);
        this.seek_roundBtn = (SeekBar) findViewById(R.id.seek_roundBtn);
        this.txt_roundBtn = (TextView) findViewById(R.id.txt_roundBtn);
        this.l_languages = (LinearLayout) findViewById(R.id.l_languages);
        this.txt_keyboardSize = (TextView) findViewById(R.id.txt_keyboardSize);
        this.seek_keyboardSize = (SeekBar) findViewById(R.id.seek_keyboardSize);
        this.l_languages.setOnClickListener(new AnonymousClass3());
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.sp = defaultSharedPreferences;
        int i = defaultSharedPreferences.getInt("font_size", 5);
        this.txt_fontSize.setText(String.valueOf(i + 15));
        this.seek_fontSize.setMax(13);
        this.seek_fontSize.setProgress(i);
        this.seek_fontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SettingActivity.this.setIntPref("font_size", progress);
                SoftKeyboard.SettingsFontSize = progress;
                SettingActivity.this.txt_fontSize.setText(String.valueOf(progress + 15));
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        int i2 = this.sp.getInt("keyboardSize", 100) - 90;
        this.txt_keyboardSize.setText(String.valueOf(i2));
        this.seek_keyboardSize.setMax(30);
        this.seek_keyboardSize.setProgress(i2);
        this.seek_keyboardSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int i3 = progress + 90;
                SettingActivity.this.setIntPref("keyboardSize", i3);
                SoftKeyboard.keyboardSize = i3;
                SettingActivity.this.txt_keyboardSize.setText(String.valueOf(progress));
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        int i3 = this.sp.getInt("roundness", 4);
        this.txt_roundBtn.setText(String.valueOf(i3));
        this.seek_roundBtn.setMax(14);
        this.seek_roundBtn.setProgress(i3);
        this.seek_roundBtn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SettingActivity.this.setIntPref("roundness", progress);
                SoftKeyboard.SettingsRoundness = progress;
                SettingActivity.this.txt_roundBtn.setText(String.valueOf(progress));
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        boolean booleanValue = Boolean.valueOf(this.sp.getBoolean("isPopup", true)).booleanValue();
        Integer valueOf = Integer.valueOf((int) R.drawable.on);
        Integer valueOf2 = Integer.valueOf((int) R.drawable.off);
        if (booleanValue) {
            Glide.with((FragmentActivity) this).load(valueOf).into(this.img_popupOnKey);
        } else {
            Glide.with((FragmentActivity) this).load(valueOf2).into(this.img_popupOnKey);
        }
        this.img_popupOnKey.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                if (Boolean.valueOf(SettingActivity.this.sp.getBoolean("isPopup", true)).booleanValue()) {
                    SoftKeyboard.settingsPopup = false;
                    SettingActivity.this.setBoolPref("isPopup", false);
                    SettingActivity.this.img_popupOnKey.setImageResource(R.drawable.off);
                } else {
                    SoftKeyboard.settingsPopup = true;
                    SettingActivity.this.setBoolPref("isPopup", true);
                    SettingActivity.this.img_popupOnKey.setImageResource(R.drawable.on);
                }
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        if (Boolean.valueOf(this.sp.getBoolean("vibration", true)).booleanValue()) {
            Glide.with((FragmentActivity) this).load(valueOf).into(this.img_vibration);
        } else {
            Glide.with((FragmentActivity) this).load(valueOf2).into(this.img_vibration);
        }
        this.img_vibration.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                if (Boolean.valueOf(SettingActivity.this.sp.getBoolean("vibration", true)).booleanValue()) {
                    SoftKeyboard.SettingsVibration = false;
                    SettingActivity.this.setBoolPref("vibration", false);
                    SettingActivity.this.img_vibration.setImageResource(R.drawable.off);
                } else {
                    SoftKeyboard.SettingsVibration = true;
                    SettingActivity.this.setBoolPref("vibration", true);
                    SettingActivity.this.img_vibration.setImageResource(R.drawable.on);
                }
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        HelperResize.setSize((LinearLayout) findViewById(R.id.layout1), 976, 295, true);
        HelperResize.setSize((LinearLayout) findViewById(R.id.layout2), 976, 295, true);
        HelperResize.setSize((LinearLayout) findViewById(R.id.layout3), 976, 295, true);
        HelperResize.setSize((LinearLayout) findViewById(R.id.layout4), 976, 178, true);
        HelperResize.setSize((LinearLayout) findViewById(R.id.layout5), 976, 178, true);
        HelperResize.setSize((ImageView) findViewById(R.id.img1), 193, 118, true);
        HelperResize.setSize((ImageView) findViewById(R.id.img2), 193, 118, true);
        HelperResize.setSize((ImageView) findViewById(R.id.img3), 193, 118, true);
        HelperResize.setSize((ImageView) findViewById(R.id.img4), 144, 116, true);
        HelperResize.setSize((ImageView) findViewById(R.id.img5), 96, 96, true);
        HelperResize.setSize(this.img_popupOnKey, 100, 52, true);
    }

    
    
    
    public class AnonymousClass3 implements View.OnClickListener {
        AnonymousClass3() {
        }

        @Override 
        public void onClick(View v) {

            Intent intent2 = new Intent(SettingActivity.this, LanguageActivity.class);
            intent2.putExtra("iAmFromInside", true);
            SettingActivity.this.startActivity(intent2);

        }
    }

    public void setIntPref(String key, int val) {
        SharedPreferences.Editor edit = this.sp.edit();
        edit.putInt(key, val);
        edit.apply();
    }

    public void setBoolPref(String key, boolean val) {
        SharedPreferences.Editor edit = this.sp.edit();
        edit.putBoolean(key, val);
        edit.apply();
    }

    @Override 
    public void onBackPressed() {

        finish();

    }


}

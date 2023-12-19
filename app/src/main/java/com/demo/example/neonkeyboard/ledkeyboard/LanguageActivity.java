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

import androidx.appcompat.app.AppCompatActivity;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;


public class LanguageActivity extends AppCompatActivity {
    ImageView img_back;
    ImageView img_check_english;
    ImageView img_check_gujarati;
    ImageView img_check_hindi;
    LinearLayout l_english;
    LinearLayout l_gujarati;
    LinearLayout l_hindi;
    SharedPreferences sp;
    boolean iAmFromInside = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_language);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd(this);


        this.img_back = (ImageView) findViewById(R.id.img_back);
        this.l_english = (LinearLayout) findViewById(R.id.l_english);
        this.l_hindi = (LinearLayout) findViewById(R.id.l_hindi);
        this.l_gujarati = (LinearLayout) findViewById(R.id.l_gujarati);
        this.img_check_english = (ImageView) findViewById(R.id.img_check_english);
        this.img_check_hindi = (ImageView) findViewById(R.id.img_check_hindi);
        this.img_check_gujarati = (ImageView) findViewById(R.id.img_check_gujarati);
        allggoen();
        this.iAmFromInside = getIntent().getBooleanExtra("iAmFromInside", true);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sp = defaultSharedPreferences;
        int i = defaultSharedPreferences.getInt("inputLangvg", 0);
        if (i == 0) {
            this.img_check_english.setVisibility(View.VISIBLE);
        } else if (i == 1) {
            this.img_check_hindi.setVisibility(View.VISIBLE);
        } else if (i == 2) {
            this.img_check_gujarati.setVisibility(View.VISIBLE);
        }
        this.img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageActivity.this.onBackPressed();
            }
        });
        this.l_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageActivity.this.allggoen();
                LanguageActivity.this.img_check_english.setVisibility(View.VISIBLE);
                SoftKeyboard.inputLangvg = 0;
                SharedPreferences.Editor edit = LanguageActivity.this.sp.edit();
                edit.putInt("inputLangvg", 0);
                edit.apply();
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        this.l_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageActivity.this.allggoen();
                LanguageActivity.this.img_check_hindi.setVisibility(View.VISIBLE);
                SoftKeyboard.inputLangvg = 1;
                SharedPreferences.Editor edit = LanguageActivity.this.sp.edit();
                edit.putInt("inputLangvg", 1);
                edit.apply();
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        this.l_gujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageActivity.this.allggoen();
                LanguageActivity.this.img_check_gujarati.setVisibility(View.VISIBLE);
                SoftKeyboard.inputLangvg = 2;
                SharedPreferences.Editor edit = LanguageActivity.this.sp.edit();
                edit.putInt("inputLangvg", 2);
                edit.apply();
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        HelperResize.setSize(this.img_back, 90, 90, true);
        HelperResize.setSize((ImageView) findViewById(R.id.divider1), 1080, 2, true);
        HelperResize.setSize((ImageView) findViewById(R.id.divider2), 1080, 2, true);
        HelperResize.setSize((ImageView) findViewById(R.id.divider3), 1080, 2, true);
        HelperResize.setSize(this.img_check_english, 74, 74, true);
        HelperResize.setSize(this.img_check_hindi, 74, 74, true);
        HelperResize.setSize(this.img_check_gujarati, 74, 74, true);
    }

    public void allggoen() {
        this.img_check_english.setVisibility(View.INVISIBLE);
        this.img_check_hindi.setVisibility(View.INVISIBLE);
        this.img_check_gujarati.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (this.iAmFromInside) {
            super.onBackPressed();
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}

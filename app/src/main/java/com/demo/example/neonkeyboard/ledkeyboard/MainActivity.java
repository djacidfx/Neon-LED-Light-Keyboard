package com.demo.example.neonkeyboard.ledkeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.Constan.Utils;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;


public class MainActivity extends AppCompatActivity {
    public static EditText et_test_main;
    public static Context mAppContext;
    ImageView im_myFont;
    ImageView im_myPhoto;
    ImageView im_setting;
    ImageView img_theme;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_main);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);



        mAppContext = getApplicationContext();
        Utils.load(this);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SoftKeyboard.settingsPrediction = defaultSharedPreferences.getBoolean("prediction", true);
        SoftKeyboard.settingsPopup = defaultSharedPreferences.getBoolean("isPopup", true);
        SoftKeyboard.isMyFont = defaultSharedPreferences.getBoolean("isMyFont", false);
        SoftKeyboard.SettingsVibration = defaultSharedPreferences.getBoolean("vibration", false);
        SoftKeyboard.SettingsDPWPermissionGranted = defaultSharedPreferences.getBoolean("dpw_permission_granted", true);
        SoftKeyboard.themeNumber = defaultSharedPreferences.getInt("wallpaper_ind", 3);
        SoftKeyboard.SettingsFontSize = defaultSharedPreferences.getInt("font_size", 5);
        SoftKeyboard.keyboardSize = defaultSharedPreferences.getInt("keyboardSize", 100);
        SoftKeyboard.SettingsRoundness = defaultSharedPreferences.getInt("roundness", 4);
        SoftKeyboard.SettingsOpacity = defaultSharedPreferences.getInt("opacity", 255);
        SoftKeyboard.fontNumber = defaultSharedPreferences.getInt("fontNumber", 1);
        SoftKeyboard.inputLangvg = defaultSharedPreferences.getInt("inputLangvg", 0);
        ImageView imageView = (ImageView) findViewById(R.id.btnSettingsPreview);
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (!Utils.isMyKeyboardEnabled(MainActivity.mAppContext)) {
                    MainActivity.this.startActivityForResult(new Intent("android.settings.INPUT_METHOD_SETTINGS"), 0);
                } else if (!Utils.isMyKeyboardActive(MainActivity.mAppContext)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.showInputMethodPicker();
                    }
                } else {
                    ((InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(2, 0);
                }
            }
        });
        this.im_setting = (ImageView) findViewById(R.id.im_setting);
        this.img_theme = (ImageView) findViewById(R.id.img_theme);
        this.im_myPhoto = (ImageView) findViewById(R.id.im_myPhoto);
        this.im_myFont = (ImageView) findViewById(R.id.im_myFont);
        EditText editText = (EditText) findViewById(R.id.et_test_main);
        et_test_main = editText;
        if (editText != null) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { 
                @Override 
                public void onFocusChange(View view, boolean z) {
                    if (!z) {
                        MainActivity.et_test_main.getText().clear();
                        MainActivity.et_test_main.clearFocus();
                    }
                }
            });
        }
        this.im_setting.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.putExtra("iAmFromInside", true);
                MainActivity.this.startActivity(intent);
            }
        });
        this.img_theme.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ThemeActivity.class));
            }
        });

        this.im_myPhoto.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MyphotoActivity.class));
            }
        });
        this.im_myFont.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FontActivity.class);
                intent.putExtra("iAmFromInside", true);
                MainActivity.this.startActivity(intent);
            }
        });
        boolean z = !Utils.isMIUI() || Utils.isDisplayPopupWindowsPermissionGranted(this);
        SoftKeyboard.SettingsDPWPermissionGranted = z;
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
        edit.putBoolean("dpw_permission_granted", SoftKeyboard.SettingsDPWPermissionGranted);
        edit.commit();
        if (!z) {
            final Dialog dialog = new Dialog(this, R.style.MaterialDialogSheet);
            dialog.setContentView(R.layout.dialog_permission);
            dialog.setCancelable(false);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 1080) / 1080, (getResources().getDisplayMetrics().heightPixels * 1920) / 1920);
            layoutParams.gravity = 17;
            ((LinearLayout) dialog.findViewById(R.id.layout)).setLayoutParams(layoutParams);
            HelperResize.setSize((LinearLayout) dialog.findViewById(R.id.layout1), 841, 859, true);
            ImageView imageView2 = (ImageView) dialog.findViewById(R.id.yes);
            ImageView imageView3 = (ImageView) dialog.findViewById(R.id.no);
            HelperResize.setSize(imageView2, 257, 101, true);
            HelperResize.setSize(imageView3, 257, 101, true);
            TextView textView = (TextView) dialog.findViewById(R.id.text);
            if (Build.VERSION.SDK_INT >= 24) {
                textView.setText(Html.fromHtml("In order to use some of the features of <b>RGB Keyboard</b> on <b>Xiaomi</b> device it is required that you grant permission<br><br><b>Display pop-up windows while running in the background</b><br><br>to allow you to open settings and set keyboard background directly from keyboard.", 0));
            } else {
                textView.setText(Html.fromHtml("In order to use some of the features of <b>RGB Keyboard</b> on <b>Xiaomi</b> device it is required that you grant permission<br><br><b>Display pop-up windows while running in the background</b><br><br>to allow you to open settings and set keyboard background directly from keyboard."));
            }
            imageView2.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) {
                    MainActivity sWRX_MainActivity = MainActivity.this;
                    Utils.openOtherPermissionSettings(sWRX_MainActivity, sWRX_MainActivity, 1);
                    dialog.dismiss();
                }
            });
            imageView3.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        ImageView imageView4 = (ImageView) findViewById(R.id.back);
        imageView4.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                MainActivity.this.onBackPressed();
            }
        });
        HelperResize.getheightandwidth(getApplicationContext());
        HelperResize.setSize(imageView4, 90, 90, true);
        HelperResize.setSize(et_test_main, 925, 131, true);
        HelperResize.setSize(imageView, 299, 201, true);
        HelperResize.setSize(this.img_theme, 1026, 366, true);
        HelperResize.setSize(this.im_myPhoto, 534, 376, true);
        HelperResize.setSize(this.im_setting, 534, 376, true);
        HelperResize.setSize(this.im_myFont, 534, 376, true);
    }

    
    @Override 
    public void onPause() {
        et_test_main.clearFocus();
        super.onPause();
    }

    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        boolean z = true;
        if (i == 1) {
            if (Utils.isMIUI() && !Utils.isDisplayPopupWindowsPermissionGranted(this)) {
                z = false;
            }
            SoftKeyboard.SettingsDPWPermissionGranted = z;
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
            edit.putBoolean("dpw_permission_granted", SoftKeyboard.SettingsDPWPermissionGranted);
            edit.commit();
        }
    }

    @Override 
    public void onBackPressed() {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }
}

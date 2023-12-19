package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.Constan.Utils;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;


public class SetupActivity extends AppCompatActivity {
    ImageView buttonEnable;
    ImageView buttonSelect;
    Context context;


    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_setup);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);



        this.buttonEnable = (ImageView) findViewById(R.id.buttonEnable);
        this.buttonSelect = (ImageView) findViewById(R.id.buttonSelect);



        this.context = this;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SoftKeyboard.themeNumber = defaultSharedPreferences.getInt("wallpaper_ind", 3);
        SoftKeyboard.SettingsFontSize = defaultSharedPreferences.getInt("font_size", 5);
        SoftKeyboard.keyboardSize = defaultSharedPreferences.getInt("keyboardSize", 100);
        SoftKeyboard.SettingsRoundness = defaultSharedPreferences.getInt("roundness", 4);
        SoftKeyboard.SettingsOpacity = defaultSharedPreferences.getInt("opacity", 255);
        SoftKeyboard.fontNumber = defaultSharedPreferences.getInt("fontNumber", 1);
        SoftKeyboard.inputLangvg = defaultSharedPreferences.getInt("inputLangvg", 0);
        SoftKeyboard.isMyFont = defaultSharedPreferences.getBoolean("isMyFont", false);
        if (Utils.isMyKeyboardEnabled(this) && Utils.isMyKeyboardActive(this)) {
            finishAffinity();
            startActivity(new Intent(this, MainActivity.class));
        }
        HelperResize.getheightandwidth(this);
        HelperResize.setSize((ImageView) findViewById(R.id.text), 459, 216, true);
        HelperResize.setSize(this.buttonEnable, 813, 131, true);
        HelperResize.setSize(this.buttonSelect, 813, 131, true);
    }

    @Override 
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!z) {
            return;
        }
        if (Utils.isMyKeyboardEnabled(this)) {
            if (Utils.isMyKeyboardActive(this)) {
                this.buttonEnable.setEnabled(false);
                this.buttonEnable.setBackgroundResource(R.drawable.enable_keyboard_press);
                this.buttonSelect.setEnabled(false);
                this.buttonSelect.setBackgroundResource(R.drawable.select_keyboard_press);
                if (Build.VERSION.SDK_INT < 23) {
                    finishAffinity();
                    startActivity(new Intent(this, MainActivity.class));
                    return;
                }else if (Build.VERSION.SDK_INT >= 33) {
                    finishAffinity();
                    startActivity(new Intent(this, MainActivity.class));
                    return;
                }  else if (ContextCompat.checkSelfPermission(this.context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                    finishAffinity();
                    startActivity(new Intent(this, MainActivity.class));
                    return;
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 234);
                }
            }
            this.buttonEnable.setEnabled(false);
            this.buttonEnable.setBackgroundResource(R.drawable.enable_keyboard_press);
            this.buttonSelect.setEnabled(true);
            this.buttonSelect.setBackgroundResource(R.drawable.select_keyboard_unpress);
            this.buttonSelect.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    InputMethodManager inputMethodManager = (InputMethodManager) SetupActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.showInputMethodPicker();
                    }
                }
            });
            return;
        }
        this.buttonEnable.setEnabled(true);
        this.buttonEnable.setBackgroundResource(R.drawable.enable_keyboard_unpress);
        this.buttonSelect.setEnabled(false);
        this.buttonSelect.setBackgroundResource(R.drawable.select_keyboard_press);
        this.buttonEnable.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                SetupActivity.this.startActivityForResult(new Intent("android.settings.INPUT_METHOD_SETTINGS"), 0);
            }
        });
    }


    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

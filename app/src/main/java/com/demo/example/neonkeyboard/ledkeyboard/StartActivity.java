package com.demo.example.neonkeyboard.ledkeyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;


public class StartActivity extends AppCompatActivity {

    ImageView btnStart;
    ImageView btnsetting;

    private PowerManager.WakeLock wl;

    
    @SuppressLint("InvalidWakeLockTag")
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_start);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 852) / 1080, (getResources().getDisplayMetrics().heightPixels * 555) / 1920);
        layoutParams.gravity = 17;


        ImageView imageView = (ImageView) findViewById(R.id.btnStart);
        this.btnStart = imageView;
        imageView.setOnClickListener(new AnonymousClass2());
        ImageView imageView2 = (ImageView) findViewById(R.id.btnsetting);
        this.btnsetting = imageView2;
        imageView2.setOnClickListener(new AnonymousClass3());
        this.wl = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(26, "DoNjfdhotDimScreen");
        HelperResize.setSize(this.btnStart, 728, 229, false);
        HelperResize.setSize(this.btnsetting, 728, 229, false);
    }

    
    
    
    public class AnonymousClass2 implements View.OnClickListener {
        AnonymousClass2() {
        }

        @Override 
        public void onClick(View v) {

            StartActivity.this.startActivity(new Intent(StartActivity.this, SetupActivity.class));
        }
    }

    
    
    
    public class AnonymousClass3 implements View.OnClickListener {
        AnonymousClass3() {
        }

        @Override 
        public void onClick(View v) {

            StartActivity.this.startActivity(new Intent(StartActivity.this, MoreActivity.class));

        }
    }

    @Override 
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this, R.style.MaterialDialogSheet);
        dialog.setContentView(R.layout.dialog_exit);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 1080) / 1080, (getResources().getDisplayMetrics().heightPixels * 1920) / 1920);
        layoutParams.gravity = 17;
        ((LinearLayout) dialog.findViewById(R.id.layout)).setLayoutParams(layoutParams);
        HelperResize.setSize((LinearLayout) dialog.findViewById(R.id.layout1), 706, 570, true);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.yes);
        ImageView imageView2 = (ImageView) dialog.findViewById(R.id.no);
        HelperResize.setSize(imageView, 201, 98, true);
        HelperResize.setSize(imageView2, 201, 98, true);
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                StartActivity.this.finishAffinity();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    
    @Override 
    public void onResume() {
        super.onResume();
    }



    

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;
import cz.msebera.android.httpclient.protocol.HTTP;


public class MoreActivity extends AppCompatActivity {
    ImageView back;
    Context context;
    ImageView policyBtn;
    ImageView rateBtn;
    ImageView shareBtn;
    RelativeLayout topbar;

    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd(this);


        this.context = this;

        this.shareBtn = (ImageView) findViewById(R.id.shareBtn);
        this.rateBtn = (ImageView) findViewById(R.id.rateBtn);
        this.policyBtn = (ImageView) findViewById(R.id.privacyBtn);
        this.back = (ImageView) findViewById(R.id.backBtn);
        this.topbar = (RelativeLayout) findViewById(R.id.topbar);
        HelperResize.getheightandwidth(this.context);
        HelperResize.setSize(this.back, 90, 90);
        HelperResize.setSize(this.topbar, 1080, 150);
        HelperResize.setSize(this.shareBtn, 948, 181);
        HelperResize.setSize(this.rateBtn, 948, 181);
        HelperResize.setSize(this.policyBtn, 948, 181);
        this.shareBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                intent.putExtra("android.intent.extra.SUBJECT", R.string.app_name);
                intent.putExtra("android.intent.extra.TEXT", "See " + MoreActivity.this.getString(R.string.app_name) + " from - https://play.google.com/store/apps/details?id=" + MoreActivity.this.getPackageName());
                MoreActivity.this.startActivity(Intent.createChooser(intent, "Share Application"));
            }
        });
        this.rateBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                MoreActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + MoreActivity.this.getPackageName())));
            }
        });
        this.policyBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                MoreActivity.this.startActivity(new Intent(MoreActivity.this, PrivacyPolicyActivity.class));
            }
        });
        this.back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                MoreActivity.this.onBackPressed();
            }
        });
    }

    @Override 
    public void onBackPressed() {
        finish();
    }

}

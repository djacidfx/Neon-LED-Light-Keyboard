package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.example.neonkeyboard.R;


public class PrivacyPolicyActivity extends AppCompatActivity {
    Context context;
    ProgressBar pbar;
    WebView wv;

    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        setContentView(R.layout.privacy_policy);
        this.context = this;
        this.pbar = (ProgressBar) findViewById(R.id.progress_bar);
        WebView webView = (WebView) findViewById(R.id.wv_privacy_policy);
        this.wv = webView;
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        this.wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        this.wv.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            this.wv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            this.wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        init();
    }

    public void init() {
        this.wv.loadUrl("https://www.google.com/");
        this.wv.requestFocus();
        this.pbar.setVisibility(View.VISIBLE);
        this.wv.setWebViewClient(new WebViewClient() { 
            @Override 
            public void onPageFinished(WebView view, String url) {
                try {
                    PrivacyPolicyActivity.this.pbar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

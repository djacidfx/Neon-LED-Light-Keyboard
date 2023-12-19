package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;
import com.tenor.android.core.constant.StringConstant;

import cz.msebera.android.httpclient.message.TokenParser;

import java.io.IOException;
import java.io.PrintStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SplashActivity extends AppCompatActivity {
    public static String ads_loading_flg = "1";
    public static String ads_loading_flgactivity = "1";
    public static String appopen = "11";
    public static String banner_font = "11";
    public static String banner_myPhoto = "11";
    public static String banner_setting = "11";
    public static String banner_themeAct = "11";
    public static String fullscreen_font = "11";
    public static String fullscreen_myPhoto = "11";
    public static String fullscreen_preload = "11";
    public static String fullscreen_setting = "11";
    public static String fullscreen_start = "11";
    public static String fullscreen_startback = "11";
    public static String fullscreen_theme = "11";
    public static String nativeid_language = "11";
    public static String nativeid_more = "11";
    public static String nativeid_setUp = "11";
    public static String nativeid_start = "11";
    public static String pubid = "11";
    SharedPreferences.Editor myEdit;
    private long secondsRemaining;
    SharedPreferences sharedPreferences;
    ScalableVideoView videoView;

    
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_splash);
        this.videoView = (ScalableVideoView) findViewById(R.id.videoview);
        this.videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + StringConstant.SLASH + R.raw.splash));
        this.videoView.start();
        this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { 
            @Override 
            public void onCompletion(MediaPlayer mp) {
                SplashActivity.this.videoView.start();
            }
        });
        next();
    }

    
    @Override 
    public void onResume() {
        super.onResume();
        this.videoView.start();
        this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { 
            @Override 
            public void onCompletion(MediaPlayer mp) {
                SplashActivity.this.videoView.start();
            }
        });
    }

    private void getOnlineIds() {
        SharedPreferences sharedPreferences = getSharedPreferences("bdcPref", 0);
        this.sharedPreferences = sharedPreferences;
        this.myEdit = sharedPreferences.edit();
        ads_loading_flg = this.sharedPreferences.getString("ads_loading_flg", "11");
        ads_loading_flgactivity = this.sharedPreferences.getString("ads_loading_flgactivity", "11");
        banner_setting = this.sharedPreferences.getString("banner_setting", "11");
        banner_font = this.sharedPreferences.getString("banner_font", "11");
        banner_themeAct = this.sharedPreferences.getString("banner_themeAct", "11");
        banner_myPhoto = this.sharedPreferences.getString("banner_myPhoto", "11");
        fullscreen_preload = this.sharedPreferences.getString("fullscreen_preload", "11");
        fullscreen_start = this.sharedPreferences.getString("fullscreen_start", "11");
        fullscreen_startback = this.sharedPreferences.getString("fullscreen_startback", "11");
        fullscreen_font = this.sharedPreferences.getString("fullscreen_font", "11");
        fullscreen_myPhoto = this.sharedPreferences.getString("fullscreen_myPhoto", "11");
        fullscreen_setting = this.sharedPreferences.getString("fullscreen_setting", "11");
        fullscreen_theme = this.sharedPreferences.getString("fullscreen_theme", "11");
        nativeid_start = this.sharedPreferences.getString("nativeid_start", "11");
        nativeid_more = this.sharedPreferences.getString("nativeid_more", "11");
        nativeid_setUp = this.sharedPreferences.getString("nativeid_setUp", "11");
        nativeid_language = this.sharedPreferences.getString("nativeid_language", "11");
        appopen = this.sharedPreferences.getString("appopen", "11");
        pubid = this.sharedPreferences.getString("pubid", "11");
        if (isNetworkAvailable()) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.newCall(new Request.Builder().url("http://phpstack-352233-2616922.cloudwaysapps.com/" + getPackageName() + ".txt").build()).enqueue(new Callback() { 
                    @Override 
                    public void onFailure(Call call, IOException e) {
                        SplashActivity.this.runOnUiThread(new Runnable() {
                            @Override 
                            public void run() {
                                SplashActivity.this.next();
                            }
                        });
                    }

                    @Override 
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Headers headers = response.headers();
                            for (int i = 0; i < headers.size(); i++) {
                                PrintStream printStream = System.out;
                                printStream.println(headers.name(i) + ": " + headers.value(i));
                            }
                            String[] split = response.body().string().trim().trim().split(StringConstant.HASH);
                            int i2 = 0;
                            while (i2 < split.length) {
                                String[] strArr = split;
                                String trim = split[i2].split("\\$")[0].trim();
                                trim.hashCode();
                                char c = 65535;
                                switch (trim.hashCode()) {
                                    case -1987557315:
                                        if (trim.equals("banner_setting")) {
                                            c = 0;
                                            break;
                                        }
                                        break;
                                    case -1969509666:
                                        if (trim.equals("fullscreen_start")) {
                                            c = 1;
                                            break;
                                        }
                                        break;
                                    case -1968939963:
                                        if (trim.equals("fullscreen_theme")) {
                                            c = 2;
                                            break;
                                        }
                                        break;
                                    case -1907236478:
                                        if (trim.equals("banner_font")) {
                                            c = 3;
                                            break;
                                        }
                                        break;
                                    case -1810444868:
                                        if (trim.equals("banner_themeAct")) {
                                            c = 4;
                                            break;
                                        }
                                        break;
                                    case -1713974907:
                                        if (trim.equals("nativeid_language")) {
                                            c = 5;
                                            break;
                                        }
                                        break;
                                    case -1334467195:
                                        if (trim.equals("fullscreen_preload")) {
                                            c = 6;
                                            break;
                                        }
                                        break;
                                    case -968910846:
                                        if (trim.equals("nativeid_more")) {
                                            c = 7;
                                            break;
                                        }
                                        break;
                                    case -793139221:
                                        if (trim.equals("appopen")) {
                                            c = '\b';
                                            break;
                                        }
                                        break;
                                    case -479566253:
                                        if (trim.equals("fullscreen_font")) {
                                            c = '\t';
                                            break;
                                        }
                                        break;
                                    case 33779600:
                                        if (trim.equals("nativeid_setUp")) {
                                            c = '\n';
                                            break;
                                        }
                                        break;
                                    case 34209109:
                                        if (trim.equals("nativeid_start")) {
                                            c = 11;
                                            break;
                                        }
                                        break;
                                    case 107017432:
                                        if (trim.equals("pubid")) {
                                            c = '\f';
                                            break;
                                        }
                                        break;
                                    case 478880610:
                                        if (trim.equals("fullscreen_myPhoto")) {
                                            c = TokenParser.CR;
                                            break;
                                        }
                                        break;
                                    case 756879557:
                                        if (trim.equals("fullscreen_startback")) {
                                            c = 14;
                                            break;
                                        }
                                        break;
                                    case 952167887:
                                        if (trim.equals("ads_loading_flg")) {
                                            c = 15;
                                            break;
                                        }
                                        break;
                                    case 969950668:
                                        if (trim.equals("fullscreen_setting")) {
                                            c = 16;
                                            break;
                                        }
                                        break;
                                    case 1411752414:
                                        if (trim.equals("ads_loading_flgactivity")) {
                                            c = 17;
                                            break;
                                        }
                                        break;
                                    case 1816339923:
                                        if (trim.equals("banner_myPhoto")) {
                                            c = 18;
                                            break;
                                        }
                                        break;
                                }
                                switch (c) {
                                    case 0:
                                        SplashActivity.banner_setting = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 1:
                                        SplashActivity.fullscreen_start = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 2:
                                        SplashActivity.fullscreen_theme = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 3:
                                        SplashActivity.banner_font = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 4:
                                        SplashActivity.banner_themeAct = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 5:
                                        SplashActivity.nativeid_language = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 6:
                                        SplashActivity.fullscreen_preload = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 7:
                                        SplashActivity.nativeid_more = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case '\b':
                                        SplashActivity.appopen = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case '\t':
                                        SplashActivity.fullscreen_font = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case '\n':
                                        SplashActivity.nativeid_setUp = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 11:
                                        SplashActivity.nativeid_start = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case '\f':
                                        SplashActivity.pubid = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case '\r':
                                        SplashActivity.fullscreen_myPhoto = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 14:
                                        SplashActivity.fullscreen_startback = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 15:
                                        SplashActivity.ads_loading_flg = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 16:
                                        SplashActivity.fullscreen_setting = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 17:
                                        SplashActivity.ads_loading_flgactivity = strArr[i2].split("\\$")[1].trim();
                                        break;
                                    case 18:
                                        SplashActivity.banner_myPhoto = strArr[i2].split("\\$")[1].trim();
                                        break;
                                }
                                i2++;
                                split = strArr;
                            }
                            SplashActivity.this.myEdit.putString("ads_loading_flgactivity", SplashActivity.ads_loading_flgactivity);
                            SplashActivity.this.myEdit.putString("ads_loading_flg", SplashActivity.ads_loading_flg);
                            SplashActivity.this.myEdit.putString("banner_setting", SplashActivity.banner_setting);
                            SplashActivity.this.myEdit.putString("banner_font", SplashActivity.banner_font);
                            SplashActivity.this.myEdit.putString("banner_themeAct", SplashActivity.banner_themeAct);
                            SplashActivity.this.myEdit.putString("banner_myPhoto", SplashActivity.banner_myPhoto);
                            SplashActivity.this.myEdit.putString("fullscreen_preload", SplashActivity.fullscreen_preload);
                            SplashActivity.this.myEdit.putString("fullscreen_start", SplashActivity.fullscreen_start);
                            SplashActivity.this.myEdit.putString("fullscreen_startback", SplashActivity.fullscreen_startback);
                            SplashActivity.this.myEdit.putString("fullscreen_font", SplashActivity.fullscreen_font);
                            SplashActivity.this.myEdit.putString("fullscreen_myPhoto", SplashActivity.fullscreen_myPhoto);
                            SplashActivity.this.myEdit.putString("fullscreen_setting", SplashActivity.fullscreen_setting);
                            SplashActivity.this.myEdit.putString("fullscreen_theme", SplashActivity.fullscreen_theme);
                            SplashActivity.this.myEdit.putString("nativeid_start", SplashActivity.nativeid_start);
                            SplashActivity.this.myEdit.putString("nativeid_more", SplashActivity.nativeid_more);
                            SplashActivity.this.myEdit.putString("nativeid_setUp", SplashActivity.nativeid_setUp);
                            SplashActivity.this.myEdit.putString("nativeid_language", SplashActivity.nativeid_language);
                            SplashActivity.this.myEdit.putString("appopen", SplashActivity.appopen);
                            SplashActivity.this.myEdit.putString("pubid", SplashActivity.pubid);
                            SplashActivity.this.myEdit.commit();
                            SplashActivity.this.runOnUiThread(new Runnable() {
                                @Override 
                                public void run() {
                                    SplashActivity.this.next();
                                }
                            });
                            return;
                        }
                        throw new IOException("Unexpected code " + response);
                    }
                });
            } catch (Exception unused) {
                next();
            }
        } else {
            next();
        }
    }

    void next() {

        goToMain();

    }

    public void goToMain() {
        this.secondsRemaining = 0L;
        createTimer(600L);
    }

    private void createTimer(final long seconds) {
        new CountDownTimer(seconds * 1000, 1000L) { 
            @Override 
            public void onTick(long millisUntilFinished) {
                SplashActivity.this.secondsRemaining = (millisUntilFinished / 1000) + 1;

                SplashActivity.this.nextactivity();
                cancel();

            }

            @Override 
            public void onFinish() {

                SplashActivity.this.nextactivity();

            }
        }.start();

    }

    
    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    void setPref() {
        SharedPreferences.Editor edit = getSharedPreferences("consentpreff", 0).edit();
        edit.putBoolean("isDone", true);
        edit.apply();
    }

    boolean isConsentDone() {
        return getSharedPreferences("consentpreff", 0).getBoolean("isDone", false);
    }

    
    public void nextactivity() {
        Log.i("TAG", "nextactivity: ");
        Intent intent = new Intent(this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

package com.demo.example.neonkeyboard.ledkeyboard;

import android.app.Application;
import android.content.Context;

import com.tenor.android.core.network.ApiClient;
import com.tenor.android.core.network.ApiService;
import com.tenor.android.core.network.IApiClient;


public class MyApplication extends Application  {
    private static final String KEY = "LIVDSRZULELA";

    @Override 
    public void onCreate() {
        super.onCreate();
        ApiService.Builder builder = new ApiService.Builder(this, IApiClient.class);
        builder.apiKey(KEY);
        ApiClient.init(this, builder);
    }



}

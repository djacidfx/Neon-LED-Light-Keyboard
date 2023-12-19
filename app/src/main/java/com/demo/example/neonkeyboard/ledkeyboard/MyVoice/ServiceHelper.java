package com.demo.example.neonkeyboard.ledkeyboard.MyVoice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class ServiceHelper extends Service {
    private static final String TAG = "ServiceHelper";
    private final IBinder mBinder = new ServiceHelperBinder();
    private Callback mCallback;

    
    public interface Callback {
        void onResult(String recognitionResult);
    }

    @Override 
    public IBinder onBind(Intent arg0) {
        return this.mBinder;
    }

    @Override 
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "#onCreate");
    }

    @Override 
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "#onDestroy");
    }

    public void startRecognition(String languageLocale, Callback callback) {
        Log.i(TAG, "#startRecognition");
        this.mCallback = callback;
        Intent intent = new Intent(this, ActivityHelper.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void notifyResult(String recognitionResult) {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onResult(recognitionResult);
        }
    }

    
    public class ServiceHelperBinder extends Binder {
        public ServiceHelperBinder() {
        }

        
        public ServiceHelper getService() {
            return ServiceHelper.this;
        }
    }
}

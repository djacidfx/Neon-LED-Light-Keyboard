package com.demo.example.neonkeyboard.ledkeyboard.MyVoice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.InputMethodService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class VoiceRecognitionTrigger {
    private ImeTrigger mImeTrigger;
    private final InputMethodService mInputMethodService;
    private IntentApiTrigger mIntentApiTrigger;
    private BroadcastReceiver mReceiver;
    private Trigger mTrigger = getTrigger();


    public interface Listener {
        void onVoiceImeEnabledStatusChange();
    }

    public VoiceRecognitionTrigger(InputMethodService inputMethodService) {
        this.mInputMethodService = inputMethodService;
    }

    private Trigger getTrigger() {

//        if (SWRX_ImeTrigger.isInstalled(this.mInputMethodService)) {
//            return getImeTrigger();
//        }
//        if (SWRX_IntentApiTrigger.isInstalled(this.mInputMethodService)) {
//            return getIntentTrigger();
//        }


        return null;
    }

    private Trigger getIntentTrigger() {
        if (this.mIntentApiTrigger == null) {
            this.mIntentApiTrigger = new IntentApiTrigger(this.mInputMethodService);
        }
        return this.mIntentApiTrigger;
    }

    private Trigger getImeTrigger() {
        if (this.mImeTrigger == null) {
            this.mImeTrigger = new ImeTrigger(this.mInputMethodService);
        }
        return this.mImeTrigger;
    }

    public boolean isInstalled() {
        return this.mTrigger != null;
    }

    public boolean isEnabled() {
        return isNetworkAvailable();
    }

    public void startVoiceRecognition() {
        startVoiceRecognition(null);
    }

    public void startVoiceRecognition(String language) {
        Trigger trigger = this.mTrigger;
        if (trigger != null) {
            trigger.startVoiceRecognition(language);
        }
    }

    public void onStartInputView() {
        Trigger trigger = this.mTrigger;
        if (trigger != null) {
            trigger.onStartInputView();
        }
        this.mTrigger = getTrigger();
    }

    private boolean isNetworkAvailable() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mInputMethodService.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isConnected()) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException unused) {
            return true;
        }
    }

    public void register(final Listener listener) {
        this.mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    listener.onVoiceImeEnabledStatusChange();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.mInputMethodService.registerReceiver(this.mReceiver, intentFilter);
    }

    public void unregister(Context context) {
        BroadcastReceiver broadcastReceiver = this.mReceiver;
        if (broadcastReceiver != null) {
            this.mInputMethodService.unregisterReceiver(broadcastReceiver);
            this.mReceiver = null;
        }
    }
}

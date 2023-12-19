package com.demo.example.neonkeyboard.ledkeyboard.MyVoice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


public class ServiceBridge {
    private static final String TAG = "ServiceBridge";
    private final IntentApiTrigger.Callback mCallback;

    public ServiceBridge() {
        this(null);
    }

    public ServiceBridge(IntentApiTrigger.Callback callback) {
        this.mCallback = callback;
    }

    public void startVoiceRecognition(final Context context, final String languageCode) {
        final ConnectionRequest connectionRequest = new ConnectionRequest(languageCode);
        connectionRequest.setServiceCallback(new ServiceHelper.Callback() {
            @Override 
            public void onResult(final String recognitionResult) {
                ServiceBridge.this.mCallback.onRecognitionResult(recognitionResult);
                context.unbindService(connectionRequest);
            }
        });
        context.bindService(new Intent(context, ServiceHelper.class), connectionRequest, Context.BIND_AUTO_CREATE);
    }

    public void notifyResult(Context context, String recognitionResult) {
        context.bindService(new Intent(context, ServiceHelper.class), new ConnectionResponse(context, recognitionResult), Context.BIND_AUTO_CREATE);
    }

    
    private class ConnectionRequest implements ServiceConnection {
        private final String mLanguageCode;
        private ServiceHelper.Callback mServiceCallback;

        @Override 
        public void onServiceDisconnected(ComponentName className) {
        }

        private ConnectionRequest(String languageCode) {
            this.mLanguageCode = languageCode;
        }

        
        public void setServiceCallback(ServiceHelper.Callback callback) {
            this.mServiceCallback = callback;
        }

        @Override 
        public void onServiceConnected(ComponentName className, IBinder service) {
            ((ServiceHelper.ServiceHelperBinder) service).getService().startRecognition(this.mLanguageCode, this.mServiceCallback);
        }
    }

    
    private class ConnectionResponse implements ServiceConnection {
        private final Context mContext;
        private final String mRecognitionResult;

        @Override 
        public void onServiceDisconnected(ComponentName name) {
        }

        private ConnectionResponse(Context context, String recognitionResult) {
            this.mRecognitionResult = recognitionResult;
            this.mContext = context;
        }

        @Override 
        public void onServiceConnected(ComponentName name, IBinder service) {
            ((ServiceHelper.ServiceHelperBinder) service).getService().notifyResult(this.mRecognitionResult);
            this.mContext.unbindService(this);
        }
    }
}

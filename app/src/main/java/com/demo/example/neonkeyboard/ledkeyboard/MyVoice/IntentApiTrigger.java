package com.demo.example.neonkeyboard.ledkeyboard.MyVoice;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import com.tenor.android.core.constant.StringConstant;
import java.util.HashSet;
import java.util.Set;


class IntentApiTrigger implements Trigger {
    private static final String TAG = "VoiceIntentApiTrigger";
    private final InputMethodService mInputMethodService;
    private String mLastRecognitionResult;
    private IBinder mToken;
    private Set<Character> mUpperCaseChars;
    private final ServiceBridge mServiceBridge = new ServiceBridge(new Callback() {
        @Override 
        public void onRecognitionResult(String recognitionResult) {
            IntentApiTrigger.this.postResult(recognitionResult);
        }
    });
    private final Handler mHandler = new Handler();

    
    
    public interface Callback {
        void onRecognitionResult(String recognitionResult);
    }

    public IntentApiTrigger(InputMethodService inputMethodService) {
        this.mInputMethodService = inputMethodService;
        HashSet hashSet = new HashSet();
        this.mUpperCaseChars = hashSet;
        hashSet.add('.');
        this.mUpperCaseChars.add('!');
        this.mUpperCaseChars.add('?');
        this.mUpperCaseChars.add('\n');
    }

    @Override 
    public void startVoiceRecognition(String language) {
        this.mToken = this.mInputMethodService.getWindow().getWindow().getAttributes().token;
        this.mServiceBridge.startVoiceRecognition(this.mInputMethodService, language);
    }

    public static boolean isInstalled(InputMethodService inputMethodService) {
        return inputMethodService.getPackageManager().queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0).size() > 0;
    }

    private InputMethodManager getInputMethodManager() {
        return (InputMethodManager) this.mInputMethodService.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    
    public void postResult(String recognitionResult) {
        this.mLastRecognitionResult = recognitionResult;
        getInputMethodManager().showSoftInputFromInputMethod(this.mToken, 1);
    }

    @Override 
    public void onStartInputView() {
        Log.i(TAG, "#onStartInputView");
        if (this.mLastRecognitionResult != null) {
            scheduleCommit();
        }
    }

    private void scheduleCommit() {
        this.mHandler.post(new Runnable() { 
            @Override 
            public void run() {
                IntentApiTrigger.this.commitResult();
            }
        });
    }

    
    public void commitResult() {
        String str = this.mLastRecognitionResult;
        if (str != null) {
            InputConnection currentInputConnection = this.mInputMethodService.getCurrentInputConnection();
            if (currentInputConnection == null) {
                Log.i(TAG, "Unable to commit recognition result, as the current input connection is null. Did someone kill the IME?");
            } else if (!currentInputConnection.beginBatchEdit()) {
                Log.i(TAG, "Unable to commit recognition result, as a batch edit cannot start");
            } else {
                try {
                    ExtractedTextRequest extractedTextRequest = new ExtractedTextRequest();
                    extractedTextRequest.flags = 1;
                    ExtractedText extractedText = currentInputConnection.getExtractedText(extractedTextRequest, 0);
                    if (extractedText == null) {
                        Log.i(TAG, "Unable to commit recognition result, as extracted text is null");
                        return;
                    }
                    if (extractedText.text != null) {
                        if (extractedText.selectionStart != extractedText.selectionEnd) {
                            currentInputConnection.deleteSurroundingText(extractedText.selectionStart, extractedText.selectionEnd);
                        }
                        str = format(extractedText, str);
                    }
                    if (!currentInputConnection.commitText(str, 0)) {
                        Log.i(TAG, "Unable to commit recognition result");
                    } else {
                        this.mLastRecognitionResult = null;
                    }
                } finally {
                    currentInputConnection.endBatchEdit();
                }
            }
        }
    }

    private String format(ExtractedText et, String result) {
        int i = et.selectionStart - 1;
        while (i > 0 && Character.isWhitespace(et.text.charAt(i))) {
            i--;
        }
        if (i == -1 || this.mUpperCaseChars.contains(Character.valueOf(et.text.charAt(i)))) {
            result = Character.toUpperCase(result.charAt(0)) + result.substring(1);
        }
        if (et.selectionStart - 1 > 0 && !Character.isWhitespace(et.text.charAt(et.selectionStart - 1))) {
            result = StringConstant.SPACE + result;
        }
        if (et.selectionEnd >= et.text.length() || Character.isWhitespace(et.text.charAt(et.selectionEnd))) {
            return result;
        }
        return result + StringConstant.SPACE;
    }
}

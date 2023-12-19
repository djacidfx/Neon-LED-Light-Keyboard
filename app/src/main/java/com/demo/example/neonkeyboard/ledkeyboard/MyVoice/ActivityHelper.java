package com.demo.example.neonkeyboard.ledkeyboard.MyVoice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;


public class ActivityHelper extends Activity {
    private static final int RECOGNITION_REQUEST = 1;
    private static final String TAG = "ActivityHelper";
    private ServiceBridge mServiceBridge;

    @Override 
    protected void onCreate(Bundle bundle) {
        String string;
        super.onCreate(bundle);
        this.mServiceBridge = new ServiceBridge();
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("calling_package", getClass().getPackage().getName());
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.MAX_RESULTS", 5);
        if (!(bundle == null || (string = bundle.getString("android.speech.extra.LANGUAGE")) == null)) {
            intent.putExtra("android.speech.extra.LANGUAGE", string);
        }
        startActivityForResult(intent, 1);
    }

    @Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1 || data == null || !data.hasExtra("android.speech.extra.RESULTS")) {
            notifyResult(null);
            return;
        }
        ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra("android.speech.extra.RESULTS");
        createResultDialog((String[]) stringArrayListExtra.toArray(new String[stringArrayListExtra.size()])).show();
    }

    private Dialog createResultDialog(final String[] recognitionResults) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 16973937);
        builder.setItems(recognitionResults, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialog, int which) {
                ActivityHelper.this.notifyResult(recognitionResults[which]);
            }
        });
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { 
            @Override 
            public void onCancel(DialogInterface dialog) {
                ActivityHelper.this.notifyResult(null);
            }
        });
        builder.setNeutralButton(17039360, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialog, int which) {
                ActivityHelper.this.notifyResult(null);
            }
        });
        return builder.create();
    }

    
    public void notifyResult(String result) {
        this.mServiceBridge.notifyResult(this, result);
        finish();
    }
}

package com.demo.example.neonkeyboard.ledkeyboard.MyVoice;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import java.util.List;


class ImeTrigger implements Trigger {
    private static final String VOICE_IME_PACKAGE_PREFIX = "com.google.android";
    private static final String VOICE_IME_SUBTYPE_MODE = "voice";
    private final InputMethodService mInputMethodService;

    @Override 
    public void onStartInputView() {
    }

    public ImeTrigger(InputMethodService inputMethodService) {
        this.mInputMethodService = inputMethodService;
    }

    @Override 
    public void startVoiceRecognition(String language) {
        InputMethodManager inputMethodManager = getInputMethodManager(this.mInputMethodService);
        InputMethodInfo voiceImeInputMethodInfo = getVoiceImeInputMethodInfo(inputMethodManager);
        if (voiceImeInputMethodInfo != null) {
            inputMethodManager.setInputMethodAndSubtype(this.mInputMethodService.getWindow().getWindow().getAttributes().token, voiceImeInputMethodInfo.getId(), getVoiceImeSubtype(inputMethodManager, voiceImeInputMethodInfo));
        }
    }

    private static InputMethodManager getInputMethodManager(InputMethodService inputMethodService) {
        return (InputMethodManager) inputMethodService.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private InputMethodSubtype getVoiceImeSubtype(InputMethodManager inputMethodManager, InputMethodInfo inputMethodInfo) throws SecurityException, IllegalArgumentException {
        List<InputMethodSubtype> list = inputMethodManager.getShortcutInputMethodsAndSubtypes().get(inputMethodInfo);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    private static InputMethodInfo getVoiceImeInputMethodInfo(InputMethodManager inputMethodManager) throws SecurityException, IllegalArgumentException {
        for (InputMethodInfo inputMethodInfo : inputMethodManager.getEnabledInputMethodList()) {
            for (int i = 0; i < inputMethodInfo.getSubtypeCount(); i++) {
                if (VOICE_IME_SUBTYPE_MODE.equals(inputMethodInfo.getSubtypeAt(i).getMode()) && inputMethodInfo.getComponent().getPackageName().startsWith(VOICE_IME_PACKAGE_PREFIX)) {
                    return inputMethodInfo;
                }
            }
        }
        return null;
    }

    public static boolean isInstalled(InputMethodService inputMethodService) {
        InputMethodInfo voiceImeInputMethodInfo;
        return Build.VERSION.SDK_INT >= 14 && (voiceImeInputMethodInfo = getVoiceImeInputMethodInfo(getInputMethodManager(inputMethodService))) != null && voiceImeInputMethodInfo.getSubtypeCount() > 0;
    }
}

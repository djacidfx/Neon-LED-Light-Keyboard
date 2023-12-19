package com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.Constan.Utils;
import com.demo.example.neonkeyboard.ledkeyboard.Emogies.EmojiPopup;
import com.demo.example.neonkeyboard.ledkeyboard.MyVoice.VoiceRecognitionTrigger;
import com.demo.example.neonkeyboard.ledkeyboard.FontActivity;
import com.demo.example.neonkeyboard.ledkeyboard.LanguageActivity;
import com.demo.example.neonkeyboard.ledkeyboard.MainActivity;
import com.demo.example.neonkeyboard.ledkeyboard.SettingActivity;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;
import com.tenor.android.core.constant.StringConstant;

import java.io.File;
import java.lang.Character;
import java.util.ArrayList;
import java.util.List;


public class SoftKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener, SpellCheckerSession.SpellCheckerSessionListener {
    private static final int CANDIDATES_LIMIT = 3;
    public static SoftKeyboard Instance = null;
    private static final boolean PROCESS_HARD_KEYS = true;
    public static boolean SettingsDPWPermissionGranted = true;
    public static int SettingsFontSize = 5;
    private static String SettingsLanguage = "";
    public static int SettingsOpacity = 255;
    public static int SettingsRoundness = 4;
    public static boolean SettingsVibration = false;
    public static final int VIBRATE_DURATION = 15;
    public static int fontNumber = 0;
    public static int inputLangvg = 0;
    public static boolean isMyFont = false;
    public static int keyboardSize = 100;
    private static AudioManager mAudioManager = null;
    public static ConnectivityManager mConnectivityManager = null;
    private static boolean mSymbolTyped = false;
    public static Vibrator mVibrator = null;
    public static boolean settingsPopup = false;
    public static boolean settingsPrediction;
    public static int themeNumber;
    public EditText et_search_gif;
    private CandidateView mCandidateView;
    public boolean mCapsLock;
    private CompletionInfo[] mCompletions;
    public LatinKeyboard mCurKeyboard;
    private int mCurKeyboardLangRes;
    private InputMethodInfo mInputMethodInfo;
    private InputMethodManager mInputMethodManager;
    private int mLastDisplayWidth;
    private long mLastShiftTime;
    private long mMetaState;
    private LatinKeyboard mNumericKeyboard;
    public LatinKeyboard mQwertyKeyboard;
    public LatinKeyboard mQwertyShifter;
    private SpellCheckerSession mScs;
    private List<String> mSuggestions;
    public LatinKeyboard mSymbolsKeyboard;
    public LatinKeyboard mSymbolsShiftedKeyboard;
    VoiceRecognitionTrigger mVoiceRecognitionTrigger;
    private String mWordSeparators;
    public RelativeLayout r_search_giphy;
    SharedPreferences sp;
    private String mCurPackageName = "";
    public EmojiPopup mEmojiPopup = null;
    public LatinKeyboardView mInputView = null;
    private LinearLayout mKeyboardBarContentLayout = null;
    private View mWrapperView = null;
    public MediaPlayer mediaPlayer = new MediaPlayer();
    public final StringBuilder mComposing = new StringBuilder();

    @Override 
    public View onCreateCandidatesView() {
        return null;
    }

    @Override 
    public void onDisplayCompletions(CompletionInfo[] completionInfoArr) {
    }

    @Override 
    public void swipeLeft() {
    }

    @Override 
    public void swipeUp() {
    }

    @Override 
    public void onCreate() {
        Instance = this;
        Utils.load(this);
        super.onCreate();
        this.mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mWordSeparators = getResources().getString(R.string.word_separators);
        this.mScs = ((TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE)).newSpellCheckerSession(null, null, this, PROCESS_HARD_KEYS);
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        VoiceRecognitionTrigger sWRX_VoiceRecognitionTrigger = new VoiceRecognitionTrigger(this);
        this.mVoiceRecognitionTrigger = sWRX_VoiceRecognitionTrigger;
        sWRX_VoiceRecognitionTrigger.register(new VoiceRecognitionTrigger.Listener() {
            @Override
            
            public void onVoiceImeEnabledStatusChange() {
                SoftKeyboard.this.updateVoiceImeStatus();
            }
        });
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        this.sp = defaultSharedPreferences;
        keyboardSize = defaultSharedPreferences.getInt("keyboardSize", 100);
    }

    @Override 
    public void onInitializeInterface() {
        if (this.mQwertyKeyboard != null) {
            int maxWidth = getMaxWidth();
            if (maxWidth != this.mLastDisplayWidth) {
                this.mLastDisplayWidth = maxWidth;
            } else {
                return;
            }
        }
        SettingsLanguage = getActiveLanguage();
        int i = inputLangvg;
        if (i == 1) {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_hindi;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_hindi_shifter, keyboardSize / 100.0f);
        } else if (i == 2) {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_gujarati;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_gujarati_shifter, keyboardSize / 100.0f);
        } else {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_us;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_gujarati_shifter, keyboardSize / 100.0f);
        }
        this.mQwertyKeyboard = new LatinKeyboard(this, this.mCurKeyboardLangRes, keyboardSize / 100.0f);
        this.mSymbolsKeyboard = new LatinKeyboard(this, R.xml.keyboard_symbols, keyboardSize / 100.0f);
        this.mSymbolsShiftedKeyboard = new LatinKeyboard(this, R.xml.keyboard_symbols_shift, keyboardSize / 100.0f);
        this.mNumericKeyboard = new LatinKeyboard(this, R.xml.keyboard_numeric, keyboardSize / 100.0f);
    }

    @Override
    
    public void onDestroy() {
        VoiceRecognitionTrigger sWRX_VoiceRecognitionTrigger = this.mVoiceRecognitionTrigger;
        if (sWRX_VoiceRecognitionTrigger != null) {
            sWRX_VoiceRecognitionTrigger.unregister(this);
        }
        super.onDestroy();
    }

    
    public void updateVoiceImeStatus() {
        if (this.mVoiceRecognitionTrigger.isInstalled()) {
            this.mVoiceRecognitionTrigger.isEnabled();
        }
    }

    public void deleteAllGiphy(File file) {
        try {
            if (file.isDirectory()) {
                for (String str : file.list()) {
                    new File(file, str).delete();
                }
            }
        } catch (NullPointerException unused) {
        }
    }

    private View createInputView() {
        View inflate = getLayoutInflater().inflate(R.layout.layout_keyboard, (ViewGroup) null);
        this.mWrapperView = inflate;
        LatinKeyboardView latinKeyboardView = (LatinKeyboardView) inflate.findViewById(R.id.layoutKeyboard).findViewById(R.id.viewKeyboard);
        this.mInputView = latinKeyboardView;
        latinKeyboardView.setOnKeyboardActionListener(this);
        this.mInputView.setPreviewEnabled(false);
        this.mQwertyKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
        this.mInputView.setKeyboard(this.mCurKeyboard);
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            deleteAllGiphy(Utils.getGifStorageDir());
        }
        this.mEmojiPopup = new EmojiPopup(this.mWrapperView, this);
        this.mKeyboardBarContentLayout = (LinearLayout) this.mWrapperView.findViewById(R.id.layoutKeyboardBarContent);
        View findViewById = this.mWrapperView.findViewById(R.id.img_keyboard_setting_poup);
        ImageView imageView = (ImageView) this.mWrapperView.findViewById(R.id.img_voice);

        ((ImageView) this.mWrapperView.findViewById(R.id.img_setting)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                if (SoftKeyboard.SettingsDPWPermissionGranted) {
                    Intent intent = new Intent(SoftKeyboard.this, SettingActivity.class);
                    intent.putExtra("iAmFromInside", false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SoftKeyboard.this.startActivity(intent);
                    return;
                }
                Toast.makeText(SoftKeyboard.this, "You need to grant permission in Neon Keyboard to open on Xiaomi device.", Toast.LENGTH_LONG).show();
            }
        });
        ((ImageView) this.mWrapperView.findViewById(R.id.img_gotofont)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                if (SoftKeyboard.SettingsDPWPermissionGranted) {
                    Intent intent = new Intent(SoftKeyboard.this, FontActivity.class);
                    intent.putExtra("iAmFromInside", false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SoftKeyboard.this.startActivity(intent);
                    return;
                }
                Toast.makeText(SoftKeyboard.this, "You need to grant permission in Neon Keyboard to open on Xiaomi device.", Toast.LENGTH_LONG).show();
            }
        });
        ((ImageView) this.mWrapperView.findViewById(R.id.img_gotolanguage)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                if (SoftKeyboard.SettingsDPWPermissionGranted) {
                    Intent intent = new Intent(SoftKeyboard.this, LanguageActivity.class);
                    intent.putExtra("iAmFromInside", false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SoftKeyboard.this.startActivity(intent);
                    return;
                }
                Toast.makeText(SoftKeyboard.this, "You need to grant permission in Neon Keyboard to open on Xiaomi device.", Toast.LENGTH_LONG).show();
            }
        });
        this.r_search_giphy = (RelativeLayout) this.mWrapperView.findViewById(R.id.r_search_giphy);
        ImageView imageView2 = (ImageView) this.mWrapperView.findViewById(R.id.close_search_giphy);
        ImageView imageView3 = (ImageView) this.mWrapperView.findViewById(R.id.start_search_giphy);
        EditText editText = (EditText) this.mWrapperView.findViewById(R.id.et_search_gif);
        this.et_search_gif = editText;
        if (editText != null) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { 
                @Override 
                public void onFocusChange(View view, boolean z) {
                    if (!z) {
                        SoftKeyboard.this.et_search_gif.getText().clear();
                        SoftKeyboard.this.et_search_gif.clearFocus();
                    }
                }
            });
        }
        HelperResize.setSize(this.r_search_giphy, 1044, 108, false);
        HelperResize.setSize(imageView2, 45, 38, PROCESS_HARD_KEYS);
        HelperResize.setSize(imageView3, 47, 47, PROCESS_HARD_KEYS);
        imageView3.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                String obj = SoftKeyboard.this.et_search_gif.getText().toString();
                if (TextUtils.isEmpty(obj)) {
                    return;
                }
                Toast.makeText(SoftKeyboard.this, "Enter two or more character!", Toast.LENGTH_SHORT).show();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                SoftKeyboard.this.r_search_giphy.setVisibility(View.GONE);
            }
        });
        if (this.mVoiceRecognitionTrigger.isInstalled()) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                SoftKeyboard.this.mVoiceRecognitionTrigger.startVoiceRecognition();
            }
        });
        final LinearLayout linearLayout = (LinearLayout) this.mWrapperView.findViewById(R.id.l_allsetings);
        findViewById.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (!SoftKeyboard.SettingsDPWPermissionGranted) {
                    Toast.makeText(SoftKeyboard.this, "You need to grant permission in RGB Keyboard application to do this action on Xiaomi device.", Toast.LENGTH_LONG).show();
                } else if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.setVisibility(View.GONE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        return this.mWrapperView;
    }

    @Override 
    public View onCreateInputView() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        this.sp = defaultSharedPreferences;
        themeNumber = defaultSharedPreferences.getInt("wallpaper_ind", 3);
        SettingsFontSize = this.sp.getInt("font_size", 5);
        keyboardSize = this.sp.getInt("keyboardSize", 100);
        SettingsRoundness = this.sp.getInt("roundness", 4);
        SettingsOpacity = this.sp.getInt("opacity", 255);
        fontNumber = this.sp.getInt("fontNumber", 1);
        inputLangvg = this.sp.getInt("inputLangvg", 0);
        isMyFont = this.sp.getBoolean("isMyFont", false);
        return createInputView();
    }

    Context getDisplayContext() {
        return createDisplayContext(((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay());
    }

    @Override 
    public void onComputeInsets(Insets insets) {
        super.onComputeInsets(insets);
        if (!isFullscreenMode()) {
            insets.contentTopInsets = insets.visibleTopInsets;
        }
    }

    @Override 
    public void onStartInput(EditorInfo editorInfo, boolean restarting) {
        super.onStartInput(editorInfo, restarting);
        this.mInputMethodInfo = null;
        List<InputMethodInfo> inputMethodList = this.mInputMethodManager.getInputMethodList();
        int i = 0;
        while (true) {
            if (i >= inputMethodList.size()) {
                break;
            }
            InputMethodInfo inputMethodInfo = inputMethodList.get(i);
            if (inputMethodInfo.getPackageName().equals(Instance.getPackageName())) {
                this.mInputMethodInfo = inputMethodInfo;
                break;
            }
            i++;
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        this.sp = defaultSharedPreferences;
        settingsPrediction = defaultSharedPreferences.getBoolean("prediction", PROCESS_HARD_KEYS);
        settingsPopup = this.sp.getBoolean("isPopup", PROCESS_HARD_KEYS);
        isMyFont = this.sp.getBoolean("isMyFont", false);
        SettingsVibration = this.sp.getBoolean("vibration", false);
        SettingsDPWPermissionGranted = this.sp.getBoolean("dpw_permission_granted", PROCESS_HARD_KEYS);
        SettingsLanguage = getActiveLanguage();
        themeNumber = this.sp.getInt("wallpaper_ind", 3);
        SettingsFontSize = this.sp.getInt("font_size", 5);
        SettingsRoundness = this.sp.getInt("roundness", 4);
        SettingsOpacity = this.sp.getInt("opacity", 255);
        fontNumber = this.sp.getInt("fontNumber", 1);
        inputLangvg = this.sp.getInt("inputLangvg", 0);
        keyboardSize = this.sp.getInt("keyboardSize", 100);
        Log.d("Dd", "onddStartInput: " + (keyboardSize / 100.0f));
        VoiceRecognitionTrigger sWRX_VoiceRecognitionTrigger = this.mVoiceRecognitionTrigger;
        if (sWRX_VoiceRecognitionTrigger != null) {
            sWRX_VoiceRecognitionTrigger.onStartInputView();
        }
        this.mCurPackageName = editorInfo.packageName;
        this.mComposing.setLength(0);
        updateCandidates();
        if (!restarting) {
            this.mMetaState = 0L;
        }
        int i2 = inputLangvg;
        if (i2 == 1) {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_hindi;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_hindi_shifter, keyboardSize / 100.0f);
        } else if (i2 == 2) {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_gujarati;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_gujarati_shifter, keyboardSize / 100.0f);
        } else {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_us;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_gujarati_shifter, keyboardSize / 100.0f);
        }
        this.mQwertyKeyboard = new LatinKeyboard(this, this.mCurKeyboardLangRes, keyboardSize / 100.0f);
        this.mSymbolsKeyboard = new LatinKeyboard(this, R.xml.keyboard_symbols, keyboardSize / 100.0f);
        this.mSymbolsShiftedKeyboard = new LatinKeyboard(this, R.xml.keyboard_symbols_shift, keyboardSize / 100.0f);
        this.mNumericKeyboard = new LatinKeyboard(this, R.xml.keyboard_numeric, keyboardSize / 100.0f);
        int i3 = editorInfo.inputType & 15;
        if (i3 == 1) {
            this.mCurKeyboard = this.mQwertyKeyboard;
            if ((editorInfo.inputType & 4080) != 32) {
            }
            int i4 = editorInfo.inputType;
            updateShiftKeyState();
        } else if (i3 == 2) {
            this.mCurKeyboard = this.mNumericKeyboard;
        } else if (i3 == 3) {
            this.mCurKeyboard = this.mNumericKeyboard;
        } else if (i3 != 4) {
            this.mCurKeyboard = this.mQwertyKeyboard;
            updateShiftKeyState();
        } else {
            this.mCurKeyboard = this.mSymbolsKeyboard;
        }
        this.mCurKeyboard.setImeOptions(getResources(), editorInfo.imeOptions);
        setInputView(createInputView());
    }

    private String getActiveLanguage() {
        InputMethodManager inputMethodManager;
        InputMethodSubtype currentInputMethodSubtype;
        return (Instance == null || (inputMethodManager = this.mInputMethodManager) == null || this.mInputMethodInfo == null || (currentInputMethodSubtype = inputMethodManager.getCurrentInputMethodSubtype()) == null) ? "" : currentInputMethodSubtype.getDisplayName(Instance, this.mInputMethodInfo.getPackageName(), this.mInputMethodInfo.getServiceInfo().applicationInfo).toString();
    }

    @Override 
    public void onFinishInput() {
        super.onFinishInput();
        this.mComposing.setLength(0);
        updateCandidates();
        this.mCurKeyboard = this.mQwertyKeyboard;
        LatinKeyboardView latinKeyboardView = this.mInputView;
        if (latinKeyboardView != null) {
            latinKeyboardView.closing();
        }
        EmojiPopup sWRX_EmojiPopup = this.mEmojiPopup;
        if (sWRX_EmojiPopup != null) {
            sWRX_EmojiPopup.dismiss();
        }

        SpellCheckerSession spellCheckerSession = this.mScs;
        if (spellCheckerSession != null) {
            spellCheckerSession.close();
            this.mScs = null;
        }
    }

    @Override 
    public void onStartInputView(EditorInfo editorInfo, boolean z) {
        super.onStartInputView(editorInfo, z);
        SettingsDPWPermissionGranted = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("dpw_permission_granted", PROCESS_HARD_KEYS);
        this.mCurKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
        this.mInputView.setKeyboard(this.mCurKeyboard);
        this.mInputView.loadBackground();
        this.mInputView.closing();
        this.mInputView.invalidateAllKeys();
        this.mCurPackageName = editorInfo.packageName;
    }

    @Override 
    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype inputMethodSubtype) {
        SettingsLanguage = getActiveLanguage();
        int i = inputLangvg;
        if (i == 1) {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_hindi;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_hindi_shifter, keyboardSize / 100.0f);
        } else if (i == 2) {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_gujarati;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_gujarati_shifter, keyboardSize / 100.0f);
        } else {
            this.mCurKeyboardLangRes = R.xml.keyboard_qwerty_us;
            this.mQwertyShifter = new LatinKeyboard(this, R.xml.keyboard_gujarati_shifter, keyboardSize / 100.0f);
        }
        LatinKeyboard latinKeyboard = new LatinKeyboard(this, this.mCurKeyboardLangRes, keyboardSize / 100.0f);
        this.mQwertyKeyboard = latinKeyboard;
        this.mCurKeyboard = latinKeyboard;
        setInputView(createInputView());
        LatinKeyboardView latinKeyboardView = this.mInputView;
        if (latinKeyboardView != null) {
            latinKeyboardView.invalidateAllKeys();
        }
    }

    @Override 
    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        if (this.mComposing.length() > 0) {
            if (newSelStart != candidatesEnd || newSelEnd != candidatesEnd) {
                this.mComposing.setLength(0);
                updateCandidates();
                InputConnection currentInputConnection = getCurrentInputConnection();
                if (currentInputConnection != null) {
                    currentInputConnection.finishComposingText();
                }
            }
        }
    }

    @Override 
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        LatinKeyboardView latinKeyboardView;
        if (i != 4) {
            if (i == 66) {
                return false;
            }
            if (i == 67 && this.mComposing.length() > 0) {
                onKey(-5, null);
                return PROCESS_HARD_KEYS;
            }
        } else if (keyEvent.getRepeatCount() == 0 && (latinKeyboardView = this.mInputView) != null) {
            if (latinKeyboardView.handleBack()) {
                return PROCESS_HARD_KEYS;
            }
            EmojiPopup sWRX_EmojiPopup = this.mEmojiPopup;
            if (sWRX_EmojiPopup != null) {
                sWRX_EmojiPopup.dismiss();
            }

        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override 
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (settingsPrediction) {
            this.mMetaState = MetaKeyKeyListener.handleKeyUp(this.mMetaState, i, keyEvent);
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void updateShiftKeyState() {
        LatinKeyboardView latinKeyboardView;
        InputConnection currentInputConnection;
        EditorInfo currentInputEditorInfo = getCurrentInputEditorInfo();
        if (currentInputEditorInfo != null && (latinKeyboardView = this.mInputView) != null && this.mQwertyKeyboard == latinKeyboardView.getKeyboard()) {
            EditorInfo currentInputEditorInfo2 = getCurrentInputEditorInfo();
            boolean z = false;
            if (currentInputEditorInfo2 != null && currentInputEditorInfo2.inputType != 0 && (currentInputConnection = getCurrentInputConnection()) != null) {
                int cursorCapsMode = currentInputConnection.getCursorCapsMode(currentInputEditorInfo.inputType);
                LatinKeyboardView latinKeyboardView2 = this.mInputView;
                if (this.mCapsLock || cursorCapsMode != 0) {
                    z = PROCESS_HARD_KEYS;
                }
                latinKeyboardView2.setShifted(z);
            }
        }
    }

    @Override 
    public void onPress(int i) {
        LatinKeyboardView latinKeyboardView = this.mInputView;
        if (latinKeyboardView != null && i != -5 && i != -1 && i != -101 && i != -100 && i != -2 && i != 10 && i != 32) {
            latinKeyboardView.setPreviewEnabled(settingsPopup);
        }
    }

    @Override 
    public void onRelease(int i) {
        LatinKeyboardView latinKeyboardView = this.mInputView;
        if (latinKeyboardView != null && i != -5 && i != -1 && i != -101 && i != -100 && i != -2 && i != 10 && i != 32) {
            latinKeyboardView.setPreviewEnabled(false);
        }
    }

    public boolean isPrintableChar(char c) {
        Character.UnicodeBlock of = Character.UnicodeBlock.of(c);
        if (Character.isISOControl(c) || of == null || of == Character.UnicodeBlock.SPECIALS || c == 65435) {
            return false;
        }
        return PROCESS_HARD_KEYS;
    }

    @Override 
    public void onKey(int i, int[] iArr) {
        int i2 = i;
        playClick(i);
        try {
            ((LinearLayout) this.mWrapperView.findViewById(R.id.l_allsetings)).setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e("TAG", "onKey: " + e.getMessage());
        }
        InputConnection currentInputConnection = getCurrentInputConnection();
        this.et_search_gif.hasFocus();
        boolean equals = this.mCurPackageName.equals(this.getPackageName());
        boolean z = PROCESS_HARD_KEYS;
        if (!equals) {
            char c = (char) i2;
            if (i2 == 10) {
                currentInputConnection.sendKeyEvent(new KeyEvent(0, 66));
                currentInputConnection.sendKeyEvent(new KeyEvent(1, 66));
            }
            if (this.mWordSeparators.contains(String.valueOf(c))) {
                if (this.mComposing.length() > 0) {
                    StringBuilder sb = this.mComposing;
                    currentInputConnection.commitText(sb, sb.length());
                    this.mComposing.setLength(0);
                    updateCandidates();
                }
                if (i2 == -4) {
                    currentInputConnection.sendKeyEvent(new KeyEvent(0, 66));
                } else if (i2 < 48 || i2 > 57) {
                    currentInputConnection.commitText(String.valueOf(c), 1);
                    if (i2 != 32) {
                        Keyboard keyboard = this.mInputView.getKeyboard();
                        if (!(keyboard == this.mSymbolsKeyboard || keyboard == this.mSymbolsShiftedKeyboard)) {
                            z = false;
                        }
                        mSymbolTyped = z;
                    } else if (mSymbolTyped) {
                        Keyboard keyboard2 = this.mInputView.getKeyboard();
                        if (keyboard2 == this.mSymbolsKeyboard || keyboard2 == this.mSymbolsShiftedKeyboard) {
                            this.mQwertyKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mQwertyKeyboard);
                        }
                        mSymbolTyped = false;
                    }
                } else {
                    int i3 = (i2 - 48) + 7;
                    currentInputConnection.sendKeyEvent(new KeyEvent(0, i3));
                    currentInputConnection.sendKeyEvent(new KeyEvent(1, i3));
                }
                updateShiftKeyState();
            } else if (i2 == -5) {
                if (this.mComposing.length() > 1) {
                    StringBuilder sb2 = this.mComposing;
                    sb2.delete(sb2.length() - 1, this.mComposing.length());
                    currentInputConnection.setComposingText(this.mComposing, 1);
                    updateCandidates();
                } else if (this.mComposing.length() > 0) {
                    this.mComposing.setLength(0);
                    currentInputConnection.commitText("", 0);
                    updateCandidates();
                } else {
                    currentInputConnection.sendKeyEvent(new KeyEvent(0, 67));
                    currentInputConnection.sendKeyEvent(new KeyEvent(1, 67));
                }
                updateShiftKeyState();
            } else if (i2 == -1) {
                LatinKeyboardView latinKeyboardView = this.mInputView;
                if (latinKeyboardView != null) {
                    Keyboard keyboard3 = latinKeyboardView.getKeyboard();
                    int i4 = inputLangvg;
                    if (i4 == 1 || i4 == 2) {
                        LatinKeyboard latinKeyboard = this.mQwertyKeyboard;
                        if (keyboard3 == latinKeyboard) {
                            this.mQwertyShifter.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mQwertyShifter);
                        } else if (keyboard3 == this.mQwertyShifter) {
                            latinKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mQwertyKeyboard);
                        } else {
                            LatinKeyboard latinKeyboard2 = this.mSymbolsKeyboard;
                            if (keyboard3 == latinKeyboard2) {
                                latinKeyboard2.setShifted(PROCESS_HARD_KEYS);
                                this.mSymbolsShiftedKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                                this.mInputView.setKeyboard(this.mSymbolsShiftedKeyboard);
                                this.mSymbolsShiftedKeyboard.setShifted(PROCESS_HARD_KEYS);
                                return;
                            }
                            LatinKeyboard latinKeyboard3 = this.mSymbolsShiftedKeyboard;
                            if (keyboard3 == latinKeyboard3) {
                                latinKeyboard3.setShifted(false);
                                this.mSymbolsKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                                this.mInputView.setKeyboard(this.mSymbolsKeyboard);
                                this.mSymbolsKeyboard.setShifted(false);
                            }
                        }
                    } else if (this.mQwertyKeyboard == keyboard3) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (this.mLastShiftTime + 800 > currentTimeMillis) {
                            this.mCapsLock ^= PROCESS_HARD_KEYS;
                            this.mLastShiftTime = 0L;
                        } else {
                            this.mLastShiftTime = currentTimeMillis;
                        }
                        LatinKeyboardView latinKeyboardView2 = this.mInputView;
                        if (!this.mCapsLock && latinKeyboardView2.isShifted()) {
                            z = false;
                        }
                        latinKeyboardView2.setShifted(z);
                    } else {
                        LatinKeyboard latinKeyboard4 = this.mSymbolsKeyboard;
                        if (keyboard3 == latinKeyboard4) {
                            latinKeyboard4.setShifted(PROCESS_HARD_KEYS);
                            this.mSymbolsShiftedKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mSymbolsShiftedKeyboard);
                            this.mSymbolsShiftedKeyboard.setShifted(PROCESS_HARD_KEYS);
                            return;
                        }
                        LatinKeyboard latinKeyboard5 = this.mSymbolsShiftedKeyboard;
                        if (keyboard3 == latinKeyboard5) {
                            latinKeyboard5.setShifted(false);
                            this.mSymbolsKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mSymbolsKeyboard);
                            this.mSymbolsKeyboard.setShifted(false);
                        }
                    }
                }
            } else if (i2 == -101) {
                EmojiPopup sWRX_EmojiPopup = this.mEmojiPopup;
                if (sWRX_EmojiPopup != null) {
                    sWRX_EmojiPopup.setWidth(-1);
                    this.mEmojiPopup.setHeight(this.mCurKeyboard.getHeight());
                    this.mEmojiPopup.showAtBottom();
                }
            } else if (i2 == -2) {
                LatinKeyboardView latinKeyboardView3 = this.mInputView;
                if (latinKeyboardView3 != null) {
                    Keyboard keyboard4 = latinKeyboardView3.getKeyboard();
                    LatinKeyboard latinKeyboard6 = this.mSymbolsKeyboard;
                    if (keyboard4 == latinKeyboard6 || keyboard4 == this.mSymbolsShiftedKeyboard) {
                        this.mQwertyKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                        this.mInputView.setKeyboard(this.mQwertyKeyboard);
                        return;
                    }
                    latinKeyboard6.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                    this.mInputView.setKeyboard(this.mSymbolsKeyboard);
                    this.mSymbolsKeyboard.setShifted(false);
                }
            } else if (i2 != -100) {
                if (isInputViewShown() && this.mInputView.isShifted()) {
                    i2 = Character.toUpperCase(i);
                }
                if (settingsPrediction) {
                    this.mComposing.append((char) i2);
                    currentInputConnection.setComposingText(this.mComposing, 1);
                    Log.d("dd", "onGetSentenceSuggestddions: " + ((Object) this.mComposing));
                    updateShiftKeyState();
                    updateCandidates();
                } else {
                    currentInputConnection.commitText(String.valueOf((char) i2), 1);
                    updateShiftKeyState();
                }
                Keyboard keyboard5 = this.mInputView.getKeyboard();
                if (!(keyboard5 == this.mSymbolsKeyboard || keyboard5 == this.mSymbolsShiftedKeyboard)) {
                    z = false;
                }
                mSymbolTyped = z;
            }
        } else {
            if (this.et_search_gif.hasFocus()) {
                char c2 = (char) i2;
                if (i2 == -5) {
                    this.et_search_gif.dispatchKeyEvent(new KeyEvent(0, 67));
                    this.et_search_gif.dispatchKeyEvent(new KeyEvent(1, 67));
                } else if (isPrintableChar(c2)) {
                    int max = Math.max(this.et_search_gif.getSelectionStart(), 0);
                    int max2 = Math.max(this.et_search_gif.getSelectionEnd(), 0);
                    this.et_search_gif.getText().replace(Math.min(max, max2), Math.max(max, max2), String.valueOf(c2));
                }
            } else {
                char c3 = (char) i2;
                if (i2 == -5) {
                    MainActivity.et_test_main.dispatchKeyEvent(new KeyEvent(0, 67));
                    MainActivity.et_test_main.dispatchKeyEvent(new KeyEvent(1, 67));
                } else if (isPrintableChar(c3)) {
                    int max3 = Math.max(MainActivity.et_test_main.getSelectionStart(), 0);
                    int max4 = Math.max(MainActivity.et_test_main.getSelectionEnd(), 0);
                    MainActivity.et_test_main.getText().replace(Math.min(max3, max4), Math.max(max3, max4), String.valueOf(c3));
                }
            }
            if (i2 == -2) {
                LatinKeyboardView latinKeyboardView4 = this.mInputView;
                if (latinKeyboardView4 != null) {
                    Keyboard keyboard6 = latinKeyboardView4.getKeyboard();
                    LatinKeyboard latinKeyboard7 = this.mSymbolsKeyboard;
                    if (keyboard6 == latinKeyboard7 || keyboard6 == this.mSymbolsShiftedKeyboard) {
                        this.mQwertyKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                        this.mInputView.setKeyboard(this.mQwertyKeyboard);
                        return;
                    }
                    latinKeyboard7.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                    this.mInputView.setKeyboard(this.mSymbolsKeyboard);
                    this.mSymbolsKeyboard.setShifted(false);
                }
            } else if (i2 == -1) {
                LatinKeyboardView latinKeyboardView5 = this.mInputView;
                if (latinKeyboardView5 != null) {
                    Keyboard keyboard7 = latinKeyboardView5.getKeyboard();
                    int i5 = inputLangvg;
                    if (i5 == 1 || i5 == 2) {
                        LatinKeyboard latinKeyboard8 = this.mQwertyKeyboard;
                        if (keyboard7 == latinKeyboard8) {
                            this.mQwertyShifter.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mQwertyShifter);
                        } else if (keyboard7 == this.mQwertyShifter) {
                            latinKeyboard8.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mQwertyKeyboard);
                        } else {
                            LatinKeyboard latinKeyboard9 = this.mSymbolsKeyboard;
                            if (keyboard7 == latinKeyboard9) {
                                latinKeyboard9.setShifted(PROCESS_HARD_KEYS);
                                this.mSymbolsShiftedKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                                this.mInputView.setKeyboard(this.mSymbolsShiftedKeyboard);
                                this.mSymbolsShiftedKeyboard.setShifted(PROCESS_HARD_KEYS);
                                return;
                            }
                            LatinKeyboard latinKeyboard10 = this.mSymbolsShiftedKeyboard;
                            if (keyboard7 == latinKeyboard10) {
                                latinKeyboard10.setShifted(false);
                                this.mSymbolsKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                                this.mInputView.setKeyboard(this.mSymbolsKeyboard);
                                this.mSymbolsKeyboard.setShifted(false);
                            }
                        }
                    } else if (this.mQwertyKeyboard == keyboard7) {
                        long currentTimeMillis2 = System.currentTimeMillis();
                        if (this.mLastShiftTime + 800 > currentTimeMillis2) {
                            this.mCapsLock ^= PROCESS_HARD_KEYS;
                            this.mLastShiftTime = 0L;
                        } else {
                            this.mLastShiftTime = currentTimeMillis2;
                        }
                        LatinKeyboardView latinKeyboardView6 = this.mInputView;
                        if (!this.mCapsLock && latinKeyboardView6.isShifted()) {
                            z = false;
                        }
                        latinKeyboardView6.setShifted(z);
                    } else {
                        LatinKeyboard latinKeyboard11 = this.mSymbolsKeyboard;
                        if (keyboard7 == latinKeyboard11) {
                            latinKeyboard11.setShifted(PROCESS_HARD_KEYS);
                            this.mSymbolsShiftedKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mSymbolsShiftedKeyboard);
                            this.mSymbolsShiftedKeyboard.setShifted(PROCESS_HARD_KEYS);
                            return;
                        }
                        LatinKeyboard latinKeyboard12 = this.mSymbolsShiftedKeyboard;
                        if (keyboard7 == latinKeyboard12) {
                            latinKeyboard12.setShifted(false);
                            this.mSymbolsKeyboard.setLanguageSwitchKeyVisibility(this.mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken()));
                            this.mInputView.setKeyboard(this.mSymbolsKeyboard);
                            this.mSymbolsKeyboard.setShifted(false);
                        }
                    }
                }
            } else if (i2 == -101) {
                EmojiPopup sWRX_EmojiPopup2 = this.mEmojiPopup;
                if (sWRX_EmojiPopup2 != null) {
                    sWRX_EmojiPopup2.setWidth(-1);
                    this.mEmojiPopup.setHeight(this.mCurKeyboard.getHeight());
                    this.mEmojiPopup.showAtBottom();
                }
            } else if (i2 == 10) {
                currentInputConnection.sendKeyEvent(new KeyEvent(0, 66));
                currentInputConnection.sendKeyEvent(new KeyEvent(1, 66));
            }
        }
    }

    @Override 
    public void onText(CharSequence charSequence) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.beginBatchEdit();
            if (this.mComposing.length() > 0) {
                StringBuilder sb = this.mComposing;
                currentInputConnection.commitText(sb, sb.length());
                this.mComposing.setLength(0);
                updateCandidates();
            }
            currentInputConnection.commitText(charSequence, 0);
            currentInputConnection.endBatchEdit();
            updateShiftKeyState();
        }
    }

    public void updateCandidates() {
        if (this.mComposing.length() > 0) {
            SpellCheckerSession spellCheckerSession = this.mScs;
            if (spellCheckerSession != null) {
                spellCheckerSession.isSessionDisconnected();
                try {
                    this.mScs.getSentenceSuggestions(new TextInfo[]{new TextInfo(this.mComposing.toString())}, 3);
                } catch (Exception unused) {
                }
            } else {
                this.mScs = ((TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE)).newSpellCheckerSession(null, null, this, PROCESS_HARD_KEYS);
            }
        } else {
            setSuggestions(null, false);
        }
    }

    private void setSuggestions(List<String> list, boolean z) {
        Log.d("DD", "setSuggessstions: " + list);
        this.mSuggestions = list;
        LinearLayout linearLayout = this.mKeyboardBarContentLayout;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            if (list == null || list.size() <= 0) {
                this.mKeyboardBarContentLayout.setGravity(GravityCompat.START);
                LinearLayout linearLayout2 = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_keyboard_top, (ViewGroup) null);
                this.mKeyboardBarContentLayout.addView(linearLayout2);
                final LinearLayout linearLayout3 = (LinearLayout) this.mWrapperView.findViewById(R.id.l_allsetings);
                linearLayout3.setVisibility(View.GONE);

                ((LinearLayout) linearLayout2.findViewById(R.id.img_keyboard_setting_poup)).setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        if (!SoftKeyboard.SettingsDPWPermissionGranted) {
                            Toast.makeText(SoftKeyboard.this, "You need to grant permission in RGB Keyboard application to do this action on Xiaomi device.", Toast.LENGTH_LONG).show();
                        } else if (linearLayout3.getVisibility() == View.VISIBLE) {
                            linearLayout3.setVisibility(View.GONE);
                        } else {
                            linearLayout3.setVisibility(View.VISIBLE);
                        }
                    }
                });
                ((ImageView) this.mWrapperView.findViewById(R.id.img_gotofont)).setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View v) {
                        if (SoftKeyboard.SettingsDPWPermissionGranted) {
                            Intent intent = new Intent(SoftKeyboard.this, FontActivity.class);
                            intent.putExtra("iAmFromInside", false);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SoftKeyboard.this.startActivity(intent);
                            return;
                        }
                        Toast.makeText(SoftKeyboard.this, "You need to grant permission in Neon Keyboard to open on Xiaomi device.", Toast.LENGTH_LONG).show();
                    }
                });
                ((ImageView) this.mWrapperView.findViewById(R.id.img_gotolanguage)).setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View v) {
                        if (SoftKeyboard.SettingsDPWPermissionGranted) {
                            Intent intent = new Intent(SoftKeyboard.this, LanguageActivity.class);
                            intent.putExtra("iAmFromInside", false);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SoftKeyboard.this.startActivity(intent);
                            return;
                        }
                        Toast.makeText(SoftKeyboard.this, "You need to grant permission in Neon Keyboard to open on Xiaomi device.", Toast.LENGTH_LONG).show();
                    }
                });
                ((ImageView) this.mWrapperView.findViewById(R.id.img_setting)).setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View v) {
                        if (SoftKeyboard.SettingsDPWPermissionGranted) {
                            Intent intent = new Intent(SoftKeyboard.this, SettingActivity.class);
                            intent.putExtra("iAmFromInside", false);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SoftKeyboard.this.startActivity(intent);
                            return;
                        }
                        Toast.makeText(SoftKeyboard.this, "You need to grant permission in Neon Keyboard to open on Xiaomi device.", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            this.mKeyboardBarContentLayout.setGravity(17);
            for (int i = 0; i < list.size(); i++) {
                Button button = (Button) getLayoutInflater().inflate(R.layout.item_suggestion, (ViewGroup) null);
                button.setText(list.get(i));
                if ((i == 1 && !z) || (i == 0 && z)) {
                    button.setTextColor(getResources().getColor(R.color.white));
                } else if (i != 0) {
                    button.setTextColor(getResources().getColor(R.color.colorCandidateOther));
                }

                int aaa = i;
                button.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        SoftKeyboard.this.pickSuggestionManually(aaa);
                        if (SoftKeyboard.SettingsVibration) {
                            SoftKeyboard.mVibrator.vibrate(15L);
                        }
                    }
                });
                this.mKeyboardBarContentLayout.addView(button);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.width = 0;
                    layoutParams.weight = 1.0f;
                    button.setLayoutParams(layoutParams);
                }
            }
        }
    }

    private void handleClose() {
        if (this.mComposing.length() > 0) {
            InputConnection currentInputConnection = getCurrentInputConnection();
            if (currentInputConnection != null) {
                StringBuilder sb = this.mComposing;
                currentInputConnection.commitText(sb, sb.length());
            }
            this.mComposing.setLength(0);
            updateCandidates();
        }
        requestHideSelf(0);
        this.mInputView.closing();
        EmojiPopup sWRX_EmojiPopup = this.mEmojiPopup;
        if (sWRX_EmojiPopup != null) {
            sWRX_EmojiPopup.dismiss();
        }

    }

    private IBinder getToken() {
        Window window;
        Dialog window2 = getWindow();
        if (window2 == null || (window = window2.getWindow()) == null) {
            return null;
        }
        return window.getAttributes().token;
    }

    public void pickSuggestionManually(int i) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (this.mComposing.length() > 0) {
            if (settingsPrediction && this.mSuggestions != null && i >= 0) {
                StringBuilder sb = this.mComposing;
                sb.replace(0, sb.length(), this.mSuggestions.get(i));
            }
            if (this.mComposing.length() > 0) {
                if (currentInputConnection != null) {
                    StringBuilder sb2 = this.mComposing;
                    currentInputConnection.commitText(sb2, sb2.length());
                }
                this.mComposing.setLength(0);
                updateCandidates();
            }
        }
    }

    public void playBeep() {
        try {
            if (this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
                this.mediaPlayer.release();
                this.mediaPlayer = new MediaPlayer();
            }
            AssetFileDescriptor openFd = getAssets().openFd("keypress/ak47.mp3");
            this.mediaPlayer.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
            openFd.close();
            this.mediaPlayer.prepare();
            this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { 
                @Override 
                public void onCompletion(MediaPlayer mp) {
                    SoftKeyboard.this.mediaPlayer.release();
                }
            });
            this.mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playClick(int i) {
        if (SettingsVibration) {
            mVibrator.vibrate(15L);
        }
        if (i == -5) {
            mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
        } else if (i == -4 || i == 10) {
            mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
        } else if (i != 32) {
            mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        } else {
            mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
        }
    }

    @Override 
    public void swipeRight() {
        if (settingsPrediction) {
            pickSuggestionManually(0);
        }
    }

    @Override 
    public void swipeDown() {
        handleClose();
    }

    @Override 
    public void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr) {
        StringBuilder sb = new StringBuilder();
        for (SuggestionsInfo suggestionsInfo : suggestionsInfoArr) {
            int suggestionsCount = suggestionsInfo.getSuggestionsCount();
            sb.append(10);
            for (int i = 0; i < suggestionsCount; i++) {
                sb.append(StringConstant.COMMA);
                sb.append(suggestionsInfo.getSuggestionAt(i));
            }
            sb.append(" (");
            sb.append(suggestionsCount);
            sb.append(")");
        }
    }

    @Override 
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        ArrayList arrayList = new ArrayList();
        for (SentenceSuggestionsInfo sentenceSuggestionsInfo : sentenceSuggestionsInfoArr) {
            if (sentenceSuggestionsInfo != null) {
                for (int i = 0; i < sentenceSuggestionsInfo.getSuggestionsCount(); i++) {
                    int suggestionsCount = sentenceSuggestionsInfo.getSuggestionsInfoAt(i).getSuggestionsCount();
                    for (int i2 = 0; i2 < suggestionsCount; i2++) {
                        if (arrayList.size() < 3) {
                            arrayList.add(sentenceSuggestionsInfo.getSuggestionsInfoAt(i).getSuggestionAt(i2));
                        }
                    }
                }
            }
        }
        setSuggestions(arrayList, PROCESS_HARD_KEYS);
    }
}

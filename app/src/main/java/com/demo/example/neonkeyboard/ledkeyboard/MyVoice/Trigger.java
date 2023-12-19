package com.demo.example.neonkeyboard.ledkeyboard.MyVoice;


interface Trigger {
    void onStartInputView();

    void startVoiceRecognition(String language);
}

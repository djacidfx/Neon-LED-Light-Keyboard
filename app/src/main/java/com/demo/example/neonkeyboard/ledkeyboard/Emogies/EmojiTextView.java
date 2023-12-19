package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;


public class EmojiTextView extends AppCompatTextView {
    public EmojiTextView(Context context) {
        super(context);
        init(null);
    }

    public EmojiTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public EmojiTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        setText(getText());
        setTextColor(ViewCompat.MEASURED_STATE_MASK);
    }

    @Override 
    public void setText(CharSequence charSequence, BufferType bufferType) {
        super.setText(new SpannableStringBuilder(charSequence), bufferType);
        super.setTextSize(26.0f);
    }
}

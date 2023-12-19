package com.demo.example.neonkeyboard.ledkeyboard.MyGif.utils;

import android.view.View;
import android.view.ViewTreeObserver;
import com.tenor.android.core.weakref.WeakRefObject;
import java.lang.ref.WeakReference;


public abstract class WeakRefOnPreDrawListener<T extends View> extends WeakRefObject<T> implements ViewTreeObserver.OnPreDrawListener {
    public abstract boolean onPreDraw(T t);

    public WeakRefOnPreDrawListener(T t) {
        super(t);
    }

    public WeakRefOnPreDrawListener(WeakReference<T> weakReference) {
        super((WeakReference) weakReference);
    }

    
    @Override 
    public boolean onPreDraw() {
        ViewTreeObserver viewTreeObserver;
        if (!hasRef() || (viewTreeObserver = ((View) getWeakRef().get()).getViewTreeObserver()) == null || !viewTreeObserver.isAlive()) {
            return false;
        }
        viewTreeObserver.removeOnPreDrawListener(this);
        return onPreDraw(getWeakRef().get());
    }
}

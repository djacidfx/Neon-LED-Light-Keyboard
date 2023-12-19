package com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import com.demo.example.neonkeyboard.R;

import java.util.ArrayList;
import java.util.List;


public class CandidateView extends View {
    private static final List<String> EMPTY_LIST = new ArrayList();
    private static final int MAX_SUGGESTIONS = 32;
    private static final int OUT_OF_BOUNDS = -1;
    private static final int SCROLL_PIXELS = 20;
    private static final int X_GAP = 10;
    private Rect mBgPadding;
    private int mColorNormal;
    private int mColorOther;
    private int mColorRecommended;
    private Paint mPaint;
    private boolean mScrolled;
    private int mSelectedIndex;
    private Drawable mSelectionHighlight;
    private SoftKeyboard mService;
    private List<String> mSuggestions;
    private int mTargetScrollX;
    private int mTotalWidth;
    private boolean mTypedWordValid;
    private int mVerticalPadding;
    private int mTouchX = -1;
    private int[] mWordWidth = new int[32];
    private int[] mWordX = new int[32];
    private GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() { 
        @Override 
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            CandidateView.this.mScrolled = true;
            int scrollX = (int) (CandidateView.this.getScrollX() + distanceX);
            if (scrollX < 0) {
                scrollX = 0;
            }
            if (CandidateView.this.getWidth() + scrollX > CandidateView.this.mTotalWidth) {
                scrollX = (int) (scrollX - distanceX);
            }
            CandidateView.this.mTargetScrollX = scrollX;
            CandidateView candidateView = CandidateView.this;
            candidateView.scrollTo(scrollX, candidateView.getScrollY());
            CandidateView.this.invalidate();
            return true;
        }
    });

    public CandidateView(Context context) {
        super(context);
        Drawable drawable = context.getResources().getDrawable(17301602);
        this.mSelectionHighlight = drawable;
        drawable.setState(new int[]{16842910, 16842908, 16842909, 16842919});
        Resources resources = context.getResources();
        setBackgroundColor(resources.getColor(R.color.candidate_background));
        this.mColorNormal = resources.getColor(R.color.candidate_normal);
        this.mColorRecommended = resources.getColor(R.color.candidate_recommended);
        this.mColorOther = resources.getColor(R.color.candidate_other);
        this.mVerticalPadding = resources.getDimensionPixelSize(R.dimen.candidate_vertical_padding);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(this.mColorNormal);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.candidate_font_height));
        this.mPaint.setStrokeWidth(0.0f);
        setHorizontalFadingEdgeEnabled(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }

    public void setService(SoftKeyboard listener) {
        this.mService = listener;
    }

    @Override 
    public int computeHorizontalScrollRange() {
        return this.mTotalWidth;
    }

    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resolveSize = resolveSize(50, widthMeasureSpec);
        Rect rect = new Rect();
        this.mSelectionHighlight.getPadding(rect);
        setMeasuredDimension(resolveSize, resolveSize(((int) this.mPaint.getTextSize()) + this.mVerticalPadding + rect.top + rect.bottom, heightMeasureSpec));
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        int i;
        int i2;
        boolean z;
        int i3;
        int i4;
        int i5;
        Canvas canvas2 = canvas;
        if (canvas2 != null) {
            super.onDraw(canvas);
        }
        this.mTotalWidth = 0;
        if (this.mSuggestions != null) {
            if (this.mBgPadding == null) {
                this.mBgPadding = new Rect(0, 0, 0, 0);
                if (getBackground() != null) {
                    getBackground().getPadding(this.mBgPadding);
                }
            }
            int size = this.mSuggestions.size();
            int height = getHeight();
            Rect rect = this.mBgPadding;
            Paint paint = this.mPaint;
            int i6 = this.mTouchX;
            int scrollX = getScrollX();
            boolean z2 = this.mScrolled;
            boolean z3 = this.mTypedWordValid;
            int textSize = (int) (((height - this.mPaint.getTextSize()) / 2.0f) - this.mPaint.ascent());
            int i7 = 0;
            int i8 = 0;
            while (i8 < size) {
                String str = this.mSuggestions.get(i8);
                int measureText = ((int) paint.measureText(str)) + 20;
                this.mWordX[i8] = i7;
                this.mWordWidth[i8] = measureText;
                paint.setColor(this.mColorNormal);
                int i9 = i6 + scrollX;
                size = size;
                if (i9 < i7 || i9 >= i7 + measureText || z2) {
                    i = i6;
                } else {
                    if (canvas2 != null) {
                        canvas2.translate(i7, 0.0f);
                        i = i6;
                        this.mSelectionHighlight.setBounds(0, rect.top, measureText, height);
                        this.mSelectionHighlight.draw(canvas2);
                        canvas2.translate(-i7, 0.0f);
                    } else {
                        i = i6;
                    }
                    this.mSelectedIndex = i8;
                }
                if (canvas2 != null) {
                    if ((i8 == 1 && !z3) || (i8 == 0 && z3)) {
                        paint.setFakeBoldText(true);
                        paint.setColor(this.mColorRecommended);
                    } else if (i8 != 0) {
                        paint.setColor(this.mColorOther);
                    }
                    canvas2.drawText(str, i7 + 10, textSize, paint);
                    paint.setColor(this.mColorOther);
                    float f = 0.5f + i7 + measureText;
                    i2 = measureText;
                    i5 = i7;
                    i3 = i8;
                    i4 = textSize;
                    z = z3;
                    canvas.drawLine(f, rect.top, f, height + 1, paint);
                    paint.setFakeBoldText(false);
                } else {
                    i2 = measureText;
                    i5 = i7;
                    i3 = i8;
                    i4 = textSize;
                    z = z3;
                }
                i7 = i5 + i2;
                i8 = i3 + 1;
                canvas2 = canvas;
                textSize = i4;
                z3 = z;
                i6 = i;
            }
            this.mTotalWidth = i7;
            if (this.mTargetScrollX != getScrollX()) {
                scrollToTarget();
            }
        }
    }

    private void scrollToTarget() {
        int i;
        int scrollX = getScrollX();
        int i2 = this.mTargetScrollX;
        if (i2 > scrollX) {
            i = scrollX + 20;
            if (i >= i2) {
                requestLayout();
            }
            i2 = i;
        } else {
            i = scrollX - 20;
            if (i <= i2) {
                requestLayout();
            }
            i2 = i;
        }
        scrollTo(i2, getScrollY());
        invalidate();
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
        clear();
        if (suggestions != null) {
            this.mSuggestions = new ArrayList(suggestions);
        }
        this.mTypedWordValid = typedWordValid;
        scrollTo(0, 0);
        this.mTargetScrollX = 0;
        onDraw(null);
        invalidate();
        requestLayout();
    }

    public void clear() {
        this.mSuggestions = EMPTY_LIST;
        this.mTouchX = -1;
        this.mSelectedIndex = -1;
        invalidate();
    }

    @Override 
    public boolean onTouchEvent(MotionEvent me) {
        int i;
        int i2;
        if (this.mGestureDetector.onTouchEvent(me)) {
            return true;
        }
        int action = me.getAction();
        int y = (int) me.getY();
        this.mTouchX = (int) me.getX();
        if (action == 0) {
            this.mScrolled = false;
            invalidate();
        } else if (action == 1) {
            if (!this.mScrolled && (i = this.mSelectedIndex) >= 0) {
                this.mService.pickSuggestionManually(i);
            }
            this.mSelectedIndex = -1;
            removeHighlight();
            requestLayout();
        } else if (action == 2) {
            if (y <= 0 && (i2 = this.mSelectedIndex) >= 0) {
                this.mService.pickSuggestionManually(i2);
                this.mSelectedIndex = -1;
            }
            invalidate();
        }
        return true;
    }

    public void takeSuggestionAt(float x) {
        this.mTouchX = (int) x;
        onDraw(null);
        int i = this.mSelectedIndex;
        if (i >= 0) {
            this.mService.pickSuggestionManually(i);
        }
        invalidate();
    }

    private void removeHighlight() {
        this.mTouchX = -1;
        invalidate();
    }
}

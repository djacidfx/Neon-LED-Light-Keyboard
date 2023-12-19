package com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.core.internal.view.SupportMenu;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.Constan.Utils;
import com.demo.example.neonkeyboard.ledkeyboard.Emogies.EmojiPopup;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.GiphyPopup;

import java.util.ArrayList;


public class LatinKeyboardView extends KeyboardView {
    private static final int KEYCODE_DELETE = -5;
    static final int KEYCODE_LANGUAGE_SWITCH = -101;
    private static final int KEYCODE_RETURN = 10;
    private static final int KEYCODE_SHIFT = -1;
    private static final int KEYCODE_SPACE = 32;
    private static final int KEYCODE_SYMBOLS = -2;
    private static Paint mPaint;
    private static Paint mPaintMultiply;
    private Bitmap bmp_bckg;
    private float f6950t;
    private float f6953w;
    private float f6954x;
    private float f6955y;
    private ArrayList<String> fontsList;
    private Drawable img_backspace;
    private Drawable img_emoji;
    private Drawable img_nextLine;
    private Drawable img_search;
    private Drawable img_shift_active;
    private Drawable img_shift_caps;
    private Drawable img_shtft;
    boolean isDefaultTheme;
    private LinearGradient linearGradient;
    private Matrix matrix;
    private int[] neonColors;
    private Paint paintNeon;
    SharedPreferences sp;
    private TypedArray typedArry;
    private static final Point mDrawableHalf = new Point();
    public static int mImeAction = 0;
    private static final Point mKeyCenter = new Point();
    private static final RectF mRect = new RectF();
    private static Typeface mTypefaceDefaultBold = Typeface.create(Typeface.DEFAULT, 1);
    private static Typeface mTypefaceDefaultNormal = Typeface.create(Typeface.DEFAULT, 0);
    public static final int[] RainbowTextView = {R.attr.colorSpace, R.attr.colorSpeed};

    public static int getRoundValue(float f) {
        return Math.round(f * getDisplyMetrics().density);
    }

    public static DisplayMetrics getDisplyMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }

    public LatinKeyboardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.img_search = drawable(R.drawable.ic_search);
        this.img_backspace = drawable(R.drawable.ic_backspace);
        this.img_emoji = drawable(R.drawable.img_emoji_light);
        this.img_nextLine = drawable(R.drawable.ic_nextline);
        this.img_shtft = drawable(R.drawable.ic_shiftup);
        this.img_shift_active = drawable(R.drawable.ic_shiftup);
        this.img_shift_caps = drawable(R.drawable.ic_shiftup);
        this.neonColors = new int[]{-16737878, -3584, SupportMenu.CATEGORY_MASK};
        this.f6950t = 1.0f;
        this.fontsList = new ArrayList<>();
        this.sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (SoftKeyboard.themeNumber == 0) {
            Paint paint = new Paint();
            mPaint = paint;
            paint.setAntiAlias(true);
            mPaint.setColor(getResources().getColor(R.color.darkTheme));
        } else if (SoftKeyboard.themeNumber == 1) {
            Paint paint2 = new Paint();
            mPaint = paint2;
            paint2.setAntiAlias(true);
            mPaint.setColor(-1);
        } else {
            Paint paint3 = new Paint(1);
            mPaint = paint3;
            paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            mPaint.setAntiAlias(true);
        }
        this.fontsList = Utils.listAssetFolders("font", context);
        if (SoftKeyboard.isMyFont) {
            AssetManager assets = context.getAssets();
            mTypefaceDefaultNormal = Typeface.createFromAsset(assets, "font/" + this.fontsList.get(SoftKeyboard.fontNumber));
            AssetManager assets2 = context.getAssets();
            mTypefaceDefaultBold = Typeface.createFromAsset(assets2, "font/" + this.fontsList.get(SoftKeyboard.fontNumber));
        } else {
            mTypefaceDefaultNormal = Typeface.create(Typeface.DEFAULT, 0);
            mTypefaceDefaultBold = Typeface.create(Typeface.DEFAULT, 1);
        }
        Paint paint4 = new Paint();
        mPaintMultiply = paint4;
        paint4.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        loadBackground();
        Paint paint5 = new Paint(1);
        this.paintNeon = paint5;
        paint5.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, RainbowTextView);
        this.typedArry = obtainStyledAttributes;
        this.f6955y = obtainStyledAttributes.getDimension(0, getRoundValue(250.0f));
        this.f6954x = this.typedArry.getDimension(1, getRoundValue(5.0f));
        this.f6950t = this.typedArry.getFloat(1, this.f6950t);
        this.typedArry.recycle();
        this.matrix = new Matrix();
        if (SoftKeyboard.themeNumber == 2) {
            this.neonColors = new int[]{Color.parseColor("#ffff00"), Color.parseColor("#00aeef"), Color.parseColor("#39b54a"), Color.parseColor("#ff00ff"), Color.parseColor("#fa0000")};
        } else if (SoftKeyboard.themeNumber == 3) {
            this.neonColors = new int[]{Color.parseColor("#fff200"), Color.parseColor("#f7941d"), Color.parseColor("#f26522")};
        } else if (SoftKeyboard.themeNumber == 4) {
            this.neonColors = new int[]{Color.parseColor("#fa5661"), Color.parseColor("#f26522"), Color.parseColor("#39b54a")};
        } else if (SoftKeyboard.themeNumber == 5) {
            this.neonColors = new int[]{Color.parseColor("#fa43bc"), Color.parseColor("#ff5017"), Color.parseColor("#39b54a")};
        } else if (SoftKeyboard.themeNumber == 6) {
            this.neonColors = new int[]{Color.parseColor("#ec008c"), Color.parseColor("#00a651"), Color.parseColor("#fff200"), Color.parseColor("#0e0ed9")};
        } else if (SoftKeyboard.themeNumber == 7) {
            this.neonColors = new int[]{Color.parseColor("#ff8a01"), Color.parseColor("#ff5017"), Color.parseColor("#fff200"), Color.parseColor("#ed1c24")};
        } else if (SoftKeyboard.themeNumber == 8) {
            this.neonColors = new int[]{Color.parseColor("#1261a0"), Color.parseColor("#1261a0"), Color.parseColor("#58cced")};
        } else if (SoftKeyboard.themeNumber == 9) {
            this.neonColors = new int[]{Color.parseColor("#046e5f"), Color.parseColor("#8a3694"), Color.parseColor("#f04770"), Color.parseColor("#f89319")};
        } else if (SoftKeyboard.themeNumber == 10) {
            this.neonColors = new int[]{Color.parseColor("#8c6239"), Color.parseColor("#fbaf5d")};
        } else if (SoftKeyboard.themeNumber == 11) {
            this.neonColors = new int[]{Color.parseColor("#fa43bc"), Color.parseColor("#01d6da")};
        } else {
            this.neonColors = new int[]{-16737878, -3584, SupportMenu.CATEGORY_MASK};
        }
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, getResources().getDisplayMetrics().widthPixels, 0.0f, this.neonColors, (float[]) null, Shader.TileMode.MIRROR);
        this.linearGradient = linearGradient;
        this.paintNeon.setShader(linearGradient);
        this.paintNeon.setAlpha(255);
    }

    public LatinKeyboardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.img_search = drawable(R.drawable.ic_search);
        this.img_backspace = drawable(R.drawable.ic_backspace);
        this.img_emoji = drawable(R.drawable.img_emoji_light);
        this.img_nextLine = drawable(R.drawable.ic_nextline);
        this.img_shtft = drawable(R.drawable.ic_shiftup);
        this.img_shift_active = drawable(R.drawable.ic_shiftup);
        this.img_shift_caps = drawable(R.drawable.ic_shiftup);
        this.neonColors = new int[]{-16737878, -3584, SupportMenu.CATEGORY_MASK};
        this.f6950t = 1.0f;
        this.fontsList = new ArrayList<>();
        mPaint = new Paint();
        Paint paint = new Paint();
        mPaintMultiply = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        loadBackground();
    }

    public void loadBackground() {
        boolean z = this.sp.getBoolean("isDefaultTheme", true);
        this.isDefaultTheme = z;
        if (!z) {
            int i = this.sp.getInt("blurOption", 1);
            if (i == 2 || i == 3) {
                this.bmp_bckg = stringToBitmap(this.sp.getString("savedMyBlur", ""));
            } else {
                this.bmp_bckg = stringToBitmap(this.sp.getString("savedMyPhoto", ""));
            }
        } else if (SoftKeyboard.themeNumber == 0) {
            this.img_emoji = drawable(R.drawable.img_emoji_dark);
            this.img_backspace = drawable(R.drawable.ic_backspace_dark);
            this.img_nextLine = drawable(R.drawable.ic_nextline_dark);
            this.img_shtft = drawable(R.drawable.ic_shiftup_dark);
            this.img_shift_active = drawable(R.drawable.ic_shiftup_dark);
            this.img_shift_caps = drawable(R.drawable.ic_shiftup_dark);
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(0).intValue());
        } else if (SoftKeyboard.themeNumber == 1) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(1).intValue());
        } else if (SoftKeyboard.themeNumber == 2) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(2).intValue());
        } else if (SoftKeyboard.themeNumber == 3) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(3).intValue());
        } else if (SoftKeyboard.themeNumber == 4) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(4).intValue());
        } else if (SoftKeyboard.themeNumber == 5) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(5).intValue());
        } else if (SoftKeyboard.themeNumber == 6) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(6).intValue());
        } else if (SoftKeyboard.themeNumber == 7) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(7).intValue());
        } else if (SoftKeyboard.themeNumber == 8) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(8).intValue());
        } else if (SoftKeyboard.themeNumber == 9) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(9).intValue());
        } else if (SoftKeyboard.themeNumber == 10) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(10).intValue());
        } else if (SoftKeyboard.themeNumber == 11) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(11).intValue());
        } else if (SoftKeyboard.themeNumber == 12) {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(12).intValue());
        } else {
            this.bmp_bckg = BitmapFactory.decodeResource(getResources(), Utils.mItems.get(2).intValue());
        }
    }

    public static Bitmap stringToBitmap(String input) {
        byte[] decode = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    private void drawRoundRect(Canvas canvas, float f, float f2, float f3, float f4, float f5, int i, int i2, int i3, int i4) {
        mPaint.setARGB(i4, i, i2, i3);
        RectF rectF = mRect;
        rectF.set(f, f2, f3, f4);
        canvas.drawRoundRect(rectF, f5, f5, mPaint);
    }

    private void drawRoundRectBlack(Canvas canvas, float left, float top, float right, float bottom, float f5, int i, int i2, int i3, int i4) {
        mPaint.setColor(getResources().getColor(R.color.darkTheme));
        RectF rectF = mRect;
        rectF.set(left, top, right, bottom);
        canvas.drawRoundRect(rectF, f5, f5, mPaint);
        mPaint.setColor(getResources().getColor(R.color.white));
    }

    private void drawRoundRectWhite(Canvas canvas, float left, float top, float right, float bottom, float f5, int i, int i2, int i3, int i4) {
        mPaint.setColor(getResources().getColor(R.color.white));
        RectF rectF = mRect;
        rectF.set(left, top, right, bottom);
        canvas.drawRoundRect(rectF, f5, f5, mPaint);
        mPaint.setColor(getResources().getColor(R.color.darkTheme));
    }

    private void drawRoundRectStroke(Canvas canvas, float f, float f2, float f3, float f4, float f5, int i, int i2, int i3, int i4) {
        mPaint.setARGB(i4, i, i2, i3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2.0f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = mRect;
        rectF.set(f, f2, f3, f4);
        canvas.drawRoundRect(rectF, f5, f5, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void drawText(Canvas canvas, String str, float f, float f2, float f3, Typeface typeface, int i) {
        Log.d("TAG", "drawText: " + i);
        mPaint.setColor(i);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(f3);
        mPaint.setTypeface(typeface);
        canvas.drawText(str, f, f2, mPaint);
    }

    private int color(int i) {
        return getResources().getColor(i);
    }

    private Drawable drawable(int i) {
        return getResources().getDrawable(i);
    }

    @Override 
    public void onDraw(Canvas canvas) {
        float f;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        Keyboard keyboard;
        Keyboard.Key key;
        int i22;
        Drawable drawable;
        int i23;
        Drawable drawable2;
        Drawable drawable3 = this.img_shtft;
        int color = color(R.color.white);
        int color2 = color(R.color.colorKeyTextPressed);
        char c = 0;
        mPaint.setARGB(255, 0, 0, 0);
        RectF rectF = mRect;
        rectF.set(getLeft(), getTop(), getRight(), getBottom());
        canvas.drawRect(rectF, mPaint);
        mPaint.setAntiAlias(true);
        float f2 = getResources().getDisplayMetrics().density;
        float f3 = SoftKeyboard.SettingsRoundness * f2;
        float f4 = 5.0f * f2 * 0.5f;
        float f5 = 6.0f * f2 * 0.5f;
        if (SoftKeyboard.themeNumber == 0) {
            color = color(R.color.darkTheme);
        }
        int i24 = color;
        rectF.set(getLeft(), getTop(), getRight(), getBottom());
        canvas.drawBitmap(this.bmp_bckg, (Rect) null, rectF, mPaint);
        if (!EmojiPopup.mVisible && !GiphyPopup.mVisible) {
            Drawable drawable4 = drawable3;
            for (Keyboard.Key key2 : getKeyboard().getKeys()) {
                float f6 = key2.x + f4;
                float f7 = key2.y + f5;
                float f8 = (key2.x + key2.width) - f4;
                float f9 = ((key2.y + key2.height) - (f2 * 2.0f)) - f5;
                int i25 = SoftKeyboard.SettingsOpacity;
                if (key2.codes[c] == -1) {
                    Keyboard keyboard2 = SoftKeyboard.Instance.mInputView.getKeyboard();
                    if (keyboard2 == SoftKeyboard.Instance.mSymbolsKeyboard || keyboard2 == SoftKeyboard.Instance.mSymbolsShiftedKeyboard) {
                        f = f2;
                        if (key2.pressed) {
                            i21 = 200;
                            i20 = 200;
                            i19 = 200;
                        } else {
                            i21 = 255;
                            i20 = 255;
                            i19 = 255;
                        }
                        if (SoftKeyboard.themeNumber == 0) {
                            keyboard = keyboard2;
                            drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i21, i20, i19, i25);
                        } else {
                            keyboard = keyboard2;
                            if (SoftKeyboard.themeNumber == 1) {
                                drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i21, i20, i19, i25);
                            } else {
                                drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i21, i20, i19, i25);
                            }
                        }
                        if (keyboard == SoftKeyboard.Instance.mSymbolsKeyboard) {
                            drawText(canvas, "=\\<", key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 14.0f, mTypefaceDefaultBold, key2.pressed ? color2 : i24);
                        } else {
                            drawText(canvas, "?123", key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 14.0f, mTypefaceDefaultBold, key2.pressed ? color2 : i24);
                        }
                    } else {
                        if (key2.pressed) {
                            key = key2;
                            f = f2;
                            drawable = drawable4;
                            i23 = 200;
                            i22 = 1;
                        } else {
                            if (SoftKeyboard.Instance.mInputView.isShifted()) {
                                if (!SoftKeyboard.Instance.mInputView.isShifted() || SoftKeyboard.Instance.mCapsLock) {
                                    key = key2;
                                    f = f2;
                                    i22 = 1;
                                    drawRoundRect(canvas, f6, f7, f8, f9, f3, 255, 255, 255, 150);
                                    drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, 255, 255, 255, 150);
                                } else if (SoftKeyboard.themeNumber == 0) {
                                    key = key2;
                                    f = f2;
                                    i22 = 1;
                                    drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, 255, 255, 255, 150);
                                } else {
                                    key = key2;
                                    f = f2;
                                    i22 = 1;
                                    if (SoftKeyboard.themeNumber == 1) {
                                        drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, 255, 255, 255, 150);
                                    } else {
                                        drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, 255, 255, 255, 150);
                                    }
                                }
                                if (!SoftKeyboard.Instance.mCapsLock) {
                                    drawable2 = this.img_shift_caps;
                                } else if (SoftKeyboard.Instance.mInputView.isShifted()) {
                                    drawable2 = this.img_shtft;
                                } else {
                                    drawable2 = this.img_shift_active;
                                }
                                Drawable drawable5 = drawable2;
                                Point point = mKeyCenter;
                                point.set(key.x + (key.width / 2), key.y + (key.height / 2));
                                Point point2 = mDrawableHalf;
                                point2.set(drawable5.getIntrinsicWidth() / 2, drawable5.getIntrinsicHeight() / 2);
                                drawable5.setBounds(point.x - point2.x, point.y - point2.y, point.x + point2.x, point.y + point2.y);
                                drawable5.draw(canvas);
                                drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, 255, 255, 255, 150);
                                drawable = drawable5;
                            } else {
                                key = key2;
                                f = f2;
                                i22 = 1;
                                drawable = drawable4;
                            }
                            i23 = 255;
                        }
                        SoftKeyboard.Instance.mInputView.isShifted();
                        if (SoftKeyboard.themeNumber == 0) {
                            drawable4 = drawable;
                            drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i23, i23, i23, i25);
                        } else {
                            drawable4 = drawable;
                            if (SoftKeyboard.themeNumber == i22) {
                                drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i23, i23, i23, i25);
                            } else {
                                drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i23, i23, i23, i25);
                            }
                        }
                        boolean z = SoftKeyboard.Instance.mCapsLock;
                        Point point3 = mKeyCenter;
                        point3.set(key.x + (key.width / 2), key.y + (key.height / 2));
                        Point point4 = mDrawableHalf;
                        point4.set(drawable4.getIntrinsicWidth() / 2, drawable4.getIntrinsicHeight() / 2);
                        int i26 = point3.y + point4.y;
                        Drawable drawable6 = drawable4;
                        drawable6.setBounds(point3.x - point4.x, point3.y - point4.y, point3.x + point4.x, i26);
                        drawable6.draw(canvas);
                    }
                } else {
                    f = f2;
                    if (key2.codes[0] == KEYCODE_DELETE) {
                        if (key2.pressed) {
                            i18 = 200;
                            i17 = 200;
                            i16 = 200;
                        } else {
                            i18 = 255;
                            i17 = 255;
                            i16 = 255;
                        }
                        if (SoftKeyboard.themeNumber == 0) {
                            drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i18, i17, i16, i25);
                        } else if (SoftKeyboard.themeNumber == 1) {
                            drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i18, i17, i16, i25);
                        } else {
                            drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i18, i17, i16, i25);
                        }
                        Point point5 = mKeyCenter;
                        point5.set(key2.x + (key2.width / 2), key2.y + (key2.height / 2));
                        Point point6 = mDrawableHalf;
                        point6.set(this.img_backspace.getIntrinsicWidth() / 2, this.img_backspace.getIntrinsicHeight() / 2);
                        this.img_backspace.setBounds(point5.x - point6.x, point5.y - point6.y, point5.x + point6.x, point5.y + point6.y);
                        this.img_backspace.draw(canvas);
                    } else if (key2.codes[0] == -2) {
                        if (key2.pressed) {
                            i15 = 200;
                            i14 = 200;
                            i13 = 200;
                        } else {
                            i15 = 255;
                            i14 = 255;
                            i13 = 255;
                        }
                        if (SoftKeyboard.themeNumber == 0) {
                            drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i15, i14, i13, i25);
                        } else if (SoftKeyboard.themeNumber == 1) {
                            drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i15, i14, i13, i25);
                        } else {
                            drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i15, i14, i13, i25);
                        }
                        if (SoftKeyboard.Instance.mInputView.getKeyboard() == SoftKeyboard.Instance.mQwertyKeyboard) {
                            drawText(canvas, "?123", key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 14.0f, mTypefaceDefaultBold, key2.pressed ? color2 : i24);
                        } else {
                            drawText(canvas, "ABC", key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 14.0f, mTypefaceDefaultBold, key2.pressed ? color2 : i24);
                        }
                    } else if (key2.codes[0] == KEYCODE_LANGUAGE_SWITCH) {
                        if (LatinKeyboard.mKeyLanguageSwitchVisible) {
                            if (key2.pressed) {
                                i12 = 200;
                                i11 = 200;
                                i10 = 200;
                            } else {
                                i12 = 255;
                                i11 = 255;
                                i10 = 255;
                            }
                            if (SoftKeyboard.themeNumber == 0) {
                                drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i12, i11, i10, i25);
                            } else if (SoftKeyboard.themeNumber == 1) {
                                drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i12, i11, i10, i25);
                            } else {
                                drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i12, i11, i10, i25);
                            }
                            Point point7 = mKeyCenter;
                            point7.set(key2.x + (key2.width / 2), key2.y + (key2.height / 2));
                            Point point8 = mDrawableHalf;
                            point8.set(this.img_emoji.getIntrinsicWidth() / 2, this.img_emoji.getIntrinsicHeight() / 2);
                            this.img_emoji.setBounds(point7.x - point8.x, point7.y - point8.y, point7.x + point8.x, point7.y + point8.y);
                            this.img_emoji.draw(canvas);
                        }
                    } else if (key2.codes[0] == 10) {
                        if (key2.pressed) {
                            i9 = 200;
                            i8 = 200;
                            i7 = 200;
                        } else {
                            i9 = 255;
                            i8 = 255;
                            i7 = 255;
                        }
                        if (SoftKeyboard.themeNumber == 0) {
                            drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i9, i8, i7, i25);
                        } else if (SoftKeyboard.themeNumber == 1) {
                            drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i9, i8, i7, i25);
                        } else {
                            drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i9, i8, i7, i25);
                        }
                        int i27 = mImeAction;
                        if (i27 == 2) {
                            drawText(canvas, getResources().getText(R.string.label_go_key).toString(), key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 14.0f, mTypefaceDefaultBold, key2.pressed ? color2 : i24);
                        } else if (i27 == 5) {
                            drawText(canvas, getResources().getText(R.string.label_next_key).toString(), key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 14.0f, mTypefaceDefaultBold, key2.pressed ? color2 : i24);
                        } else if (i27 == 3) {
                            Point point9 = mKeyCenter;
                            point9.set(key2.x + (key2.width / 2), key2.y + (key2.height / 2));
                            Point point10 = mDrawableHalf;
                            point10.set(this.img_search.getIntrinsicWidth() / 2, this.img_search.getIntrinsicHeight() / 2);
                            this.img_search.setBounds(point9.x - point10.x, point9.y - point10.y, point9.x + point10.x, point9.y + point10.y);
                            this.img_search.draw(canvas);
                        } else if (i27 == 4) {
                            drawText(canvas, getResources().getText(R.string.label_send_key).toString(), key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 14.0f, mTypefaceDefaultBold, key2.pressed ? color2 : i24);
                        } else {
                            Point point11 = mKeyCenter;
                            point11.set(key2.x + (key2.width / 2), key2.y + (key2.height / 2));
                            Point point12 = mDrawableHalf;
                            point12.set(this.img_nextLine.getIntrinsicWidth() / 2, this.img_nextLine.getIntrinsicHeight() / 2);
                            this.img_nextLine.setBounds(point11.x - point12.x, point11.y - point12.y, point11.x + point12.x, point11.y + point12.y);
                            this.img_nextLine.draw(canvas);
                        }
                    } else {
                        if (key2.codes[0] == 32) {
                            if (key2.pressed) {
                                i6 = 200;
                                i5 = 200;
                                i4 = 200;
                            } else {
                                i6 = 255;
                                i5 = 255;
                                i4 = 255;
                            }
                            if (SoftKeyboard.themeNumber == 0) {
                                drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i6, i5, i4, i25);
                            } else if (SoftKeyboard.themeNumber == 1) {
                                drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i6, i5, i4, i25);
                            } else {
                                drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i6, i5, i4, i25);
                            }
                            if (key2.label != null) {
                                drawText(canvas, key2.label.toString(), key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), f * 16.0f, mTypefaceDefaultNormal, key2.pressed ? color2 : i24);
                            }
                        } else {
                            if (key2.pressed) {
                                i3 = 200;
                                i2 = 200;
                                i = 200;
                            } else {
                                i3 = 255;
                                i2 = 255;
                                i = 255;
                            }
                            if (SoftKeyboard.themeNumber == 0) {
                                drawRoundRectWhite(canvas, f6, f7, f8, f9, f3, i3, i2, i, i25);
                            } else if (SoftKeyboard.themeNumber == 1) {
                                drawRoundRectBlack(canvas, f6, f7, f8, f9, f3, i3, i2, i, i25);
                            } else {
                                drawRoundRectStroke(canvas, f6, f7, f8, f9, f3, i3, i2, i, i25);
                            }
                            if (key2.label != null) {
                                String charSequence = key2.label.toString();
                                if (SoftKeyboard.Instance.mInputView.getKeyboard() == SoftKeyboard.Instance.mQwertyKeyboard && (SoftKeyboard.Instance.mCapsLock || SoftKeyboard.Instance.mInputView.isShifted())) {
                                    charSequence = charSequence.toUpperCase();
                                }
                                drawText(canvas, charSequence, key2.x + (key2.width / 2.0f), key2.y + (f * 33.0f), (SoftKeyboard.SettingsFontSize + 15.0f) * f, mTypefaceDefaultNormal, key2.pressed ? color2 : i24);
                            }
                        }
                        f2 = f;
                        c = 0;
                    }
                }
                f2 = f;
                c = 0;
            }
        }
        if (!(SoftKeyboard.themeNumber == 0 || SoftKeyboard.themeNumber == 1)) {
            if (!(this.linearGradient == null || this.matrix == null)) {
                canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), this.paintNeon);
                float f10 = this.f6953w + this.f6954x;
                this.f6953w = f10;
                this.matrix.setTranslate(f10, 0.0f);
                this.linearGradient.setLocalMatrix(this.matrix);
            }
            postInvalidateDelayed(0L);
        }
    }
}

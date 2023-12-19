package com.demo.example.neonkeyboard.ledkeyboard.Constan;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.demo.example.neonkeyboard.R;


public class GridLinesViewCute extends View {
    public static final int[] RainbowTextView = {R.attr.colorSpace, R.attr.colorSpeed};
    private LinearGradient f6949s;
    private TypedArray f6951u;
    private Matrix f6952v;
    private float f6953w;
    private float f6954x;
    private float f6955y;
    private Paint f6956z;
    private int[] f6948r = {-9437219, -196403, -272506};
    private float f6950t = 1.0f;

    public GridLinesViewCute(Context context) {
        super(context);
        m8252a(context, null);
    }

    public static int m8693a(float f) {
        return Math.round(f * m8694a().density);
    }

    public static DisplayMetrics m8694a() {
        return Resources.getSystem().getDisplayMetrics();
    }

    private void m8252a(Context context, AttributeSet attributeSet) {
        this.f6956z = new Paint(1);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, RainbowTextView);
        this.f6951u = obtainStyledAttributes;
        this.f6955y = obtainStyledAttributes.getDimension(0, m8693a(150.0f));
        this.f6954x = this.f6951u.getDimension(1, m8693a(5.0f));
        this.f6950t = this.f6951u.getFloat(1, this.f6950t);
        this.f6951u.recycle();
        this.f6952v = new Matrix();
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, this.f6955y, 0.0f, this.f6948r, (float[]) null, Shader.TileMode.MIRROR);
        this.f6949s = linearGradient;
        this.f6956z.setShader(linearGradient);
    }

    @Override 
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(this.f6949s == null || this.f6952v == null)) {
            canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), this.f6956z);
            float f = this.f6953w + this.f6954x;
            this.f6953w = f;
            this.f6952v.setTranslate(f, 0.0f);
            this.f6949s.setLocalMatrix(this.f6952v);
        }
        postInvalidateDelayed(0L);
    }

    public GridLinesViewCute(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m8252a(context, attributeSet);
    }

    public GridLinesViewCute(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m8252a(context, attributeSet);
    }
}

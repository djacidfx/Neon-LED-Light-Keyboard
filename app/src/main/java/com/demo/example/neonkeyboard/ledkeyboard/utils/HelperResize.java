package com.demo.example.neonkeyboard.ledkeyboard.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;


public class HelperResize {
    public static int SCALE_HEIGHT = 1920;
    public static int SCALE_WIDTH = 1080;
    public static int height;
    public static int width;

    public static void getheightandwidth(Context context) {
        getHeight(context);
        getwidth(context);
    }

    public static int getwidth(Context context) {
        int i = context.getResources().getDisplayMetrics().widthPixels;
        width = i;
        return i;
    }

    public static int getHeight(Context context) {
        int i = context.getResources().getDisplayMetrics().heightPixels;
        height = i;
        return i;
    }

    public static void setHeight(Context mContext, View view, int v_height) {
        view.getLayoutParams().height = (mContext.getResources().getDisplayMetrics().heightPixels * v_height) / SCALE_HEIGHT;
    }

    public static void setWidth(Context mContext, View view, int v_Width) {
        view.getLayoutParams().width = (mContext.getResources().getDisplayMetrics().widthPixels * v_Width) / SCALE_WIDTH;
    }

    public static int setHeight(int h) {
        return (height * h) / 1920;
    }

    public static int setWidth(int w) {
        return (width * w) / 1080;
    }

    public static void setSize(View view, int width2, int height2) {
        view.getLayoutParams().height = setHeight(height2);
        view.getLayoutParams().width = setWidth(width2);
    }

    public static void setHeightByWidth(Context mContext, View view, int v_height) {
        view.getLayoutParams().height = (mContext.getResources().getDisplayMetrics().widthPixels * v_height) / SCALE_WIDTH;
    }

    public static void setSize(View view, int width2, int height2, boolean sameheightandwidth) {
        if (sameheightandwidth) {
            view.getLayoutParams().height = setWidth(height2);
            view.getLayoutParams().width = setWidth(width2);
            return;
        }
        view.getLayoutParams().height = setHeight(height2);
        view.getLayoutParams().width = setHeight(width2);
    }

    public static void setMargins(View view, int left, int top, int right, int bottom) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(setWidth(left), setHeight(top), setWidth(right), setHeight(bottom));
    }

    public static void setPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(left, top, right, bottom);
    }

    public static void setHeightWidth(Context mContext, View view, int v_width, int v_height) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int i = (displayMetrics.widthPixels * v_width) / SCALE_WIDTH;
        int i2 = (displayMetrics.heightPixels * v_height) / SCALE_HEIGHT;
        view.getLayoutParams().width = i;
        view.getLayoutParams().height = i2;
    }

    public static void setHeightWidthAsWidth(Context mContext, View view, int v_width, int v_height) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int i = (displayMetrics.widthPixels * v_width) / SCALE_WIDTH;
        int i2 = (displayMetrics.widthPixels * v_height) / SCALE_WIDTH;
        view.getLayoutParams().width = i;
        view.getLayoutParams().height = i2;
    }

    public static void setMargins(Context mContext, View view, int m_left, int m_top, int m_right, int m_bottom) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int i = (displayMetrics.widthPixels * m_left) / SCALE_WIDTH;
        int i2 = (displayMetrics.heightPixels * m_top) / SCALE_HEIGHT;
        int i3 = (displayMetrics.widthPixels * m_right) / SCALE_WIDTH;
        int i4 = (displayMetrics.heightPixels * m_bottom) / SCALE_HEIGHT;
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(i, i2, i3, i4);
            view.requestLayout();
        }
    }

    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static void setMarginLeft(Context mContext, View view, int m_left) {
        int i = (mContext.getResources().getDisplayMetrics().widthPixels * m_left) / SCALE_WIDTH;
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(i, 0, 0, 0);
            view.requestLayout();
        }
    }

    public static void setPadding(Context mContext, View view, int p_left, int p_top, int p_right, int p_bottom) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        view.setPadding((displayMetrics.widthPixels * p_left) / SCALE_WIDTH, (displayMetrics.heightPixels * p_top) / SCALE_HEIGHT, (displayMetrics.widthPixels * p_right) / SCALE_WIDTH, (displayMetrics.heightPixels * p_bottom) / SCALE_HEIGHT);
    }

    public static void setHeightWidth2(Context mContext, View view, int v_width, int v_height) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int i = (displayMetrics.widthPixels * v_width) / SCALE_WIDTH;
        int i2 = (displayMetrics.widthPixels * v_height) / SCALE_WIDTH;
        view.getLayoutParams().width = i;
        view.getLayoutParams().height = i2;
    }

    public static void FS(Activity mActivity) {
        mActivity.getWindow().addFlags(1024);
    }

    public static void FS2(Activity mActivity) {
        mActivity.getWindow().getDecorView().setSystemUiVisibility(4102);
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }

}

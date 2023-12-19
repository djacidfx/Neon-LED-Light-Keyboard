package com.demo.example.neonkeyboard.ledkeyboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.demo.example.neonkeyboard.R;
import com.tenor.android.core.constant.StringConstant;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Help {
    public static String Image_Path = null;
    private static final String TAG = "Jay_Help";
    public static int height;
    public static String mAudioPath;
    public static Bitmap mBitmap;
    public static boolean mBoolean;
    public static Bitmap mOriginal;
    public static TextView mTextView;
    public static Uri mUri;
    public static String mVideoPath;
    public static String mWaterMarkPath;
    public static Bitmap stickBit;
    public static int width;

    public static Bitmap bitmapResize(Bitmap bit, int width2, int height2) {
        int width3 = bit.getWidth();
        int height3 = bit.getHeight();
        if (width3 >= height3) {
            int i = (height3 * width2) / width3;
            if (i > height2) {
                width2 = (width2 * height2) / i;
            } else {
                height2 = i;
            }
        } else {
            int i2 = (width3 * height2) / height3;
            if (i2 > width2) {
                height2 = (height2 * width2) / i2;
            } else {
                width2 = i2;
            }
        }
        return Bitmap.createScaledBitmap(bit, width2, height2, true);
    }

    public static Bitmap getBitmap(String path) {
        return BitmapFactory.decodeFile(path);
    }

    public static void setLaySize(View view, int width2, int height2) {
        int width3 = view.getWidth();
        int height3 = view.getHeight();
        if (width2 >= height2) {
            int i = (height2 * width3) / width2;
            if (i > height3) {
                width3 = (width3 * height3) / i;
            } else {
                height3 = i;
            }
        } else {
            int i2 = (width2 * height3) / height2;
            if (i2 > width3) {
                height3 = (height3 * width3) / i2;
            } else {
                width3 = i2;
            }
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width3, height3);
        layoutParams.addRule(13);
        view.setLayoutParams(layoutParams);
    }

    public static void showLog(String str) {
        Log.e("999999", str);
    }

    public static Bitmap getBitmap(View v) {
        Bitmap createBitmap = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(canvas);
        return createBitmap;
    }

    public static float rotationToStartPoint(MotionEvent event, Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        float f = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        return (float) Math.toDegrees(Math.atan2(event.getY(0) - (((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5]), event.getX(0) - f));
    }

    public static PointF midPointToStartPoint(MotionEvent event, Matrix matrix) {
        PointF pointF = new PointF();
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        pointF.set(((((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]) + event.getX(0)) / 2.0f, ((((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5]) + event.getY(0)) / 2.0f);
        return pointF;
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static int w(int v) {
        return (width * v) / 1080;
    }

    public static int h(int v) {
        int i = height;
        if (i <= 1280 || i >= 1920) {
            return (i * v) / 1920;
        }
        return (i * v) / 1280;
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor, Typeface typeface) {
        Paint paint = new Paint(1);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.LEFT);
        float f = -paint.ascent();
        Bitmap createBitmap = Bitmap.createBitmap((int) (paint.measureText(text) + 0.5f), (int) (paint.descent() + f + 0.5f), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawText(text, 0.0f, f, paint);
        return createBitmap;
    }

    public static Bitmap drawMultilineTextToBitmap(Context gContext, String gText, int color, Typeface typeface) {
        float f = gContext.getResources().getDisplayMetrics().density;
        int i = width;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        TextPaint textPaint = new TextPaint(1);
        textPaint.setColor(color);
        if (gText.length() < 25) {
            textPaint.setTextSize((int) (60.0f * f));
        } else if (gText.length() < 75) {
            textPaint.setTextSize((int) (40.0f * f));
        } else {
            textPaint.setTextSize((int) (20.0f * f));
        }
        textPaint.setTypeface(typeface);
        int width2 = canvas.getWidth() - ((int) (f * 16.0f));
        StaticLayout staticLayout = new StaticLayout(gText, textPaint, width2, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        int height2 = staticLayout.getHeight();
        canvas.save();
        canvas.translate((createBitmap.getWidth() - width2) / 2, (createBitmap.getHeight() - height2) / 2);
        staticLayout.draw(canvas);
        canvas.restore();
        return createBitmap;
    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File file : fileOrDirectory.listFiles()) {
                deleteRecursive(file);
            }
        }
        fileOrDirectory.delete();
    }

    public static void Toast(Context cn, String str) {
        Toast.makeText(cn, str, Toast.LENGTH_SHORT).show();
    }

    public static void ToastL(Context cn, String str) {
        Toast.makeText(cn, str, Toast.LENGTH_LONG).show();
    }

    public static Bitmap getMask(Context mContext, Bitmap bit, Bitmap maskBitmap) {
        int width2 = bit.getWidth();
        int height2 = bit.getHeight();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bit, width2, height2, true);
        Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(maskBitmap, width2, height2, true);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap2.getWidth(), createScaledBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }

    public static String getImagePath(Context mContext, Uri uri) {
        String[] strArr = {"_data"};
        Cursor query = mContext.getContentResolver().query(uri, strArr, null, null, null);
        query.moveToFirst();
        String string = query.getString(query.getColumnIndex(strArr[0]));
        query.close();
        return string;
    }

    public static GradientDrawable createGradient(int color1, int color2, GradientDrawable.Orientation ort) {
        GradientDrawable gradientDrawable = new GradientDrawable(ort, new int[]{color1, color2});
        gradientDrawable.setCornerRadius(0.0f);
        return gradientDrawable;
    }

    public static String getPath(final Context context, final Uri uri) {
        Uri uri2 = null;
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(StringConstant.COLON);
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + StringConstant.SLASH + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (!TextUtils.isEmpty(documentId)) {
                try {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), null, null);
                } catch (NumberFormatException e) {
                    Log.i(TAG, e.getMessage());
                    return null;
                }
            }
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(StringConstant.COLON);
            String str = split2[0];
            if ("image".equals(str)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
        }
        return null;
    }

    
    
    
    
    
    
    
    
    
    
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Throwable th;
        IllegalArgumentException e;
        Cursor cursor;
        try {
            try {
                cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                            if (cursor != null) {
                                cursor.close();
                            }
                            return string;
                        }
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        Log.i(TAG, String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", e.getMessage()));
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }catch (Throwable th3) {

        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap enhanceImage(Bitmap mBitmap2, float contrast, float brightness) {
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{contrast, 0.0f, 0.0f, 0.0f, brightness, 0.0f, contrast, 0.0f, 0.0f, brightness, 0.0f, 0.0f, contrast, 0.0f, brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Bitmap createBitmap = Bitmap.createBitmap(mBitmap2.getWidth(), mBitmap2.getHeight(), mBitmap2.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(mBitmap2, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap updateSat(Bitmap src, float settingSat) {
        Bitmap createBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(settingSat);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(src, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap LRBitmap(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap UDBitmap(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Typeface getTypeface(Context mContext, String str) {
        return Typeface.createFromAsset(mContext.getAssets(), str);
    }

    public static void FS(Activity mActivity) {
        mActivity.getWindow().addFlags(1024);
    }

    public static void FS2(Activity mActivity) {
        mActivity.getWindow().getDecorView().setSystemUiVisibility(4102);
    }

    public static void HideKeyBoard(Activity mActivity) {
        mActivity.getWindow().setSoftInputMode(3);
    }

    public static void next(Context mContext, Class cls) {
        mContext.startActivity(new Intent(mContext, cls));
    }

    public static void nextwithnew(Context mContext, Class cls) {
        Intent intent = new Intent(mContext, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    
    
    public static void unzip(Activity mActivity, Class cls, File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    File file = new File(targetDirectory, nextEntry.getName());
                    File parentFile = nextEntry.isDirectory() ? file : file.getParentFile();
                    if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                        break;
                    }
                    if (!nextEntry.isDirectory()) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        while (true) {
                            int read = zipInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.close();
                    }
                } else if (mActivity == null) {
                    return;
                } else {
                    return;
                }
            }
        } finally {
            zipInputStream.close();
            if (mActivity != null) {
                Intent intent = new Intent(mActivity, cls);
                intent.putExtra(ClientCookie.PATH_ATTR, zipFile.getAbsolutePath().replace(".zip", ""));
                mActivity.startActivity(intent);
                mActivity.finish();
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getNavBarHeight(Context context) {
        int i;
        boolean hasPermanentMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean deviceHasKey = KeyCharacterMap.deviceHasKey(4);
        if (hasPermanentMenuKey || deviceHasKey) {
            return 0;
        }
        Resources resources = context.getResources();
        int i2 = resources.getConfiguration().orientation;
        String str = "navigation_bar_height";
        if (isTablet(context)) {
            if (i2 != 1) {
                str = "navigation_bar_height_landscape";
            }
            i = resources.getIdentifier(str, "dimen", "android");
        } else {
            if (i2 != 1) {
                str = "navigation_bar_width";
            }
            i = resources.getIdentifier(str, "dimen", "android");
        }
        if (i > 0) {
            return resources.getDimensionPixelSize(i);
        }
        return 0;
    }

    private static boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static int getRealHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
        try {
            return ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
        } catch (Exception unused) {
            defaultDisplay.getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
    }

    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 320;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (windowManager == null) {
            return 320;
        }
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static String getVideoDate(String path) {
        File file = new File(path);
        return file.exists() ? getDate(file.lastModified()) : "";
    }

    public static String getVideoDuration(String path) {
        String str = "";
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            str = getDuration(mediaPlayer.getDuration());
            mediaPlayer.reset();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String getTime(long l) {
        return DateFormat.format("hh:mm aa", new Date(l)).toString();
    }

    public static String getDuration(long val) {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(val)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(val) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(val))));
    }

    public static String getDate(long l) {
        return DateFormat.format("dd-MMM-yyyy", new Date(l)).toString();
    }

    public static String getDateTime(long l) {
        return DateFormat.format("dd-MMM-yyyy hh-mm-ss", new Date(l)).toString();
    }

    public static int getDay(long l) {
        DateFormat.format("dd", new Date(l)).toString();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l);
        return gregorianCalendar.get(5);
    }

    public static int getMonth(long l) {
        DateFormat.format("MM", new Date(l)).toString();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l);
        return gregorianCalendar.get(2);
    }

    public static String getMonth2(long l) {
        return DateFormat.format("MMM", new Date(l)).toString();
    }

    public static int getYear(long l) {
        return Integer.parseInt(DateFormat.format("yyyy", new Date(l)).toString());
    }

    public static int getHour(long l) {
        DateFormat.format("hh", new Date(l)).toString();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l);
        return gregorianCalendar.get(10);
    }

    public static int getMinutes(long l) {
        DateFormat.format("mm", new Date(l)).toString();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l);
        return gregorianCalendar.get(12);
    }

    public static boolean hasChar(String string) {
        return string.matches(".*[0-9].*") || string.matches(".*[A-Z].*") || string.matches(".*[a-z].*");
    }

    public static void setSize(View view, int width2, int height2, boolean b) {
        view.getLayoutParams().width = w(width2);
        if (b) {
            view.getLayoutParams().height = h(height2);
            return;
        }
        view.getLayoutParams().height = w(height2);
    }

    public static void gone(View view) {
        view.setVisibility(View.GONE);
    }

    public static void seekThumb(SeekBar sb, int color) {
        sb.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public static void visible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void invisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void setCenter(View view) {
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(13);
    }

    public static void setCenterHorizontal(View view) {
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(14);
    }

    public static void setCenterVertical(View view) {
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(15);
    }

    public static void setMargin(View view, int l, int t, int r, int b, boolean bo) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (bo) {
            marginLayoutParams.setMargins(w(l), h(t), w(r), h(b));
        } else {
            marginLayoutParams.setMargins(w(l), w(t), w(r), w(b));
        }
    }

    public static void set_share_rate(final Context mContext, View share, View rate) {
        share.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + mContext.getPackageName());
                mContext.startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
        rate.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                try {
                    Context context = mContext;
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + mContext.getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    Context context2 = mContext;
                    context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
                }
            }
        });
    }

    public static String getDay(int mYear, int mMonth, int mDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        switch (calendar.get(7)) {
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tue";
            case 4:
                return "Wed";
            case 5:
                return "Thu";
            case 6:
                return "Fri";
            case 7:
                return "Sat";
            default:
                return "";
        }
    }

    public static String getMonth(int mYear, int mMonth, int mDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        switch (calendar.get(2)) {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default:
                return "";
        }
    }

    public static void hideKeyBoard(Context mContext, View view) {
        ((InputMethodManager) mContext.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String capitaliseName(String name) {
        String[] split = name.split(StringConstant.SPACE);
        String str = "";
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim().toLowerCase();
            if (!split[i].isEmpty()) {
                str = str + split[i].substring(0, 1).toUpperCase() + split[i].substring(1) + StringConstant.SPACE;
            }
        }
        return str.trim();
    }

    public static String capitaliseOnlyFirstLetter(String data) {
        return data.substring(0, 1).toUpperCase() + data.substring(1);
    }

    public static String getThemePath(Context mContext) {
        return Environment.getExternalStorageDirectory() + "/Android/data/" + mContext.getPackageName() + "/files/.Theme/";
    }

    public static String getFolderPath(Context mContext) {
        return Environment.getExternalStorageDirectory() + StringConstant.SLASH + mContext.getResources().getString(R.string.app_name) + StringConstant.SLASH;
    }

    public static String getString(Context mContext, int val) {
        return mContext.getResources().getString(val);
    }

    public static String getFileSize(long l) {
        double d = l;
        Double.isNaN(d);
        Double.isNaN(d);
        double d2 = d / 1024.0d;
        double d3 = d2 / 1024.0d;
        if (d3 > 1.0d) {
            return new DecimalFormat("0.00").format(d3).concat("MB");
        }
        return new DecimalFormat("0").format(Math.round(d2)).concat("KB");
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static ProgressDialog setPD(Context mContext, String msg, boolean canCancel) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(canCancel);
        return progressDialog;
    }

    public static void setLay(View view, int width2, int height2, boolean b) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = w(width2);
        if (b) {
            layoutParams.height = h(height2);
        } else {
            layoutParams.width = w(height2);
        }
    }

    public static BitmapDrawable RepeatedBitmap(Context mContext, int res, Bitmap bit) {
        if (res != -1) {
            bit = BitmapFactory.decodeResource(mContext.getResources(), res);
        }
        int i = width;
        Bitmap.createScaledBitmap(bit, (i * 100) / 720, (i * 100) / 720, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bit);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return bitmapDrawable;
    }

    public static ArrayList<String> setList(Context mContext, String folder) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        try {
            for (String str : mContext.getAssets().list(folder)) {
                arrayList.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static int getColor(Context mContext, int color) {
        return mContext.getResources().getColor(color);
    }

    
    public static class AppPreferences {
        private static String PREFS_NAME;
        private final SharedPreferences.Editor editor;
        private final SharedPreferences preferences;
        String LockApps = "LockApps";
        String LockType = "LockType";
        String PatternLock = "PatternLock";
        String PinLock = "PinLock";
        String Question = "Question";
        String AAG = "AAG";
        String Answer = "Answer";

        public AppPreferences(Context context) {
            String string = context.getResources().getString(R.string.app_name);
            PREFS_NAME = string;
            SharedPreferences sharedPreferences = context.getSharedPreferences(string, 0);
            this.preferences = sharedPreferences;
            this.editor = sharedPreferences.edit();
        }

        public void setLockApps(String s) {
            this.editor.putString(this.LockApps, s);
            this.editor.commit();
        }

        public void setAAG(boolean b) {
            this.editor.putBoolean(this.AAG, b);
            this.editor.commit();
        }

        public boolean getAAG() {
            return this.preferences.getBoolean(this.AAG, false);
        }

        public String getLockApps() {
            return this.preferences.getString(this.LockApps, "");
        }

        public void setLockType(String s) {
            this.editor.putString(this.LockType, s);
            this.editor.commit();
        }

        public String getLockType() {
            return this.preferences.getString(this.LockType, "Pattern");
        }

        public void setPatternLock(String s) {
            this.editor.putString(this.PatternLock, s);
            this.editor.commit();
        }

        public String getPatternLock() {
            return this.preferences.getString(this.PatternLock, "");
        }

        public void setPinLock(String s) {
            this.editor.putString(this.PinLock, s);
            this.editor.commit();
        }

        public String getPinLock() {
            return this.preferences.getString(this.PinLock, "");
        }

        public void setQuestion(String s) {
            this.editor.putString(this.Question, s);
            this.editor.commit();
        }

        public String getQuestion() {
            return this.preferences.getString(this.Question, "");
        }

        public void setAnswer(String s) {
            this.editor.putString(this.Answer, s);
            this.editor.commit();
        }

        public String getAnswer() {
            return this.preferences.getString(this.Answer, "");
        }
    }
}

package com.demo.example.neonkeyboard.ledkeyboard.Constan;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Utils {
    private static final int[] mImages = {R.drawable.bg_theme1, R.drawable.bg_theme2, R.drawable.bg_theme3, R.drawable.bg_theme4, R.drawable.bg_theme5, R.drawable.bg_theme6, R.drawable.bg_theme7, R.drawable.bg_theme8, R.drawable.bg_theme9, R.drawable.bg_theme10, R.drawable.bg_theme11, R.drawable.bg_theme12};
    public static final ArrayList<Integer> mItems = new ArrayList<>();

    public static void load(Context context) {
        FileSerializer sWRX_FileSerializer = new FileSerializer();
        if (!sWRX_FileSerializer.openFileRead(context, "rgbkeyboard1.dat")) {
            mItems.clear();
            for (int i : mImages) {
                mItems.add(Integer.valueOf(i));
            }
            return;
        }
        mItems.clear();
        int readInt = sWRX_FileSerializer.readInt(0, 0);
        for (int i2 = 0; i2 < readInt; i2++) {
            mItems.add(Integer.valueOf(sWRX_FileSerializer.readInt(0, 0)));
        }
        sWRX_FileSerializer.close();
    }

    public static ArrayList<String> listAssetFolders(String folderName, Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String[] list = context.getAssets().list(folderName);
            if (list != null) {
                arrayList.addAll(Arrays.asList(list));
            }
        } catch (IOException unused) {
            Log.i("gf", "listAssetFolders: failed to load list");
        }
        return arrayList;
    }

    public static File getGifStorageDir() {
        File file;
        if (Build.VERSION.SDK_INT > 29) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MLEDKeyboard/.gifs");
        } else {
            file = new File(Environment.getExternalStorageDirectory() + "/MLEDKeyboard/.gifs");
        }
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        return null;
    }

    public static String makeFilename() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static Uri getImageContentUri(Context context, File file) {
        String absolutePath = file.getAbsolutePath();
        Cursor query = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{absolutePath}, null);
        if (query != null && query.moveToFirst()) {
            int i = query.getInt(query.getColumnIndex("_id"));
            query.close();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            return Uri.withAppendedPath(uri, "" + i);
        } else if (!file.exists()) {
            return null;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", absolutePath);
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }

    public static String getPath(Context context, Uri uri) {
        if (uri.getScheme().contains("content")) {
            Cursor query = context.getContentResolver().query(uri, null, null, null, null);
            if (query.moveToFirst()) {
                return query.getString(query.getColumnIndexOrThrow("_data"));
            }
            return null;
        } else if (uri.getScheme().contains("file")) {
            return uri.getPath();
        } else {
            return null;
        }
    }

    public static Bitmap loadBitmapFromUri(Context context, Uri uri) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
            if (openInputStream != null) {
                try {
                    openInputStream.close();
                } catch (IOException unused) {
                }
            }
            return decodeStream;
        } catch (FileNotFoundException unused2) {
            return null;
        }
    }

    public static boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = SoftKeyboard.mConnectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        return activeNetworkInfo.isConnected();
    }

    public static boolean isMyKeyboardEnabled(Context context) {
        for (InputMethodInfo inputMethodInfo : ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).getEnabledInputMethodList()) {
            if (inputMethodInfo.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMyKeyboardActive(Context context) {
        List<InputMethodInfo> enabledInputMethodList = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).getEnabledInputMethodList();
        String string = Settings.Secure.getString(context.getContentResolver(), "default_input_method");
        for (InputMethodInfo inputMethodInfo : enabledInputMethodList) {
            if (inputMethodInfo.getPackageName().equals(context.getPackageName()) && inputMethodInfo.getId().equals(string)) {
                return true;
            }
        }
        return false;
    }

    public static String stringToBase64(String str) {
        return Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), 0);
    }

    public static boolean isMIUI() {
        return Build.MANUFACTURER.equals("Xiaomi");
    }

    public static boolean isDisplayPopupWindowsPermissionGranted(Context context) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            return ((Integer) appOpsManager.getClass().getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, 10021, Integer.valueOf(Process.myUid()), context.getPackageName())).intValue() == 0;
        } catch (Exception unused) {
            return true;
        }
    }

    public static void openOtherPermissionSettings(Context context, Activity activity, int i) {
        try {
            try {
                try {
                    Intent intent = new Intent();
                    intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.putExtra("extra_pkgname", context.getPackageName());
                    activity.startActivityForResult(intent, i);
                } catch (Exception unused) {
                    Intent intent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    intent2.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    intent2.putExtra("extra_pkgname", context.getPackageName());
                    activity.startActivityForResult(intent2, i);
                }
            } catch (Exception unused2) {
            }
        } catch (Exception unused3) {
            Intent intent3 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent3.setData(Uri.fromParts("package", context.getPackageName(), null));
            activity.startActivityForResult(intent3, i);
        }
    }
}

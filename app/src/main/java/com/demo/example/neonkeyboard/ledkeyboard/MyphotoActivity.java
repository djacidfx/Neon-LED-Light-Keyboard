package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MyphotoActivity extends AppCompatActivity {
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 7.5f;
    private FrameLayout adContainerView;
    int blurOption;
    RadioButton btn_customtheme;
    RadioButton btn_phototheme;
    CardView card_myPhoto;
    Context context;
    RadioGroup grpBlur;
    ImageView img_disable;
    ImageView img_myPhoto;
    boolean isDefaultTheme;
    boolean isMyPhotoHere;
    RadioGroup radiogrp_photo;
    RadioButton rb_default;
    RadioButton rb_full;
    RadioButton rb_medium;
    SharedPreferences sp;

    
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_myphoto);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.FullscreenAd(this);

        this.context = this;
        this.radiogrp_photo = (RadioGroup) findViewById(R.id.radiogrp_photo);
        this.grpBlur = (RadioGroup) findViewById(R.id.grpBlur);
        this.rb_default = (RadioButton) findViewById(R.id.rb_default);
        this.rb_medium = (RadioButton) findViewById(R.id.rb_medium);
        this.rb_full = (RadioButton) findViewById(R.id.rb_full);
        this.img_disable = (ImageView) findViewById(R.id.img_disable);
        this.btn_customtheme = (RadioButton) findViewById(R.id.btn_customtheme);
        this.btn_phototheme = (RadioButton) findViewById(R.id.btn_phototheme);
        this.card_myPhoto = (CardView) findViewById(R.id.card_myPhoto);
        this.img_myPhoto = (ImageView) findViewById(R.id.img_myPhoto);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.sp = defaultSharedPreferences;
        this.isDefaultTheme = defaultSharedPreferences.getBoolean("isDefaultTheme", true);
        this.isMyPhotoHere = this.sp.getBoolean("isMyPhotoHere", false);
        this.blurOption = this.sp.getInt("blurOption", 1);
        if (this.isDefaultTheme) {
            this.btn_customtheme.setChecked(true);
            this.img_disable.setVisibility(View.VISIBLE);
            this.card_myPhoto.setEnabled(false);
            this.rb_default.setEnabled(false);
            this.rb_medium.setEnabled(false);
            this.rb_full.setEnabled(false);
        } else {
            this.btn_phototheme.setChecked(true);
            this.img_disable.setVisibility(View.GONE);
            this.card_myPhoto.setEnabled(true);
            this.rb_default.setEnabled(true);
            this.rb_medium.setEnabled(true);
            this.rb_full.setEnabled(true);
        }
        int i = this.blurOption;
        if (i == 1) {
            this.rb_default.setChecked(true);
        } else if (i == 2) {
            this.rb_medium.setChecked(true);
        } else if (i == 3) {
            this.rb_full.setChecked(true);
        }
        if (this.isMyPhotoHere) {
            int i2 = this.blurOption;
            if (i2 == 2 || i2 == 3) {
                this.img_myPhoto.setImageBitmap(stringToBitmap(this.sp.getString("savedMyBlur", "")));
            } else {
                this.img_myPhoto.setImageBitmap(stringToBitmap(this.sp.getString("savedMyPhoto", "")));
            }
        }
        this.card_myPhoto.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    Intent intent = new Intent("android.intent.action.PICK");
                    intent.setType("image/*");
                    MyphotoActivity.this.startActivityForResult(intent, PointerIconCompat.TYPE_GRABBING);
                } else if (Build.VERSION.SDK_INT >= 33) {
                    Intent intent = new Intent("android.intent.action.PICK");
                    intent.setType("image/*");
                    MyphotoActivity.this.startActivityForResult(intent, PointerIconCompat.TYPE_GRABBING);
                }  else if (ContextCompat.checkSelfPermission(MyphotoActivity.this.context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(MyphotoActivity.this.context, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                    Intent intent2 = new Intent("android.intent.action.PICK");
                    intent2.setType("image/*");
                    MyphotoActivity.this.startActivityForResult(intent2, PointerIconCompat.TYPE_GRABBING);
                } else {
                    ActivityCompat.requestPermissions(MyphotoActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 234);
                }
            }
        });
        this.radiogrp_photo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_customtheme :
                        MyphotoActivity.this.setBooll("isDefaultTheme", true);
                        MyphotoActivity.this.img_disable.setVisibility(View.VISIBLE);
                        MyphotoActivity.this.card_myPhoto.setEnabled(false);
                        MyphotoActivity.this.rb_default.setEnabled(false);
                        MyphotoActivity.this.rb_medium.setEnabled(false);
                        MyphotoActivity.this.rb_full.setEnabled(false);
                        return;
                    case R.id.btn_phototheme :
                        MyphotoActivity sWRX_MyphotoActivity = MyphotoActivity.this;
                        sWRX_MyphotoActivity.isMyPhotoHere = sWRX_MyphotoActivity.sp.getBoolean("isMyPhotoHere", false);
                        if (MyphotoActivity.this.isMyPhotoHere) {
                            MyphotoActivity.this.setBooll("isDefaultTheme", false);
                        }
                        MyphotoActivity.this.img_disable.setVisibility(View.GONE);
                        MyphotoActivity.this.card_myPhoto.setEnabled(true);
                        MyphotoActivity.this.rb_default.setEnabled(true);
                        MyphotoActivity.this.rb_medium.setEnabled(true);
                        MyphotoActivity.this.rb_full.setEnabled(true);
                        return;
                    default:
                        return;
                }
            }
        });
        this.grpBlur.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Bitmap stringToBitmap = MyphotoActivity.stringToBitmap(MyphotoActivity.this.sp.getString("savedMyPhoto", ""));
                switch (checkedId) {
                    case R.id.rb_default :
                        MyphotoActivity sWRX_MyphotoActivity = MyphotoActivity.this;
                        sWRX_MyphotoActivity.isMyPhotoHere = sWRX_MyphotoActivity.sp.getBoolean("isMyPhotoHere", false);
                        if (MyphotoActivity.this.isMyPhotoHere) {
                            MyphotoActivity.this.img_myPhoto.setImageBitmap(stringToBitmap);
                            MyphotoActivity.this.setINnteGer("blurOption", 1);
                            return;
                        }
                        return;
                    case R.id.rb_full :
                        MyphotoActivity sWRX_MyphotoActivity2 = MyphotoActivity.this;
                        sWRX_MyphotoActivity2.isMyPhotoHere = sWRX_MyphotoActivity2.sp.getBoolean("isMyPhotoHere", false);
                        if (MyphotoActivity.this.isMyPhotoHere) {
                            Bitmap blurImage = MyphotoActivity.blurImage(MyphotoActivity.this.context, stringToBitmap, 0.2f);
                            MyphotoActivity.this.img_myPhoto.setImageBitmap(blurImage);
                            MyphotoActivity.this.setStrrint("savedMyBlur", MyphotoActivity.encodeTobase64(blurImage));
                            MyphotoActivity.this.setINnteGer("blurOption", 3);
                            return;
                        }
                        return;
                    case R.id.rb_medium :
                        MyphotoActivity sWRX_MyphotoActivity3 = MyphotoActivity.this;
                        sWRX_MyphotoActivity3.isMyPhotoHere = sWRX_MyphotoActivity3.sp.getBoolean("isMyPhotoHere", false);
                        if (MyphotoActivity.this.isMyPhotoHere) {
                            Bitmap blurImage2 = MyphotoActivity.blurImage(MyphotoActivity.this.context, stringToBitmap, MyphotoActivity.BITMAP_SCALE);
                            MyphotoActivity.this.img_myPhoto.setImageBitmap(blurImage2);
                            MyphotoActivity.this.setStrrint("savedMyBlur", MyphotoActivity.encodeTobase64(blurImage2));
                            MyphotoActivity.this.setINnteGer("blurOption", 2);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                MyphotoActivity.this.onBackPressed();
            }
        });
        HelperResize.setSize(imageView, 90, 90, true);
    }

    
    @Override
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null && requestCode == 1021) {
            CropImage.activity(data.getData()).setAspectRatio(720, 444).start(this);
        }
        if (requestCode == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                Uri uri = activityResult.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Glide.with((FragmentActivity) this).load(uri).into(this.img_myPhoto);
                    setBooll("isMyPhotoHere", true);
                    setBooll("isDefaultTheme", false);
                    setINnteGer("blurOption", 1);
                    this.rb_default.setChecked(true);
                    setStrrint("savedMyPhoto", encodeTobase64(bitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == 204) {
                activityResult.getError();
            }
        }
    }

    @Override 
    public void onBackPressed() {

        finish();

    }

    public static Bitmap blurImage(Context context, Bitmap image, float blurOpacity) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(image, Math.round(image.getWidth() * blurOpacity), Math.round(image.getHeight() * blurOpacity), false);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap);
        RenderScript create = RenderScript.create(context);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        Allocation createFromBitmap = Allocation.createFromBitmap(create, createScaledBitmap);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        create2.setRadius(BLUR_RADIUS);
        create2.setInput(createFromBitmap);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        return createBitmap;
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        Log.d("Image Log:", encodeToString);
        return encodeToString;
    }

    public static Bitmap stringToBitmap(String input) {
        byte[] decode = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    public static String getPathImage(Context context, Uri uri) {
        String[] strArr = {"_data"};
        Cursor query = context.getContentResolver().query(uri, strArr, null, null, null);
        String str = null;
        if (query != null) {
            if (query.moveToFirst()) {
                str = query.getString(query.getColumnIndexOrThrow(strArr[0]));
            }
            query.close();
        }
        return str == null ? "Not found" : str;
    }

    public void setBooll(String key, boolean vale) {
        SharedPreferences.Editor edit = this.sp.edit();
        edit.putBoolean(key, vale);
        edit.apply();
    }

    public void setStrrint(String key, String vale) {
        SharedPreferences.Editor edit = this.sp.edit();
        edit.putString(key, vale);
        edit.apply();
    }

    public void setINnteGer(String key, int vale) {
        SharedPreferences.Editor edit = this.sp.edit();
        edit.putInt(key, vale);
        edit.apply();
    }


}

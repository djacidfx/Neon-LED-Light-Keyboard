package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chootdev.recycleclick.RecycleClick;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.Constan.Utils;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;

import java.util.ArrayList;


public class FontActivity extends AppCompatActivity {
    private FrameLayout adContainerView;
    FontAdapter fontAdapter;
    private ArrayList<String> fontsList = new ArrayList<>();
    boolean iAmFromInside = true;
    ImageView img_back;
    RecyclerView rv_font;
    SharedPreferences sp;
    ImageView switch_font;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_font);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd(this);


        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 852) / 1080, (getResources().getDisplayMetrics().heightPixels * 555) / 1920);
        layoutParams.gravity = 17;

        this.img_back = (ImageView) findViewById(R.id.img_back);
        this.switch_font = (ImageView) findViewById(R.id.switch_font);
        this.iAmFromInside = getIntent().getBooleanExtra("iAmFromInside", true);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sp = defaultSharedPreferences;
        if (Boolean.valueOf(defaultSharedPreferences.getBoolean("isMyFont", false)).booleanValue()) {
            this.switch_font.setImageResource(R.drawable.on2);
        } else {
            this.switch_font.setImageResource(R.drawable.off2);
        }
        this.img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FontActivity.this.onBackPressed();
            }
        });
        this.switch_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Boolean.valueOf(FontActivity.this.sp.getBoolean("isMyFont", false)).booleanValue()) {
                    SoftKeyboard.isMyFont = false;
                    FontActivity.this.setBoolPref("isMyFont", false);
                    FontActivity.this.switch_font.setImageResource(R.drawable.off2);
                } else {
                    SoftKeyboard.isMyFont = true;
                    FontActivity.this.setBoolPref("isMyFont", true);
                    FontActivity.this.switch_font.setImageResource(R.drawable.on2);
                }
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        this.fontsList = Utils.listAssetFolders("font", this);
        this.rv_font = (RecyclerView) findViewById(R.id.rv_font);
        this.rv_font.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        FontAdapter fontAdapter = new FontAdapter(this);
        this.fontAdapter = fontAdapter;
        this.rv_font.setAdapter(fontAdapter);
        RecycleClick.addTo(this.rv_font).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                FontActivity.this.fontAdapter.notifyItemChanged(SoftKeyboard.fontNumber);
                SoftKeyboard.fontNumber = position;
                FontActivity.this.fontAdapter.notifyItemChanged(SoftKeyboard.fontNumber);
                SharedPreferences.Editor edit = FontActivity.this.sp.edit();
                edit.putInt("fontNumber", position);
                edit.apply();
                if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
            }
        });
        HelperResize.setSize(this.img_back, 90, 90, true);
        HelperResize.setSize(this.switch_font, 90, 47, true);
    }


    private class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {
        Context context;

        public FontAdapter(Context fontActivity) {
            this.context = fontActivity;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_font, parent, false));
        }

        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.txt_fontName.setText((CharSequence) FontActivity.this.fontsList.get(position));
            AssetManager assets = FontActivity.this.getAssets();
            holder.txt_fontName.setTypeface(Typeface.createFromAsset(assets, "font/" + ((String) FontActivity.this.fontsList.get(position))));
            if (SoftKeyboard.fontNumber == position) {
                holder.img_font_checked.setVisibility(View.VISIBLE);
            } else {
                holder.img_font_checked.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return FontActivity.this.fontsList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img_font_checked;
            TextView txt_fontName;

            public ViewHolder(View itemView) {
                super(itemView);
                this.img_font_checked = (ImageView) itemView.findViewById(R.id.img_font_checked);
                this.txt_fontName = (TextView) itemView.findViewById(R.id.txt_fontName);
                HelperResize.setSize(this.img_font_checked, 74, 74, true);
                HelperResize.setSize((ImageView) itemView.findViewById(R.id.divider), 1080, 2, true);
            }
        }
    }

    public void setBoolPref(String key, boolean val) {
        SharedPreferences.Editor edit = this.sp.edit();
        edit.putBoolean(key, val);
        edit.apply();
    }

    @Override
    public void onBackPressed() {

        finish();

    }

}

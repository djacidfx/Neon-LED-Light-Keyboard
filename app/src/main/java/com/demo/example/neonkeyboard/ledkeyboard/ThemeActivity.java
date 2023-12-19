package com.demo.example.neonkeyboard.ledkeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chootdev.recycleclick.RecycleClick;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;

import cz.msebera.android.httpclient.HttpStatus;


public class ThemeActivity extends AppCompatActivity {
    private FrameLayout adContainerView;
    RecyclerView rv_theme;
    ThemeAdapter themeAdapter;
    private int[] themeDemo = {R.drawable.a1_light, R.drawable.a2_dark, R.drawable.a3_rgb, R.drawable.a4_sunny, R.drawable.a5_floral, R.drawable.a6_feather, R.drawable.a7_galaxy, R.drawable.a8_fire, R.drawable.a9_blue, R.drawable.a10_rainbow, R.drawable.a11_chocolate, R.drawable.a12_smoke};


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        HelperResize.width = getResources().getDisplayMetrics().widthPixels;
        HelperResize.height = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.activity_theme);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd(this);


        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 852) / 1080, (getResources().getDisplayMetrics().heightPixels * 555) / 1920);
        layoutParams.gravity = 17;

        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeActivity.this.onBackPressed();
            }
        });
        HelperResize.setSize(imageView, 90, 90, true);
        this.rv_theme = (RecyclerView) findViewById(R.id.rv_theme);
        this.rv_theme.setLayoutManager(new GridLayoutManager((Context) this, 2, RecyclerView.VERTICAL, false));
        ThemeAdapter themeAdapter = new ThemeAdapter(this);
        this.themeAdapter = themeAdapter;
        this.rv_theme.setAdapter(themeAdapter);
        RecycleClick.addTo(this.rv_theme).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                ThemeActivity sWRX_ThemeActivity = ThemeActivity.this;
                sWRX_ThemeActivity.themeAdapter.notifyItemChanged(SoftKeyboard.themeNumber);
                SoftKeyboard.themeNumber = position;
                ThemeActivity.this.themeAdapter.notifyItemChanged(SoftKeyboard.themeNumber);
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(sWRX_ThemeActivity).edit();
                edit.putInt("wallpaper_ind", SoftKeyboard.themeNumber);
                edit.apply();
                if (!(SoftKeyboard.Instance == null || SoftKeyboard.Instance.mInputView == null)) {
                    SoftKeyboard.Instance.mInputView.invalidate();
                }
                int i = PreferenceManager.getDefaultSharedPreferences(sWRX_ThemeActivity).getInt("roundness", 4);
                int i2 = PreferenceManager.getDefaultSharedPreferences(sWRX_ThemeActivity).getInt("dummyRound", 4);
                if (position == 5) {
                    SharedPreferences.Editor edit2 = PreferenceManager.getDefaultSharedPreferences(sWRX_ThemeActivity).edit();
                    edit2.putInt("dummyRound", i);
                    edit2.putInt("roundness", 14);
                    edit2.apply();
                    SoftKeyboard.SettingsRoundness = 14;
                    return;
                }
                SharedPreferences.Editor edit3 = PreferenceManager.getDefaultSharedPreferences(sWRX_ThemeActivity).edit();
                edit3.putInt("roundness", i2);
                edit3.apply();
                SoftKeyboard.SettingsRoundness = i2;
            }
        });
    }


    private class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
        Context context;

        public ThemeAdapter(ThemeActivity themeActivity) {
            this.context = themeActivity;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_theme, parent, false));
        }

        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.img_theme.setImageResource(ThemeActivity.this.themeDemo[position]);
            if (SoftKeyboard.themeNumber == position) {
                holder.img_theme_checked.setVisibility(View.VISIBLE);
            } else {
                holder.img_theme_checked.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return ThemeActivity.this.themeDemo.length;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img_theme;
            ImageView img_theme_checked;
            RelativeLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);
                this.img_theme = (ImageView) itemView.findViewById(R.id.img_theme);
                this.img_theme_checked = (ImageView) itemView.findViewById(R.id.img_theme_checked);
                RelativeLayout relativeLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
                this.layout = relativeLayout;
                HelperResize.setSize(relativeLayout, 301, 420, true);
                HelperResize.setSize(this.img_theme, 301, HttpStatus.SC_EXPECTATION_FAILED, true);
                HelperResize.setSize(this.img_theme_checked, 102, 101, true);
            }
        }
    }

    @Override
    public void onBackPressed() {

        finish();

    }


}

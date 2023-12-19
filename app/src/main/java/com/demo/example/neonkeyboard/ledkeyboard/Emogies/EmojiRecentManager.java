package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class EmojiRecentManager extends ArrayList<EmojiData> {
    private static final Object LOCK = new Object();
    private static final String PREFERENCE_NAME = "cpk_emoji";
    private static final String PREF_PAGE = "cpk_recent_page";
    private static final String PREF_RECENT = "cpk_recent_emojis";
    private static EmojiRecentManager sInstance;
    private Context mContext;

    private EmojiRecentManager(Context context) {
        this.mContext = context.getApplicationContext();
        loadRecent();
    }

    
    public static EmojiRecentManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new EmojiRecentManager(context);
                }
            }
        }
        return sInstance;
    }

    public int getRecentPage() {
        return getPreferences().getInt(PREF_PAGE, 0);
    }

    public void setRecentPage(int i) {
        getPreferences().edit().putInt(PREF_PAGE, i).apply();
    }

    public void push(EmojiData emoji) {
        if (contains(emoji)) {
            super.remove(emoji);
        }
        add(0, emoji);
    }

    public boolean add(EmojiData emoji) {
        return super.add( emoji);
    }

    public void add(int i, EmojiData emoji) {
        super.add(i, emoji);
    }

    @Override 
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    private SharedPreferences getPreferences() {
        return this.mContext.getSharedPreferences(PREFERENCE_NAME, 0);
    }

    private void loadRecent() {
        StringTokenizer stringTokenizer = new StringTokenizer(getPreferences().getString(PREF_RECENT, ""), "~");
        while (stringTokenizer.hasMoreTokens()) {
            try {
                add(new EmojiData(stringTokenizer.nextToken()));
            } catch (NumberFormatException unused) {
            }
        }
    }

    public void saveRecent() {
        StringBuilder sb = new StringBuilder();
        int size = size();
        for (int i = 0; i < size; i++) {
            sb.append(get(i).getEmoji());
            if (i < size - 1) {
                sb.append('~');
            }
        }
        getPreferences().edit().putString(PREF_RECENT, sb.toString()).apply();
    }
}

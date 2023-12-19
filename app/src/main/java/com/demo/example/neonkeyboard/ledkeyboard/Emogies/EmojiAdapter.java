package com.demo.example.neonkeyboard.ledkeyboard.Emogies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.demo.example.neonkeyboard.R;

import java.util.List;


class EmojiAdapter extends ArrayAdapter<EmojiData> {
    public EmojiGridView.OnEmojiClickedListener emojiClickListener;

    
    static class ViewHolder {
        TextView mIcon;

        ViewHolder() {
        }
    }

    
    public EmojiAdapter(Context context, List<EmojiData> list) {
        super(context, (int) R.layout.item_emojis, list);
    }

    
    public EmojiAdapter(Context context, EmojiData[] emojiArr) {
        super(context, (int) R.layout.item_emojis, emojiArr);
    }

    public void setEmojiClickListener(EmojiGridView.OnEmojiClickedListener onEmojiClickedListener) {
        this.emojiClickListener = onEmojiClickedListener;
    }

    @Override 
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.item_emojis, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mIcon = (TextView) view.findViewById(R.id.iconEmojiItem);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder2 = (ViewHolder) view.getTag();
        viewHolder2.mIcon.setText(getItem(i).getEmoji());
        viewHolder2.mIcon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view2) {
                EmojiAdapter.this.emojiClickListener.onEmojiClicked(EmojiAdapter.this.getItem(i));
            }
        });
        return view;
    }
}

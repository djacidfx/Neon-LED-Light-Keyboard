package com.demo.example.neonkeyboard.ledkeyboard.MyGif;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.collection.ArrayMap;


import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.holder.GifNoResultsVH;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.holder.GifRVItem;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.holder.GifSearchItemVH;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.IFetchGifDimension;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.ITrendingView;
import com.tenor.android.core.model.impl.Result;
import com.tenor.android.core.util.AbstractListUtils;
import com.tenor.android.core.widget.adapter.AbstractRVItem;
import com.tenor.android.core.widget.adapter.ListRVAdapter;
import com.tenor.android.core.widget.viewholder.StaggeredGridLayoutItemViewHolder;

import java.util.List;
import java.util.Map;
import java.util.Stack;


public class TrendingAdapter<CTX extends ITrendingView> extends ListRVAdapter<CTX, AbstractRVItem, StaggeredGridLayoutItemViewHolder<CTX>> implements IFetchGifDimension {
    private static final String ID_ITEM_NO_RESULT = "ID_ITEM_NO_RESULT";
    private static final AbstractRVItem NO_RESULT_ITEM = new AbstractRVItem(0, ID_ITEM_NO_RESULT) { 
    };
    private final Map<String, Integer> mHeights = new ArrayMap();






    public TrendingAdapter(CTX ctx) {
        super(ctx);
    }

    
    
    @Override 
    public StaggeredGridLayoutItemViewHolder<CTX> onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if (i == 0) {
            return new GifNoResultsVH(from.inflate(R.layout.item_no_giphy, viewGroup, false), getRef());
        }
        return new GifSearchItemVH(from.inflate(R.layout.item_giphy, viewGroup, false), getRef());
    }

    @Override
    public void onBindViewHolder(StaggeredGridLayoutItemViewHolder<CTX> staggeredGridLayoutItemViewHolder, int i) {
        if (staggeredGridLayoutItemViewHolder instanceof GifNoResultsVH) {
            ((GifNoResultsVH) staggeredGridLayoutItemViewHolder).setFullWidthWithHeight();
        } else if (staggeredGridLayoutItemViewHolder instanceof GifSearchItemVH) {
            GifSearchItemVH gifSearchItemVH = (GifSearchItemVH) staggeredGridLayoutItemViewHolder;
            if (getList().get(i) instanceof GifRVItem) {
                GifRVItem gifRVItem = (GifRVItem) getList().get(i);
                if (gifRVItem.get() instanceof Result) {
                    if (this.mHeights.containsKey(gifRVItem.getId())) {
                        gifSearchItemVH.setHeightInPixel(this.mHeights.get(gifRVItem.getId()).intValue());
                    } else {
                        gifSearchItemVH.setFetchGifHeightListener(this);
                        gifSearchItemVH.setupViewHolder((Result) gifRVItem.get(), 1);
                    }
                    gifSearchItemVH.renderGif((Result) gifRVItem.get(), i);
                }
            }
        }
    }

    @Override 
    public int getItemViewType(int i) {
        return getList().get(i).getType();
    }

    @Override 
    public int getItemCount() {
        return getList().size();
    }

    @Override 
    public void insert(List<AbstractRVItem> list, boolean z) {
        if (!AbstractListUtils.isEmpty(list) || z) {
            if (!z) {
                threadSafeRemove(new IThreadSafeConditions<AbstractRVItem>() { 
                    @Override
                    
                    public void onItemsRemoved(Stack<Integer> stack) {
                    }

                    public boolean removeIf(AbstractRVItem abstractRVItem) {
                        return abstractRVItem.getType() != 1;
                    }
                });
                this.mHeights.clear();
            }
            if (!AbstractListUtils.isEmpty(list) && (list.get(0) instanceof GifRVItem)) {
                getList().addAll(list);
                int itemCount = getItemCount();
                if (!z) {
                    notifyDataSetChanged();
                } else {
                    notifyItemRangeInserted(itemCount, list.size());
                }
            }
        } else {
            notifyListEmpty();
        }
    }

    public void notifyListEmpty() {
        clearList();
        this.mHeights.clear();
        getList().add(NO_RESULT_ITEM);
        notifyDataSetChanged();
    }

    @Override 
    public void onReceiveViewHolderDimension(String str, int i, int i2, int i3) {
        if (i3 == 1) {
            this.mHeights.put(str, Integer.valueOf(i2));
        }
    }
}

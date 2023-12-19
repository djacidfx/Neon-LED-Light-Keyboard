package com.demo.example.neonkeyboard.ledkeyboard.MyGif;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bumptech.glide.Glide;

import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.holder.GifRVItem;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.IGifSearchPresenter;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.IGifSearchView;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.ITrendingPresenter;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.ITrendingView;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.impl.GifSearchPresenter;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.impl.TrendingPresenter;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.widget.GifSearchItemDecoration;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.widget.GiphyGridLayout;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.demo.example.neonkeyboard.ledkeyboard.utils.HelperResize;
import com.tenor.android.core.model.impl.Result;
import com.tenor.android.core.response.BaseError;
import com.tenor.android.core.response.impl.GifsResponse;
import com.tenor.android.core.response.impl.TrendingGifResponse;
import com.tenor.android.core.util.AbstractLayoutManagerUtils;
import com.tenor.android.core.util.AbstractListUtils;
import com.tenor.android.core.util.AbstractUIUtils;
import com.tenor.android.core.weakref.WeakRefOnScrollListener;
import com.tenor.android.core.widget.adapter.AbstractRVItem;
import java.util.ArrayList;
import java.util.List;


public class GiphyPopup extends PopupWindow implements ITrendingView, IGifSearchView {
    public static boolean mVisible = false;
    ImageView img_gif_search;
    public String keyOfSearch;
    private final Context mContext;
    private final View mEmojiView;
    public boolean mIsLoadingMore;
    private RecyclerView mRecyclerView;
    private final View mRootView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    FrameLayout mask_view;
    public ProgressBar progress_gif_botm;
    public ProgressBar progress_gif_main;
    ImageView progressgif;
    TextView txt_gif1;
    TextView txt_gif10;
    TextView txt_gif11;
    TextView txt_gif12;
    TextView txt_gif13;
    TextView txt_gif14;
    TextView txt_gif15;
    TextView txt_gif16;
    TextView txt_gif17;
    TextView txt_gif18;
    TextView txt_gif19;
    TextView txt_gif2;
    TextView txt_gif20;
    TextView txt_gif21;
    TextView txt_gif22;
    TextView txt_gif23;
    TextView txt_gif24;
    TextView txt_gif25;
    TextView txt_gif26;
    TextView txt_gif27;
    TextView txt_gif28;
    TextView txt_gif29;
    TextView txt_gif3;
    TextView txt_gif30;
    TextView txt_gif31;
    TextView txt_gif32;
    TextView txt_gif33;
    TextView txt_gif34;
    TextView txt_gif35;
    TextView txt_gif36;
    TextView txt_gif37;
    TextView txt_gif38;
    TextView txt_gif39;
    TextView txt_gif4;
    TextView txt_gif40;
    TextView txt_gif5;
    TextView txt_gif6;
    TextView txt_gif7;
    TextView txt_gif8;
    TextView txt_gif9;
    TextView txt_gif_trend;
    private String mNextPageId = "";
    public boolean isSearched = false;
    private ITrendingPresenter mTrendingPresenter = new TrendingPresenter(this);
    private IGifSearchPresenter mSearchPresenter = new GifSearchPresenter(this);
    private TrendingAdapter mTrendingAdapter = new TrendingAdapter(this);
    public GiphyGridLayout mStaggeredGridLayoutManager = new GiphyGridLayout(3, 1);

    public GiphyPopup(View view, Context context) {
        super(context);
        this.mContext = context;
        this.mRootView = view;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_giphy, (ViewGroup) null, false);
        this.mEmojiView = inflate;
        ImageView imageView = (ImageView) inflate.findViewById(R.id.close_giphy);
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view2) {
                GiphyPopup.this.dismiss();
            }
        });
        HelperResize.setSize(imageView, 33, 33, true);
        this.progress_gif_main = (ProgressBar) inflate.findViewById(R.id.progress_gif_main);
        this.progressgif = (ImageView) inflate.findViewById(R.id.gif);
        Glide.with(context).load(Integer.valueOf((int) R.drawable.gif_loading)).asGif().into(this.progressgif);
        this.progress_gif_botm = (ProgressBar) inflate.findViewById(R.id.progress_gif_botm);
        this.mask_view = (FrameLayout) inflate.findViewById(R.id.mask_view);
        this.img_gif_search = (ImageView) inflate.findViewById(R.id.img_gif_search);
        this.mRecyclerView = (RecyclerView) inflate.findViewById(R.id.as_rv_recyclerview);
        this.mRecyclerView.addItemDecoration(new GifSearchItemDecoration(AbstractUIUtils.dpToPx(context, 1.0f)));
        this.mRecyclerView.setAdapter(this.mTrendingAdapter);
        this.mRecyclerView.setLayoutManager(this.mStaggeredGridLayoutManager);
        this.mRecyclerView.addOnScrollListener(new WeakRefOnScrollListener(this) { 
            @Override 
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (i2 > 0) {
                    int itemCount = recyclerView.getLayoutManager().getItemCount();
                    int findLastVisibleItemPosition = AbstractLayoutManagerUtils.findLastVisibleItemPosition(GiphyPopup.this.mStaggeredGridLayoutManager);
                    int spanCount = AbstractLayoutManagerUtils.getSpanCount(recyclerView.getLayoutManager());
                    if (!GiphyPopup.this.mIsLoadingMore && itemCount <= findLastVisibleItemPosition + (spanCount * 3)) {
                        GiphyPopup.this.mIsLoadingMore = true;
                        GiphyPopup.this.progress_gif_botm.setVisibility(View.VISIBLE);
                        if (!GiphyPopup.this.isSearched) {
                            GiphyPopup.this.getTrending(true);
                        } else if (!TextUtils.isEmpty(GiphyPopup.this.keyOfSearch)) {
                            GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                            sWRX_GiphyPopup.performSearch(sWRX_GiphyPopup.keyOfSearch, true);
                        }
                    }
                }
            }
        });
        if (!this.isSearched) {
            getTrending(false);
        } else if (!TextUtils.isEmpty(this.keyOfSearch)) {
            performSearch(this.keyOfSearch, false);
        }
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swiperefresh);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeColors(context.getResources().getColor(R.color.colorAccent));
        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { 
            @Override 
            public void onRefresh() {
                GiphyPopup.this.mSwipeRefreshLayout.setRefreshing(false);
                GiphyPopup.this.mask_view.setVisibility(View.VISIBLE);
                GiphyPopup.this.progress_gif_main.setVisibility(View.GONE);
                GiphyPopup.this.progressgif.setVisibility(View.VISIBLE);
                if (!GiphyPopup.this.isSearched) {
                    GiphyPopup.this.getTrending(false);
                } else if (!TextUtils.isEmpty(GiphyPopup.this.keyOfSearch)) {
                    GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                    sWRX_GiphyPopup.performSearch(sWRX_GiphyPopup.keyOfSearch, false);
                }
            }
        });
        this.img_gif_search.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                SoftKeyboard.Instance.r_search_giphy.setVisibility(View.VISIBLE);
                SoftKeyboard.Instance.et_search_gif.requestFocus();
                SoftKeyboard.Instance.et_search_gif.hasFocus();
                GiphyPopup.this.dismiss();
            }
        });
        allCatClick();
        setContentView(inflate);
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public void seachCategory(TextView view) {
        this.mask_view.setVisibility(View.VISIBLE);
        this.progress_gif_main.setVisibility(View.GONE);
        this.progressgif.setVisibility(View.VISIBLE);
        this.isSearched = true;
        String trim = view.getText().toString().trim();
        this.keyOfSearch = trim;
        if (!TextUtils.isEmpty(trim)) {
            performSearch(this.keyOfSearch, false);
        }
    }

    public void showAtBottom() {
        showAtLocation(this.mRootView, 80, 0, 0);
        mVisible = true;
        if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
            SoftKeyboard.Instance.mInputView.invalidate();
        }
    }

    @Override 
    public void dismiss() {
        super.dismiss();
        mVisible = false;
        if (SoftKeyboard.Instance != null && SoftKeyboard.Instance.mInputView != null) {
            SoftKeyboard.Instance.mInputView.invalidate();
        }
    }

    @Override 
    public Context getContext() {
        return this.mContext;
    }

    public void getTrending(boolean z) {
        if (!z) {
            this.mNextPageId = "";
            this.mTrendingAdapter.clearList();
            this.mTrendingAdapter.notifyDataSetChanged();
        }
        this.mTrendingPresenter.getTrending(36, this.mNextPageId, z);
    }

    public void performSearch(String str, boolean z) {
        if (!z) {
            this.mNextPageId = "";
            this.mTrendingAdapter.clearList();
            this.mTrendingAdapter.notifyDataSetChanged();
        }
        if (!TextUtils.isEmpty(str)) {
            this.mSearchPresenter.search(str, 36, this.mNextPageId, z);
        }
    }

    @Override 
    public void onReceiveSearchResultsSucceed(TrendingGifResponse trendingGifResponse, boolean z) {
        this.mNextPageId = trendingGifResponse.getNext();
        this.mTrendingAdapter.insert(castToRVItems(trendingGifResponse.getResults()), z);
        if (this.mIsLoadingMore) {
            this.progress_gif_botm.setVisibility(View.GONE);
        } else {
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    try {
                        GiphyPopup.this.progress_gif_main.setVisibility(View.GONE);
                        GiphyPopup.this.progressgif.setVisibility(View.GONE);
                        GiphyPopup.this.mask_view.setVisibility(View.GONE);
                    } catch (Exception unused) {
                    }
                }
            }, 600L);
        }
        this.mIsLoadingMore = false;
    }

    @Override 
    public void onReceiveSearchResultsSucceed(GifsResponse gifsResponse, boolean z) {
        this.mNextPageId = gifsResponse.getNext();
        this.mTrendingAdapter.insert(castToRVItems(gifsResponse.getResults()), z);
        if (this.mIsLoadingMore) {
            this.progress_gif_botm.setVisibility(View.GONE);
        } else {
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    try {
                        GiphyPopup.this.progress_gif_main.setVisibility(View.GONE);
                        GiphyPopup.this.progressgif.setVisibility(View.GONE);
                        GiphyPopup.this.mask_view.setVisibility(View.GONE);
                    } catch (Exception unused) {
                    }
                }
            }, 600L);
        }
        this.mIsLoadingMore = false;
    }

    @Override 
    public void onReceiveSearchResultsFailed(BaseError baseError, boolean z) {
        if (!z) {
            this.mTrendingAdapter.notifyListEmpty();
        }
    }

    private static List<AbstractRVItem> castToRVItems(List<Result> list) {
        ArrayList arrayList = new ArrayList();
        if (AbstractListUtils.isEmpty(list)) {
            return arrayList;
        }
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(new GifRVItem(2, list.get(i)).setRelativePosition(i));
        }
        return arrayList;
    }

    public void allCatClick() {
        this.txt_gif_trend = (TextView) this.mEmojiView.findViewById(R.id.txt_gif_trend);
        this.txt_gif1 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif1);
        this.txt_gif2 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif2);
        this.txt_gif3 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif3);
        this.txt_gif4 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif4);
        this.txt_gif5 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif5);
        this.txt_gif6 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif6);
        this.txt_gif7 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif7);
        this.txt_gif8 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif8);
        this.txt_gif9 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif9);
        this.txt_gif10 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif10);
        this.txt_gif11 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif11);
        this.txt_gif12 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif12);
        this.txt_gif13 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif13);
        this.txt_gif14 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif14);
        this.txt_gif15 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif15);
        this.txt_gif16 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif16);
        this.txt_gif17 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif17);
        this.txt_gif18 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif18);
        this.txt_gif19 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif19);
        this.txt_gif20 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif20);
        this.txt_gif21 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif21);
        this.txt_gif22 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif22);
        this.txt_gif23 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif23);
        this.txt_gif24 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif24);
        this.txt_gif25 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif25);
        this.txt_gif26 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif26);
        this.txt_gif27 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif27);
        this.txt_gif28 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif28);
        this.txt_gif29 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif29);
        this.txt_gif30 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif30);
        this.txt_gif31 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif31);
        this.txt_gif32 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif32);
        this.txt_gif33 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif33);
        this.txt_gif34 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif34);
        this.txt_gif35 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif35);
        this.txt_gif36 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif36);
        this.txt_gif37 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif37);
        this.txt_gif38 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif38);
        this.txt_gif39 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif39);
        this.txt_gif40 = (TextView) this.mEmojiView.findViewById(R.id.txt_gif40);
        this.txt_gif_trend.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup.this.mask_view.setVisibility(View.VISIBLE);
                GiphyPopup.this.progress_gif_main.setVisibility(View.GONE);
                GiphyPopup.this.progressgif.setVisibility(View.VISIBLE);
                GiphyPopup.this.isSearched = false;
                GiphyPopup.this.getTrending(false);
            }
        });
        this.txt_gif1.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif1);
            }
        });
        this.txt_gif2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif2);
            }
        });
        this.txt_gif3.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif3);
            }
        });
        this.txt_gif4.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif4);
            }
        });
        this.txt_gif5.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif5);
            }
        });
        this.txt_gif6.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif6);
            }
        });
        this.txt_gif7.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif7);
            }
        });
        this.txt_gif8.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif8);
            }
        });
        this.txt_gif9.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif9);
            }
        });
        this.txt_gif10.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif10);
            }
        });
        this.txt_gif11.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif11);
            }
        });
        this.txt_gif12.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif12);
            }
        });
        this.txt_gif13.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif13);
            }
        });
        this.txt_gif14.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif14);
            }
        });
        this.txt_gif15.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif15);
            }
        });
        this.txt_gif16.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif16);
            }
        });
        this.txt_gif17.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif17);
            }
        });
        this.txt_gif18.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif18);
            }
        });
        this.txt_gif19.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif19);
            }
        });
        this.txt_gif20.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif20);
            }
        });
        this.txt_gif21.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif21);
            }
        });
        this.txt_gif22.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif22);
            }
        });
        this.txt_gif23.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif23);
            }
        });
        this.txt_gif24.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif24);
            }
        });
        this.txt_gif25.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif25);
            }
        });
        this.txt_gif26.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif26);
            }
        });
        this.txt_gif27.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif27);
            }
        });
        this.txt_gif28.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif28);
            }
        });
        this.txt_gif29.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif29);
            }
        });
        this.txt_gif30.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif30);
            }
        });
        this.txt_gif31.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif31);
            }
        });
        this.txt_gif32.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif32);
            }
        });
        this.txt_gif33.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif33);
            }
        });
        this.txt_gif34.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif34);
            }
        });
        this.txt_gif35.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif35);
            }
        });
        this.txt_gif36.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif36);
            }
        });
        this.txt_gif37.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif37);
            }
        });
        this.txt_gif38.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif38);
            }
        });
        this.txt_gif39.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif39);
            }
        });
        this.txt_gif40.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View v) {
                GiphyPopup sWRX_GiphyPopup = GiphyPopup.this;
                sWRX_GiphyPopup.seachCategory(sWRX_GiphyPopup.txt_gif40);
            }
        });
    }
}

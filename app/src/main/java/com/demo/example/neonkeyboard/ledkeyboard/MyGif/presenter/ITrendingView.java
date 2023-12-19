package com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter;

import com.tenor.android.core.response.BaseError;
import com.tenor.android.core.response.impl.TrendingGifResponse;
import com.tenor.android.core.view.IBaseView;


public interface ITrendingView extends IBaseView {
    void onReceiveSearchResultsFailed(BaseError baseError, boolean z);

    void onReceiveSearchResultsSucceed(TrendingGifResponse trendingGifResponse, boolean z);
}

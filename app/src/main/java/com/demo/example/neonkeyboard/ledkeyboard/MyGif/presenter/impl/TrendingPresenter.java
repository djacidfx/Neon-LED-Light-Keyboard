package com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.impl;

import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.ITrendingPresenter;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.ITrendingView;
import com.tenor.android.core.network.ApiClient;
import com.tenor.android.core.presenter.impl.BasePresenter;
import com.tenor.android.core.response.BaseError;
import com.tenor.android.core.response.impl.TrendingGifResponse;
import retrofit2.Call;


public class TrendingPresenter extends BasePresenter<ITrendingView> implements ITrendingPresenter {
    public TrendingPresenter(ITrendingView iTrendingView) {
        super(iTrendingView);
    }

    @Override 
    public Call<TrendingGifResponse> getTrending(int i, String str, final boolean z) {
        Call<TrendingGifResponse> trending = ApiClient.getInstance(getView().getContext()).getTrending(ApiClient.getServiceIds(getView().getContext()), Integer.valueOf(i), str);
        trending.enqueue(new BaseWeakRefCallback<TrendingGifResponse>(getWeakRef()) { 
            public void success(ITrendingView iTrendingView, TrendingGifResponse trendingGifResponse) {
                if (trendingGifResponse == null) {
                    iTrendingView.onReceiveSearchResultsFailed(new BaseError(), z);
                } else {
                    iTrendingView.onReceiveSearchResultsSucceed(trendingGifResponse, z);
                }
            }

            public void failure(ITrendingView iTrendingView, BaseError baseError) {
                iTrendingView.onReceiveSearchResultsFailed(baseError, z);
            }
        });
        return trending;
    }
}

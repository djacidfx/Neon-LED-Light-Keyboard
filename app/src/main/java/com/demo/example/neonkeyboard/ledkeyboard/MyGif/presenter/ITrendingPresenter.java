package com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter;

import com.tenor.android.core.response.impl.TrendingGifResponse;
import retrofit2.Call;


public interface ITrendingPresenter {
    Call<TrendingGifResponse> getTrending(int i, String str, boolean z);
}

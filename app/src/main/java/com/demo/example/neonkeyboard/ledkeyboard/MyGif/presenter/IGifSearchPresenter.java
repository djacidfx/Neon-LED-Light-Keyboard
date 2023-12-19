package com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter;

import com.tenor.android.core.presenter.IBasePresenter;
import com.tenor.android.core.response.impl.GifsResponse;
import retrofit2.Call;


public interface IGifSearchPresenter extends IBasePresenter<IGifSearchView> {
    Call<GifsResponse> search(String str, int i, String str2, boolean z);
}

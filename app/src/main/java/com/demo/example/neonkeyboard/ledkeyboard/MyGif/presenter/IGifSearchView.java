package com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter;

import com.tenor.android.core.response.BaseError;
import com.tenor.android.core.response.impl.GifsResponse;
import com.tenor.android.core.view.IBaseView;


public interface IGifSearchView extends IBaseView {
    void onReceiveSearchResultsFailed(BaseError baseError, boolean z);

    void onReceiveSearchResultsSucceed(GifsResponse gifsResponse, boolean z);
}

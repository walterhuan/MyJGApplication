package com.walter.sc.czboke.fragment;

import com.loopj.android.http.RequestParams;
import com.walter.sc.common.AppNetConfig;
import com.walter.sc.common.BaseFragment;
import com.walter.sc.myjgapplication.R;

/**
 * Created by huangxl on 2016/5/24.
 */
public class HomeFragment extends BaseFragment{
    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.FlightPsgInfo;
    }

    @Override
    protected void initData(String content) {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
}

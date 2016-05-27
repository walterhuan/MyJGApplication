package com.walter.sc.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.walter.sc.common.ui.LoadingPage;
import com.walter.sc.common.utils.UIUtils;
import com.walter.sc.myjgapplication.BaseApplication;

import butterknife.ButterKnife;

/**
 * Created by huangxl on 2016/5/23.
 */
public abstract class BaseFragment extends Fragment {
    private LoadingPage loadingPage;
    private String mBaseFragementTag;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBaseFragementTag = getTag();
        Log.i(BaseApplication.COMMONTAG, mBaseFragementTag + " onAttach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseFragementTag = getTag();
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(BaseApplication.COMMONTAG, mBaseFragementTag + " onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onCreateView");

        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int LayoutId() {
                return getLayoutId();
            }

            @Override
            protected void onSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(BaseFragment.this,successView);
                initTitle();
                initData(resultState.getContent());

            }

            @Override
            protected String url() {
                return getUrl();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }
        };
        return loadingPage;

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onActivityCreate");
        super.onActivityCreated(savedInstanceState);
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingPage.show();
            }
        },1000);
    }

    @Override
    public void onStart() {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onDestroyView");
        super.onDestroyView();
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    protected abstract void initData(String content);

    protected abstract void initTitle();

    public abstract int getLayoutId();

    @Override
    public void onDestroy() {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onDestroy");
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDetach() {
        Log.i(BaseApplication.COMMONTAG,mBaseFragementTag+" onDetach");
        super.onDetach();
    }
}

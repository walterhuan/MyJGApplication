package com.walter.sc.common;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.walter.sc.myjgapplication.BaseApplication;

import butterknife.ButterKnife;

/**
 * Created by huangxl on 2016/5/24.
 */
public abstract class BaseActivity extends FragmentActivity{
    AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        AppManager.getInstance().addStack(this);
        initTitle();
        initData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(BaseApplication.COMMONTAG,"onConfigurationChanged");
    }

    protected abstract int getLayoutId();

    public abstract void initTitle();

    public abstract void initData();

    public void closeCurrent(){
        AppManager.getInstance().removeCurrent();
    }

    public void gotoActivity(Class clazz,Bundle bundle){
        Intent intent = new Intent(this, clazz);
        if (bundle != null){
            intent.putExtra("param",bundle);
        }
        startActivity(intent);
    }
}

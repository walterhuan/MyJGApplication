package com.walter.sc.okhttp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by huangxl on 2016/3/31.
 */
public abstract class LoginCallBack extends Callback<LoginEntity>{
    @Override
    public LoginEntity parseNetworkResponse(Response response) throws Exception {
        String bodyStr=response.body().string();
        Log.i(OKHttpActivity.TAG,"bodyStr="+bodyStr);
        String result =new JSONObject(bodyStr).getJSONObject("result").toString();
        Log.i(OKHttpActivity.TAG,"result="+result);
        LoginEntity loginEntity = new Gson().fromJson(result,new TypeToken<LoginEntity>(){}.getType());
        return loginEntity;
    }
}

package com.walter.sc.okhttp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

/**
 * Created by huangxl on 2016/3/31.
 */
public abstract class FlightInfoCallBack extends Callback<List<FlightEntity>> {
    @Override
    public List<FlightEntity> parseNetworkResponse(Response response) throws Exception {
        String bodyStr=response.body().string();
        String result=new JSONObject(bodyStr).getJSONArray("result").toString();
        List<FlightEntity> list_flights=new Gson().fromJson(result,new TypeToken<List<FlightEntity>>(){}.getType());



        return list_flights;
    }
}

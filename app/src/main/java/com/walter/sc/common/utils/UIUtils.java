package com.walter.sc.common.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.walter.sc.myjgapplication.BaseApplication;

/**
 * Created by huangxl on 2016/5/23.
 */
public class UIUtils {

    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int layoutId){
        return View.inflate(getContext(), layoutId, null);

    }

    public static String[] getStringArr(int arrId){
        return getContext().getResources().getStringArray(arrId);
    }

    public static int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }

    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density+0.5);

    }

    public static Handler getHandler(){
        return BaseApplication.handler;
    }



    public static Context getContext(){
        return BaseApplication.context;
    }

    public static void runOnUIThread(Runnable runnable){
        if (isInMainThread()){
            runnable.run();
        }else{
            getHandler().post(runnable);
        }

    }

    private static boolean isInMainThread(){
        int tid = android.os.Process.myTid();
        if (tid==BaseApplication.mainThreadId){
            return true;
        }
        return false;
    }
}

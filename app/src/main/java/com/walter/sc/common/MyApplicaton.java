package com.walter.sc.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

/**
 * Created by huangxl on 2016/5/23.
 * 已经迁移到BaseApplication
 */


public class MyApplicaton extends Application{

    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;

    public static String COMMONTAG = "COMMON";

    @Override
    public void onCreate() {
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        Log.e(COMMONTAG,"mainThread="+mainThread);
        mainThreadId = android.os.Process.myTid();
        CrashHandler.getInstance().init(this);
    }
}

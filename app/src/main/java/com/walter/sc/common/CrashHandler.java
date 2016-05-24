package com.walter.sc.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.walter.sc.myjgapplication.BaseApplication;

/**
 * Created by huangxl on 2016/5/23.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

    public static CrashHandler crashHandler = null;

    private CrashHandler (){

    }

    public static CrashHandler getInstance(){
        if (crashHandler==null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    private Context mContext;

    public void init(Context context){
        this.mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(thread, ex);

    }

    public boolean isHandle(Throwable ex) {
        if (ex == null) {
            return false;
        } else {
            return true;
        }
    }

    private void handleException(Thread thread, Throwable ex) {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Log.e(BaseApplication.COMMONTAG, "run方法执行"+" CT="+this.currentThread());
                Toast.makeText(mContext, "抱歉，系统出现未知异常，即将退出....", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        Log.e(BaseApplication.COMMONTAG, "CT=" + Thread.currentThread() + "   thread=" + thread);
        collectionException(ex);

        try {
            thread.sleep(2000);
            AppManager.getInstance().removeAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void collectionException(Throwable ex) {
        final String deviceInfo = Build.DEVICE +Build.VERSION.SDK_INT+Build.MODEL+Build.PRODUCT;
        final String errInfo = ex.getMessage();
        new Thread(){
            @Override
            public void run() {
                Log.e(BaseApplication.COMMONTAG,"deviceInfo="+deviceInfo+" \n errInfo=" +errInfo);
            }
        }.start();
    }
}

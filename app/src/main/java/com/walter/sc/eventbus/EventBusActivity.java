package com.walter.sc.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.walter.sc.greendao.ToastUtils;
import com.walter.sc.myjgapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusActivity extends AppCompatActivity {
    public static String TAG="EventBusActivity";

    @Bind(R.id.tv)
    TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        setTitle("EventBusActivity");

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.btn_try)
    void tryOnClick(View view){
        Intent intent = new Intent(getApplicationContext(),EventBus2Activity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_myMain)
    void toMainClick(){
        Intent intent = new Intent(getApplicationContext(),MyMainFragmentActivity.class);
        startActivity(intent);

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMyMainThreadEvent(MyEvents.FirstEvent event){
        String mMsg = "onMyMainThreadEvent 接收到消息:"+event.getmMsg();
        Log.i(TAG,mMsg);
        mTV.setText(mMsg);
        ToastUtils.show(this, mMsg);
    }

    @Subscribe(threadMode = ThreadMode.POSTING) //默认方式, 在发送线程执行
    public void onMyPostingEvent(MyEvents.FirstEvent event){
        String mMsg = "onMyPostingEvent 接收到消息:"+event.getmMsg();
        Log.i(TAG, mMsg);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND) //在后台线程执行
    public void onMyBackgroundEvent(MyEvents.FirstEvent event){
        String mMsg = "onMyBackgroundEvent 接收到消息:"+event.getmMsg();
        Log.i(TAG,mMsg);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC) //强制在后台执行
    public void onMyAsyncEvent(MyEvents.FirstEvent event){
        String mMsg = "onMyAsyncEvent 接收到消息:"+event.getmMsg();
        Log.i(TAG,mMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

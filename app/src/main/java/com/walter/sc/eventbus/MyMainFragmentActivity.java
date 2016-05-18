package com.walter.sc.eventbus;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.walter.sc.myjgapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 使用EventBus进行Fragment和Activity通信
 */

public class MyMainFragmentActivity extends FragmentActivity implements DataCallBack{
    private MyFragment myFragment;

    public EventBus eventBus;
    @Bind(R.id.tv_main)
    TextView mTV_Main;

    @OnClick(R.id.btn_Main)
    void BtnMainClick(){
        List<String> list = new ArrayList<String>();
        list.add("aaaa");
        list.add("bbbb");
        MyEvents.CommunicationEvent cEvent = new MyEvents.CommunicationEvent();
        cEvent.eventType = 1;
        cEvent.data = list;
        eventBus.post(cEvent);

    }

    @OnClick(R.id.btn_DataBack)
    void BtnDataBackClick(){
        MyEvents.CommunicationEvent cEvent = new MyEvents.CommunicationEvent();
        cEvent.eventType = 2;
        cEvent.data = this;
        eventBus.post(cEvent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main_fragment);
        ButterKnife.bind(this);
        mTV_Main.setText("你好 MyMainFragmentActivity");
        eventBus = new EventBus();
        FragmentManager fm = getFragmentManager();
        myFragment = new MyFragment();
        fm.beginTransaction().replace(R.id.id_content,myFragment).commit();
        eventBus.register(myFragment);


    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onMyEvent(MyEvents.CommunicationEvent eventData){
        mTV_Main.setText(eventData.data.toString());


    }


    @Override
    public void onCallBack(MyEvents.CommunicationEvent eventData) {
        mTV_Main.setText(eventData.data.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(myFragment);
        ButterKnife.unbind(this);
    }
}

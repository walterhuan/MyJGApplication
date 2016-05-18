package com.walter.sc.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.walter.sc.myjgapplication.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBus2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("EventBus2Activity");
        setContentView(R.layout.activity_event_bus2);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_first_event)
    void toFirstClick(){
        EventBus.getDefault().post(new MyEvents.FirstEvent("发送了一条消息"));
    }
}

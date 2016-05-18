package com.walter.sc.eventbus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walter.sc.myjgapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huangxl on 2016/4/11.
 */
public class MyFragment extends BaseEventBusFragment{
    private DataCallBack  actCallBack;
    private EventBus eventBus;
    private View view;

    @Bind(R.id.tv_myfragment)
    TextView mTV_MyFragment;

    public MyFragment(){
        setHasOptionsMenu(true);
        eventBus = new EventBus();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myfragment,container,false);
        ButterKnife.bind(this, view);
        mTV_MyFragment.setText("你好，MyFragment");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity()!=null){
            eventBus.register(getActivity());
        }
    }

    @OnClick(R.id.btn_myfragment)
    void BtnOnClick(){
        if (actCallBack!=null){
            MyEvents.CommunicationEvent cEvent = new MyEvents.CommunicationEvent();
            cEvent.data = "来自MyFragment的点击 调用Activity的回调方法";
            actCallBack.onCallBack(cEvent);
        }else{
            mTV_MyFragment.setText("主Activity并没有绑定回调方法");
        }

    }


    @OnClick(R.id.btn_myfragment_post)
    void BtnPostOnClick(){
        MyEvents.CommunicationEvent cEvent = new MyEvents.CommunicationEvent();
        cEvent.data = "这是来自MyFragment的Post Evnent";
        eventBus.post(cEvent);
    }




    @Override
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onMyEvent(MyEvents.CommunicationEvent eventData) {
        int type = eventData.eventType;
        System.out.println("type="+type);
        switch (type){
            case 1:
                StringBuffer sb = new StringBuffer();
                for (String str :(List<String>)eventData.data){
                    sb.append(str+"\n");

                }
                mTV_MyFragment.setText("MyFragment:"+sb);
                break;

            case 2:
                if (eventData.data instanceof DataCallBack){
                    mTV_MyFragment.setText("MyFragment:主要MainActivity实现了DataCallBack");
                    actCallBack = (DataCallBack)eventData.data;
                }
                break;

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        eventBus.unregister(getActivity());
    }
}

package com.walter.sc.eventbus;

import android.app.Fragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by huangxl on 2016/4/11.
 */
public abstract class BaseEventBusFragment extends Fragment{
    public BaseEventBusFragment(){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void onMyEvent(MyEvents.CommunicationEvent eventData);
}

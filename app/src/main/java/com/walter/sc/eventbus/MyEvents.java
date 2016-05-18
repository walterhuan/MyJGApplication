package com.walter.sc.eventbus;

/**
 * Created by huangxl on 2016/4/8.
 */
public class MyEvents {

    public static class FirstEvent{
        private String mMsg;

        public FirstEvent(String mMsg){
            this.mMsg=mMsg;
        }

        public String getmMsg(){
            return this.mMsg;
        }


    }

    public static class CommunicationEvent{
        public CommunicationEvent(){

        }
        public int eventType;//可能类型有很多种，数据也不一样
        public Object data;//数据对象

    }
}

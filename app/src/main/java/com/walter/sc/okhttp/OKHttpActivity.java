package com.walter.sc.okhttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.walter.sc.myjgapplication.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

public class OKHttpActivity extends AppCompatActivity {
    public static final String TAG = "OKHttpActivity";
 //   private String baseURL="http://192.168.118.121:8080/3upsi_v2/";
    private String baseURL="http://182.151.210.179/";



    @Bind(R.id.id_progress)
    ProgressBar mProgressBar;

    @Bind(R.id.id_textview)
    TextView mTextView;

    @Bind(R.id.id_imageview)
    ImageView mImageView;

    public class MyStingCallBack extends StringCallback{
        @Override
        public void onError(Call call, Exception e) {
            mTextView.setText("onError:"+e.getMessage());

        }

        @Override
        public void onResponse(String response) {
            mTextView.setText("onResponse:"+response);

        }

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            setTitle("loading...");
        }

        @Override
        public void onAfter() {
            super.onAfter();
            setTitle("Sample-okHttp");
        }

        @Override
        public void inProgress(float progress) {
            super.inProgress(progress);
            Log.e(TAG, "inProgress" + progress);
            mProgressBar.setProgress((int)(100*progress));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
        mProgressBar.setMax(100);

    }

    @OnClick(R.id.getHtml)
    void getHtml(View view){
        String url = "http://www.baidu.com";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStingCallBack());

    }


    @OnClick(R.id.postString)
    void postString(View view){
        String url = baseURL+"login_m.action?loginInfo={\"userName\":\"admin\",\"userPwd\":\"3upsi0601\",\"type\":\"pad\"}";
        Log.i(TAG, "" + new Gson().toJson(new User("admin", "3upsi0601", "pad")));
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                //content 没有用
                .content("loginInfo={\"userName\":\"admin\",\"userPwd\":\"3upsi0601\",\"type\":\"pad\"}")//new Gson().toJson(new User("admin", "3upsi0601","pad")))
                .build()
                .execute(new MyStingCallBack());

    }

    //GSON->JSON 自定义Callback解析
    @OnClick(R.id.getUser)
    void getUser(View view){
       String url = baseURL+"login_m.action";
        Log.i(TAG, "getUser Click");
        OkHttpUtils
                .post()
                .url(url)
                .addParams("loginInfo", new Gson().toJson(new User("admin", "3upsi0601", "phone")))
                .build()
                .connTimeOut(40000)
                .execute(new LoginCallBack() {
                    @Override
                    public void onError(Call call, Exception e) {

                        mTextView.setText("error:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(LoginEntity response) {
                        if (null==response.getTest()){
                            Log.i(TAG,"test is null");

                        }

                        if (response.getTest()==null){
                            Log.i(TAG,"what the fuck");
                        }
                        mTextView.setText("Department=" + response.getDepartment()[0] + "\n  userName=" + response.getUserName()
                                + "\n  test=" + response.getTest() + "\n   areaNames=" + response.getAreaNames()[0]);
                    }
                });

    }


    //解析航班数据 包括旅客信息和旅客临时乘机记录
    @OnClick(R.id.getUsers)
    void getUsers(View view){

        String url=baseURL+"loadPassengerInfo_m.action";

        JSONObject param_LoadPsg = new JSONObject();
        try {
            param_LoadPsg.put("segments","3U8319");
            param_LoadPsg.put("flag","flightNo");
            param_LoadPsg.put("flightDate","2015-12-16");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Map<String,String> params = new HashMap<String,String>();
        params.put("loadPassengers",param_LoadPsg.toString());
        params.put("userName","gujiao");
        params.put("userPwd", "aaaa1234!");


        OkHttpUtils
                .post()
                .url(url)
//                .addParams("userName", "gujiao")
//                .addParams("loadPassengers",param_LoadPsg.toString())
//                .addParams("userPwd","aaaa1234!")
                .params(params)
                .build()
                .connTimeOut(40000)
                .execute(new FlightInfoCallBack() {
                    @Override
                    public void onError(Call call, Exception e) {
                        mTextView.setText("error="+e.getMessage());
                    }

                    @Override
                    public void onResponse(List<FlightEntity> response) {

                        //mTextView.setText(" size="+response.size()+" "+response.get(0).getFlightNo());
                        StringBuffer sb = new StringBuffer();
                        for (FlightEntity fe:response){
                            sb.append("\n flightNo=" + fe.getFlightNo() + " planeCode=" + fe.getPlaneCode() + "  "
                                    + fe.getOrgCityAirp() + " - " + fe.getDstCityAirp());
                            for (PsgEntity pe:fe.getPassengers()){
                                sb.append("\n paxName"+pe.getPaxName());
                                for (PsgFlightHistoryEntity pfhe:pe.getFlightHistory()){
                                    sb.append("\n historyFlt="+pfhe.getFlightNo()+"  historyDate="+pfhe.getFltDate());
                                }
                            }
                        }

                        mTextView.setText(sb);



                    }
                });

    }


    @OnClick(R.id.getImage)
    void getImage(View view){
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .connTimeOut(20000)
                .writeTimeOut(20000)
                .readTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        mTextView.setText("onError:"+e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap response) {
                        mTextView.setText("ok");
                        mImageView.setImageBitmap(response);


                    }
                });

    }

    @OnClick(R.id.downloadFile)
    void downLoadFile(View view){
        String url="http://pss.sichuanair.com/apk/SCMobile.apk";
        String absolutePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/uuuuuu";
        Log.i(TAG, "  " + absolutePath);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(absolutePath,"just.apk") {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        mTextView.setText("begin");
                        mProgressBar.setProgress(0);
                    }

                    @Override
                    public void inProgress(float progress, long total) {
                        mProgressBar.setProgress((int)(100*progress));

                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        mTextView.setText("error:"+e.getMessage());

                    }

                    @Override
                    public void onResponse(File response) {
                        mTextView.setText("file="+response.getAbsolutePath());

                    }
                });


    }

//    public void uploadFile(View view)
//    {
//
//        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
//        if (!file.exists())
//        {
//            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Map<String, String> params = new HashMap<>();
//        params.put("username", "张鸿洋");
//        params.put("password", "123");
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("APP-Key", "APP-Secret222");
//        headers.put("APP-Secret", "APP-Secret111");
//
//        String url = mBaseUrl + "user!uploadFile";
//
//        OkHttpUtils.post()//
//                .addFile("mFile", "messenger_01.png", file)//
//                .url(url)//
//                .params(params)//
//                .headers(headers)//
//                .build()//
//                .execute(new MyStringCallback());
//    }
//
//
//    public void multiFileUpload(View view)
//    {
//        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
//        File file2 = new File(Environment.getExternalStorageDirectory(), "test1.txt");
//        if (!file.exists())
//        {
//            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Map<String, String> params = new HashMap<>();
//        params.put("username", "张鸿洋");
//        params.put("password", "123");
//
//        String url = mBaseUrl + "user!uploadFile";
//        OkHttpUtils.post()//
//                .addFile("mFile", "messenger_01.png", file)//
//                .addFile("mFile", "test1.txt", file2)//
//                .url(url)
//                .params(params)//
//                .build()//
//                .execute(new MyStringCallback());
//    }


}

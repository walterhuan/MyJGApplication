package com.walter.sc.handheld;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.PrinterManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cvrt07.Device.HandlerMsg;
import com.cvrt07.Device.IDCardInfo;
import com.cvrt07.Device.SAMModule;
import com.walter.sc.greendao.ToastUtils;
import com.walter.sc.myjgapplication.R;

import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandHeldActivity extends AppCompatActivity {


    //QRCode
    private int mBarcodeTypeValue;
    //打印Action返回
    private final static String PRNT_ACTION = "android.prnt.message";
    //身份证相关
    private String m_Port = "";// this.getResources().getString(R.string.ModulePort);
    private String FILE_NAME = "/sys/urovo_control/led/sys_gpio_ctl";
    private boolean mBoolMine = false;
    SoundPool readSound = null;
    int hit = 0;
    MyConn myConn = new MyConn();
    IDCardInfo ic;
    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wltlib";

    @SuppressLint("HandlerLeak")
    class MyConn extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerMsg.Successful_election_card:
                    readSound.play(hit, 2, 1, 0, 0, 1f);
                    // Looper.prepare();
                    Toast.makeText(getApplicationContext(), "读卡成功",
                            Toast.LENGTH_SHORT).show();
                    Log.d("ccc", "      读卡成功   ");

                    // Looper.loop();
                    // CDialog.showDialog(MainActivity.this, R.string.loading);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");// 设置日期格式
                    mEdt_HandHeld.setText(ic.getPeopleName()+ "\n"+ ic.getIDCard());
//                    "姓名：" + ic.getPeopleName() + "\n" + "性别："
//                            + ic.getSex() + "\n" + "民族：" + ic.getPeople() + "\n"
//                            + "出生日期：" + df.format(ic.getBirthDay()) + "\n" + "地址："
//                            + ic.getAddr() + "\n" + "身份号码：" + ic.getIDCard() + "\n"
//                            + "签发机关：" + ic.getDepartment() + "\n" + "有效期限："
//                            + ic.getStrartDate() + "-" + ic.getEndDate() + "\n\n"
//                            + ic.getFp1() + "   " + ic.getFp2() + "\n"

                    //                    Log.d("ccc","姓名：" + ic.getPeopleName() + "\n" + "性别："
                    //                            + ic.getSex() + "\n" + "民族：" + ic.getPeople() + "\n"
                    //                            + "出生日期：" + df.format(ic.getBirthDay()) + "\n" + "地址："
                    //                            + ic.getAddr() + "\n" + "身份号码：" + ic.getIDCard() + "\n"
                    //                            + "签发机关：" + ic.getDepartment() + "\n" + "有效期限："
                    //                            + ic.getStrartDate() + "-" + ic.getEndDate() + "\n");

                    //                    try {
                    //                        FileInputStream fis = new FileInputStream(filePath
                    //                                + "/zp.bmp");
                    //                        Bitmap bmp = BitmapFactory.decodeStream(fis);
                    //                        fis.close();
                    //                        image.setImageBitmap(bmp);
                    //                    } catch (FileNotFoundException e) {
                    //                        Toast.makeText(getApplicationContext(), "头像不存在！",
                    //                                Toast.LENGTH_SHORT).show();
                    //                    } catch (IOException e) {
                    //                        // TODO 自动生成的 catch 块
                    //                        Toast.makeText(getApplicationContext(), "头像读取错误",
                    //                                Toast.LENGTH_SHORT).show();
                    //                    }

                    mText_Status.refreshDrawableState();
                    break;
                case HandlerMsg.Init_Feild:
                    mText_Status.setText("设备连接失败！");
                    break;
                case HandlerMsg.Init_Successful:
                    mText_Status.setText("设备连接成功！");
                    break;
                case HandlerMsg.ReadingCard:
                    mText_Status.setText("正在读卡...");
                    mText_Status.refreshDrawableState();
                    break;
                case HandlerMsg.Listening:
                    mText_Status.setText("请放卡...");
                    mText_Status.refreshDrawableState();
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    }


    @Bind(R.id.text_status_info)
    TextView mText_Status;


    @Bind(R.id.edit_handheld)
    EditText mEdt_HandHeld;


    PrinterManager printer = new PrinterManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_held);
        ButterKnife.bind(this);
        initIDCard();


    }

    //身份证读取
    private void initIDCard() {
        //获取路径  /dev/ttyHSL0
        m_Port = "/dev/ttyHSL0";
        readSound = new SoundPool(1, AudioManager.STREAM_SYSTEM, 10);
        // 从资源或者文件截入音频流
        hit = readSound.load(this, R.raw.readsound, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            SAMModule.Initialization(myConn, m_Port, FILE_NAME);
        } catch (Exception e) {
            return;
        }
    }

    //注册监听打印回调
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PRNT_ACTION);
        registerReceiver(mPrtReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mPrtReceiver);
    }


    @Override
    protected void onStop() {
        super.onStop();
        try {
            mBoolMine=false;
            SAMModule.Destruction();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private BroadcastReceiver mPrtReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int ret = intent.getIntExtra("ret", 0);
            if (ret == -1)
                Toast.makeText(HandHeldActivity.this, "缺纸！！！", Toast.LENGTH_SHORT).show();
        }
    };

    @OnClick(R.id.btn_print_word)
    void wordPrint() {
        String message = mEdt_HandHeld.getText().toString();
        if (message.length() > 0) {
            doPrintWork(message);

        } else {
            ToastUtils.show(this, "打印预设信息");
            String myMsg = "商户号:812110045110001\r\n"
                    + "商户名:北京宅急送快运股份有限公司\r\n" + "终端号:45851701\r\n" + "批次号:000001\r\n"
                    + "流水号:000001\r\n";
            doPrintWork(myMsg);
        }
    }


    @OnClick(R.id.btn_print_QR_Code)
    void QRCodePrint() {
        String msg = mEdt_HandHeld.getText().toString();
        if (msg.length() > 0) {
            doPrintQRCode(msg);
        } else {
            ToastUtils.show(this, "请输入打印信息");
        }

    }


    @OnClick(R.id.btn_get_id)
    void IDOnClick(){
        mBoolMine=true;
        Thread thread = new Thread(runnable,"test");
        thread.start();
    }


    //打印文字
    private void doPrintWork(String message) {
        Intent intentService = new Intent(this, PrintBillService.class);
        intentService.putExtra("SPRT", message);
        startService(intentService);

    }

    //打印二维码
    private void doPrintQRCode(String text) {
        mBarcodeTypeValue = 58;//二维码打印
        printer.setupPage(384, -1);
        printer.setGrayLevel(20);
        printer.prn_drawBarcode(text, 50, 10, mBarcodeTypeValue, 8, 120, 0);

        int ret = printer.printPage(0);
        Intent intent = new Intent("urovo.prnt.message");
        intent.putExtra("ret", ret);
        this.sendBroadcast(intent);

        Intent intentM = new Intent(PRNT_ACTION);
        intentM.putExtra("ret", ret);
        this.sendBroadcast(intentM);
        printer.prn_paperForWard(10);

    }


    //身份证
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (mBoolMine) {
                //                System.out.println("run   isCancel="+isCancel);
                //
                //                if (isCancel) {
                //                    continue;
                //                }

                try {
                    System.out.println("ReadCard");
                    ReadCard();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    continue;
                }

            }
        }

    };


    private void ReadCard() {
        try {
            if (!SAMModule.SearchCard()) {
                return;
            }
            Message msg = myConn.obtainMessage(HandlerMsg.ReadingCard);
            myConn.sendMessage(msg);
            SAMModule.PickCard();
            getData();


        } catch (Exception e) {
            return;
        } finally {
            Message msg = myConn.obtainMessage(HandlerMsg.Listening);
            myConn.sendMessage(msg);

        }
        // CDialog.dismissDialog();
    }

    private void getData() {

        try {

            ic = SAMModule.ReadCard(filePath);
            if (ic == null) {
                return;
            }

            Message msg = myConn
                    .obtainMessage(HandlerMsg.Successful_election_card);
            myConn.sendMessage(msg);

        } catch (InterruptedException e) {
            mText_Status.setText("读取数据异常！");
//            image.setImageBitmap(BitmapFactory.decodeResource(getResources(),
//                    R.drawable.ic_launcher));
            // CDialog.dismissDialog();
        }
        // CDialog.dismissDialog();
        catch (IOException e1) {
            // TODO 自动生成的 catch 块
            mText_Status.setText("读取数据异常！");
        } catch (Exception e) {
            mText_Status.append("照片解码失败，请检查路径" + filePath);
//            image.setImageBitmap(BitmapFactory.decodeResource(getResources(),
//                    R.drawable.ic_launcher));
        }
    }



}



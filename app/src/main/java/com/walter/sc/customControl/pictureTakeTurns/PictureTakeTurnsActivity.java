package com.walter.sc.customControl.pictureTakeTurns;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.walter.sc.myjgapplication.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PictureTakeTurnsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private int[] imageViewIds;
    private String[] contentDescs;
    private ArrayList<ImageView> imageViewList;
    private int previousPostion = 0;
    private boolean isRuning=false;


    @Bind(R.id.ttp_viewPager)
    ViewPager mViewPager_TTP;

    @Bind(R.id.id_textDesc)
    TextView mTV_Desc;

    @Bind(R.id.ll_point_container)
    LinearLayout mLL_Point_Container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_take_turns);
        setTitle("PictureTakeTurnsActivity");
        ButterKnife.bind(this);

        initData();
        initAdapter();

        new Thread(){
            public void run(){
                isRuning = true;
                while (isRuning){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isRuning)
                                return;
                            mViewPager_TTP.setCurrentItem(mViewPager_TTP.getCurrentItem()+1);
                        }
                    });
                }

            }

        }.start();

    }

    private void initData() {

        //图片资源
        imageViewIds = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};


        //文字描述
        contentDescs = new String[]{
                "巩俐不低俗，我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"
        };


        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        LinearLayout.LayoutParams layoutParams;
        View pointView;
        for (int i=0;i<imageViewIds.length;i++){
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageViewIds[i]);
            imageViewList.add(imageView);


            //加小白点
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);

            layoutParams = new LinearLayout.LayoutParams(10,10);
            if (i!=0)
            layoutParams.leftMargin=10;
            pointView.setEnabled(false);
            mLL_Point_Container.addView(pointView,layoutParams);

        }










    }

    private void initAdapter() {
        mViewPager_TTP.addOnPageChangeListener(this);
        mViewPager_TTP.setAdapter(new MyPagerAdapter());
        mViewPager_TTP.setCurrentItem(50000 - 50000 % imageViewList.size());
        mLL_Point_Container.getChildAt(0).setEnabled(true);
        mTV_Desc.setText(contentDescs[0]);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    // 新的条目被选中时调用
    @Override
    public void onPageSelected(int position) {
        int newPosition = position%imageViewList.size();
        mLL_Point_Container.getChildAt(newPosition).setEnabled(true);
        mLL_Point_Container.getChildAt(previousPostion).setEnabled(false);
        mTV_Desc.setText(contentDescs[newPosition]);
        previousPostion = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        //1.返回要显示的条目内容,创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //container 容器 ViewPager
            //position 当前要显示的条目的位置
            int newPosition = position%imageViewList.size();
            ImageView imageView = imageViewList.get(newPosition);
            //a.把View对象加入到容器中
            container.addView(imageView);


            //b.把View对象返回给框架,适配器
            return imageView;
        }

        //2.销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //object 要销毁的条目

            container.removeView((View)object);
        }

        //3.指定要复用的判断逻辑,固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRuning = false;
        ButterKnife.unbind(this);
    }
}

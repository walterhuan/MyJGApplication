package com.walter.sc.customControl.customUtil;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by huangxl on 2016/5/18.
 */
public class AnimationUtils {

    //正在运行的动画个数
    public static int runningAnimationCount = 0;

    //旋转出去的动画
    public static void rotateOutAnim(RelativeLayout layout,long delay) {
        int childCount = layout.getChildCount();
        //如果隐藏,则找到所有的子View,禁用
        for (int i =0; i<childCount; i++){
            layout.getChildAt(i).setEnabled(false);
        }
        RotateAnimation ra = new RotateAnimation(
                0f, -180f,
                Animation.RELATIVE_TO_SELF, 0.5f,//相对X坐标点
                Animation.RELATIVE_TO_SELF, 1.0f);//相对Y坐标点
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setStartOffset(delay);
        ra.setAnimationListener(new MyAnimationListener());
        layout.startAnimation(ra);
    }

    //旋转进来的动画
    public static void rotateInAnim(RelativeLayout layout,long delay) {
        int childCount = layout.getChildCount();
        for (int i=0; i<childCount; i++){
            layout.getChildAt(i).setEnabled(true);
        }
        RotateAnimation ra = new RotateAnimation(
                -180f,0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,1.0f
                );
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setStartOffset(delay);
        ra.setAnimationListener(new MyAnimationListener());
        layout.startAnimation(ra);
    }

    //监听是否有动画正在进行
    static class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            runningAnimationCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            runningAnimationCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}

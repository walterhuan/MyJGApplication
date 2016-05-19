package com.walter.sc.customControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.walter.sc.customControl.customUtil.AnimationUtils;
import com.walter.sc.customControl.pictureTakeTurns.PictureTakeTurnsActivity;
import com.walter.sc.myjgapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomCActivity extends AppCompatActivity {


    @Bind(R.id.rl_level1)
    RelativeLayout rl_level1;

    @Bind(R.id.rl_level2)
    RelativeLayout rl_level2;

    @Bind(R.id.rl_level3)
    RelativeLayout rl_level3;

    boolean isL3Display = true;
    boolean isL2Display = true;
    boolean isL1Display = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_c);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.id_home)
    void homeOnClick(){
        if (AnimationUtils.runningAnimationCount>0)
            return;

       if (isL2Display){
           long delay = 0;
           if (isL3Display){
           AnimationUtils.rotateOutAnim(rl_level3,delay);
               delay+=200;
               isL3Display = !isL3Display;
           }
           AnimationUtils.rotateOutAnim(rl_level2,delay);

       }else {
           AnimationUtils.rotateInAnim(rl_level2,0);
       }
        isL2Display = !isL2Display;


    }

    @OnClick(R.id.id_menu)
    void menuOnClick(){
        if(AnimationUtils.runningAnimationCount>0)
            return;
        if (isL3Display) {
            AnimationUtils.rotateOutAnim(rl_level3,0);
        }else{
            AnimationUtils.rotateInAnim(rl_level3,0);
        }
        isL3Display = !isL3Display;


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_MENU){
            if (AnimationUtils.runningAnimationCount>0)
                return true;

            if (isL1Display){
                long delay = 0;
                if (isL3Display){
                    AnimationUtils.rotateOutAnim(rl_level3, delay);
                    delay+=200;
                    isL3Display = false;
                }
                if (isL2Display){
                    AnimationUtils.rotateOutAnim(rl_level2, delay);
                    delay+=200;
                    isL2Display = false;
                }
                AnimationUtils.rotateOutAnim(rl_level1,delay);

            }else{
                AnimationUtils.rotateInAnim(rl_level1,0);
                AnimationUtils.rotateInAnim(rl_level2,200);
                AnimationUtils.rotateInAnim(rl_level3,400);
                isL2Display = true;
                isL3Display = true;

            }
            isL1Display = !isL1Display;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

    public void mBtnClick(View view){
        switch (view.getId()){
            case R.id.buttonTTP:
                Intent intent = new Intent(this, PictureTakeTurnsActivity.class);
                this.startActivity(intent);
                break;
        }
    }
}

package com.walter.sc.czboke;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.walter.sc.common.BaseActivity;
import com.walter.sc.common.utils.UIUtils;
import com.walter.sc.czboke.fragment.HomeFragment;
import com.walter.sc.czboke.fragment.InvestmentFragment;
import com.walter.sc.czboke.fragment.MeFragment;
import com.walter.sc.czboke.fragment.MoreFragment;
import com.walter.sc.myjgapplication.R;

import butterknife.Bind;
import butterknife.OnClick;

public class CZBKMainActivity extends BaseActivity {


    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.iv_home)
    ImageView ivHome;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.ll_home)
    LinearLayout llHome;
    @Bind(R.id.iv_touzi)
    ImageView ivTouzi;
    @Bind(R.id.tv_touzi)
    TextView tvTouzi;
    @Bind(R.id.ll_touzi)
    LinearLayout llTouzi;
    @Bind(R.id.iv_me)
    ImageView ivMe;
    @Bind(R.id.tv_me)
    TextView tvMe;
    @Bind(R.id.ll_me)
    LinearLayout llMe;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.ll_more)
    LinearLayout llMore;

    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private InvestmentFragment investmentFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_czbkmain;
    }

    @Override
    public void initTitle() {


    }

    @Override
    public void initData() {
        setSelection(0);

    }

    @OnClick({R.id.ll_home,R.id.ll_touzi,R.id.ll_me,R.id.ll_more})
    public void changeTab(View view){
        switch (view.getId()){
            case R.id.ll_home:
                setSelection(0);
                break;
            case R.id.ll_touzi:
                setSelection(1);
                break;
            case R.id.ll_me:
                setSelection(2);
                break;
            case R.id.ll_more:
                setSelection(3);
                break;
        }
    }

    private void setSelection(int selection){
        FragmentManager fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        reSetTab();
        hideFragemtn();
        switch (selection){
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.content,homeFragment,"TAG_home");
                }
                ivHome.setImageResource(R.drawable.bid01);
                tvHome.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                ft.show(homeFragment);
                break;
            case 1:
                if (investmentFragment == null){
                    investmentFragment = new InvestmentFragment();
                    ft.add(R.id.content,investmentFragment,"TAG_investment");
                }
                ivTouzi.setImageResource(R.drawable.bid03);
                tvTouzi.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                ft.show(investmentFragment);
                break;
            case 2:
                if (meFragment == null){
                    meFragment = new MeFragment();
                    ft.add(R.id.content,meFragment,"TAG_me");
                }
                ivMe.setImageResource(R.drawable.bid05);
                tvMe.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                ft.show(meFragment);
                break;
            case 3:
                if (moreFragment == null){
                    moreFragment = new MoreFragment();
                    ft.add(R.id.content,moreFragment,"TAG_more");
                }
                ivMore.setImageResource(R.drawable.bid07);
                tvMore.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                ft.show(moreFragment);
                break;
        }

        ft.commit();



    }

    private void hideFragemtn() {
        if (homeFragment != null){
            ft.hide(homeFragment);
        }
        if (meFragment != null){
            ft.hide(meFragment);
        }
        if (investmentFragment != null){
            ft.hide(investmentFragment);
        }
        if (moreFragment != null){
            ft.hide(moreFragment);
        }
    }

    private void reSetTab() {
        ivHome.setImageResource(R.drawable.bid02);
        ivTouzi.setImageResource(R.drawable.bid04);
        ivMe.setImageResource(R.drawable.bid06);
        ivMore.setImageResource(R.drawable.bid08);
        tvHome.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvTouzi.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMe.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMore.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
    }


}

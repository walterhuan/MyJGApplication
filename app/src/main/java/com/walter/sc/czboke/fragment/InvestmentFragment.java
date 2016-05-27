package com.walter.sc.czboke.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;
import com.walter.sc.common.BaseFragment;
import com.walter.sc.common.utils.UIUtils;
import com.walter.sc.myjgapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by huangxl on 2016/5/24.
 */
public class InvestmentFragment extends BaseFragment {
    @Bind(R.id.title_left)
    ImageView titleLeft;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.title_right)
    ImageView titleRight;
    @Bind(R.id.investment_textView)
    TextView investmentTextView;
    @Bind(R.id.investment_tabIndicator)
    TabPageIndicator investmentTabIndicator;
    @Bind(R.id.investment_viewPager)
    ViewPager investmentViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        initFragment();
        investmentViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        investmentTabIndicator.setViewPager(investmentViewPager);

    }

    @Override
    protected void initTitle() {
        titleTv.setText("我要投资");
        titleRight.setVisibility(View.INVISIBLE);


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_investment;
    }

    private void initFragment() {
        ProductAllFragment productAllFragment = new ProductAllFragment();
        Fragment productRecommendFragment = new ProductRecommendFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        fragmentList.add(productAllFragment);
        fragmentList.add(productRecommendFragment);
        fragmentList.add(productHotFragment);



    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList == null ?0:fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return UIUtils.getStringArr(R.array.touzi_tab)[position];
        }
    }
}

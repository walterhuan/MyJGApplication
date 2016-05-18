package com.walter.sc.tablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.walter.sc.NavigationView.ContentFragment;
import com.walter.sc.myjgapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MyTabActivity extends AppCompatActivity {

    @Bind(R.id.tab_FindFragment_title)
    TabLayout tab_FindFragment_title;          //定义TabLayout

    @Bind(R.id.vp_FindFragment_pager)
    ViewPager vp_FindFragment_pager;          //定义viewPager

    private FragmentPagerAdapter fAdapter;    //定义adapter

    private List<Fragment> list_fragment;     //定义要装fragment的列表
    private List<String> list_title;          //tab名称列表

    private ContentFragment cf1;
    private ContentFragment cf2;
    private ContentFragment cf3;
    private ContentFragment cf4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        initControls();
    }

    private void initControls() {
        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("热门推荐");
        list_title.add("热门收藏");
        list_title.add("本月热榜");
        list_title.add("今日热榜");

        //初始化各fragment
        cf1 = ContentFragment.newInstance(list_title.get(0));
        cf2 = ContentFragment.newInstance(list_title.get(1));
        cf3 = ContentFragment.newInstance(list_title.get(2));
        cf4 = ContentFragment.newInstance(list_title.get(3));

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(cf1);
        list_fragment.add(cf2);
        list_fragment.add(cf3);
        list_fragment.add(cf4);

        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText("热门推荐"));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(3)));

        fAdapter = new FragmentTabAdapter(this.getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
        //tab_FindFragment_title.set




    }
}

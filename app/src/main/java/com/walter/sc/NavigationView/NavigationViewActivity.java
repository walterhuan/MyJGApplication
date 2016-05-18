package com.walter.sc.NavigationView;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.walter.sc.myjgapplication.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NavigationViewActivity extends AppCompatActivity {

    public static String TAG = "NavigationViewActivity";
    private ContentFragment mCurrentFragment;
    private String mTitle;
    private FragmentManager fm;

    @Bind(R.id.id_toolbar)
    Toolbar toolbar;

    @Bind(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.id_nv_menu)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);
        ButterKnife.bind(this);
        fm=getSupportFragmentManager();
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setupDrawerContent(mNavigationView);

        //恢复title
        restoreTitle(savedInstanceState);

        //查找当前的Fragment
        findCurrentFragment();

        //隐藏别的Fragment，如果存在的话
        hideFragments();



    }

    private void hideFragments() {
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null){
//
            for (Fragment fragment : fragments) {
                if (fragment==mCurrentFragment) continue;
                fm.beginTransaction().hide(fragment).commit();
            }
            }
    }

    private void findCurrentFragment() {

        mCurrentFragment = (ContentFragment)fm.findFragmentByTag(mTitle);
        if (mCurrentFragment==null){
            mCurrentFragment = ContentFragment.newInstance(mTitle);
            fm.beginTransaction().add(R.id.id_content_container,mCurrentFragment,mTitle).commit();
        }

    }

    private void restoreTitle(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mTitle = savedInstanceState.getString(ContentFragment.KEY_TITLE);

        if (TextUtils.isEmpty(mTitle)) {
            mTitle = "Home";
        }

        toolbar.setTitle(mTitle);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            private MenuItem mPreMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (mPreMenuItem != null)
                    mPreMenuItem.setChecked(false);
                item.setChecked(true);
                //mDrawerLayout.closeDrawers();
                mPreMenuItem = item;

                Log.i(TAG, "item.getItemId()=" + item.getItemId() + "  title=" + item.getTitle());

                String theChosenTitle = item.getTitle().toString();


                ContentFragment contentFragment = (ContentFragment) fm.findFragmentByTag(theChosenTitle);

                if (contentFragment == mCurrentFragment) {
                    mDrawerLayout.closeDrawers();
                    return true;
                }

                FragmentTransaction ft = fm.beginTransaction();
                ft.hide(mCurrentFragment);

                if (contentFragment == null) {
                    contentFragment = ContentFragment.newInstance(theChosenTitle);
                    ft.add(R.id.id_content_container, contentFragment, theChosenTitle);
                } else {
                    ft.show(contentFragment);
                }
                ft.commit();

                mCurrentFragment = contentFragment;
                mTitle = theChosenTitle;
                toolbar.setTitle(mTitle);
                mDrawerLayout.closeDrawers();


                return true;
            }
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ContentFragment.KEY_TITLE, mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

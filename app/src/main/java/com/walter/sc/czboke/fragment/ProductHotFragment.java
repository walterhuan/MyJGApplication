package com.walter.sc.czboke.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walter.sc.common.utils.UIUtils;
import com.walter.sc.myjgapplication.BaseApplication;
import com.walter.sc.myjgapplication.R;

import butterknife.ButterKnife;

/**
 * Created by huangxl on 2016/5/27.
 */
public class ProductHotFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(BaseApplication.COMMONTAG,"ProductHotFragment  onCreateView");
        View view = UIUtils.getXmlView(R.layout.fragment_producthot);
        ButterKnife.bind(this,view);

        return view;
    }
}

package com.walter.sc.NavigationView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by huangxl on 2016/4/12.
 */
public class ContentFragment extends Fragment{
    public static String KEY_TITLE = "key_title";
    private String mTitle;
  //  private View rootView;//缓存Fragment view
    private TextView rootView;

    public static ContentFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE,title);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(NavigationViewActivity.TAG,"CF onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(NavigationViewActivity.TAG,"CF onCreateView");

        if(rootView==null){
            //rootView=inflater.inflate(R.layout.tab_fragment, null);
             rootView = new TextView(getActivity());
            String title = (String)getArguments().get(KEY_TITLE);
            if (!TextUtils.isEmpty(title)){
                rootView.setText(title);
                rootView.setGravity(Gravity.BOTTOM);
                rootView.setTextSize(40);
            }
            Log.i(NavigationViewActivity.TAG,"CF onCreateView   rootView==null");
            return rootView;
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
            Log.i(NavigationViewActivity.TAG, "CF onCreateView   parent != null");
        }
        return rootView;





    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(NavigationViewActivity.TAG, "CF onViewCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(NavigationViewActivity.TAG, "CF onDestroy");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(NavigationViewActivity.TAG, "CF onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(NavigationViewActivity.TAG, "CF onDetach");
    }
}

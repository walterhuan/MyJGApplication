package com.walter.sc.common.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.walter.sc.common.utils.UIUtils;
import com.walter.sc.myjgapplication.BaseApplication;
import com.walter.sc.myjgapplication.R;

import cz.msebera.android.httpclient.Header;

/**
 * Created by huangxl on 2016/5/23.
 */
public abstract class LoadingPage extends FrameLayout{

    AsyncHttpClient client = new AsyncHttpClient();

    private static final int PAGE_LOADING_STATE = 1;

    private static final int PAGE_ERROR_STATE = 2;

    private static final int PAGE_EMPTY_STATE = 3;

    private static final int PAGE_SUCCESS_STATE = 4;

    private int PAGE_CURRENT_STATE = 1;

    private View loadingView;

    private View errorView;

    private View emptyView;

    private View successView;

    private Context mContext;

    private ViewGroup.LayoutParams lp;

    private ResultState resultState =null;



    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        if (loadingView==null){
            loadingView = UIUtils.getXmlView(R.layout.page_loading);
            addView(loadingView,lp);
        }
        if (errorView==null){
            errorView = UIUtils.getXmlView(R.layout.page_error);
            addView(errorView,lp);
        }
        if (emptyView==null){
            emptyView = UIUtils.getXmlView(R.layout.page_empty);
            addView(emptyView,lp);
        }

        showSafePage();


    }

    private void showSafePage() {
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {
        loadingView.setVisibility(PAGE_CURRENT_STATE==PAGE_LOADING_STATE?View.VISIBLE : View.GONE);
        errorView.setVisibility(PAGE_CURRENT_STATE==PAGE_ERROR_STATE?View.VISIBLE : View.GONE);
        emptyView.setVisibility(PAGE_CURRENT_STATE == PAGE_EMPTY_STATE ? View.VISIBLE : View.GONE);
        if (successView==null){
            successView = View.inflate(mContext,LayoutId(),null);
            addView(successView,lp);
        }
        successView.setVisibility(PAGE_CURRENT_STATE == PAGE_SUCCESS_STATE ? View.VISIBLE : View.GONE);
    }

    public abstract int LayoutId();

    public void show(){
        if (PAGE_CURRENT_STATE != PAGE_LOADING_STATE){
            PAGE_CURRENT_STATE = PAGE_LOADING_STATE;
        }
        String url = url();
        if (TextUtils.isEmpty(url)){
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
        }else{
            client.post(url, params(), new AsyncHttpResponseHandler() {
                @Override
                public void onFinish() {
                    Log.i(BaseApplication.COMMONTAG,"LoadingPageOnFinish======");
                    super.onFinish();
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    Log.i(BaseApplication.COMMONTAG, "LoadingPageonCancel======");
                }

                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.i(BaseApplication.COMMONTAG,"LoadingPageOnSuccess======"+new String(bytes));
                    if (TextUtils.isEmpty(new String(bytes))){
                        resultState = ResultState.EMPTY;
                        resultState.setContent("");
                    }else{
                        resultState = ResultState.SUCCESS;
                        resultState.setContent(new String(bytes));
                    }
                    loadPage();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.i(BaseApplication.COMMONTAG, "LoadingPageOnFailure======" + new String(bytes));
                    resultState = ResultState.ERROR;
                    resultState.setContent("");
                    loadPage();
                }
            });
        }

    }




    private void loadPage() {
        switch (resultState){
            case ERROR:
                PAGE_CURRENT_STATE = PAGE_ERROR_STATE;
                break;
            case EMPTY:
                PAGE_CURRENT_STATE = PAGE_EMPTY_STATE;
                break;
            case SUCCESS:
                PAGE_CURRENT_STATE = PAGE_SUCCESS_STATE;
                break;
        }
        showSafePage();
        if (PAGE_CURRENT_STATE == PAGE_SUCCESS_STATE){
            onSuccess(resultState, successView);
        }
    }

    protected abstract void onSuccess(ResultState resultState, View successView);

    protected abstract String url();

    protected abstract RequestParams params();



    public enum ResultState{

        ERROR(2), EMPTY(3), SUCCESS(4);
        private int state;
        private String content;

         ResultState(int state) {
             this.state=state;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}

package com.walter.sc.czboke.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.walter.sc.common.utils.UIUtils;
import com.walter.sc.czboke.bean.Product;
import com.walter.sc.myjgapplication.BaseApplication;
import com.walter.sc.myjgapplication.R;

import butterknife.ButterKnife;

/**
 * Created by huangxl on 2016/5/27.
 */
public class ProductAllFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_productall);
        ButterKnife.bind(this,view);
        initData();

        return view;
    }

    private void initData() {
        String  msg = "{\n" +
                "    \"data\": [\n" +
                "\t{\n" +
                "\t  \"id\":\"1\",\n" +
                "\t  \"name\":\"超级新手计划\",\n" +
                "\t  \"money\":\"10\",\n" +
                "\t  \"yearLv\":\"8.00\",\n" +
                "\t  \"suodingDays\":\"30\",\n" +
                "\t  \"minTouMoney\":\"100\",\n" +
                "\t  \"memberNum\":\"100\",\n" +
                "\t  \"progress\":\"50\"\n" +
                "\t},\n" +
                "    {\n" +
                "\t  \"id\":\"2\",\n" +
                "\t  \"name\":\"乐享活系列90天计划\",\n" +
                "\t  \"money\":\"20\",\n" +
                "\t  \"yearLv\":\"8.00\",\n" +
                "\t  \"suodingDays\":\"40\",\n" +
                "\t  \"minTouMoney\":\"1000\",\n" +
                "\t  \"memberNum\":\"100\",\n" +
                "\t  \"progress\":\"60\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"3\",\n" +
                "\t  \"name\":\"钱包计划\",\n" +
                "\t  \"money\":\"200\",\n" +
                "\t  \"yearLv\":\"11.00\",\n" +
                "\t  \"suodingDays\":\"100\",\n" +
                "\t  \"minTouMoney\":\"10000\",\n" +
                "\t  \"memberNum\":\"400\",\n" +
                "\t  \"progress\":\"90\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"4\",\n" +
                "\t  \"name\":\"90天理财计划(加息5%)\",\n" +
                "\t  \"money\":\"40\",\n" +
                "\t  \"yearLv\":\"15.00\",\n" +
                "\t  \"suodingDays\":\"80\",\n" +
                "\t  \"minTouMoney\":\"10000\",\n" +
                "\t  \"memberNum\":\"200\",\n" +
                "\t  \"progress\":\"30\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"5\",\n" +
                "\t  \"name\":\"林业局投资商业经营\",\n" +
                "\t  \"money\":\"500\",\n" +
                "\t  \"yearLv\":\"10.00\",\n" +
                "\t  \"suodingDays\":\"150\",\n" +
                "\t  \"minTouMoney\":\"50000\",\n" +
                "\t  \"memberNum\":\"150\",\n" +
                "\t  \"progress\":\"40\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"6\",\n" +
                "\t  \"name\":\"中学老师购买车辆\",\n" +
                "\t  \"money\":\"10\",\n" +
                "\t  \"yearLv\":\"8.00\",\n" +
                "\t  \"suodingDays\":\"60\",\n" +
                "\t  \"minTouMoney\":\"5000\",\n" +
                "\t  \"memberNum\":\"60\",\n" +
                "\t  \"progress\":\"40\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"7\",\n" +
                "\t  \"name\":\"屌丝下海经商计划\",\n" +
                "\t  \"money\":\"20\",\n" +
                "\t  \"yearLv\":\"13.00\",\n" +
                "\t  \"suodingDays\":\"120\",\n" +
                "\t  \"minTouMoney\":\"10000\",\n" +
                "\t  \"memberNum\":\"80\",\n" +
                "\t  \"progress\":\"90\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"8\",\n" +
                "\t  \"name\":\"新西游影视拍摄投资\",\n" +
                "\t  \"money\":\"500\",\n" +
                "\t  \"yearLv\":\"14.00\",\n" +
                "\t  \"suodingDays\":\"100\",\n" +
                "\t  \"minTouMoney\":\"2000\",\n" +
                "\t  \"memberNum\":\"1000\",\n" +
                "\t  \"progress\":\"80\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"9\",\n" +
                "\t  \"name\":\"Java培训老师自己周转\",\n" +
                "\t  \"money\":\"5\",\n" +
                "\t  \"yearLv\":\"8.00\",\n" +
                "\t  \"suodingDays\":\"90\",\n" +
                "\t  \"minTouMoney\":\"1000\",\n" +
                "\t  \"memberNum\":\"100\",\n" +
                "\t  \"progress\":\"40\"\n" +
                "\t},{\n" +
                "\t  \"id\":\"10\",\n" +
                "\t  \"name\":\"阿里巴巴洗钱计划\",\n" +
                "\t  \"money\":\"1000\",\n" +
                "\t  \"yearLv\":\"10.00\",\n" +
                "\t  \"suodingDays\":\"200\",\n" +
                "\t  \"minTouMoney\":\"100000\",\n" +
                "\t  \"memberNum\":\"1000\",\n" +
                "\t  \"progress\":\"60\"\n" +
                "\t}],\n" +
                "    \"success\": true\n" +
                "}";


            String aa = "";
        Gson mGson = new Gson();

        Product  product = mGson.fromJson(msg,Product.class);
            Log.i(BaseApplication.COMMONTAG, "product=" + product);
            if (product==null){
                    Log.i(BaseApplication.COMMONTAG, "product11112222=");
            }
            //aa product都为null
            //aa product.getData() 报错
//            Log.i(BaseApplication.COMMONTAG,"getData="+product.getData());
            if (product!=null){
                    if (product.getData()!=null){

                    }
            }


        Log.i(BaseApplication.COMMONTAG,"size="+product.getData().size()
                +"   success="+product.isSuccess()
                +"   Lastname="
                +product.getData().get(product.getData().size()-1).getName());

            if (TextUtils.isEmpty(product.getData().get(product.getData().size() - 1).getName())){
                    Log.i(BaseApplication.COMMONTAG,"aaaabbbb");
            }

            Log.i(BaseApplication.COMMONTAG,mGson.toJson(product));






    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

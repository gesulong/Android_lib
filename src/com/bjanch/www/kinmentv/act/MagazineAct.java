package com.bjanch.www.kinmentv.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.adapter.MagazineAdapter;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.bean.MagazineList;
import com.bjanch.www.kinmentv.http.HttpCallBack;
import com.bjanch.www.kinmentv.http.JsonParams;
import com.bjanch.www.kinmentv.http.Urls;
import com.bjanch.www.kinmentv.util.HttpHelper;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.magazine_act_layout)

public class MagazineAct extends BaseFrgActivity {

    private MagazineAdapter magazine_adp;
    private MagazineList magazineList;


    @ViewInject(R.id.magazine_lv)
    private RecyclerView magazine_lv;

    private HttpHelper httpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);

        initControl();
        initData();


    }


    /**
     * 初始化一些控件
     */
    private void initControl() {

        httpHelper = new HttpHelper(this);

        magazine_adp = new MagazineAdapter(this);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        magazine_lv.setLayoutManager(layoutManager);

        magazine_lv.setFocusable(false);

        magazine_lv.setFocusableInTouchMode(false);

        magazine_lv.setAdapter(magazine_adp);


    }

    /**
     * 初始化数据
     */
    public void initData() {
        httpHelper.getData(Urls.getInstance().MAGAZINE_DATA, null, new HttpCallBack() {
            @Override
            public void onSuccess(ResponseInfo response) {
                super.onSuccess(response);
                magazineList = JsonParams.getMagazineList(response.result + "");
                if (magazineList.getSuccess().equals("true") && magazineList.getData().size() > 0) {
                    magazine_adp.addMagazines(magazineList.getData());
                } else {
                    ToastTools.frameToast(MagazineAct.this, "无报刊数据", R.drawable.custom_toast);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        });

    }

}

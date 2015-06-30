package com.bjanch.www.kinmentv.act;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.fragment.OnDemandSortListFg;

/**
 * 点播界面
 * Created by wxy on 2015/4/14.
 *
 * @author wxy
 */
public class OnDemandAct extends BaseFrgActivity {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    public static OnDemandAct mOnDemandContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_demand_page_act);
        mOnDemandContext = this;
        init();
    }

    private void init() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.on_demand_sort_list_fl, new OnDemandSortListFg()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

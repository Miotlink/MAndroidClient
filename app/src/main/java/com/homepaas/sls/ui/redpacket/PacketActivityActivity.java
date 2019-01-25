package com.homepaas.sls.ui.redpacket;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerFavourComponent;
import com.homepaas.sls.di.module.FavourModule;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.redpacket.adapter.ActivityPacketListAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 
 *
 * @authoAdministratoror
 * @time 2016/4/21    17:46
 */
public class PacketActivityActivity extends CommonBaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    ActivityPacketListAdapter adapter;
    List<Object> datas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_packet;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        //// FIXME: 2016/4/28
        mockData();
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerFavourComponent.builder()
                .applicationComponent(getApplicationComponent())
                .favourModule(new FavourModule())
                .build().inject(this);
    }

    private void mockData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            datas.add(new Object());
        }
        adapter = new ActivityPacketListAdapter(datas,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}

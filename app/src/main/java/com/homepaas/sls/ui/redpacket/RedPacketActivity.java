package com.homepaas.sls.ui.redpacket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerFavourComponent;
import com.homepaas.sls.domain.entity.RedPacket;
import com.homepaas.sls.mvp.presenter.redpacket.RedPacketPresenter;
import com.homepaas.sls.mvp.view.redpacket.GetRedPacketView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.redpacket.adapter.MyPacketListAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class RedPacketActivity extends CommonBaseActivity implements GetRedPacketView{

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.get_packet)
    TextView getPacket;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    List<Object> datas;
    MyPacketListAdapter adapter;


    @Inject
    RedPacketPresenter presenter;

    public static final String PACKET_ALL = "1";//所有红包
    public static final String PACKET_SPENDABLE = "0";//可使用红包


    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_packet;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();
        //FIXME LATER　PLEASE!!!
        mockData();
        presenter.setGetRedPacketView(this);
        presenter.getMyRedPacket(PACKET_ALL);
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.get_packet)
    public void getRedPacket(){
        mNavigator.getRedPacket(this);
    }

    @Override
    protected void initializeInjector() {

        DaggerFavourComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void mockData() {
        datas = new ArrayList<>();
        adapter = new MyPacketListAdapter(datas,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    private void initialize() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(RedPacketActivity.this, PacketIllustrateActvity.class));
                return true;
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.red_packet, menu);
        return true;
    }

    @Override
    public void render(List<RedPacket> packets) {
        datas.addAll(packets);
        adapter.notifyDataSetChanged();
    }
}

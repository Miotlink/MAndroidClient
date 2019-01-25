package com.homepaas.sls.ui.order.orderplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.order.adapter.ServiceTypeAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 二级数据结构服务类别选择
 *
 * @author CJJ
 * @time 2016/5/4    9:07
 */
public class ServiceTypeActivity extends AppCompatActivity {

    private static final String TAG = "ServiceTypeActivity";

    @Bind(R.id.listview)
    ExpandableListView listview;

    ServiceTypeAdapter adapter;

    List<String> groupDatas;
    List<List<String>> childDatas;
    List<List<Boolean>> checkedList;

    String[] groupNames = new String[]{"家政", "小时工", "保洁"};
    String[] childNames = new String[]{"AAA", "BBB", "CCC", "DDD", "EEE"};
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;

    private String service;

    private int lastSelectGroupPosition = -1;
    private int lastSelectChildPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type_select);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mockData();
        setUpListener();
    }

    private void setUpListener() {

        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                service = childDatas.get(groupPosition).get(childPosition);
                TextView checkBox = (TextView) v.findViewById(R.id.cb);
                if (lastSelectChildPosition != -1) {
                    checkedList.get(lastSelectGroupPosition).set(lastSelectChildPosition, false);
                }
                lastSelectGroupPosition = groupPosition;
                lastSelectChildPosition = childPosition;
                checkedList.get(lastSelectGroupPosition).set(lastSelectChildPosition, true);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    private void mockData() {
        groupDatas = new ArrayList<>();
        childDatas = new ArrayList<>();
        checkedList = new ArrayList<>();
        List<String> list;
        List<Boolean> booleanList;
        for (int i = 0; i < 3; i++) {
            groupDatas.add(groupNames[i]);
            list = new ArrayList<>();
            booleanList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                list.add(childNames[j]);
                booleanList.add(false);
            }
            childDatas.add(list);
            checkedList.add(booleanList);
        }

        adapter = new ServiceTypeAdapter(childDatas, groupDatas, checkedList, this);
        listview.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {
            listview.expandGroup(i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: 2016/3/31 0031
        //  getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra(PlaceOrderActivity.SERVICETYPE, service);
                setResult(Activity.RESULT_OK, intent);
                this.finish();
                return true;
            case R.id.share:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

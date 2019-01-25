package com.homepaas.sls.ui.order.directOrder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerAddressComponent;
import com.homepaas.sls.di.module.AddressModule;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.event.EmptyAddressEvent;
import com.homepaas.sls.mvp.presenter.addressmanage.AddressPresenter;
import com.homepaas.sls.mvp.view.addressmanage.ManageAddressView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.AddressManageAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class AddressManageActivity extends CommonBaseActivity implements ManageAddressView, AddressManageAdapter.EditAddressListener, AddressManageAdapter.HostAction {


    @Inject
    AddressPresenter addressPresenter;
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_TYPE = "KeyType";
    public static final String KEY_POSITION = "KeyPosition";
    public static final int REQUEST_CODE_EDIT = 0xf1;
    public static final int REQUEST_CODE_ADD = 0xf2;
    public static final String KEY_ENTITY = "ENTITY";
    public static final int MODE_BROW = 0;//查看模式
    public static final int MODE_CHOOSE = 1;//选择地址模式
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.no_address_hint)
    TextView noAddressHint;
    @Bind(R.id.empty_view)
    RelativeLayout emptyView;

    private int delIndex = -1;
    private int modifyIndex;
    private int mode;
    private int addressPosition = -1;

    private AddressManageAdapter adapter;
    private List<AddressEntity> addressList;
    private CommonDialog deleteDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manage;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        mode = getIntent().getIntExtra("Mode", MODE_BROW);//默认浏览查看模式
        addressPosition = getIntent().getIntExtra(ConfirmOrderActivity.ADDRESS_POSITION, 0);
        addressPresenter.setAddressView(this);
        addressPresenter.getMyServiceAddressList();
        init();
    }

    @Override
    protected void initData() {

    }

    public void init() {
        SpannableString spStr = new SpannableString("您还没有服务地址，快去新增吧~");
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3fbef9"));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(AddressManageActivity.this, EditAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        }, 11, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        noAddressHint.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        noAddressHint.append(spStr);
        noAddressHint.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerAddressComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressModule(new AddressModule())
                .build()
                .inject(this);
    }

    @Override
    public void renderAddress(List<AddressEntity> addressCollection) {
        addressList = addressCollection;
        if (adapter == null) {
            adapter = new AddressManageAdapter(addressCollection);
            adapter.setHostAction(this);
            adapter.setEditAddressListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//            recyclerView.addItemDecoration(new SimpleItemDecoration(this, SimpleItemDecoration.VERTICAL_LIST));
            recyclerView.setAdapter(adapter);
        }
        if (addressList != null && addressList.isEmpty()) {
            EventBus.getDefault().post(new EmptyAddressEvent());
            setEmptyView(true);
        } else setEmptyView(false);
        adapter.setAddressPosition(addressPosition);
        adapter.setDatas(addressCollection);
    }

    @OnClick(R.id.btn_add_address)
    public void appendAddress() {
        Intent intent = new Intent(this, EditAddressActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    @Override
    public void deleteSuccess(int delIndex) {
        if (delIndex < addressPosition) {
            addressPosition = addressPosition - 1;

        } else if (addressPosition == delIndex) {
            addressPosition = -1;
        }
        addressPresenter.getMyServiceAddressList();
    }

    @Override
    public void onEdit(int index, AddressEntity address) {
        //跳转到修改服务地址页面
        modifyIndex = index;
        Intent intent = new Intent(this, EditAddressActivity.class);
        intent.putExtra(KEY_ENTITY, address);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            AddressEntity en = (AddressEntity) data.getSerializableExtra(KEY_ENTITY);
            switch (requestCode) {
                case REQUEST_CODE_EDIT:
                    adapter.modifyItem(modifyIndex, en);
                    break;
                case REQUEST_CODE_ADD:
                    addressPresenter.getMyServiceAddressList();
                    break;
            }
        }
    }

    @Override
    public void startAddressDelete(int index, String id) {
        delIndex = index;
        addressPresenter.deleteServiceAddress(id, index);
    }

    @Override
    protected void retrieveData() {
        addressPresenter.getMyServiceAddressList();
    }

    @Override
    public void onItemSelected(int pos) {


        if (mode == MODE_CHOOSE) {
            Intent resultIntent = new Intent();
            if (addressList != null && addressList.size() > pos) {
                resultIntent.putExtra(KEY_ADDRESS, addressList.get(pos));
                resultIntent.putExtra(KEY_TYPE, "1");
            } else {
                resultIntent.putExtra(KEY_ADDRESS, "");
                resultIntent.putExtra(KEY_TYPE, "0");
            }
            resultIntent.putExtra(KEY_POSITION, pos);
            setResult(Activity.RESULT_OK, resultIntent);
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void startDeleteAddress(int pos) {
        delIndex = pos;
        deleteDialog = new CommonDialog.Builder()
                .setContent("确定删除该服务地址")
                .setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                })
                .setConfirmButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressPresenter.deleteServiceAddress(addressList.get(delIndex).getId(), delIndex);
                    }
                }).setTitle("删除服务地址")
                .create();
        deleteDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (mode == MODE_CHOOSE) {
            Intent resultIntent = new Intent();
            if (addressPosition >= 0 && addressList != null && addressList.size() > addressPosition) {
                resultIntent.putExtra(KEY_ADDRESS, addressList.get(addressPosition));
                resultIntent.putExtra(KEY_TYPE, "1");
            } else {
                resultIntent.putExtra(KEY_ADDRESS, "");
                resultIntent.putExtra(KEY_TYPE, "0");
            }
            resultIntent.putExtra(KEY_POSITION, addressPosition);
            setResult(Activity.RESULT_OK, resultIntent);
            ActivityCompat.finishAfterTransition(this);
        } else {
            finish();
        }
        return super.onSupportNavigateUp();
    }

    public void setEmptyView(boolean empty) {
        if (empty) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

}

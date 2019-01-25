package com.homepaas.sls.ui.order.directOrder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerAddressComponent;
import com.homepaas.sls.di.module.AddressModule;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.mvp.presenter.ServicePresenter;
import com.homepaas.sls.mvp.presenter.addressmanage.AddressPresenter;
import com.homepaas.sls.mvp.view.GetSystemConfigView;
import com.homepaas.sls.mvp.view.addressmanage.UpdateServiceAddressView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.location.RegisterAddressMapActivity;
import com.homepaas.sls.ui.location.location.AddressModel;
import com.homepaas.sls.ui.location.location.GeoCoderHelper;
import com.homepaas.sls.ui.location.location.SuggestionAddressModel;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.MyTextWatcher;
import com.homepaas.sls.ui.widget.ScalableHintEditText;
import com.homepaas.sls.util.KeyBoardUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.KEY_ENTITY;

/**
 * 本页面既是服务地址编辑页面也充当服务地址新增页面
 * ActionSheetPickerView.OnItemSelectListener,
 */
public class EditAddressActivity extends CommonBaseActivity implements UpdateServiceAddressView, GetSystemConfigView {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.label_selector)
    LinearLayout labelSelector;
    @Bind(R.id.name)
    ScalableHintEditText name;
    @Bind(R.id.phone)
    ScalableHintEditText phone;
    @Bind(R.id.address)
    ScalableHintEditText addressTv;
    @Bind(R.id.detail_address)
    ScalableHintEditText detailAddressTv;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView18)
    TextView textView18;
    @Bind(R.id.img_de_address_info)
    ImageView imgDeAddressInfo;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.txt_man)
    TextView txtMan;
    @Bind(R.id.txt_woman)
    TextView txtWoman;
    @Inject
    AddressPresenter addressPresenter;
    @Inject
    ServicePresenter servicePresenter;
    @Bind(R.id.rl_no)
    RadioButton rlNo;
    @Bind(R.id.rl_family)
    RadioButton rlFamily;
    @Bind(R.id.rl_company)
    RadioButton rlCompany;
    @Bind(R.id.ly_address)
    LinearLayout lyAddress;
    @Bind(R.id.rab)
    RadioGroup rab;

    private LatLng latLng;
    private AddressEntity entity;
    private CommonDialog commonDialog;
    private GeoCoderHelper geoCoderHelper;

    private static final String TAG = "EditAddressActivity";
    private static final int REQUESTCODE_GETADDRESS = 0xf8;
    private boolean addAction;
    private String gender = "-1";//0男生，其他为女生

    @Override
    protected int getLayoutId() {
        return R.layout.activity_append_address;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        servicePresenter.setConfigView(this);
        servicePresenter.getSystemConfig();
        addressPresenter.setUpdateServiceAddressView(this);
        init();
        KeyBoardUtil.openKeybord(name, this);
        detailAddressTv.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (TextUtils.isEmpty(s1)) {
                    imgDeAddressInfo.setVisibility(View.GONE);
                } else {
                    imgDeAddressInfo.setVisibility(View.VISIBLE);
                }
            }
        });


        detailAddressTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                imgDeAddressInfo.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        KeyBoardUtil.hideActivityKeyboard(this);
        commonDialog = new CommonDialog.Builder()
                .setContent("是否放弃编辑服务地址")
                .setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commonDialog.dismiss();
                    }
                })
                .setConfirmButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogOnBackPressed();
                    }
                }).setTitle("")
                .create();
        commonDialog.show(getSupportFragmentManager(), null);
    }

    public void dialogOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            KeyBoardUtil.hideActivityKeyboard(this);
            save();
        } else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存操作，触发修改提交或者增加提交
     */
    private void save() {
        String contact = name.getText().toString().trim();
        String phoneNumber = phone.getText().toString();
        String address = addressTv.getText().toString().trim();
        String address1 = detailAddressTv.getText().toString().trim();
        String lab = rlNo.isSelected() ? "无" : rlFamily.isSelected() ? "家" : "公司";
        if (entity == null)
            entity = new AddressEntity();
        entity.setContact(contact);
        entity.setGender(gender);
        entity.setPhoneNumber(phoneNumber);
        entity.setDetailAddress(address1);
        if (!TextUtils.isEmpty(address))
            entity.setAddress(address.replace("\t", ""));
        entity.setTag(lab);
        if (latLng != null) {
            entity.setLat(String.valueOf(latLng.latitude));
            entity.setLng(String.valueOf(latLng.longitude));
        }
        if (!validateParam())
            return;
        if (addAction)//增加服务地址
            addressPresenter.addServiceAddress(entity);
        else//更新服务地址
            addressPresenter.updateServiceAddress(entity);
    }

    @Override
    protected void retrieveData() {
        super.retrieveData();
        save();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    private boolean validateParam() {
        if (TextUtils.isEmpty(entity.contact)) {
            showMessage("请填写联系人姓名");
            return false;
        }
        if (TextUtils.isEmpty(entity.phoneNumber)) {
            showMessage("请填写联系电话");
            return false;
        }
        if (!isMobile(entity.phoneNumber) || entity.phoneNumber.length() != 11) {
            showMessage("请填写正确手机号");
            return false;
        }
        if (TextUtils.isEmpty(entity.getAddress())) {
            showMessage("请填写服务地址");
            return false;
        }
        if (TextUtils.equals("-1", gender)) {
            showMessage("请选择性别");
            return false;
        }
        if (!isMobile(entity.phoneNumber) && entity.phoneNumber.length() != 11) {
            showMessage("手机号格式有误");
            return false;
        }
        return true;
    }

    public void init() {
        Intent intent = getIntent();
        entity = (AddressEntity) intent.getSerializableExtra(KEY_ENTITY);
        if (entity == null)
            addAction = true;
        if (addAction)
            toolbar.setTitle("新增服务地址");
        else {
            mapData();
            toolbar.setTitle("编辑服务地址");
        }
    }

    private void mapData() {
        name.setText(entity.getContact());
        phone.setText(entity.getPhoneNumber());
        gender = entity.getGender();
        chanageBtnStyle();
        addressTv.setText(entity.getAddress());
        detailAddressTv.setText(entity.getDetailAddress());
        if (TextUtils.isEmpty(entity.getTag())) {
            rlNo.setSelected(true);
        } else {
            if (entity.getTag().equals("无")) {
                rlNo.setChecked(true);
            } else if (entity.getTag().equals("家")) {
                rlFamily.setChecked(true);
            } else {
                rlCompany.setChecked(true);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyBoardUtil.hideActivityKeyboard(this);
    }

    @OnClick(R.id.address)
    public void address() {
        startActivityForResult(new Intent(this, RegisterAddressMapActivity.class), REQUESTCODE_GETADDRESS);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerAddressComponent.builder()
                .addressModule(new AddressModule())
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

//    /**
//     * 选择地址类型
//     */
//    @OnClick(R.id.label_selector)
//    public void selectAddressLabel() {
//        if (sheetPickerView == null) {
//            sheetPickerView = ActionSheetPickerView.newInstance();
//            sheetPickerView.setOnItemSelectListener(this);
//        }
//        sheetPickerView.show(this);
//    }

    /**
     * 接受地址回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUESTCODE_GETADDRESS) {
                int type = data.getIntExtra("type", 0);
                AddressModel addressModel;
                switch (type) {
                    case 1://地图移动坐标地址[用户当前地址]
                        addressModel = data.getParcelableExtra("addressModel");
                        latLng = addressModel.latLng;
                        if (addressModel == null)
                            return;
                        addressTv.setText(addressModel.province + "\t" + addressModel.city + "\t" + addressModel.district);
                        detailAddressTv.setText(addressModel.resultAdress.replace(addressModel.province + addressModel.city + addressModel.district, ""));
                        break;
                    case 2://poi地址[搜索框搜索地址]
                        addressModel = data.getParcelableExtra("addressModel");
                        latLng = addressModel.latLng;
                        //pio地址没有那么详细，需要反地理编码进行获取
                        geoCoderHelper = new GeoCoderHelper();
                        geoCoderHelper.setLatLng(latLng);
                        geoCoderHelper.setOnReverseGeoCodeResultListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
                            @Override
                            public void result(Object obj, LatLng latLng) {
                                ReverseGeoCodeResult reverseGeoCodeResult = (ReverseGeoCodeResult) obj;
                                ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
                                addressTv.setText(addressDetail.province + "\t" + addressDetail.city + "\t" + addressDetail.district);
                            }
                        });
                        detailAddressTv.setText(addressModel.poiAddress+addressModel.poiName);
                        geoCoderHelper.reverseGeoCode();
                        if (addressModel == null)
                            return;
                        break;
                    case 3://搜索地址[当前地址附近地址列表]
                        final SuggestionAddressModel suggestionAddressModell = data.getParcelableExtra("addressModel");
                        geoCoderHelper = new GeoCoderHelper();
                        latLng = suggestionAddressModell.getLatLng();
                        geoCoderHelper.setLatLng(latLng);
                        geoCoderHelper.setOnReverseGeoCodeResultListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
                            @Override
                            public void result(Object obj, LatLng latLng) {
                                ReverseGeoCodeResult reverseGeoCodeResult = (ReverseGeoCodeResult) obj;
                                ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
                                addressTv.setText(addressDetail.province + "\t" + addressDetail.city + "\t" + addressDetail.district);
                                detailAddressTv.setText(reverseGeoCodeResult.getAddress().replace(addressDetail.province + addressDetail.city + addressDetail.district, "")+suggestionAddressModell.getKey());
                                detailAddressTv.setSelection(detailAddressTv.getText().toString().length());
                            }
                        });
                        geoCoderHelper.reverseGeoCode();
                        break;
                }
                detailAddressTv.requestFocus();
                detailAddressTv.setSelection(detailAddressTv.getText().toString().length());
            }
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
//
//    @Override
//    public void onItemSelect(String address) {
//        tag.setVisibility(View.VISIBLE);
//        tag.setText(address);
//    }

    /**
     * 保存地址到服务器成功回调,新增和修改的回调，自行判断是哪一种
     */
    @Override
    public void onUpdateServiceAddressSuccess(AddressEntity addressEntity) {

        Intent intent = new Intent();
        intent.putExtra(KEY_ENTITY, addressEntity);
        setResult(Activity.RESULT_OK, intent);
        ActivityCompat.finishAfterTransition(this);
    }

    /**
     * 标签回调
     *
     * @param configEntity
     */
    @Override
    public void render(SystemConfigEntity configEntity) {
        Log.i(TAG, "render: " + configEntity);
    }

    @Override
    protected void onDestroy() {
        addressPresenter.destroy();
        servicePresenter.destroy();
        if (geoCoderHelper != null)
            geoCoderHelper.clear();
        KeyBoardUtil.hideActivityKeyboard(this);
        super.onDestroy();
    }

    @OnClick(R.id.img_de_address_info)
    public void onViewClicked() {
        detailAddressTv.setText("");
        imgDeAddressInfo.setVisibility(View.GONE);
    }

    @OnClick({R.id.txt_man, R.id.txt_woman, R.id.rl_no, R.id.rl_family, R.id.rl_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_man:
                gender = "0";
                chanageBtnStyle();
                break;
            case R.id.txt_woman:
                gender = "1";
                chanageBtnStyle();
                break;
            case R.id.rl_no:
                rlNo.setSelected(true);
                rlCompany.setSelected(false);
                rlFamily.setSelected(false);
                break;
            case R.id.rl_company:
                rlNo.setSelected(false);
                rlCompany.setSelected(true);
                rlFamily.setSelected(false);
                break;
            case R.id.rl_family:
                rlNo.setSelected(false);
                rlCompany.setSelected(false);
                rlFamily.setSelected(true);
                break;
        }
    }

    public void chanageBtnStyle() {
        if (TextUtils.equals("0", gender)) {
            txtMan.setBackgroundResource(R.drawable.btn_address_gender_bg_red);
            txtWoman.setBackgroundResource(R.drawable.btn_address_gender_bg_gray);
            txtMan.setTextColor(getResources().getColor(R.color.count_down_time));
            txtWoman.setTextColor(getResources().getColor(R.color.order_list_info_color));
        } else {
            txtMan.setBackgroundResource(R.drawable.btn_address_gender_bg_gray);
            txtWoman.setBackgroundResource(R.drawable.btn_address_gender_bg_red);
            txtMan.setTextColor(getResources().getColor(R.color.order_list_info_color));
            txtWoman.setTextColor(getResources().getColor(R.color.count_down_time));
        }
    }

    @OnClick(R.id.ly_address)
    public void onClicked() {
        startActivityForResult(new Intent(this, RegisterAddressMapActivity.class), REQUESTCODE_GETADDRESS);
    }
}

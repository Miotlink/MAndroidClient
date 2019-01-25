package com.homepaas.sls.ui.order.chooseservice;

import android.support.design.widget.AppBarLayout;
import android.view.MenuItem;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.mvp.presenter.ServiceListPresenter;
import com.homepaas.sls.mvp.view.ServiceListView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/11/17.
 */

public class TitleServiceActivity extends CommonBaseActivity implements ServiceListView{

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.text)
    TextView text;


    private List<ServiceTypeEx> allServiceTypeExList;

    @Inject
    ServiceListPresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_title_service;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        this.presenter.setView(this);
        this.presenter.loadServiceList();
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    @Override
    public void render(List<ServiceTypeEx> serviceTypeExList) {
        allServiceTypeExList=new ArrayList<ServiceTypeEx>();
        //过滤到不符合条件的数据结构
        for (ServiceTypeEx serviceTypeEx : serviceTypeExList) {
            if (serviceTypeEx.getChildren() != null &&
                    !serviceTypeEx.getChildren().isEmpty()) {
                allServiceTypeExList.add(serviceTypeEx);
            }
        }
        ServiceTypeEx addServiceTypeEx=getServiceTypeEx(allServiceTypeExList,"维修");
        if(addServiceTypeEx.getChildren()!=null&&!addServiceTypeEx.getChildren().isEmpty()){
            TitleServiceItemFragment.show(this,addServiceTypeEx);
        }

    }

    /**
     * 查找相对应的服务类型
     */

    private ServiceTypeEx getServiceTypeEx(List<ServiceTypeEx> list,String serviceStr){
        for(ServiceTypeEx firstServiceTpyeEx:list){
            if(firstServiceTpyeEx.getTypeName().equals(serviceStr)){
                return firstServiceTpyeEx;
            }else{
                if (firstServiceTpyeEx.getChildren()!=null&&!firstServiceTpyeEx.getChildren().isEmpty()){
                    for (ServiceTypeEx secondServiceTypeEx:firstServiceTpyeEx.getChildren()){
                        if(secondServiceTypeEx.getTypeName().equals(serviceStr)){
                            return secondServiceTypeEx;
                        }else{
                            if (secondServiceTypeEx.getChildren()!=null&&!secondServiceTypeEx.getChildren().isEmpty()){
                                for (ServiceTypeEx thirdServiceTypeEx:secondServiceTypeEx.getChildren()){
                                    if(thirdServiceTypeEx.getTypeName().equals(serviceStr)){
                                        return thirdServiceTypeEx;
                                    }else{
                                        if (thirdServiceTypeEx.getChildren()!=null&&!thirdServiceTypeEx.getChildren().isEmpty()){
                                            for (ServiceTypeEx fourthServiceTypeEx:thirdServiceTypeEx.getChildren()){
                                                if(fourthServiceTypeEx.getTypeName().equals(serviceStr)){
                                                    return  fourthServiceTypeEx;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * EventBus 直接监听事件
     * @param serviceTypeEx 传递的数据结构
     */
    @Subscribe
    public void onEventMainThread(ServiceTypeEx serviceTypeEx) {
     text.setText(serviceTypeEx.getTypeName());
    }


}

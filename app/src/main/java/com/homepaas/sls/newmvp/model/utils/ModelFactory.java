package com.homepaas.sls.newmvp.model.utils;


import com.homepaas.sls.newmvp.contract.AddServiceNumContract;
import com.homepaas.sls.newmvp.contract.ColleactionMerchantContract;
import com.homepaas.sls.newmvp.contract.ColleactionWorkerContract;
import com.homepaas.sls.newmvp.contract.ComplaintContract;
import com.homepaas.sls.newmvp.contract.HistoryContract;
import com.homepaas.sls.newmvp.contract.HomeContract;
import com.homepaas.sls.newmvp.contract.HomeListContract;
import com.homepaas.sls.newmvp.contract.MerchantContract;
import com.homepaas.sls.newmvp.contract.MessageContract;
import com.homepaas.sls.newmvp.contract.NearbyServiceOrWorkerContract;
import com.homepaas.sls.newmvp.contract.OrderContract;
import com.homepaas.sls.newmvp.contract.OrderStatusContract;
import com.homepaas.sls.newmvp.contract.RecentlyServiceOrWorkerContract;
import com.homepaas.sls.newmvp.contract.TestContract;
import com.homepaas.sls.newmvp.model.impl.IAddServiceNumModel;
import com.homepaas.sls.newmvp.model.impl.IColleactionMerchantModel;
import com.homepaas.sls.newmvp.model.impl.IColleactionWorkerModel;
import com.homepaas.sls.newmvp.model.impl.IComplaintModel;
import com.homepaas.sls.newmvp.model.impl.IHistoryModel;
import com.homepaas.sls.newmvp.model.impl.IHomeListModel;
import com.homepaas.sls.newmvp.model.impl.IHomeModel;
import com.homepaas.sls.newmvp.model.impl.IMerchantModel;
import com.homepaas.sls.newmvp.model.impl.IMessageListModel;
import com.homepaas.sls.newmvp.model.impl.INearbyServiceOrWorkerModel;
import com.homepaas.sls.newmvp.model.impl.IOrderModel;
import com.homepaas.sls.newmvp.model.impl.IOrderStatusModel;
import com.homepaas.sls.newmvp.model.impl.IRecentlyServiceOrWorkerModel;
import com.homepaas.sls.newmvp.model.impl.ITestModel;

/**
 * 保存moudle单例 ，便于动态替换moudle的实现方式
 */

public class ModelFactory {

    private TestContract.Model iTestModel = new ITestModel();
    private HomeListContract.Model homeListModel = new IHomeListModel();
    private HistoryContract.Model historyModel = new IHistoryModel();
    private ComplaintContract.Model complaintModel = new IComplaintModel();
    private AddServiceNumContract.Model addServiceNumModel = new IAddServiceNumModel();
    private HomeContract.Model homeModel=new IHomeModel();
    private OrderContract.Model orderModel=new IOrderModel();
    private NearbyServiceOrWorkerContract.Model nearbyServiceOrWorkerModel=new INearbyServiceOrWorkerModel();
    private RecentlyServiceOrWorkerContract.Model recentlyServiceOrWorkerModel=new IRecentlyServiceOrWorkerModel();

    private OrderStatusContract.Model orderStatusModel=new IOrderStatusModel();
    private MessageContract.Model messageModel=new IMessageListModel();
    private ColleactionMerchantContract.Model colleactionMerchantModel=new IColleactionMerchantModel();
    private ColleactionWorkerContract.Model colleactionWorkerModel=new IColleactionWorkerModel();
    private MerchantContract.Model merchantContract=new IMerchantModel();

    private static ModelFactory moduleFactory;


    public static ModelFactory getInstance() {
        if (moduleFactory == null) {
            synchronized (ModelFactory.class) {
                if (moduleFactory == null)
                    moduleFactory = new ModelFactory();
            }
        }
        return moduleFactory;
    }

    public NearbyServiceOrWorkerContract.Model getNearbyServiceOrWorkerModel() {
        return nearbyServiceOrWorkerModel;
    }

    public OrderStatusContract.Model getOrderStatusModel() {
        return orderStatusModel;
    }

    public RecentlyServiceOrWorkerContract.Model getRecentlyServiceOrWorkerModel() {
        return recentlyServiceOrWorkerModel;
    }

    public MessageContract.Model getMessageModel() {
        return messageModel;
    }

    public OrderContract.Model getOrderModel() {
        return orderModel;
    }

    public HomeContract.Model getHomeModel() {
        return homeModel;
    }

    public AddServiceNumContract.Model getAddServiceNumModel() {
        return addServiceNumModel;
    }

    public ComplaintContract.Model getComplaintModel() {
        return complaintModel;
    }

    public ColleactionMerchantContract.Model getColleactionMerchantModel() {
        return colleactionMerchantModel;
    }

    public ColleactionWorkerContract.Model getColleactionWorkerModel() {
        return colleactionWorkerModel;
    }

    public MerchantContract.Model getMerchantContract() {
        return merchantContract;
    }

    public TestContract.Model getiTestModel() {
        return iTestModel;
    }

    public HomeListContract.Model getHomeListModel() {
        return homeListModel;
    }

    public HistoryContract.Model getHistoryModel() {
        return historyModel;
    }
}

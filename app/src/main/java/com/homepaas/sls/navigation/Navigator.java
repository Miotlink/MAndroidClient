package com.homepaas.sls.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.WebViewActivity;
import com.homepaas.sls.ui.account.WalletActivity;
import com.homepaas.sls.ui.comment.AddCommentActivity;
import com.homepaas.sls.ui.comment.MyCommentActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.coupon.CouponActivity;
import com.homepaas.sls.ui.detail.BusinessDetailActivity;
import com.homepaas.sls.ui.detail.WorkerDetailActivity;
import com.homepaas.sls.ui.homepage_3_3_0.CategoryActivity;
import com.homepaas.sls.ui.login.FastLoginActivity;
import com.homepaas.sls.ui.login.ModifyPasswordActivity;
import com.homepaas.sls.ui.login.RegisterActivity;
import com.homepaas.sls.ui.login.ResetPasswordActivity;
import com.homepaas.sls.ui.newdetail.MerchantWorkerActivity;
import com.homepaas.sls.ui.order.AddServiceNumActivity;
import com.homepaas.sls.ui.order.ComplaintActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.order.history.HistoryOrderActivity;
import com.homepaas.sls.ui.order.manage.OrderManageActivity;
import com.homepaas.sls.ui.order.orderplace.FlatServiceTypeActivity;
import com.homepaas.sls.ui.order.orderplace.NewFlatServiceTypeActivity;
import com.homepaas.sls.ui.order.pay.DirectPayActivity;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.personalcenter.CallLogsActivity;
import com.homepaas.sls.ui.personalcenter.EmptyMessageActivity;
import com.homepaas.sls.ui.personalcenter.MessageCenterActivity;
import com.homepaas.sls.ui.personalcenter.WriteBackInvitationActivity;
import com.homepaas.sls.ui.personalcenter.collection.NewCollectionActivity;
import com.homepaas.sls.ui.personalcenter.more.FeedbackActivity;
import com.homepaas.sls.ui.personalcenter.more.GeneralWebViewActivity;
import com.homepaas.sls.ui.personalcenter.more.MoreActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.PersonalMessageActivity;
import com.homepaas.sls.ui.qrcode.QrCodeScanActivity;
import com.homepaas.sls.ui.redpacket.PacketActivityActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewRedPacketActivity;
import com.homepaas.sls.ui.search.NewSearchActivity;
import com.homepaas.sls.ui.search.SearchActivity;
import com.homepaas.sls.ui.widget.CallDialogFragment;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/12 0012
 *
 * @author zhudongjie .
 */
@Singleton
public class Navigator {

    public static final String SEARCH_CONTENT = "searchContent";

    public static final String WORKER_ID = "workerId";
    public static final String MY_ID = "myId";
    public static final String TYPE = "type";
    public static final String SERVICE_INFO = "SERVICEINFO";

    public static final String BUSINESS_ID = "businessId";

    public static final String WEB_VIEW_URL = "webViewUrl";

    public static final String WEB_VIEW_TITLE = "webViewTitle";

    public static final String REGISTER_PASSWORD = "password";

    public static final String REGISTER_ACCOUNT = "account";

    public static final String MESSAGE_TITLE = "message_title";

    public static final String MESSAGE_CONTENT = "message_content";

    public static final String SEARCH_LAT = "latitude";

    public static final String SEARCH_LNG = "longitude";

    public static final String SEARCH_TYPE = "type";
    public static final String SEARCH_EDITTEXTSTR="edittext_str";

    public static final int SERVICE_TYPE_LIST_REQUEST_CODE = 0x00101;
    public static final String SERVICELISTTYPE = "servicelisttype";
    public static final String ADDRESS_ID="address_id";
    public static final String BUSSINESS_LIST="business_list";
    public static final String SELECT_POSITION="select_position";
    public static final String SERVICE_NAME="service_name";
    public static final String ID = "ID";//ID
    public static final int REQUEST_CODE_GET_MY_ADDRESS = 0x00102;//选择一个服务地址
    public static final int BUSINESS_SERVICE_TYPE_LIST_REQUEST_CODE=99;
    //类目页面
    public static final String SERVICE_ID = "serviceId";
    public static final String LONGTITUDE = "longtitude";
    public static final String LATITUDE = "latitude";

    public static final int MODE_BROW = 0;//查看模式
    public static final int MODE_CHOOSE = 1;//选择地址模式
    @Inject
    public Navigator() {
    }

    public void showCategory(Context context, String serviceId, String longtitude, String latitude){
        Intent intent = new Intent( context, CategoryActivity.class);
        intent.putExtra(SERVICE_ID, serviceId);
        intent.putExtra(LONGTITUDE, longtitude);
        intent.putExtra(LATITUDE, latitude);
        context.startActivity(intent);
    }
    @Deprecated
    public void showWorkerDetail(Context context, String workerId) {
        Intent intent = new Intent(context, WorkerDetailActivity.class);
        intent.putExtra(WORKER_ID, workerId);
        context.startActivity(intent);
    }

    @Deprecated
    public void showBusinessDetail(Context context, String businessId) {
        Intent intent = new Intent(context, BusinessDetailActivity.class);
        intent.putExtra(BUSINESS_ID, businessId);
        context.startActivity(intent);
    }
//    String id = getIntent().getStringExtra(Navigator.MY_ID);
//    String type = getIntent().getStringExtra(Navigator.TYPE);
    public void showMerchantWorkerDetail(Context context,String type, String id ){
        Intent intent = new Intent(context, MerchantWorkerActivity.class);
        intent.putExtra(MY_ID, id);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    public void searchText(Fragment fragment, double latitude, double longitude, String type, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), SearchActivity.class);
        intent.putExtra(SEARCH_LAT, latitude);
        intent.putExtra(SEARCH_LNG, longitude);
        intent.putExtra(SEARCH_TYPE, type);
        fragment.startActivityForResult(intent, requestCode);
    }

    public void NewSearchText(Fragment fragment, double latitude, double longitude, String type, int requestCode,String edittextStr){
        Intent intent = new Intent(fragment.getActivity(), NewSearchActivity.class);
        intent.putExtra(SEARCH_LAT, latitude);
        intent.putExtra(SEARCH_LNG, longitude);
        intent.putExtra(SEARCH_TYPE, type);
        intent.putExtra(SEARCH_EDITTEXTSTR,edittextStr);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Deprecated
    public void searchText(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), SearchActivity.class);

        fragment.startActivityForResult(intent, requestCode);
    }


    public void resetPassword(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }

    public void startHistoryActivity(Context context) {
        Intent intent = new Intent(context, HistoryOrderActivity.class);
        context.startActivity(intent);
    }


    public void modifyPassword(Context context) {
        Intent intent = new Intent(context, ModifyPasswordActivity.class);
        context.startActivity(intent);
    }

    public void showCollections(Context context) {
        context.startActivity(new Intent(context, NewCollectionActivity.class));
    }

    public void showCallLogs(Context context) {
        context.startActivity(new Intent(context, CallLogsActivity.class));
    }

    public void viewMessage(Context context) {
        context.startActivity(new Intent(context, MessageCenterActivity.class));
    }

    public void otherMessage(Context context) {
        context.startActivity(new Intent(context, MoreActivity.class));
    }

    public void showPersonalDetail(Context context) {
        context.startActivity(new Intent(context, PersonalMessageActivity.class));
    }

    public void loadWebView(Context context, String url, String title) {
        Intent intent = new Intent(context, GeneralWebViewActivity.class);
        intent.putExtra(WEB_VIEW_URL, url);
        intent.putExtra(WEB_VIEW_TITLE, title);
        context.startActivity(intent);
    }

    public void register(Activity context, int requestCode) {
        context.startActivityForResult(new Intent(context, RegisterActivity.class), requestCode);
    }

    public void register(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getActivity(), RegisterActivity.class), requestCode);
    }

    public void login(Activity context, int requestCode) {
        context.startActivityForResult(new Intent(context, FastLoginActivity.class), requestCode);
//        context.overridePendingTransition(R.anim.anim_bottom_in,R.anim.anim_no);
    }


    public void userLoginOut(Activity context, int requestCode) {
        context.startActivityForResult(new Intent(context, FastLoginActivity.class).putExtra("isLoginOutTime",true), requestCode);
//        context.overridePendingTransition(R.anim.anim_bottom_in,R.anim.anim_no);
    }

    public void main(Activity context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    /*
     为了能够使Fragment的onActivityResult得到回调，要调用Fragment的startActivityForResult
     */
    public void login(Fragment fragment, int requestCode) {

        fragment.startActivityForResult(new Intent(fragment.getActivity(), FastLoginActivity.class), requestCode);
    }

    public void feedback(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    public void addServiceNum(Context context,String orderID,String serviceI) {
        context.startActivity(new Intent(context, AddServiceNumActivity.class).putExtra(StaticData.ORDER_ID,orderID).putExtra(StaticData.SERVICE_ID,serviceI));
    }

    public void dial(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:" + number);
        intent.setData(uri);
        context.startActivity(intent);
    }

//    public void selectServiceProviderOrWorker(Activity context,String address,String serviceId){
//        Intent intent = new Intent(context, SelectServiceProviderOrWorkerActivity.class);
//        intent.putExtra(StaticData.ADDRESS,address);
//        intent.putExtra(StaticData.SERVICE_ID,serviceId);
//        context.startActivityForResult(intent,SELECT_WORKER_SERVICE);
//    }
    public void call(Activity context,String number){
        CallDialogFragment fragment = CallDialogFragment.newInstance(number);
        if (context instanceof FragmentActivity) {
            fragment.show(((FragmentActivity) context).getSupportFragmentManager(),"");
        }
    }

    public void showShortMessage(Context context, String title, String content) {
        Intent intent = new Intent(context, EmptyMessageActivity.class);
        intent.putExtra(MESSAGE_TITLE, title);
        intent.putExtra(MESSAGE_CONTENT, content);
        context.startActivity(intent);
    }

/*    public void placeOrder(Context context, String mId) {
        Intent intent = new Intent(context, WorkerDetailActivity.class);
        intent.putExtra(WORKER_ID, mId);
        context.startActivity(intent);
    }*/

    public void serviceType(AppCompatActivity context, String type, String id,String serviceName) {
        Intent intent = new Intent(context, FlatServiceTypeActivity.class);
        intent.putExtra(SERVICELISTTYPE, type);
        intent.putExtra(SERVICE_NAME,serviceName);
        intent.putExtra(ID, id);
        context.startActivityForResult(intent, SERVICE_TYPE_LIST_REQUEST_CODE);
    }


    public void serviceType(AppCompatActivity context, String type, String id) {
        Intent intent = new Intent(context, FlatServiceTypeActivity.class);
        intent.putExtra(SERVICELISTTYPE, type);
        intent.putExtra(ID, id);
        context.startActivityForResult(intent, SERVICE_TYPE_LIST_REQUEST_CODE);
    }

    public void serviceBusinessType(AppCompatActivity context, BusinessServiceTypeInfo businessServiceTypeInfo, String id, String addressId,int selectPosition,String serviceName) {
        Intent intent = new Intent(context, NewFlatServiceTypeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(SERVICE_NAME,serviceName);
        bundle.putInt(SELECT_POSITION,selectPosition);
        bundle.putString(ID, id);
        bundle.putString(ADDRESS_ID,addressId);
        bundle.putSerializable(BUSSINESS_LIST,businessServiceTypeInfo);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, BUSINESS_SERVICE_TYPE_LIST_REQUEST_CODE);
    }

    public void getOrder(Context context, int type, String id) {
        com.homepaas.sls.ui.order.directOrder.PlaceOrderActivity.start(context, type, id);
    }

    public void getOrder(Context context, int type, String id, String gender) {
        com.homepaas.sls.ui.order.directOrder.PlaceOrderActivity.start(context, type, id, gender);
    }

    public void showComment(Context context) {
        context.startActivity(new Intent(context, MyCommentActivity.class));
    }

    public void showMyAccount(Context context) {
//        context.startActivity(new Intent(context, AccountActivity.class));
        context.startActivity(new Intent(context, WalletActivity.class));
    }

    public void showOrder(Context context) {
        context.startActivity(new Intent(context, OrderManageActivity.class));
    }

    public void showMyCoupons(Context context) {
        context.startActivity(new Intent(context, CouponActivity.class));
    }
    public void showMyNewRed(Context context) {
        context.startActivity(new Intent(context, NewRedPacketActivity.class));
    }
    public void payOrder(Context context, String orderCode,String totalMoney,String serviceProviderName) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra(Constant.OrderId,orderCode);
        intent.putExtra("ServiceProviderName",serviceProviderName);
        intent.putExtra("TotalMoney",totalMoney);
        context.startActivity(intent);
    }

    public void getRedPacket(Context context) {
        context.startActivity(new Intent(context, PacketActivityActivity.class));
    }

    public void addComment(Context context,String orderId,String ownerId,String type){
        AddCommentActivity.start(context,orderId,ownerId,type);
    }
    public void addComment(Fragment fragment,int requestCode,String orderId,String ownerId,String type){
        AddCommentActivity.start(fragment,requestCode,orderId,ownerId,type);
    }

    public void scanQrCode(Context context){
        context.startActivity(new Intent(context, QrCodeScanActivity.class));
    }

    public void geToMarket(Context context) {
        String uri = "market://details?id=" + context.getPackageName();
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setData(Uri.parse(uri));
        context.startActivity(localIntent);
    }

    public void pay(Context context, String orderId, String price, String serviceProderName) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("TotalMoney",price);
        intent.putExtra(Constant.OrderId,orderId);
        intent.putExtra("ServiceProviderName",serviceProderName);
        context.startActivity(intent);
    }

    public void directpay(Context context,String receiverId, String receiverType, String serviceTypeId,String serviceProviderName) {
        DirectPayActivity.start(context,receiverId,receiverType,serviceTypeId,serviceProviderName);
    }

    public void myOrderList(Context context) {
        context.startActivity(new Intent(context,OrderManageActivity.class));
        ((CommonBaseActivity)context).finish();
    }

    /**
     * 查看个人服务地址
     * @param context
     */
    public void showAddress(Context context) {
        Intent intent=new Intent(context,AddressManageActivity.class);
        intent.putExtra(StaticData.MODE,MODE_BROW);
        intent.putExtra(StaticData.ADDRESS_POSITION, -1);
        context.startActivity(intent);
    }

    public void myAddressList(CommonBaseActivity context) {
        context.startActivityForResult(new Intent(context,AddressManageActivity.class),REQUEST_CODE_GET_MY_ADDRESS);

    }

    //投诉
    public void complaint(Context context,String orderID) {
        context.startActivity(new Intent(context, ComplaintActivity.class).putExtra(StaticData.ORDER_ID,orderID));
    }


    public void myInvitationCode(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    public void writeBackInvitation(Context context) {
        context.startActivity(new Intent(context,WriteBackInvitationActivity.class));
    }
}

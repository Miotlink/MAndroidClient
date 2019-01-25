package com.homepaas.sls.ui.location;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.homepaas.sls.R;
import com.homepaas.sls.ui.location.location.AddressModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by XIEJIALI on 2016/6/7.
 */
public class RegisterAddressRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    private LatLng latLng;
    private List<AddressModel> mapAddressDataList;
    private ReverseGeoCodeResult.AddressComponent addressComponent;
    //    private ArrayList<PoiInfo> list2;
//    private ArrayList<ReverseGeoCodeResult> list1;

    public RegisterAddressRecyclerviewAdapter(Context context, List<AddressModel> mapAddressDataList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mapAddressDataList = mapAddressDataList;
    }


    //建立枚举 2个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    /**
     * 第一条Item加载第一种布局，第二条Item加载第二种布局，
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE.ITEM1.ordinal() : ITEM_TYPE.ITEM2.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new Item1ViewHolder(inflater.inflate(R.layout.register_item_1, parent, false));
        } else {
            return new Item2ViewHolder(inflater.inflate(R.layout.register_item_2, parent, false));
        }
    }

    public interface OnGetAddressListener {
        void getAddress(AddressModel addressModel, ReverseGeoCodeResult.AddressComponent component);
    }

    private OnGetAddressListener onGetAddressListener;

    public void setOnGetAddressListener(OnGetAddressListener onGetAddressListener) {
        this.onGetAddressListener = onGetAddressListener;
    }


    /**
     * 刷新数据
     */

    public void setData(ReverseGeoCodeResult reverseGeoCodeResult, LatLng latLng) {
        this.latLng = latLng;
        mapAddressDataList.clear();
        String resultAddress = reverseGeoCodeResult.getAddress();
        addressComponent = reverseGeoCodeResult.getAddressDetail();
//        ReverseGeoCodeResult.AddressComponent component = addressComponent;
        String province = addressComponent.province;
        String city = addressComponent.city;
        String district = addressComponent.district;
        String street = addressComponent.street;
        String streetNum = addressComponent.streetNumber;
        //第一个添加当前用户定位的地址信息
        mapAddressDataList.add(new AddressModel(latLng, null, null, resultAddress, province, city, district, street, streetNum));
        List<PoiInfo> poiInfoList = reverseGeoCodeResult.getPoiList();
        if (poiInfoList != null) {
            for (PoiInfo poiInfo : poiInfoList) {
                mapAddressDataList.add(new AddressModel(poiInfo.location, poiInfo.name, poiInfo.address,null, null, null, null, null, null));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof Item1ViewHolder) {
            ((Item1ViewHolder) holder).location1.setText(mapAddressDataList.get(holder.getAdapterPosition()).resultAdress);
            ((Item1ViewHolder) holder).location2.setText(mapAddressDataList.get(holder.getAdapterPosition()).street + "附近");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    keepData(holder.getAdapterPosition());
                    onGetAddressListener.getAddress(mapAddressDataList.get(holder.getAdapterPosition()),addressComponent);
                }
            });
        } else if (holder instanceof Item2ViewHolder) {
            ((Item2ViewHolder) holder).location1.setText(mapAddressDataList.get(holder.getAdapterPosition()).poiName);
            ((Item2ViewHolder) holder).location2.setText(mapAddressDataList.get(holder.getAdapterPosition()).poiAddress);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    keepData(holder.getAdapterPosition());
                    onGetAddressListener.getAddress(mapAddressDataList.get(holder.getAdapterPosition()),addressComponent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mapAddressDataList.size();
    }

    /**
     * Item点击事件监听器
     */
    public interface OnItemClickListener {
        public void onClick(View parent, int position);
    }

    //item1 的ViewHolder
    public static class Item1ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.location1)
        TextView location1;
        @Bind(R.id.location2)
        TextView location2;


        public Item1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //item2 的ViewHolder
    public static class Item2ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.location1)
        TextView location1;
        @Bind(R.id.location2)
        TextView location2;

        public Item2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

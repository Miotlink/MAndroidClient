package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.ui.order.chooseservice.SelectServiceItemFragment;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewUsedRedPacketFragment;
import com.homepaas.sls.ui.widget.LineGridView;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.LinearLayout.LayoutParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2016/12/16.
 */

public class NewServiceTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_HEADER = 0;
    private static final int ITEM_LIST = 1;
    private static Context context;
    static Map<Integer, Integer> map = new HashMap();


    public NewServiceTypeAdapter(Context context) {
        this.context = context;

    }

    private List<ServiceTypeEx> serviceTypeExList = new ArrayList<>();

    private LayoutInflater mLayoutInflater;

    public void setServiceTypeExList(List<ServiceTypeEx> list) {
        this.serviceTypeExList.clear();
        //过滤到不符合条件的数据结构
        for (ServiceTypeEx serviceTypeEx : list) {
            if (serviceTypeEx.getChildren() != null &&
                    !serviceTypeEx.getChildren().isEmpty()) {
                this.serviceTypeExList.add(serviceTypeEx);
            }
        }
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view;
        if (viewType == ITEM_HEADER) {
            view = mLayoutInflater.inflate(R.layout.services_type_item_first, parent, false);
            ServiceTypeViewHolder holder = new ServiceTypeViewHolder(view);
            holder.setIsRecyclable(false);
            return holder;
        } else {
            view = mLayoutInflater.inflate(R.layout.service_type_item_second, parent, false);
            ServiceSubTypeViewHolder holder = new ServiceSubTypeViewHolder(view);
            holder.setIsRecyclable(false);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceTypeEx serviceTypeEx = serviceTypeExList.get(position);
        if (position == 0) {
            ((ServiceTypeViewHolder) holder).setModelFirst(serviceTypeEx);
        } else {
            ((ServiceSubTypeViewHolder) holder).setModelSecond(serviceTypeEx);
        }
    }


    @Override
    public int getItemCount() {
        return serviceTypeExList == null ? 0 : serviceTypeExList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEADER;
        } else {
            return ITEM_LIST;
        }
    }

    class ServiceTypeViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.service_content_gridView)
        LineGridView serviceContentGridView;

        //三级目录数据列表(整合后)
        List<ServiceTypeEx> serviceTypeExListFirst = new ArrayList<>();
        NewServiceTypeGridViewAdapter adapter;


        public ServiceTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            adapter = new NewServiceTypeGridViewAdapter(context);
            serviceContentGridView.setAdapter(adapter);
            serviceContentGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ServiceTypeEx serviceTypeEx = serviceTypeExListFirst.get(position);
                    if (serviceTypeEx != null) {
                        if (serviceTypeEx.getChildren() != null &&
                                !serviceTypeEx.getChildren().isEmpty()) {
                            SelectServiceItemFragment.show((FragmentActivity) parent.getContext(), serviceTypeEx);
                        } else {
                               EventBus.getDefault().post(serviceTypeEx);

                        }
                    }
                }
            });
        }

        public void setModelFirst(ServiceTypeEx serviceTypeEx) {
            serviceTypeExListFirst.clear();
            for (ServiceTypeEx serviceTypeExItem : serviceTypeEx.getChildren()) {
                if (serviceTypeExItem.getChildren() != null &&
                        !serviceTypeExItem.getChildren().isEmpty()) {
                    serviceTypeExListFirst.addAll(serviceTypeExItem.getChildren());
                } else {
                    serviceTypeExListFirst.add(serviceTypeExItem);
                }
            }
            if (serviceTypeExListFirst.size() > 6) {
                serviceTypeExListFirst.subList(0, 6);
            }
            adapter.setServiceTypeExList(serviceTypeExListFirst);
        }


    }

    class ServiceSubTypeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_second)
        ImageView imageSecond;
        @Bind(R.id.service_name_second)
        TextView serviceName;
        @Bind(R.id.view_pager)
        ViewPager viewPager;
        @Bind(R.id.service_background)
        LinearLayout linearLayout;
        @Bind(R.id.dots_lin)
        LinearLayout dotsLin;

        List<ServiceTypeEx> serviceTypeExListSecond = new ArrayList<>();
        private ViewPageAdapter viewPageAdapter;
        private ImageView[] dotViews;
        private int number_dot;
        private int remainder_dot;
        private int count_dot;

        public ServiceSubTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewPageAdapter = new ViewPageAdapter(context);
            viewPager.setAdapter(viewPageAdapter);
//            Iterator iter = map.entrySet().iterator();
//            while (iter.hasNext()) {
//                Map.Entry entry = (Map.Entry) iter.next();
//                int key = (int) entry.getKey();
//                int val = (int) entry.getValue();
//                if (position==key){
//                    viewPager.setCurrentItem(val);
//                }
//            }

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
//                    int recyclerPosition = getLayoutPosition();
//                    map.put(recyclerPosition,position);
                    for (int i = 0; i < dotViews.length; i++) {
                        if (position == i) {
                            dotViews[i].setSelected(true);
                        } else {
                            dotViews[i].setSelected(false);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

        public void setModelSecond(ServiceTypeEx root) {
            serviceTypeExListSecond.clear();
            serviceName.setText(root.getTypeName());
            int defaultImage = R.mipmap.service_hot;
            Glide.with(context).load(root.getIcon1()).placeholder(defaultImage)
                    .into(new ImageTarget(imageSecond));

            //整合所有三级服务到一个列表下,如果二级列表下没有三级数据,则添加自己作为三级显示
            for (ServiceTypeEx serviceTypeEx : root.getChildren()) {
                if (serviceTypeEx.getChildren() != null &&
                        !serviceTypeEx.getChildren().isEmpty()) {
                    serviceTypeExListSecond.addAll(serviceTypeEx.getChildren());
                } else {
                    serviceTypeExListSecond.add(serviceTypeEx);
                }
            }
            if (serviceTypeExListSecond != null) {
                number_dot = serviceTypeExListSecond.size() / 6;
                remainder_dot = serviceTypeExListSecond.size() % 6;
                count_dot = remainder_dot == 0 ? number_dot : number_dot + 1;
            }
            switch (root.getTypeId()) {
                case "168":
                    linearLayout.setBackgroundResource(R.color.service_install_color);
                    serviceName.setTextColor(Color.parseColor("#FD792A"));
                    addDots(R.drawable.install_dots);
                    break;
                case "169":
                    linearLayout.setBackgroundResource(R.color.service_convenience_color);
                    serviceName.setTextColor(Color.parseColor("#EE2C09"));
                    addDots(R.drawable.convenience_dots);
                    break;
                case "170":
                    linearLayout.setBackgroundResource(R.color.service_housekeep_color);
                    serviceName.setTextColor(Color.parseColor("#11BB7B"));
                    addDots(R.drawable.housekeeping_dots);
                    break;
                case "171":
                    linearLayout.setBackgroundResource(R.color.service_repair_color);
                    serviceName.setTextColor(Color.parseColor("#955AE8"));
                    addDots(R.drawable.repair_dots);
                    break;
                case "172":
                    linearLayout.setBackgroundResource(R.color.service_fix_color);
                    serviceName.setTextColor(Color.parseColor("#F82B4B"));
                    addDots(R.drawable.renovation_dots);
                    break;
                case "0":
                default:
                    linearLayout.setBackgroundResource(R.color.service_hot_color);
                    serviceName.setTextColor(Color.parseColor("#E94B14"));
                    break;
            }
            viewPageAdapter.setList(serviceTypeExListSecond);

        }

        private void addDots(int resId) {
            /**
             * 根据引导页的数量，动态生成相应数量的导航小圆点，并添加到LinearLayout中显示。
             */
            LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mParams.setMargins(10, 0, 10, 0);//设置小圆点左右之间的间隔
            dotViews = new ImageView[count_dot];
            for (int i = 0; i < count_dot; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(mParams);
                imageView.setImageResource(resId);
                if (i == 0) {
                    imageView.setSelected(true);//默认启动时，选中第一个小圆点
                } else {
                    imageView.setSelected(false);
                }
                dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
                dotsLin.addView(imageView);//添加到布局里面显示
            }

        }
    }

}

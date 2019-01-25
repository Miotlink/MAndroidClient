package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

/**
 * Created by mhy on 2017/12/25.
 */

public class MerchantDetailsFilterTagAdapter extends BaseRecyclerAdapter<BusinessSecServiceEntity.SecServicesBean> {


    public MerchantDetailsFilterTagAdapter(Context context) {
        super(context, null);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.merchant_detail_tag_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BusinessSecServiceEntity.SecServicesBean item) {
        TextView textView = holder.getTextView(R.id.tv_name);
        textView.setText(stringFilter(item.getName()));
        if (item.isSelect()) {
            textView.setTextColor(mContext.getResources().getColor(R.color.filter_item_select));
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.merchant_tag_select_bg));
        } else {
            textView.setTextColor(mContext.getResources().getColor(R.color.filter_item));
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.merchant_tag_bg));
        }
    }

    public String stringFilter(String string) {
        if (!TextUtils.isEmpty(string)) {
            if (string.length() > 6)
                return string.substring(0, 5) + "...";
            else return string;
        } else {
            return string;
        }
    }

}

package com.homepaas.sls.ui.location;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.homepaas.sls.R;
import com.homepaas.sls.ui.location.location.SuggestionAddressModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ServiceAddressSearchAdapter extends RecyclerView.Adapter<ServiceAddressSearchAdapter.SuggestionResultViewHolder> {


    private List<SuggestionAddressModel> suggestionAddressModelList;
    private LayoutInflater inflater;
    private Context context;

    public ServiceAddressSearchAdapter(List<SuggestionAddressModel> suggestionAddressModelList, Context context) {
        this.suggestionAddressModelList = suggestionAddressModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void clearData() {
        suggestionAddressModelList.clear();
    }

    public void setData(SuggestionResult suggestionResult) {
        suggestionAddressModelList.clear();
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            ((RegisterAddressMapActivity) context).showNoResult();
        } else {
            ((RegisterAddressMapActivity) context).showSearchResult();
            List<SuggestionResult.SuggestionInfo> suggestionInfoList = suggestionResult.getAllSuggestions();
            if (suggestionInfoList != null) {
                for (SuggestionResult.SuggestionInfo suggestionInfo : suggestionInfoList) {
                    if (!TextUtils.isEmpty(suggestionInfo.city) && !TextUtils.isEmpty(suggestionInfo.district) && !suggestionInfo.district.equals(suggestionInfo.key))
                        suggestionAddressModelList.add(new SuggestionAddressModel(suggestionInfo.city, suggestionInfo.district, suggestionInfo.key, suggestionInfo.pt));//, province,  detailsAddress
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public SuggestionResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuggestionResultViewHolder(inflater.inflate(R.layout.search_address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SuggestionResultViewHolder holder, int position) {
        final SuggestionAddressModel suggestionAddressModel = suggestionAddressModelList.get(holder.getAdapterPosition());
        holder.location1.setText(suggestionAddressModel.getKey());
        holder.location2.setText(suggestionAddressModel.getCity() + suggestionAddressModel.getDistrict());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSuggestionLaltgListener != null) {
                    onSuggestionLaltgListener.getLaltg(suggestionAddressModel);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return suggestionAddressModelList.size();
    }

    public static class SuggestionResultViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.location1)
        TextView location1;
        @Bind(R.id.location2)
        TextView location2;
        @Bind(R.id.item)
        LinearLayout item;

        public SuggestionResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnSuggestionLaltgListener onSuggestionLaltgListener;

    public void setOnSuggestionLaltgListener(OnSuggestionLaltgListener onSuggestionLaltgListener) {
        this.onSuggestionLaltgListener = onSuggestionLaltgListener;
    }

    public interface OnSuggestionLaltgListener {
        void getLaltg(SuggestionAddressModel suggestionAddressModel);
    }
}

package com.homepaas.sls.ui.personalcenter.address.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;

import java.util.List;

/**
 * on 2016/1/5 0005
 *
 * @author zhudongjie .
 */
public class MapAddressAdapter extends RecyclerView.Adapter<MapAddressAdapter.AddressViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    private static final int FIRST = 1;
    private static final int NORMAL = 2;

    private List<Object> mAddressList;

    private LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        switch (viewType) {
            case NORMAL:
            default:
                view = mInflater.inflate(R.layout.map_search_address_item, parent, false);
                break;
            case FIRST:
                view = mInflater.inflate(R.layout.map_search_address_first_item, parent, false);
                break;
        }

        final AddressViewHolder viewHolder = new AddressViewHolder(view);
        if (mOnItemClickListener!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(viewHolder.getAdapterPosition());
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        // TODO: 2016/1/5 0005
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? FIRST : NORMAL;
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView address;

        TextView detail;

        public AddressViewHolder(View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.address);
            detail = (TextView) itemView.findViewById(R.id.address_detail);
        }
    }
}

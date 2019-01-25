package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.AddressEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/9/13.
 */

public class AddressManageAdapter extends RecyclerView.Adapter<AddressManageAdapter.Holder> {

    List<AddressEntity> datas;
    LayoutInflater inflater;
    private int addressPosition = -1;

    public AddressManageAdapter(List<AddressEntity> datas) {
        this.datas = datas;
    }

    public void setAddressPosition(int addressPosition) {
        this.addressPosition = addressPosition;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        return new Holder(inflater.inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.setData(datas.get(position));
        if (position == addressPosition) {
            holder.selectItem.setVisibility(View.VISIBLE);
        } else {
            if (addressPosition == -1)
                holder.selectItem.setVisibility(View.GONE);
            else
                holder.selectItem.setVisibility(View.INVISIBLE);
        }
        holder.editPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editAddressListener != null)
                    editAddressListener.onEdit(holder.getAdapterPosition(), datas.get(holder.getAdapterPosition()));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostAction.onItemSelected(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hostAction.startDeleteAddress(position);
                return true;
            }
        });

    }

    public void setDatas(List<AddressEntity> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public List<AddressEntity> getDatas() {
        return datas;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    private EditAddressListener editAddressListener;

    public void setEditAddressListener(EditAddressListener editAddressListener) {
        this.editAddressListener = editAddressListener;
    }


    public void addOneAddress(AddressEntity entity) {
        datas.add(entity);
        notifyItemInserted(datas.size() - 1);
    }

    public void modifyItem(int index, AddressEntity entity) {
        if (datas.size() > index) {
            datas.set(index, entity);
            notifyItemChanged(index);
        }
    }


    public interface EditAddressListener {

        void onEdit(int index, AddressEntity address);

        void startAddressDelete(int index, String id);
    }

    class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.select_item)
        FrameLayout selectItem;

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.client_gender)
        TextView clientGender;

        @Bind(R.id.client_phone)
        TextView clientPhone;

        @Bind(R.id.address_type)
        TextView addressType;

        @Bind(R.id.address)
        TextView address;

        @Bind(R.id.edit_pen)
        FrameLayout editPen;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(AddressEntity entity) {
            name.setText(entity.getContact());
            if ("0".equals(entity.getGender())) {
                clientGender.setText("(先生)");
            } else {
                clientGender.setText("(女士)");
            }
            clientPhone.setText(entity.getPhoneNumber());
            if (entity.getTag() == null || entity.getTag().isEmpty() || TextUtils.equals("无", entity.getTag())) {
                addressType.setVisibility(View.INVISIBLE);
            } else {
                addressType.setText(entity.getTag());
                addressType.setVisibility(View.VISIBLE);
            }
            address.setText(String.format("%s%s", entity.getAddress(), entity.getDetailAddress()));
        }
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }

    public interface HostAction {
        void onItemSelected(int pos);

        void startDeleteAddress(int pos);
    }
}

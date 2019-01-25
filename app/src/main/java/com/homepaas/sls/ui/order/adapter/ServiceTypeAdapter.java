package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ServiceTypeAdapter extends BaseExpandableListAdapter {
    List<String> groupDatas;
    List<List<String>> datas;
    List<List<Boolean>> checkedList;
    LayoutInflater inflater;
    Context context;

    public ServiceTypeAdapter(List<List<String>> datas, List<String> groupDatas, List<List<Boolean>> checkedList, Context context) {
        this.datas = datas;
        this.groupDatas = groupDatas;
        this.checkedList = checkedList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datas.get(groupPosition) == null ? 0 : datas.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datas.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pay_type_group_layout, parent, false);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.groupName.setText(groupDatas.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_service_type_child_layout, parent, false);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.childName.setText(datas.get(groupPosition).get(childPosition));
        holder.checkBox.setVisibility(checkedList.get(groupPosition).get(childPosition) ? View.VISIBLE : View.INVISIBLE);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHolder {
        @Bind(R.id.group_name)
        TextView groupName;

        public GroupHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    class ChildHolder {
        @Bind(R.id.child_name)
        TextView childName;

        @Bind(R.id.cb)
        TextView checkBox;

        public ChildHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}

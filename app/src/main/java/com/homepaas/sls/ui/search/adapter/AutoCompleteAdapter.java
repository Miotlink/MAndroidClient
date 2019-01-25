package com.homepaas.sls.ui.search.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.common.PinyinUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * on 2016/3/23 0023
 *
 * @author zhudongjie .
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable {

    //Key文字，value拼音
    private Map<CharSequence, String> originalValues;

    private List<? extends CharSequence> showValues;

    private LayoutInflater inflater;

    private Filter filter;

    public AutoCompleteAdapter(List<String> list) {

        originalValues = new LinkedHashMap<>(list.size());
        for (String s : list) {
            originalValues.put(s, PinyinUtils.toPinyinPhrase(s));
        }
        showValues = list;
    }


    @Override
    public int getCount() {
        return showValues.size();
    }

    @Override
    public Object getItem(int position) {
        return showValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (inflater == null) {
                inflater = LayoutInflater.from(parent.getContext());
            }
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            ((TextView) convertView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        }
        ((TextView) convertView).setText(showValues.get(position));
        return convertView;
    }


    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new AdaptPinyinFilter();
        }
        return filter;
    }

    private class AdaptPinyinFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CharSequence> list;
            if (TextUtils.isEmpty(constraint)) {
                list = new ArrayList<>(originalValues.keySet());
            } else {
                String pinyin = PinyinUtils.toPinyinPhrase(constraint.toString());
                list = new ArrayList<>();
                for (Map.Entry<CharSequence, String> entry : originalValues.entrySet()) {
                    if (entry.getValue().toLowerCase().contains(pinyin.toLowerCase())) {
                        String key = entry.getKey().toString();
                        if (key.contains(constraint)) {
                            SpannableString ss = new SpannableString(key);
                            int start = key.indexOf(constraint.toString());
                            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#32BEFF")), start, start + constraint.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            list.add(ss);
                        } else {
                            list.add(entry.getKey());
                        }
                    }
                }
            }
            if (list.isEmpty()) {
                list.add("没有相应服务，请换个关键词试试");
            }
            results.count = list.size();
            results.values = list;
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            showValues = (List<CharSequence>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}

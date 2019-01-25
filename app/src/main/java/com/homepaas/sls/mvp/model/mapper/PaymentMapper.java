package com.homepaas.sls.mvp.model.mapper;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.PayDetail;
import com.homepaas.sls.mvp.model.NormalPayItem;
import com.homepaas.sls.mvp.model.PayItem;
import com.homepaas.sls.mvp.model.TimePayItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@Singleton
public class PaymentMapper {



    @Inject
    public PaymentMapper() {
    }

    @NonNull
    public List<PayItem> transform(@NonNull List<PayDetail> payDetails){
        List<PayItem> payItems = new ArrayList<>();
        Collections.sort(payDetails, new Comparator<PayDetail>() {
            @Override
            public int compare(PayDetail lhs, PayDetail rhs) {
                return -lhs.getTime().compareTo(rhs.getTime());
            }
        });
        Map<String,List<NormalPayItem>> map = new LinkedHashMap<>();
        for (int i = 0; i < payDetails.size(); i++) {
            PayDetail detail = payDetails.get(i);
            String month = TimeUtils.formatMonth(detail.getTime());
            if (map.containsKey(month)){
                map.get(month).add(new NormalPayItem(detail));
            }else {
                List<NormalPayItem> list = new ArrayList<>();
                list.add(new NormalPayItem(detail));
                map.put(month,list);
            }
        }

        for (Map.Entry<String, List<NormalPayItem>> entry : map.entrySet()) {
            payItems.add(new TimePayItem(entry.getKey()));
            payItems.addAll(entry.getValue());
        }

        return payItems;
    }




}

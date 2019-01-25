package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.ui.order.chooseservice.SelectServiceItemFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by JWC on 2016/12/19.
 */

public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private List<ServiceTypeEx> list;
    private int number;
    private int remainder;
    private int count;


    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    public void setList( List<ServiceTypeEx> list){
        this.list=list;
        notifyDataSetChanged();
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.service_type_viewpage_item, null);
        TextView text_one = (TextView)view.findViewById(R.id.text_one);
        TextView text_two = (TextView)view.findViewById(R.id.text_two);
        TextView text_three = (TextView)view.findViewById(R.id.text_three);
        TextView text_four = (TextView)view.findViewById(R.id.text_four);
        TextView text_five = (TextView)view.findViewById(R.id.text_five);
        TextView text_six = (TextView)view.findViewById(R.id.text_six);
        if(remainder==0){
            text_one.setText(list.get(0 + position * 6).getTypeName());
            text_two.setText(list.get(1 + position * 6).getTypeName());
            text_three.setText(list.get(2 + position * 6).getTypeName());
            text_four.setText(list.get(3 + position * 6).getTypeName());
            text_five.setText(list.get(4 + position * 6).getTypeName());
            text_six.setText(list.get(5 + position * 6).getTypeName());
        }else {
            if (position < count - 1) {
                text_one.setText(list.get(0 + position * 6).getTypeName());
                text_two.setText(list.get(1 + position * 6).getTypeName());
                text_three.setText(list.get(2 + position * 6).getTypeName());
                text_four.setText(list.get(3 + position * 6).getTypeName());
                text_five.setText(list.get(4 + position * 6).getTypeName());
                text_six.setText(list.get(5 + position * 6).getTypeName());
            } else if (position == count-1) {
                text_one.setText(0 < remainder ? list.get(0 + position * 6).getTypeName() : "");
                text_two.setText(1 < remainder ? list.get(1 + position * 6).getTypeName() : "");
                text_three.setText(2 < remainder ? list.get(2 + position * 6).getTypeName() : "");
                text_four.setText(3 < remainder ? list.get(3 + position * 6) .getTypeName(): "");
                text_five.setText(4 < remainder ? list.get(4 + position * 6).getTypeName() : "");
                text_six.setText(5 < remainder ? list.get(5 + position * 6).getTypeName() : "");
            }
        }
        setOnclick(text_one,0 + position * 6);
        setOnclick(text_two,1 + position * 6);
        setOnclick(text_three,2 + position * 6);
        setOnclick(text_four,3+ position * 6);
        setOnclick(text_five,4 + position * 6);
        setOnclick(text_six,5 + position * 6);

        container.addView(view);
        return view;
    }

    private void setOnclick(View v, final int position){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<list.size()) {
                    ServiceTypeEx serviceTypeEx = list.get(position);
                    if (serviceTypeEx != null) {
                        if (serviceTypeEx.getChildren() != null &&
                                !serviceTypeEx.getChildren().isEmpty()) {
                            SelectServiceItemFragment.show((FragmentActivity) context, serviceTypeEx);
                        } else {
                            EventBus.getDefault().post(serviceTypeEx);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }else {
            number = list.size() / 6;
            remainder = list.size() % 6;
            count = remainder == 0 ? number : number + 1;
            return count;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}

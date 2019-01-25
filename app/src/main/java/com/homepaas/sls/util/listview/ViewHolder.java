package com.homepaas.sls.util.listview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder {
	
	private SparseArray<View> mViews;
	private View mConvertView;
	private int position;
	
	public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		mViews = new SparseArray<>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.position = position;
		mConvertView.setTag(this);
	}
	
	public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.position = position;
			return holder;
		}
	}
	
	/**
	 * 根据viewId获取控件
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	
	public View getConvertView() {
		return mConvertView;
	}
	
	public void setText(int id, String s) {
		TextView tv = getView(id);
		tv.setText(s);
	}
	
	public void setTextColor(int id, int color) {
		TextView tv = getView(id);
		tv.setTextColor(color);
	}
	
	public void setImageResource(int viewId, int rId) {
		ImageView iv = getView(viewId);
		iv.setImageResource(rId);
	}
	
	public void setVisibility(int id, int visibility) {
		getView(id).setVisibility(visibility);
	}

	public int getPosition() {
		return position;
	}

	public void append(int id, CharSequence charSequence){
		TextView tv = getView(id);
		tv.append(charSequence);
	}
}

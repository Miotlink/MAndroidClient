package com.homepaas.sls.ui.adapter;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.interactor.UpdateServiceAddressInteractor;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * on 2015/12/29 0029
 *
 * @author zhudongjie .
 */
public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {


    public interface MenuItem {

        void setOnClickListener(OnClickListener listener);

    }

    public static class Header implements MenuItem {

        private String mPhoto;

        private int mDefaultPhoto;

        private String mNickname;

        private String mPhone;

        OnClickListener mClickListener;

        OnClickListener mQrCodeClickListener;

        public Header(String photo,@DrawableRes int defaultRes, String nickname, String phone) {
            mPhoto = photo;
            mNickname = nickname;
            mDefaultPhoto = defaultRes;
            mPhone = phone;
        }

        public void setPhoto(String photo,@DrawableRes int defaultRes) {
            mPhoto = photo;
            mDefaultPhoto = defaultRes;
        }

        public void setNickname(String nickname) {
            mNickname = nickname;
        }

        public void setPhone(String phone) {
            mPhone = phone;
        }

        public void setQrCodeClickListener(OnClickListener listener){
            mQrCodeClickListener = listener;
        }

        @Override
        public void setOnClickListener(OnClickListener listener) {
            mClickListener = listener;
        }
    }

    public static class NormalView implements MenuItem {

        private int mIconRes;

        private String mTitle;

        OnClickListener mClickListener;

        public NormalView(@DrawableRes int iconRes, String title) {
            mIconRes = iconRes;
            mTitle = title;
        }

        @Override
        public void setOnClickListener(OnClickListener listener) {
            mClickListener = listener;
        }
    }

    public static class NoticeView implements MenuItem {

        private int mIconRes;

        private String mTitle;

        private int mNoticeCount;

        OnClickListener mClickListener;

        public NoticeView(@DrawableRes int iconRes, String title, int noticeCount) {
            mIconRes = iconRes;
            mTitle = title;
            mNoticeCount = noticeCount;
        }

        public void setNoticeCount(int noticeCount) {
            mNoticeCount = noticeCount;
        }

        @Override
        public void setOnClickListener(OnClickListener listener) {
            mClickListener = listener;
        }
    }

    public static class DividerView implements MenuItem{
        @Override
        public void setOnClickListener(OnClickListener listener) {

        }
    }

    public static final int TYPE_HEADER = 0;

    public static final int TYPE_NORMAL = 1;

    public static final int TYPE_NOTICE = 2;

    public static final int TYPE_DIVIDER = 3;

    private List<? extends MenuItem> mMenuItems;

    private LayoutInflater mInflater;


    public MenuItemAdapter(List<? extends MenuItem> menuItems) {
        mMenuItems = menuItems;
    }

    public void refreshItem(List<? extends MenuItem> menuItems_new){
        mMenuItems = menuItems_new;
        notifyDataSetChanged();
    }


    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        final MenuItemViewHolder viewHolder;
        if (viewType == TYPE_NORMAL) {
            view = mInflater.inflate(R.layout.drawer_left_item, parent, false);
            viewHolder = new NormalViewHolder(view);
        } else if (viewType == TYPE_NOTICE) {
            view = mInflater.inflate(R.layout.drawer_left_item_notice, parent, false);
            viewHolder = new NoticeViewHolder(view);
        } else if (viewType == TYPE_HEADER){
            view = mInflater.inflate(R.layout.nav_header_main, parent, false);
            viewHolder = new HeaderViewHolder(view);
        }else{
            view = mInflater.inflate(R.layout.drawer_left_divider,parent,false);
            viewHolder = new DividerViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        MenuItem menuItem = mMenuItems.get(position);
        holder.bindData(menuItem);
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        MenuItem menuItem = mMenuItems.get(position);
        if (menuItem instanceof NormalView) {
            return TYPE_NORMAL;
        } else if (menuItem instanceof NoticeView) {
            return TYPE_NOTICE;
        } else if (menuItem instanceof Header) {
            return TYPE_HEADER;
        }else if (menuItem instanceof DividerView){
            return TYPE_DIVIDER;
        }
        throw new RuntimeException("Unknown item type.");
    }


    static abstract class MenuItemViewHolder<T extends MenuItem> extends RecyclerView.ViewHolder {
        public MenuItemViewHolder(View itemView) {
            super(itemView);
        }
        public abstract void bindData(T data);
    }

    static class HeaderViewHolder extends MenuItemViewHolder<Header> {

        @Bind(R.id.head_photo)
        ImageView photo;

        @Bind(R.id.head_nick_name)
        TextView nickName;

        @Bind(R.id.head_phone)
        TextView phone;

        @Bind(R.id.qr_code)
        ImageView qrCode;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(Header data) {
            Glide.with(phone.getContext()).load(data.mPhoto)
                    .placeholder(data.mDefaultPhoto)
                    .into(new ImageTarget(photo));
            nickName.setText(data.mNickname);
            phone.setText(data.mPhone);
            itemView.setOnClickListener(data.mClickListener);
            qrCode.setOnClickListener(data.mQrCodeClickListener);
        }
    }

    static class NormalViewHolder extends MenuItemViewHolder<NormalView> {

        TextView title;

        public NormalViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        public void bindData(NormalView data) {
            title.setText(data.mTitle);
            title.setCompoundDrawablesWithIntrinsicBounds(data.mIconRes, 0, 0, 0);
            itemView.setOnClickListener(data.mClickListener);
        }
    }

    static class NoticeViewHolder extends MenuItemViewHolder<NoticeView> {

        @Bind(R.id.notice_count)
        TextView count;

        @Bind(R.id.title)
        TextView title;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(NoticeView data) {
            title.setText(data.mTitle);
            title.setCompoundDrawablesWithIntrinsicBounds(data.mIconRes, 0, 0, 0);
            if (data.mNoticeCount == 0) {
                count.setVisibility(View.GONE);
            } else {
                count.setVisibility(View.VISIBLE);
                count.setText(String.valueOf(data.mNoticeCount));
            }
            itemView.setOnClickListener(data.mClickListener);
        }
    }

    public static class DividerViewHolder extends MenuItemViewHolder<DividerView>{

        public DividerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(DividerView data) {
            //do nothing
        }
    }
}

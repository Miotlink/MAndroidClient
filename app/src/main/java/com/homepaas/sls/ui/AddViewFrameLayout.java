package com.homepaas.sls.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.CheckType;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.adapter.WorkerBussinesRecyclerviewAdapter;
import com.homepaas.sls.ui.widget.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/29.
 */
public class AddViewFrameLayout extends FrameLayout implements WorkerBussinesRecyclerviewAdapter.OnItemClickListener {
    private static final String TAG = "AddViewFrameLayout";
    @Bind(R.id.worker_bussines_list)
    RecyclerView workerBussinesList;
    @Bind(R.id.check)
    ImageView check;

    @Inject
    Navigator mNavigator;
    @Bind(R.id.check_rl)
    PercentRelativeLayout checkRl;
    @Bind(R.id.noDataImage)
    ImageView noDataImage;
    @Bind(R.id.noDatabg)
    FrameLayout noDatabg;
    @Bind(R.id.list_fl)
    FrameLayout listFl;
//
//    @Inject
//    ShortDetailPresenter mPresenter;

    private LinearLayoutManager layoutManager;
    private WorkerBussinesRecyclerviewAdapter workerBussinesRecyclerviewAdapter;
    private boolean move = false;
    private int mIndex = 0;
    private boolean isOpen = false;
    private PercentRelativeLayout addview;
    private FrameLayout newview;
    private PercentRelativeLayout.LayoutParams layoutParams;
    private LinearLayout payorder, phonecall, detial_ll, showDetial;
    private TextView name, sex, nativeplace, exprience, distance, like, zan, serviceList, detail, activityIcon;
    private FrameLayout shadow;
    private int newviewWith = 0;

    private float downx = 0;
    private float downy = 0;
    private float upx = 0;
    private float upy = 0;
    private boolean flag;
    private int lastposition = -1;
    private boolean scrollAfterAdd = false;

    private WorkerBussinesModel bindWorkerBusinessModel;


    public AddViewFrameLayout(Context context) {
        super(context);
    }

    public AddViewFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddViewFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isScrollAfterAdd() {
        return scrollAfterAdd;
    }

    public void setScrollAfterAdd(boolean scrollAfterAdd) {
        this.scrollAfterAdd = scrollAfterAdd;
    }

    @Override
    public void focusableViewAvailable(View v) {
        super.focusableViewAvailable(v);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.reycyclerviewbutton, null);
        ButterKnife.bind(this, view);
        addView(view);
        addview = (PercentRelativeLayout) getParent();
        initRecyclerView();

    }

    public void initRecyclerView() {

        List<IService> workerBussinesModels = new ArrayList<>();
        workerBussinesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        workerBussinesList.setLayoutManager(layoutManager);
        workerBussinesList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        workerBussinesRecyclerviewAdapter = new WorkerBussinesRecyclerviewAdapter(getContext());
        workerBussinesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && isScrollAfterAdd()) {
                    setScrollAfterAdd(false);
//                    dimiss();
                    if (newview != null) {
                        addview.removeView(newview);
                        flashHighlightItem();
                        newview = null;
                    }
                }

                if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    move = false;
//                    int n = mIndex - layoutManager.findFirstVisibleItemPosition();
//                    if (0 <= n && n < workerBussinesList.getChildCount()) ;
//                    int top = workerBussinesList.getChildAt(n % 5).getTop();
//                    workerBussinesList.smoothScrollBy(0, top);
                    workerBussinesList.smoothScrollToPosition(mIndex);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }


        });

        workerBussinesList.setAdapter(workerBussinesRecyclerviewAdapter);
//        workerBussinesList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        workerBussinesRecyclerviewAdapter.setOnItemClickListener(this);
    }

    private void createview(View itemview) {
        if (addview == null)
            addview = (PercentRelativeLayout) getParent();
        if (newview != null) {
            addview.removeView(newview);
            newview = null;
        }
        setScrollAfterAdd(true);
        int windowWidth = addview.getWidth();
        int listWidth = workerBussinesList.getWidth();
        LayoutInflater inflater = LayoutInflater.from(getContext());
//        int height = layoutManager.getChildAt(position).getHeight();
//        int top = layoutManager.getChildAt(position).getTop();
//        int bottom = layoutManager.getChildAt(position).getBottom();
        int height = itemview.getHeight();
        int top = itemview.getTop();

        //获取控件对象
        newview = (FrameLayout) inflater.inflate(R.layout.add_view, null).findViewById(R.id.add_new_view);
        payorder = (LinearLayout) newview.findViewById(R.id.pay_order);
        phonecall = (LinearLayout) newview.findViewById(R.id.call_phone);
        name = (TextView) newview.findViewById(R.id.name);
        sex = (TextView) newview.findViewById(R.id.sex);
        nativeplace = (TextView) newview.findViewById(R.id.native_place);
        exprience = (TextView) newview.findViewById(R.id.experience);
        distance = (TextView) newview.findViewById(R.id.distance);
        like = (TextView) newview.findViewById(R.id.like);
        zan = (TextView) newview.findViewById(R.id.zan);
        serviceList = (TextView) newview.findViewById(R.id.service_list);
        detail = (TextView) newview.findViewById(R.id.detial);
        detial_ll = (LinearLayout) newview.findViewById(R.id.detail_ll);
        shadow = (FrameLayout) newview.findViewById(R.id.shadow);
        showDetial = (LinearLayout) newview.findViewById(R.id.showDetial);
        activityIcon = (TextView) newview.findViewById(R.id.activity_icon);

        newviewWith = windowWidth - listWidth;
        layoutParams = new PercentRelativeLayout.LayoutParams(0, height);
//        layoutParams.height = itemview.getHeight();
        layoutParams.topMargin = top;
        layoutParams.leftMargin = listWidth;
    }


    public interface OnLinkListener {
        void link(WorkerBussinesModel workerBussinesModel, int linkCode);//linkcode:0-下单，1-打电话，2-跳转到详情页面
    }

    private OnLinkListener onLinkListener;

    public void setOnLinkListener(OnLinkListener onLinkListener) {
        this.onLinkListener = onLinkListener;
    }

    public interface OnShowDetialListener {
        void show(int position, WorkerBussinesModel workerBussinesModel);
    }

    private OnShowDetialListener onShowDetialListener;

    public void setOnShowDetialListener(OnShowDetialListener onShowDetialListener) {
        this.onShowDetialListener = onShowDetialListener;
    }


    //    模拟数据
    public void bindView(WorkerBussinesModel workerBussinesModel) {
        if (workerBussinesModel != null) {
            bindWorkerBusinessModel = workerBussinesModel;
            shadow.setVisibility(VISIBLE);
            name.setText(workerBussinesModel.getName());
            if (workerBussinesModel.getType() == Constant.SERVICE_TYPE_WORKER_INT) {
                sex.setVisibility(VISIBLE);
                exprience.setVisibility(VISIBLE);
                sex.setText(workerBussinesModel.getAge());
                Drawable man = ContextCompat.getDrawable(getContext(), R.mipmap.man);
                man.setBounds(0, 0, man.getMinimumWidth(), man.getMinimumHeight());
                Drawable woman = ContextCompat.getDrawable(getContext(), R.mipmap.woman);
                woman.setBounds(0, 0, woman.getMinimumWidth(), woman.getMinimumHeight());
                if (workerBussinesModel.getGender().equals("0")) {
                    Log.d("sex", "bindView: " + workerBussinesModel.getName() + workerBussinesModel.getGender());
                    sex.setCompoundDrawables(man, null, null, null);
                } else {
                    Log.d("sex", "bindView: " + workerBussinesModel.getName() + workerBussinesModel.getGender());
                    sex.setCompoundDrawables(woman, null, null, null);
                }
                exprience.setText("从业" + workerBussinesModel.getDisplayAttribute() + "年");
                payorder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.button));

            } else {
                sex.setVisibility(GONE);
                exprience.setVisibility(GONE);
                payorder.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.textHint));
            }
            nativeplace.setText(workerBussinesModel.getNativePlace());
            distance.setText(workerBussinesModel.getDistance());
            like.setText(workerBussinesModel.getFavoriteCount());
            zan.setText(workerBussinesModel.getPraiseCount());
            String services = "";
            for (String service : workerBussinesModel.getServices()) {
                services += service + "  ";
            }
            serviceList.setText(services);
            if (workerBussinesModel.getActionEntity() != null) {
                activityIcon.setVisibility(VISIBLE);
                activityIcon.setText(workerBussinesModel.getActionEntity().getSlogan());
            } else {
                activityIcon.setVisibility(GONE);
            }
            payorder.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bindWorkerBusinessModel.getType() == Constant.SERVICE_TYPE_WORKER_INT) {
                        dimiss();
                        if (onLinkListener != null) {
                            onLinkListener.link(bindWorkerBusinessModel, 0);
                        }
                    }
                }
            });
            phonecall.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLinkListener != null) {
                        onLinkListener.link(bindWorkerBusinessModel, 1);
                    }
                }
            });
            showDetial.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dimiss();
                    if (onLinkListener != null) {
                        onLinkListener.link(bindWorkerBusinessModel, 2);
                    }

                }
            });
            addview.addView(newview, layoutParams);
            int type = Animation.RELATIVE_TO_SELF;
//            float fromX, float toX, float fromY, float toY,
//            int pivotXType, float pivotXValue, int pivotYType, float pivotYValue
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, type, 0, type, newview.getPivotY());
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(100);
            scaleAnimation.setInterpolator(new Interpolator() {

                @Override
                public float getInterpolation(float input) {
                    return input * input * input * input * input * input;
                }
            });
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    shadow.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            newview.startAnimation(scaleAnimation);
            layoutParams.width = newviewWith;
            newview.setLayoutParams(layoutParams);
        }


    }

    public interface OnItemSelectListener {
        void select(int index);
    }

    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    @Override
    public void ItemClick(View itemview, int position, WorkerBussinesModel workerBussinesModel) {

//        if (newview != null){
//            dimiss();
//        } else {
        if (newview != null && lastposition == position) {
            dimiss();
            return;
        } else {
            createview(itemview);
            if (onShowDetialListener != null) {
                onShowDetialListener.show(position, workerBussinesModel);
            }
        }
        lastposition = position;
//        }
        onItemSelectListener.select(position);

    }


    private void smoothMoveToPosition(int n) {
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            workerBussinesList.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = workerBussinesList.getChildAt(n - firstItem).getTop();
            workerBussinesList.smoothScrollBy(0, top);
        } else {
            workerBussinesList.smoothScrollToPosition(n);
            move = true;
        }

    }

    public void restHighLight() {
        workerBussinesRecyclerviewAdapter.highLightItem(-1, false);
    }

    public void flashHighlightItem() {
        workerBussinesRecyclerviewAdapter.highLightItem(lastposition, false);
    }

    public void dimiss() {
        if (newview != null) {
            int type = Animation.RELATIVE_TO_SELF;
//            float fromX, float toX, float fromY, float toY,
//            int pivotXType, float pivotXValue, int pivotYType, float pivotYValue
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, type, 0, type, newview.getPivotY());
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(100);
            scaleAnimation.setInterpolator(new Interpolator() {

                @Override
                public float getInterpolation(float input) {
                    return input * input * input * input * input * input;
                }
            });
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    shadow.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            newview.startAnimation(scaleAnimation);
            addview.removeView(newview);
            flashHighlightItem();
            newview = null;
        }
    }

    public void openList(boolean open) {
        isOpen = open;
        listFl.setVisibility(VISIBLE);
        changeBannerState(isOpen);
        check.setSelected(isOpen);

    }

    public void move(final int position, boolean firstMove) {
        mIndex = position;
        lastposition = position;
        dimiss();
        workerBussinesRecyclerviewAdapter.highLightItem(position, false);
        if (firstMove) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    workerBussinesList.smoothScrollToPosition(mIndex);
//                    smoothMoveToPosition(mIndex);
                    move = true;

                }
            }, 5);
        } else {
            workerBussinesList.smoothScrollToPosition(mIndex);
//            smoothMoveToPosition(mIndex);
            move = true;
        }

    }

    private void changeBannerState(boolean isOpen) {
        if (onBannerStateListener != null) {
            onBannerStateListener.setBanner(isOpen);
        }
    }
//    @OnClick(R.id.check)
//    public void checkList() {
//        if (isOpen) {
//            changeBannerState(isOpen);
//            isOpen = false;
//            dimiss();
////            workerBussinesList.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_out));
//            workerBussinesList.setVisibility(View.GONE);
//            isOpen =false;
//        } else {
//            changeBannerState(isOpen);
//            isOpen = true;
////            workerBussinesList.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_in));
//            workerBussinesList.setVisibility(View.VISIBLE);
//            isOpen =false;
//        }
//    }

    public void setData(List<IService> list, String type) {
        if (list == null || list.isEmpty()) {
            noDatabg.setVisibility(VISIBLE);
            if (type.equals(Constant.SERVICE_TYPE_ALL )|| type.equals(Constant.SERVICE_TYPE_WORKER)){
                noDataImage.setImageResource(R.mipmap.no_worker);
            }
            if (type.equals(Constant.SERVICE_TYPE_BUSINESS )){
                noDataImage.setImageResource(R.mipmap.no_business);
            }
        } else {
            noDatabg.setVisibility(GONE);
            workerBussinesRecyclerviewAdapter.setData(list);
        }

    }



    public interface OnBannerStateListener {
        void setBanner(boolean isopen);
    }

    private OnBannerStateListener onBannerStateListener;

    public void setOnBannerStateListener(OnBannerStateListener onBannerStateListener) {
        this.onBannerStateListener = onBannerStateListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                if (!isOpen && event.getX() < 60) {
                    return true;
                }
                if (isOpen && event.getX() > workerBussinesList.getWidth() && event.getX() < (workerBussinesList.getWidth() + checkRl.getWidth() * 2)) {

                    return true;

                }
            case MotionEvent.ACTION_MOVE:
                float offsetx = 0;
                if (!isOpen) {//打开
                    Log.i(TAG, "dispatchTouchEvent: offsetXXX" + offsetx);
                    upx = event.getX();
                    upy = event.getY();
                    offsetx = upx - downx;
                    double tanValue = Math.abs(upy - downy) / Math.abs(upx - downx);

                    Log.i(TAG, "dispatchTouchEvent: moveXX downX  Delta" + upx + "----" + downx + "----" + (upx - downx));
                    if (tanValue < 1) {
                        if (offsetx > 150) {
                            isOpen = true;
                            listFl.setVisibility(VISIBLE);
                            changeBannerState(isOpen);
                            check.setSelected(isOpen);
                            return true;
                        }
                    } else return false;

                } else {//关闭
                    upx = event.getX();
                    upy = event.getY();
                    offsetx = upx - downx;
                    double tanValue = Math.abs(upy - downy) / Math.abs(upx - downx);
                    if (tanValue < 1 && offsetx < -150) {
                        dimiss();
                        isOpen = false;
//                        workerBussinesRecyclerviewAdapter.highLightItem(-1,false);
                        listFl.setVisibility(GONE);
                        changeBannerState(isOpen);
                        check.setSelected(isOpen);
                        flag = true;
                        return flag;
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                float releaseX = event.getX();
                float releaseY = event.getY();
                if (releaseX > checkRl.getLeft() && releaseX < checkRl.getLeft() + checkRl.getWidth() && releaseY > checkRl.getTop() && releaseY < checkRl.getTop() + checkRl.getHeight()) {
                    if (!isOpen & !flag)//打开
                    {
                        isOpen = true;
                        listFl.setVisibility(VISIBLE);
                        changeBannerState(isOpen);
                        check.setSelected(isOpen);
                    } else {//关闭
                        dimiss();
                        isOpen = false;
//                        workerBussinesRecyclerviewAdapter.highLightItem(-1,false);
                        listFl.setVisibility(GONE);
                        changeBannerState(isOpen);
                        check.setSelected(isOpen);
                    }

                }
                double tanValue = Math.abs(releaseY - downy) / Math.abs(releaseX - downx);
                if (tanValue < 1 && releaseX < workerBussinesList.getWidth())
                    return true;
                flag = false;
                break;
        }
        if (!flag)
            return super.dispatchTouchEvent(event);

        return flag;
    }
}

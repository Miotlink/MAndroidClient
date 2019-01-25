package com.homepaas.sls.ui.order.adapter;

import android.support.annotation.IntDef;
import android.util.SparseIntArray;

/**
 * Created by CJJ on 2016/11/1.
 * 处理不同情况下RecyclerView item的类型问题
 * 提供 position到 viewType映射以及viewType到position映射
 *
 * p.s.随着界面剧烈猛烈而激烈的变动，这些都没什么yong
 */

public class Converter {

    //invalid item
    public static final int BLOCK_NULL = -1;
    @Deprecated
    //地址
    public static final int BLOCK_ADDRESS = 0;
    //工人信息
    public static final int BLOCK_WORKERINFO = 1;
    //区间价格信息
    public static final int BLOCK_RANGE_INFO = 2;
    //服务类型
    public static final int BLOCK_SERVTYPE = 3;
    //服务价格
    public static final int BLOCK_PRICE = 4;
    @Deprecated
    //服务数量
    public static final int BLOCK_SERVNUMBER = 5;
    @Deprecated
    //服务时间
    public static final int BLOCK_SERVTIME = 6;
    //活动
    public static final int BLOCK_ACTIVITY = 7;
    //备注
    public static final int BLOCK_NOTE = 8;
    @Deprecated
    //照片
    public static final int BLOCK_PHOTO = 9;
    //服务说明
    public static final int BLOCK_DESCRIPTION = 10;

    public static final int BLOCK_FAVOR = 11;

    //

    @IntDef({BLOCK_NULL, BLOCK_ADDRESS, BLOCK_WORKERINFO, BLOCK_RANGE_INFO, BLOCK_SERVTYPE, BLOCK_PRICE, BLOCK_SERVNUMBER, BLOCK_SERVTIME, BLOCK_ACTIVITY, BLOCK_NOTE, BLOCK_PHOTO, BLOCK_DESCRIPTION})
    public @interface ViewType {
    }

    //invalid type
    public static final int TYPE_INVALID = -1;
    //区间价
    public static final int TYPE_RANGE = 0;
    //定价
    public static final int TYPE_STABLE = 1;
    //面议
    public static final int TYPE_NEG = 2;

    @IntDef({TYPE_INVALID, TYPE_RANGE, TYPE_STABLE, TYPE_NEG})
    public @interface PriceType {
    }

    private static SparseIntArray cache = new SparseIntArrayEx(10);

    /**
     * Position---------->Type
     *
     * @param serviceNull 服务是否为空
     * @param type        服务价格类型：面议、区间价格、定价
     * @param position    item position
     * @param hasAction   是否有活动
     * @return itemViewType
     */
    private static int convertP2T(boolean serviceNull, @PriceType int type, int position, boolean hasAction) {
        //item 0、1、2位置为地址、工人信息、服务类型
        switch (position) {
            case 0:
                return BLOCK_ADDRESS;
            case 1:
                return BLOCK_WORKERINFO;
            case 2:
                return BLOCK_SERVTYPE;
            default:
                //没有服务（选择服务前）
                if (serviceNull) {
                    return convertP2TForNullService(position);
                } else {
                    switch (type) {
                        case TYPE_RANGE:
                            return convertP2TForRangeService(position, hasAction);
                        case TYPE_NEG:
                            return convertP2TForNegService(position, hasAction);
                        case TYPE_STABLE:
                            return convertP2TForStableService(position, hasAction);
                        default:
                            return BLOCK_NULL;
                    }
                }
        }
    }

    private static int convertP2TForStableService(int position, boolean hasAction) {
        switch (position) {
            case 3:
                return BLOCK_PRICE;
            case 4:
                return BLOCK_SERVNUMBER;
            case 5:
                return BLOCK_SERVTIME;
            case 6:
                if (hasAction)
                    return BLOCK_ACTIVITY;
                else return BLOCK_NOTE;
            case 7:
                if (hasAction)
                    return BLOCK_NOTE;
                else return BLOCK_PHOTO;
            case 8:
                if (hasAction)
                    return BLOCK_PHOTO;
                else return BLOCK_DESCRIPTION;
            default:
                return BLOCK_DESCRIPTION;
        }
    }

    private static int convertP2TForNegService(int position, boolean hasAction) {
        switch (position) {
            case 3:
                return BLOCK_SERVTIME;
            case 4:
                if (hasAction)
                    return BLOCK_ACTIVITY;
                else return BLOCK_NOTE;
            case 5:
                if (hasAction)
                    return BLOCK_NOTE;
                else return BLOCK_PHOTO;
            case 6:
                if (hasAction)
                    return BLOCK_PHOTO;
                else return BLOCK_DESCRIPTION;
            case 7:
                return BLOCK_DESCRIPTION;
            default:return BLOCK_NULL;
        }
    }

    private static int convertP2TForRangeService(int position, boolean hasAction) {
        switch (position) {
            case 3:
                //区间价信息
                return BLOCK_RANGE_INFO;
            case 4://选择服务价格
                return BLOCK_PRICE;
            case 5:
                return BLOCK_SERVTIME;
            case 6:
                if (hasAction)
                    return BLOCK_ACTIVITY;
                else return BLOCK_NOTE;
            case 7:
                if (hasAction)
                    return BLOCK_PHOTO;
                else return BLOCK_DESCRIPTION;
            default://如果有第9个item，就是服务说明
                return BLOCK_DESCRIPTION;
        }
    }

    private static int convertP2TForNullService(int position) {
        switch (position) {
            case 3:
                return BLOCK_SERVTIME;
            case 4:
                return BLOCK_ACTIVITY;
            case 5:
                return BLOCK_NOTE;
            case 6:
                return BLOCK_PHOTO;
            case 7:
                return BLOCK_DESCRIPTION;
            default:
                return BLOCK_NULL;
        }
    }

    /**
     * @param hasService .
     * @param type       .
     * @param position   .
     * @param hasAction  .
     * @return
     */
    public static int convertPositionToViewType(boolean hasService, @PriceType int type, int position, boolean hasAction) {
        cache.clear();
        for (int pos = 0; pos <= 10; pos++) {
            int t = convertP2T(!hasService, type, pos, hasAction);
            cache.put(t, pos);
        }
        return convertP2T(!hasService, type, position, hasAction);
    }

    /**
     * type------->position的映射比较困难，利用{@link #convertPositionToViewType}建立缓存
     *
     * @param viewType .
     * @return .
     */
    public static int convertViewTypeToPosition(@ViewType int viewType) {
        return cache.get(viewType);
    }

    /**
     * 针对指定下单页面，如果一键下单页后期改为使用列表也可以使用该方法
     *
     * @param hasService .
     * @param type       .
     * @param hasAction  .是否有活动
     * @return .
     */
    public static int getItemCount(boolean hasService, @PriceType int type, boolean hasAction) {
        if (hasService) {
            if (type == TYPE_NEG) {//面议
                if (hasAction)
                    return 8;
                else return 7;
            } else if (type == TYPE_STABLE)//定价
            {
                if (hasAction)
                    return 10;
                else return 9;
            } else {//区间价
                //// TODO: 2016/11/1 指定下单没有区间价，一键下单如果采用列表则需要补全区间价的情况
            }
        } else {
            return 5;
        }
        return 5;
    }

    //修改SparseIntArray valueKeyNotFound的默认值
    private static final class SparseIntArrayEx extends SparseIntArray {
        SparseIntArrayEx(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        public int get(int key, int valueIfKeyNotFound) {
            return super.get(key, -1);
        }
    }

}

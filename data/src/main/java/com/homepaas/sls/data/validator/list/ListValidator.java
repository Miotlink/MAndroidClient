package com.homepaas.sls.data.validator.list;

import com.homepaas.sls.data.validator.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2015/12/21.
 * 列表数据校验器
 */

public class ListValidator<T> implements Validator<List<T>> {

    private final Validator<T> validator;

    /**
     * 初始化列表数据校验器
     * @param validator 列表项的每个数据的校验器
     */
    @Inject
    public ListValidator(Validator<T> validator) {
        this.validator = validator;
    }

    /**
     * 验证对象的有效性
     *
     * @param list 验证的对象
     * @return 对象有效时返回true，否则返回false
     */
    @Override
    public boolean isValid(List<T> list) {
        if (list == null || list.size() == 0) {
            return false;
        }

        for (T t : list) {
            if (!isValidSingle(t)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取校验无效的数据列表
     * @param list 校验的原始列表数据
     * @return 无效的列表数据
     */
    public List<T> getInvalidList(List<T> list) {
        List<T> invalidList = new ArrayList<>();
        for (T t : list) {
            if (!isValidSingle(t)){
                invalidList.add(t);
            }
        }
        return invalidList;
    }

    /**
     * 单个数据的校验
     * @param t 校验对象
     * @return 返回单项的校验结果
     */
    private boolean isValidSingle(T t) {
        return validator.isValid(t);
    }
}

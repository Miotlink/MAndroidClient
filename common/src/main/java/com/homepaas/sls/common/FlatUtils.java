package com.homepaas.sls.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJJ on 2016/9/22.
 * 用于提取字段，针对本应用中频繁出现的List<Picture> 需要转化为List<File>、List<String>的场景
 */

public class FlatUtils {


    public static abstract  class ExtractFunc<T,R>{

      public abstract   T func(R r);

    }

    /**
     *
     * @param origin the dataSource to be extracted
     * @param f the extract function obj
     * @param <T> dataSource  type class
     * @param <R> result type class
     * @return
     */
    public static <T,R> List<T> extract(List<R> origin,ExtractFunc<T,R> f){
        List<T> result = new ArrayList<>();
        if (origin != null&&!origin.isEmpty()&&f != null){
            for (R r:origin
                 ) {
              T t =   f.func(r);
                result.add(t);
            }
        }
        return result;
    }
}

package com.homepaas.sls.ui.location.location;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

/**
 * Created by Administrator on 2016/7/28.
 */
public class SuggestionSearchHelper {
    private LatLng latLng;
    private String keyword;
    private static String city;
    private SuggestionSearch suggestionSearch;

    public SuggestionSearchHelper() {
        suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(suggestionSearchListener);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static void setCity(String locationCity) {
        city = locationCity;
    }

    public void requestSuggestion() {
        SuggestionSearchOption suggestionSearchOption = new SuggestionSearchOption();
        if(city==null){
            city="";
        }
        if(keyword==null){
            keyword="";
        }
        suggestionSearchOption.city(city);
        suggestionSearchOption.keyword(keyword);
        suggestionSearch.requestSuggestion(suggestionSearchOption);
    }
    private OnGetSuggestionResultListener suggestionSearchListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
//            if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
//                return;
//            }
            listener.result(suggestionResult);
        }
    };

    public void clear() {
        if ( suggestionSearch != null ){
            suggestionSearch.destroy();
        }
    }
    public interface OnResultListener {
        void result(Object object);
    }
    private OnResultListener listener;
    public void setListener(OnResultListener listener) {
        this.listener = listener;
    }
}

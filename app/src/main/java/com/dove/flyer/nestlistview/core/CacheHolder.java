package com.dove.flyer.nestlistview.core;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by flyer on 2016/12/22.
 * 缓存了所有 ListView 内部的
 * 单个 ItemView 的每个布局实例
 * 里面每个 Item 的布局相同时，不需要再次调用 findViewById(id)
 * 和 convertView 绑定
 * @param
 */
public class CacheHolder{
    private SparseArray<View> mViews = new SparseArray<>();
    private View mConvertView = null;

    protected  <T extends View> T getView(@IdRes int viewId){
        View cacheView = mViews.get(viewId);
        if ( cacheView != null){
            return (T) cacheView;
        }
        cacheView = mConvertView.findViewById(viewId);
        mViews.put(viewId,cacheView);
        return (T) cacheView;
    }

    protected void setConvertView(View convertView){
        this.mConvertView = convertView;
    }
}
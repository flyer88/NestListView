package com.dove.flyer.nestlistview.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flyer on 2016/12/20.
 */

/**
 * 此处有重用机制
 * 开关为 mReuse
 * 如果开启，那么会直接拿原有的 vie 实例进行加载
 * 需要注意，对于 Glide 进行网络图片加载时，如果图片大小不确定，
 * 即 wrap_content 情况下，glide 加载图片大小是不确定的
 * 那会导致图片各个格式不一致，从而在加载时出现 view 突然扩大，突然变小的情况
 */
public class NestListView extends LinearLayout{

    /**
     * NestListView 每个 Item 出现过的所有 convertView
     */
    List<View> mViewCaches = new ArrayList<View>();

    /**
     * 是否开启复用 ConvertView
     */
    boolean mReuse = false;


    public NestListView(Context context) {
        super(context);
    }

    public NestListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setReuse(boolean reuse){
        this.mReuse = reuse;
    }

    /**
     * 开始整体的构建 NestListView 里面的内容
     * @param positionInList
     * @param dataCount
     */
    public void createNestListView(int positionInList, int dataCount){
        if (mBindViewGroupListener == null){
            return;
        }
        if (!mReuse){// 不开启重用机制
            removeAllViews();
            inflateAllNullView(positionInList,dataCount);
            return;
        }
        if (dataCount == 0){// nestListView 全为空，那就直接移除所有的无需再次添加
            removeAllViews();
            return;
        }
        int viewCachesSize = mViewCaches.size();
        if (viewCachesSize > 0){
            if (viewCachesSize > dataCount){
                // 当前 cacheView 中已经存在的 view 数量比添加的要多，需要 remove 掉
                inflatePartConvertView(dataCount,positionInList);
            } else if (viewCachesSize == dataCount){
                // 相等情况直接复用
                inflateAllConvertView(positionInList);
            } else {
                // 当前 cacheView 中已存在的 view 数量比要显示的要少，需要新的 inflate
                inflatePartNullView(dataCount,viewCachesSize,positionInList);
            }
        } else {
            inflateAllNullView(positionInList,dataCount);
        }
    }

    /**
     * 缓存数量多于需要填充的数据
     * 即，NestListView 需要显示的 view 数量
     * 小于缓存中持有的 view 数量
     * dataCount < viewCachesSize
     * @param dataCount
     * @param positionInList
     */
    private void inflatePartConvertView(int dataCount,int positionInList){
        for (int i = getChildCount() - 1 ; i >= dataCount; i--){
            removeView(getChildAt(i));
        }
        for (int i = 0; i < dataCount ;i++){
            View convertView = mViewCaches.get(i);
            if (convertView.getParent() == null){
                addView(convertView);
            }
            mBindViewGroupListener.getNestView(positionInList,i, convertView);
        }
    }

    /**
     * 缓存中缺少 view 实例
     * 需要重新添加
     * dataCount > viewCachesSize
     * @param dataCount
     * @param viewCachesSize
     * @param positionInList
     */
    private void inflatePartNullView(int dataCount, int viewCachesSize, int positionInList){
        for (int i = 0; i < dataCount ;i++){
            if (i < viewCachesSize){
                View convertView = mViewCaches.get(i);
                if (convertView.getParent() == null){
                    addView(convertView);
                }
                mBindViewGroupListener.getNestView(positionInList,i, convertView);
            } else {
                addView(mBindViewGroupListener.getNestView(positionInList,i,null));
            }
        }
    }

    /**
     * 和缓存实例数量一样
     * 直接填充数据即可
     * @param positionInList
     */
    private void inflateAllConvertView(int positionInList){
        for (int i = 0 ;i<mViewCaches.size() ;i++){
            View convertView = mViewCaches.get(i);
            if (convertView.getParent() == null){
                addView(convertView);
            }
            mBindViewGroupListener.getNestView(positionInList,i,mViewCaches.get(i));
        }
    }

    /**
     * 缓存是空的
     * 全部都需要添加
     * @param positionInList
     * @param viewCount
     */
    private void inflateAllNullView(int positionInList, int viewCount){
        for (int i = 0;i<viewCount;i++) {
            View view = mBindViewGroupListener.getNestView(positionInList,i,null);
            addView(view);
        }
    }


    @Override
    public void addView(View child) {
        super.addView(child);
        if (!mReuse){
            if (mViewCaches != null){
                mViewCaches.clear();
            }
            return;
        }
        if (!mViewCaches.contains(child)){
            mViewCaches.add(child);
        }
    }


    BindViewGroupListener mBindViewGroupListener;
    public void setBindViewGroupListener(BindViewGroupListener bindViewGroupListener){
        this.mBindViewGroupListener = bindViewGroupListener;
    }

    public interface BindViewGroupListener {
        View getNestView(int positionInList,int positionInNestList,
                         View convertView);
    }

}

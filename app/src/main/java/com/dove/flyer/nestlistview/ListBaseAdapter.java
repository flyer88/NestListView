package com.dove.flyer.nestlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dove.flyer.nestlistview.core.CacheHolder;
import com.dove.flyer.nestlistview.core.NestListView;

import java.util.List;

/**
 * Created by flyer on 2016/12/20.
 */

public class ListBaseAdapter extends BaseAdapter implements NestListView.BindViewGroupListener {


    private List<StringVo> mStringList;
    private Context mContext;
    ListBaseAdapter(List<StringVo> list, Context context){
        this.mStringList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(mStringList.get(position).getItemId());
        viewHolder.mNestListView.setBindViewGroupListener(this);
        viewHolder.mNestListView.setReuse(true);
        viewHolder.mNestListView.createNestListView(position,mStringList.get(position).getUrlList().size());
        return convertView;
    }

    @Override
    public View getNestView(int positionInList, int positionInNestList, View convertView){
        NestListViewHolder nestListHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.nest_list_item, null);
            // 缓存所有 convertView 下的小 view 的实例对象，避免重复 findViewById
            nestListHolder = new NestListViewHolder(convertView);
            convertView.setTag(nestListHolder);
        } else {
            nestListHolder = (NestListViewHolder) convertView.getTag();
        }
        String url = mStringList.get(positionInList).getUrlList().get(positionInNestList);
        nestListHolder.mTextView.setText(url);
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(nestListHolder.mImageView);
        return convertView;
    }

    private static class NestListViewHolder extends CacheHolder {
        ImageView mImageView;
        TextView mTextView;

        NestListViewHolder(View convertView){
            setConvertView(convertView);
            mImageView = getView(R.id.nest_list_item_iv);
            mTextView = getView(R.id.nest_list_item_tv);
        }
    }


    private static class ViewHolder extends CacheHolder{
        TextView mTextView;
        NestListView mNestListView;
        ViewHolder(View convertView){
            setConvertView(convertView);
            mTextView = getView(R.id.list_item_content_tv);
            mNestListView = getView(R.id.list_item_content_nlv);
        }
    }


}

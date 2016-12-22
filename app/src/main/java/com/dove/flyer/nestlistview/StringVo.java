package com.dove.flyer.nestlistview;

import java.util.List;

/**
 * Created by flyer on 2016/12/20.
 */

public class StringVo {

    String mItemId;

    List<String> mUrlList;

    public StringVo(String itemId,List<String> urlList){
        this.mItemId = itemId;
        this.mUrlList = urlList;
    }

    public void setUrlList(List<String> stringList) {
        mUrlList = stringList;
    }

    public List<String> getUrlList() {
        return mUrlList;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

    public String getItemId() {
        return mItemId;
    }
}

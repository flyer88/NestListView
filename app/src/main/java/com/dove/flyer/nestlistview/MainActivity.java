package com.dove.flyer.nestlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mAdd;
    Button mDel;
    ListView mListView;
    List<StringVo> mStringList = new ArrayList<>();
    ListBaseAdapter mListBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdd = (Button) findViewById(R.id.add_item);
        mDel = (Button) findViewById(R.id.del_item);
        mListView = (ListView) findViewById(R.id.main_lv);
        mStringList.add(addItemData("" + 0,
                "http://ww2.sinaimg.cn/large/610dc034jw1faza3ghd2lj20f00k1gof.jpg"));
        mStringList.add(addItemData("" + 1,
                "http://ww3.sinaimg.cn/large/610dc034gw1fay98gt0ocj20u011hn24.jpg"));
        mStringList.add(addItemData("" + 2,
                "http://ww2.sinaimg.cn/large/610dc034jw1fawx09uje2j20u00mh43f.jpg"));
        mStringList.add(addItemData("" + 3,
                "http://ww4.sinaimg.cn/large/610dc034jw1favb116hm2j20u00u0gqi.jpg"));
        mStringList.add(addItemData("" + 4,
                "http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg"));


        mListBaseAdapter = new ListBaseAdapter(mStringList,this);
        mListView.setAdapter(mListBaseAdapter);
        mAdd.setOnClickListener(this);
        mDel.setOnClickListener(this);
    }

    private StringVo addItemData(String position,String url){
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i<(int)(Math.random()*5)+1; i++){
            urlList.add(url);
        }
        return new StringVo(position,urlList);
    }

    @Override
    public void onClick(View v) {
        int size = mStringList.size();
        switch (v.getId()) {
            case R.id.add_item:
                List<String> urlList = new ArrayList<>();
                urlList.add("http://ww2.sinaimg.cn/large/610dc034gw1farbzjliclj20u00u076w.jpg");
                urlList.add("http://ww2.sinaimg.cn/large/610dc034gw1farbzjliclj20u00u076w.jpg");
                mStringList.add(new StringVo("" + size,urlList));
                break;
            case R.id.del_item:
                mStringList.remove(size - 1);
                break;
        }
        mListBaseAdapter.notifyDataSetChanged();
    }
}

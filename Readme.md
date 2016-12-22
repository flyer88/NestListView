### 作用

解决 `ListView` 嵌套`ListView` 问题

`NestListView` 继承自 `Linearlayout`

通过 `addView` 来构造出类似 `ListView` 样式

### 原理参考以下文章

[http://www.jianshu.com/p/b805091bd4e5](http://www.jianshu.com/p/b805091bd4e5)

[http://blog.csdn.net/lmj623565791/article/details/38902805/](http://blog.csdn.net/lmj623565791/article/details/38902805/)



### 使用方法：

直接拷贝 [`NestListView.java`](https://github.com/flyer88/NestListView/blob/master/app/src/main/java/com/dove/flyer/nestlistview/core/NestListView.java) 和 [`CacheHolder.java` ](https://github.com/flyer88/NestListView/blob/master/app/src/main/java/com/dove/flyer/nestlistview/core/CacheHolder.java)

对外层的 `ListView` 的 `Adapter` 添加并实现该 `NestListView.BindViewGroupListene` 接口即可，如下：

```java
public class xxAdapter extends BaseAdapter implements NestListView.BindViewGroupListener{
  	....
    ....
       @Override 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
       	...
        ...
        viewHolder.mNestListView.setBindViewGroupListener(this);// 绑定当前实现的接口
        viewHolder.mNestListView.setReuse(true);// 开启重用机制
        viewHolder.mNestListView.createNestListView(position,data.size());// 开始创建具体的 NestListView
        return convertView;
    } 
  
    //该方法和重写 BaseAdapter 的 getView 类似
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
		...
        nestListHolder.mTextView.setText(url);
		...
        return convertView;
    } 
}
```



具体可以参考[`ListBaseAdapter.java`](https://github.com/flyer88/NestListView/blob/master/app/src/main/java/com/dove/flyer/nestlistview/ListBaseAdapter.java)


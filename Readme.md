解决 `ListView` 嵌套`ListView` 问题



`NestListView` 继承自 `Linearlayout`

通过 `addView` 来构造出类似 `ListView` 样式

原理参考以下文章

[http://www.jianshu.com/p/b805091bd4e5](http://www.jianshu.com/p/b805091bd4e5)

[http://blog.csdn.net/lmj623565791/article/details/38902805/](http://blog.csdn.net/lmj623565791/article/details/38902805/)

直接使用，直接拷贝 `NestListView.java` 和 `CacheHolder.java` 即可
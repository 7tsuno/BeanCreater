Creater for creating JavaBeans in one line.

# example

```java
List<HogeBean> hoges = ...

hoges.stream().map(hoge -> BeanCreater.of(FugaBean::new)
    .construct(bean -> bean.setId(hoge.getId())).build())...
```

http://qiita.com/7tsuno/items/14ca45aa53ce4b98b7f2

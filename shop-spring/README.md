# SpringShop

#### 介绍
reactShop的后端，使用SpringBoot2框架

前后端分离，react全家桶+SpringBoot+jwt

[配套前端ReactShop](https://gitee.com/zhuyunlong2018/ReactShop)

#### 功能备注
1. 会员管理
会员列表
会员收藏
会员购物车
收货地址
会员足迹

2. 商城管理
商品分类管理
商品管理

3. 商品管理
商品管理
评论管理

4. 订单管理

5. 权限管理

6. 系统管理

#### 本机环境

1. JDK: v1.8.0_221
2. maven: v3.6.0
3. redis: v3.2.1
4. mysql: v5.5.53
5. 开发工具：intellij IDEA 2019.1(Community Edition)
6. 需要插件：lombok(v0.25-2019.1), MyBatisX(v.0.1.0)

#### 框架依赖

```xml
<java.version>1.8</java.version>
<commons.lang.version>2.6</commons.lang.version>
<mybatis.spring.boot.version>2.0.1</mybatis.spring.boot.version>
<mybatisplus.version>3.0.7.1</mybatisplus.version>
<mysql.version>5.1.38</mysql.version>
<apache.httpcomponents.version>4.3.6</apache.httpcomponents.version>
<qcloud.cos.version>4.4</qcloud.cos.version>
<druid.version>1.1.13</druid.version>
<jwt.version>0.7.0</jwt.version>
<swagger.version>2.7.0</swagger.version>
<lombok.version>1.18.2</lombok.version>
```

#### 安装教程
1. 下载源码
```shell
$ git https://gitee.com/zhuyunlong2018/SpringShop.git

```
2. 配置数据库
导入sql文件

3. 导入IDEA，等待maven安装依赖

4. 运行项目

5. 进入swagger文档页面
[swagger-ui](http://localhost:8083/swagger-ui.html)

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

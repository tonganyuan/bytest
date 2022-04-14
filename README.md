# 云南白药·平台项目(platform) 1.0
- 运营中心1.0项目, 技术栈为AlibabaCloud + springSecurity + Oauth2 + jwt + mybatis-plus
- 用户中心1.0项目(暂未配置), 技术栈为AlibabaCloud + springSecurity + Oauth2 + jwt + mybatis-plus


#### 核心依赖
依赖 | 版本
---|---
Spring Boot |  2.2.5.RELEASE
Spring Cloud | Hoxton.SR3
spring-cloud-alibaba | 2.2.1.RELEASE
Mybatis Plus | 3.4.1
hutool | 5.5.6
lombok | 1.18.12
guava | 30.1-jre
fastjson | 1.2.75

#### 模块架构
```
platform-center(云南白药综合内部平台)
└── platform-api -- 平台接口聚合(内部服务提供feign调用)
     └── platform-operation-api -- 运营中心接口
├── platform-auth -- 平台认证服务[9501]
└── platform-biz -- 平台业务聚合
     └── platform-operation-biz -- 运营中心业务[9502]
├── platform-gateway -- 网关服务[9500]
└── platform-generator -- mybatis-plus代码生成器
```

#### 模块说明
- 认证服务 platform-auth

```
1，采用springSecurity集成oauth2协议开发
2，采用无状态的jwt做鉴权，redis作为上线下线的标志
3，其他的业务服务作为资源服务器，并且在oauth_client_details表配置clientId和secret，本项目不使用resourceId做更细粒度的权限控制
4，auth暴露公钥端点，所有资源服务可以利用该端点做自认证， 但目前认证的处理全部集成在网关中，内部服务无需认证
```
- 公共模块 platform-common

```
1，platform-common-cache -- 使用springCache和redis作为缓存组件，支持代码中使用@cacheAble等注解，并将直接序列化进redis
2，platform-common-core -- 常量、枚举、异常以及全局父对象和工具类等
3，platform-common-log -- 日志模块
4，platform-common-security -- 安全模块，封装springSecurity配置
5，platform-common-swagger -- 接口文档
6，platform-common-config -- 包括orm配置，http配置，feign配置等
7，platform-common-unit -- 公共服务单元（聚合core + config），原因是网关采用webflux方式编程，与config中相关severlet冲突，所以网关只引用core包，其他服务引用unit
```
- 网关 platform-gateway

```
1，white_paths配置项为路由白名单
2，集成统一认证功能
3，集成统一限流，跨域，日志等解决方案
```


#### 开发规范(重要)
``` 
1，项目开发中涉及到的该biz下的pojo实体类和枚举或者工具类请写在platform-api对应子module下或者platform-common-core下, 方便其他模块引用, platform-biz下只涉及业务实现
2，项目开发中涉及到的所有全局常量工具类和需要继承的公共类请写在platform-common-core下
3，redis的所有key实行统一管理，位置为com.ynby.platform.common.core.constant.RedisKeyConstants
4，全局用户线程变量为com.ynby.platform.common.security.utils.ContextUtil, 只有在传递jwt的情况下才能调用，如果未传且调用，直接抛出异常，请保证在白名单接口中不会使用contextUtil
5，借鉴于前面中医项目和商城项目中比较不太规范的feign调用情况，本项目所有涉及feign调用的地方请使用统一工具类com.ynby.platform.common.core.utils.HttpUtil, 如不满足请自行扩展
6，项目中所抛特殊异常请统一规划异常码com.ynby.platform.common.core.enums.OperaEnum，若有需要也可新增异常类com.ynby.platform.common.core.exception
7，项目上线后需要在线上更新数据库的时候请将sql语句写入根目录下modify.sql中
8，关于feign以及熔断的配置可以参考platform-operation-api
9，请大致遵循gitflow开发规范
10，代码生成器platform-generator直接解压到portal目录下，该文件已做git忽略
11，token手动签出 com.ynby.platform.auth.config.WebSecurityConfigTest.createTwtToken
```

#### 如何创建一个业务工程

```
1, platform-api和platform-biz分别创建对应的工程
2, ServiceNameConstants类创建工程名称和常量
3, logback-spring.xml修改日志的工程名称
4, platform-common-security中的pom新增platform-xxx-api的包路径
5, 修改有效port
6, 加入构建的shell
7, 网关加入对应路由
8, 修改bootStrap中的typeAliasesPackage
```
#### 架构图

<img src="http://ynbymall.oss-cn-shenzhen.aliyuncs.com/canteen/%E7%9F%AD%E4%BF%A1%E5%B9%B3%E5%8F%B0%E6%9E%B6%E6%9E%84%E5%9B%BE.png"/>

#### 平台项目git地址
[运营中心git](http://172.16.10.65:10080/internet-hospital)

#### 平台项目swagger地址
[平台swagger](http://localhost:9500/swagger-ui/index.html)



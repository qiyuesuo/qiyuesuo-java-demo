# 契约锁私有云 Java SDK 集成指南

Java SDK提供了契约锁API的请求封装、摘要签名、响应解释等功能，您可以直接使用SDK实现合同的签署流程。

我们提供了Java SDK示例，可访问 https://github.com/qiyuesuo/private-sdk-sample 查看。
## 环境依赖:
JDK版本：1.6及以上
SDK文件说明：
```java
private-sdk-xxx.jar – 核心包，请求封装、响应解释等
```
## 集成方法：
方法1：直接将SDK的中jar文件引入到项目的Classpath下。

方法2：Maven构建

将private-sdk-xxx.jar移动到您的Maven中央仓库下列目录下：/net/qiyuesuo/sdk/private-sdk/xxx/,
添加以下依赖到您的项目pom文件中：
```xml
<dependency>
	<groupId>net.qiyuesuo.sdk</groupId>
	<artifactId>private-sdk</artifactId>
	<version>xxx</version>
</dependency>
```
## 调用方法：
方法1：直接调用

初始化：
```javascript

serviceUrl:请求服务的地址
accessToken:接入令牌
accessSecret：接入秘钥
备注：以上参数从电子签署平台管理后台中获取
SDKClient client = new SDKClient(serviceUrl, accessToken, accessSecret);
```
调用具体接口，参考SDK文档，如：
```java
ContracService contractService = new ContractService(client);

String documentName = "测试文档";
InputStream inputStream = new FileInputStream(new File("E:\\1.pdf"));
contractService.createDocument(inputStream, documentName);
logger.info("创建文档成功");
```

方法2：如果您的项目采用Spring构建，您需要将SDKClient和具体的服务Bean放置在Spring的XML 配置文件中：
```xml
<!-- SDKClient -->
<bean id="client" class="net.qiyuesuo.sdk.SDKClient">
    <constructor-arg value="https://privopen.qiyuesuo.me/" index="0"></constructor-arg>
    <constructor-arg value="OKbWm3FiG8" index="1"></constructor-arg>
    <constructor-arg value="hALEhFSnBVj3Wnzz9972VohAHQBNT8" index="2"></constructor-arg>
</bean>

<!--其他服务类似，请参考SDK文档 -->
<bean id="ContractService" class="net.qiyuesuo.sdk.impl.ContractServiceImpl">
    <constructor-arg>
        <ref bean="client">
    </ref></constructor-arg>
</bean>
```
你也可以采用注解的方式：
```java
	@Bean
	public SDKClient getClient() {
		String url = "http://privopen.qiyuesuo.me";
		String accessKey = "OKbWm3FiG8";
		String accessSecret = "hALEhFSnBVj3Wnzz9972VohAHQBNT8";
		SDKClient client = new SDKClient(url, accessKey, accessSecret);
		return client;
	}
	
	@Bean
	public ContractService contractService(SDKClient client) {
		return new ContractServiceImpl(client);
	}
```




[TOC]
# 契约锁私有云开放平台接入流程

## 开放平台介绍

契约锁私有云开放平台（以下简称开放平台）是契约锁私有云（以下简称私有云）在现有业务功能的基础上，将私有云相关服务以SDK和API的形式对外开放，以方便客户系统与私有云的对接。

**对接语言：**Java SDK（Java1.6以上版本） 以及 标准的Restful API。

## 接入流程

### 1 创建应用

**描述：**使用开放平台前需要先创建“接入应用”，用来作为调用开放平台接口的凭证；

**操作：**

1、登录契约锁控制台（通常地址为 http://[host]:9181）；

2、点击左侧导航栏：集成中心 -> 集成管理；

3、在页面中点击“添加应用”按钮生成应用，应用的AppToken和AppSecret将作为接口调用的凭证；

### 2 接口对接

**描述：**接口对接分为SDK对接和API对接。

#### 2.1 SDK对接

1、在客户系统中引入SDK的jar文件；

2、初始化接口并调用，如下为“合同详情”调用示例：

```java
// 初始化接口
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(sdkClient);
// 调用接口
Long contractId = 2488573272094023691L;
ContractDetail contractDetail = contractService.detail(2488573272094023691L);
```

示例中的url为开放平台地址，一般为 http://[host]:9182；

accessKey即“创建应用”时生成的AppToken；

accessSecret即“创建应用”时生成的AppSecret；

3、具体的接口文档请查看**“JavaSDK接口文档”**。

#### 2.2 API对接

如下为"用业务分类创建合同"接口的调用示例：

```html
POST /contract/createbycategory HTTP/1.1
Host: privopen.qiyuesuo.me
Content-Type: application/json
x-qys-accesstoken: DWjfxmA12a
x-qys-timestamp: 0
x-qys-signature: 4e77af6b465c4080e25ecd012eaf2916
Cache-Control: no-cache
Postman-Token: 0d071437-f527-7974-03bc-3bfad235f021

{
  "subject": "测试合同",
  "sn": "01",
  "ordinal": true,
  "send": true,
  "documents": [2582068183430812187],
  "signatories": [
  	{
  		"tenantType": "CORPORATE",
  		"serialNo": 1,
  		"actions": [
  			{
        		"type":"CORPORATE",
        		"serialNo":1
  			}
  			]
  	}
  	]
}
```

针对上述示例：

**请求方法：** POST，在“API接口文档”中可查看；

**接口地址：**/contract/createbycategory，在“API接口文档”中可查看；

**请求域名：**开放平台地址，一般为 http://[host]:9182；

**请求头：**三个自定义参数和一个Content-Type：

​	1、自定义参数 x-qys-accesstoken ，即“创建应用”时生成的AppToken；

​	2、自定义参数 x-qys-timestamp ，即当前时间戳；

​	3、自定义参数 x-qys-signature ，即签名信息，生成算法为：Md5(AppToken + AppSecret + timestamp)，即AppToken 和 AppSecret 和 时间戳拼接的字符串的MD5值，注：**MD5值为32位小写**。

​	4、Content-Type，请求格式，在“API接口文档”中查看；

**具体的各个接口调用请参考“API接口文档”。**


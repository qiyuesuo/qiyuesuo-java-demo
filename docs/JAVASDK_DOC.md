# 契约锁私有云 Java SDK 接口文档

**发布时间**：2019-09-19

**发布版本**：4.0.0

**更新内容**：

>1.公章签署支持只盖骑缝章
>
>2.新增接口支持未签署合同删除文件
>
>3.签署页面支持签署完成后跳转
>
>4.新增草稿合同填参
>
>5.支持创建合同时指定接收人身份证号

# 1 合同接口

## 1.1 合同创建

### 初始化

接口：`net.qiyuesuo.sdk.api.ContractService`

实现类：`net.qiyuesuo.sdk.impl.ContractServiceImpl`

构造函数：`ContractServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

示例
```java

String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(sdkClient);
```

### 1.1.1 创建合同文档

**描述：**在契约锁系统中，一份合同至少包含一个合同文档，合同文档以PDF格式保存。合同文档可以通过用户上传文件来生成，也可以通过在模板来生成。此接口用来创建合同文档，在创建合同前调用。

#### 1.1.1.1 用文件创建合同文档

**方法**：`Long createDocument(InputStream inputStream, String title, List<WaterMarkContent> waterMarkConfig)`

**描述**：用文件创建合同文档。支持的文件类型包括：doc, docx, pdf, png, gif, jpg, jpeg, tiff, html。注：由于合同文件以PDF格式保存，所以推荐使用PDF格式的文件来创建合同文档，使用其他格式的文件需要先转换成PDF格式再创建，效率较低。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| inputStream | InputStream | 是 | 原始的合同文件流 |
| title | String | 是 | 合同文件名称 |
| waterMarks | List&lt;WaterMarkContent&gt; | 否 | 水印 |

WaterMarkContent

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

**返回值**： Long 文档ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
InputStream inputStream1 = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\测试1.pdf"));
List<WaterMarkContent> list = new ArrayList<WaterMarkContent>();
WaterMarkContent waterMarkContent1 = new WaterMarkContent();
waterMarkContent1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMarkContent1.setImageBase64("/9j/4AAQSkZJR");

WaterMarkContent waterMarkContent2 = new WaterMarkContent("文字水印2", 15, WaterMarkLocation.UPPER_RIGHT);
list.addAll(Arrays.asList(waterMarkContent1,waterMarkContent2));

documentId = contractService.createDocument(inputStream1, "代持协议文档", list);
IOUtils.safeClose(inputStream1);
logger.info("创建合同文件成功,documentId:{}", documentId);
```

#### 1.1.1.2 用模板创建合同文档

**方法**：`Long createDocument(Long templateId,Map<String, String> params,String title, List<WaterMarkContent> waterMarkConfig)`

**描述**：用户在私有云系统中维护好文件模板，记录下模板ID和模板参数，使用此接口即可创建一个合同文档。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| templateId | Long | 是 |模板ID，在私有云系统维护、查看 |
| params | Map | 否 | 模板参数，Map中的key为参数名称，value参数值 |
| title | String | 是 | 合同文件名称 |
| waterMarks | List&lt;WaterMarkContent&gt; | 否 | 水印 |

WaterMarkContent

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

**当使用HTML模板时，参数params注意事项如下：**

>参数类型是单行文本时，value大小不超过300;<br>
>参数类型是日期时，value格式为：yyyy-MM-dd，如：2019-06-04;<br>
>参数类型是身份证号，value只能是15或18位的数字或字母，如：123456789123456789;<br>
>参数类型是单选，value只能是单选的选项,如：val1;<br>
>参数类型是多选，value只能是多选的选项，多个value用逗号隔开，如：val1,val2;<br>
>参数类型是表格，可以动态添加行，暂不支持动态添加列，value格式是一维数组，数组的每项对应表格的每行，每行格式是key-value格式，key是表格每列的列名如：[{\"key1\":\"1\",\"key2\":\"2\",\"key3\":\"3\",\"key4\":\"4\"},{\"key1\":\"5\",\"key2\":\"6\",\"key3\":\"7\",\"key4\":\"8\"}]。

**返回值：** Long 文档ID

**示例：**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Map<String, String> params = new HashMap<String, String>();
params.put("param1", "参数值一");
params.put("param2", "参数值二");
List<WaterMarkContent> list = new ArrayList<WaterMarkContent>();
WaterMarkContent waterMarkContent1 = new WaterMarkContent();
waterMarkContent1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMarkContent1.setImageBase64("/9j/4AAQSkZJR");

WaterMarkContent waterMarkContent2 = new WaterMarkContent("文字水印2", 15, WaterMarkLocation.UPPER_RIGHT);
list.addAll(Arrays.asList(waterMarkContent1,waterMarkContent2));
Long documentId = contractService.createDocument(2597003797105070598L, params, "参数模板文档", list);
logger.info("创建合同文件成功,documentId:{}", documentId);
```

#### 1.1.1.3 多文件创建合同文档

**方法**：`Long createDocument(List<InputStream> inputStreams, String title, List<WaterMarkContent> waterMarkConfig)`

**描述**：多个文件组合成一个合同文档，返回合同文档ID，合同文档在创建合同时用到。生成的合同文档的内容顺序与参数中文件顺序一致。支持的文件类型包括：doc, docx, pdf, png, gif, jpg, jpeg, tiff, html。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| inputStreams | Array[InputStream] | 是 | 原始的合同文件流 |
| title | String | 是 | 合同文件名称 |
| waterMarks | List&lt;WaterMarkContent&gt; | 否 | 水印 |

WaterMarkContent

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

**返回值**： Long 文档ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
List<InputStream> inputstreams = new ArrayList<InputStream>();
InputStream inputStream1 = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\测试1.pdf"));
InputStream inputStream2 = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\测试1.pdf"));
inputstreams.add(inputStream1);
inputstreams.add(inputStream2);
List<WaterMarkContent> list = new ArrayList<WaterMarkContent>();
WaterMarkContent waterMarkContent1 = new WaterMarkContent();
waterMarkContent1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMarkContent1.setImageBase64("/9j/4AAQSkZJR");

WaterMarkContent waterMarkContent2 = new WaterMarkContent("文字水印2", 15, WaterMarkLocation.UPPER_RIGHT);
list.addAll(Arrays.asList(waterMarkContent1,waterMarkContent2));
Long documentId = contractService.createDocument(inputstreams, "代持协议文档", list);
IOUtils.safeClose(inputStream1);
IOUtils.safeClose(inputStream2);
logger.info("创建合同文件成功,documentId:{}", documentId);
```

#### 1.1.1.4 根据路径创建合同文档

**方法**：`Long createDocumentByUrl(String url, String title, List<WaterMarkContent> waterMarkConfig)`

**描述**：根据文件路径创建文档（文件路径可以是本地路径或网络路径），返回合同文档ID，合同文档在创建合同时用到。生成的合同文档的内容顺序与参数中文件顺序一致。支持的文件类型包括：doc, docx, pdf, png, gif, jpg, jpeg, tiff, html。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| url | String | 是 | 文件路径 |
| title | String | 是 | 合同文档名称 |
| waterMarks | List&lt;WaterMarkContent&gt; | 否 | 水印 |

WaterMarkContent

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

**返回值**： Long 文档ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
String url = "http://d.hiphotos.baidu.com/image/pic/item/f636afc379310a55f00f421ab94543a982261030.jpg";
List<WaterMarkContent> list = new ArrayList<WaterMarkContent>();
WaterMarkContent waterMarkContent1 = new WaterMarkContent();
waterMarkContent1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMarkContent1.setImageBase64("/9j/4AAQSkZJR");

WaterMarkContent waterMarkContent2 = new WaterMarkContent("文字水印2", 15, WaterMarkLocation.UPPER_RIGHT);
list.addAll(Arrays.asList(waterMarkContent1,waterMarkContent2));
documentId = contractService.createDocumentByUrl(url, "路径文档", list);
logger.info("创建合同文件成功,documentId:{}", documentId);
```

#### 1.1.1.5 根据文件类型创建合同文档

**方法**：`CreateDocumentResult createByFile(CreateDocumentRequest request)`

**描述**：调用接口须填写文件类型，用文件创建合同文档。支持的文件类型包括：doc, docx, pdf, png, gif, jpg, jpeg, tiff, html, rtf, xls。注：由于合同文件以PDF格式保存，所以推荐使用PDF格式的文件来创建合同文档，使用其他格式的文件需要先转换成PDF格式再创建，效率较低。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | File | 是 | 文档文件 |
| title | String | 是 | 合同文档名称 |
| fileType | String | 是 | 文件类型：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html, rtf, xls |
| waterMarks | List&lt;WaterMarkContent&gt; | 否 | 水印 |

WaterMarkContent

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

**返回值**：CreateDocumentResult

CreateDocumentResult:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| documentId | Long | 文档Id |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
CreateDocumentRequest request = new CreateDocumentRequest();
request.setFile(new StreamFile(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\测试1.pdf"))));
request.setFileType("pdf");
request.setTitle("文档1");
List<WaterMarkContent> list = new ArrayList<WaterMarkContent>();
WaterMarkContent waterMarkContent1 = new WaterMarkContent();
waterMarkContent1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMarkContent1.setImageBase64("/9j/4AAQSkZJR");

WaterMarkContent waterMarkContent2 = new WaterMarkContent("文字水印2", 15, WaterMarkLocation.UPPER_RIGHT);
list.addAll(Arrays.asList(waterMarkContent1,waterMarkContent2));
request.setWaterMarkConfig(list);
CreateDocumentResult result = contractService.createByFile(request);
Long documentId = result.getDocumentId();
logger.info("创建合同文件成功,documentId:{}", documentId);
```


#### 1.1.1.6 用文件创建发起方可见附件文档

**方法**：`Long createSponsorDocumentByFile(InputStream inputStream, String title)

**描述**：调用接口须填写文件类型，用文件创建附件文档，该文档作为内部附件使用，只供发起方内部查看、盖章时的参考文件。。支持的文件类型包括：doc, docx, pdf, png, gif, jpg, jpeg, tiff, html, rtf, xls。注：由于合同文件以PDF格式保存，所以推荐使用PDF格式的文件来创建合同文档，使用其他格式的文件需要先转换成PDF格式再创建，效率较低。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | File | 是 | 文档文件 |
| title | String | 是 | 合同文档名称 |
| fileType | String | 是 | 文件类型：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html, rtf, xls |

**返回值**：CreateDocumentResult

CreateDocumentResult:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| documentId | Long | 文档Id |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用   
InputStream inputStream;
    try {
        inputStream = new FileInputStream(new File("D:\\book\\135018.pdf"));
        logger.info(String.valueOf(contractService.createSponsorDocumentByFile(inputStream, "测试文档")));
    } catch (Exception e) {
        logger.error(e.getMessage());
    } final {
        IOUtils.safeClose(inputStream);
    }
```

### 1.1.2 添加合同文档

**描述：**为已生成的草稿状态的合同中添加合同文档。此接口在创建合同接口后调用。

#### 1.1.2.1 用文件添加合同文档

**方法**：`Long addDocumentByFile(AddDocumentByFile request)`

**描述**：为已生成的草稿状态的合同添加合同文档。支持的文件类型包括：doc, docx, pdf, png, gif, jpg, jpeg, tiff, html。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| inputStream | InputStream | 是 | 原始的合同文件流 |
| title | String | 是 | 合同文件名称 |
| contractId | Long | 是 | 合同ID |
| waterMarks | List&lt;WaterMarkContent&gt; | 否 | 水印 |

WaterMarkContent

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

**返回值**： Long 文档ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
InputStream inputStream1 = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\测试1.pdf"));
AddDocumentByFile request = new AddDocumentByFile();
request.setFile(inputStream1);
request.setTitle("新增文档");
request.setContractId(2599525424337997896L);
List<WaterMarkContent> list = new ArrayList<WaterMarkContent>();
WaterMarkContent waterMarkContent1 = new WaterMarkContent();
waterMarkContent1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMarkContent1.setImageBase64("/9j/4AAQSkZJR");

WaterMarkContent waterMarkContent2 = new WaterMarkContent("文字水印2", 15, WaterMarkLocation.UPPER_RIGHT);
list.addAll(Arrays.asList(waterMarkContent1,waterMarkContent2));
request.setWaterMarkConfig(list);
Long documentId = contractService.addDocumentByFile(request);
IOUtils.safeClose(inputStream1);
logger.info("添加合同文件成功,documentId:{}", documentId);
```

#### 1.1.2.2 用模板添加合同文档

**方法**：`Long addDocumentByTemplate(AddDocumentByTemplate request)`

**描述**：为已生成的草稿状态的合同添加合同文档。用户在私有云系统中维护好文件模板，记录下模板ID和模板参数，使用此接口即可添加合同文档。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| templateId | Long | 是 | 模板ID，在私有云系统中维护、查看 |
| params | Map | 否 | 模板参数，Map中的key为参数名称，value参数值 |
| title | String | 是 | 合同文档名称 |
| contractId | Long | 是 | 合同ID |
| waterMarks | List&lt;WaterMarkContent&gt; | 否 | 水印 |

WaterMarkContent

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

**当使用HTML模板时，参数params注意事项如下：**

>参数类型是单行文本时，value大小不超过300;<br>
>参数类型是日期时，value格式为：yyyy-MM-dd，如：2019-06-04;<br>
>参数类型是身份证号，value只能是15或18位的数字或字母，如：123456789123456789;<br>
>参数类型是单选，value只能是单选的选项,如：val1;<br>
>参数类型是多选，value只能是多选的选项，多个value用逗号隔开，如：val1,val2;<br>
>参数类型是表格，可以动态添加行，暂不支持动态添加列，value格式是一维数组，数组的每项对应表格的每行，每行格式是key-value格式，key是表格每列的列名如：[{\"key1\":\"1\",\"key2\":\"2\",\"key3\":\"3\",\"key4\":\"4\"},{\"key1\":\"5\",\"key2\":\"6\",\"key3\":\"7\",\"key4\":\"8\"}]。

**返回值**： Long 文档ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
AddDocumentByTemplate request = new AddDocumentByTemplate();
request.setTemplateId(2597003797105070598l);
request.setTitle("新增模板");
request.setContractId(2599525424337997896L);
List<WaterMarkContent> list = new ArrayList<WaterMarkContent>();
WaterMarkContent waterMarkContent1 = new WaterMarkContent();
waterMarkContent1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMarkContent1.setImageBase64("/9j/4AAQSkZJR");

WaterMarkContent waterMarkContent2 = new WaterMarkContent("文字水印2", 15, WaterMarkLocation.UPPER_RIGHT);
list.addAll(Arrays.asList(waterMarkContent1,waterMarkContent2));
request.setWaterMarkConfig(list);
Long documentId = contractService.addDocumentByTemplate(request);
logger.info("添加合同文件成功,documentId:{}", documentId);
```

#### 1.1.2.3 用文件添加附件文档

**方法**：`Long addSponsorDocumentByFile(AddDocumentByFile request)`

**描述**：为已生成的草稿状态的合同添加附件文档，该文档作为内部附件使用，只供发起方内部查看、盖章时的参考文件。。支持的文件类型包括：doc, docx, pdf, png, gif, jpg, jpeg, tiff, html。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| inputStream | InputStream | 是 | 原始的合同文件流 |
| title | String | 是 | 合同文件名称 |
| contractId | Long | 是 | 合同ID |

**返回值**： Long 文档ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
InputStream inputStream1 = new FileInputStream(new File("D:\\book\\135018.pdf"));
AddDocumentByFile request = new AddDocumentByFile();
request.setFile(inputStream1);
request.setTitle("附件文档");
request.setContractId(2603874358994120763L);
System.out.println(contractService.addSponsorDocumentByFile(request));
IOUtils.safeClose(inputStream1);
```

### 1.1.3 编辑合同文档

#### 1.1.3.1 添加附件

**方法**：`void addAttachment(InputStream inputStream, Long documentId, String title, String description)`

**描述**：合同文档在签章系统中是以PDF形式存在，此接口可以为生成的PDF文档添加附件，附件将嵌入进PDF文档中。附件可以在下载的合同文档中查看。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 文档ID |
| title | String | 是 | 附件名称（应包含扩展名） |
| description | String | 否 | 附件描述 |
| attachment | InputStream | 是 | 附件 |


**返回值**： 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
InputStream inputStream = new FileInputStream(new File("D:\\Test.pdf"));
contractService.addAttachment(inputStream, 2488573259477557254L, "添加附件", "描述");
```

#### 1.1.3.2 添加水印

**方法**：`void addWatermark(WaterMarkContent waterMark)`

**描述**：向已经创建好的文档中添加水印。

**参数:**

WaterMarkContent：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 合同文件ID |
| type | WaterMarkType | 否 | 水印类型:IMAGE(图片)，QRCODE（二维码），TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色,16进制颜色值,需携带# |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| scaling | Double | 否 | 图片缩放比例 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充） |


**返回值**： 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//文字水印
WaterMarkContent waterMark1 = new WaterMarkContent();
waterMark1.setDocumentId(2599891659383853062L);
waterMark1.setType(WaterMarkType.TEXT);
waterMark1.setContent("水印");
waterMark1.setFontSize(16);
waterMark1.setTransparency(0.5);
waterMark1.setColor("#999999");
waterMark1.setLocation(WaterMarkLocation.UPPER_LEFT);
waterMark1.setDensity(TileDensity.SUITABLE);
waterMark1.setRotateAngle(0.45);
waterMark1.setScaling(0.5);
//图片水印
WaterMarkContent waterMark2 = new WaterMarkContent();
waterMark2.setDocumentId(2621273342019834156L);
waterMark2.setContent("图片水印");
waterMark2.setType(WaterMarkType.QRCODE);
String imageBase64 = "iVBORw0KGgoAAAANSUhEUgAA";
waterMark2.setImageBase64(imageBase64);
waterMark2.setLocation(WaterMarkLocation.LOWER_RIGHT);
waterMark2.setScaling(1.5);
//添加文字水印
//contractService.addWatermark(waterMark1);
//添加图片水印
contractService.addWatermark(waterMark2);
```

### 1.1.4 创建合同

**描述：**调用此接口来创建合同。可以选择是否发起合同，如果选择不发起合同，则可以继续修改任意合同信息，此时合同是草稿状态；如果选择发起合同，则签署方收到签署通知即可登录签署合同，合同发起后不允许添加合同文档。

#### 1.1.4.1 自定义创建合同

**方法**：`Long createContract(CreateContractRequest request)`

**描述**：自定义创建合同。可以指定合同文档、合同签署方、签署位置、认证方式等信息。在“创建合同文档”后调用此接口来创建合同（此接口为旧接口，不推荐使用，推荐使用接口“用业务分类创建合同”）。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| subject | String | 是 | 合同名称 |
| ordinal | Boolean | 否 | 是否顺序签署，默认为false |
| categoryId | Long | 否 | 业务分类ID |
| categoryName | String | 否 | 业务分类名称，如果分类ID为空，则用此参数确定业务分类 |
| sn | String | 否 | 合同编号，对接方系统中的业务编号 |
| description | String | 否 | 合同描述 |
| send | Boolean | 否 | 是否立即发起合同，默认true。（true：立即发起；false：保存为草稿）|
| documents | List&lt;Long&gt; | 是 | 文档ID的集合，一个合同可以包含多个文档，但一个文档只能属于一个合同 |
| expireTime | String | 否 | 合同过期时间；格式：yyyy-MM-dd HH:mm:ss。过期未结束签署，则作废，不传该参数则默认使用控制台配置的过期时间。 |
| creatorName | String | 否 | 合同创建人姓名 |
| creatorContact | String | 否 | 合同创建人手机号码 |
| tenantName | String | 否 | 发起方名称 |
| signatories | List&lt;Signatory&gt; | 否 | 签署方，如果未指定签署方，在合同签署完成后必须“合同完成”接口主动结束合同 |
| businessData | String | 否 | 用户的业务数据 |
| bizId | String | 否 | 合同的唯一标识，由调用方传入 |
| isCloudSign | Boolean | 否 | 是否公有云签署，默认私发私签 |


Signatory（签署方）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantType | TenantType | 是 | 签署方类型：COMPANY（企业），PERSONAL（个人） |
| tenantName | String | 是 | 签署方名称 |
| receiverName | String | 否 | 接收人姓名 |
| contact | String | 否 | 接收人联系方式 |
| faceAuthSign | Boolean | 否 | 是否人脸识别签署，默认不需要人脸识别（仅签署方类型为个人时有效） |
| serialNo | Integer | 是 | 签署顺序（从1开始) |
| actions | List&lt;Action&gt; | 是 | 签署动作 |
| categoryId | Long | 否 | 业务分类ID，接收方是内部企业时，可指定业务分类 |
| categoryName | String | 否 | 业务分类名称，接收方是内部企业时，可指定业务分类 |
| faceAuthWay | String | 否 | 人脸识别签署失败后的降级认证，DEFAULT（不允许），IVS（手机三要素），BANK（银行卡四要素） |

Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 否 | 签署顺序（从1开始） |
| sealId | Long | 否 | 印章ID，指定企业签章所用印章 |
| sealIds | Set&lt;Long&gt; | 否 | 印章ID列表，预签署页面指定签署位置时，限制使用的印章 |
| actionOperators | List&lt;ActionOperator&gt; | 否 | 签署人（法定代表人签字无需填写该项） |
| locations | Array[SignatoryRect] | 否 | 签署位置 |
| autoSign | Boolean | 否 | 自动签署 |
ActionOperator（签署人）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operatorName | String | 否 | 签署人姓名 |
| operatorContact | String | 是 | 签署人联系方式 |

SignatoryRect（签署位置）

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 文档ID |
| rectType | StamperType | 是 | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章） |
| page | Integer | 否 | 签署页码，坐标指定位置时必须                                 |
| keyword | String | 否 | 关键字，关键字指定位置时必须 |
| keywordIndex | Integer | 否 | 第几个关键字,0:全部,-1:最后一个,1:第1个；默认为1 |
| offsetX | Double | 否 | X轴坐标，坐标定位时必传，关键字定位时选传 |
| offsetY | Double | 否 | Y轴坐标，坐标定位时必传，关键字定位时选传 |

**返回值**： Long 合同ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
CreateContractRequest createContractRequest = new CreateContractRequest();
createContractRequest.addDocument(24836096507689902342L);
createContractRequest.setCategoryId(2472609650768990241L);

createContractRequest.setSubject("测试合同");
createContractRequest.setCreatorName("张三");
createContractRequest.setCreatorContact("19000000000");
createContractRequest.setSend(true);

List<Signatory> signatories = new ArrayList<Signatory>();
// 发起方
Signatory signatory1 = new Signatory();
signatory1.setSerialNo(1);
signatory1.setContact("19000000000");
signatory1.setTenantType(TenantType.CORPORATE);
signatory1.setTenantName("上海契约锁网络科技有限公司");
List<Action> actions = new ArrayList<Action>();
Action action = new Action(ActionType.LP, 1);
action.setName("企业签章");
actions.add(action);
signatory1.setActions(actions);

List<SignatoryRect> rects = new ArrayList<SignatoryRect>();
SignatoryRect rect1 = new SignatoryRect();
// 指定签署位置（使用关键字形式）
rect1.setDocumentId(documentId1);
rect1.setRectType(StamperType.SEAL_CORPORATE);
rect1.setKeyword("创建时甲方签署位置");
rect1.setActionName("企业签章");
rects1.add(rect1);
signatory1.setLocations(rects1);
// 添加签署方
signatories.add(signatory1);
createContractRequest.setSignatories(signatories);

Long contractId = contractService.createContract(createContractRequest);
logger.info("创建合同成功,contractId:", contractId);
```

#### 1.1.4.2 用业务分类创建合同

**方法**：`Long createContractByCategory(CreateContractRequest request)`

**描述**：根据业务分类创建合同。业务分类中配置的模板将自动生成合同文档，同时也可使用业务分类中配置好的签署流程、签署位置、认证方式等信息。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| subject | String | 是 | 合同名称 |
| sn | String | 否 | 合同编号，对接方系统中的业务编号 |
| description | String | 否 | 合同描述 |
| categoryId | Long | 否 | 业务分类ID |
| categoryName | String | 否 | 业务分类名称，如果分类ID为空，则用此参数确定业务分类 |
| send | Boolean | 否 | 是否立即发起合同，默认true。（true：立即发起；false：保存为草稿）|
| documents | List&lt;Long&gt; | 否 | 文档ID的集合 |
| expireTime | String | 否 | 合同过期时间；格式：yyyy-MM-dd HH:mm:ss。过期未结束签署，则作废，不传该参数则默认使用控制台配置的过期时间。 |
| endTime | String | 否 | 合同终止时间；格式：yyyy-MM-dd 。系统将时间调至传入时间的23时59分59秒。 |
| creatorName | String | 否 | 合同创建人姓名 |
| creatorContact | String | 否 | 合同创建人手机号码 |
| tenantName | String | 否 | 发起方名称 |
| signatories | List&lt;Signatory&gt; | 否 | 签署方，为空时在合同签署完成后需要调用接口“封存合同”主动结束合同 |
| documentParams | List&lt;DocumentParam&gt; | 否 | 模板参数 |
| businessData | String | 否 | 用户的业务数据 |
| bizId | String | 否 | 合同的唯一标识，由调用方传入 |
| waterMarkConfigs | Array[WaterMarkContent] | 否 | 水印配置，参考WaterMarkContent |
| extraSign | Boolean | 否 | 指定位置外签署，默认为false |
| mustSign | Boolean | 否 | 允许指定位置签署，默认为true，指定位置外签署和指定位置签署两者不可同时为false |

**注意**：

>**合同文档：**如果业务分类允许用户上传文档，则用户传入的 documents 与业务分类中配置的文档都作为合同文档；如果业务分类不允许用户上传文档，只能使用业务分类中配置的文档，如果用户传入 documents则提示错误。<br>
>**参数send：**是否发起合同；如果发起，必须传入发起方的模板参数值（用documentParams来设置）；如果不发起，可以在创建合同后调用“预签署页面”接口，在预签署页面填写参数。<br>
>
>**业务分类：**业务分类通过“分类Id”和"分类名称"来指定，优先用“分类ID”指定，如果两个参数均为空，则使用“默认业务分类”。<br>
>
>**签署方：**签署方要符合业务分类中配置的规则：<br>
>
>(1) 配置中“预设”签署方：参数中的签署方与业务分类中配置的签署方必须匹配（数量、顺序、类型均匹配），此时签署动作以业务分类中配置的为准，参数中无须传签署动作；
>
>(2) 配置中“默认”签署方：以参数中传入的签署方为准，如果发起方未传审批流（即签署动作），则发起方使用业务分类中配置的审批流；
>
>**指定签署位置：**支持配置的三种方式：“必须签署可增加”（mustSign:true；extraSign:true）、“必须签署不可增加”（mustSign:true；extraSign:false）、“非必须签署”（mustSign:false；extraSign:true）。默认是必须签署不可增加。

Signatory（签署方）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantType | TenantType | 是 | 签署方类型：COMPANY（企业），PERSONAL（个人） |
| tenantName | String | 是 | 签署方名称 |
| receiverName | String | 否 | 接收人姓名 |
| contact | String | 否 | 接收人联系方式 |
| serialNo | Integer | 是 | 签署顺序（从1开始) |
| actions | List&lt;Action&gt; | 否 | 签署动作,业务分类非预设且签署方为发起方时，使用用户传入的签署动作，其余情况使用业务分类的配置 |
| categoryId | Long | 否 | 业务分类ID，接收方是内部企业时，可指定业务分类 |
| categoryName | String | 否 | 业务分类名称，接收方是内部企业时，可指定业务分类 |
| faceAuthSign | Boolean | 否 | 是否人脸识别签署，默认不需要人脸识别（仅签署方类型为个人时有效） |
| remind | Boolean | 否 | 是否发送消息提醒，默认为true |
| cardNo | String | 否 | 身份证号，用于指定个人用户认证时的身份证号 |

DocumentParam（模板参数）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String | 是 | 参数名称 |
| value | String | 是 | 参数值 |

**当使用HTML模板时，参数value注意事项如下：**

>参数类型是单行文本时，value大小不超过300;<br>
>参数类型是日期时，value格式为：yyyy-MM-dd，如：2019-06-04;<br>
>参数类型是身份证号，value只能是15或18位的数字或字母，如：123456789123456789;<br>
>参数类型是单选，value只能是单选的选项,如：val1;<br>
>参数类型是多选，value只能是多选的选项，多个value用逗号隔开，如：val1,val2;<br>
>参数类型是表格，可以动态添加行，暂不支持动态添加列，value格式是一维数组，数组的每项对应表格的每行，每行格式是key-value格式，key是表格每列的列名如：[{\"key1\":\"1\",\"key2\":\"2\",\"key3\":\"3\",\"key4\":\"4\"},{\"key1\":\"5\",\"key2\":\"6\",\"key3\":\"7\",\"key4\":\"8\"}]。

Action（签署动作/签署节点）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 是 | 签署顺序（从1开始） |
| sealId | Long | 否 | 印章ID，指定企业签章所用印章 |
| sealIds | String | 否 | 指定印章，格式：[123123123213,123213213213] |
| autoSign | Boolean | 否 | 是否自动签署，默认不自动签署 |
| actionOperators | List&lt;ActionOperator&gt; | 否 | 签署人（法定代表人签字无需填写该项） |
| locations | List&lt;SignatoryRect&gt; | 否 | 签署位置 |

ActionOperator（签署人）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operatorName | String | 否 | 签署人姓名 |
| operatorContact | String | 是 | 签署人联系方式 |

SignatoryRect（签署位置）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 合同文档ID |
| rectType | String | 是 | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章） |
| page | Integer | 否 | 签署页码，坐标指定位置时必须 |
| keyword | String | 否 | 关键字，关键字指定位置时必须 |
| keywordIndex | Integer | 否 | 第几个关键字,0:全部,-1:最后一个,其他:第keyIndex个,默认为1 |
| offsetX | Double | 否 | X轴坐标，坐标定位时必传，关键字定位时选传 |
| offsetY | Double | 否 | Y轴坐标，坐标定位时必传，关键字定位时选传 |

WaterMarkContent（水印配置）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 水印类型：TEXT(文本),IMAGE(图片) |
| content | String | 水印类型为文本时必填 | 文字内容 |
| imageBytes | byte[] | 水印类型为图片时必填 | 图片水印。只支持png格式 |

**返回值**： Long 合同ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
CreateContractRequest createContractRequest = new CreateContractRequest();
createContractRequest.setCategoryId(2472609650768990241L);
createContractRequest.setEndTime(TimeUtils.format(TimeUtils.after(new Date(), 1), TimeUtils.STANDARD_PATTERN));
createContractRequest.setSend(true);
createContractRequest.setMustSign(true);
createContractRequest.setExtraSign(false);

createContractRequest.setSubject("测试合同");
createContractRequest.setCreatorName("张三");
createContractRequest.setCreatorContact("19000000000");
// createContractRequest.setSend(false);

List<Signatory> signatories = new ArrayList<Signatory>();
// 发起方
Signatory signatory1 = new Signatory();
signatory1.setContact("19000000000");
signatory1.setTenantType(TenantType.CORPORATE);
signatory1.setTenantName("上海契约锁网络科技有限公司");

// 添加签署方
signatories.add(signatory1);
createContractRequest.setSignatories(signatories);

// 设置模板参数
DocumentParam param1 = new DocumentParam("param1", "val1");
DocumentParam param2 = new DocumentParam("param2", "val2");
createContractRequest.addDocumentParam(param1);
createContractRequest.addDocumentParam(param2);

Long contractId = contractService.createContractByCategory(createContractRequest);
logger.info("创建合同成功,contractId:", contractId);
```

#### 1.1.4.3 编辑合同

**方法**：`Long editContract(CreateContractRequest request)`

**描述**：编辑草稿状态的合同，保留旧的模版参数。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| id | Long | 是 | 合同ID |
| subject | String | 是 | 合同名称 |
| documents | List&lt;Long&gt; | 是 | 文档ID的集合，一个合同可以包含多个文档，但一个文档只能属于一个合同 |
| expireTime | Date | 否 | 合同过期时间；过期未结束签署，则作废，不传该参数则默认使用控制台配置的过期时间。 |
| sn | String | 否 | 合同编号，对接方系统中的业务编号 |
| creatorName | String | 否 | 合同创建人姓名 |
| creatorContact | String | 否 | 合同创建人手机号码 |
| tenantName | String | 否 | 发起方名称 |
| signatories | List&lt;Signatory&gt; | 否 | 签署方 |
| send | Boolean | 否 | 是否立即发起合同，默认true。（true：立即发起；false：保存为草稿）|
| businessData | String | 否 | 用户的业务数据 |
| bizId | String | 否 | 合同的唯一标识，由调用方传入 |

Signatory（签署方）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantType | TenantType | 是 | 签署方类型：COMPANY（企业），PERSONAL（个人） |
| tenantName | String | 是 | 签署方名称 |
| receiverName | String | 否 | 接收人姓名 |
| contact | String | 否 | 接收人联系方式 |
| serialNo | Integer | 是 | 签署顺序（从1开始) |
| actions | List&lt;Action&gt; | 是 | 签署动作 |
Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 否 | 签署顺序（从1开始） |
| actionOperators | List&lt;ActionOperator&gt; | 否 | 签署人（法定代表人签字无需填写该项） |

ActionOperator（签署人）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operatorName | String | 否 | 签署人姓名 |
| operatorContact | String | 是 | 签署人联系方式 |

SignatoryRect（签署位置）

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 文档Id |
| rectType | StamperType | 是 | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章） |
| page | Integer | 否  按坐标指定位置时：必传| 签署位置所在页数  |
| keyword | String | 否  按关键字指定位置时：必传 | 关键字  |
| offsetX | Double | 否  按坐标时：必传 按关键字时：选传 | X轴坐标  |
| offsetY | Double | 否  按坐标时：必传 按关键字时：选传 | Y轴坐标  |

**返回值**： Long 合同ID

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
CreateContractRequest createContractRequest = new CreateContractRequest();
createContractRequest.addDocument(24836096507689902342L);
createContractRequest.setCategoryId(2472609650768990241L);
createContractRequest.setId(2472609650768990281L);

createContractRequest.setSubject("测试合同");
createContractRequest.setCreatorName("张三");
createContractRequest.setCreatorContact("19000000000");
createContractRequest.setSend(true);

List<Signatory> signatories = new ArrayList<Signatory>();
// 发起方
Signatory signatory1 = new Signatory();
signatory1.setSerialNo(1);
signatory1.setContact("19000000000");
signatory1.setSponsor(true);
signatory1.setTenantType(TenantType.CORPORATE);
signatory1.setTenantName("上海契约锁网络科技有限公司");
List<Action> actions = new ArrayList<Action>();
Action action = new Action(ActionType.LP, 1);
action.setName("企业签章");
actions.add(action);
signatory1.setActions(actions);

List<SignatoryRect> rects = new ArrayList<SignatoryRect>();
SignatoryRect rect1 = new SignatoryRect();
// 指定签署位置（使用关键字形式）
rect1.setDocumentId(documentId1);
rect1.setRectType(StamperType.SEAL_CORPORATE);
rect1.setKeyword("创建时甲方签署位置");
rect1.setActionName("企业签章");
rects1.add(rect1);
signatory1.setLocations(rects1);

// 添加签署方
signatories.add(signatory1);
createContractRequest.setSignatories(signatories);
Long contractId = contractService.editContract(createContractRequest);
logger.info("创建合同成功,contractId:", contractId);
```


#### 1.1.4.4 预签署页面

**方法**：`String presignUrl(Long contractId)`

**描述**：获取预签署页面链接，用户可打开页面，通过拖动左侧签章来指定签署位置，也可以填写模板参数（如果需要填写模板参数），通过点击“保存草稿”按钮来保存签署位置和模板参数；也可通过“确定”按钮来发起合同。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |

**返回值**：String 预签署连接

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Long contractId = 22326096507689902846L
String presignUrl = contractService.presignUrl(contractId);
logger.info("预签署链接:", presignUrl);
```

#### 1.1.4.5 发起合同

**方法**：`void send(SendContractReuest request)`

**描述**：调用此接口来发起草稿状态的合同。合同发起后，签署方将收到签署通知签署合同。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |

**返回值**： 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
SendContractReuest request = new SendContractReuest();
request.setContractId(2563672888920076330L);
contractService.send(request);
logger.info("合同发起成功");
```
#### 1.1.4.6 草稿合同填参

**方法**：void fillParams(DocumentFillParam request)

**描述**：调用此接口用于填写草稿状态的合同参数，同时保留旧的参数。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| documentParams | List&lt;FillDocumentParam&gt; | 否 | 合同文档参数 |
FillDocumentParam
| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String | 是 | 参数名称 |
| value | String | 是 | 参数值|
**返回值**： 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
DocumentFillParam fillParam = new DocumentFillParam();
fillParam.setContractId(2611141940252005036L);

List<FillDocumentParam> documentParams = new ArrayList<FillDocumentParam>();
FillDocumentParam param1 = new FillDocumentParam();
param1.setName("身份证号");
param1.setValue("12345678901234567");
documentParams.add(param1);
FillDocumentParam param2 = new FillDocumentParam();
param2.setName("乙方姓名");
param2.setValue("二哈");
documentParams.add(param2);

fillParam.setDocumentParams(documentParams);
contractService.fillParams(fillParam);
logger.info("填参完成");
```

### 1.1.5 删除合同文件

#### 1.1.5.1 解绑文件
**方法**：`unBind(DocumentDelete request)`

**描述**：调用此接口可以删除合同文档。合同必须是未签署状态（草稿、签署中或拟定中状态），非草稿状态合同至少保留一个文件。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| documentIds | List&lt;Long&gt; | 是 | 文档ID集合 |

**返回值**： 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
DocumentService documentService = new DocumentServiceImpl(client);
//方法调用
DocumentDelete docDelete = new DocumentDelete();
docDelete.setContractId(2609004396768080003L);
docDelete.setDocumentIds(Arrays.asList(2609004396596113534L));
documentService.unBind(docDelete);
logger.info("删除成功");
```

## 1.2 合同签署

### 初始化

**描述：**合同签署接口的初始化方法。

接口：`net.qiyuesuo.sdk.api.SignService`

实现类：`net.qiyuesuo.sdk.impl.SignServiceImpl`

构造函数：`SignServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

示例

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(sdkClient);
```

### 1.2.1 静默签署

#### 1.2.1.1 运营方签署

**方法**：`void signByPlatform(PlatformSignRequest request)`

**描述**：运营方可以调用该接口直接签署运营方公章，无需展现签署页面。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 是 | 合同ID |
| stampers | List&lt;Stamper&gt; | 否 | 签署位置，为空时签署不可见签名，参考【Stamper】 |
| noSignAllKeyword | Boolean | 否 | 不签署所有关键字位置，默认为true，即只签署第1个关键字 |

Stamper：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 文档ID |
| type | StamperType | 是 | 签署类型：SEAL_CORPORATE（公章）,TIMESTAMP（时间戳）,ACROSS_PAGE（骑缝章） |
| sealId | String | 否 | 印章ID |
| page | int | 否 | 签署页码，从1开始，坐标定位时必传 |
| allPage | boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 横坐标，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 纵坐标，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

印章位置说明：

>印章可见时，需要指定印章的坐标位置，坐标位置有以下两种表现方式：<br><br>
>1、用关键字确定坐标：<br>
keyword：关键字。<br>
x：横坐标偏移量；默认合同页的宽为1，所以取值范围是(-1, 1)。<br>
y：纵坐标偏移量；默认合同页的高为1，所有取值范围是(-1, 1)。<br>
>找到keyword的坐标(X, Y)，再加上偏移量(x, y)，最终得到的坐标是(X+x, Y+y)。<br>
坐标原点是合同页的左下角，坐标是指印章图片的左下角的坐标。<br><br>
>2、直接确定坐标：<br>
page：印章所在页码；从1开始。<br>
x：横坐标；默认合同页的宽为1，所以取值范围是(0, 1)。<br>
y：纵坐标；默认合同页的高为1，所以取值范围是(0, 1)。<br>
>由page确定页码，由(x, y)确定坐标。<br>
坐标原点是合同页的左下角，坐标是指印章图片的左下角的坐标。

**返回值**： 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
PlatformSignRequest request = new PlatformSignRequest();
request.setContractId(24836096507689902342L);

// 由关键字确定签署位置
Stamper stamper2 = new Stamper();
stamper2.setType(StamperType.SEAL_CORPORATE);
stamper2.setDocumentId(22326096507689902846L);
stamper2.setKeyword("创建时甲方签署位置");
// stamper2.setPage(1); // 页码
stamper2.setX(0.5f); // 横坐标偏移量
stamper2.setY(0.5f); // 纵坐标偏移量
stamper2.setSealId(2462591201580826634L);

List<Stamper> stampers = new ArrayList<Stamper>();
stampers.add(stamper2);
request.setStampers(stampers);
signService.signByPlatform(request);
logger.info("平台完成签署！");
```

#### 1.2.1.2 公司公章签署

**方法**：`void signByCompany(CompanySignRequest request)`

**描述：**运营方可以调用该接口直接签署公司公章，无需展现签署页面（只能签署内部企业的公章）。

**参数**：


| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 是 | 合同ID |
| tenantId | Long | 否 | 公司ID |
| tenantName | String | 是 | 公司名称 |
| stampers | List&lt;Stamper&gt; | 否 | 签署位置，为空时签署不可见签名，参考【Stamper】 |
| noSignAllKeyword | Boolean | 否 | 不签署所有关键字位置，默认为true，即只签署第1个关键字 |
| useDefaultSeal | Boolean | 否 | 是否使用默认印章签署，默认true（仅在已设置签署位置未设置印章的情况下生效） |

Stamper：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 文档ID |
| type | StamperType | 是 | 签章类型：SEAL_CORPORATE（公章）,ACROSS_PAGE（骑缝章）,TIMESTAMP（时间戳） |
| sealId | String | 是 | 印章ID |
| page | int | 否 | 签署页码，从1开始，坐标定位时必传 |
| allPage | boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 横坐标，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 纵坐标，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
CompanySignRequest request = new CompanySignRequest();
request.setContractId(2372515150140903467L);

// 由关键字确定签署位置
Stamper stamper2 = new Stamper();
stamper2.setType(StamperType.SEAL_CORPORATE);
stamper2.setDocumentId(documentId1);
stamper2.setKeyword("创建时甲方签署位置");
// stamper2.setPage(1); // 页码
stamper2.setX(0.5f); // 横坐标偏移量
stamper2.setY(0.5f); // 纵坐标偏移量
stamper2.setSealId(2483515150140903439L);

List<Stamper> stampers = new ArrayList<Stamper>();
stampers.add(stamper2);
request.setStampers(stampers);
request.setTenantId(2483515144440844298L);
signService.signByCompany(request);
logger.info("公司完成签署！");
```

#### 1.2.1.3 法定代表人签署

**方法**：`void signByLp(CompanySignRequest signRequest)`

**描述**：运营方可以调用该接口直接签署公司法人章，无需展现签署页面（只能签署内部企业的法人章）。法人章签署的前提是公司法人已认证，且系统中已维护法人章。

**参数：**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| tenantId | Long | 否 | 公司ID |
| tenantName | String | 是 | 公司名称 |
| stampers | List&lt;Stamper&gt; | 是| 签署位置，详情参考Stamper。 |

Stamper：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 文档ID |
| type | StamperType | 是 | 签章类型：SEAL_CORPORATE（法人章）,TIMESTAMP（时间戳） |
| page | int | 否 | 签署页码，从1开始，坐标定位时必传 |
| allPage | boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 横坐标，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 纵坐标，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
logger.info("法人章签署！");
CompanySignRequest signRequest = new CompanySignRequest();
signRequest.setContractId(contractId);
signRequest.setTenantName("上海泛微网络科技股份有限公司");
signRequest.setTenantType(TenantType.CORPORATE);
// 1、由坐标确定签署位置
List stampers = new ArrayList();
Stamper stamper1 = new Stamper();
stamper1.setType(StamperType.SEAL_CORPORATE); // 签署类型
stamper1.setDocumentId(documentId); // 合同文件ID
// stamper1.setPage(1); // 页码
stamper1.setX(0.1f); // 横坐标偏移量
stamper1.setY(0.2f); // 纵坐标偏移量
stampers.add(stamper1);
signRequest.setStampers(stampers);
signService.signByLp(signRequest);	
```

#### 1.2.1.4 个人签署

**方法**：`void signByPerson(PersonalSignRequest request)`

**描述**：运营方可以调用该接口直接签署个人签名，无需展现签署页面（只能签署内部员工）。<br>
个人签署时必须有个人签名：（1）用户提前在契约锁中维护好签名；（2）通过接口中的sealStr指定签名；（3）指定接口中的generatePersonSeal为true，此时契约锁检测到用户无签名时会自动生成一个签名。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| tenantName | String | 是 | 签署人姓名 |
| contact | String | 是 | 签署人联系方式，手机号码或邮箱地址 |
| cardNo | String | 否 | 签署人身份证号码 |
| generatePersonSeal | Boolean | 否 | 是否为用户自动生成个人签名，已存在则不生成，默认不自动生成 |
| stampers | List&lt;Stamper&gt; | 是 | 签署位置，详情参考Stamper |

Stamper

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 文档ID |
| type | StamperType | 是 | 签章类型：SEAL_PERSONAL（个人签名）,TIMESTAMP（时间戳） |
| sealStr | String | 否 | 签名图片，base64编码 |
| page | int | 否 | 签署页码，从1开始 |
| allPage | boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 横坐标，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 纵坐标，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

**返回值**： 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
PersonalSignRequest signRequest = new PersonalSignRequest();
signRequest.setContractId(22326096507689902846L);
// 1、由坐标确定签署位置
List stampers = new ArrayList();
Stamper stamper1 = new Stamper();
stamper1.setType(StamperType.SEAL_PERSONAL); // 签署类型
stamper1.setDocumentId(documentId1); // 合同文件ID
stamper1.setKeyword("创建时甲方签署位置");
//stamper1.setPage(1); // 页码
//stamper1.setX(0.1f); // 横坐标
//stamper1.setY(0.2f); // 纵坐标
stampers.add(stamper1);
signRequest.setStampers(stampers);
signRequest.setContractId(contractId);
signRequest.setTenantName("张三");
signRequest.setContact("19000000000");
signService.signByPerson(signRequest);
logger.info("个人完成签署！");
```
#### 1.2.1.5 合同审批

**方法**：`void audit(CompanyAuditRequest request)`

**描述**：调用该接口对签署方下的审批节点进行审批。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| tenantName | String | 否 | 公司名称，默认发起方公司 |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
CompanyAuditRequest request = new CompanyAuditRequest();
request.setContractId(2534936696674701362L);
request.setTenantName("上海泛微网络科技股份有限公司");
signService.audit(request);
```

#### 1.2.1.6 公司用户无外观签署

**方法**：`void signByCompanyWithoutAppearance(CompanyAppearanceSignRequest request)`

**描述**：为合同加盖无外观签名。使用场景：运营方已在合同文档加盖印章图片，需要契约锁在对应位置添加电子签名。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 是 | 合同ID |
| tenantId | Long | 否 | 签署方ID |
| tenantName | String | 是 | 签署方姓名 |
| stampers | List&lt;AppearanceStamper&gt; | 是 | 签署位置，参考【AppearanceStamper】 |

AppearanceStamper：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 文档ID |
| width | int | 是 | 无外观占位图宽度,取值1~200 |
| height | int | 是 | 无外观占位图宽度,取值1~200 |
| page | int | 否 | 签署页码，从1开始，坐标定位时必传 |
| allPage | boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 横坐标，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 纵坐标，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
CompanyAppearanceSignRequest request=new CompanyAppearanceSignRequest();
request.setContractId(2563929748124012744L);
request.setTenantName("上海契约锁网络科技有限公司");
AppearanceStamper stamper=new AppearanceStamper();
stamper.setDocumentId(2563929659561283780L);
stamper.setHeight(20);
stamper.setWidth(20);
stamper.setPage(1);
stamper.setX(0f);
stamper.setY(0f);
request.addStamper(stamper);
signService.signByCompanyWithoutAppearance(request);
```

#### 1.2.1.7 个人用户无外观签署

**方法**：`void signByPersonWithoutAppearance(PersonalAppearanceSignRequest request)`

**描述**：为合同加盖无外观签名。使用场景：运营方已在合同文档加盖印章图片，需要契约锁在对应位置添加电子签名。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| tenantName | String | 是 | 签署人姓名 |
| contact | String | 是 | 签署人联系方式，手机号码或邮箱地址 |
| cardNo | String | 否 | 签署人身份证号码 |
| stampers | List&lt;AppearanceStamper&gt; | 是 | 签署信息，AppearanceStamper。|

AppearanceStamper：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 文档ID |
| width | int | 是 | 无外观占位图宽度,取值1~200 |
| height | int | 是 | 无外观占位图宽度,取值1~200 |
| page | int | 否 | 签署页码，从1开始，坐标定位时必传 |
| allPage | boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 横坐标，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 纵坐标，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
PersonalAppearanceSignRequest request=new PersonalAppearanceSignRequest();
request.setContractId(2563928333624348773L);
request.setTenantName("张大壮");
request.setContact("12312378910");
AppearanceStamper stamper=new AppearanceStamper();
stamper.setDocumentId(2563928307317674081L);
stamper.setHeight(20);
stamper.setWidth(20);
stamper.setPage(1);
stamper.setX(0f);
stamper.setY(0f);
request.addStamper(stamper);	
signService.signByPersonWithoutAppearance(request);
```

### 1.2.2 页面签署

#### 1.2.2.1 合同签署页面

**方法**：`String signUrl(SignUrlRequest request)`

**描述**：对于签署中的合同，可以调用此接口来获取签署页面链接，用户可以打开签署链接签署合同。如果传入的签署方不存在，则自动创建签署方。此链接的有效期为30分钟。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| tenantType | String | 是 | 签署方类型：CORPORATE（平台企业）,COMPANY（企业）,PERSONAL（个人） |
| tenantId | Long | 否 | 签署方ID（公司ID/个人ID），和tenantName不能同时为空 |
| tenantName | String | 否 | 签署方名称，和tenantId不能同时为空 |
| contact | String | 否 | 签署方的联系方式，签署方为个人时必须 |
| cardNo | String | 否 | 证件号码：个人/公司证件号 |
| canLpSign | String | 否 | 是否可以同时签署法人章：1（是），0（否） |
| actions | List&lt;Action&gt; | 否 | 签署动作,添加发起方时签署动作必填 |
| expireTime | Integer | 否 | 链接过期时间，取值范围：5分钟 - 3天，默认30分钟，单位（秒） |
| callbackParam | String | 否 | 回调参数，用户签署完成后或退回合同，将该参数通过回调函数返回（旧参数，建议使用业务分类中配置的回调） |
| callbackHeader | String | 否 | 回调Header参数，Json格式（旧参数，建议使用业务分类中配置的回调） |
| callbackPage | String | 否 | 回调页面 |

> **contact参数说明：**
>
> 1、签署方类型是“个人”时必传，此时页面默认是个人的登录态。
>
> 2、签署方类型是“公司”时，如果contact为空，则页面默认是公司的登录态；如果contact不为空，则页面默认为个人登录态。

Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 是 | 签署顺序（从1开始） |
| sealId | String | 否 | 印章ID，指定企业签章所用印章 |
| sealIds | String | 否 | 指定印章，格式：[123123123213,123213213213] |
| locations | List&lt;SignatoryRect&gt; | 否 | 签署位置 |
| actionOperators | Array[ActionOperator] | 否 | 签署动作操作人 |

SignatoryRect（签署位置）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 合同文档ID |
| rectType | String | 是 | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章） |
| page | Integer | 否  按坐标指定位置时：必传| 签署页码，坐标指定位置时必须 |
| keyword | String | 否  按关键字指定位置时：必传 | 关键字，关键字指定位置时必须 |
| keywordIndex | Integer | 否 | 第几个关键字,0:全部,-1:最后一个,其他:第keyIndex个,默认为1 |
| offsetX | Double | 否  按坐标时：必传 按关键字时：选传 | X轴坐标，坐标定位时必传，关键字定位时选传 |
| offsetY | Double | 否  按坐标时：必传 按关键字时：选传 | Y轴坐标，坐标定位时必传，关键字定位时选传 |

ActionOperator（签署动作操作人）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operatorName | String | 否 | 签署人姓名 |
| operatorContact | String | 是 | 签署人联系方式 |

**返回值**：String 签署合同的页面链接；链接有效期30分钟 

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
SignUrlRequest request = new SignUrlRequest();
request.setContractId(2612149633882444111L);
request.setTenantType(TenantType.PERSONAL);
request.setTenantName("张三");
request.setContact("19000000000");
request.setContractId(2612149633882444194L);
//回调页面
request.setCallbackPage("https://www.baidu.com/");

String signurl = signService.signUrl(request);
logger.info("签署地址：", signurl);
```

### 1.2.3 签署通知

#### 1.2.3.1 发送签署通知

**方法**：`void signSms(SignUrlRequest request)`

**描述**：对于签署中的合同，可以调用此接口来通知用户签署合同。如果传入的签署方不存在，则自动创建签署方。<br><br>

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| tenantType | String | 是 | 签署方类型：CORPORATE（平台企业）,COMPANY（企业）,PERSONAL（个人） |
| tenantId | Long | 否 | 签署方ID（公司ID/个人ID），和tenantName不能同时为空 |
| tenantName | String | 否 | 签署方名称，和tenantId不能同时为空 |
| contact | String | 是 | 签署方的联系方式 |
| actions | List&lt;Action&gt; | 否 | 签署动作,添加发起方时签署动作必填 |
| locations | List&lt;SignatoryRect> | 否 | 签署位置，会覆盖已有的签署位置 |

Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 是 | 签署顺序（从1开始） |
| sealId | String | 否 | 印章ID，指定企业签章所用印章 |
| sealIds | String | 否 | 指定印章，格式：[123123123213,123213213213] |
| locations | List&lt;SignatoryRect&gt; | 否 | 签署位置 |
| actionOperators | List&lt;ActionOperator&gt; | 否 | 签署动作操作人 |

签署位置（SignatoryRect）

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 文档ID |
| rectType | String | 是 | 签章类型； SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章）, TIMESTAMP（时间戳）, ACROSS_PAGE（骑缝章） |
| page | Integer | 否 | 签署页码，从1开始，坐标定位时必传 |
| keyword | String | 否 | 关键字，关键字定位时必传 |
| offsetX | Double | 否 | 横坐标，坐标定位时必传，关键字定位时选传 |
| offsetY | Double | 否 | 纵坐标，坐标定位时必传，关键字定位时选传 |

ActionOperator（签署动作操作人）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operatorName | String | 否 | 签署人姓名 |
| operatorContact | String | 是 | 签署人联系方式 |

**返回值**：无

**示例**
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
signUrlRequest.setTenantType(TenantType.PERSONAL);
signUrlRequest.setTenantName("张三");
signUrlRequest.setContact("19000000000");
signUrlRequest.setContractId(contractId);

String signurl = signService.signSms(signUrlRequest);
logger.info("签署地址：", signurl);
```


#### 1.2.3.2 合同催签

**方法**：`void notify(Long contractId)`

**描述**：短信通知合同接收方签署合同，通知对象包括接收方经办人和签署中的人。合同状态必须为：“填参中”，“签署中”，“作废中”。

**参数**：

| 名称        | 类型 | 是否必须 | 描述             |
| ----------- | ---- | -------- | ---------------- |
| contractId | Long | 是       | 合同ID |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
contractService.notify(2555956569832575093L);
```

#### 1.2.3.3 签署方催签

**方法**：`void pressOperator(Long signatoryId)`

**描述**：短信通知签署方签署合同，通知对象为签署方经办人。签署方状态必须为“签署中”。签署方状态必须为：“填参中”，“签署中”，“作废中”。

**参数**：

| 名称        | 类型 | 是否必须 | 描述                                          |
| ----------- | ---- | -------- | --------------------------------------------- |
| signatoryId | Long | 是       | 签署方ID，合同详情中的Signatory（签署方）的id |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Long signatoryId = 2422044802991313367L;
contractService.pressOperator(signatoryId);
```


#### 1.2.3.4 签署节点催签

**方法**：`void press(Long actionId)`

**描述**：短信通知签署节点的操作人签署合同，通知对象为签署节点的操作人。签署节点状态必须为“签署中”。签署节点状态必须为：“填参中”，“签署中”（签署节点中“签署中”和“作废中”状态均为“签署中”）。

**参数**：

| 名称     | 类型 | 是否必须 | 描述                                                         |
| -------- | ---- | -------- | ------------------------------------------------------------ |
| actionId | Long | 是       | 签署节点ID，合同详情中的Signatory（签署方）下的Action（签署节点）的id |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Long actionId = 2422044802991313000L;
contractService.press(actionId);
```


### 1.2.4 签署密码校验

**方法**：`boolean checkPassword(SignCheck signCheck)`

**描述**：校验用户的签署密码是否正确。要求在契约锁已维护签署密码的前提下，才能调用此接口。使用场景：可以在内部员工签署时调用此接口来进一步校验。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile | String | 是 | 用户手机号 |
| signPassword | String | 是 | 签署密码 |

**返回值**：boolean 签署密码是否正确

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SignService signService = new SignServiceImpl(client);
//方法调用
SignCheck signCheck = new SignCheck();
signCheck.setMobile("18462");
signCheck.setSignPassword("123456");
signService.checkPassword(signCheck);
```



## 1.3 结束合同

### 初始化

**描述：**合同完成接口的初始化方法。

接口：`net.qiyuesuo.sdk.api.ContractService`

实现类：`net.qiyuesuo.sdk.impl.ContractServiceImpl`

构造函数：`ContractServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

示例
```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(sdkClient);
```

### 1.3.1 封存合同

**方法**：`void complete(Long contractId)`

**描述**：完成指定合同，合同完成后不能再签署。条件：所有签署方已签署完成。使用场景：创建时未指定签署方的合同，在签署方签署完成后，需要调用此接口来完成合同。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Long contractId = 2488573272094023691L;
contractService.complete(contractId);
logger.info("合同封存完成");
```

### 1.3.2 强制结束合同

#### 1.3.2.1 强制结束合同

**方法**：` void forceFinish(ContractFinish contractFinish) `

**描述**：强制结束签署中的合同，结束后合同不能再签署。使用场景：如果运营方想要保证合同的有效性，又想立即结束合同，可调用此接口强制结束合同；例：一份合同发起且发起方签署后，接收方想要在线下签署合同，则可调用此接口来实现。

**参数:**


| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId| Long | 是 | 合同ID |
| companyName| String | 是 | 公司名称 |
| mobile| String | 是 | 用户手机号 |
| finishReason| String | 否 | 强制结束原因 |
| attachment| String | 否 | 附件列表，即强制结束合同的说明文档 |

**参数说明：**

attachment为附件列表，格式如下:

[{"id":"2495903120714977622","pages":1,"title":"附件1"},{"id":"2495903120714977621","pages":1,"title":"附件2"}]。

其中id为合同文件ID，可通过接口“用户文件创建合同文档”生成，pages为文件页数，title为文件名称。

**返回值**：
无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
ContractFinish record = new ContractFinish();
record.setCompanyName("南京市龙眠大道信息技术有限公司（内部）");
record.setContractId(2525520378737487874L);
record.setFinishReason("接口强制结束测试 002");
record.setMobile("158****5001");
record.setAttachment("[{\"id\":\"2525516910157132273\",\"pages\":1,\"title\":\"附件1\"},{\"id\":\"2525517247035241029\",\"pages\":2,\"title\":\"附件2\"}]");
contractService.forceFinish(record);
```


#### 1.3.2.2 修改说明文档

**方法**：` ContractFinish forceFinishAddAttachement(Long contractId, String attachment) `

**描述**：合同在强制结束时，可以提交附件（说明文档）来表明强制结束的原因，调用此接口可以修改附件。

**参数:**


| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId| Long | 是 | 合同ID |
| attachment| String | 是 | 附件列表 |

**参数说明：**

attachment为附件列表，格式如下:

[{"id":"2495903120714977622","pages":1,"title":"附件1"},{"id":"2495903120714977621","pages":1,"title":"附件2"}]。

其中id为合同文件ID，可通过接口“用户文件创建合同文档”生成，pages为文件页数，title为文件名称。



**返回值**：

ContractFinish 参见1.1.10数据结构

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Long contractId = 2525520378737487874L;
String attachment = "[{\"id\":\"2525516910157132273\",\"pages\":1,\"title\":\"附件1\"},{\"id\":\"2525517247035241029\",\"pages\":2,\"title\":\"附件2\"}]";
ContractFinish finsih = contractService.forceFinishAddAttachement(contractId, attachment);

```

### 1.3.3 作废合同

**方法**：`void cencelContract(Long contractId, Long sealId, String reason, Boolean removeContract)`

**描述**：调用此接口对已完成的合同发起作废，生成作废文件，并代发起方签署作废文件（在所有合同签署方签署作废文件后，合同作废完成）。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| sealId | Long | 是 | 发起方的印章ID，用于签署作废声明 |
| reason | String | 否 | 作废原因或说明 |
| removeContract | Boolean | 否 | 作废成功后是否删除原合同，默认为false |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
contractService.cencelContract(2488573272094023691L, 2488566967564566606L, "作废测试", false);
logger.ingo("作废合同成功");
```

### 1.3.4 撤回合同

**方法**：`void recallContract(Long contractId, String reason)`

**描述**：撤回“签署中”或“指定中”的合同。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| reason | String | 否 | 作废原因或说明 |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
contractService.recallContract(2488649326097261399L, "撤回合同");
logger.info("撤回合同成功");
```


### 1.3.5 删除合同

**方法**：`void delete(Long contractId)`

**描述**：删除合同，只能删除“草稿”状态下的合同。

**参数**：

| 名称        | 类型 | 是否必须 | 描述             |
| ----------- | ---- | -------- | ---------------- |
| contractId | Long | 是 | 合同ID |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
contractService.delete(2568343002102116471l);
```

### 1.3.6 设置合同终止时间

**方法**：`void contractEndTime(Long contractId, String endTime)`

**描述**：设置合同终止时间，只能对已完成或强制结束的合同操作.终止时间必须在今天之后。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID与业务ID不能都为空 | 合同Id |
| bizId | String | 合同ID与业务ID不能都为空 | 业务ID |
| endtime | String | 是 | 合同终止时间，格式：yyyy-MM-dd |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
contractService.contractEndTime(2602464478822126072l, TimeUtils.format(TimeUtils.after(new Date(), 1)));
```

## 1.4 查看、下载合同

### 初始化

接口：`net.qiyuesuo.sdk.api.ContractService`

实现类：`net.qiyuesuo.sdk.impl.ContractServiceImpl`

构造函数：`ContractServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

示例
```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(sdkClient);
```

### 1.4.1 查看合同

#### 1.4.1.1 合同详情

**方法**：`ContractDetail detail(Long contractId)`

**描述**：根据已有的合同ID，查询该合同的详细信息。

**参数**：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |

**返回值**： ContractDetail 合同详情，参照Contract。

Contract：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | String | 合同ID |
| subject | String | 合同主题 |
| description |String | 描述 |
| ordinal | Boolean | 是否顺序签署 |
| status | String | 合同状态：DRAFT（草稿），SIGNING（签署中），COMPLETE（已完成），REJECTED(已退回)，RECALLED(已撤回)，EXPIRED(已过期)，FILLING(拟定中)，TERMINATING(作废确认中)，TERMINATED(已作废)，DELETE(已删除)，FINISHED(已完成) |
| categoryId | Long | 合同分类 |
| categoryName | String | 合同分类名称 |
| creatorType | String | 合同具体发起人类型,个人PERSONAL/平台CORPORATE |
| creatorId | Long | 合同具体发起人ID |
| creatorName | String | 创建人名称 |
| tenantType| String | 合同发起方类型:CORPORATE（平台方）,COMPANY（外部企业）,INNER_COMPANY（内部企业）,PERSONAL/个人 |
| tenantId | Long | 合同发起方ID |
| tenantName | String | 发起方名称 |
| updateTime | String | 合同最后更新时间；格式yyyy-MM-dd HH:mm:ss |
| expireTime | String | 合同过期时间；格式yyyy-MM-dd HH:mm:ss |
| createTime | String | 合同创建时间；格式yyyy-MM-dd HH:mm:ss |
| comments | String | 合同拒绝/撤回原因 |
| sn | String | 合同编号 |
| documents | Array[Document] | 文档信息；参照Document |
| signatories | Array[Signatory] | 签署方；参照Signatory |
| businessData | String | 用户的业务数据 |
| signCode | Integer | 签署code |
| leftPrintCount | Integer | 剩余打印次数 |

Signatory：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | Long | 签约主体ID |
| tenantId | String | 签署方ID |
| tenantType | String | 签署方类型：CORPORATE（平台企业）,INTERNAL_CORPORATE（内部企业）,EXTERNAL_CORPORATE（外部企业）,INTERNAL_PERSONAL（内部个人）,EXTERNAL_PERSONAL（外部个人） |
| tenantName | String | 签署方名称 |
| receiverType | String | 接收人类型:CORPORATE(平台方),COMPANY(平台外部企业),INNER_COMPANY(平台企业（内部）),PERSONAL(个人) |
| receiverId | Long | 接收人ID，即可以转发合同的人 |
| receiverName | String | 接收人名称 |
| contact | String | 接收人联系方式 |
| serialNo | Integer | 签署顺序 |
| status | Boolean | 签署状态；DRAFT（草稿），SIGNING（签署中），COMPLETE（已完成） |
| sponsor | Boolean | 是否是发起方 |
| remind | Boolean | 是否发送信息提醒 |
| createTime | String | 创建时间；格式yyyy-MM-dd HH:mm:ss |
| updateTime | String | 更新时间；格式yyyy-MM-dd HH:mm:ss |
| receiveTime | String | 接收时间；格式yyyy-MM-dd HH:mm:ss |
| faceSign | Boolean | 是否当面签 |
| authMode | String | 业务分类中，配置的个人认证方式:NONEED("无需认证"),DEFAULT("默认配置"),FACE("人脸识别认证"),BANK("银行卡认证") |
| actions | Array[Action] | 签署动作,参考Action |

Action:

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | Long | 签署动作ID |
| type | String | 动作类型： OPERATOR("经办人签字"),LP("法定代表人签字"),CORPORATE("企业签章"),PERSONAL("个人签字"),AUDIT("审批"),FILL("合同填参"),TERMINATE_CORPORATE("合同作废"),TERMINATE_PERSONAL("合同作废") |
| name | String | 名称 |
| status | String | 状态：FILLING(填参中),FILLED(完成填参 ),WAITING(等待签署),SIGNING(签署中),SIGNED(完成),DISCARDED(废弃),REJECTED(拒绝),FINISHED(已完成，强制结束),DISABLED(已失效，仅用于展示，不记录数据库) |
| createTime | Date | 创建时间 |
| completeTime | Date | 完成时间 |
| actionOperators | Array[Operator] | 签署动作操作人，参考Operator |

Operator：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| operatorType | String | 操作人类型：EMPLOYEE,ROLE,USER,CATEGORY,COMPANY,LP_USER |
| operatorId | Long | 操作人ID |
| operatorName | String | 操作人名称 |

Document：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | String | 合同文档ID |
| title | String | 文档名称 |
| pages | int | 文档页数 |
| size | Long | 文档大小 |
| createTime | Date | 创建时间；格式yyyy-MM-dd HH:mm:ss |
| antifakeCode | String | 防伪码 |
| params | Array[DocumentParam] | 合同文档模板参数,参考DocumentParam |

DocumentParam :

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| name | String | 文档参数名称 |
| value | Stirng | 文档参数值 |
| required | boolean | 是否必填 |
| page | int | 参数所在页码 |
| offsetX | double | 参数X坐标（占页宽比） |
| offsetY | double | 参数Y坐标（占页高比） |
| signatory | Long | 参数由哪方填写，小于等于0:发起方，1~10000：接收方，长整型为对应签署方 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Long contractId = 2488573272094023691L;
ContractDetail contractDetail = contractService.detail(2488573272094023691L);
logger.info("获取的合同名称为：{}",contractDetail.getSubject);
```



#### 1.4.1.2 合同浏览页面

**方法**：`String viewUrl(ViewUrlRequest request)`

**描述**：获取合同浏览页面链接，用户可打开链接查看合同内容，此页面内只能查看不能操作。链接有效期为30分钟。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| expireTime | Integer | 否       | 链接过期时间，取值范围：5分钟 - 3天，默认30分钟，单位（秒） |
| pageType | String | 否 | 页面预览类型：DETAIL（详情页），CONTENT（合同正文），默认DETAIL |

**返回值**：String 合同浏览页面连接

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
ViewUrlRequest request = new ViewUrlRequest();
request.setContractId(2583867074148532352l);
request.setPageType(PageType.CONTENT);
//request.setExpireTime(300);
String viewUrl = contractService.viewUrl(request);
logger.info("合同查看链接：{}", viewUrl);
```


#### 1.4.1.3 合同列表

**方法**：`ContractListResponse list(ContractListRequest request)`

**描述**： 查询公司参与的合同，即查询某公司发起或签署的合同。如果不传公司信息则查询所有合同。

**参数**：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| companyId | String | 否 | 公司ID，ID与名称都不传默认查询所有合同 |
| companyName | String | 否 | 公司名称，ID与名称都不传默认查询所有合同 |
| selectOffset | String | 否 | 查询开始位置，默认为0 |
| selectLimit | String | 否 | 查询限制条数，默认为1000 |
| orderMode | String | 否 | 排序方式；ASC（升序），DESC（降序），默认为DESC |

**返回值**：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| totalCount | Int | 合同总数 |
| contractList | Array[Object] | 合同列表，参考【Contract】|

Contract：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | String | 合同ID |
| subject | String | 合同主题 |
| description |String | 描述 |
| sn | String | 合同编号 |
| ordinal | Boolean | 是否顺序签署 |
| status | String | 合同状态：DRAFT（草稿），SIGNING（签署中），COMPLETE（已完成） |
| categoryId | Long | 合同分类 |
| creatorType | String | 合同具体发起人类型,个人PERSONAL/平台CORPORATE |
| creatorId | Long | 合同具体发起人ID |
| creatorName | String | 创建人名称 |
| tenantType| String | 合同发起方类型:CORPORATE/平台方,COMPANY/平台外部企业,INNER_COMPANY/平台企业（内部）,PERSONAL/个人 |
| tenantId | Long | 合同发起方ID |
| tenantName | String | 发起方名称 |
| expireTime | String | 合同过期时间；格式yyyy-MM-dd HH:mm:ss |
| createTime | String | 合同创建时间；格式yyyy-MM-dd HH:mm:ss |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
ContractListRequest request = new ContractListRequest();
request.setCompanyId(2422042838839468036L);
request.setSelectOffset(0);
request.setSelectLimit(10);
ContractListResponse response = contractService.list(request);
logger.info("合同总数： "+response.getTotalCount());
```

#### 1.4.1.4 查询指定的签署位置

**方法**：`List<Signatory> queryLocations(Long contractId)`

**描述**：查看指定的合同签署位置。实际签署位置以坐标定位为准，关键字只作为记录保存。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |

**返回值**：


| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 返回信息 |
| result |`List<Signatory>` | 签署方信息列表(不为空即存在签署位置) |

签署方（Signatory）:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| tenantType | TenantType | 签约主体类型：COMPANY（企业），PERSONAL（个人） |
| tenantName | String | 签约主体名称 |
| receiverName | String | 接收人姓名 |
| contact | String | 接收人联系方式 |
| serialNo | Integer | 签署顺序（从1开始) |
| actions | List&lt;Action&gt; | 签署动作 |
| sponsor |Boolean | 是否是发起方 |
| locations | List&lt;SignatoryRect&gt; | 签署位置 |

签署位置（SignatoryRect）

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| documentId | Long | 文档Id |
| rectType | StamperType | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章），TIMESTAMP（时间戳）,ACROSS_PAGE（骑缝章） |
| page | Integer | 签署位置所在页数  |
| keyword | String | 关键字  |
| offsetX | Double | X轴坐标  |
| offsetY | Double | Y轴坐标  |

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
Long contractId = 2505670774793138196L;
List<Signatory> sigs = contractService.queryLocations(contractId);
if(sigs != null && sigs.size() > 1) {
    logger.info("拥有签署位置：{}", sigs);
}else {
    logger.info("没有签署位置");
}
```

#### 1.4.1.5 查询合同操作日志

**方法**：`List<ContractOperationLog> queryOperationLog(ContractOperationLog condition)`

**描述**：根据合同ID获取合同相关的操作日志。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operation | String | 否 | 操作类型：DOWNLOADCONTRACT（文件下载）、PRINTCONTRACT（文件打印），默认查询下载和打印操作类型 |
| contractId | Long | 是 | 合同Id |
| operatorContact | String | 否 | 操作人联系方式 |

**返回值**：

操作日志（SystemAuditLog）

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| entityId | Long | 操作对象的id |
| entityName | String | 主题 |
| operatorId | Long | 操作人Id |
| operator | String | 操作人 |
| mobile | String | 操作人手机号 |
| number | String | 操作人员工编号 |
| email | String | 操作人邮箱 |
| departments | List[Department] |  操作人所属部门 |
| detailedOperation | String | 详细操作 |
| createTime | Date | 操作时间 |

部门信息（Department）

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 部门ID |
| companyName | String | 组织架构名称 |
| type | String | 类型：CORPORATE(总公司),CHILD(子公司),COMPANY(外部企业),SECTION(部门) |

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
ContractOperationLog condition = new ContractOperationLog();
condition.setOperation("DOWNLOADCONTRACT");
condition.setContractId(2599501922709893626l);
condition.setOperatorContact("");
List<ContractOperationLog> logs = contractService.queryOperationLog(condition);
logger.info("查询合同操作日志成功");
```

#### 1.4.1.6 查询合同填参情况
**方法**：`DocumentParams documentParams (Long contractId,String bizId)`

**描述**：根据合同ID查询合同填参情况。

**参数**：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

**返回值**：

DocumentParams 
| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| sponsorComplete | Boolean | 发起方是否填参完成 |
| documents | List&lt;Document&gt; | 文档参数集合 |

Document：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | Long | 合同文档ID |
| title | String | 文档名称 |
| params | Array[DocumentParam] | 合同文档模板参数,参考DocumentParam |

DocumentParam :

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| name | String | 文档参数名称 |
| value | Stirng | 文档参数值 |
| required | Boolean | 是否必填 |
| type | ParamType | text(文本),email(邮件),money(小数),mobile(手机),integer(整数) |
| readOnly | Boolean | 是否只读 |
| belongToSponsor | Boolean | 是否是发起方填写 |
| paramKey | String | 模板参数key |
**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
DocumentParams documentParams = contractService.documentParams(2596202673608380445L,"12345");
logger.info("合同文档参数:{}",documentParams);
```

#### 1.4.1.7 查询用户对合同的操作权限

**方法**：`ContractPerminssionBean queryPermission(ContractPerminssionBean request)`

**描述**：根据合同ID或业务ID及用户联系方式查询用户对合同是否有查看、打印及下载权限。

**参数**：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| contact | String | 用户联系方式 |用户联系方式|

**返回值**：

ContractPerminssionBean：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| viewPermission | Boolean | 查看权限 |
| downloadPermission | Boolean | 下载权限 |
| printPermission | Boolean | 打印权限 |

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
ContractPerminssionBean request = new ContractPerminssionBean();
request.setContractId(2603409159703212163l);
request.setBizId("");
request.setContact("1500000000000");
ContractPerminssionBean result = contractService.queryPermission(request);
logger.info("用户对合同是否有打印权限：" + result.getPrintPermission());
```

### 1.4.2 下载合同

#### 1.4.2.1 下载合同

**方法**：`void download(Long contractId,OutputStream outputStream)`

**描述**：下载合同（包括所有合同文件），以压缩包(.zip)格式返回。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| ouputStream | OutputStream | 是 | 输出流 |

**返回值**：无 注:未完成的合同无法下载

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
OutputStream outputStream = new FileOutputStream(new File("E://test.zip"));
contractService.download(2490383502199911073L, outputStream);
IOUtils.safeClose(outputStream);
logger.info("下载合同完成");
```

#### 1.4.2.2 下载合同文档

**方法**：`void downloadDoc(Long documentId,OutputStream outputStream)`

**描述**：下载合同文档。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 文档ID |

**返回值**：无

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
OutputStream outputStream = new FileOutputStream(new File("E://test.pdf"));
contractService.downloadDoc(2490742677621088281L, outputStream);
IOUtils.safeClose(outputStream);
logger.info("下载文档成功");
```


#### 1.4.2.3 批量下载合同文件

**方法**：` void batchDownload(List<Long> contractIds, OutputStream outputStream)`

**描述**：批量下载合同文件，以压缩包(.zip)格式返回。注：最多下载20份合同文件，如果有一份合同下载失败，则整个下载请求失败。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractIds | Array[Long] | 是 | 合同ID集合 |
| ouputStream | OutputStream | 是 | 输出流 |


**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
List<Long> contractIds = new ArrayList<Long>();
contractIds.add(2560390108042498212L);
contractIds.add(2559229023145640399L);
FileOutputStream fos = new FileOutputStream("D:/sdk_contract.zip");
contractService.batchDownload(contractIds, fos);
IOUtils.safeClose(fos);
logger.info("下载合同完成");
```

#### 1.4.2.4 下载当面签用户资料

**方法**：` void downloadFaceEvidenceFile(Long contractId, OutputStream outputStream)`

**描述**：下载当面签用户签署时的认证资料，包括身份证正反照、手持身份证照片、签署双方合影、手持营业执照照片等内容。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| ouputStream | OutputStream | 是 | 输出流 |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
OutputStream outputStream = new FileOutputStream(new File("E://test.zip"));
contractService.downloadFaceEvidenceFile(2490742677621088281L, outputStream);
IOUtils.safeClose(outputStream);
logger.info("下载当面签资料完成");
```




## 1.5 防伪打印接口

### 初始化

接口：`net.qiyuesuo.sdk.api.PrintService`

实现类：`net.qiyuesuo.sdk.impl.PrintServiceImpl`

构造函数：`PrintServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
PrintService printService = new PrintServiceImpl(sdkClient);
```

### 1.5.1 防伪打印页面

**方法**：` String getEntiFakePrintUrl(Long contractId, List<Long> documentIds)`

**描述：**获取防伪打印的页面链接（传入文档ID可以指定文档打印），打开链接可进行防伪打印。链接有效期为30分钟。

**参数：**

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 是 | 合同ID |
| documentIds | List&lt;Long&gt; | 否 | 文档ID集合 |

**返回值**： String 防伪打印页面地址

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
PrintService printService = new PrintServiceImpl(client);
//方法调用
Long contractId = 2604894573842489413L;
List<Long> documentIds = new ArrayList<Long>();
documentIds.add(2604894332795838519L);
String printUrl = printService.getEntiFakePrintUrl(contractId, documentIds);
logger.info("防伪打印页面URL:{}", printUrl);
```

### 1.5.2 设置合同打印次数

**方法**：`void setPrintCount(Long contractId, int count)`

**描述：** 设置该合同允许被打印的次数。

**参数：**

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同ID |
| count | int | 是 | 合同可被打印的次数 |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
PrintService printService = new PrintServiceImpl(client);
//方法调用
Long contractId = 2469390476984254483L;
int count = 6;
printService.setPrintCount(contractId, count);
logger.info("设置合同可被打印次数成功");
```

### 1.5.3 获取合同已打印次数

**方法**：`int getPrintCount(Long contractId)`

**描述：** 获取合同已经被打印的次数。

**参数**：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 是 | 合同ID |

**返回值**：int 合同已经被打印次数

**示例：**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
PrintService printService = new PrintServiceImpl(client);
//方法调用
Long contractId = 2469390476984254483L;
int count = printService.getPrintCount(contractId);
logger.info("合同已被打印次数为：{}",count);
```

### 1.5.4 设置文档打印次数
**方法：**`void setDocumentPrintCount(List<Document> documents)`

**描述：** 设置该文档允许被打印的次数。

**参数**：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| id | String | 是 | 文档ID |
| documentPrintCount | int | 是 | 文档可被打印的次数 |

**返回值**：无

**示例：**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
PrintService printService = new PrintServiceImpl(client);
//方法调用
List<Document>documents = new ArrayList<Document>();
Document document = new Document();
document.setId(2495178212620112083L);
document.setDocumentPrintCount(15);
documents.add(document);
printService.setDocumentPrintCount(documents);
```

### 1.5.5 获取文档已打印次数

**方法：**`int getDocumentPrintCount(Long documentId)`

**描述：** 获取文档打印时该文档已经被打印的次数。

**参数**：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 文档ID |

**返回值**：int 文档已经被打印次数

**示例：**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
PrintService printService = new PrintServiceImpl(client);
//方法调用
Long documentId = 2495178212620112083L;
int count = printService.getDocumentPrintCount(documentId);
logger.info("文档已被打印次数为：{}",count);
```

## 1.6 合同抄送

### 初始化

接口：`net.qiyuesuo.sdk.api.ContractService`

实现类：`net.qiyuesuo.sdk.impl.ContractServiceImpl`

构造函数：`ContractServiceImpl(SDKClient client)`

| 参数      | 参数名称 | 类型      | 描述                                       |
| --------- | -------- | --------- | ------------------------------------------ |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

示例：

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "l2PRfrle60";
String accessSecret = "C1S98q39kWVg5LocGD9op4iXRorrdG";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(sdkClient);
```

### 1.6.1添加抄送人

**方法：**` void addTransmitter(ContractTransmit transmitter)`

**描述：** 为合同添加抄送人信息。

**参数**：

| 名称         | 类型                             | 是否必须                  | 说明                         |
| ------------ | -------------------------------- | ------------------------- | ---------------------------- |
| contractId   | Long                             | 合同ID和bizId必须填写一个 | 合同ID                       |
| bizId        | String                           | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| transmitters | List&lt;ContractTransmitItem&gt; | 是                        | 抄送人信息                   |

ContractTransmitBean(抄送人信息)

| 名称           | 类型   | 是否必须 | 说明         |
| -------------- | ------ | -------- | ------------ |
| receiverMobile | String | 是       | 抄送人手机号 |
| receiverName   | String | 是       | 抄送人姓名   |

**返回值**：无

**示例：**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
ContractService contractService = new ContractServiceImpl(client);
//方法调用
ContractTransmit transmit1= new ContractTransmit();
transmit1.setContractId(2602087235075612694L);

List<ContractTransmitItem> transmitters = new ArrayList<ContractTransmit.ContractTransmitItem>();
ContractTransmitItem transmitItem = new ContractTransmitItem();
transmitItem.setReceiverMobile("15505178155");
transmitItem.setReceiverName("徐林峰");
transmitters.add(transmitItem);
transmit1.setTransmitters(transmitters);

contractService.addTransmitter(transmit1);
logger.info("添加抄送人成功");
```

# 2 模板接口

### 初始化

接口：`net.qiyuesuo.sdk.api.TemplateService`

实现类：`net.qiyuesuo.sdk.impl.TemplateServiceImpl`

构造函数：`TemplateServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**
```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
TemplateService templateService = new TemplateServiceImpl(sdkClient);
```

### 2.1 模板列表

**方法**：`List<Template> list(TemplateRequest request)`

**描述**：根据条件获取合同模板。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tags | String	| 否 | 标签，不传则查询全部	|
| tenantId |Long | 否 | 公司Id |
| tenantName | String | 否 | 公司名称 |
| createTimeOrder |	String | 否 | 按时间排序方式 ，ASC（升序），DESC（降序）；默认为DESC |

**返回值**： List&lt;Template&gt; 文件模板集合


Template:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | String | 模板ID |
| createTime | Date | 创建时间 |
| form  | boolean | 是否为参数模板|
| status | Integer | 模版状态   1：启用，0：停用，2：删除 |
| title | String | 模版名称 |
| updateTime | Date | 更新时间 |


**示例：**
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
TemplateService templateService = new TemplateServiceImpl(client);
//方法调用
TemplateRequest request = new TemplateRequest();
request.setTenantId(2488299970128982020L);
List<Template> list = templateService.list(request);
logger.info("合同模板数量为：{}",list.size());
```

### 2.2 模板浏览页面

**方法**：`String viewUrl(Long templateId) throws PrivateAppException`

**描述**：调用该接口获取模板模板浏览页面的链接，打开链接可以查看模板。链接有效期为30分钟。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| templateId | Long	| 是 | 模板ID	|

**返回值**： String 模板预览页面的链接


**示例：**
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
TemplateService templateService = new TemplateServiceImpl(client);
//方法调用
String viewUrl = templateService.viewUrl(2515877732244288422L);
logger.info("合同模板查看链接为：{}",viewUrl);
```

### 2.3 查询模板分组

**方法**：`TemplateGroup templateGroup(TemplateGroupRequest request)`

**描述**：查询公司下文件模板的分组，返回的分组结构与契约锁系统中的分组结构一致。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long	| 否 | 公司ID	|
| companyName | String | 否 | 公司名称 |

注：若公司ID和公司名称都为空，则查询平台方下的模板分组。

**返回值**： `List<TemplateGroup>` 文件模板分组集合

TemplateGroup（分组信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 模板的分组ID |
| name | String | 模板的分组名称 |
| creator | String | 创建人名称 |
| companyId | Long | 所属公司ID |
| levels | Int | 该分组所在的分组层级，0为根节点 |
| createTime | String | 分组的创建时间，格式yyyy-MM-dd HH:mm:ss |
| children | Array&lt;TemplateGroup&gt; | 该分组下的子分组列表 |
| templateList | Array&lt;Template&gt; | 该分组下的模板信息 |

Template（模板信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | String | 模板ID |
| status | Integer | 模版状态   1：启用，0：停用，2：删除 |
| tags  | Array[Tag] | 标签信息；参照Tag |
| templateType | String | 模板类型：HTML(html文本),	HTML_FORM(html参数模板),	WORD(word文本),	WORD_FORM(word参数模板) |
| tenantId | Long | 所属公司ID |
| title | String | 模版名称 |
| createTime | Date | 创建时间 |
| updateTime | Date | 更新时间 |
| word  | boolean | 是否为word模板|
| form  | boolean | 是否为参数模板|


**示例：**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
TemplateService templateService = new TemplateServiceImpl(client);
//方法调用
TemplateGroupRequest request = new TemplateGroupRequest();
TemplateGroup templateGroup = templateService.templateGroup(request);
```

### 2.4 查询所有内部企业模板分组

**方法**：`List<Company> innercompanyTempalteGroup(TemplateGroupRequest request)`

**描述**：查询所有内部企业下文件模板的分组，模板只查询已启用的，返回的模板分组结构与契约锁系统中的模板分组结构一致。

**参数:** 无

**返回值**： `List<Company>` 内部企业分组集合

Company(公司信息)：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司ID |
| name | String | 公司名称 |
| charger | String | 管理员名称 |
| templateGroup | TemplateGroup | 模板分组，参考TemplateGroup |

TemplateGroup（分组信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 模板的分组ID |
| name | String | 模板的分组名称 |
| creator | String | 创建人名称 |
| companyId | Long | 所属公司ID |
| levels | Int | 该分组所在的分组层级，0为根节点 |
| createTime | String | 分组的创建时间，格式yyyy-MM-dd HH:mm:ss |
| children | Array&lt;TemplateGroup&gt; | 该分组下的子分组列表 |
| templateList | Array&lt;Template&gt; | 该分组下的模板信息 |

Template（模板信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | String | 模板ID |
| status | Integer | 模版状态   1：启用，0：停用 |
| tags  | Array[Tag] | 标签信息；参照Tag |
| templateType | String | 模板类型：HTML(html文本),	HTML_FORM(html参数模板),	WORD(word文本),	WORD_FORM(word参数模板) |
| tenantId | Long | 所属公司ID |
| title | String | 模版名称 |
| createTime | Date | 创建时间 |
| updateTime | Date | 更新时间 |
| word  | boolean | 是否为word模板|
| form  | boolean | 是否为参数模板|


**示例：**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
TemplateService templateService = new TemplateServiceImpl(client);
//方法调用
List<Company> companies = templateService.innercompanyTempalteGroup(new TemplateGroupRequest());
```

# 3 企业接口



## 3.1 企业接口

### 初始化

接口：`net.qiyuesuo.sdk.api.CompanyService`

实现类：`net.qiyuesuo.sdk.impl.CompanyServiceImpl`

构造函数：`CompanyServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**
```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(sdkClient);
```

### 3.1.1 企业认证状态

**方法**：`String getComnpanyAuthStatus(String companyName)`

**描述**：根据企业的名称查询企业的认证状态。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyName | String | 是 | 企业名称 |

**返回值**: 

String 认证状态：UNSUBMIT（未提交认证）,APPLIED（认证申请中）,PASSED（认证通过）,REJECTED（认证拒绝）

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
String companyName = "泛微8";
String companyStatus = companyService.getComnpanyAuthStatus(companyName);
logger.info("企业：{}认证状态是:{}",companyName,companyStatus);
```

### 3.1.2 创建企业

**方法**：`Company create(CreateCompanyRequest request)`

**描述**：提交企业认证信息，创建内部企业或外部企业，新创建的企业状态 是”审核中“，需要”基本信息审核“和”基本户审核“通过后才能生效。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String	| 是 | 企业名称	|
| license |	FileItem | 是 | 营业执照 |
| legalAuthorization | FileItem	| 创建内部企业时必填 | 法人授权书 |
| registerNo | String| 是 |	工商注册号、统一社会信用码 |
| legalPerson |String | 是	| 法人姓名|
| paperType |String | 否 |	法人证件照类型：IDCARD("二代身份证"), PASSPORT("护照"), OTHER("其他");|
| legalPersonId | String | 否 |	法人证件号|
| charger |String| 是 |负责人|
| mobile |String| 是 |负责人手机号|
| area |String| 否 |地区：CN("中国大陆 "),TW("中国台湾"),HK("中国香港"),MO("中国澳门");默认为中国大陆CN|
| province |String | 否  |所在区域|
| tenantType | String| 是  |	企业类型：INNER_COMPANY（内部企业），COMPANY（外部企业）|
| openCompanyId |String| 否 |第三方平台企业的ID，为空则自动生成 |
| operator |String| 否 | 操作人名称 |
| companyType |Integer| 否 | 公司类型：1（企业法人） 2（个体工商户）默认为企业 3（政府） 4（事业单位）5（其他组织）默认为企业法人 |
| remind |Boolean| 否 | 企业创建成功，是否短信通知管理员，默认不通知 |

FileItem：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| fileName| String	| 是 | 文件名称 |
| stream |	InputStream | 是 | 文件输入流 |

**返回值**: company  公司信息，参考company。

Company：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司id |
| name | String | 公司名称 |
| registerNo | String | 公司代码 |
| status | TenantStatus | 公司状态；UNREGISTERED（未注册），REGISTERED（已注册），CERTIFYING（认证中），AUTH_SUCCESS（认证完成），AUTH_FAILURE（认证失败） |
| tenantType | TenantType | 公司类型；CORPORATE（平台方），INNER_COMPANY（内部公司），COMPANY（外部公司） |
| legalPerson | String | 法人姓名 |
| legalPersonId | String | 法人证件号 |
| createTime | Date | 创建时间，格式：yyyy-MM-dd HH:mm:ss |

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CreateCompanyRequest req = new CreateCompanyRequest();
req.setName("私有云SDK测试内部企业002");
req.setRegisterNo("2783624");
req.setLegalPerson("张三");
req.setPaperType("IDCARD");
req.setLegalPersonId("100200196010171564");
req.setCharger("张三");
req.setMobile("15850695001");
req.setProvince("上海");
req.setCity("上海");
req.setLabel("华东");
req.setTenantType(TenantType.INNER_COMPANY);
req.setOperator("管理员");
req.setCompanyType(2);
InputStream licenseInput = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\license.png"));
InputStream legalInput = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\license.png"));
req.setLegalAuthorization(new StreamFile("legalAuthorization.png",legalInput));
req.setLicense(new StreamFile("license.png",licenseInput));
String companyId = companyService.create(req);
logger.info("第三方企业平台id:{}",companyId);
```

### 3.1.3 企业列表

**方法**：`List<Company> queryList(TenantType tenantType) `

**描述**：获取所有企业信息列表

**参数**: 

| 参数| 类型 | 是否必须 | 描述 |
| --------| -------- | -------- | -------- |
| tenantType| TenantType | 否 | 公司类型：CORPORATE（平台方），INNER_COMPANY（内部企业），COMPANY（外部企业） |

**返回值**: 

`List<Company>` 公司列表，参考【Company】。

Company：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司id |
| name | String | 公司名称 |
| status | TenantStatus | 公司状态：UNREGISTERED(未注册),	REGISTERED(已注册),	CERTIFYING(认证中),	AUTH_SUCCESS(认证完成),	AUTH_FAILURE(认证失败) |
| tenantType | TenantType | 租户类型 |
| parentId | Long | 上级公司id |



**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
List<Company> companies = companyService.queryList();
if(companies != null) {
    logger.info("公司数量为：{}",companies.size());
}else {
    logger.info("无公司数据");
}		
```


### 3.1.4 查询企业详情

**方法**：`Company detail(CompanyRequest request) `

**描述**：获取企业信息。

**参数**: 

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | --------  | -------- | -------- |
| companyId | Long | 否 | 公司ID |
| companyName | String | 否 | 公司名称 |
| registerNo | String | 否 | 公司代码；工商注册号或统一社会信息代码 |

**返回值**: Company 公司信息，参考【Company】

Company：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司id |
| name | String | 公司名称 |
| registerNo | String | 公司代码 |
| status | TenantStatus | 公司状态；UNREGISTERED（未注册），REGISTERED（已注册），CERTIFYING（认证中），AUTH_SUCCESS（认证完成），AUTH_FAILURE（认证失败） |
| tenantType | TenantType | 公司类型；CORPORATE（平台方），INNER_COMPANY（内部公司），COMPANY（外部公司） |
| parentId | Long| 父级公司Id |
| legalPerson | String | 法人姓名 |
| legalPersonId | String | 法人证件号 |
| createTime | Date | 创建时间，格式：yyyy-MM-dd HH:mm:ss |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CompanyRequest request = new CompanyRequest();
request.setCompanyId(2421235669853425710L);
Company company = companyService.detail(request);
logger.info("公司的法人： "+company.getLegalPerson());		
```
### 3.1.5 通知用户企业认证 

**方法：**` Company sendCompanyAuthNotify(CompanyAuthNoticeRequset requset) `

**描述：**传入企业信息和联系人信息，短信通知联系人登录契约锁私有云进行企业认证。

**参数：**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String	| 是 | 认证企业名称|
| registerNo |	String | 是 |认证企业工商注册号 |
| charger | String| 是 |认证企业负责人姓名 |
| mobile |String|是|认证企业负责人联系方式|
| license |FileItem|否|营业执照（需要完整的文件名，包含文件后缀）|
| legalPerson |String|否|法人姓名|

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CompanyAuthNoticeRequset requset=new CompanyAuthNoticeRequset();
requset.setName("测试企业");
requset.setCharger("王五");
requset.setRegisterNo("1234564565");
requset.setMobile("15000000000");
requset.setLegalPerson("王五");
InputStream licenseInput = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\测试2.jpg"));
requset.setLicense(new StreamFile("测试2.jpg",licenseInput));
companyService.sendCompanyAuthNotify(requset);
logger.info("认证通知成功");	
```

### 3.1.6 变更企业信息

**方法：**`String changeInfo(CompanyRequest request)`

**描述：**已认证成功的企业，可以调用此接口获取认证链接，对企业基本认证信息进行变更，基本信息变更通过后会发送短信提醒申请人进行下一步认证。

**参数：**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long | 公司ID、公司名称、企业代码不能同时为空 | 公司ID |
| companyName | String | 公司ID、公司名称、企业代码不能同时为空 | 公司名 |
| registerNo | String  | 公司ID、公司名称、企业代码不能同时为空 | 企业代码 |
| mobile | String | 是 | 申请人手机号 |

**返回值**：String 企业变更信息链接

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CompanyRequest request = new CompanyRequest();
request.setCompanyName("测试变更企业");
request.setMobile("1500000000");
companyService.changeInfo(request);
```

### 3.1.7 删除未认证公司

**方法**：`void deleteUnceritifiedCompany(CompanyRequest request)`

**描述**：将未认证的公司用户删除，公司未签署的合同退回处理，删除公司下所有员工。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long | 公司ID与公司名称必须填写一个 | 公司ID |
| companyName | String | 公司ID与公司名称必须填写一个 | 公司名称 |

**返回值**: 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CompanyRequest request = new CompanyRequest();
request.setCompanyName("测试删除企业");
companyService.deleteUnceritifiedCompany(request);
```
### 3.1.8 冻结企业 

**方法**：`void freezeOrUnfreezeCompany(CompanyRequest request, Boolean freeze)`

**描述**：将非平台方企业冻结，并冻结其相关操作。

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long | 公司ID与公司名称必须填写一个 | 公司ID |
| companyName | String | 公司ID与公司名称必须填写一个 | 公司名称 |
| freeze | Boolean | 是 | 是否冻结 |

**返回值**: 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CompanyRequest request = new CompanyRequest();
request.setCompanyName("测试变更企业");
companyService.freezeOrUnfreezeCompany(request, true);
```

### 3.1.9 解冻企业

**方法**：`void freezeOrUnfreezeCompany(CompanyRequest request, Boolean freeze)`

**描述**：将冻结状态的企业解冻。

| 名称        | 类型    | 是否必须                     | 描述     |
| ----------- | ------- | ---------------------------- | -------- |
| companyId   | Long    | 公司ID与公司名称必须填写一个 | 公司ID   |
| companyName | String  | 公司ID与公司名称必须填写一个 | 公司名称 |
| freeze      | Boolean | 是                           | 是否冻结 |

**返回值**: 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CompanyRequest request = new CompanyRequest();
request.setCompanyName("测试变更企业");
companyService.freezeOrUnfreezeCompany(request, false);
```

### 3.1.10 企业经营状态信息 

**方法**：`CompanyCredit manageStatus(CompanyRequest request)`

**描述**：获取企业经营状态信息，查看企业信誉。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long | ID、名称和公司代码必须三选一 | 公司ID |
| companyName | String | ID、名称和公司代码必须三选一 | 公司名称 |
| registerNo | String | ID、名称和公司代码必须三选一 | 工商注册号 |

**返回值**: CompanyCredit

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CompanyService companyService = new CompanyServiceImpl(client);
//方法调用
CompanyRequest request = new CompanyRequest();
request.setCompanyName("上海亘岩网络科技有限公司");
CompanyCredit companyCredit = companyService.manageStatus(request);
System.out.println("企业名称：" + companyCredit.getName());
```

## 3.2 员工接口

### 初始化

接口：`net.qiyuesuo.sdk.api.EmployeeService`

实现类：`net.qiyuesuo.sdk.impl.EmployeeServiceImpl`

构造函数：`EmployeeServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
EmployeeService employeeService = new EmployeeServiceImpl(sdkClient);
```

### 3.2.1 创建内部员工

**方法**：`String createInnerCompanyEmployee(InnerEmployeeRequest rquest)`

**描述**：根据企业名称创建内部企业员工信息。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String	| 是 | 员工姓名	|
| contact |	String | 是 | 员工联系方式，为登录帐号 |
| password | String| 否 |	员工登录密码 |
| companyName |String|是|	企业名称|
| cardNo |String | 否	| 员工身份证号|
| number |String | 否 |第三方平台企业员工编号	|

**返回值**：String 企业员工ID

**示例**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
EmployeeService employeeService = new EmployeeServiceImpl(client);
//方法调用
InnerEmployeeRequest req = new InnerEmployeeRequest();
req.setName("李四");
req.setContact("15850695002");
req.setPassword("qwer1234");
req.setCompanyName("好好喝");
req.setCardNo("32072119951019143X");
req.setNumber("S0001");
String userId = employeeService.createInnerCompanyEmployee(req);
logger.info("创建的内部企业员工id:{}", userId);
```

### 3.2.2 移除员工

**方法**：`void quit(RemoveEmployeeRequest request)`

**描述**：移除企业下员工。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contact |	String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工手机号 |
| employeeNo | String| 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工编号 |
| openUserId | String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 第三方平台员工ID |
| companyName | String | 是 | 企业名称 |

**返回值**：无

**示例**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
EmployeeService employeeService = new EmployeeServiceImpl(client);
//方法调用
RemoveEmployeeRequest request = new RemoveEmployeeRequest();
request.setEmployeeNo("1234");
request.setCompanyName("好好喝");
employeeService.quit(request);
```

### 3.2.3 移除所有内部企业该员工

**方法**：`void quitInnerCompanyEmployee(RemoveEmployeeRequest request)`

**描述**：从所有内部企业下，移除该员工。

**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contact |	String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工手机号 |
| employeeNo | String| 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工编号 |
| openUserId | String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 第三方平台员工ID |

**返回值**：无

**示例**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
EmployeeService employeeService = new EmployeeServiceImpl(client);
//方法调用
RemoveEmployeeRequest request = new RemoveEmployeeRequest();
request.setEmployeeNo("1234");
request.setCompanyName("好好喝");
employeeService.quitInnerCompanyEmployee(request);
```

### 3.2.4 查询用户信息

**方法**：`UserDetail userDetail(UserSearchRequest request)`

**描述**：查询用户信息。

**参数：**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile |	String |手机号、用户身份证号，用户Id三者必填一项 | 员工手机号 |
| cardNo |String | 手机号、用户身份证号，用户Id三者必填一项 | 用户身份证号 |
| id |Long | 手机号、用户身份证号，用户Id三者必填一项 | 用户Id |

**返回值**：用户认证信息，参照UserDetail

UserDetail：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 用户id |
| name | String | 用户姓名 |
| mobile | String | 用户联系方式 |
| cardNo | String |用户身份证号码 |
| status  | UserAuthStatus | 用户认证状态：AUTH_SUCCESS("认证完成"),AUTH_FAILURE("认证失败"); |
| companies | List&lt;Company&gt; | 用户所在的公司列表信息 |

Company：

| 名称       | 类型         | 描述                                                         |
| ---------- | ------------ | ------------------------------------------------------------ |
| id         | Long         | 公司id                                                       |
| name       | String       | 公司名称                                                     |
| status     | TenantStatus | 公司状态；UNREGISTERED（未注册），REGISTERED（已注册），CERTIFYING（认证中），AUTH_SUCCESS（认证完成），AUTH_FAILURE（认证失败） |
| tenantType | TenantType   | 公司类型；CORPORATE（平台方），INNER_COMPANY（内部公司），COMPANY（外部公司） |
| freeze     | Boolean      | 冻结或未冻结状态                                             |

**示例**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
EmployeeService employeeService = new EmployeeServiceImpl(client);
//方法调用
UserSearchRequest request = new UserSearchRequest();
request.setMobile("12345678910");
UserDetail result = employeeService.userDetail(request);
logger.info("角色列表：{}", JSONUtils.toJson(result));
```

### 3.2.5 按角色查询员工

**方法**：`List<Role> listByRoleType(Role request)`

**描述**：按角色查询员工，返回角色列表及其下的员工。

**参数：**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long | 否 | 公司ID |
| companyName |String | 否 | 公司名称 |
| roleType | String | 否 | 角色类型：SYSTEM(系统管理员),SA(印章管理员),LP(法定代表人),CONTRACT(文件管理员),CUSTOM(自定义角色)，不填默认查询所有 |

**返回值**：员工角色信息列表，参照Role

Role

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| name | String | 角色名称 |
| companyId | Long | 公司ID |
| roleType | String | 角色类型 |
| employees | Array[Employee] | 员工列表 |

Employee:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 员工ID |
| name | String | 员工名称 |
| userId | Long | 员工对应的用户ID |
| mobile | String | 员工手机号 |
| email | String | 员工邮箱 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
EmployeeService employeeService = new EmployeeServiceImpl(client);
//方法调用
Role request = new Role();
request.setCompanyName("维森集团有限公司");
request.setRoleType(RoleType.LP);
List<Role> roles = employeeService.listByRoleType(request);
logger.info("角色列表："+roles.size());
```

### 3.2.6 通知个人用户实名认证

**方法**：`void authNotify(UserBean userBean)`

**描述**：发送个人认证短信邮箱通知,若用户不存在则自动创建用户。

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile |	String | 是 | 用户手机号 |
| email | String | 否 | 用户邮箱 |
| name | String | 是 | 用户名 |
| cardNo | String | 是 | 身份证号 |

**返回值**: 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
EmployeeService employeeService = new EmployeeServiceImpl(client);
//方法调用
UserBean user = new UserBean();
user.setMobile("10000000001");
user.setName("刘磊");
employeeService.authNotify(user);
logger.info("通知认证成功");
```

### 3.2.7 删除未认证个人用户

**方法**：`void deleteUnceritifiedUser(User user)`

**描述**：将未认证的个人用户相关信息删除，所有公司将该员工删除，待处理的合同退回处理。

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| userId |	String | 用户ID与用户联系方式必须填写一项 | 用户ID |
| contact | String | 用户ID与用户联系方式必须填写一项 | 用户联系方式 |

**返回值**: 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
UserService userService = new UserServiceImpl(client);
//方法调用
User user = new User();
user.setEmail("1186653090@qq.com");
user.setMobile("15000000000");
userService.deleteUnceritifiedUser(user);
logger.info("删除未认证用户成功");
```

### 3.2.8 用户修改手机号短信通知

**方法**：`void changeMobileNotify(ChangeMobileRequest request)`

**描述**：用户修改手机号，向新手机号发送修改手机号的链接。

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| oldMobile | String | 是 | 用户旧手机号 |
| newMobile | String | 是 | 用户新手机号 |

**返回值**: 无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
UserService userService = new UserServiceImpl(client);
//方法调用
ChangeMobileRequest request = new ChangeMobileRequest();
request.setOldMobile("15000000001");
request.setNewMobile("15000000000");
userService.changeMobileNotify(request);
logger.info("用户修改手机号短信通知成功");
```

# 4 印章接口

### 初始化

接口：`net.qiyuesuo.sdk.api.SealService`

实现类：`net.qiyuesuo.sdk.impl.SealServiceImpl`

构造函数：`SealServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(sdkClient);
```

### 4.1 印章使用记录

**方法**：`List<SealRecord> sealRecord(SealRequest request)`

**描述**：获取印章使用记录。


**参数:**

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| startTime | Date| 否 |开始时间，格式：yyyy-MM-dd	 |
| endTime   | Date| 否 |结束时间，格式：yyyy-MM-dd |
| companyName   | String| 否 |企业名称，不传默认为平台方 |

**返回值**： `List<SealRecord>` 印章使用记录，参照【SealRecord】

SealRecord:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| contractId | Long | 合同ID |
| sealId | Long | 印章ID |
| userId | Long |用印人ID |
| userName  | String | 用印人名称 |
| subject  | String |合同名称 |
| createTime  | Date | 用印时间 |
| number  | Integer | 用印次数 |
| sealName | String | 印章名称 |
| tenantId  | Long | 合同发起方ID |
| tenantName | String | 合同发起方名称 |

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
SealRequest req = new SealRequest();
req.setCompanyName("上海契约锁网络科技有限公司");
List<SealRecord> list = sealService.sealRecord(req);
logger.info("印章共有{}条使用记录",list.size());
```

### 4.2 公司印章列表

**方法**：`List<Seal> sealList(Long companyId,String companyName,String category)`

**描述**：获取公司印章列表

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long| 否 |	不传默认平台方 |
| companyName | String | 否 |	不传默认平台方 |
| category | String | 否 | 为空默认查询电子章，PHYSICS("物理签章"),ELECTRONIC("电子签章") |

**返回值**： List&lt;Seal&gt;  印章列表，参照Seal。

Seal：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 印章ID |
| name | String | 印章名称 |
| status | String | 印章状态，NORMAL("正常"),FREEZE("冻结"),DELETE("删除"),INVALID("失效") |
| category | String | 印章分类，PHYSICS(物理章),ELECTRONIC(电子章) |
| type | String | 印章类型，COMPANY("企业公章"),PERSONAL("个人签名"),LP("法定代表人章") |
| owner | Long | 印章所属公司Id |
| ownerName | String | 印章所属公司名称 |


**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
Long companyId = 2488299970128982020L;
String companyName = "泛微8";
List<Seal> list = sealService.sealList(companyId,companyName);
System.out.println("印章数量："+list.size());
```

### 4.3 创建企业公章

**方法**：`Seal createCompanySeal(Seal seal, String image)`

**描述**：根据用户上传的图片创建公司印章。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| owner | Long | 是 | 公司ID |
| image | String| 是 | Base64格式的印章图片  |
| spec | String | 是 | 印章规格，参照下表【SealSpec】 |
| name | String | 否 | 印章名称，不传默认为公司名称 |

SealSpec：

| 参数 | 说明 |
| -------- | -------- |
| CIRCULAR_60 | 圆形_60 |
| CIRCULAR_58 | 圆形_58 |
| CIRCULAR_50 | 圆形_50 |
| CIRCULAR_46 | 圆形_46 |
| CIRCULAR_45 | 圆形_45 |
| CIRCULAR_44 | 圆形_44 |
| CIRCULAR_42 | 圆形_42 |
| CIRCULAR_40 | 圆形_40 |
| CIRCULAR_38 | 圆形_38 |
| OVAL_45_30 | 椭圆_45_30 |
| OVAL_40_30 | 椭圆_40_30 |
| SQUARE_25_25 | 正方形_25 |
| SQUARE_22_22 | 正方形_22 |
| SQUARE_20_20 | 正方形_20 |
| SQUARE_18_18 | 正方形_18 |
| RECTANGLE_50_30 | 长方形_50_30 |
| RECTANGLE_40_16 | 长方形_40_16 |

**返回值**： Seal  印章，参照Seal。


Seal:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 印章ID |
| owner | Long | 公司编号 或者用户编号 |
| name | String | 印章名称 |
| type | SealType | 签章类型 |
| spec  | String | 印章规格 |
| sealKey  | String | 印章图片key |
| createTime  | Date | 创建时间 |
| status  | String | 印章状态 |
| useCount | Integer | 印章使用的次数 |


**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
String image = "data:image/jpg;base64,/9j/4ASDJAOJ...."; //base64编码
Seal seal = new Seal();
seal.setName("测试之章");
seal.setOwner(2483213421275689526L);
seal.setSpec("CIRCULAR_38");
Seal result = sealService.createCompanySeal(seal,image );
logger.info("创建印章完成");
```

### 4.4 查询印章详情

**方法**：`Seal detail(Long sealId)`

**描述**：查询印章详情。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| sealId  | Long | 是 | 印章ID |

**返回值**： Seal 印章数据

Seal:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 印章ID |
| owner | Long | 公司ID |
| name | String | 印章名称 |
| otherName | String | 印章别名 |
| type | SealType | 签章类型 |
| spec  | SealSpec | 印章规格 |
| sealKey  | String | 印章图片key |
| createTime  | Date | 创建时间 |
| status  | SealStatus | 印章状态 |
| useCount | Integer | 印章使用的次数 |
| users | Array[User] | 印章使用者用户信息 |
| employees | Array[Employee] | 印章使用者员工信息 |

User:	

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 用户Id |
| name | String | 用户名 |
| mobile | String | 手机号 |
| email | String | 邮箱 |
| createTime | Date | 创建时间 |

Employee:	

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 员工Id |
| name | String | 员工名 |
| mobile | String | 手机号 |
| email | String | 邮箱 |
| number | String | 员工编号 |


请求示例：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
Seal seal = sealService.detail(0L);
```

### 4.5 获取印章图片

**方法**：`void image(Seal request, OutputStream outputStream)`

**描述**：获取印章图片

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| request   | Seal | 是 | 获取印章的查询条件 |

Seal:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| id  | Long | 是 | 印章ID |

响应:

印章图片数据

Content-Type：image/png


请求示例：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
OutputStream outputStream;
try {
	outputStream = new FileOutputStream(new File("E://别名.png"));
	Seal request = new Seal();
	request.setId(2616199893913903237l);
	sealService.image(request, outputStream);
} catch (Exception e) {
	System.out.println(e);
}
```


### 4.5 查询用户可使用的印章

**方法**：`List<Seal> getSealByUser(String mobile, String companyName)`

**描述**：查询公司下用户有权限使用的印章。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyName   | String | 是 | 公司名称 |
| mobile | String | 是 | 员工手机号 |

**返回值**： `List<Seal>`  印章列表，参照 4.5 中 Seal数据结构

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
String mobile = "18000000004";
String companyName = "洁云3";
seals = sealService.getSealByUser(mobile, companyName);
```

### 4.7 获取个人用户印章图片

**方法**：`void personalSealImage(Long userId, String mobile, String cardNo, OutputStream outputStream)`

**描述**：获取用户设置的个人签名图片。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| userId   | String | 用户ID、手机号和身份证号必须有一个不为空 | 用户ID |
| mobile | String | 用户ID、手机号和身份证号必须有一个不为空 | 手机号 |
| cardNo | String | 用户ID、手机号和身份证号必须有一个不为空 | 身份证号 |

响应:

印章图片数据

Content-Type：image/png

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
OutputStream outputStream;
Long userId = 111L;
String mobile = "";
String cardNo = "";
try {
    outputStream = new FileOutputStream(new File("D://个人.png"));
    sealService.personalImage(userId, mobile, cardNo, outputStream);
} catch (Exception e) {
    System.out.println(e);
}
```

### 4.8 批量获取个人用户印章图片

**方法**：`void personalSealImages(SealCondition condition, OutputStream outputStream)`

**描述**：用于获取个人签名图片，根据时间区间获取，如果不传默认获取所有个人签名，以压缩包(.zip)格式返回。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| startTime | Date | 否 | 查询开始时间，格式：yyyy-MM-dd HH:mm:ss |
| endTime | Date | 否 | 查询截止时间，格式：yyyy-MM-dd HH:mm:ss |

**返回值**：无


**请求示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
OutputStream outputStream;
SealCondition condition = new SealCondition();
condition.setStartTime(Date.valueOf("2019-04-15"));
condition.setEndTime(Date.valueOf("2019-05-08"));
try {
    outputStream = new FileOutputStream(new File("D://个人批量.zip"));
    sealService.personalSealImages(condition, outputStream);
} catch (Exception e) {
    System.out.println(e);
}
```

### 4.9 获取时间戳图片

**方法**：`void timeStampImage(OutputStream outputStream)`

**描述**：获取印章图片

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| outputStream  | OutputStream | 是 | 输出的时间戳图片 |
响应:

印章图片数据

Content-Type：`image/png`

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
OutputStream outputStream;
try {
	outputStream = new FileOutputStream(new File("D://时间戳.png"));
	sealService.timeStampImage(outputStream);
} catch (Exception e) {
	System.out.println(e);
}
```

### 4.10  所有内部企业的印章列表

**方法**：`List<Company> innercompanySealList(SealCondition condition)`

**描述**：获取所有内部企业包含平台方的印章列表。

**参数**：

SealCondition：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| category | String | 否 | 印章类型：PHYSICS("物理签章"),ELECTRONIC("电子签章"),不传默认查询电子章 |

**返回值**：`List<Company> ` 内部公司列表。

Company(公司信息)：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司ID |
| name | String | 公司名称 |
| status | String | 公司状态：UNREGISTERED("未注册"),REGISTERED("已注册"),CERTIFYING("认证中"),AUTH_SUCCESS("认证完成"),AUTH_FAILURE("认证失败") |
| charger | String | 管理员名称 |
| seals | Array[Seal] | 印章列表，参考Seal |

Seal:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 印章ID |
| owner | Long | 公司ID |
| name | String | 印章名称 |
| type | SealType | 印章类型，COMPANY("企业公章"),PERSONAL("个人签名"),LP("法定代表人章") |
| spec  | SealSpec | 印章规格 |
| sealKey  | String | 印章图片key |
| createTime  | Date | 创建时间 |
| status  | SealStatus | 印章状态，NORMAL("正常"),FREEZE("冻结"),DELETE("删除"),INVALID("失效") |
| useCount | Integer | 印章使用的次数 |
| category | String | 印章分类，PHYSICS(物理章),ELECTRONIC(电子章) |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealService sealService = new SealServiceImpl(client);
//方法调用
List<Company> companies = sealService.innercompanySealList(new SealCondition());
System.out.println("内部企业数量：" + companies.size());
```

# 5 业务分类接口

### 初始化

接口：`net.qiyuesuo.sdk.api.CategoryService`

实现类：`net.qiyuesuo.sdk.impl.CategoryServiceImpl`

构造函数：`CategoryServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**
```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
CategoryService categoryService = new CategoryServiceImpl(sdkClient);
```

### 5.1 业务分类列表

**方法**：`List<Category> categoryList(Long companyId, String companyName)`

**描述**：查询公司下所有的业务分类。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long| 否 |	不传默认查询所有已启用的业务分类。 |
| companyName | String | 否 |	不传默认查询所有已启用的业务分类。 |

**返回值**： `List<Category> ` 业务分类列表。


Category

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务分类ID |
| name | String | 分类名称 |
| state | Intger | 业务分类状态；0：启用，1：停用 2:逻辑删除 |
| tenantID | Long | 租户ID |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CategoryService categoryService = new CategoryServiceImpl(client);
//方法调用
Long companyId = 2488299970128982020L;
String companyName = "泛微8";
List<Category> list = categoryService.categoryList(companyId, companyName);
logger.info("公司：{}，业务分类数量为{}",companyName,list.size());
```

### 5.2 查询业务分类分组

**方法**：`List<CategoryGroup> categoryGroup(CategoryGroup condition)`

**描述**：查询公司下业务分类的分组，返回的分组结构与契约锁私有云中的分组结构一致。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long| 否 | 不传默认查询平台方所有已启用的业务分类。 |
| companyName | String | 否 | 不传默认查询平台方所有已启用的业务分类。 |
| name | String | 否 | 分组名称 |

**返回值**： `List<CategoryGroup>`  业务分类分组。

CategoryGroup（分组信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务分类的分组ID |
| name | String |分组名称|
| categoryCount | Int | 分组中所有的业务分类数量 |
| disableCount | Int | 分组中已停用的业务分类数量 |
| createTime | String | 分类创建时间，格式yyyy-MM-dd HH:mm:ss |
| children | Array<CategoryGroup> | 该分组下的子分组列表 |
| categoryList | Array<Category> | 该分组下的业务分类 |

Category（业务分类信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务分类ID |
| type | String | 分类类型:ELECTRONIC（电子合同分类），PHYSICAL（物理用印分类） |
| name | String | 业务分类名称 |
| tenantId | Long | 公司ID |
| createTime | Date | 创建时间 |
| primary | Boolean | 是否为默认类型 |
| state | Integer | 业务分类状态 0：启用， 1：停用， 2：逻辑删除 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CategoryService categoryService = new CategoryServiceImpl(client);
//方法调用
CategoryGroup condition = new CategoryGroup();
condition.setName("Mysql");
List<CategoryGroup> list = categoryService.categoryGroup(condition);
System.out.println(list.size());
```

### 5.3 业务分类详情

**方法**：`Category deatil(Category condition)`

**描述**：查询业务分类详情（包含模板信息）。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| categoryId | Long	| 否 | 业务分类ID	|
| categoryName | String | 分类ID与分类名称至少一个必填 | 业务分类名称  |

注：只能查询内部企业的业务分类。

**返回值**： 

Category：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务分类ID |
| name | String | 业务分类名称 |
| tenantId | Long | 公司ID |
| createTime | Date | 创建时间 |
| primary | Boolean | 是否为默认类型 |
| state | Integer | 业务分类状态 0：启用， 1：停用， 2：逻辑删除 |
| sealId | Long | 印章ID |
| templates | Array[TemplateBean] | 业务分类模板信息 |

TemplateBean：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 模板或文档ID |
| title | String | 模板名称 |
| createTime | Date | 创建时间 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CategoryService categoryService = new CategoryServiceImpl(client);
//方法调用
Category condition = new Category();
condition.setId(2571538668816531570l);
Category category = categoryService.deatil(condition);
```

### 5.4 所有内部企业的业务分类

**方法**：`List<Company> categoryGroupList()`

**描述**：查询所有内部企业下业务分类的分组，返回的分组结构与契约锁私有云中的分组结构一致。

**参数**：无

**返回值**：  `List<Company>`  内部企业列表。

Company(公司信息)：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司ID |
| name | String | 公司名称 |
| charger | String | 管理员名称 |
| categoryGroups | Array[CategoryGroup] | 模板分组，参考CategoryGroup |

CategoryGroup（分组信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务分类的分组ID |
| name | String |业务分类的分组名称|
| categoryCount | Int | 分组中所有的业务分类数量 |
| disableCount | Int | 分组中已停用的业务分类数量 |
| createTime | String | 分类创建时间，格式yyyy-MM-dd HH:mm:ss |
| children | Array[CategoryGroup] | 该分组下的子分组列表 |
| categoryList | Array[Category] | 该分组下的业务分类 |

Category（业务分类信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务分类ID |
| type | String | 分类类型:ELECTRONIC（电子合同分类），PHYSICAL（物理用印分类） |
| name | String | 业务分类名称 |
| tenantId | Long | 公司ID |
| createTime | Date | 创建时间 |
| primary | Boolean | 是否为默认类型 |
| state | Integer | 业务分类状态 0：启用， 1：停用， 2：逻辑删除 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
CategoryService categoryService = new CategoryServiceImpl(client);
//方法调用
List<Company> companies = categoryService.categoryGroupList();
logger.info("内部企业数量："+companies.size());
```

# 6 数据签名接口

### 初始化

接口：`net.qiyuesuo.sdk.api.DataSignService`

实现类：`net.qiyuesuo.sdk.impl.DataSignServiceImpl`

构造函数：`DataSignServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
DataSignService dataSignService = new DataSignServiceImpl(client);
//方法调用
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
DataSignService dataSignService = new DataSignServiceImpl(sdkClient);
```

### 6.1 业务数据签名

**方法**：`String sign(InputStream data, String bizId, String user, String cardNo, String contact)`

**描述**：业务数据签名

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| data | InputStream | 是 |	业务数据流 |
| bizId | String | 是 | 业务ID |
| user | String | 是 | 用户信息 |
| cardNo | String | 否 | 身份证号 |
| contact | String | 是 |	联系方式 |

**返回值**： String 签名后数据

**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
DataSignService dataSignService = new DataSignServiceImpl(client);
//方法调用
InputStream data = new ByteArrayInputStream("Test Data哈哈哈".getBytes());
String bizId = UUID.randomUUID().toString();
String user = "张红";
String cardNo = "123456789";
String contact = "13111111111";
String signedData = dataSignService.sign(data, bizId, user, cardNo, contact);
logger.info("签名后数据：{} ", signedData);
```

### 6.2 签名结果链接

**方法**：`String viewUrl(String bizId)`

**描述**：获取签名结果的页面链接。链接有效期为30分钟。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| bizId | String | 否 | 业务ID |

**返回值**： String 签名结果链接。

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
DataSignService dataSignService = new DataSignServiceImpl(client);
//方法调用
String viewUrl = dataSignService.viewUrl(bizId);
System.out.println(viewUrl);
```

### 6.3 获取签名原始数据

**方法**：`String getSrcData(String bizId)`

**描述**：业务数据签名

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| bizId | String | 否 | 业务ID |

**返回值**： String 原始签名数据


**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
DataSignService dataSignService = new DataSignServiceImpl(client);
//方法调用
String data = dataSignService.getSrcData(bizId);
System.out.println(data);
```

### 6.4 签名原始数据与证书信息

**方法**：`VerifyResult getSrcAndCert(QueryDataRequest queryDataRequest)`

**描述**：查询签名的原始数据和证书信息，可选择是否隐藏私有信息（手机号、企业代码等）。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| bizId | String | 否 | 业务ID |
| sensitiveInfoInvisible | boolean | 否 | 私密信息是否不可见 |

**返回值**：

VerifyResult:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| signResult | String | 签名后数据 |
| strAlgName | String | 签名所用算法 |
| serialNumber | String | 签名证书编号 |
| organization  | String | 证书颁发机构 |
| signatory  | String | 签署方信息 |
| certDateFrom  | String | 证书有效期开始 |
| certDateTo  | String | 证书有效期结束 |
| signDate | String | 签署时间 |
| srcDate | String | 签署原文信息 |


**示例**：
```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
DataSignService dataSignService = new DataSignServiceImpl(client);
//方法调用
QueryDataRequest queryDataRequest = new QueryDataRequest();
queryDataRequest.setBizId("2520952950522937349");
queryDataRequest.setSensitiveInfoInvisible(true);
VerifyResult result = dataSignService.getSrcAndCert(queryDataRequest);
```


# 7 验签接口

### 初始化

接口：net.qiyuesuo.sdk.api.VerifyService

实现类：net.qiyuesuo.sdk.impl.VerifyServiceImpl

构造函数：VerifyServiceImpl(SDKClient client)

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

示例

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
VerifyService verifyService = new VerifyServiceImpl(client);
```


### 7.1 PDF文件验签

**方法**：`PdfVerifierResult verifyPdf(InputStream file) throws Exception`

**描述**：PDF文件验签。

**参数**：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | InputStream | 是 |	PDF文件 |

**返回值**： 

PdfVerifierResult：

| 名称 | 类型  | 描述 |
| -------- | --------| -------- |
| statusCode | int | 验签结果码；0为未修改 |
| statusMsg | String | 验签结果 |
| signatureInfos | String | 签署详情 |
| documentId | Long | 文档ID |


**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
VerifyService verifyService = new VerifyServiceImpl(client);
//方法调用
InputStream inputstream = new FileInputStream("D:/test/test.pdf");
PdfVerifierResult result = verifyService.verifyPdf(inputstream);
logger.info("验证结果，statusCode：{}", result.getStatusCode());		
```

# 8 物理用印接口

### 初始化

接口：`net.qiyuesuo.sdk.api.SealApplyService`

实现类：`net.qiyuesuo.sdk.impl.SealApplyServiceImpl`

构造函数：`SealApplyServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**:

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(sdkClient);
```

### 8.1 授权认证

**方法**：`SealApplyBean apply(SealApplyRequest sealapplyRequest)`

**描述**：用于开放平台接口授权认证，第三方平台传递授权参数下发用印授权，返回当前用户对该印章操作的授权码。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantName | String | 是 | 发起公司 |
| categoryId | Long | 否 | 业务分类id |
| applyerName | String | 否 | 申请人姓名 |
| applyerContact | String | 是 | 申请人联系方式 |
| applyerNumber | String | 是 | 申请人员工编号，联系方式与员工编号必须二选一 |
| subject | String | 是 | 主题 |
| serialNo | String | 否 | 序列号 |
| description | String | 否 | 描述 |
| auths | Array[SealAuthRequest] | 是 | 用印授权人；参照SealAuthRequest |
| documents | Array[Long] | 否 | 文档ID的集合 |
| forms | Array[Form] | 否 | 第三方传入的表单集合,参照Form |

SealAuthRequest（用印授权人）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| deviceNo | String | deviceNo与sealName至少填写一个| 授权印章识别码 |
| sealName | String | deviceNo与sealName至少填写一个 | 授权印章名称 |
| ownerName | String | 否 | 印章所属的公司名称 |
| count | int | 是 | 授权次数 |
| startTime | Sting | 否 | 用印开始时间，格式：2019-04-09 09:18:53 |
| endTime | Sting | 否| 用印结束时间 ，格式：2019-04-09 09:18:53|
| users | Array[Employee] | 是 | 用印授权人；参照Employee |


Employee（用印授权人）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile | String | 是 | 授权人手机号 |
| number | String | 是 | 授权人员工编号,联系手机号与员工编号必须二选一 |

Form(第三方表单):

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| key | String | 是 | 键 |
| value | String | 是 | 值 |

**返回值**：SealApplyBean 申请详情；参照SealApplyBean

SealApplyBean：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务主键 |
| sealAuths | Array[SealAuth] | 用印授权信息，对应授权人信息；参照SealAuth |
| status | String | 申请状态 |

sealAuth：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 授权id |
| userId | Long | 授权用户id |
| userName | String | 授权人姓名 |
| vertifyCode | String | 授权用印码 |
| contact | String | 授权人手机号 |
| number | String | 授权人员工编号 |
| sealName | String | 授权的印章名称 |
| ownerName | String | 授权的印章所属公司名称 |
| deviceNo | String | 授权的印章编号 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//申请基本信息
SealApplyRequest request = new SealApplyRequest();
request.setApplyerContact("15201852663");
request.setApplyerNumber("");
request.setSerialNo("S-0000007");
request.setSubject("测试接口发起合同用印多印章");
request.setDescription("测试接口发起授权8");
request.setTenantName("上海泛微网络科技股份有限公司");
//授权信息1
SealAuthRequest authrequest1 = new SealAuthRequest("201811281017312E",3);
authrequest1.addEmployee("1520185***", "");
authrequest1.addEmployee("1347270***", "");
authrequest1.addEmployee("1505543***", "");
authrequest1.addEmployee("", "NO000001");
authrequest1.startTime(new Date());
authrequest1.endTime(new Date());
request.addSealAuth(authrequest1);
//授权信息2
SealAuthRequest authrequest2 = new SealAuthRequest();
authrequest2.setCount(2);
authrequest2.setSealName("物理章测试011");
authrequest2.setOwnerName("契约锁");
authrequest2.addEmployee("1520185****", "");
authrequest2.addEmployee("1505543****", "");
request.addSealAuth(authrequest2);
//发起
SealApplyBean response = sealApplyService.apply(request);
```

### 8.2 追加用印次数

**方法**：`SealApplyBean append(SealApplyAppendRequest request)`

**描述**：用于追加接口用印次数。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |
| deviceNo | String | 否 | 授权印章识别码，不传表示所有申请的印章都增加次数 |
| count | int | 是 | 追加数量,必须大于0 |
| contact | String | 是 | 追加人联系方式 |
| number | String | 是 | 追加人员工编号，手机号与编号二选一必填 |
| contactName | String | 否 | 追加人姓名|


**返回值**：SealApplyBean 详情；参照 SealApplyBean

SealApplyBean：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务ID |
| sealAuths | Array[SealAuth] | 用印授权信息，对应授权人信息；参照SealAuth |
| status | String | 申请状态,状态变成用印中 |

sealAuths：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 授权id |
| userId | Long | 授权用户id |
| userName | String | 授权人姓名 |
| vertifyCode | String | 授权用印码 |
| contact | String | 授权人手机号 |
| number | String | 授权人员工编号 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
SealApplyAppendRequest appendRequest = new SealApplyAppendRequest();
appendRequest.setBusinessId(2525979115262386178l);
appendRequest.setContact("13588887777");
appendRequest.setNumber("");
appendRequest.setContactName("修智");
appendRequest.setCount(2);
appendRequest.setDeviceNo("201810091143943F");
SealApplyBean response = sealApplyService.append(appendRequest);
System.out.println(JSONUtils.toJson(response));
```

### 8.3 结束用印

**方法**：`void finish(SealApplyCompleteRequest request)`

**描述**：用于第三方平台结束此次物理用印申请，接口调用成功后用印状态变为 FINISHED。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |
| deviceNo | String | 否 | 印章编号，如果不为空，结束指定印章的用印申请；如果为空，结束所有印章的用印申请 |
| contactName | String | 否 | 结束人姓名 |
| contact | String | 是 | 结束人联系方式 |
| number | String | 是 | 结束人编号，手机号与编号二选一必填 |
| reason | String | 是 | 结束原因|

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
SealApplyCompleteRequest request = new SealApplyCompleteRequest();
request.setBusinessId(2525979115262386178l);
request.setDeviceNo("201810091143943F");
request.setContact("13588887777");
request.setNumber("");
request.setContactName("修智");
request.setReason("测试");
sealApplyService.finish(request);
```

### 8.4 用印详情

**方法**：SealApplyBean detail(Long businessId)

**描述**：用于获取此次用印申请详情

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |

**返回值**： SealApplyBean 申请详情；参照 SealApplyBean：

SealApplyBean：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务ID |
| tenantName | String | 公司名称 |
| applyerName | String | 申请人姓名 |
| applyerContact | String | 申请人联系方式 |
| applyerNumber | String | 申请人员工编号 |
| subject | String | 申请事项 |
| serialNo | String | 序列号 |
| count | int | 申请次数 |
| description | String | 描述 |
| status | String | 状态 USING(“用印中”)，COMPLETE(“结束”)，FINISHED(“第三方平台手动结束用印”) |
| createTime | Date | 发起时间 |
| sealAuths | Array[SealAuth] | 用印授权信息，对应授权人信息；参照SealAuth |
| faceImages | Array[PhysicsImg] | 人脸图片，参照PhysicsImg |
| useImages | Array[PhysicsImg] | 用印图片，参照PhysicsImg |
| appendLogs | Array[SealAppend] | 追加记录，参照SealAppend |
| seal | Seal | 印章信息，参照Seal |

SealAuth:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 授权ID |
| userName | String | 授权人姓名 |
| leftCount | int | 剩余次数 |
| usedCount | int | 该授权人已用次数 |
| vertifyCode | String | 授权用印码 |
| complete | Boolean | 授权是否结束，true：是，false：否 |
| completeTime | Date | 用印结束时间|
| contact | String | 用印人联系方式 |
| number | String | 用印人员工编号 |

PhysicsImg:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| authId | Long | 授权ID |
| photokey | String | 图片key |

SealAppend:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| userName | String | 追加人姓名 |
| count | int | 追加次数 |
| createTime | Date | 追加时间 |
| contact | String | 追加人联系方式 |
| number | String | 追加人员工编号 |

Seal:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| name | String | 印章名称 |
| useCount | int | 使用次数 |
| deviceId | String | 用印宝识别码 |
| bluetooth | String | 蓝牙地址 |

**示例**：

```java
SealApplyBean sealApplyBean = sealApplyService.detail(2504502697086554116L);
```

### 8.5 下载图片

**方法**：`void imagesDownload(Long businessId, PhysicsPhotoType photoType, OutputStream outputStream)`

**描述**：用于获取用印图片，以压缩包(.zip)格式返回。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |
| photoType | String | 否 | 图片类型：FACE(人脸拍照),SIGNATORY(签署拍照) |
| ouputStream | OutputStream | 是 | 输出流 |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
FileOutputStream fos = new FileOutputStream("D:/test/seal_apply_images.zip");
sealApplyService.imagesDownload(2503517759928934401L, null, fos);
IOUtils.safeClose(fos);
```

### 8.6 获取用印图片

**方法**：`void image(String photokey, OutputStream outputStream)`

**描述**：用于获取用印图片。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| photokey | String | 是 | 图片key |
| ouputStream | OutputStream | 是 | 输出流 |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
FileOutputStream fos = new FileOutputStream("D:/test/apply.png");
sealApplyService.image("20181123-19fe7f34-6140-404a-9528-8e34f08e5b5e", fos);
IOUtils.safeClose(fos);
```

### 8.7 上传用印图片

**方法**：`void upload(SealApplyImageRequest request)`

**描述**：用于补传缺少的用印图片。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| images | file | 是 | 要上传的图片 |
| sealAuthId | Long | 否 | 该次用印的授权id |
| type | String | 否 | 图片类型，FACE(人脸),SIGNATORY(用印) |
| businessId | Long | 否 | 申请id，若不传sealAuthId，则此字段必填 |
| contact | Long | 否 | 用户联系方式，若不传sealAuthId，则此字段和number字段必填一个 |
| number | Long | 否 | 用户工号，若不传sealAuthId，则此字段和contact字段必填一个 |

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
SealApplyImageRequest request = new SealApplyImageRequest();
InputStream is = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\Jellyfish.jpg"));
List<InputStream> list = Collections.singletonList(is);
request.setStreams(list);
request.setBusinessId(2586135949182755136L);
request.setContact("18702549503");
sealApplyService.upload(request);
```

### 8.8 获取用户拖欠用印图片的用印记录

**方法**：`List<SealAuth> getOweAuths(String contact, String number)`

**描述**：用于补传缺少的用印图片。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contact | String | 否 | 用户联系方式，与number字段必填一个 |
| number | String | 否 | 用户编号，与contact字段必填一个 |

**返回值**：

当result的长度大于0时，表示当前申请无法发起新的用印申请:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 授权ID |
| subject | String | 该次用印申请的主题 |
| oweUseImageCount | int | 拖欠的用印图片的数量 |
| complete | Boolean | 是否完成用印 |
| sealId | Long | 印章ID |
| businessId  | Long | 用印申请ID |
| vertifyCode  | String | 用印码 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
List<SealAuth> list = sealApplyService.getOweAuths("187****9509", "WS1009");
System.out.println(list);
```

### 8.9 通过业务分类发起用印

**方法**：SealApplyBean applyByCategory(SealApplyRequest sealapplyRequest) throws PrivateAppException;

**描述**：用于开放平台接口使用业务分类发起用印

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantName | String | 否 | 发起公司, 业务分类id为空时必传 |
| categoryId | Long | 否 | 业务分类id |
| categoryName | String | 否 | 业务分类名称, 业务分类id为空时必传 |
| subject | String | 是 | 主题 |
| serialNo | String | 否 | 序列号 |
| description | String | 否 | 描述 |
| applyerName | String | 否 | 申请人姓名 |
| applyerContact | String | 联系方式与员工编号必须二选一 | 申请人联系方式 |
| applyerNumber | String | 联系方式与员工编号必须二选一 | 申请人员工编号 |
| callbackUrl | String | 否 | 回调地址 |
| auths | Array[SealAuthRequest] | 是 | 用印授权人；参照SealAuthRequest |
| flowNodes | Array[ThirdFlowNode] | 否 | 第三方审批节点；参照ThirdFlowNode |
| documents | Array[Long] | 否 | 文档ID的集合 |
| forms | Array[Form] | 否 | 第三方传入的表单集合,参照Form |

SealAuthRequest（用印授权）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| deviceNo | String | deviceNo与sealName至少填写一个| 授权印章识别码 |
| sealName | String | deviceNo与sealName至少填写一个 | 授权印章名称 |
| ownerName | String | sealName不为空时必填 | 印章所属公司名称 |
| count | int | 是 | 授权次数 |
| startTime | Date | 否 | 授权使用开始时间,格式样例：2019-04-09 09:18:53 |
| endTime | Date | 否  | 授权使用结束时间,格式样例：2019-04-09 09:18:53 |

ThirdFlowNode（第三方审批节点）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String | 是 | 节点名称 |
| category | String | 是 | 固定为SEAL_AUDIT |
| createTime | Date | 是 | 开始时间 |
| completeTime | Date | 是 | 完成时间 |
| operatorData | OperatorData | 是 | 操作数据,见OperatorData |

OperatorData（第三方节点操作数据）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operateType | String | 是 | 固定为approve |
| info | String | 是 | 操作信息 |
| operatorTime | Date | 是 | 操作时间 |
| operator | Operator | 是 | 操作人,见Operator |

Operator（第三方节点操作人）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile | String | mobile和number至少填写一个 | 联系方式 |
| number | String | mobile和number至少填写一个 | 员工编号 |

Form(第三方表单):

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| key | String | 是 | 键 |
| value | String | 是 | 值 |

**响应**：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 响应信息 |
| result  | SealApplyBean | 返回结果；参照SealApplyBean |

SealApplyBean：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务主键 |
| sealAuths | Array[SealAuth] | 用印授权信息，对应授权人信息；参照SealAuth |
| status | String | 申请状态 |

sealAuth：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 授权id |
| userId | Long | 授权用户id |
| userName | String | 授权人姓名 |
| vertifyCode | String | 授权用印码 |
| contact | String | 授权人手机号 |
| number | String | 授权人员工编号 |
| sealName | String | 授权的印章名称 |
| ownerName | String | 授权的印章所属公司名称 |
| deviceNo | String | 授权的印章编号 |

**响应码解释**:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
SealApplyRequest request = new SealApplyRequest();
request.setCategoryId(2596607614068138038L);
request.setTenantName("阿里哔哔");
request.setSubject("测试物理用印1");
request.setApplyerContact("15555559999");
request.setDescription("测试物理用印");
List<SealAuthRequest> auths = new ArrayList<>();
SealAuthRequest authRequest1 = new SealAuthRequest();
authRequest1.setDeviceNo("001");
authRequest1.setCount(2);
auths.add(authRequest1);
SealAuthRequest authRequest2 = new SealAuthRequest();
authRequest2.setDeviceNo("002");
authRequest2.setCount(3);
auths.add(authRequest2);
request.setAuths(auths);
SealApplyBean bean = sealApplyService.applyByCategory(request);
```

### 8.10 用印后文件上传

**方法**：void sealUsedFileUpload(SealUsedFileUploadRequest request) throws Exception;

**描述**：用于开放平台接口在用印完成后上传用印文件

**参数**:

SealUsedFileUploadRequest:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 用印申请id |
| fileUploadNodeId | String | 是 | 用印完成后回调回去的用印文件上传节点的id |
| file | StreamFile | 是 | 用印文件,只支持docx和pdf, 见StreamFile |
| title | String | 是 | 文件标题, 例: xxxxx.docx |

StreamFile:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| fileName | String | 是 | 文件名称 |
| stream | InputStream | 是 | 文件输入流 |

**请求示例**：

```JAVA
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
SealUsedFileUploadRequest request = new SealUsedFileUploadRequest();
request.setBusinessId(2598710781476721187L);
request.setTitle("标题");
request.setFileUploadNodeId(2598711119285965370L);
StreamFile file = new StreamFile("xxx.docx", new FileInputStream(new File("/Users/yufeng/xxx.docx")));
request.setFile(file);
sealApplyService.sealUsedFileUpload(request);
```

### 8.11 用印后文件上传(图片列表)

**方法**：void sealUsedFileUpload(SealUsedImageUploadRequest request) throws Exception;

**描述**：用于开放平台接口在用印完成后上传用印文件的图片列表, 系统会自动转换为pdf

**参数**:

SealUsedImageUploadRequest:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 用印申请id |
| fileUploadNodeId | String | 是 | 用印完成后回调回去的用印文件上传节点的id |
| images | Array[StreamFile] | 是 | 用印文件的图片列表,支持jpg,jpeg,gif,png,tiff,见StreamFile |
| title | String | 是 | 文件标题, 例: xxxxx.docx |

StreamFile:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| fileName | String | 是 | 文件名称 |
| stream | InputStream | 是 | 文件输入流 |

**请求示例**：

```JAVA
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
SealUsedImageUploadRequest request = new SealUsedImageUploadRequest();
request.setBusinessId(2598710781476721187L);
request.setTitle("test5555555");
request.setFileUploadNodeId(2598711119285965370L);
List<StreamFile> images = new ArrayList<>();
images.add(new StreamFile("1.jpg", new FileInputStream(new File("/Users/qiyuesuo/Documents/doc/1.jpg"))));
images.add(new StreamFile("2.jpg", new FileInputStream(new File("/Users/qiyuesuo/Documents/doc/2.jpg"))));
images.add(new StreamFile("3.jpg", new FileInputStream(new File("/Users/qiyuesuo/Documents/doc/3.jpg"))));
request.setImages(images);
sealApplyService.sealUsedFileUpload(request);
```

### 8.12、更新用印时间

**方法**：`void updateUseSealTime(SealAuthUpdateTimeRequest request)`

**描述**：用于更新用印时间。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 用印申请id |
| deviceNo | String | 否 | 授权印章识别码  |
| sealName | String | 否 | 印章名称 |
| ownerName | String | 否 | 公司名称 |
| startTime | String | 是 | 开始时间(格式--yyyy:MM:dd HH:mm:ss) |
| endTime | String | 是 | 结束时间 (格式--yyyy:MM:dd HH:mm:ss)|

参数备注:当deviceNo、sealName、ownerName都不传参,默认更新当前用印所有未结束的印章使用时间。

**返回值**：无

**示例**：

```java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
SealApplyService sealApplyService = new SealApplyServiceImpl(client);
//方法调用
SealAuthUpdateTimeRequest request = new SealAuthUpdateTimeRequest();
request.setBusinessId(2601348612074230050L);
request.setStartTime("2020-08-27 11:07:51");
request.setEndTime("2020-08-27 11:07:51");
sealApplyService.updateUseSealTime(request);
```

# 9 存证接口

### 初始化

接口：`net.qiyuesuo.sdk.api.EvidenceService`

实现类：`net.qiyuesuo.sdk.impl.EvidenceServiceImpl`

构造函数：`EvidenceServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**:

```java
String url = "https://privopen.qiyuesuo.me";
String accessKey = "fVKUJXKk3q";
String accessSecret = "UiNetL5xXo7RZ9I1gpt123lbQ0nYvd";
SDKClient sdkClient = new SDKClient(url, accessKey, accessSecret);
EvidenceService evidenceService = new EvidenceServiceImpl(sdkClient);
```

### 9.1 存证下载

**方法**：`void download(Long contractId,OutputStream outputStream) throws Exception`

**描述**：下载存证报告。

**参数**:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同id |
| ouputStream | OutputStream | 是 | 输出流 |

**返回值**：无

**示例**：

``` java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
EvidenceService evidenceService = new EvidenceServiceImpl(client);
//方法调用
FileOutputStream fos = new FileOutputStream("D:/contract_evidence_file.pdf");
evidenceService.download(contractId, fos);
IOUtils.safeClose(fos);
```

# 10 开放平台检查

### 初始化

接口：`net.qiyuesuo.sdk.api.HealthService`

实现类：`net.qiyuesuo.sdk.impl.HealthServiceImpl`

构造函数：`HealthServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**:

```java
String url = "https://privopen.qiyuesuo.me";
SDKClient sdkClient = new SDKClient(url, null, null);
HealthService healthService = new HealthServiceImpl(privateAppClient);
```

### 10.1 健康检查

**方法**：`String checkHealth()`

**描述**：校验开放平台健康状态。

**参数**: 无

**返回值**：String  OK(成功)

**示例**：

``` java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
HealthService healthService = new HealthServiceImpl(client);
//方法调用
String result = healthService.checkHealth();
logger.info("系统健康情况：" + result);
```

# 11 版本号

### 初始化

接口：`net.qiyuesuo.sdk.api.VersionService`

实现类：`net.qiyuesuo.sdk.impl.HealthServiceImpl`

构造函数：`VersionServiceImpl(SDKClient client)`

| 参数 | 参数名称 | 类型 | 描述 |
| -------- | -------- | -------- | -------- |
| sdkClient | 平台信息 | SDKClient | SDKClient对象(身份信息和API服务器地址封装) |

**示例**:

```java
String url = "https://privopen.qiyuesuo.me";
SDKClient client = new SDKClient(url, null, null);
VersionService versionService = new VersionServiceImpl(client);
```

### 11.1 根据版本号校验sdk是否可用

**方法**：` VersionInfo checkVersion()`

**描述**：获取sdk版本、服务器版本，对比校验sdk是否可用。

**参数**: 无

**返回值**：Boolean  true(可用)、false(不可用)

**示例**：

``` java
//初始化
String url = "http://127.0.0.1:9182";
String accessKey = "0hniGJcs";
String accessSecret = "qdelAh4pkXou463g9zgHA0dHoNfo";
SDKClient client = new SDKClient(url, accessKey, accessSecret);
VersionService versionService = new VersionServiceImpl(client);
//方法调用
VersionInfo info = versionService.checkVersion();
logger.info("sdk版本 {}与服务器版本 {}对比结果：{}" , info.getSdkVersion(), info.getServerVersion(), info.getCheckResult());
```

[TOC]
#  契约锁私有云 API 接口文档

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

# 1 合同接口

## 1.1 合同创建

### 1.1.1 创建合同文档

#### 1.1.1.1 用文件创建合同文档

描述：用文件创建合同文档。支持的文件类型包括：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html。注：由于合同文件以PDF格式保存，所以推荐使用PDF格式的文件来创建合同文档，使用其他格式的文件需要先转换成PDF格式再创建，效率较低。（文档ID在后续接口会用到）。

Request URL： /document/createbyfile

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | File | 是 | 文档文件 |
| title | String | 是 | 合同文档名称 |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}] |

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID参数不用添加）请见本文档 1.1.3.2 添加水印

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documentId | String | 合同文档ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /document/createbyfile HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----
Content-Disposition: form-data; name="file"; filename="/D:/授权.pdf


----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

测试文档
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

#### 1.1.1.2 用模板创建合同文档

描述：用户在私有云签署平台中维护好文件模板，用文件模板创建合同文档，返回文档ID（文档ID在后续接口会用到）。

Request URL： /document/createbytemplate

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| templateId | Long | 是 | 模板ID；在签章平台的【文件模板】中查看 |
| params | String | 否 | 模板参数，json格式字符串；如：{"paramer1":"value1","paramer2":"value2"} |
| title | String | 否 | 合同文档名称,默认使用模板名称 |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}] |

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID不用添加）请见本文档 1.1.3.2 添加水印

当使用HTML模板时，参数params注意事项如下：
>参数类型是单行文本时，value大小不超过300,如：{"单行文本":"value1"};<br>
>参数类型是日期时，value格式为：yyyy-MM-dd，如{"日期":"2019-06-04"};<br>
>参数类型是身份证号，value只能是15或18位的数字或字母，如：{"身份证":"123456789123456789；"};<br>
>参数类型是单选，value只能是单选的选项,如：{"单选":"val1"};<br>
>参数类型是多选，value只能是多选的选项，多个value用逗号隔开，如：{"多选":"val1,val2"};<br>
>参数类型是表格，可以动态添加行，暂不支持动态添加列，value格式是一维数组，数组的每项对应表格的每行，每行格式是key-value格式，key是表格每列的列名如：[{\"key1\":\"1\",\"key2\":\"2\",\"key3\":\"3\",\"key4\":\"4\"},{\"key1\":\"5\",\"key2\":\"6\",\"key3\":\"7\",\"key4\":\"8\"}]。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documentId | String | 合同文档ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /document/createbytemplate HTTP/1.1
Host: hostname
x-qys-accesstoken: AU4HofNTpo
x-qys-signature: 0c43a5b98b64a82079ec73a0f221084e
x-qys-timestamp: 0
Content-Type： multipart/form-data

Content-Disposition: form-data; name="templateId"

2578526090984927799
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="params"

{"参数1":"公司1"}
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

#### 1.1.1.3 根据HTML创建文档

描述：用户上传HTML字符串，在私有云系统中创建文档，返回文档ID（文档ID在后续接口会用到）。

Request URL： /document/createbyhtml

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| html | String | 是 | html报文 |
| title | String | 是 | 合同文档名称 |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]|

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID不用添加）请见本文档 1.1.3.2 添加水印

注意：如果上传的html创建的文档出现乱码问题，需要在HTML报文中添加 <meta http-equiv="content-type" content="text/html;charset=utf-8"> 

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documentId | String | 合同文档ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /document/createbyhtml HTTP/1.1
Host: hostname
x-qys-accesstoken: AU4HofNTpo
x-qys-signature: 0c43a5b98b64a82079ec73a0f221084e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="html"

<html><head><meta http-equiv="content-type" content="text/html;charset=utf-8"></head><body><p>title</p><p>在线第三方电子合同平台。企业及个人用户可通过本平台与签约方快速完成合同签署，安全、合法、有效。</p></body></html>
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

html文档
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

#### 1.1.1.4 多文件创建合同文档

描述：多个文件组合成一个合同文档，返回合同文档ID，合同文档在创建合同时用到。生成的合同文档的内容顺序与参数中文件顺序一致。支持的文件类型包括：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html。

Request URL： /document/createbyfiles

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| files | Array[File] | 是 | 多个文件 |
| title | String | 是 | 合同文档名称 |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}] |

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID不用添加）请见本文档 1.1.3.2 添加水印

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documentId | String | 合同文档ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST  /document/createbyfiles HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Cache-Control: no-cache
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="files"; filename="/C:/Desktop/Test/授权.png

------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="files"; filename="/C:/Desktop/Test/222.pdf

------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="title"

合并文档
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

#### 1.1.1.5 根据路径创建合同文档

描述：根据文件路径创建文档（文件路径可以是服务器本地文件路径或网络文件路径），返回合同文档ID，合同文档在创建合同时用到。生成的合同文档的内容顺序与参数中文件顺序一致。支持的文件类型包括：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html。

Request URL： /document/createbyurl

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| url | String | 是 | 文件路径 |
| title | String | 是 | 合同文档名称 |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}] |

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID不用添加）请见本文档 1.1.3.2 添加水印

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documentId | String | 合同文档ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /document/createbyurl HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a
x-qys-timestamp: 0
Cache-Control: no-cache
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="url"

http://d.hiphotos.baidu.com/image/pic/item/f636afc379310a55f00f421ab94543a982261030.jpg
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="title"

路径文档
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

#### 1.1.1.6 根据文件类型创建合同文档

描述：调用接口须填写文件类型，用文件创建合同文档。支持的文件类型包括：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html, rtf, xls。注：由于合同文件以PDF格式保存，所以推荐使用PDF格式的文件来创建合同文档，使用其他格式的文件需要先转换成PDF格式再创建，效率较低。（文档ID在后续接口会用到）。

Request URL： /v2/document/createbyfile

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | File | 是 | 文档文件 |
| title | String | 是 | 合同文档名称 |
| fileType | String | 是 | 文件类型：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html, rtf, xls |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}] |

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID不用添加）请见本文档 1.1.3.2 添加水印

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Document | 合同文档 |

Document:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| documentId | Long | 文档Id |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /v2/document/createbyfile HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a78
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/D:/授权.pdf


------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

测试文档
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="fileType"

pdf
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
    "result": {
        "documentId": "2598725910263943175"
    },
    "code": 0,
    "message": "SUCCESS"
}
```


####1.1.1.7 用文件创建发起方可见附件文档
描述：用文件创建发起方可见的文档，该文档作为内部附件使用，只供发起方内部查看、盖章时的参考文件。支持的文件类型包括：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html。注：由于合同文件以PDF格式保存，所以推荐使用PDF格式的文件来创建合同文档，使用其他格式的文件需要先转换成PDF格式再创建，效率较低。（文档ID在后续接口会用到）。

Request URL： /document/createsponsorfile

Request Method： POST

Content-Type： multipart/form-data

参数:

名称 |类型| 是否必须 |描述
-|-|-
file |	File |	是	 |文档文件
title	 |String |	是	 |合同文档名称
响应:

名称 |类型 |描述
-|-|-
code |Int| 响应码
message| String| 响应消息
documentId| String| 合同文档ID
响应码解释:

响应码| 描述
-|-
0 |请求成功
1000000 |未知错误
1000001 |参数错误
请求示例：

```HTTP

POST /document/createbyfile HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----
Content-Disposition: form-data; name="file"; filename="/D:/授权.pdf


----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

测试文档
----WebKitFormBoundary7MA4YWxkTrZu0gW
```
响应示例：
```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```


### 1.1.2 添加合同文件

描述：为已生成的草稿状态的合同中添加合同文档。此接口在创建合同接口后调用。

#### 1.1.2.1  用文件添加合同文档

描述：为已生成的草稿状态的合同添加合同文档。支持的文件类型包括：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html。，返回文档ID（文档ID在后续接口会用到）。

Request URL： /document/addbyfile

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | File | 是 | 支持PDF文件 和Word文件，txt文件和单张图片格式（png, gif, jpg, jpeg, tiff） |
| title | String | 是 | 合同文档名称 |
| contractId | Long | 合同ID和bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}] |

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID不用添加）请见本文档 1.1.3.2 添加水印

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documentId | String | 合同文档ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /document/addbyfile HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Cache-Control: no-cache
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="files"; filename="/C:/Desktop/Test/222.pdf

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

测试文档
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="contractId"

2572980225330036970
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

#### 1.1.2.2 用模板添加合同文档

描述：为已生成的草稿状态的合同添加合同文档。用户在私有云系统中维护好文件模板，记录下模板ID和模板参数，使用此接口即可添加合同文档。

Request URL： /document/addbytemplate

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| templateId | Long | 是 | 模板ID；在签署平台的【文件模板】中查看 |
| params | String | 否 | 模板参数，json格式字符串；如：{"paramer1":"value1","paramer2":"value2"} |
| title | String | 否 | 合同文档名称，不传默认使用模板名称 |
| contractId | Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| waterMarks | String | 否 | 水印，json格式字符串；如：[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}] |

当要添加水印时，注意事项如下：
>添加水印具体参数（文档ID不用添加）请见本文档 1.1.3.2 添加水印

当使用HTML模板时，参数params注意事项如下：
>参数类型是单行文本时，value大小不超过300,如：{"单行文本":"value1"};<br>
>参数类型是日期时，value格式为：yyyy-MM-dd，如{"日期":"2019-06-04"};<br>
>参数类型是身份证号，value只能是15或18位的数字或字母，如：{"身份证":"123456789123456789；"};<br>
>参数类型是单选，value只能是单选的选项,如：{"单选":"val1"};<br>
>参数类型是多选，value只能是多选的选项，多个value用逗号隔开，如：{"多选":"val1,val2"};<br>
>参数类型是表格，可以动态添加行，暂不支持动态添加列，value格式是一维数组，数组的每项对应表格的每行，每行格式是key-value格式，key是表格每列的列名如：[{\"key1\":\"1\",\"key2\":\"2\",\"key3\":\"3\",\"key4\":\"4\"},{\"key1\":\"5\",\"key2\":\"6\",\"key3\":\"7\",\"key4\":\"8\"}]。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documentId | String | 合同文档ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /document/addbytemplate HTTP/1.1
Host: hostname
x-qys-accesstoken: AU4HofNTpo
x-qys-signature: 0c43a5b98b64a82079ec73a0f221084e
x-qys-timestamp: 0
Cache-Control: no-cache
Content-Type: multipart/form-data

------WebKitFormBoundary7MA4YWxkTrZu0gW--,
Content-Disposition: form-data; name="templateId"

contractId=24549626498099077&templateId=2454962649809907788&params=%7B%22paramer1%22%3A%22value1%22%2C%22paramer2%22%3A%22value2%22%7D&title=模板文档

```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

####1.1.2.3 用文件添加发起方可见附件文档
描述：为已生成的草稿状态的合同添加发起方可见的文档，该文档作为内部附件使用，只供发起方内部查看、盖章时的参考文件。支持的文件类型包括：doc, docx, txt, pdf, png, gif, jpg, jpeg, tiff, html。，返回文档ID（文档ID在后续接口会用到）。

Request URL： privapp.qiyuesuo.me/document/addsponsorfile

Request Method： POST

Content-Type： multipart/form-data

参数:

名称| 类型 |是否必须 |	描述
-|-|-|-
file |File| 是 |支持PDF文件 和Word文件，txt文件和单张图片格式（png, gif, jpg, jpeg, tiff）
title |String| 是 |合同文档名称
contractId| Long| 合同ID和bizId必须填写一个| 合同ID
bizId |String| 合同ID和bizId必须填写一个| 合同的唯一标识，由调用方生成
响应:

名称| 类型| 描述
-|-|-
code |Int| 响应码
message| String| 响应消息
documentId |String| 合同文档ID
响应码解释:

响应码 |描述
-|-
0 |请求成功
1000000| 未知错误
1000001 |参数错误
请求示例：

```HTTP

POST /document/addbyfile HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Cache-Control: no-cache
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="files"; filename="/C:/Desktop/Test/222.pdf

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"


2597003797105070598
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="contractId"

2603918635415224392
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="waterMarks"

[{"content":"水印1","fontSize":"30","location":"UPPER_LEFT","imageBase64":"/9j/4AAQSkZJRgABAQEASABIAAD/4gxY"},{"content":"水印2","fontSize":"30","location":"LOWER_LEFT"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
  "code": 0,
  "documentId": "2407437103157690373",
  "message": "SUCCESS"
}
```

### 1.1.3 编辑合同文档

#### 1.1.3.1 添加附件

描述：合同文档在签章系统中是以PDF形式存在，此接口可以为生成的PDF文档添加附件，附件将嵌入进PDF文档中。附件可以在下载的合同文档中查看。

Request URL： /document/addattachment

Request Method： POST

Content-Type： multipart/form-data;charset=UTF-8

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |
| title | String | 是 | 附件名称；必须包含扩展名，如：“附件.pdf” |
| description | String | 否 | 附件描述 |
| attachment | file | 是 | 附件 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /document/addattachment HTTP/1.1
Host: privopen.qiyuesuo.me
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="attachment"; filename="附件.pdf"
Content-Type: application/pdf


------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

附件名称.pdf
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="description"

附件描述
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="documentId"

2497607666778542211
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```
#### 1.1.3.2 添加水印

描述：根据用户传入的水印，向已经创建好的文档中添加水印，默认水印位置居中。

Request URL： /document/addwatermark

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 合同ID |
| type | String | 否 | 水印类型:IMAGE(图片)，QRCODE（二维码），TEXT(文字)默认文字水印 |
| content | String | 否 | 文字水印  |
| imageBase64 | String | 否 | 水印图片（Base64格式） |
| fontSize | int | 否 | 字体大小 |
| color | String | 否 | 字体颜色（16进制颜色值,需携带#） |
| rotateAngle | Double | 否 | 旋转角度 |
| transparency | Double | 否 | 透明度 |
| scaling | Double | 否 | 图片缩放比例 |
| location | String | 否 | 水印位置：UPPER_LEFT（左上角），UPPER_RIGHT（右上角），LOWER_LEFT（左下角），LOWER_RIGHT（右下角），MIDDLE_CENTER（居中），TILE（平铺），FILL（填充）|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /document/addwatermark HTTP/1.1
Host: privopen.qiyuesuo.me
Content-Type: application/json
x-qys-accesstoken: KgyBm8d5j3
x-qys-timestamp: 0
x-qys-signature: e19dd29e1286024b2cbc98f743c432d6

{
  "documentId":2621273342019834156,
  "type": "IMAGE",
  "imageBase64": "iVBORw0KGgoAAAANSU",
  "location": "LOWER_LEFT",
  "scaling" : 2.0
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

### 1.1.4 创建合同

描述：调用此接口来创建合同。可以选择是否发起合同，如果选择不发起合同，则可以继续修改任意合同信息，此时合同是草稿状态；如果选择发起合同，则签署方收到签署通知即可登录签署合同，合同发起后不允许添加合同文档。

#### 1.1.4.1 自定义创建合同

描述：自定义创建合同。可以指定合同文档、合同签署方、签署位置、认证方式等信息。在“创建合同文档”后调用此接口来创建合同（此接口为旧接口，不推荐使用，推荐使用接口“用业务分类创建合同”）。

Request URL： /contract/create

Request Method： POST

Content-Type： application/json

参数：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| subject | String | 是 | 合同名称 |
| ordinal | Boolean | 否 | 是否顺序签署，默认为false |
| sn | String | 否 | 合同编号（对接方系统中的业务编号） |
| description | String | 否 | 合同描述 |
| categoryId | String | 否 | 业务分类ID |
| categoryName | String | 否 | 业务分类名称，如果分类ID为空，则用此参数确定业务分类 |
| send | Boolean | 否 | 是否发起合同，默认true。（true：立即发起；false：保存为草稿） |
| documents | Array[Long] | 是 | 合同文档ID的集合，一个合同可以包含多个文档，但一个文档只能属于一个合同 |
| expireTime | String | 否 | 合同过期时间；格式：yyyy-MM-dd HH:mm:ss。过期未结束签署，则作废，不传该参数则默认使用控制台配置的过期时间。 |
| creatorName | String | 否 | 合同创建人姓名 |
| creatorContact | String | 否 | 合同创建人手机号码 |
| tenantName | String | 否 | 发起方名称 |
| signatories | Array[Signatory] | 否 | 签署方，如果未指定签署方，在合同签署完成后须调用“合同完成”接口主动结束合同 |
| businessData | String | 否 | 用户的业务数据|
| bizId | String | 否 | 合同的唯一标识，由调用方传入 |
| isCloudSign | Boolean | 否 | 是否公有云签署，默认私发私签 |


Signatory（签署方）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantType | String | 是 | 签约主体类型：CORPORATE（平台企业）， INNER_COMPANY（内部企业），COMPANY（外部企业），PERSONAL（个人） |
| tenantName | String | 是 | 签约主体名称 |
| receiverName | String | 否 | 接收人姓名 |
| contact | String | 否 | 接收人联系方式 |
| faceAuthSign | Boolean | 否 | 是否人脸识别签署，默认不需要人脸识别（仅签署方类型为个人时有效） |
| serialNo | Integer | 是 | 签署顺序（从1开始) |
| actions | Array[Action] | 否 | 签署动作 |
| categoryId | String | 否 | 业务分类ID，接收方是内部企业时，可指定业务分类 |
| categoryName | String | 否 | 业务分类名称，接收方是内部企业时，可指定业务分类 |
| faceAuthWay | String | 否 | 人脸识别签署失败后的降级认证，DEFAULT（不允许），IVS（手机三要素），BANK（银行卡四要素） |

Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 否 | 签署顺序（从1开始），若有多个签署动作，此字段必填 |
| sealId | String | 否 | 印章ID，指定企业签章所用印章 |
| sealIds | String | 否 | 指定印章，格式：[123123123213,123213213213]，预签署页面指定签署位置时，限制使用的印章 |
| actionOperators | Array[ActionOperator] | 否 | 签署人（法定代表人签字无需填写该项） |
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
| documentId | Long | 是 | 合同文档ID |
| rectType | String | 是 | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章） |
| page | Integer | 否 | 签署位置所在页数，坐标指定位置时必填 |
| keyword | String | 否 | 关键字，关键字指定位置时必填 |
| keywordIndex | Integer | 否 | 第几个关键字,0:全部,-1:最后一个,其他:第keyIndex个,默认为1 |
| offsetX | Double | 否 | X轴坐标，按坐标时必填；按关键字时选填 |
| offsetY | Double | 否 | Y轴坐标，坐标定位时必传，关键字定位时选传 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| contractId | String | 合同ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /contract/create HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json
Cache-Control: no-cache

{
	"subject": "接口-指定签署位置-两方签署",
	"send": true,
	"documents": [
		"2581422495428091905"
	],
	"tenantName": "上海契约锁网络科技有限公司",
	"signatories": [{
			"tenantType": "CORPORATE",
			"tenantName": "上海契约锁网络科技有限公司",
			"contact": "10000000000",
			"serialNo": 1,
			"actions": [{
				"type": "CORPORATE",
				"name": "企业签章",
				"serialNo": 1,
				"locations": [{
						"documentId": "2581422495428091905",
						"rectType": "SEAL_CORPORATE",
						"page": 1,
						"offsetX": 0.20,
						"offsetY": 0.80,
						"actionName": "企业签章"
					},
					{
						"documentId": "2581422495428091905",
						"rectType": "SEAL_CORPORATE",
						"keyword": "任务",
						"keywordIndex": 1,
						"actionName": "企业签章"
					}
				]
			}]
		}
	],
	"creatorName": "张三",
	"creatorContact": "15000000000"
}

```

响应示例：

```javascript
{
  "code": 0,
  "contractId": "2407446180688510983",
  "message": "SUCCESS"
}
```

#### 1.1.4.2 用业务分类创建合同

描述：根据业务分类创建合同。业务分类中配置的模板将自动生成合同文档，同时也可使用业务分类中配置好的签署流程、签署位置、认证方式等信息。

Request URL： /contract/createbycategory

Request Method： POST

Content-Type： application/json

参数：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| subject | String | 是 | 合同名称 |
| sn | String | 否 | 合同编号，对接方系统中的业务编号 |
| description | String | 否 | 合同描述 |
| categoryId | String | 否 | 业务分类ID |
| categoryName | String | 否 | 业务分类名称，如果分类ID为空，则用此参数确定业务分类 |
| send | Boolean | 否 | 是否发起合同，默认true。（true：立即发起；false：保存为草稿） |
| documents | Array[Long] | 否 | 合同文档ID的集合（一个文档只能属于一个合同） |
| expireTime | String | 否 | 合同过期时间；格式：yyyy-MM-dd HH:mm:ss，过期未结束签署，则作废，不传该参数则默认使用控制台配置的过期时间。 |
| endTime | String | 否 | 合同终止时间；格式：yyyy-MM-dd ，系统将时间调至传入时间的23时59分59秒。 |
| creatorName | String | 否 | 合同创建人姓名 |
| creatorContact | String | 否 | 合同创建人手机号码 |
| tenantName | String | 否 | 发起方名称 |
| signatories | Array[Signatory] | 否 | 签署方，为空时在合同签署完成后需要调用接口“封存合同”主动结束合同 |
| documentParams | Array[DocumentParam] | 否 | 模板参数 |
| businessData | String | 否 | 用户的业务数据|
| bizId | String | 否 | 合同的唯一标识，由调用方生成 |
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
| tenantType | String | 是 | 签约主体类型：CORPORATE（平台企业）， INNER_COMPANY（内部企业），COMPANY（外部企业），PERSONAL（个人） |
| tenantName | String | 是 | 签约主体名称 |
| receiverName | String | 否 | 接收人姓名 |
| contact | String | 否 | 接收人联系方式 |
| faceAuthSign | Boolean | 否 | 是否人脸识别签署，默认不需要人脸识别（仅签署方类型为个人时有效） |
| serialNo | Integer | 是 | 签署顺序（从1开始) |
| actions | Array[Action] | 否 | 签署动作,业务分类非预设且签署方为发起方时，使用用户传入的签署动作，其余情况使用业务分类的配置 |
| categoryId | String | 否 | 业务分类ID，接收方是内部企业时，可指定业务分类 |
| categoryName | String | 否 | 业务分类名称，接收方是内部企业时，可指定业务分类 |
| remind | Boolean | 否 | 是否发送消息提醒，默认为true |
| cardNo | String | 否 | 身份证号，用于指定个人用户认证时的身份证号 |

人脸识别签署注意：
>业务分类中未预设签署方，是否人脸识别签署由调用方指定；业务分类中预设了个人签署方，是否需要人脸识别签署根据业务分类的配置确定<br>

DocumentParam（模板参数）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String | 是 | 模板参数名称 |
| value | String | 是 | 模板参数值 |

当使用HTML模板时，参数value注意事项如下：
>参数类型是单行文本时，value大小不超过300;<br>
> 参数类型是日期时，value格式为：yyyy-MM-dd，如：2019-06-04;<br>
> 参数类型是身份证号，value只能是15或18位的数字或字母，如：123456789123456789;<br>
> 参数类型是单选，value只能是单选的选项,如：val1;<br>
> 参数类型是多选，value只能是多选的选项，多个value用逗号隔开，如：val1,val2;<br>
> 参数类型是表格，可以动态添加行，暂不支持动态添加列，value格式是一维数组，数组的每项对应表格的每行，每行格式是key-value格式，key是表格每列的列名如：[{\"key1\":\"1\",\"key2\":\"2\",\"key3\":\"3\",\"key4\":\"4\"},{\"key1\":\"5\",\"key2\":\"6\",\"key3\":\"7\",\"key4\":\"8\"}]。

Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 是 | 签署顺序（从1开始） |
| sealId | String | 否 | 印章ID，指定企业签章所用印章 |
| sealIds | String | 否 | 指定印章，格式：[123123123213,123213213213] |
| autoSign | Boolean | 否 | 是否自动签署 |
| actionOperators | Array[ActionOperator] | 否 | 签署人（法定代表人签字无需填写该项） |
| locations | Array[SignatoryRect] | 否 | 签署位置 |

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
| page | Integer | 否  按坐标指定位置时：必传| 签署页码，坐标指定位置时必须 |
| keyword | String | 否  按关键字指定位置时：必传 | 关键字，关键字指定位置时必须 |
| keywordIndex | Integer | 否 | 第几个关键字,0:全部,-1:最后一个,其他:第keyIndex个,默认为1 |
| offsetX | Double | 否  按坐标时：必传 按关键字时：选传 | X轴坐标，坐标定位时必传，关键字定位时选传 |
| offsetY | Double | 否  按坐标时：必传 按关键字时：选传 | Y轴坐标，坐标定位时必传，关键字定位时选传 |

WaterMarkContent（水印配置）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 水印类型：TEXT(文本),IMAGE(图片) |
| content | String | 水印类型为文本时必填 | 文字内容 |
| imageBytes | byte[] | 水印类型为图片时必填 | 图片水印。只支持png格式 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| contractId | String | 合同ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /contract/createbycategory HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json
Cache-Control: no-cache

{
    "subject": "分类创建合同-1",
    "ordinal": true,
    "sn": "789456",
    "categoryId": "2512855063730495489",
    "send": "true",
    "documents": [
        "2536123126066663445"
    ],
    "creatorName": "阿达",
    "creatorContact": "18000000000",
    "extraSign": false,
    "mustSign": true,
    "signatories": [
        {
            "tenantType": "CORPORATE",
            "tenantName": "上海泛微网络科技股份有限公司",
            "actions": [
                {
                    "type": "CORPORATE",
                    "name": "企业签章1",
                    "serialNo": "1",
                    "sealIds": "[2538259596691300494]",
                    "actionOperators": [
                        {
                            "operatorContact": "12354623562"
                        }
                    ],
                    "locations":[{
                    	"documentId":"2536123126066663445",
                    	"rectType":"SEAL_CORPORATE",
                    	"page":1,
                    	"offsetX":0.01,
                    	"offsetY":0.01,
                    	"actionName":"企业签章1"
                    }]
                },
                {
                    "type": "CORPORATE",
                    "name": "企业签章2",
                    "serialNo": "2",
                    "sealIds": "[2535730334467916016]",
                    "actionOperators": [
                        {
                            "operatorContact": "14561335412"
                        }
                    ],
                    "locations":[{
                    	"documentId":"2536123126066663445",
                    	"rectType":"SEAL_CORPORATE",
                    	"page":1,
                    	"offsetX":0.05,
                    	"offsetY":0.05,
                    	"actionName":"企业签章1"
                    }]
                }
            ]
        }
    ]
}

```

响应示例：

```javascript
{
  "code": 0,
  "contractId": "2407446180688510983",
  "message": "SUCCESS"
}
```

#### 1.1.4.3 编辑合同

描述：编辑草稿状态的合同，保留旧的模版参数

Request URL： /contract/createRetainParams

Request Method： POST

Content-Type： application/json

参数：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| id | Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| subject | String | 是 | 合同名称 |
| documents | Array[Long] | 是 | 合同文档ID的集合，一个合同可以包含多个文档，但一个文档只能属于一个合同 |
| expireTime | String | 否 | 合同过期时间；格式：yyyy-MM-dd HH:mm:ss。过期未结束签署，则作废，不传该参数则默认使用控制台配置的过期时间。 |
| sn | String | 否 | 合同编号，对接方系统中的业务编号 |
| creatorName | String | 否 | 合同创建人姓名 |
| creatorContact | String | 否 | 合同创建人手机号码 |
| tenantName | String | 否 | 发起方名称 |
| signatories | Array[Signatory] | 否 | 签署方 |
| send | Boolean | 否 | 是否立即发起合同，默认true。（true：立即发起；false：保存为草稿） |
| businessData | String | 否 | 用户的业务数据 |

Signatory（签署方）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantType | String | 是 | 签署方类型：CORPORATE（平台企业）， INNER_COMPANY（内部企业），COMPANY（外部企业），PERSONAL（个人） |
| tenantName | String | 是 | 签署方名称 |
| receiverName | String | 否 | 接收人姓名 |
| contact | String | 否 | 接收人联系方式 |
| serialNo | Integer | 是 | 签署顺序（从1开始) |
| actions | Array[Action] | 是 | 签署动作 |
| sponsor |Boolean | 是 | 是否是发起方 |
| locations | Array[SignatoryRect] | 否 | 签署位置 |

Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称（此参数与签署位置SignatoryRect.actionName对应,不重复就行） |
| serialNo | Integer | 否 | 签署顺序（从1开始） |
| actionOperators | Array[ActionOperator] | 否 | 签署人（法定代表人签字无需填写该项） |

ActionOperator（签署人）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operatorName | String | 否 | 签署人姓名 |
| operatorContact | String | 是 | 签署人联系方式 |

SignatoryRect（签署位置）

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 合同文档ID |
| rectType | String | 是 | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章） |
| page | Integer | 否 | 签署页码，坐标指定位置时必须 |
| keyword | String | 否 | 关键字，按关键字指定位置时：必传 |
| offsetX | Double | 否 | X轴坐标，按坐标时必传 按关键字时选传 |
| offsetY | Double | 否 | Y轴坐标，按坐标时必传 按关键字时选传 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| contractId | String | 合同ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /contract/createRetainParams HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json
Cache-Control: no-cache

{
	"id": "2407430103157090373",
	"subject": "测试合同",
	"sn": "1234567",
	"expireTime": "2018-12-30",
	"documents": ["2407437103157690373"],
	"creatorName": "张三",
	"creatorContact": "10020012345"
}

```

响应示例：

```javascript
{
  "code": 0,
  "contractId": "2407446180688510983",
  "message": "SUCCESS"
}
```

#### 1.1.4.4 预签署页面

描述：获取预签署页面链接，用户可打开页面，通过拖动左侧签章来指定签署位置，也可以填写模板参数（如果需要填写模板参数），通过点击“保存草稿”按钮来保存签署位置和模板参数；也可通过“确定”按钮来发起合同。<br>

Request URL：/contract/presignurl

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID和bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| operable | Boolean | 否 | 是否可操作（发起、填参等），默认为true |
| panel | String | 否 | 模块，包括CONTRACT（全文），POINT（要点），PARAMS（参数对比） |
| tag | String | 否 | 要点标签，当模块是“要点”时，可以指定标签，如“履约计划”、“普通要点”；标签在私有云系统中维护； |

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |
| presignUrl | String | 预签署页面链接；链接有效期30分钟 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /contract/presignurl?contractId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
```

响应示例:

```javascript
{
  "code": 0,
  "presignUrl": "http://privapp.qiyuesuo.me/appoint?viewToken=f2a56a74",
  "message": "SUCCESS"
}
```

#### 1.1.4.5 发起合同

描述：调用此接口来发起草稿状态的合同。合同发起后，签署方将收到签署通知签署合同。<br><br>

Request URL：/contract/send

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/send HTTP/1.1
Host: hostname
x-qys-timestamp: 0
x-qys-signature: c41e16fa3fe3eeb65ed5f1fa3db49
x-qys-accesstoken: 17RKme43Mc
Content-Type: application/json

{
	"contractId":"2563668791491915803"
}

```

响应示例:

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```
#### 1.1.4.6 草稿合同填参

描述：调用此接口用于填写草稿状态的合同参数，同时保留旧的参数。<br>

Request URL：/contract/fillparams

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| documentParams | List&lt;FillDocumentParam&gt; | 否 | 合同文档参数 |
FillDocumentParam
| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String | 是 | 参数名称 |
| value | String | 是 | 参数值|

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP
POST /contract/fillparams HTTP/1.1
Host: hostname
x-qys-timestamp: 0
x-qys-signature: c41e16fa3fe3eeb65ed5f1fa3db49
x-qys-accesstoken: 17RKme43Mc
Content-Type: application/json

{
	"contractId": 2611141940251005036,
	"documentParams": [
		{
			"name": "乙方姓名2",
			"value": "邓茜茜"
		},
		{
			"name": "乙方姓名3",
			"value": "邓茜"
		}
		]
}
```

响应示例:

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```

### 1.1.5 删除合同文件

#### 1.1.5.1 解绑文件
描述：调用此接口可以解绑合同文档。合同必须是未签署状态（草稿、签署中或拟定中状态），非草稿状态合同至少保留一个文件。

Request URL： /document/unbind

Request Method： POST

Content-Type： application/json
参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| documentIds | Long[] | 是 | 文档ID数组 |
响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST  /document/unbind HTTP/1.1
Host:hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json
Cache-Control: no-cache

{
    "contractId": 2609399471916188092,
    "documentIds": [2609399451108245936,2611857982808252441]
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

## 1.2 合同签署

### 1.2.1 静默签署


#### 1.2.1.1 运营方签署

描述：通过该接口，运营方可以自动实现合同的签署，无需展现签署页面。

Request URL： /contract/signbyplatform

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| stampers | Array[Stamper] | 否 | 签署信息，详情参考Stamper。若为空，则为不可见签名 |
| noSignAllKeyword | Boolean | 否 | 不签署所有关键字位置，默认为true，即只签署第1个关键字 |

Stamper

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |
| type | String | 是 | 签署类型：SEAL_CORPORATE（公章）,TIMESTAMP（时间戳）,ACROSS_PAGE（骑缝章） |
| sealId | String | 否 | 印章ID |
| page | int | 否 | 签署位置所在页码，从1开始，，坐标定位时必传 |
| allPage | Boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 签署位置在页面宽的占比，取值0~1，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 签署位置在页面高的占比，取值0~1，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

印章位置说明：
>印章可见时，需要指定印章的坐标位置，坐标位置有以下两种表现方式：<br>
>
>有关键字则优先使用关键字定位<br>
>1、用关键字确定坐标：<br>
>keyword：关键字。<br>
>x：横坐标偏移量；默认合同页的宽为1，所以取值范围是(-1, 1)。<br>
>y：纵坐标偏移量；默认合同页的高为1，所有取值范围是(-1, 1)。<br>
>找到keyword的坐标(X, Y)，再加上偏移量(x, y)，最终得到的坐标是(X+x, Y+y)。<br>
>坐标原点是合同页的左下角，坐标是指印章图片的左下角的坐标。<br><br>
>2、直接确定坐标：<br>
>page：印章所在页码；从1开始。<br>
>x：横坐标；默认合同页的宽为1，所以取值范围是(0, 1)。<br>
>y：纵坐标；默认合同页的高为1，所以取值范围是(0, 1)。<br>
>由page确定页码，由(x, y)确定坐标。<br>
>坐标原点是合同页的左下角，坐标是指印章图片的左下角的坐标。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signbyplatform HTTP/1.1
Host:hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json
Cache-Control: no-cache

{
	"contractId": "2407500906296528900",
	"stampers": [{
		"type": "SEAL_CORPORATE",
		"documentId": "2407500358589952027",
		"sealId": "2407361029375676423",
		"page": 1,
		"x": 0.5,
		"y": 0.5
	}]
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.1.2 公司公章签署

描述：运营方可以调用该接口直接签署公司公章，无需展现签署页面（只能签署内部企业的公章）。

Request URL： /contract/signbycompany

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantName | String | 是 | 公司名称 |
| stampers | Array[Stamper] | 否 | 签署位置，为空时签署不可见签名，参考【Stamper】 |
| noSignAllKeyword | Boolean | 否 | 不签署所有关键字位置，默认为true，即只签署第1个关键字 |
| useDefaultSeal | Boolean | 否 | 是否使用默认印章签署，默认true（仅在已设置签署位置未设置印章的情况下生效） |

Stamper

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |
| type | String | 是 | 签章类型：SEAL_CORPORATE（公章）,ACROSS_PAGE（骑缝章）,TIMESTAMP（时间戳） |
| sealId | String | 是 | 印章ID |
| page | int | 否 | 签署页码，从1开始，坐标定位时必传 |
| allPage | Boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 签署位置在页面宽的占比，取值0~1，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 签署位置在页面高的占比，取值0~1，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- |-------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signbycompany HTTP/1.1
Host: hostname
Content-Type: application/json
x-qys-accesstoken: DNq3uuJesv
x-qys-timestamp: 0
x-qys-signature: 4501cf3255ba2e87cef3458827bc7677

{
	"contractId":"2456446599913828356",
	"tenantName":"测试企业",
	"stampers": [
			{
				"documentId":"2456446523330031618",
				"type": "SEAL_CORPORATE",
				"sealId":"2472032341465210888",
				"page":1,
				"x":0.1,
				"y":0.1
			}
			
		]
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.1.3 法定代表人签署

描述：签署法人章。

Request URL： /contract/signbylegalperson

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID和bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantName | String | 是 | 公司名称 |
| stampers | Array[Stamper] | 是| 签署信息，详情参考Stamper。 |

Stamper

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |
| type | String | 是 | 签章类型：SEAL_CORPORATE（法人章）,TIMESTAMP（时间戳） |
| page | int | 否 | 签署位置所在页码，从1开始 |
| allPage | Boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 签署位置在页面宽的占比，取值0~1 |
| y | float | 否 | 签署位置在页面高的占比，取值0~1 |
| keyword | String | 否 | 定位关键字|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- |-------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signbycompany HTTP/1.1
Host: hostname
x-qys-accesstoken: DNq3uuJesv
x-qys-timestamp: 0
x-qys-signature: 4501cf3255ba2e87cef3458827bc7677
Content-Type: application/json

{
	"contractId":"2456446599913828356",
	"tenantName":"测试企业",
	"stampers": [
			{
				"documentId":"2456446523330031618",
				"type": "SEAL_CORPORATE",
				"page":1,
				"x":0.1,
				"y":0.1
			}
			
		]
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.1.4 个人签署

描述：运营方可以调用该接口直接签署个人签名，无需展现签署页面（只能签署内部员工）。

Request URL： /contract/signbyperson

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID和bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantName | String | 是 | 签署人姓名 |
| contact | String | 是 | 签署人手机号/邮箱地址 |
| cardNo | String | 否 | 签署人身份证号码 |
| generatePersonSeal | Boolean | 否 | 是否自动根据用户名生成个人签名，已存在则不生成，默认不自动生成 |
| stampers | Array[Stamper] | 是| 签署信息，详情参考Stamper。 |

Stamper

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |
| type | String | 是 | 签章类型：SEAL_PERSONAL（个人签名）,TIMESTAMP（时间戳） |
| sealStr | int | 否 | 签名图片的base格式字符串；为空时取用户已有签名图片 |
| page | int | 否 | 签署位置所在页码，从1开始 |
| allPage | Boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 签署位置在页面宽的占比，取值0~1 |
| y | float | 否 | 签署位置在页面高的占比，取值0~1 |
| keyword | String | 否 | 定位关键字|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- |-------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signbyperson HTTP/1.1
Host: hostname
x-qys-accesstoken: DNq3uuJesv
x-qys-timestamp: 0
x-qys-signature: 4501cf3255ba2e87cef3458827bc7677
Content-Type: application/json

{
	"contractId":"2456446599913828356",
	"tenantName":"张三",
	"contact":"13111111111",
	"stampers": [
			{
				"documentId":"2456446523330031618",
				"type": "SEAL_PERSONAL",
				"page":1,
				"x":0.1,
				"y":0.1
			}
			
		]
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.1.5 合同审批

描述：调用该接口对签署方下的审批节点进行审批。<br><br>

Request URL：/contract/audit

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID和bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantName | String | 否 | 公司名称，默认发起方公司 |

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/audit HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json

{
	"contractId":"2534936696674701362",
	"tenantName":"上海泛微网络科技股份有限公司"
}

```

响应示例:

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.1.6 公司用户无外观签署

描述：为合同加盖无外观签名。使用场景：运营方已在合同文档加盖印章图片，需要契约锁在对应位置添加电子签名。

Request URL：/contract/signbycompanywithoutappearance

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantName | String | 是 | 签署方姓名 |
| stampers | Array[AppearanceStamper] | 是 | 签署信息，详情参考AppearanceStamper。 |

AppearanceStamper

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |
| width | int | 是 | 无外观占位图宽度,取值1~200 |
| height | int | 是 | 无外观占位图高度，取值1~200 |
| page | int | 否 | 签署位置所在页码，从1开始，坐标定位时必传 |
| allPage | Boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 签署位置在页面宽的占比，取值0~1，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 签署位置在页面高的占比，取值0~1，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- |-------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signbycompanywithoutappearance HTTP/1.1
Host: hostname
x-qys-timestamp: 1550473876172
x-qys-signature: 59369634a6d9452455644595b46b9a8
x-qys-accesstoken: faIxUyhnfK
Content-Type: application/json

{
    "contractId": "2563928333624348773",
    "tenantName": "上海契约锁网络科技有限公司",
    "stampers": [
        {
            "documentId": "2563928307317674081",
            "page":"1"，
            "x": "0.1",
            "y": "0.1",
            "width":20,
            "height":40
        }
    ]
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.1.7 个人用户无外观签署

描述：为合同加盖无外观签名。使用场景：运营方已在合同文档加盖印章图片，需要契约锁在对应位置添加电子签名。

Request URL：/contract/signbypersonwithoutappearance

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantName | String | 是 | 签署人姓名 |
| contact | String | 是 | 签署人联系方式，手机号码或邮箱地址 |
| cardNo | String | 否 | 签署人身份证号码 |
| stampers | Array[AppearanceStamper] | 是| 签署信息，详情参考AppearanceStamper。 |

AppearanceStamper

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |
| width | int | 是 | 无外观占位图宽度，取值1~200 |
| height | int | 是 | 无外观占位图高度，取值1~200 |
| page | int | 否 | 签署位置所在页码，从1开始，坐标定位时必传 |
| allPage | Boolean | 否 | 是否签署所有页面 |
| x | float | 否 | 横坐标，取值0~1，坐标定位时必传，关键字定位时选传 |
| y | float | 否 | 纵坐标，取值0~1，坐标定位时必传，关键字定位时选传 |
| keyword | String | 否 | 定位关键字|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- |-------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signbypersonwithoutappearance HTTP/1.1
Host: hostname
x-qys-timestamp: 1550432876172
x-qys-signature: 59369634a6d92ewc4531644595b46b9a8
x-qys-accesstoken: foueYUphnfK
Content-Type: application/json

{
    "contractId": "2563917698652516731",
    "tenantName": "张大壮",
    "contact":"12312378910",
    "stampers": [
        {
            "documentId": "2563915937644622199",
            "page": "2",
            "x": "0.5",
            "y": "0.5",
            "width":20,
            "height":20
        },
        {
           "documentId": "2563915937644622199",
           "keyword":"时间",
           "x": "0.0",
           "y": "0.0",
           "width":20,
           "height":20
        }
    ]
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

### 1.2.2 页面签署

#### 1.2.2.1 合同签署页面

描述：对于签署中的合同，可以调用此接口来获取签署页面链接，用户可以打开签署链接签署合同。如果传入的签署方不存在，则自动创建签署方。此链接的有效期为30分钟。<br><br>

Request URL：/contract/signurl

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantId | Long | 否 | 签署方ID（公司ID/个人ID），和tenantName不能同时为空 |
| tenantName | String | 否 | 签署方名称，和tenantId不能同时为空 |
| tenantType | String | 是 | 签署方类型：CORPORATE（平台企业）,INNER_COMPANY（内部企业）,COMPANY（外部企业）,PERSONAL（个人）; |
| receiverName | String | 否 | 接收人名称(在签署方类型为企业的时候用到) |
| contact | String | 否 | 签署方的联系方式，签署方为个人时必须 |
| expireTime | Integer | 否 | 链接过期时间，取值范围：5分钟 - 3天，默认30分钟，单位（秒） |
| cardNo | String | 否 | 证件号码：个人/公司证件号 |
| canLpSign | String | 否 | 是否可以同时签署法人章：1（是）;0（否），默认否 |
| actions | Array[Action] | 否 | 签署动作,添加发起方时签署动作必填 |
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
| locations | Array[SignatoryRect] | 否 | 签署位置 |
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

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |
| signUrl | String | 签署合同的页面链接；链接有效期默认30分钟 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signurl HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json

{
	"contractId":"2446670990297247832",
	"tenantName":"测试公司",
	"tenantType":"COMPANY",
	"receiverName":"杨丽",
	"contact":"15000000000",
  	"callbackParam": "1231342423435354",
  	"callbackHeader": "{\"param1\":\"val1\"}",
  	"callbackPage": "https://www.baidu.com/",
  	"locations": [
        {
          "documentId": 2494810528664281090,
          "rectType": "SEAL_CORPORATE",
          "keyword": "乙方电子签名（签字）:"
        },
        {
           "documentId": "2494810528664281090",
           "rectType": "SEAL_CORPORATE",
           "page": 1,
           "offsetX": "0.5",
           "offsetY": "0.5"
        }
  ]
}

```

响应示例:

```javascript
{
  "code": 0,
  "signUrl": "http://privapp.qiyuesuo.me/sign?viewToken=OGZkYThhYWUtNWU1Mi00ZDhkYmZTZl",
  "message": "SUCCESS"
}
```

### 1.2.3 签署通知

#### 1.2.3.1 发送签署通知

描述：对于签署中的合同，可以调用此接口来通知用户签署合同。如果传入的签署方不存在，则自动创建签署方。短信上的签署链接永久有效。<br>

Request URL：/contract/signurl/send

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| tenantId | String | 否 | 签署方ID（公司ID/个人ID），和tenantName不能同时为空 |
| tenantName | String | 否 | 签署方名称，和tenantId不能同时为空 |
| tenantType | String | 是 | 签署方类型：CORPORATE（平台企业）,INNER_COMPANY（内部企业）,COMPANY（外部企业）,PERSONAL（个人）; |
| receiverName | String | 否 | 接收人名称 |
| contact | String | 是 | 签署方的联系方式 |
| cardNo | String | 否 | 证件号码：个人/公司证件号 |
| callbackParam | String | 否 | 回调参数，用户签署完成后或退回合同，将该参数通过回调函数返回（旧参数，建议使用业务分类中配置的回调） |
| callbackHeader | String | 否 | 回调Header参数，Json格式（旧参数，建议使用业务分类中配置的回调） |
| actions | Array[Action] | 否 | 签署动作,添加发起方时签署动作必填 |
| locations | Array[SignatoryRect] | 否 | 签署位置，会覆盖已有的签署位置 |

Action（签署动作）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| type | String | 是 | 签署动作类型：CORPORATE（企业签章），PERSONAL（个人签字），LP（法定代表人签字），AUDIT（审批） |
| name | String | 是 | 签署动作名称 |
| serialNo | Integer | 是 | 签署顺序（从1开始） |
| sealId | String | 否 | 印章ID，指定企业签章所用印章 |
| sealIds | String | 否 | 指定印章，格式：[123123123213,123213213213] |
| locations | Array[SignatoryRect] | 否 | 签署位置 |
| actionOperators | Array[ActionOperator] | 否 | 签署动作操作人 |

签署位置（SignatoryRect）

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | Long | 是 | 合同文档ID |
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

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/signurl/send HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json

{
	"contractId":"244667099029724528",
	"tenantName":"测试公司",
	"tenantType":"COMPANY",
	"receiverName":"杨丽",
	"contact":"15000000000",
  	"callbackParam": "1231342423435354",
  	"callbackHeader": "{\"param1\":\"val1\"}",
  	"locations": [
        {
          "documentId": 2494810528664245853,
          "rectType": "SEAL_PERSONAL",
          "keyword": "乙方电子签名（签字）:"
        },
        {
          "documentId": 2494810528664245853,
          "rectType": "TIMESTAMP",
          "keyword": "乙方电子签名（签字）:",
          "offsetX": 0.06，
          "offsetY": 0.01
        }
  ]
}

```

响应示例:

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

####	1.2.3.2 合同催签

描述: 短信通知合同接收方签署合同，通知对象包括接收方经办人和签署中的人。合同状态必须为：“填参中”，“签署中”，“作废中”。

Request URL：/contract/notify

Request Method：GET

参数：

| 名称        | 类型   | 是否必须 | 说明       |
| ----------- | ------ | -------- | ---------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

返回值：

| 名称    | 类型   | 说明            |
| ------- | ------ | --------------- |
| code    | Int    | 响应码，0为成功 |
| message | String | 响应信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```http

GET /contract/notify?contractId=2555664204783751234 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```http

{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.3.3 签署方催签

描述: 短信通知签署方签署合同，通知对象为签署方经办人。签署方状态必须为“签署中”。签署方状态必须为：“填参中”，“签署中”，“作废中”。

Request URL：/contract/press/operator

Request Method：GET

参数：

| 名称        | 类型   | 是否必须 | 说明                                          |
| ----------- | ------ | -------- | --------------------------------------------- |
| signatoryId | String | 是       | 签署方ID，合同详情中的Signatory（签署方）的id |

返回值：

| 名称    | 类型   | 说明            |
| ------- | ------ | --------------- |
| code    | Int    | 响应码，0为成功 |
| message | String | 响应信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```http

GET /contract/press/operator?signatoryId=2495178212620112083 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```http

{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.2.3.4 签署节点催签

描述: 短信通知签署节点的操作人签署合同，通知对象为签署节点的操作人。签署节点状态必须为“签署中”。签署节点状态必须为：“填参中”，“签署中”（签署节点中“签署中”和“作废中”状态均为“签署中”）。

Request URL：/contract/press

Request Method：GET

参数：

| 名称     | 类型   | 是否必须 | 说明                                                         |
| -------- | ------ | -------- | ------------------------------------------------------------ |
| actionId | String | 是       | 签署节点ID，合同详情中的Signatory（签署方）下的Action（签署节点）的id |

返回值：

| 名称    | 类型   | 说明            |
| ------- | ------ | --------------- |
| code    | Int    | 响应码，0为成功 |
| message | String | 响应信息        |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```http

GET /contract/press?actionId=2495178212620112083 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```http

{
  "code": 0,
  "message": "SUCCESS"
}
```

### 1.2.4 签署密码校验

描述：校验用户的签署密码是否正确。要求在契约锁已维护签署密码的前提下，才能调用此接口。使用场景：可以在内部员工签署时调用此接口来进一步校验。<br>

Request URL：/contract/checkpassword

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile | String | 是 | 用户手机号 |
| signPassword | String | 是 | 签署密码 |

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| result | Boolean | 验证结果，密码正确 true；密码错误 false |
| code | Int | 响应码，0为成功 |
| message | String | 响应信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/checkpassword?mobile=15000000000&amp; signPassword=123456 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c96a1f07e
x-qys-timestamp: 0

```

响应示例:

```javascript
{
    "result": true,
    "code": 0,
    "message": "SUCCESS"
}
```

## 1.3 结束合同

### 1.3.1 封存合同

描述：完成指定合同，合同完成后不能再签署。条件：所有签署方已签署完成。使用场景：创建时未指定签署方的合同，在签署方签署完成后，需要调用此接口来完成合同。

Request URL： /contract/finish

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |


返回值:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/finish HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json

{
	"contractId":"2407500906296528900"
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

### 1.3.2 强制结束合同

#### 1.3.2.1 强制结束合同

描述：强制结束签署中的合同，结束后合同不能再签署。使用场景：如果运营方想要保证合同的有效性，又想立即结束合同，可调用此接口强制结束合同；例：一份合同发起且发起方签署后，接收方想要在线下签署合同，则可调用此接口来实现。

Request URL： /contract/finish/force

Request Method： POST

Content-Type： application/json

参数：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId| Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| companyName| String | 是 | 公司名称 |
| mobile| String | 是 | 用户手机号 |
| finishReason| String | 否 | 强制结束原因 |
| attachment| String | 否 | 附件列表，即强制结束合同的说明文档 |

参数说明：

attachment为附件列表，格式如下:

[{"id":"2495903120714977622","pages":1,"title":"附件1"},{"id":"2495903120714977621","pages":1,"title":"附件2"}]。

其中id为合同文件ID，可通过接口“用户文件创建合同文档”生成，pages为文件页数，title为文件名称。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /contract/finish/force HTTP/1.1
Host: hostname
x-qys-accesstoken: F9q0WaZj8D
x-qys-timestamp: 0
x-qys-signature: 0d6f366839790ad2483cf297d8768928
Content-Type: application/json
{
    "contractId":2525526609548046551,
    "companyName":"南京市龙眠大道信息技术有限公司（内部）",
    "finishReason":"强制结束测试 002",
    "mobile":"15850695001",
    "attachment":"[{\"id\":\"2525516910157132273\",\"pages\":1,\"title\":\"附件1\"},{\"id\":\"2525517247035241029\",\"pages\":243,\"title\":\"附件2\"}]"
}
```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.3.2.2 修改说明文档

描述：合同在强制结束时，可以提交附件（说明文档）来表明强制结束的原因，调用此接口可以修改附件。

Request URL： /contract/finish/addattachment

Request Method： POST

Content-Type: multipart/form-data


参数：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId| Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| attachment| String | 是 | 附件列表 |

attachment为附件列表，格式如下:

[{"id":"2495903120714977622","pages":1,"title":"附件1"},{"id":"2495903120714977621","pages":1,"title":"附件2"}]。

其中id为合同文件ID，可通过接口“用户文件创建合同文档”生成，pages为文件页数，title为文件名称。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | ContractFinish | 强制结束合同信息,参考ContractFinish |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

ContractFinish:
| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| contractId | Long  | 合同ID |
| companyName | String | 公司名称 |
| userName | String | 强制完成人 |
| createTime | Date | 强制完成时间 |
| finishReason | String | 强制完成原因 |
| attachment | String | 附件列表 |

请求示例：

```HTTP

POST /contract/finish/addattachment HTTP/1.1
Host: hostname
x-qys-accesstoken: F9q0WaZj8D
x-qys-timestamp: 0
x-qys-signature: 0d6f366839790ad2483cf297d8768928
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="contractId"

2554208591192801404
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="attachment"

[{"id":"2495903120714977622","pages":1,"title":"附件1"},{"id":"2495903120714977621","pages":1,"title":"附件2"}]
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "result": {
        "contractId": "2525520378737487874",
        "companyName": "南京市龙眠大道信息技术有限公司（内部）",
        "userName": "邵**",
        "createTime": "2019-01-23 10:36:56",
        "finishReason": "接口强制结束测试 002",
        "attachment": "[{\"id\":\"2525516910157132273\",\"pages\":1},{\"id\":\"2525517247035241029\",\"pages\":243}]"
    },
    "code": 0,
    "message": "SUCCESS"
}
```

### 1.3.3 作废合同

描述：调用此接口对已完成的合同发起作废，生成作废文件，并代发起方签署作废文件（在所有合同签署方签署作废文件后，合同作废完成）。

Request URL：/contract/cancel

Request Method：POST

Content-Type: multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID和bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| sealId | String | 是 | 印章ID，用于签署作废声明 |
| reason | String | 否 | 作废原因或说明 |
| removeContract | Boolean | 否 | 作废成功后是否删除原合同，默认false不删除 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/cancel HTTP/1.1
Host: hostname
x-qys-accesstoken: DNq3uuJesv
x-qys-timestamp: 0
x-qys-signature: 4501cf3255ba2e87cef3458827bc7677
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="contractId"

2548810535499390998
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="sealId"

2535730334467916016
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="reason"

作废原因
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="removeContract"

false
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```HTTP

{
 	"code": 0,
  	"message": "SUCCESS"
}
```

### 1.3.4 撤回合同

描述：撤回“签署中”或“指定中”的合同。

Request URL：/contract/recall

Request Method：POST

Content-Type: multipart/form-data


参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| reason | String | 否 | 作废原因或说明 |

返回值：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| code | Int | 响应码，0为成功 |
| message | String | 返回信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |
| 3001008 | 合同状态为已退回、已撤回、已过期、已作废不允许撤回 |

```http

POST /contract/recall HTTP/1.1
Host: hostname
x-qys-accesstoken: DNq3uuJesv
x-qys-timestamp: 0
x-qys-signature: 4501cf3255ba2e87cef3458827bc7677
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="contractId"

2546311614012928299
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="reason"

撤回
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```HTTP

{
 	"code": 0,
  	"message": "SUCCESS"
}
```

### 1.3.5 删除合同

描述：删除合同，只能删除“草稿”状态下的合同。

Request URL： /contract/delete

Request Method： POST

Content-Type: multipart/form-data

参数：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST/contract/delete HTTP/1.1
Host: hostname
x-qys-accesstoken: F9q0WaZj8D
x-qys-timestamp: 0
x-qys-signature: 0d6f366839790ad2483cf297d8768928

Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="contractId"

2566575082331874096
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```

### 1.3.6 设置合同终止时间

描述：设置合同终止时间，只能对已完成或强制结束的合同操作.终止时间必须在今天之后。

Request URL： /contract/endtime

Request Method： POST

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID与业务ID不能都为空 | 合同Id |
| bizId | String | 合同ID与业务ID不能都为空 | 业务ID |
| endtime | String | 是 | 合同终止时间，格式：yyyy-MM-dd |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /contract/endtime HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="contractId"

2604627233064177829
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="endTime"

2019-08-30
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```

## 1.4 查看、下载合同

### 1.4.1 查看合同

#### 1.4.1.1 合同详情

描述: 根据已有的合同ID，查询该合同的详细信息。

Request URL：/contract/detail

Request Method：GET

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

返回值：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |
| contract | Object | 合同详情；参照Contract |

Contract：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | Long | 合同ID |
| bizId | String | 合同的唯一标识，由调用方生成 |
| subject | String | 合同主题 |
| sn | String | 合同编号 |
| description |String | 描述 |
| ordinal | Boolean | 是否顺序签署 |
| status | String | 合同状态：DRAFT（草稿），SIGNING（签署中），COMPLETE（已完成），REJECTED(已退回)，RECALLED(已撤回)，EXPIRED(已过期)，FILLING(拟定中)，TERMINATING(作废确认中)，TERMINATED(已作废)，DELETE(已删除)，FINISHED(已完成) |
| categoryId | Long | 合同分类 |
| categoryName | String | 合同分类名称 |
| creatorId | Long | 合同具体发起人ID |
| creatorName | String | 创建人名称 |
| creatorType | String | 合同具体发起人类型,个人PERSONAL/平台CORPORATE |
| tenantId | Long | 合同发起方ID |
| tenantName | String | 发起方名称 |
| tenantType| String | 合同发起方类型:CORPORATE（平台方）,COMPANY（外部企业）,INNER_COMPANY（内部企业）,PERSONAL/个人 |
| updateTime | String | 合同最后更新时间；格式yyyy-MM-dd HH:mm:ss |
| expireTime | String | 合同过期时间；格式yyyy-MM-dd HH:mm:ss |
| createTime | String | 合同创建时间；格式yyyy-MM-dd HH:mm:ss |
| comments | String | 合同拒绝/撤回原因 |
| currentCategoryName | String | 当前用户的合同分类名称 |
| rejectable | boolean | 是否可退回 |
| recallable | boolean | 是否可撤回 |
| cancelable | boolean | 是否可作废 |
| transpondable | boolean | 是否可转发 |
| faceSign | boolean | 是否可面对面签署 |
| sponsor | boolean | 当前操作人是否属于发起方 |
| rejectCancel | boolean | 是否可拒绝作废 |
| documents | Array[Document] | 文档信息；参照Document |
| signatories | Array[Signatory] | 签署方；参照Signatory |
| businessData | String | 用户的业务数据 |
| signCode | Integer | 签署code |
| leftPrintCount | Integer | 剩余打印次数，-1表示无限制 |

Signatory：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | Long | 签署方ID |
| tenantId | String | 签署方公司ID或个人ID |
| tenantType | String | 签署方类型：CORPORATE（平台企业）,INNER_COMPANY（内部企业）,（外部企业）,PERSONAL（个人） |
| tenantName | String | 签署方名称 |
| receiverType | String | 接收人类型:CORPORATE(平台方),COMPANY(平台外部企业),INNER_COMPANY(平台企业（内部）),PERSONAL(个人) |
| receiverId | Long | 接收人ID，即可以转发合同的人 |
| receiverName | String | 接收人名称 |
| contact | String | 接收人联系方式 |
| serialNo | Integer | 签署顺序 |
| status | Boolean | 签署状态；DRAFT（草稿），SIGNING（签署中），COMPLETE（已完成） |
| sponsor | Boolean | 是否是发起方 |
| remind | Boolean | 是否发送信息提醒 |
| configured | Boolean | 是否从业务配置中得到 |
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
| id | Long | 合同文档ID |
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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /contract/detail?contractId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```javascript
{
	"code": 0,
	"contract": {
		"id": "2507446878000910395",
		"subject": "阿斯顿发生阿斯顿发生阿斯顿发阿斯顿发射点手动阀",
		"description": "",
		"sn": "",
		"ordinal": true,
		"status": "DELETE",
		"categoryId": "2507443088693272684",
		"categoryName": "测试1204",
		"creatorType": "PERSONAL",
		"creatorId": "2422042842433986573",
		"creatorName": "张三",
		"tenantType": "CORPORATE",
		"tenantId": "2422042838839468036",
		"tenantName": "上海泛微网络科技股份有限公司",
		"expireTime": "2019-01-03 23:59:59",
		"createTime": "2018-12-04 13:38:42",
		"updateTime": "2018-12-04 13:38:42",
		"currentCategoryName": "测试1204",
		"rejectable": false,
		"recallable": false,
		"cancelable": false,
		"transpondable": false,
		"faceSign": false,
		"sponsor": true,
		"rejectCancel": false,
		"documents": [{
			"id": "2507446856698040372",
			"title": "0801微博",
			"pages": 5,
			"size": "174583",
			"createTime": "2018-12-04 13:38:37",
			"antifakeCode": "B7CW",
			"dimension": {
				"width": 0,
				"height": 0,
				"type": "OTHER",
				"pixelHeight": 0,
				"pixelWidth": 0
			},
			"html": false,
			"form": false,
			"params": [{
				"id": "2536453409348575241",
				"documentId": "2536453408891396104",
				"name": "customer",
				"type": "text",
				"required": true,
				"page": 1,
				"offsetX": 0.1721871333007635,
				"offsetY": 0.8169820127899061,
				"signatory": "0",
				"updateTime": "2019-02-22 14:40:18"
			}]
		}],
		"signatories": [{
			"id": "2507446878617473085",
			"tenantType": "CORPORATE",
			"tenantId": "2422042838839468036",
			"tenantName": "上海泛微网络科技股份有限公司",
			"receiverType": "PERSONAL",
			"receiverId": "2422042842433986573",
			"receiverName": "张三",
			"contact": "15000000000",
			"serialNo": 1,
			"status": "DRAFT",
			"sponsor": true,
			"remind": true,
			"configured": true,
			"createTime": "2018-12-04 13:38:42",
			"updateTime": "2018-12-04 13:38:42",
			"faceSign": false,
			"authMode": "DEFAULT",
			"actions": [{
				"id": "2507446878835576894",
				"type": "CORPORATE",
				"name": "企业签章",
				"status": "WAITING",
				"createTime": "2018-12-04 13:38:42",
				"actionOperators": [{
						"operatorType": "ROLE",
						"operatorId": "2422042838998851590",
						"operatorName": "印章管理员"
					},
					{
						"operatorType": "COMPANY",
						"operatorId": "2422042838839468036",
						"operatorName": "上海泛微网络科技股份有限公司"
					}
				],
				"statusDesc": "等待中",
				"complete": false
			}],
			"statusDesc": "草稿",
			"complete": false
		}],
		"leftPrintCount": -1,
		"statusDesc": "已删除"
	},
	"message": "SUCCESS"
}
```

#### 1.4.1.2 合同浏览页面

描述：获取合同浏览页面链接，用户可打开链接查看合同内容，此页面内只能查看不能操作。链接有效期为30分钟。

Request URL：/contract/viewurl

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| expireTime | Integer | 否 | 链接过期时间，默认30分钟，单位（秒） |
| pageType | String | 否 | 页面预览类型：DETAIL（详情页），CONTENT（合同正文），默认DETAIL |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| viewUrl | String | 浏览链接 |


响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /contract/viewurl?contractId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP

{
  "code": 0,
  "viewUrl": "http://privapp.qiyuesuo.me/sign?viewToken=OGZkYThhYWUtNWU1MDk1LThkYzgtNzF",
  "message": "SUCCESS"
}
```

#### 1.4.1.3 合同列表

描述:  查询公司参与的合同，即查询某公司发起或签署的合同。如果不传公司信息则查询所有合同。

Request URL：/contract/list

Request Method：GET

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| companyId | String | 否 | 公司ID，ID与名称都不传默认查询所有合同 |
| companyName | String | 否 | 公司名称，ID与名称都不传默认查询所有合同 |
| selectOffset | String | 否 | 查询开始位置，默认为0 |
| selectLimit | String | 否 | 查询限制条数，默认为1000 |
| orderMode | String | 否 | 排序方式；ASC（升序），DESC（降序），默认为DESC |

返回值：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| code | Int | 响应码，0为成功 |
| message | String | 响应描述 |
| result | Object | 公司信息，参考[Result] |

Result：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| totalCount | Int | 合同总数 |
| contractList | Array[Object] | 合同列表，参考[Contract] |

Contract：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| id | Long | 合同ID |
| bizId | String | 合同的唯一标识，由调用方生成 |
| subject | String | 合同主题 |
| description |String | 描述 |
| sn | String | 合同编号 |
| ordinal | Boolean | 是否顺序签署 |
| status | String | 合同状态：DRAFT（草稿），SIGNING（签署中），COMPLETE（已完成） |
| categoryId | Long | 合同分类 |
| creatorType | String | 合同具体发起人类型,个人PERSONAL/平台CORPORATE |
| creatorId | Long | 合同具体发起人ID |
| creatorName | String | 创建人名称 |
| tenantType| String | 合同发起方类型:CORPORATE(平台方),COMPANY(平台外部企业),INNER_COMPANY(平台企业（内部）),PERSONAL(个人) |
| tenantId | Long | 合同发起方ID |
| tenantName | String | 发起方名称 |
| expireTime | Date | 合同过期时间；格式yyyy-MM-dd HH:mm:ss |
| createTime | Date | 合同创建时间；格式yyyy-MM-dd HH:mm:ss |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /contract/list?selectOffset=0&selectLimit=2&companyId=2422042838839468036 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```javascript
{
    "result": {
        "totalCount": 2422,
        "contractList": [
            {
                "id": "2550926328800313366",
                "subject": "java基础面试",
                "description": "",
                "sn": "",
                "ordinal": true,
                "status": "DRAFT",
                "categoryId": "2474948369232633863",
                "creatorType": "PERSONAL",
                "creatorId": "2422042842433986573",
                "creatorName": "阿萨德",
                "tenantType": "CORPORATE",
                "tenantId": "2422042838839468036",
                "tenantName": "上海泛微网络科技股份有限公司",
                "expireTime": "2019-04-13 23:59:59",
                "createTime": "2019-04-03 13:10:31"
            },
            {
                "id": "2550886389007241235",
                "subject": "横版多页",
                "description": "",
                "sn": "",
                "ordinal": true,
                "status": "SIGNING",
                "categoryId": "2474948369232633863",
                "creatorType": "PERSONAL",
                "creatorId": "2422042842433986573",
                "creatorName": "邓茜茜",
                "tenantType": "CORPORATE",
                "tenantId": "2422042838839468036",
                "tenantName": "上海泛微网络科技股份有限公司",
                "expireTime": "2019-04-13 23:59:59",
                "createTime": "2019-04-03 10:31:48"
            }
        ]
    },
    "code": 0,
    "message": "SUCCESS"
}
```

#### 1.4.1.4 查询指定的签署位置

描述：根据合同Id获取合同的签署位置

Request URL：/contract/query/location

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

返回值：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| code | Int | 响应码，0为成功 |
| message | String | 响应信息 |
| result |List[Signatory] | 签署方信息列表(不为空即存在签署位置) |

签署方（Signatory）:

| 名称 | 类型 |  描述 |
| -------- | -------- | -------- |
| tenantType | TenantType | 签署方类型：CORPORATE（平台企业）,INNER_COMPANY（内部企业）,（外部企业）,PERSONAL（个人） |
| tenantName | String | 签署方名称 |
| receiverName | String  | 接收人姓名 |
| contact | String | 接收人联系方式 |
| serialNo | Integer | 签署顺序（从1开始) |
| actions | List[Action] | 签署动作 |
| sponsor |Boolean |  是否是发起方 |
| locations | List[SignatoryRect] | 签署位置 |

签署位置（SignatoryRect）

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| documentId | Long | 文档Id |
| rectType | StamperType | 签章类型： SEAL_PERSONAL（个人签名）, SEAL_CORPORATE（公司公章），TIMESTAMP（时间戳）,ACROSS_PAGE（骑缝章） |
| page | Integer | 签署位置所在页数  |
| keyword | String | 关键字  |
| offsetX | Double |  X轴坐标  |
| offsetY | Double |  Y轴坐标  |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```http

GET /contract/query/location?contractId=2505670935170740268 HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9469a4baac0c91547205b93

```

响应示例：

```HTTP

{
    "result": [
        {
            "id": "2505670935271403566",
            "contractId": "2505670935170740268",
            "categoryId": "2505670260189786118",
            "tenantType": "CORPORATE",
            "tenantId": "2499564877105446918",
            "tenantName": "洁云3",
            "receiverType": "PERSONAL",
            "receiverId": "2499565462074101765",
            "receiverName": "张三",
            "contact": "15000000000",
            "serialNo": 1,
            "status": "SIGNING",
            "sponsor": true,
            "remind": true,
            "configured": false,
            "createTime": "2018-11-29 16:01:44",
            "updateTime": "2018-11-29 16:01:44",
            "receiveTime": "2018-11-29 16:01:55",
            "locations": [
                {
                    "id": "2505670980477612088",
                    "contractId": "2505670935170740268",
                    "signatoryId": "2505670935271403566",
                    "actionId": "2505670935283986479",
                    "documentId": "2505670931655913512",
                    "rectType": "SEAL_CORPORATE",
                    "page": 1,
                    "width": 0.198,
                    "height": 0.14,
                    "offsetX": 0.18,
                    "offsetY": 0.7779,
                    "fromTemplate": false,
                    "createTime": "2018-11-29 16:01:55",
                    "samePositionId": "1543478505063"
                }
            ],
            "complete": false,
            "statusDesc": "签署中"
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

#### 1.4.1.5 查询合同操作日志

描述：根据合同ID获取合同相关的操作日志

Request URL： /contract/operationlog

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| operation | String | 否 | 操作类型：DOWNLOADCONTRACT（文件下载）、PRINTCONTRACT（文件打印），默认查询下载和打印操作类型 |
| contractId | Long | 是 | 合同Id |
| operatorContact | String | 否 | 操作人联系方式 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result| List[SystemAuditLog] | 操作日志信息|

SystemAuditLog

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

Department：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 部门ID |
| companyName | String | 组织架构名称 |
| type | String | 类型：CORPORATE(总公司),CHILD(子公司),COMPANY(外部企业),SECTION(部门) |


响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

GET /contract/operationlog?operation=DOWNLOADCONTRACT&amp; contractId=2599501922709893626 HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9

```

响应示例：

```javascript
{
    "result": [
        {
            "id": "2599505559033049496",
            "entityId": "2599501922709893626",
            "operator": "张三",
            "operatorId": "2570809757352608417",
            "operation": "DOWNLOADCONTRACT",
            "createTime": "2019-08-15 14:27:01",
            "entityName": "普自项目一期A5地块场地平整工程合同模板(1).doc",
            "detailedOperation": "下载了文件：《测试文档.doc》",
            "companyId": "2578530725139492930",
            "mobile": "1500000000000",
            "departments": [
                {
                    "id": "2599504874920120482",
                    "companyName": "产品组",
                    "type": "SECTION"
                },
                {
                    "id": "2599504876165828785",
                    "companyName": "产品组",
                    "type": "SECTION"
                }
            ],
            "number": "789456123",
            "operationDesc": "文件下载"
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

#### 1.4.1.6 查询合同填参情况
描述: 获取合同参数情况，返回参数“是否填参完成” 和 所有模板填参数据。

Request URL：/contract/documentparams

Request Method：GET

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| documents | List&lt;Document&gt; | 文档参数集合 |
| sponsorComplete | Boolean | 发起方是否填参完成 |

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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /contract/documentparams?contractId=2596202673608380445 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP
{
    "code": 0,
    "documents": [
        {
            "id": "2596202625436798982",
            "title": "劳动合同_带参数",
            "params": [
                {
                    "id": "2596202625814286343",
                    "name": "乙方姓名",
                    "value": "12",
                    "type": "text",
                    "required": true,
                    "readOnly": false,
                    "paramKey": "key2",
                    "componentValue": "12",
                    "componentType": "text"
                },
                {
                    "id": "2596202626518929416",
                    "name": "身份证号",
                    "value": "12",
                    "type": "text",
                    "required": true,
                    "readOnly": false,
                    "componentValue": "12",
                    "componentType": "text"
                }
            ]
        }
    ],
    "sponsorComplete": true,
    "message": "SUCCESS"
}
```

#### 1.4.1.7 查询用户对合同的操作权限

描述：根据合同ID及用户联系方式查询操作权限。

Request URL： /contract/permission

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 合同ID与业务ID不能都为空 | 合同Id |
| bizId | String | 合同ID与业务ID不能都为空 | 业务ID |
| contractId | String | 是 | 用户联系方式 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result| ContractPermission | 合同权限信息|

ContractPermission：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| viewPermission | Boolean | 查看权限 |
| downloadPermission | Boolean | 下载权限 |
| printPermission | Boolean | 打印权限 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

GET /contract/permission HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="contractId"

2603409159703212163
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="bizId"


------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="contact"

18435186216
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "result": {
        "viewPermission": false,
        "downloadPermission": true,
        "printPermission": false
    },
    "code": 0,
    "message": "SUCCESS"
}
```

### 1.4.2 下载合同

#### 1.4.2.1 下载合同

描述：下载合同（包括所有合同文件），以压缩包(.zip)格式返回。

Request URL：/contract/download

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

请求示例：

```HTTP

GET /contract/download?contractId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Cache-Control: no-cache

```

响应示例：

```HTTP

Content-Type →application/zip
Content-Disposition →attachment;fileName=2252412945004756992.zip
```

#### 1.4.2.2 下载合同文档

描述：下载合同文档

Request URL：/document/download

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 合同文档ID |

请求示例：

```HTTP

GET /document/download?documentId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP

Content-Type →application/pdf
Content-Disposition →attachment;fileName=2252412945004756992.pdf
```

#### 1.4.2.3 批量下载合同文件

描述：批量下载合同文件，以压缩包(.zip)格式返回。注：最多下载20份合同文件，如果有一份合同下载失败，则整个下载请求失败。

Request URL：/contract/batchdownload

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractIds | String | 是 | 合同ID，多个合同用逗号隔开 |

请求示例：

```HTTP

GET /contract/batchdownload?contractIds=2560390108042498212,2559229023145640399 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP

Content-Type →application/zip
Content-Disposition →attachment;fileName=2252412945004756992.zip 
```

#### 1.4.2.4 下载当面签用户资料

描述：下载当面签用户签署时的认证资料，包括身份证正反照、手持身份证照片、签署双方合影、手持营业执照照片等内容。以压缩包(.zip)格式返回。

Request URL：/contract/face/download/zip

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

请求示例：

```HTTP

GET /contract/face/download/zip?contractId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Cache-Control: no-cache

```

响应示例：

```HTTP

Content-Type →application/zip
Content-Disposition →attachment;fileName=2252412945004756992.zip 
```

## 1.5 打印合同

### 1.5.1 防伪打印页面

描述: 获取防伪打印的页面链接（传入文档ID可以指定文档打印），打开链接可进行防伪打印。链接有效期为30分钟。

Request URL：/contract/efprinturl

Request Method：GET

Content-Type: multipart/form-data

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| documentIds | Long[] | 否 | 文档ID数组 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| printUrl | String | 合同打印链接 |


响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /contract/efprinturl?contractId=2407446180688510983&documentIds=2604894332795838519 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP

{
  "code": 0,
  "printUrl": "http://privapp.qiyuesuo.me/print?viewToken=cac77544-7-8041-d308a29c1a70",
  "message": "SUCCESS"
}
```

### 1.5.2 打印合同

#### 1.5.2.1 设置合同打印次数

描述: 根据已有的合同ID，设置该合同允许被打印的次数。

Request URL：/contract/setprintcount

Request Method：POST

Content-Type: multipart/form-data

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID 或 bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID 或 bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| count | Int | 是 | 合同可被打印的次数 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /contract/setprintcount HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="contractId"

2536042088387944536
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="count"

10
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```HTTP

{
  "code": 0,
  "message": "SUCCESS"
}

```

#### 1.5.2.2 获取合同已打印次数

描述: 根据已有的合同ID，获取该合同已经被打印的次数。

Request URL：/contract/getprintcount

Request Method：GET

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| contractId | String | 合同ID和bizId必须填写一个 | 合同ID |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |

返回值：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |
| printCount | Int | 此合同已经被打印的次数 |

请求示例：

```HTTP

GET /contract/getprintcount?contractId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP

{
  "code": 0,
  "printCount": 10,
  "message": "SUCCESS"
}
```

### 1.5.3 打印文档

#### 1.5.3.1 设置文档打印次数

描述: 设置该文档允许被打印的次数。

Request URL：/document/setprintcount

Request Method：POST

Content-Type: application/json

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| id | Long | 是 | 文档ID |
| documentPrintCount | Int | 是 | 合同可被打印的次数 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /document/setprintcount HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: application/json

[
 {
	"id": 2495178212620112083,
	"documentPrintCount": 13
  },
  {
	"id": 2495178271948542171,
	"documentPrintCount": 10
  }
]

```

响应示例：

```HTTP

{
  "code": 0,
  "message": "SUCCESS"
}
```

#### 1.5.3.2 获取文档已打印次数

描述: 获取文档打印时该文档已经被打印的次数。

Request URL：/document/getprintcount

Request Method：GET

参数：

| 名称 | 类型 | 是否必须 | 说明 |
| -------- | -------- | -------- | -------- |
| documentId | String | 是 | 文档ID |

返回值：

| 名称 | 类型 | 说明 |
| -------- | -------- | -------- |
| code | Int | 错误码，0为成功 |
| message | String | 错误信息 |
| printCount | Int | 此文档已经被打印的次数 |

请求示例：

```HTTP

GET /documentId/getprintcount?document=2495178212620112083 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP

{
  "code": 0,
  "printCount": 10,
  "message": "SUCCESS"
}
```

# 1.6 合同抄送

## 1.6.1添加抄送人

描述：为合同添加抄送人信息。

Request URL： /contract/transmit

Request Method： POST

Content-Type: application/json

参数:

| 名称         | 类型                       | 是否必须                  | 说明                         |
| ------------ | -------------------------- | ------------------------- | ---------------------------- |
| contractId   | Long | 合同ID和bizId必须填写一个 | 合同ID                       |
| bizId | String | 合同ID和bizId必须填写一个 | 合同的唯一标识，由调用方生成 |
| transmitters | List&lt;ContractTransmitItem&gt; | 是                        | 抄送人信息                   |

ContractTransmitBean(抄送人信息)

| 名称           | 类型   | 是否必须 | 说明         |
| -------------- | ------ | -------- | ------------ |
| receiverMobile | String | 是       | 抄送人手机号 |
| receiverName   | String | 是       | 抄送人姓名   |

返回值：

| 名称    | 类型   | 说明            |
| ------- | ------ | --------------- |
| code    | Int    | 错误码，0为成功 |
| message | String | 错误信息        |



请求示例：

```java
POST  /contract/transmit HTTP/1.1
Host: hostname
x-qys-accesstoken: F9q0WaZj8D
x-qys-timestamp: 0
x-qys-signature: 0d6f366839790ad2483cf297d8768928
Content-Type: application/json

{
 "contractId":"2602036082319806474",
 "transmitters":[
  	{
  		"receiverMobile":"15021504325",
		"receiverName":"邓茜茜"
  	},
  	{
  		"receiverMobile":"17621157872"
  	}
  	]
}
```

响应示例：

```java
{
  "code": 0,
  "message": "SUCCESS"
}
```

# 2 模板接口

## 2.1 模板列表

描述：根据条件获取合同模板列表。

Request URL： /template/list

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tags | String	| 否 | 标签，不传则查询全部	|
| type | String | 否 | 模板类型：ALL（所有），WORD（WORD类型模板），HTML（HTML类型模板）；默认ALL |
| pageNo | Int| 否 |	当前页，不传则默认1 |
| pageSize |Int |否 |	每页条数，不传则默认10 |
| tenantId |Long | 否 | 公司ID，默认查询平台方的模板 |
| tenantName | String | 否 | 公司名称，默认查询平台方的模板 |
| property |String | 否 |模板属性：ALL（所有），FORM（参数模板），NORMAL（普通模板）；默认ALL |
| createTimeOrder |	String | 否 | 创建时间排序方式 ；ASC（升序），DESC（降序）；默认DESC |
| thumbable | boolean | 否 | 是否需要首页缩略图，不传则默认true |


响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| page | Page | 分页信息；参照Page |
| result  | Array[Template] | 模板信息；参照Template |

Page:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| pageNo | Int | 当前页 |
| totalPages | Int | 总页数 |
| pageSize | Int | 每页条数 |
| totalCount  | Int | 总条数 |

Template:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| cover | String | 封面图片 |
| createTime | String | 创建时间 |
| fileKey | String | 模版原文件 |
| form  | boolean | 是否为参数模板|
| id | String | 模板ID |
| pdfKey | String | 转换为PDF后的文件 |
| status | Integer | 模版状态   1：启用，0：停用，2：删除 |
| tags  | Array[Tag] | 标签信息；参照Tag |
| templateType | String | 模板类型 |
| tenantId | Long | 用户ID |
| title | String | 模版名称 |
| type  | String | 模版原文件类型|
| updateTime | Date | 更新时间 |
| word  | boolean | 是否为word模板|

Tag:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 标签ID |
| name | String | 标签名 |
| templateId | Long | 模板ID |
| tenantId  | Long | 用户ID |
| updateTime | Date | 操作时间 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /template/list?type=ALL&pageNo=1&pageSize=12&tenantId=2421235669853425710&property=ALL&createTimeOrder=DESC&thumbable=true  HTTP/1.1
Host: hostname
x-qys-accesstoken: INblkBaTJq
x-qys-timestamp: 0
x-qys-signature: be46397bdf7c78b9ec6eeb216f4af2e5

```

响应示例：

```javascript
{
    "result": [
        {
            "id": "2456799414341607506",
            "tenantId": "2422042838839468036",
            "title": "劳动合同-模板",
            "templateType": "WORD_FORM",
            "fileKey": "20180717-497a8bf9-045e-4949-8fe6-8a7719aa2be8",
            "pdfKey": "20180717-1bb100f7-48a9-4506-a031-1275e89f9999",
            "type": "docx",
            "status": 1,
            "createTime": "2018-07-17 19:23:41",
            "updateTime": "2018-09-06 15:21:28",
            "cover": "data:image/jpg;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAB/AFoDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiigAooooAKKa7iNGdvuqMmspPEumOgbzZACCeYm4HPt7VShKWyE5JbmvRWbaa5Y3txHBC7l3GRlCO2a0qUouOjBNPYKKKKQwooooAKjnmEEe8ruyQoGQMknHepKZL9zqg5HLjigBplkx/qH6eo/wAab58mf+PaTHrkf40nmSOxEcsBOOnJ/rU67sfNjPtQBEZHK/8AHu5BByCR/jVUWdq0gDaVEBgruMacA9fwrQop3a2FYpxQQxMrx6ckbgnBVUBGRzU6yyEDMDjJxyRxx16/hTk83c28ptz8u3Ofxp9Dbe4yETSE820g/Ff8adHI7sQ0Lx4AOSQQfbg1JRSAKKKKACmS42c7Ov8AH0p9RzECPkxjJAG/pnNADIwS25BCV9VHep6qLI6EYe1CknOGxnnmp0nikcokqMw6qrAkUASUUUUAQn7RuODFjPGQalXdtG7G7vjpS0UAFFFFABRRRQAVHMFKDcisAwOGGe/WpKa5wB8xHI6DNAFRrV2c7razK8kZXnJ69vXNCW8sTl4re0RjwWUEE/pSrNln/fTHAP8AyxIx09qnFzGQTh+DjlD6Z9KbEiNPt28eYLfbnnaWzihTfZ+YW+M9i3Sn+cjpwzrkZBKkH9ad50f97tn/AD7+1IZJTH8zK+WEx33Gmi4jYEgtx2KnJ+nrTvOTn5s49O/0oAfRSKwZQw6GloAKKKKACopwTGMStHhgcqAc89Px6VLTJThM7ynI5AzQBVBZ/kF7KpBYlvLAzk8DkY4pUjkWVWbUXdQeUITB/IU0zbpAq3coI6gQ9efpSrOFY5upG6jHld/yoAs+fHkjJOBngE0LNGwyGA6jng/rVYybF2tdyk8nPlc9fpTkmARma4dgDjmLHP5UAWPNj/vr+BpysHXcM49xiq4kCuwNw5whbHl/r0/SmLcKCSbmQjB4MXT36UAXKKrAPKW2XTjHGNg4/MVPGrIgDOXP94gDP5UAOooooAKjnDFAElMR3D5goPfpz69Kkpkqh1AKlvmB4OO9AFGRpCAg1GSNzu58geuR1HYcUAyqmG1GRjkjd5A/wq2ZZQOLdj/wIf40vmSZx5DY9dwoArK7Bw7XrsoPK+UMHjOOmanW7gYEhjx1+U/4Uvmy5A+zNj13Dj9aQzTZI+yv9d68/rQAq3ULoWDHAOPukds+lJ9rhAB3Nz/sH/CgTTFsG2YD13L/AI1KhLICylSeoJzigCIXcJBIY4H+yf8ACpIpUmTehJX3BH86fRQAUUUUAFMlICgsCRkd8Y5p9Q3PmeT+6aJXLDBlGR1+o5oArBonYhI2Zu4WYf404RAlgbWYD1M3Xj/eppNyicTWKyDOSVOBzx3+lWIrhREvnTwF8Eko2B+GTQBGFKji2l5GCPM7fnSCI7QRBOCDwvmD069fwqx58WT+9T5Rk/MOKPtEGSPOjyOo3Ci4WE8xwWAgcgdDkc/rQZJAoIgYkjkZHH605ZonJCyoSOeGFJ9ogzjzo89MbhQAzz5v+fV/++l/xp8cjuTvhaPgdSDn8qUzRBtpkQN0xuFKsiP911bjPBzxQA6iiigAprpvXbkj6U6igCBbRVk3F3br8rYx/KpPKj/uL+VPooAb5cf9xfyo8qPJOxefanUUANEaDoi/lR5Uec7Fz9KdRQA3Yn90flShVXooH0FLRQAUUUUAf//Z",
            "tags": [],
            "params": [
                {
                    "id": "2456799415729922134",
                    "templateId": "2456799414341607506",
                    "name": "用人单位名称",
                    "type": "text",
                    "required": true,
                    "page": 1,
                    "offsetX": 0.30992777386745846,
                    "offsetY": 0.7716910413384671,
                    "signatory": "1"
                },
                {
                    "id": "2456799415876722782",
                    "templateId": "2456799414341607506",
                    "name": "雇员通信地址",
                    "type": "text",
                    "required": true,
                    "page": 1,
                    "offsetX": 0.2746514418825445,
                    "offsetY": 0.6441224211940387,
                    "signatory": "0"
                }
            ],
            "word": true,
            "form": true
        }
    ],
    "code": 0,
    "page": {
        "pageNo": 1,
        "totalPages": 1,
        "pageSize": 12,
        "totalCount": 1
    },
    "message": "SUCCESS"
}
```

## 2.2 模板浏览页面

描述：调用该接口获取模板模板浏览页面的链接，打开链接可以查看模板。链接有效期为30分钟。

Request URL： /template/viewurl

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| templateId | String	| 是 | 模板ID	|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| url  | String | 模板浏览页面的链接 |

```HTTP

GET /template/viewurl?templateId=2515877732244281211 HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQab
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS",
    "url": "http://privapp.qiyuesuo.me/template/view?viewToken=34f-9715-d84cca751a0a"
}
```

## 2.3 查询模板分组

描述：查询公司下文件模板的分组，返回的分组结构与契约锁系统中的分组结构一致。

Request URL：/template/templategroup

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long	| 否 | 公司ID	|
| companyName |	String | 否 | 公司名称  |

注：若公司ID和公司名称都为空，则查询平台方下的模板分组。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result  | Array[TemplateGroup] | 该公司业务分类分组列表 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

TemplateGroup（分组信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 模板的分组ID |
| name | String | 模板的分组名称 |
| creator | String | 创建人名称 |
| companyId | Long | 所属公司ID |
| levels | Int | 该分组所在的分组层级，0为根节点 |
| parentId | Long | 父分组的ID |
| createTime | String | 分组的创建时间，格式yyyy-MM-dd HH:mm:ss |
| children | Array[TemplateGroup] | 该分组下的子分组列表 |
| templateList | Array[Template] | 该分组下的模板信息 |

Template（模板信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | String | 模板ID |
| pdfKey | String | 转换为PDF后的文件 |
| fileKey | String | 模版原文件 |
| status | Integer | 模版状态   1：启用，0：停用，2：删除 |
| tags  | Array[Tag] | 标签信息；参照Tag |
| templateType | String | 模板类型：HTML(html文本),	HTML_FORM(html参数模板),	WORD(word文本),	WORD_FORM(word参数模板) |
| tenantId | Long | 所属公司ID |
| title | String | 模版名称 |
| type  | String | 模版原文件类型 |
| createTime | Date | 创建时间 |
| updateTime | Date | 更新时间 |
| word  | boolean | 是否为word模板|
| form  | boolean | 是否为参数模板|

Tag:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 标签ID |
| name | String | 标签名 |
| templateId | Long | 模板ID |
| tenantId  | Long | 用户ID |
| updateTime | Date | 操作时间 |

请求示例：

```HTTP

GET /template/templategroup?companyName=花里家 HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript
{
    "result": {
        "id": "2571937718710128650",
        "name": "花里家",
        "creator": "张一",
        "companyId": "2571937718710128650",
        "leftValue": 0,
        "rightValue": 3,
        "levels": 0,
        "createTime": "2019-06-05 11:46:54",
        "updateTime": "2019-06-05 11:46:54",
        "templateType": "FOLDER",
        "children": [
            {
                "id": "2573735722934808739",
                "name": "1",
                "creator": "张一",
                "companyId": "2571937718710128650",
                "leftValue": 1,
                "rightValue": 2,
                "levels": 1,
                "createTime": "2019-06-05 11:46:54",
                "updateTime": "2019-06-05 11:46:54",
                "templateType": "FOLDER",
                "parentId": "2571937718710128650",
                "templateList": [],
				"children": [{
					"id": "2570978560281371528",
					"name": "ccc",
					"creator": "沈文强",
					"companyId": "2570617114465419270",
					"leftValue": 2,
					"rightValue": 7,
					"levels": 2,
					"createTime": "2019-05-28 21:10:55",
					"updateTime": "2019-05-28 21:10:55",
					"templateType": "FOLDER",
					"children": [],
					"parentId": "2570978516505420679",
					"templateList": [{
						"id": "2571290999393931502",
						"tenantId": "2570617114465419270",
						"title": "可变内容测试-张豪",
						"templateType": "HTML",
						"fileKey": "20190529-315d2ae4-a778-4d42-abf9-51b70c327b1f",
						"pdfKey": "20190529-f37de9fa-d230-4454-b6c8-d4e656a805db",
						"type": "html",
						"status": 1,
						"createTime": "2019-05-29 17:52:26",
						"updateTime": "2019-05-29 19:31:19",
						"attribute": "{\"title\":\"\\u00FE\\u00FF\",\"creator\":\"wkhtmltopdf 0.12.4\",\"producer\":\"Qt 4.8.7\",\"creationDate\":\"2019-05-29T11:31:19.000+0000\",\"numberOfPages\":1,\"pixelWidth\":595.0,\"pixelHeight\":842.0,\"pageSize\":1191,\"pageAttributes\":[{\"pixelWidth\":595.0,\"pixelHeight\":842.0,\"vertical\":true}],\"vertical\":true}",
						"permissionType": "PRIVATE",
						"groupId": "2570978560281371528",
						"creator": "张豪",
						"word": false,
						"form": false,
						"entityName": "可变内容测试-张豪",
						"entityId": "2571290999393931502"
					}],
					"child": false
				}],
                "child": true
            }
        ],
        "templateList": [
            {
                "id": "2573716833647670069",
                "tenantId": "2571937718710128650",
                "title": "出资确认书",
                "templateType": "WORD_FORM",
                "fileKey": "20190605-f6bc3c94-7280-4454-bc00-74ccb4dfb8ef",
                "pdfKey": "20190605-4eb59be0-5db1-4cbb-8d8b-07d00f7ef841",
                "type": "docx",
                "status": 1,
                "createTime": "2019-06-05 10:31:49",
                "updateTime": "2019-06-05 10:31:50",
                "permissionType": "PRIVATE",
                "groupId": "2571937718710128650",
                "creator": "张一",
                "tags": [
					{
                        "id": "2572052758956261918",
                        "tenantId": "2570617114465419270",
                        "templateId": "2571993981630091758",
                        "name": "新的标签",
                        "updateTime": "2019-05-31 20:19:24"
                    }],
                "userNum": 1,
                "tenantName": "花里家",
                "word": true,
                "form": true
            }
        ],
        "child": true
    },
    "code": 0,
    "message": "SUCCESS"
}
```
## 2.4 查询所有内部企业模板分组

描述：查询所有内部企业下文件模板的分组，模板只查询已启用的，返回的分组结构与契约锁系统中的分组结构一致。

Request URL：/template/templategroup/list

Request Method： GET

参数:无

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result  | Array[Company] | 公司列表 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

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
| parentId | Long | 父分组的ID |
| createTime | String | 分组的创建时间，格式yyyy-MM-dd HH:mm:ss |
| children | Array[TemplateGroup] | 该分组下的子分组列表 |
| templateList | Array[Template] | 该分组下的模板信息 |

Template（模板信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | String | 模板ID |
| pdfKey | String | 转换为PDF后的文件 |
| fileKey | String | 模版原文件 |
| status | Integer | 模版状态   1：启用，0：停用 |
| tags  | Array[Tag] | 标签信息；参照Tag |
| templateType | String | 模板类型：HTML(html文本),	HTML_FORM(html参数模板),	WORD(word文本),	WORD_FORM(word参数模板) |
| tenantId | Long | 所属公司ID |
| title | String | 模版名称 |
| type  | String | 模版原文件类型 |
| createTime | Date | 创建时间 |
| updateTime | Date | 更新时间 |
| word  | boolean | 是否为word模板|
| form  | boolean | 是否为参数模板|

Tag:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 标签ID |
| name | String | 标签名 |
| templateId | Long | 模板ID |
| tenantId  | Long | 用户ID |
| updateTime | Date | 操作时间 |

请求示例：

```HTTP

GET /template/innercompany/templategroup HTTP/1.1
Host: [host]
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d

```

响应示例：

```javascript
{
	"result": [{
		"id": "2593709441139560456",
		"name": "娃哈哈娃哈哈",
		"status": "CERTIFYING",
		"province": "上海",
		"label": "",
		"templateGroup": {
			"id": "2571937718710128650",
			"name": "花里家",
			"creator": "张一",
			"companyId": "2571937718710128650",
			"leftValue": 0,
			"rightValue": 3,
			"levels": 0,
			"createTime": "2019-06-05 11:46:54",
			"updateTime": "2019-06-05 11:46:54",
			"templateType": "FOLDER",
			"children": [{
				"id": "2573735722934808739",
				"name": "1",
				"creator": "张一",
				"companyId": "2571937718710128650",
				"leftValue": 1,
				"rightValue": 2,
				"levels": 1,
				"createTime": "2019-06-05 11:46:54",
				"updateTime": "2019-06-05 11:46:54",
				"templateType": "FOLDER",
				"parentId": "2571937718710128650",
				"templateList": [],
				"children": [{
					"id": "2570978560281371528",
					"name": "ccc",
					"creator": "张三",
					"companyId": "2570617114465419270",
					"leftValue": 2,
					"rightValue": 7,
					"levels": 2,
					"createTime": "2019-05-28 21:10:55",
					"updateTime": "2019-05-28 21:10:55",
					"templateType": "FOLDER",
					"children": [],
					"parentId": "2570978516505420679",
					"templateList": [{
						"id": "2571290999393931502",
						"tenantId": "2570617114465419270",
						"title": "可变内容测试-张豪",
						"templateType": "HTML",
						"fileKey": "20190529-315d2ae4-a778-4d42-abf9-51b70c327b1f",
						"pdfKey": "20190529-f37de9fa-d230-4454-b6c8-d4e656a805db",
						"type": "html",
						"status": 1,
						"createTime": "2019-05-29 17:52:26",
						"updateTime": "2019-05-29 19:31:19",
						"permissionType": "PRIVATE",
						"groupId": "2570978560281371528",
						"creator": "张豪",
						"word": false,
						"form": false,
						"entityName": "可变内容测试-张豪",
						"entityId": "2571290999393931502"
					}],
					"child": false
				}],
				"child": true
			}],
			"child": true
		}
	}],
	"code": 0,
	"message": "SUCCESS"
}
```


# 3 企业接口

## 3.1 企业接口

### 3.1.1 企业认证状态

描述：根据企业的名称查询企业的认证状态。

Request URL： /company/status

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyName | String | 是 | 企业名称 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | String | 审核状态：UNSUBMIT（未提交认证）,APPLIED（认证申请中）,PASSED（认证已通过）,REJECTED（认证已拒绝）|

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

GET /company/status?companyName=测试公司 HTTP/1.1
Host: hostname
x-qys-accesstoken: INblkBaTJq
x-qys-signature: be46397bdf7c78b9ec6eeb216f4af2e5
x-qys-timestamp: 0

```

响应示例：

```javascript
{
    "result": "APPLIED",
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.1.2 创建企业

描述：提交企业认证信息，创建内部企业或外部企业，新创建的企业状态 是”审核中“，需要”基本信息审核“和”基本户审核“通过后才能生效。

Request URL： /company/create

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String	| 是 | 企业名称	|
| license |	File | 是 | 营业执照 |
| legalAuthorization | File	| 创建内部企业时必填 | 法人授权书 |
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

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result |String| 公司信息，参考Company |

Company：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司id |
| name | String | 公司名称 |
| registerNo | String | 公司代码 |
| status | TenantStatus | 公司状态；UNREGISTERED（未注册），REGISTERED（已注册），CERTIFYING（认证中），AUTH_SUCCESS（认证完成），AUTH_FAILURE（认证失败） |
| tenantType | TenantType | 公司类型；CORPORATE（平台方），INNER_COMPANY（内部公司），COMPANY（外部公司） |
| legalPerson | String | 法人姓名 |
| area | String | 地区：CN("中国大陆 "),TW("中国台湾"),HK("中国香港"),MO("中国澳门");默认为中国大陆CN |
| createTime | Date | 创建时间，格式：yyyy-MM-dd HH:mm:ss |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /company/create HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="name"

测试公司
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="license"; filename="营业执照.jpg"
Content-Type: image/jpeg


------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="registerNo"

1234587
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="legalPerson"

宋三
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="charger"

宋三
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="mobile"

13636350222
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="tenantType"

INNER_COMPANY
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="province"

河南
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="companyType"

3
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{

  "result": "2464005029970018322",
  "code": 0,
  "message": "SUCCESS"
}
```

### 3.1.3 企业列表

描述：获取所有企业信息列表

Request URL： /company/list

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantType | TenantType | 否 | 租户类型:CORPORATE(平台方),COMPANY(平台外部企业),INNER_COMPANY(内部企业)，默认获取全部企业 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result| List[Company] | 公司列表信息|

Company：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司id |
| name | String | 公司名称 |
| status | TenantStatus | 公司状态：UNREGISTERED(未注册),REGISTERED(已注册),CERTIFYING(认证中),	AUTH_SUCCESS(认证完成),	AUTH_FAILURE(认证失败) |
| tenantType | TenantType | 租户类型 |
| parentId | Long| 上级公司Id |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

GET /company/list HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9469a4baac0c91547205b93

```

响应示例：

```javascript
{
    "result": [
       {
            "id": "2499564877105446918",
            "name": "洁云3",
            "tenantType": "CORPORATE",
            "status": "AUTH_SUCCESS",
            "certified": true
        },
        {
            "id": "2502022524185190436",
            "name": "测试外部企业1",
            "tenantType": "COMPANY",
            "status": "UNREGISTERED",
            "certified": false
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.1.4 企业详情

描述：获取企业信息

Request URL： /company/detail

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | --------  | -------- | -------- |
| companyId | Long | ID、名称和公司代码必须三选一 | 公司ID |
| companyName | String | ID、名称和公司代码必须三选一 | 公司名称 |
| registerNo | String | ID、名称和公司代码必须三选一 | 公司代码；工商注册号或统一社会信息代码 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result| Object | 公司信息，参考【Company】 	|

Company：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司id |
| name | String | 公司名称 |
| registerNo | String | 公司代码 |
| status | TenantStatus | 公司状态；UNREGISTERED（未注册），REGISTERED（已注册），CERTIFYING（认证中），AUTH_SUCCESS（认证完成），AUTH_FAILURE（认证失败） |
| tenantType | TenantType | 公司类型；CORPORATE（平台方），INNER_COMPANY（内部公司），COMPANY（外部公司） |
| parentId | Long| 上级公司Id |
| legalPerson | String | 法人姓名 |
| legalPersonId | String | 法人证件号 |
| createTime | Date | 创建时间，格式：yyyy-MM-dd HH:mm:ss |
| freeze | Boolean | 冻结或未冻结状态 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：


```HTTP

GET /company/detail HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9469a4baac0c91547205b93

```

响应示例：

```javascript

{
    "result": {
        "id": "2421235669853425710",
        "name": "GJ部落",
        "registerNo": "91350181MA31H4JK1E",
        "tenantType": "INNER_COMPANY",
        "createTime": "2018-04-10 16:06:08",
        "status": "AUTH_SUCCESS",
        "legalPerson": "爱迪生",
        "legalPersonId": "41278978997890038",
        "parentId": "2422042838839468036",
        "certificate": "91350181MA31H4JK1E",
        "certified": true
    },
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.1.5 通知用户企业认证 

描述：传入企业信息和联系人信息，短信通知联系人登录契约锁私有云进行企业认证。

Requset URL：/companyauth/notice

Request Method：POST

Content-Type： multipart/form-data

参数：

| 名称 | 类型 | 是否必须 |描述 |
| -------- | -------- |  -------- |-------- |
| name | String | 是|认证企业名称 |
| registerNo | String |是| 认证企业工商注册号 |
| charger | String | 是|认证企业负责人 |
| mobile | String | 是|认证企业负责人手机号，用于接收通知短信 |
| license | File | 否 |营业执照 |
| legalPerson | String | 否 |法人姓名 |

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Company | 公司信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

Company：
| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司ID |

请求示例：


```HTTP

POST /companyauth/notice HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9469a4baac0c91547205b93
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW


Content-Disposition: form-data; name="name"

下雨不愁
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="registerNo"

06231735
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="charger"

张三
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="mobile"

15000000000
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript

{
    "result": {
        "id": "2596554565124366339"
    },
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.1.6 变更企业信息

描述：已认证成功的企业，可以调用此接口获取认证链接，对企业基本认证信息进行变更，基本信息变更通过后会发送短信提醒申请人进行下一步认证。

Request URL： /company/changeinfo

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long | 公司ID、公司名称、企业代码不能同时为空 | 公司ID |
| companyName | String | 公司ID、公司名称、企业代码不能同时为空 | 公司名 |
| registerNo | String  | 公司ID、公司名称、企业代码不能同时为空 | 企业代码 |
| mobile | String | 是 | 申请人手机号 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| authUrl | String | 企业变更信息链接 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：
```HTTP

POST /company/changeinfo HTTP/1.1
Host: hostname
x-qys-accesstoken: AU4HofNTpo
x-qys-signature: 0c43a5b98b64
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="companyName"

测试企业
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "authUrl": "https://auth.qiyuesuo.me/enterprise-m/corp/home?ticket=tfPKsx",
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.1.7 删除未认证公司 

描述：将未认证的公司用户删除，公司未签署的合同退回处理，删除公司下所有员工。

Requset URL：/company/delete

Request Method：POST

Content-Type： multipart/form-data

参数：

| 名称 | 类型 | 是否必须 |描述 |
| -------- | -------- |  -------- |-------- |
| companyId | Long | 公司ID与公司名称必须填写一个 | 公司ID |
| companyName | String | 公司ID与公司名称必须填写一个 | 公司名称 |

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：


```HTTP

POST /company/delete HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf9469a4baac0c91547205b93
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="companyName"

维森集团企业
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript

{
    "code": 0,
    "message": "SUCCESS"
}
```
### 3.1.8 冻结企业 

描述：冻结非平台方的企业。

Requset URL：/company/freeze

Request Method：POST

Content-Type： multipart/form-data

参数：

| 名称        | 类型   | 是否必须                     | 描述     |
| ----------- | ------ | ---------------------------- | -------- |
| companyId   | Long   | 公司ID与公司名称必须填写一个 | 公司ID   |
| companyName | String | 公司ID与公司名称必须填写一个 | 公司名称 |

响应：

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| code    | Int    | 响应码   |
| message | String | 响应消息 |

响应码解释:

| 响应码  | 描述     |
| ------- | -------- |
| 0       | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP
POST /company/freeze HTTP/1.1
Host: hostname
x-qys-accesstoken: 0hniG
x-qys-timestamp: 156507
x-qys-signature: cfcea2dd
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="companyName"

好好喝
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```
### 3.1.9 解冻企业 

描述：解冻冻结状态的企业。

Requset URL：/company/unfreeze

Request Method：POST

Content-Type： multipart/form-data

参数：

| 名称        | 类型   | 是否必须                     | 描述     |
| ----------- | ------ | ---------------------------- | -------- |
| companyId   | Long   | 公司ID与公司名称必须填写一个 | 公司ID   |
| companyName | String | 公司ID与公司名称必须填写一个 | 公司名称 |

响应：

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| code    | Int    | 响应码   |
| message | String | 响应消息 |

响应码解释:

| 响应码  | 描述     |
| ------- | -------- |
| 0       | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```java
POST /company/unfreeze HTTP/1.1
Host: hostname
x-qys-accesstoken: 0hniG
x-qys-timestamp: 156507
x-qys-signature: cfcea2dd
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="companyName"

好好喝
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

### 3.1.10 企业经营状态详情

描述：获取企业经营状态信息

Request URL： /company/managestatus

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | --------  | -------- | -------- |
| companyId | Long | ID、名称和公司代码必须三选一 | 公司ID |
| companyName | String | ID、名称和公司代码必须三选一 | 公司名称 |
| registerNo | String | ID、名称和公司代码必须三选一 | 工商注册号 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result| Object | 公司经营信息，参考CorpCredit |

CorpCredit：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| name | String | 公司名称 |
| legalPerson | String | 法人姓名 |
| registCapital | String | 注册资本 |
| address | String | 地址 |
| registerNo | String | 企业注册号 |
| status | String | 企业经营状态 |
| organizationNo | String | 组织机构号 |
| creditNo | String | 统一社会信用代码 |
| abnormalItems | List[AbnormalItem] | 经营异常信息 |
| executions | List[Execution] | 严重违法失信企业名单（黑名单）信息 |
| adminPunishs | List[AdminPunishment] | 行政处罚信息 |

AbnormalItem（经营异常信息）:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| inReason | String | 列入经营异常原因 |
| inDate | String | 列入时间 |
| outReason | String | 移出经营异常原因 |
| outDate | String | 移出时间 |
| department | String | 做出决定的部门 |

Execution（严重违法失信企业名单（黑名单）信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| inReason | String | 列入严重违法失信企业名单（黑名单）原因 |
| inDate | String | 列入时间 |
| inDepartment | String | 作出决定机关（列入）|
| outReason | String | 移出经营异常原因 |
| outDate | String | 移出时间 |
| outDepartment | String | 作出决定机关（移出） |

AdminPunishment（行政处罚信息）：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| seq_no | String | 序号 |
| number | String | 决定书文号 |
| illegalType | String | 违法行为类型|
| content | String | 行政处罚内容 |
| department | String | 决定机关名称 |
| date | String | 处罚决定日期 |
| publishDate | String | 公示日期 |
| description | String | 详情 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：


```HTTP

GET /company/querystatus?companyName=测试企业公司 HTTP/1.1
Host: hostname
x-qys-accesstoken: 7fES1m3Unv
x-qys-timestamp: 0
x-qys-signature: a91c189cf946

```

响应示例：

```javascript

{
    "result": {
        "name": "测试企业公司",
        "address": "上海市奉贤区",
        "status": "存续",
        "legalPerson": "张三",
        "registCapital": "100 万元人民币",
        "registerNo": "45678912",
        "organizationNo": "JAJJAHD1",
        "creditNo": "ahsdiuashdaiudhaiu",
        "abnormalItems": [
            {
                "department": "市场监督管理局",
                "inReason": "无法联系",
                "inDate": "2019-05-05",
                "outReason": "申请移出",
                "outDate": "2019-05-30"
            }
        ]
    },

### 3.1.9 解冻企业 

描述：解冻冻结状态的企业。

Requset URL：/company/unfreeze

Request Method：POST

Content-Type： multipart/form-data

参数：

| 名称        | 类型   | 是否必须                     | 描述     |
| ----------- | ------ | ---------------------------- | -------- |
| companyId   | Long   | 公司ID与公司名称必须填写一个 | 公司ID   |
| companyName | String | 公司ID与公司名称必须填写一个 | 公司名称 |

响应：

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| code    | Int    | 响应码   |
| message | String | 响应消息 |

响应码解释:

| 响应码  | 描述     |
| ------- | -------- |
| 0       | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

​```java
POST /company/unfreeze HTTP/1.1
Host: hostname
x-qys-accesstoken: 0hniG
x-qys-timestamp: 156507
x-qys-signature: cfcea2dd
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="companyName"

好好喝
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```

## 3.2 员工接口

### 3.2.1 创建内部员工

描述：根据企业名称创建内部企业员工信息。

Request URL： /employee/create

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| name | String	| 是 | 员工姓名	|
| contact |	String | 是 | 员工联系方式，为登录帐号 |
| password | String| 否 |	员工登录密码 |
| companyName |String|是|	企业名称|
| cardNo |String | 否	| 员工身份证号|
| number |String | 否 |第三方平台企业员工编号	|

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result  | String | 第三方平台员工ID |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /employee/create HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e
Content-Type: application/json

{
	"name":"宋三",
	"contact":"15000000000",
	"companyName":"测试公司",
	"password":"q123456",
	"cardNo":"11111111111116"
}

```

响应示例：

```javascript

{
  "result": "2463985841126608906",  //第三方员工ID
  "code": 0,
  "message": "SUCCESS"
}
```

### 3.2.2 移除员工

描述：移除企业下员工。

Request URL： /company/removeemployee

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contact |	String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工手机号 |
| employeeNo | String| 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工编号 |
| openUserId | String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 第三方平台员工ID	|
| companyName | String | 是 | 企业名称 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |
| 2003004 | 员工是企业唯一系统管理员 |

请求示例：


```HTTP

GET /company/removeemployee?contact=18435186216&employeeNo=&openUserId=&companyName=上海泛微网络科技股份有限公司  HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript

{
  "code": 0,
  "message": "SUCCESS"
}
```

### 3.2.3 移除所有内部企业该员工

描述：从所有内部企业下，移除该员工。

Request URL： /company/removeinneremployees

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contact |	String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工手机号 |
| employeeNo | String| 手机号、员工编号、第三方平台员工ID，三者必填一项 | 员工编号 |
| openUserId | String | 手机号、员工编号、第三方平台员工ID，三者必填一项 | 第三方平台员工ID |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |
| 2003004 | 员工是企业唯一系统管理员 |

请求示例：

```HTTP

GET /company/removeinneremployees?contact=1843518&employeeNo&openUserId&companyName=1111 HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript
{
  "code": 0,
  "message": "SUCCESS"
}
```

### 3.2.4 查询用户信息

描述： 查询用户信息。

Request URL：/user

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile |	String |手机号、用户身份证号，用户Id两者必填一项 | 员工手机号 |
| cardNo |String | 手机号、用户身份证号，用户Id两者必填一项 | 用户身份证号 |
| id |String | 手机号、用户身份证号，用户Id两者必填一项 | 用户Id |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result  | UserAuthStatus | 员工查询结果 |

UserAuthStatus：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | String | 用户id |
| name | String | 用户名称 |
| mobile | String | 用户联系方式 |
| cardNo | String | 用户身份证号码 |
| status | String | 用户认证状态：AUTH_SUCCESS("已认证成功")，AUTH_FAILURE("认证失败") |
| companies | List&lt;Company&gt; | 用户所在的公司信息 |

Company：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 公司id |
| name | String | 公司名称 |
| status | TenantStatus | 公司状态；UNREGISTERED（未注册），REGISTERED（已注册），CERTIFYING（认证中），AUTH_SUCCESS（认证完成），AUTH_FAILURE（认证失败） |
| tenantType | TenantType | 公司类型；CORPORATE（平台方），INNER_COMPANY（内部公司），COMPANY（外部公司） |
| freeze | Boolean | 冻结或未冻结状态 |


响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /user?contact=12312378900 HTTP/1.1
Host: openapi.qiyuesuo.me
x-qys-timestamp: 0
x-qys-signature: 159da9d90dda41dac84920077fca37
x-qys-accesstoken: qAJeIep

```

响应示例：

```javascript
{
    "result": {
        "id": "2595869369835479862",
        "name": "小峰峰",
        "mobile": "15505178555",
        "cardNo": "32068378887887",
        "status": "AUTH_SUCCESS",
        "companies": [
            {
                "id": "2594737273725567704",
                "name": "斯塔克工业总部",
                "tenantType": "INNER_COMPANY",
                "freeze": false,
                "status": "AUTH_SUCCESS",
                "certified": true
            },
            {
                "id": "2570617114465419270",
                "name": "维森集团有限公司",
                "tenantType": "CORPORATE",
                "freeze": false,
                "status": "AUTH_SUCCESS",
                "certified": true
            }
        ]
    },
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.2.5 根据角色查询员工

描述： 根据用户输入的角色类型角色列表包含员工信息

Request URL：/rolelist

Request Method：GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long | 公司ID与公司名必须有一个存在 | 公司ID |
| companyName |String | 公司ID与公司名必须有一个存在 | 公司名称 |
| roleType | String | 否 | 角色类型：SYSTEM("系统管理员"),SA("印章管理员"),LP("法定代表人"),CONTRACT("文件管理员"),CUSTOM("自定义角色")，不填默认查询所有 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result  | Array[Role] | 角色员工列表查询结果 |

Role：

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

请求示例：

```HTTP

GET /employee/listbyrole?companyName=维森集团有限公司&amp; roleType=LP HTTP/1.1
Host: hostname
x-qys-timestamp: 0
x-qys-signature: 159da9d90dda41dac84920077fca37
x-qys-accesstoken: qAJeIep

```

响应示例：

```javascript
{
    "result": [
        {
            "id": "2570617114624802825",
            "name": "法定代表人",
            "companyId": "2570617114465419270",
            "description": "法定代表人",
            "roleType": "LP",
            "employees": [
                {
                    "id": "2570809757436494499",
                    "name": "王五",
                    "userId": "2570809757352608417",
                    "mobile": "12345678912",
                    "contractAdmin": false,
                    "entityName": "王五",
                    "sa": false,
                    "lp": false,
                    "nickName": "王五",
                    "entityId": "2570809757436494499",
                    "admin": false
                }
            ],
            "entityName": "法定代表人",
            "entityId": "2570617114624802825"
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.2.6 通知个人用户实名认证

描述： 发送个人认证短信邮箱通知,若用户不存在则自动创建用户。

Request URL：/userauth/notice

Request Method：POST

Content-Type: application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile |	String | 是 | 用户手机号 |
| email | String | 否 | 用户邮箱 |
| name | String | 是 | 用户名 |
| cardNo | String | 是 | 身份证号 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /userauth/notice HTTP/1.1
Host: hostname
x-qys-timestamp: 0
x-qys-signature: 159da9d90dda41dac84920077fca37
x-qys-accesstoken: qAJeIep
Content-Type: application/json


{
	"name":"张三",
	"mobile":"10000000000"
}

```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.2.7 删除未认证个人用户

描述：将未认证的个人用户相关信息删除，所有公司将该员工删除，待处理的合同退回处理。

Request URL：/user/delete

Request Method：POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| userId |	String | 用户ID与用户联系方式必须填写一项 | 用户ID |
| contact | String | 用户ID与用户联系方式必须填写一项 | 用户联系方式 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /user/delete HTTP/1.1
Host: hostname
x-qys-timestamp: 0
x-qys-signature: 159da9d90dda41dac84920077fca37
x-qys-accesstoken: qAJeIep
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW


Content-Disposition: form-data; name="contact"

15000000000000
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```

### 3.2.8 用户修改手机号短信通知

描述：用户修改手机号，向新手机号发送修改手机号的链接。

Request URL：/user/changemobile

Request Method：POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| oldMobile | String | 是 | 用户旧手机号 |
| newMobile | String | 是 | 用户新手机号 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /user/changemobile HTTP/1.1
Host: hostname
x-qys-timestamp: 0
x-qys-signature: 159da9d90dda41dac84920077fca37
x-qys-accesstoken: qAJeIep
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW


Content-Disposition: form-data; name="oldMobile"

15000000000
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="newMobile"

15000000001
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS"
}
```

# 4 印章接口

### 4.1 印章使用记录

描述：获取印章使用记录。

Request URL： /seal/records

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| startTime | Date| 否 |开始时间，格式：yyyy-MM-dd HH:mm:ss |
| endTime   | Date| 否 |结束时间，格式：yyyy-MM-dd HH:mm:ss |
| companyName   | String| 否 |企业名称，不传默认为平台方 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Array[SealRecord] | 印章使用记录，参照SealRecord |

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


响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /seal/records?startTime=2018-08-01&endTime=2018-08-02&companyName=测试公司  HTTP/1.1
Host: hostname
x-qys-accesstoken: INblkBaTJq
x-qys-timestamp: 0
x-qys-signature: be46397bdf7c78b9ec6eeb216f4af2e5

```
响应示例：

```javascript
{
    "result": [
        {
            "contractId": "2474555242151948472",
            "sealId": "2467260736791310865",
            "userId": "2422042842433986573",
            "userName": "王五",
            "subject": "劳动合同",
            "createTime": "2018-09-04 19:19:20",
            "number": 1,
            "sealName": "合同专用章dxx",
            "tenantId": "2422042838839468036",
            "tenantName": "上海泛微网络科技股份有限公司"
        },
        {
            "contractId": "2474185613177184368",
            "sealId": "2467260736791310865",
            "userId": "2422042842433986573",
            "userName": "李四",
            "subject": "线上测试",
            "createTime": "2018-09-03 18:57:17",
            "number": 1,
            "sealName": "合同专用章dxx",
            "tenantId": "2422042838839468036",
            "tenantName": "上海泛微网络科技股份有限公司"
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

### 4.2  公司印章列表

描述：获取公司印章列表。

Request URL：/seal/list

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId   | Long | 否 | 公司ID |
| companyName   | String| 否 | 公司名称（公司ID与公司名称如果都不传的话获取的是平台的印章列表） |
| category | String | 否 | 印章类型：PHYSICS("物理签章"),ELECTRONIC("电子签章"),不传默认查询电子章 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Array[Seal] | 印章列表，参照Seal |

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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /seal/list?companyName=测试公司 HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```
响应示例：

```javascript
{
    "code": 0,
    "message": "SUCCESS",
    "list": [
        {
            "id": "2457407333014970396",
            "owner": "2422043939275067412",
            "name": "党纪委45mm",
            "otherName": "别名",
            "type": "COMPANY",
            "spec": {
                "height": 45,
                "width": 45,
                "type": "CIRCULAR",
                "key": "CIRCULAR_45",
                "pixelWidth": 127.56,
                "pixelHeight": 127.56,
                "label": "圆形（直径45mm）"
            },
            "sealKey": "20180719-ba9de4ec-f104-4a79-917d-a489b747cbb2",
            "createTime": "2018-07-19 11:39:25",
            "status": {
                "description": "正常",
                "key": "NORMAL"
            },
            "useCount": 0,
            "category": "ELECTRONIC"
        },
        {
            "id": "2457407488053223457",
            "owner": "2422043939275067412",
            "name": "工会公章42",
            "otherName": "别名",
            "type": "COMPANY",
            "spec": {
                "height": 42,
                "width": 42,
                "type": "CIRCULAR",
                "key": "CIRCULAR_42",
                "pixelWidth": 119.06,
                "pixelHeight": 119.06,
                "label": "圆形（直径42mm）"
            },
            "sealKey": "20180719-85a11e1a-753e-4a14-aa9e-3ea3f630bad4",
            "createTime": "2018-07-19 11:40:02",
            "status": {
                "description": "正常",
                "key": "NORMAL"
            },
            "useCount": 0,
            "category": "ELECTRONIC"
        }
    ]
}
```

### 4.3 创建企业公章

描述：通过用户上传的base64格式图片，创建公司印章。

Request URL：/seal/create/company

Request Method： POST

Content-Type: application/x-www-form-urlencoded

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyName | String | 是 | 公司名称 |
| image | String| 是 | Base64格式的印章图片  |
| name | String | 是 | 印章名称，不传默认为公司名称 |
| type | SealType | 是 | 签章类型，不传默认企业公章 |
| spec  | SealSpec | 是 | 印章规格，参照 下表 |


spec

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


响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Seal | 生成的印章，参照Seal |

Seal:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 印章ID |
| owner | Long | 公司ID或者用户ID |
| ownerName | String | 公司名称或者用户名称 |
| name | String | 印章名称 |
| type | SealType | 签章类型 |
| spec  | SealSpec | 印章规格 |
| sealKey  | String | 印章图片key |
| createTime  | Date | 创建时间 |
| status  | SealStatus | 印章状态 |
| useCount | Integer | 印章使用的次数 |


响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /seal/create/company HTTP/1.1
Host: localhost
x-qys-accesstoken: ISW18kJJzU
x-qys-timestamp: 0
x-qys-signature: 101327b71e9bc632407306be13e13f2e
Content-Type: application/x-www-form-urlencoded

companyName=%E7%BE%8E%E5%9B%A2&image=iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAPuUlEQVR42u2d64tV1RvHD79%2FJ3oVGBkZmHaB7GIURnYRysyI1G4KpaVQeUkjsjKhtGLCSLsIOaXCFCYpkZkOWGTYy%2BefWOu3PmvtNbM7ntGZc%2Fbae%2B1znhcPczszs89a3%2FXcn%2B%2FqWGv%2FpzK60tFFUADoQigAVBQAKgoAFQWAigJARQGgogBQUQCoKABUFAAqCgAVBUA75d9%2FxU5Oiv39d7G%2F%2FSb25EmxJ05My6lT4ftnz4r9809RALRZLlwIm%2Frll2LeeUfMxo1innpKzIMPirn3XjF33SXm9tvFLFokZvHi8PHOO8Xcc4%2BYpUvFPPywmGeeEfPqq2L37xc7Pi72l1%2FE%2Fv23KABylH%2F%2BCSd6bCxs9qOPho294QYxN94Y5OabwwY%2F9JCYRx4Rs2qVmKefFrN6dfj4xBNili8PIFmyRMxNN4Xf428gd9wRXrNliweWPX1aFABNy6%2B%2Fiv3007CBbFDcrFtvFfP442JfeUXs22%2BLPXhQ7I8%2Fij1zRrx2QMVfuiRTpoGPFy%2BGn2Em2Nzjx8UeOCB2%2B3axL70UwLFw4TSoANOLL4r94otgNhQANQkb9fXXYl5%2BOahzNmTBAn%2By%2FYZ%2F%2FrnYn38W%2B9df1W7K%2BfMBRJ98ItZtvLnvPjHz5wcwLFsm9o03xB49KgqAlHbdnTZvm2%2B5JWy8A4B1QLBffSV%2Bg%2Bp8HhzGzz4Ts2ZN0D7z5oWPGzaI%2FfZbUQBUJahqbPvKlUEFc%2BKck2Y%2F%2Fli8Ccjh%2BfA%2Fdu%2Be1kjOBNnnnvOaSgEwiPzwg9gXXggOHE4Zdt3Z%2FKpsrnnrLTFVnlZCyQ8%2BEPPAA8E83Hab2F278gBqqwCAun%2F33RCucaKcZ%2B5tb8Vq3kcAH34oSZzT99%2Bffn58BMJJBcAsBEeKmL18grC3Cf6Xjx5ee01SajCDBsN0OS3m%2FZVE72U4ALBvX%2FCuOTUuTrfffJN0sbxf4cCWPANJqIpZcP6LXbEiWyex02giZ8eO4N1zUrZtq8Wrx6cw69bVsxkuhPQRA07skiViU5ieVgIAe09czcIQ1uHd1%2FW%2FXexutm6t7%2F%2BRSiY1TaIKoDsndLQBcOaMmGefDapx%2BfLaVaNxYDNNOGfOoTV33%2B0jG%2Fvmm9NZyZECwOnTIbfO5j%2F2WEi91gw%2BMofmo4%2BaWXwKVfg7aD7nKI4WADj5Tz4ZNh8njERKE3WELVvE1GlyuuW778RSnHJOr928WUYDANj8qPYdCBoLi376SQwbQH7%2FyJHmFv%2F778UuW%2BbDXu%2F8Dj0AiItRe6j9Jk5%2BtP8kligauQjEUDFs8vQdPhz6EBYs8E7i8AKA1CsJHip3ExONLroh7AQAfI76RRM0CQIKXfQhLFrki0zDBwAqZ8T5zvttPBFy%2FrwYKojx6%2FfeE5NDSOYcUp81dM5h7U5xUgBgb%2B%2B%2FP6i4Jp2uePqPHhVTTjQ5P8QnZxo6ef8RFxZ6p5BMaM3hYTIA%2BBo%2Bb4qYt%2BkFdo6foaWr%2B%2FtEJddeW39fQa%2BsKOExTuHOnUMAgD17pp2%2BDJoqffWPzuAeWTrjnjWLxk%2BKSCSKFi9OXg9JC4ATJ0KygwaJss1VubrQV4AWqNEUVA%2BADRuC6t%2B4UTe%2FD1MwlSmtqXBULQAOHgz99s75s0W4pTJHcVrTt7cvXdrbbOUMgKkiTwZef5vFdz%2FPm%2Bd7DtsDABfn%2B5ifhA%2BpX93IgVLFvtuYUnliLVAZABiY8LZ%2F717d%2FCrWE1%2Fq%2BuuT%2BwLVAGB83PfyedvfdEw9LELugiYSNGrCNa0GAHTZYLOIqXXzqtMCa9cGn4rJp2wBQI6d4Upns4ZxeLLxYhEJNQZOsgUAXS6gdM0a3fyq5dy50FnM4Uo0aDI4ANavD9mrHIoqs63DZ9idO6Ns3hyc60Sh9WAAoKLGyLSTOpIWlcjWraFG0RYAAFjG5EgPUzTKCgCMbNPl2hb1f%2FGi33yYQXKe1rnMx8IMULpO8MyDAYAOm4TqKUmPArN7DXbg9CXkWDhoCSaP%2BwfApUuBpYNhB7ewrVhIuoDIsCFMB0WmkNxl%2F%2F4wOpegt6J%2FANDjTz8bU7B%2F%2FJH%2FQl64ECptaADK1bRgQQLVBgBQYscPWLlSPFtKFgCYmGhX%2BMecHoBl8%2BnLp%2FFi377mn52DdDWKurNnQ3sdFUKGW7IAwIEDAQBXGm5AxZbLwkQNvL6JdDHqnzIr%2FfgAgDF0wNvwiBZJHjM2FjqVSan3Mqf0CTA%2BD2grNredgcIpHEB67bt%2FFhFNV%2FD69f%2F9OQxc3XE4HcPbtgVwpGjPio0W8AKStQQEBT9gyuylOXVKDKxjZfYTBJYxWE9OnhQD0VThRPvwdCatxEgbxSF%2BPwsAQKMGAI4dk54j0bB9QLNGjoBhTCZyCGMYkiwRNJgCJJ4RhMWYo%2Fr0YIMdDAqZc%2Bf%2BK3wP%2FyRyEMDnw%2BajThHCQQZEeC7yGLy2%2FPt8TWmbv99PCHbkiB%2BEnVoXKG8omuE88yxopUOHwummHQzHlHXr9b927UoScXX6PlF01KJGuzt%2FUKk0NdKFCzkjC79zp2fg9ISOEDQwJwCdCqagMAewe%2FqRqbnE9Js2hRiZ2J6%2Fu2JF%2BLwsEEjyGp6Dj3HzkcIZ9Cyh3b%2BHoDUQJnjIx8%2BVTpamUzQgQKW6R9Mnz8jnbvP9aaePgilpWE4dCP06cVhmSLlXzW7SHwDcQvhF43Rf6WRwossqkBMV%2BfboeuH0cdIAAJs5V3RzakmVwhnIcAULjKMHJWwM94rGCr%2F53QDgazYX08Dryh%2BLDl1%2F6p5%2Ffs4tbob2OADPpkFKQZs8J5%2FKqYs%2B%2FCYzpRy1BHkJNOFMTvXx4%2BFvQUnXOADITqFKCaV6sXcRXtHbtmNH0ATl1%2BzdK6awYwa1iDPJG7%2FmGn%2Fa%2Bu5F5KSz%2BZgdwImtx9lDymq%2FWwBBfC0f%2BV3%2BFj4C76%2BfuoEzGQaVDdjJ4EFmyfuEoArwO%2BDj73guIUwAE8uYQuw89f9emgYAEApWXBnsDwCoqsKeXubRo%2FZ4I7BwABTib2cSPDUbXi4aoXtAlAwd3jBqvN84l7%2BJ70FbGpvHZkbVP9Pmd5sCfoev%2BRsOBFWMs3ny6WIG0fdMOkfOmxVMDBNB5Pqx%2FZFxFG6hXp4%2Ba4SWc1qkeQAQu6IqnY3vSc%2BKWSh58x7xnDDUHz%2FD7jkfwKc2I4AIGQtzMBA5E%2F8LFY70UvtX0gRoEEItyKEHfZZyuPz66yGBA0jxc%2FAJyloRE7F2rVwN4N4koSUrDF37AwDhDXZ2FpM%2FBrBg27F5ZVDg7dLxgvp2C%2BQXoKpqFw4TJ4ys32wAwGvQaKh8gFl1OMjQ55U2jUNxtXo%2F60gEAXFm4wBwDxvt7UAXLRTqHq3gTUOV5U5ODOFVka6ecfP5GQ4fGgqG8VwzmbwfTBMaoMIaRn8AcN58DKtybgL1TljRWHmZzY%2BfY%2FcxF3AR59zOjsOME5iFDzA5GRYVlZlrIwgnho3GDLDJ0dHjxEdnL6p%2Fvo9PUw5Zc5Njx0K4WzG5VH8AIMzBxpLQybURlOwfpz86gjHci9fHAIS4%2BXwf%2B5rzPCMAIA9ASJlFKpikhrNJPoOVs%2Fpno6Og6kn8FI2WUxoBwVfAqc21tF0U38xc0%2BXJAECWiyxZjo4TTmrcVNLRRWxvsfPE9oA25gzQCCR%2FAAO1gVxH2imWMXtBESkLABBvUw5umm1rJm4iTjh%2BChsMVwGxeNlfIQKBwhWtEDOItF%2Fzuhw12kzFt8YAQPwOADZtym7BfF0h5vRxVK80r0gqltRvrB9AYplbZEPhiyype8aqTW7%2FAKBNCQIoFiwT3tupOgUnH3tJkWU2lzqRlaO%2FAZNAJjA3vwaWVXyXBFFX%2FwCgHoAzRSSQU4s1%2FQaRlXuuiSWaLwFADsRW3TkAeglWr668kXWwtvB164IWyOiiJAP16iCTP9QpCAerqgVUIe79eAcwAYPYYACAeh3HhDavXBarirsDE0zgDARqegk4aIcPZwaA6AeQnszJDxgmwdTG3oQEWdfBAMD0Kg4X3qmOhqcdEacrKcvp4EgO0aaJ2zaRRBRX66QiiRgcAPS0ET5xM1bFUysjL5TJi0mmVJdVd6pIUnAtmk%2B1NnkJwzBK4WT7trKsOYJo9GRoIcOsYGuF3koqrhyshFT7ncpUFV015NK5S1c3sJrqH8mfxJnW6ogi6fG77ro8i0NtEwZvVq0KnADF3GD%2BAKBRlGJKIiaLkRKaWmn%2FogE0MZV9tVzBjDXhC9R86cFQCcQbNLMS%2BtUwvl4tWzicAfgC1N8zvSw5e8GhLsbJ6rjIovr7Aph4hTaOypWmh%2FtrZIXDqNcVN60AAOELeQEcGM0Ozk0YqaO4VnHjZ70AiE4MF0dAdz6bhgyV0MdARpWsX40NKemujSso5OARyK28mp3gO6H6aWSp%2BWbzTtJYFhKJDO7HzV7w%2Bhu6Yi%2FtzaEMM9DLRjpT%2FYGZuZY4JHj9DWjK9HcHM%2B%2BOP0CauE3snHUI2VPGvSCrqrjdOx8AILt3h8wWDaSHDikIEO4M5mCQOa0p5GsOAAi3iJPg4FqZUU8S0X1M2pwO5F40e0MJgNg9RGQACHKexU%2Bd6WPzFy5MQkaRNwAiCNAEmINR8wkYRYPmhZMPTVwGz9Rp5B%2FDGwjhEY5hJguRvFV9%2B%2Fbg8GHzM6LX7zT2zyGKZCaflDHxbxsYx%2FsR%2BIHo60frkRnNjISi0%2BgDjI2F8TKSIMTBCQYfGp9SJsPH%2B4PbZ3w8u%2FfXafwh4NCLJwT1iElo%2B%2BWT5PKZUMbeMzhTcCbm%2BKydLB6Eujd8A5iE%2BfNDLbyNUQLvg05etBrvgwQPdLAZP3MnqweCnaPohvGnB17cDNVmz42HLwHaHBw9Ut88%2B8RE9s%2Feye6h4B1kGrawnT5SgHEbosnc7viBVo7u3YIvyQMXnqGc2cayB0AUCKfJlTMYCRBg8IBTl8sWEk3JzFpg9SSbB%2BcvJ572bfiHYCJpWTTTyf4hAcKePcGucsJYbEiquV0ErVCXc8VkLn4J17rDOwQoqW%2Fg3UPc1DQohxYApeETH1bB7kUqFdZtNoDpZEwEWUU4%2FScnq%2BEHYMOx4SRtYDLHJAFANh4AYOMBRM7sokMFgFLbtGfdxk9ABQOGuDHwAsJfDPE0GsKZEM9eQqmVcBOAMLkEmPgbhGt8n59TkaOdnYsscESLDh0PNNQ8psiFq%2F60J7rIWQHQj%2FeNicAvIO6Ol0bEjYsmI94ogqNGqAl7GFEG3%2BO10Y7zOwg%2FAwAAAfYTrsBh04ewy7kzVG%2BICIITTvqVxlRqDpxoVDjJpnhRA13LCF9ziQMz%2BHTiou6ZcObCBkzACPQydob9DV5m28uicwsjBgAVBYCKAkBFAaCiAFBRAKgoABQAuggKAF0IBYCKAkBFAaCiAFBRAKgoAFQUACoKAJURkP8Dy%2F5bLfaUQBAAAAAASUVORK5CYII%3D&spec=CIRCULAR_60&name=%E5%BC%A0%E5%BF%B1%E6%98%8A%E6%B5%8B%E8%AF%95&otherName=%E5%BC%A0%E5%BF%B1%E6%98%8A%E5%88%AB%E5%90%8D

```
响应示例：

```javascript
{
    "result": {
        "id": "2491885161152983042",
        "owner": "2487158676155875334",
        "name": "postman测试章",
        "type": "COMPANY",
        "spec": {
            "height": 60,
            "width": 60,
            "type": "CIRCULAR",
            "key": "CIRCULAR_60",
            "label": "圆形（直径60mm）",
            "pixelWidth": 170.08,
            "pixelHeight": 170.08
        },
        "sealKey": "20181022-8d960427-bcc7-4a4e-8636-023bacfaf25d",
        "createTime": "2018-10-22 15:01:59",
        "status": {
            "description": "正常",
            "key": "NORMAL"
        }
    },
    "code": 0,
    "message": "SUCCESS"
}
```

### 4.4 获取印章详情

Request URL：/seal/detail

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| sealId   | Long | 是 | 印章ID |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Seal | 印章，参照Seal |

Seal:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 印章ID |
| owner | Long | 公司ID |
| name | String | 印章名称 |
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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /seal/detail?sealId=2494785477926060041&sealOtherName= HTTP/1.1
Host: privopen.qiyuesuo.me
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e
Content-Type: application/x-www-form-urlencoded
Cache-Control: no-cache
Postman-Token: 2c909fb7-c822-6923-26b3-1f8dabb5c2d6

```

响应示例：

```javascript

{
    "code": 0,
    "seal": {
        "id": "2513266167381377036",
        "owner": "2422042838839468036",
        "name": "测试之章55",
        "otherName": "印章别名",
        "type": "COMPANY",
        "spec": {
            "height": 38,
            "width": 38,
            "type": "CIRCULAR",
            "key": "CIRCULAR_38",
            "label": "圆形（直径38mm）",
            "pixelWidth": 107.72,
            "pixelHeight": 107.72
        },
        "sealKey": "20181220-fc8a3937-5f5a-487f-8709-4a6749769153",
        "createTime": "2018-12-20 15:02:29",
        "status": {
            "description": "正常",
            "key": "NORMAL"
        },
        "useCount": 0,
        "category": "ELECTRONIC",
        "users": [
            {
                "id": "2422042842433986573",
                "name": "邓茜茜",
                "mobile": "15021504325",
                "email": "874500173@qq.com",
                "createTime": "2018-04-12 21:33:33"
            }
        ],
        "employees": [
            {
                "id": "2422042843449008143",
                "name": "邓茜茜",
                "mobile": "15021504325"
            }
        ]
    },
    "message": "SUCCESS"
}

```

### 4.5 获取印章图片

Request URL：/seal/image

Request Method： GET

Content-Type： application/x-www-form-urlencoded

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| sealId   | String | 否 | 印章ID |

响应:

印章图片数据

Content-Type：image/png


请求示例：

```HTTP

GET /seal/image?sealId=2494785477926060041 HTTP/1.1
Host: privopen.qiyuesuo.me
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e
Content-Type: application/x-www-form-urlencoded
Cache-Control: no-cache
Postman-Token: 2c909fb7-c822-6923-26b3-1f8dabb5c2d6

```

响应示例：

```javascript

cache-control →no-store, no-cache
content-type →image/png

```

### 4.6 获取用户可使用印章

描述：获取当前用户可使用的印章

Request URL：/seal/user/charge

Request Method： GET


参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyName   | String| 是 | 公司名称 |
| mobile | String | 手机号与员工编号不能同时为空 | 用户手机号 |
| employeeNo | String | 手机号与员工编号不能同时为空 | 员工编号 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Array[Seal] | 印章列表，参照Seal |

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
| category | String | 印章类型：物理章，电子章 |
| sealCategoryName | String | 印章分类名称 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /seal/user/charge?companyName=洁云3&amp; mobile=15850695001 HTTP/1.1
Host: hostname
x-qys-accesstoken: KgyBm8d5j3
x-qys-timestamp: 0
x-qys-signature: e19dd29e1286024b2cbc98f743c432d6

```
响应示例：

```javascript
{
    "result": [
        {
            "id": "2499564880922263564",
            "owner": "2499564877105446918",
            "name": "企业公章",
            "type": "COMPANY",
            "spec": {
                "height": 42,
                "width": 42,
                "type": "CIRCULAR",
                "key": "CIRCULAR_42",
                "pixelWidth": 119.06,
                "pixelHeight": 119.06,
                "label": "圆形（直径42mm）"
            },
            "sealKey": "20181112-6fa2702c-afd4-4d1f-bce0-895a97505bf0",
            "createTime": "2018-11-12 19:38:27",
            "status": {
                "description": "正常",
                "key": "NORMAL"
            },
            "useCount": 64,
            "category": "ELECTRONIC"
        },
        {
            "id": "2499788611535319046",
            "owner": "2499564877105446918",
            "name": "测试专用章",
            "type": "COMPANY",
            "spec": {
                "height": 42,
                "width": 42,
                "type": "CIRCULAR",
                "key": "CIRCULAR_42",
                "pixelWidth": 119.06,
                "pixelHeight": 119.06,
                "label": "圆形（直径42mm）"
            },
            "sealKey": "20181113-421e7bb7-0eca-44e8-a3c9-906c3f512f1a",
            "createTime": "2018-11-13 10:27:29",
            "status": {
                "description": "正常",
                "key": "NORMAL"
            },
            "useCount": 17,
            "category": "ELECTRONIC"
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

### 4.7 获取个人用户印章图片

Request URL：/seal/personalsealimage

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| userId   | String | 用户ID、手机号和身份证号必须有一个不为空 | 用户ID |
| mobile | String | 用户ID、手机号和身份证号必须有一个不为空 | 手机号 |
| cardNo | String | 用户ID、手机号和身份证号必须有一个不为空 | 身份证号 |

响应:

印章图片数据

Content-Type：image/png


请求示例：

```HTTP

GET /seal/personalimage?mobile=131111111111 HTTP/1.1
Host: privopen.qiyuesuo.me
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e
Content-Type: application/x-www-form-urlencoded
Cache-Control: no-cache
Postman-Token: 2c909fb7-c822-6923-26b3-1f8dabb5c2d6

```

响应示例：

```javascript

cache-control →no-store, no-cache
content-type →image/png

```

### 4.8 批量下载个人签名图片

描述：用于获取个人签名图片，根据时间区间获取，默认获取所有个人签名，以压缩包(.zip)格式返回。

Request URL：/seal/personalsealimages

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| startTime | Date | 否 | 查询开始时间，格式：yyyy-MM-dd HH:mm:ss |
| endTime | Date | 否 | 查询截止时间，格式：yyyy-MM-dd HH:mm:ss |

请求示例：

```HTTP

GET /seal/personalsealimages HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab856a1f07e
x-qys-timestamp: 0
```

响应示例：

```HTTP

Content-Type →application/zip
Content-Disposition →attachment;fileName=2252412945004756992.zip 
```

### 4.9 获取时间戳图片

Request URL：/seal/timestampimage

Request Method： GET

响应:

印章图片数据


请求示例：

```HTTP

GET /seal/timestampimage HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript

cache-control →no-store, no-cache
content-type →image/png

```

### 4.10  所有内部企业的印章列表

描述：获取所有内部企业包含平台方的印章列表。

Request URL：/seal/innercompany/seallist

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| category | String | 否 | 印章类型：PHYSICS("物理签章"),ELECTRONIC("电子签章"),不传默认查询电子章 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Array[Company] | 公司列表，参照Company |

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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /seal/innercompany/seallist HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999

```
响应示例：

```javascript
{
    "result": [
        {
            "id": "2570617114465419270",
            "name": "维森集团有限公司",
            "status": "AUTH_SUCCESS",
            "charger": "沈文强",
            "province": "上海",
            "seals": [
                {
                    "id": "2570617118953324556",
                    "owner": "2570617114465419270",
                    "name": "企业公章",
                    "type": "COMPANY",
                    "spec": {
                        "height": 42,
                        "width": 42,
                        "type": "CIRCULAR",
                        "pixelWidth": 119.06,
                        "pixelHeight": 119.06,
                        "label": "圆形（直径42mm）",
                        "key": "CIRCULAR_42"
                    },
                    "sealKey": "20190704-9204aaab-88a0-4dc1-a432",
                    "createTime": "2019-05-27 21:14:40",
                    "status": {
                        "description": "正常",
                        "key": "NORMAL"
                    },
                    "useCount": 807,
                    "category": "ELECTRONIC"
                }
            ],
            "certified": true
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

# 5 业务分类接口

### 5.1 业务分类列表

描述：查询公司下所有的业务分类。

Request URL：/contract/categories

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long	| 否 | 公司ID	|
| companyName |	String | 否 | 公司名称  |

注：若公司ID和公司名称都为空，则查询所有已启用的业务分类。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| categories  | Array[Category] | 该公司业务分类列表 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

Category

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务分类ID |
| name | String | 业务分类名称 |
| tenantId | Long | 公司ID |
| createTime | Date | 创建时间 |
| primary | Boolean | 是否为默认类型 |
| state | Integer | 业务分类状态 0：启用， 1：停用， 2：逻辑删除 |
| sealId | Long | 印章ID |
| config | String | 业务分类配置信息 |
| currentYear | String | 当前保存年份 |
| currentNum | Long | 当前编码流水号 |
| faceSign | Boolean | 是否启用面对面签署 |
| admin | Boolean | 是否是业务分类管理员 |
| permissions | Array[CategoryPermission] | 业务分类配置信息 |

CategoryPermission：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 合同分类权限ID |
| categoryId | Long | 合同分类ID |
| userId | Long | 用户ID |
| userName | String | 用户姓名 |
| mask | Integer | 权限位列掩码 |
| createTime | Date | 创建时间 |

请求示例：

```HTTP

GET /contract/categories?companyName=测试公司 HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript
{
    "code": 0,
    "categories": [
        {
            "id": "2487916444086890506",
            "name": "默认业务分类",
            "tenantId": "2487916440035192836",
            "createTime": "2018-10-11 16:11:44",
            "primary": true,
            "state": 0,
            "faceSign": false,
            "permissions": [
                {
                    "id": "2487916444846059536",
                    "categoryId": "2487916444086890506",
                    "userId": "2474431248612606738",
                    "userName": "张三",
                    "mask": 9,
                    "createTime": "2018-10-11 16:11:44",
                    "view": true,
                    "viewOrSign": true,
                    "admin": true,
                    "send": false,
                    "sign": false
                }
            ]
        }
    ],
    "message": "SUCCESS"
}
```

### 5.2 查询业务分类分组

描述：查询公司下业务分类的分组，返回的分组结构与契约锁私有云中的分组结构一致。

Request URL：/contract/categorygroup

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| companyId | Long	| 否 | 公司ID	|
| companyName |	String | 否 | 公司名称  |
| groupName | String | 否 | 分组名称 |

注：若公司ID和公司名称都为空，则查询平台方下的业务分类。
若分组名为空，则查询公司下的所有分组信息

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result  | Array[CategoryGroup] | 该公司业务分类分组列表 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

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

请求示例：

```HTTP

GET /contract/categorygroup?companyId=&companyName&groupName=实名认证 HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript
{
    "result": [
        {
            "id": "2569101251168764245",
            "name": "实名认证",
            "companyId": "2563294051644768259",
            "leftValue": 8,
            "rightValue": 9,
            "level": 2,
            "categoryCount": 2,
            "disableCount": 0,
            "createTime": "2019-05-23 16:51:10",
            "categoryList": [
                {
                    "id": "2569101320441889116",
                    "name": "银行卡认证",
                    "type": "ELECTRONIC",
                    "tenantId": "2563294051644768259",
                    "createTime": "2019-05-23 16:51:26",
                    "primary": false,
                    "state": 0,
                    "config": "",
                    "downloadSwitch": true,
                    "printSwitch": true,
                    "groupId": "2569101251168764245",
                    "groupName": "实名认证",
                    "faceSign": false,
                    "entityName": "银行卡认证",
                    "entityId": "2569101320441889116"
                },
                {
                    "id": "2569101287134921046",
                    "name": "人脸认证",
                    "type": "ELECTRONIC",
                    "tenantId": "2563294051644768259",
                    "createTime": "2019-05-23 16:51:18",
                    "primary": false,
                    "state": 0,
                    "config": "",
                    "downloadSwitch": true,
                    "printSwitch": true,
                    "groupId": "2569101251168764245",
                    "groupName": "实名认证",
                    "faceSign": false,
                    "entityName": "人脸认证",
                    "entityId": "2569101287134921046"
                }
            ],
			"children": [
                {
                    "id": "2569363876880805895",
                    "name": "Mysql",
                    "companyId": "2563294051644768259",
                    "leftValue": 11,
                    "rightValue": 12,
                    "level": 3,
                    "categoryCount": 0,
                    "disableCount": 0,
                    "createTime": "2019-05-24 10:14:45",
                    "parentId": "2569101251168764245",
                    "categoryList": [],
                    "child": false
                }
            ],
            "child": false
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

### 5.3 业务分类详情


描述：查询业务分类详情（包含模板信息）。

Request URL：/contract/categorydetail

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| categoryId | Long	| 分类ID与分类名称至少一个必填 | 业务分类ID	|
| categoryName | String | 分类ID与分类名称至少一个必填 | 业务分类名称  |

注：只能查询内部企业的业务分类。

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | Category | 业务分类详情 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

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

请求示例：

```HTTP

GET /contract/categorydetail?categoryId=2571538668816531570&categoryName HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e

```

响应示例：

```javascript
{
    "result": {
        "id": "2571538668816531570",
        "name": "默认业务分类",
        "type": "ELECTRONIC",
        "tenantId": "2571538665742106731",
        "createTime": "2019-05-30 10:16:35",
        "primary": true,
        "state": 0,
        "templates": [
            {
                "id": "2576267794210190215",
                "title": "特约经销合同V20190325",
                "createTime": "2019-06-12 11:28:26"
            },
            {
                "id": "2576267809943024529",
                "title": "html-非导入",
                "createTime": "2019-06-06 15:08:55"
            }
        ]
    },
    "code": 0,
    "message": "SUCCESS"
}
```

### 5.4 所有内部企业的业务分类分组

描述：查询所有内部企业下业务分类的分组，返回的分组结构与契约锁私有云中的分组结构一致。

Request URL：/contract/categorygroup/list

Request Method： GET

参数: 无

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result  | Array[Company] | 该公司业务分类分组列表 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

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

请求示例：

```HTTP

GET /contract/categorygroup/list HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc49

```

响应示例：

```javascript
{
    "result": [
        {
            "id": "2570831992022863878",
            "name": "lyc_ms",
            "status": "AUTH_SUCCESS",
            "charger": "李燕超",
            "province": "上海",
            "categoryGroups": [
                {
                    "id": "2581711513355632750",
                    "name": "lyc_ms",
                    "companyId": "2570831992022863878",
                    "leftValue": 0,
                    "rightValue": 7,
                    "level": 0,
                    "categoryCount": 23,
                    "disableCount": 1,
                    "createTime": "2019-06-27 11:59:50",
                    "children": [
                        {
                            "id": "2581711513913475183",
                            "name": "总部业务分类配置",
                            "companyId": "2570831992022863878",
                            "leftValue": 1,
                            "rightValue": 4,
                            "level": 1,
                            "categoryCount": 17,
                            "disableCount": 1,
                            "createTime": "2019-06-27 11:59:50",
                            "children": [
                                {
                                    "id": "2583218861745062259",
                                    "name": "7-1",
                                    "companyId": "2570831992022863878",
                                    "leftValue": 2,
                                    "rightValue": 3,
                                    "level": 2,
                                    "categoryCount": 7,
                                    "disableCount": 1,
                                    "createTime": "2019-07-01 15:49:30",
                                    "parentId": "2581711513913475183",
                                    "categoryList": [
                                        {
                                            "id": "2599516299089809413",
                                            "name": "默认分类9999",
                                            "type": "ELECTRONIC",
                                            "tenantId": "2570831992022863878",
                                            "createTime": "2019-08-15 15:09:42",
                                            "primary": false,
                                            "state": 0,
                                            "downloadSwitch": true,
                                            "printSwitch": true,
                                            "groupId": "2583218861745062259",
                                            "groupName": "7-1",
                                            "canSend": true,
                                            "viewAllContract": false,
                                            "faceSign": false,
                                            "signatoryCount": 0,
                                            "entityName": "默认分类9999",
                                            "entityId": "2599516299089809413"
                                        }
                                    ],
                                    "child": false
                                }
                            ],
                            "parentId": "2581711513355632750",
                            "categoryList": [
                                {
                                    "id": "2596198350334755025",
                                    "name": "hytest",
                                    "type": "PHYSICAL",
                                    "tenantId": "2570831992022863878",
                                    "createTime": "2019-08-06 11:25:21",
                                    "primary": false,
                                    "state": 0,
                                    "downloadSwitch": true,
                                    "printSwitch": true,
                                    "groupId": "2581711513913475183",
                                    "groupName": "总部业务分类配置",
                                    "canSend": true,
                                    "viewAllContract": false,
                                    "faceSign": false,
                                    "signatoryCount": 0,
                                    "entityName": "hytest",
                                    "entityId": "2596198350334755025"
                                }
                            ],
                            "child": true
                        },
                        {
                            "id": "2594061203830853634",
                            "name": "测试1",
                            "companyId": "2570831992022863878",
                            "leftValue": 5,
                            "rightValue": 6,
                            "level": 1,
                            "categoryCount": 6,
                            "disableCount": 0,
                            "createTime": "2019-07-31 13:53:06",
                            "parentId": "2581711513355632750",
                            "categoryList": [
                                {
                                    "id": "2601627121277472785",
                                    "name": "测试分类",
                                    "type": "ELECTRONIC",
                                    "tenantId": "2570831992022863878",
                                    "createTime": "2019-08-21 10:57:21",
                                    "primary": false,
                                    "state": 0,
                                    "downloadSwitch": true,
                                    "printSwitch": true,
                                    "groupId": "2594061203830853634",
                                    "groupName": "测试1",
                                    "canSend": true,
                                    "canAllView": true,
                                    "viewAllContract": false,
                                    "faceSign": false,
                                    "signatoryCount": 0,
                                    "entityName": "测试分类",
                                    "entityId": "2601627121277472785"
                                }
                            ],
                            "child": false
                        }
                    ],
                    "child": true
                }
            ],
            "certified": true
        }
    ],
    "code": 0,
    "message": "SUCCESS"
}
```

#  6 数据签名接口

### 6.1 业务数据签名

描述：业务数据签名。

Request URL： /datasign/sign

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | File | 是 | 要签名的数据 |
| businessId | String | 是 | 业务ID |
| userName | String | 是 | 用户信息 |
| contact | String | 是 | 联系方式（手机或邮箱） |
| cardNo | String | 否 | 身份证号 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| result | String | 签名后数据 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |


请求示例：

```HTTP

POST /msg/strategy/upload HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename=""
Content-Type: 

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="businessId"

123456789
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="userName"

张三
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="contact"

18300000000
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="cardNo"

1234567890123456789
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "result": "569aed...8ee9db",
    "code": 0,
    "message": "SUCCESS"
}
```

### 6.2 签名结果页面

描述：获取签名结果的页面链接。链接有效期为30分钟。

Request URL： /datasign/viewurl

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 业务ID |

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应信息 |
| viewUrl | String | 签名结果页面链接；链接有效期30分钟 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /datasign/viewurl?businessId=2407446180688510983 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例:

```javascript
{
  "code": 0,
  "viewUrl": "http://privapp.qiyuesuo.me/verify?viewToken=OGZkYThhYWUtNWU1Mi00ZDk1LTZl",
  "message": "SUCCESS"
}
```

### 6.3 原始签名数据

描述：业务数据签名。

Request URL： /datasign/getsrcdata

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 业务ID |

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应信息 |
| data | String | 签名数据 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /datasign/getsrcdata?businessId=789456123 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例:

```javascript
{
    "code": 0,
    "data": "Test Data哈哈哈",
    "message": "SUCCESS"
}
```

### 6.4 签名原始数据与证书信息

描述：查询签名的原始数据和证书信息，可选择是否隐藏私有信息（手机号、企业代码等）。

Request URL：/datasign/getsrcandcert

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 业务Id |
| sensitiveInfoInvisible | Boolean | 否 | 私密信息是否不可见 |

返回值：

| 名称 | 类型 | 响应 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应信息 |
| verifyinfo | VerifyResult | 签名结果页面链接；链接有效期30分钟 |

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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

GET /datasign/getsrcandcert?businessId=111111 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例:

```javascript
{
    "verifyinfo": {
        "code": 0,
        "msg": "校验成功",
        "signResult": "6be7ff53e3f2afe416a37b4989838efa13e4a3e358aeea4aef8c47bedbc01b2037b7df34b2cca6e710590a0bf780167eb0bd5cf4279d580beb11e0407aff0aa99d6cad9c64a9d281989e8eadd9757b8c54d2588e48d0846eed4600466becd7fa69ade3869f7ce7a664795642d1cec3b3c71a57b210e4b9cf35b9dacda54fdd5991542e4240fd2d543efc1e330222e5f6f91936d24baf86c85569a40f34c1ef733bb0083276d4aa5217bd6c48804e6cd8d44fa680dcd049c266047380229e0e4bd187e7637dbe411a903cb6bd72e511cb4b05e072b3d36e4d1f5d4b80a36068fa3ae9c8f0a0cfce194a6c73c3608bd4c505a14f19886f7ec0b9e31bd5d1fcd667",
        "strAlgName": "SHA256withRSA",
        "serialNumber": "112478606618793065772099958301348230342",
        "organization": "SHECA Rapid (UniTrust)",
        "signatory": "李风@13111111111",
        "certDateFrom": "2018-11-07 19:58:07",
        "certDateTo": "2018-11-07 20:08:07",
        "signDate": "2018-11-07 19:58:26",
        "srcDate": "Test Data哈哈哈"
    },
    "code": 0,
    "message": "SUCCESS"
}
```

#  7 验签接口

### 7.1 PDF文件验签

**描述**：PDF文件验签。

Request URL： /pdfverifier

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| file | File | 是 |  需要验证的签名文件，仅支持pdf文件 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应描述 |
| statusCode | Int | 验签结果码   |
| statusMsg | String | 验签结果   |
| signatureInfos | jsonString | 签署详情 |
| documentId | String | 文档ID |


请求示例：

```HTTP

POST /pdfverifier HTTP/1.1
Host: hostname
x-qys-accesstoken: seUTZBNQua
x-qys-timestamp: 0
x-qys-signature: 426cfc4999d6d8393a0a7b83f135462e
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="测试文档.pdf"
Content-Type: application/pdf


------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例：

```javascript
{
    "code": 0,
    "statusMsg": "文件未被修改",
    "signatureInfos": [
        {
            "code": 0,
            "msg": "校验成功",
            "signatory": "王五@340826144444444444",
            "signTime": "2018-10-30 11:28:15",
            "signReason": "我同意签署该合同，并承认数字签名的法律效力。",
            "organization": "SHECA Rapid (UniTrust)",
            "strAlgName": "SHA256",
            "signatureCoversWholeDocument": true,
            "hasTimeStamp": false,
            "certDateFrom": "2018-10-30 11:28:11",
            "certDateTo": "2018-10-30 11:38:11",
            "visibleSignature": true,
            "imgString": "data:image/png;base64,iVBORw0KGgoA......",
            "signPlatform": "契约锁电子合同云平台"
        }
    ],
    "documentId": "",
    "message": "SUCCESS",
    "statusCode": 0
}
```

# 8 物理用印接口


### 8.1 授权认证

描述：用于开放平台接口授权认证，第三方平台传递授权参数下发用印授权，返回当前用户对该印章操作的授权码。

Request URL： /seal/apply/multiple

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| tenantName | String | 是 | 发起公司 |
| subject | String | 是 | 主题 |
| categoryId | Long | 否 | 业务分类id |
| serialNo | String | 否 | 序列号 |
| description | String | 否 | 描述 |
| applyerName | String | 否 | 申请人姓名 |
| applyerContact | String | 联系方式与员工编号必须二选一 | 申请人联系方式 |
| applyerNumber | String | 联系方式与员工编号必须二选一 | 申请人员工编号， |
| auths | Array[SealAuthBean] | 是 | 用印授权人；参照SealAuthBean |
| documents | Array[Long] | 否 | 文档ID的集合 |
| forms | Array[Form] | 否 | 第三方传入的表单集合,参照Form |

SealAuthBean（用印授权）：

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| deviceNo | String | deviceNo与sealName至少填写一个| 授权印章识别码 |
| sealName | String | deviceNo与sealName至少填写一个 | 授权印章名称 |
| ownerName | String | 否 | 印章所属公司名称 |
| count | int | 是 | 授权次数 |
| startTime | Date | 否 | 授权使用开始时间,格式样例：2019-04-09 09:18:53 |
| endTime | Date | 否  | 授权使用结束时间,格式样例：2019-04-09 09:18:53 |
| users | Array[Employee] | 是 | 用印授权人；参照Employee |

Employee（用印授权人）:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| mobile | String | 联系手机号与员工编号至少填写一个 | 授权人手机号 |
| number | String | 联系手机号与员工编号至少填写一个 | 授权人员工编号 |

Form(第三方表单):

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| key | String | 是 | 键 |
| value | String | 是 | 值 |

响应：

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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /seal/apply/multiple HTTP/1.1
Host: hostname
x-qys-accesstoken:jlmi3OxWrj
x-qys-timestamp:0
x-qys-signature:9537c7ab5ad6bbe25ac76de126a27981
Content-Type:application/json

{
	"tenantName":"上海契约锁网络科技有限公司",
	"subject":"测试发起2",
	"serialNo":"test-000002",
	"applyerContact":"13588889999",
	"callbackUrl":"http://192.168.51.203:9083/callback",
	"description":"描述",
	"auths":[
		{
			"sealName":"物理章1",
			"ownerName":"契约锁",
			"count":4,
			"users":[
				{
					"mobile":"13588887777"
				}
			]
		},
		{
			"deviceNo":"201810091143943E",
			"count":6,
			"users":[
				{
					"number":"NO000003"
				}
			]
		}	
	]	
}


```

响应示例:

```javascript
{
    "result": {
        "id": "2501966386851659778",  
        "sealAuths": [              
            {
                "id": "2501966388630044675", 
                "userId": "2448782428920709137", 
                "userName": "李四", 
                "vertifyCode": "835200",  
                "contact": "150000000000" ,  
				"number": "000008", 
				"sealId": "2525602783620960258",
				"sealName": "物理章",
				"ownerName": "契约锁", 
				"deviceNo": "201810091143943F" 
            }
        ],
		"status":"USING"
    },
    "code": 0,    
    "message": "SUCCESS" 
}

```

### 8.2 用印结束回调

描述：用于接口回调，在oss平台中的系统设置->物理用印服务->回调设置中设置回调地址，将在用印结束后进行回调。

Request URL：第三方平台定义

Request Method： POST

Content-Type: application/x-www-form-urlencoded

回调参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务Id |
| status | String | 是 | 状态USING:表示用印中,COMPLETE:表示正常结束 |

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 返回信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

响应示例:

```javascript
{
	"code": 0, 
    "message": "SUCCESS"
}
```

### 8.3 用印图片回调

描述：用于接口回调，在oss平台中的系统设置->物理用印服务->回调设置中设置回调地址，将在每次用印后进行回调传送图片。

Request URL：第三方平台定义

Request Method： POST

回调参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务Id |
| number | String | 是 | 此次用印人的员工编号,number和mobile至少有一个不为空 |
| mobile | String | 是 | 此次用印人的手机号,number和mobile至少有一个不为空 |
| imageType | String | 是 | 图片类型,FACE（人脸）;SIGNATORY（用印）;EMERGENCY（应急） |
| images | List | 是 | 回传图片的列表 |

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 返回信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

响应示例:

```javascript
{
	"code": 0,
    "message": "SUCCESS"
}
```

### 8.4 用印剩余次数回调

描述：用于接口回调，在oss平台中的系统设置->物理用印服务->回调设置中设置回调地址，将在每次用印后把用印剩余次数发送给第三方平台。

Request URL：第三方平台定义

Request Method： POST

回调参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务Id |
| number | String | 是 | 此次用印人的编号,number和mobile至少有一个不为空 |
| mobile | String | 是 | 此次用印人的手机号,number和mobile至少有一个不为空 |
| leftCount | String | 是 | 用印剩余次数 |
| timestamp | String | 是 | 此次用印时间的时间戳 |

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 返回信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

响应示例:

```javascript
{
	"code": 0,
    "message": "SUCCESS"
}
```

### 8.5 追加用印次数

描述：用于追加接口用印次数。

Request URL： /seal/apply/append

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |
| count | int | 是 | 追加数量,必须大于0 |
| contact | String | 联系方式与员工编号必须二选一 | 追加人联系方式 |
| number | String | 联系方式与员工编号必须二选一 | 追加人员工编号 |
| contactName | String | 否 | 追加人姓名|
| deviceNo | String | 否 |追加的设备编号,不传默认全部用印设备均增加|

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 返回信息 |
| result  | SealApplyBean | 返回结果；参照SealApplyBean |

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

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /seal/apply/append HTTP/1.1
Host: hostname
x-qys-accesstoken:jlmi3OxWrj
x-qys-timestamp:0
x-qys-signature:9537c7ab5ad6bbe25ac76de126a27981
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="businessId"

2554208591192801404
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="count"

2
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="contactName"

张三
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="contact"

15000000000
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例:

```javascript
{
    "result": {
        "id": "2501966386851659778",  
        "status": "USING",        
        "sealAuths": [             
            {
                "id": "2501966388630044675",  
                "userId": "2448782428920709137",
                "userName": "李四",
                "vertifyCode": "835200",  
                "contact": "13472701093",    
				"number": "000008",  
				"deviceNo": "201810091143943F"
            }
        ]
    },
    "code": 0,               
    "message": "SUCCESS"
}

```

### 8.6 结束用印

描述：用于第三方平台结束此次物理用印申请，接口调用成功后用印状态变为 FINISHED。

Request URL：/seal/apply/finish

Request Method： POST

Content-Type： multipart/form-data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |
| deviceNo | String | 否 | 印章编号，如果不为空，结束指定印章的用印申请；如果为空，结束所有印章的用印申请 |
| contactName | String | 否 | 结束人姓名 |
| contact | String | 联系方式与员工编号必须二选一 | 结束人联系方式 |
| number | String | 联系方式与员工编号必须二选一 | 结束人员工编号 |
| reason | String | 是 | 结束原因|

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 响应信息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /seal/apply/finish HTTP/1.1
Host: hostname
x-qys-accesstoken:jlmi3OxWrj
x-qys-timestamp:0
x-qys-signature:9537c7ab5ad6bbe25ac76de126a27981
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

Content-Disposition: form-data; name="businessId"

2554208591192801404
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="reason"

强制结束用印
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="contactName"

张三
------WebKitFormBoundary7MA4YWxkTrZu0gW--
Content-Disposition: form-data; name="contact"

15000000000
------WebKitFormBoundary7MA4YWxkTrZu0gW--

```

响应示例:

```javascript
{
    "code": 0,  
    "message": "SUCCESS" 
}
```

### 8.7 获取详情

描述：用于获取此次用印申请详情

Request URL：/seal/apply/detail

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 返回信息 |
| result  | SealApplyBean | 返回结果；参照SealApplyBean |

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

sealAuths:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 授权id |
| userId | Long | 授权用户id |
| userName | String | 授权人姓名 |
| leftCount | int | 剩余次数 |
| usedCount | int | 该授权人已用次数 |
| vertifyCode | String | 授权用印码 |
| complete | Boolean | 授权是否结束，true：是，false：否 |
| completeTime | Date | 用印结束时间|
| contact | String | 用印人联系方式 |
| number | String | 用印人员工编号 |
| deviceNo | String | 申请设备的识别码 |

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
| number | String | 追加人编号 |

Seal:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| name | String | 印章名称 |
| useCount | int | 使用次数 |
| deviceId | String | 用印宝识别码 |
| bluetooth | String | 蓝牙地址 |

响应码解释

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 ||


请求示例

```HTTP

GET /seal/apply/detail?businessId=2501966386851659778 HTTP/1.1
Host: hostname
x-qys-accesstoken:jlmi3OxWr
x-qys-timestamp:0
x-qys-signature:9537c7ab5ad6bbe25ac76de126a2798

```

响应示例

```javascript
{
    "result": {
        "id": "2505592705608941573", 
        "tenantName": "上海泛微网络科技股份有限公司",
        "applyerName": "娃哈哈"    
        "applyerContact": "18270888888",  
        "applyerNumber": "NO007",      
        "subject": "测试发起2",  
		"serialNo": "N000001",     
        "count": 2,    
		"description": "测试用印",  
        "status": "FINISHED",
        "createTime": "2018-11-29 10:50:53",  
        "sealAuths": [ 
            { 
			     "id": "2501966388630044675",  
                "userName": "黄玲玲",  
                "leftCount": 0,  
                "usedCount": 17,  
                "vertifyCode": "164778", 
                "complete": true,  
                "completeTime": "2018-11-29 11:31:02",  
                "contact": "150000000000",
                "number": "000008",       
				"deviceNo": "201810091143943F" 
            }
        ],
        "faceImages": [  
            { 	
			  "authId": "2503116143229120514",  
              "photokey": "20181129-2244b6d5-c982-4297-9c1b-d4aaa62941f9"
            }
        ],
        "useImages": [ 
            {
			   authId": "2503116143229120514", 
               "photokey": "20181129-92c7c592-e8bc-49fc-95e5-f1faee423d0b" 
            }
        ],
        "appendLogs": [
            {
                "userName": "黄玲玲",  
                "count": 5,   
                "createTime": "2018-11-29 11:30:14", 
                "contact": "1500000000",  
                "number": "000008"   
            }
        ]
    },
    "code": 0,
    "message": "SUCCESS"
}

```

### 8.8 下载图片

描述：用于获取用印图片，以压缩包(.zip)格式返回。

Request URL：/seal/apply/images/download

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | Long | 是 | 业务ID |
| photoType | String | 否 | 图片类型：FACE(人脸拍照),SIGNATORY(签署拍照),默认获取所有 |

请求示例：

```HTTP

GET /seal/apply/images/download?businessId=2503116143094902785 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例：

```HTTP

Content-Type：application/zip
Content-Disposition: attachment;fileName=2252412945004756992.zip 
```


### 8.9 获取用印图片

描述：用于获取用印图片。

Request URL：/seal/apply/image

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| photokey | String | 是 | 图片key |

响应：

用印图片数据

请求示例：

```HTTP

GET /seal/apply/image?photokey=20181122-50b791d8-91f0-43c7-bff0-d1879a342d40 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
```

响应示例:

```HTTP

Cache-control →no-store, no-cache
Content-type →image/png
```

### 8.10 上传用印图片

描述：用于补传用户缺少的用印图片;若是开放平台接口发起的物理用印申请则填businessId、contact、number字段或sealAuthId字段;若是契约锁页面(即APP)发起，则需要传递sealAuthId字段;其他两个字段-images、type传参逻辑相同。

Request URL：/seal/apply/images

Request Method： POST

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| images | file | 是 | 要上传的图片 |
| sealAuthId | Long | 否 | 该次用印的授权id |
| type | String | 否 | 图片类型，FACE(人脸),SIGNATORY(用印),默认值：SIGNATORY |
| businessId | Long | 否 | 申请id，若不传sealAuthId，则此字段必填 |
| contact | Long | 否 | 用户联系方式，若不传sealAuthId，则此字段和number字段必填一个 |
| number | Long | 否 | 用户工号，若不传sealAuthId，则此字段和contact字段必填一个 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /seal/apply/images HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----
Content-Disposition: form-data; name="file"; filename="/D:/用印图片.jpg


----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

----WebKitFormBoundary7MA4YWxkTrZu0gW

```

响应示例:

```HTTP

Cache-control →no-store, no-cache
Content-type →image/png
```

### 8.11 获取用户拖欠用印图片的用印记录

描述：申请人申请用印申请时，若开启校验机制，则当申请人存在用印次数大于用印图片的情况，则无法发起新的用印申请。

Request URL：/seal/apply/owe/auths

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contact | String | 否 | 用户联系方式，与number字段必填一个 |
| number | String | 否 | 用户编号，与contact字段必填一个 |

响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Int | 响应码 |
| message | String | 响应消息 |
| result | List | 结果集 |

result数组字段解释:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| subject | String | 拖欠图片的用印申请主题 |
| oweUseImageCount | String | 拖欠的图片数量 |
| id | Long | 用印授权id |
| businessId | Long | 用印的申请id |
| useImageCount | int | 上传的用印图片数量 |
| usedCount | int | 用印次数 |

结果集解释:

当result字段存在且长度大于0时，则存在当前申请人有用印次数大于用印图片的记录，申请人无法发起新的用印申请。

请求示例：

```HTTP

GET /seal/apply/owe/auths?contact=152****5948 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0

```

响应示例:

```HTTP
{
    "code": 0,
    "result": [
    		{
			"id": "2586460805589585960",
			"tenantId": "2570831992022863878",
			"userId": "2571639022552441110",
			"userName": "李**",
			"businessId": "2586460804993994784",
			"sealId": "2581787213849497744",
			"subject": "长期按压印章测试1",
			"vertifyCode": "190123",
			"createTime": "2019-07-10 14:31:50",
			"complete": true,
			"completeTime": "2019-07-10 14:37:17",
			"oweUseImageCount": 3
		}
    ],
    "message": "SUCCESS"
}
```

### 8.12 通过业务分类发起用印

描述：用于开放平台接口使用业务分类发起用印

Request URL： /seal/apply/mulitipleByCategory

Request Method： POST

Content-Type： application/json

参数:

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
| auths | Array[SealAuthBean] | 是 | 用印授权人；参照SealAuthBean |
| flowNodes | Array[ThirdFlowNode] | 是 | 第三方审批节点；参照ThirdFlowNode |
| documents | Array[Long] | 否 | 文档ID的集合 |
| forms | Array[Form] | 否 | 第三方传入的表单集合,参照Form |

SealAuthBean（用印授权）：

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

响应：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| code | Integer | 响应码 |
| message | String | 响应信息 |
| result  | SealApplyBean | 返回结果；参照SealApplyBean |

SealApplyBean：

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| id | Long | 业务主键 |
| status | String | 申请状态 |

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /seal/apply/multiple HTTP/1.1
Host: hostname
x-qys-accesstoken:jlmi3OxWrj
x-qys-timestamp:0
x-qys-signature:9537c7ab5ad6bbe25ac76de126a27981
Content-Type:application/json

{
	"tenantName":"上海契约锁网络科技有限公司",
    "categoryId":"2596607614068138038",
	"subject":"测试发起2",
	"serialNo":"test-000002",
	"applyerContact":"13588889999",
	"description":"描述",
	"auths":[
		{
			"sealName":"物理章1",
			"ownerName":"契约锁",
			"count":4
		},
		{
			"deviceNo":"201810091143943E",
			"count":6
		}
	]
}
```

响应示例:

```javascript
{
    "result": {
        "id": "2598424636884172818",
        "status": "USING"
    },
    "code": 0,
    "message": "SUCCESS"
}

```

### 8.13 用印后文件上传(图片列表)

描述：用于开放平台接口在用印完成后上传用印文件的图片列表, 系统会自动转换为pdf

Request URL： /seal/apply/used/images

Request Method： POST

Content-Type： form/data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 用印申请id |
| images | Array[MultipartFile] | 是 | 用印文件的图片列表,支持jpg,jpeg,gif,png,tiff |
| title | String | 是 | 文件标题 |

请求示例：

```HTTP

POST /seal/apply/used/file HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----
Content-Disposition: form-data; name="images"; filename="/D:/用印文件.pdf"


----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="businessId"

2598426864373596177
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

文件标题
```

### 8.14 用印后文件上传

描述：用于开放平台接口在用印完成后上传用印文件

Request URL： /seal/apply/used/file

Request Method： POST

Content-Type： form/data

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 用印申请id |
| file | MultipartFile | 是 | 用印文件,只支持docx和pdf |
| title | String | 是 | 文件标题 |

请求示例：

```HTTP

POST /seal/apply/used/file HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
Content-Type: multipart/form-data; boundary=----
Content-Disposition: form-data; name="file"; filename="/D:/用印文件.pdf"


----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="businessId"

2598426864373596177
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="title"

文件标题

```
### 8.15 修改用印时间

描述：用于修改用印的开始和结束时间

Request URL：/seal/apply/updateUseSealTime

Request Method： POST

Content-Type： application/json

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| businessId | String | 是 | 用印申请id |
| deviceNo | String | 否 | 授权印章识别码  |
| sealName | String | 否 | 印章名称 |
| ownerName | String | 否 | 公司名称 |
| startTime | Date | 是 | 开始时间 |
| endTime | Date | 是 | 结束时间 |
参数备注:当deviceNo、sealName、ownerName都不传参,默认更新当前用印所有未结束的印章使用时间。

响应码解释:

| 响应码 | 描述 |
| -------- | -------- |
| 0 | 请求成功 |
| 1000000 | 未知错误 |
| 1000001 | 参数错误 |

请求示例：

```HTTP

POST /seal/apply/updateUseSealTime HTTP/1.1
Host: hostname
x-qys-accesstoken:jlmi3OxWrj
x-qys-timestamp:0
x-qys-signature:9537c7ab5ad6bbe25ac76de126a27981
Content-Type:application/json
{
	"businessId":"2598875823213387783",
	"sealName":"1",
	"deviceNo":"",
	"ownerName":"",
	"startTime":"2019-08-27 11:07:51",
	"endTime":"2019-08-28 11:07:52"
}
```

响应示例:

```javascript
{
    "code": 0,  
    "message": "SUCCESS" 
}
```

# 9 存证接口


### 9.1 存证下载

描述：用于下载存证报告

Request URL：/evidence/download

Request Method： GET

参数:

| 名称 | 类型 | 是否必须 | 描述 |
| -------- | -------- | -------- | -------- |
| contractId | Long | 是 | 合同id |

响应：

合同存证报告文件流

请求示例：

```HTTP

GET /evidence/download?contractId=2523027645856763977 HTTP/1.1
Host: hostname
x-qys-accesstoken: cwdYln1l6e
x-qys-signature: b267a7887b08af7230bab8c956a1f07e
x-qys-timestamp: 0
```

响应示例:

```HTTP

Content-Type →application/pdf
Content-Disposition →attachment;fileName=2252412945004756992.pdf
```

#  10 开放平台健康检查

描述：调用健康检查接口获取系统健康情况。

Request URL： /checkHealth

Request Method： GET

参数: 无


响应:

| 名称 | 类型 | 描述 |
| -------- | -------- | -------- |
| ok | String | 健康 |

请求示例：

```HTTP

GET /checkHealth  HTTP/1.1
Host: hostname
x-qys-accesstoken: INblkBaTJq
x-qys-timestamp: 0
x-qys-signature: be46397bdf7c78b9ec6eeb216f4af2e5

```

响应示例：

```javascript
OK
```
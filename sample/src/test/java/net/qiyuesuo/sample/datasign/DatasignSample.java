package net.qiyuesuo.sample.datasign;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.sdk.SDKClient;
import net.qiyuesuo.sdk.api.DataSignService;
import net.qiyuesuo.sdk.bean.company.Company;
import net.qiyuesuo.sdk.bean.datasign.DataSignBean;
import net.qiyuesuo.sdk.bean.datasign.DataSignRequest;
import net.qiyuesuo.sdk.bean.datasign.DataSignSignatory;
import net.qiyuesuo.sdk.bean.seal.Seal;
import net.qiyuesuo.sdk.bean.user.User;
import net.qiyuesuo.sdk.common.exception.PrivateAppException;
import net.qiyuesuo.sdk.common.http.StreamFile;
import net.qiyuesuo.sdk.common.json.JSONUtils;
import net.qiyuesuo.sdk.impl.DataSignServiceImpl;

/**
 * 数据签名示例
 */
public class DatasignSample {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private DataSignService dataSignService;
	private Long dataId;

	@Before
	public void init() {
		String serverUrl = "http://privopen.qiyuesuo.me"; // 签章平台-开放平台地址，一般在 http://[host]:9182
		String accessKey = "DWjfxmAl1w"; // 接入token，在签章平台-控制台的 “集成中心-集成管理”的“应用接入”中创建、查看
		String accessSecret = "x7rzj4Rm3CGNol9FBJACPoB8uF7PWw"; // 接入密钥，在签章平台-控制台的 “集成中心-集成管理”的“应用接入”中创建、查看
		SDKClient sdkClient = new SDKClient(serverUrl, accessKey, accessSecret);
		this.dataSignService = new DataSignServiceImpl(sdkClient);
	}
	
	@Test
	public void testAll() throws Exception {
		dataSign();
		detail();
		download();
	}

	/**
	 * 公司签署方签署文件
	 * 必须填写公司签署方名称
	 */
	@Test
	public void dataSign() throws Exception {
		DataSignBean dataSignBean = new DataSignBean();
		// 签名原文件
		dataSignBean.setData(new StreamFile(new FileInputStream(new File("file/contract.pdf"))));
		
		DataSignSignatory signatory = new DataSignSignatory();
		signatory.setBizId("20010012"); // 对接方业务ID，非必填
		
		// 公司签署方信息
		Company company = new Company();
		company.setName("维森集团有限公司"); // 公司名称（只能是平台方或内部企业）
		signatory.setCompany(company);
		// 签署使用的印章（非必填）
		Seal seal = new Seal();
		seal.setName("企业公章");
		signatory.setSeal(seal);
		// 签署操作人（非必填）
		User operator = new User();
		operator.setName("张三");
		operator.setContact("15000000000");
		signatory.setOperator(operator);
		dataSignBean.setSignatory(signatory);
		dataId = dataSignService.sign(dataSignBean);
		logger.info("公司数据签名签署成功，签名ID：{}", dataId);
	}
	
	/**
	 * 获取数据签名的详细信息
	 */
	@Test
	public void detail() throws PrivateAppException {
		DataSignRequest request = new DataSignRequest();
		request.setId(dataId);
		DataSignSignatory dataSignSignatory = dataSignService.detail(request);
		logger.info("获取数据签名详细信息成功:" + JSONUtils.toJson(dataSignSignatory));
	}
	
	/**
	 * 下载数据签名原文文件
	 */
	@Test
	public void download() throws Exception {
		DataSignRequest request = new DataSignRequest();
		request.setId(dataId);
		OutputStream outputStream = new FileOutputStream("file/data.pdf");
		dataSignService.download(request, outputStream);
		logger.info("数据签名原文下载成功");
	}
}

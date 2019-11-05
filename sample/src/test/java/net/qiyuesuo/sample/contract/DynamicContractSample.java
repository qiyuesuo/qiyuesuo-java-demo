package net.qiyuesuo.sample.contract;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.sample.contract.param.CompanyReceiver;
import net.qiyuesuo.sample.contract.param.PersonalReceiver;
import net.qiyuesuo.sample.contract.param.Sender;
import net.qiyuesuo.sdk.SDKClient;

/**
 * 动态添加签署方的形式创建合同：创建合同时不指定签署方，在签署时再指定；
 * 注意：在所有签署方签署完成后，需要主动调用结束合同接口来结束合同；
 * 适用场景：创建合同时，签署方不确定，需要在具体签署时才确定签署方；
 */
public class DynamicContractSample {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private DynamicContractService createContractService;
	private DocumentService documentService;
	private SignContractService signContractService;
	private Long documentId;
	private Long contractId;
	private Sender sender;
	private CompanyReceiver companyReceiver;
	private PersonalReceiver personalReceiver;
	private Long templateId; // 模板ID，用于创建模板文档
	private Map<String, String> params; // 模板参数，用于创建模板文档
	
	@Before
	public void init() {
		String serverUrl = "http://192.168.0.140:9182"; // 签章平台-开放平台地址，一般在 http://[host]:9182
		String accessKey = "DWjfxmAl1a"; // 接入token，在签章平台-控制台的 “集成中心-集成管理”的“应用接入”中创建、查看
		String accessSecret = "x7rzj4Rm3CGNol9FBJACPoB8uF7PWa"; // 接入密钥，在签章平台-控制台的 “集成中心-集成管理”的“应用接入”中创建、查看
		SDKClient sdkClient = new SDKClient(serverUrl, accessKey, accessSecret);
		createContractService = new DynamicContractService(sdkClient);
		documentService = new DocumentService(sdkClient);
		signContractService = new SignContractService(sdkClient);

		Long categoryId = 2629283493798740228L; // 业务分类ID
		Long sealId = 2628522311019635000L; // 印章ID
		String tenantName = "维森集团有限公司"; // 发起方公司名称（平台名称/内部企业名称）
		String creatorContact = "15000000001"; // 合同发起人联系方式
		String creatorName = "宋三"; // 合同发起人姓名
		String companyReceiverName = "测试公司"; // 公司接收方名称
		String receiverContact = "15000000002"; // 接收人联系方式
		String receiverName = "李四"; // 接收人人姓名
		this.sender = new Sender(categoryId, sealId, tenantName, creatorContact, creatorName);
		this.companyReceiver = new CompanyReceiver(companyReceiverName, receiverContact, receiverName);
		this.personalReceiver = new PersonalReceiver(receiverName, receiverContact);
		this.templateId = 2628812821298720813L;
		this.params = new HashMap<String, String>();
		this.params.put("param1", "value1");
		this.params.put("param2", "value2");
	}
	
	/**
	 * 发送合同给公司、签署、下载；
	 */
	@Test
	public void toCompanyTest() throws Exception {
		createDocumentByFile();
		createContractToCompany();
		senderSign();
		companySignUrl();
//		completeContract(); // 待所有签署方签署完成后，调用结束合同接口结束合同
		downloadDocument();
	}
	
	/**
	 * 发送合同给个人、签署、下载；
	 */
	@Test
	public void toPersonalTest() throws Exception {
		createDocumentByFile();
		createContractToPersonal();
		senderSign();
		personalSignUrl();
//		completeContract(); // 待所有签署方签署完成后，调用结束合同接口结束合同
		downloadDocument();
	}
	
	@Test
	public void createDocumentByFile() throws Exception {
		documentId = documentService.createByFile();
		logger.info("创建合同文档，文档ID:{}", documentId);
	}
	
	@Test
	public void createDocumentByTemplate() throws Exception {
		documentId = documentService.createByTemplate(templateId, params);
		logger.info("创建合同文档，文档ID:{}", documentId);
	}

	@Test
	public void createContractToCompany() throws Exception {
		contractId = createContractService.createCompanyContract(sender, documentId);
		logger.info("创建合同，合同ID:{}", contractId);
	}

	@Test
	public void createContractToPersonal() throws Exception {
		contractId = createContractService.createPersonalContract(sender, documentId);
		logger.info("创建合同，合同ID:{}", contractId);
	}
	
	@Test
	public void senderSign() throws Exception {
		signContractService.senderSignWithLocation(sender, contractId, documentId);
		logger.info("发起方签署合同");
	}
	
	@Test
	public void companySignUrl() throws Exception {
		String signUrl = signContractService.addAndGetCompanySignUrl(companyReceiver, contractId, documentId);
		logger.info("添加公司签署方，公司接收方签署页面链接，url:{}", signUrl);
	}
	
	@Test
	public void personalSignUrl() throws Exception {
		String signUrl = signContractService.addAndGetPersonalSignUrl(personalReceiver, contractId, documentId);
		logger.info("添加个人签署方，个人接收方签署页面链接，url:{}", signUrl);
	}
	
	@Test
	public void downloadDocument() throws Exception {
		documentService.download(documentId, "dynamicDoc.pdf");
		logger.info("下载合同文档");
	}
	
	@Test
	public void completeContract() throws Exception {
		createContractService.complete(contractId);
		logger.info("结束签署，封存合同");
	}

}

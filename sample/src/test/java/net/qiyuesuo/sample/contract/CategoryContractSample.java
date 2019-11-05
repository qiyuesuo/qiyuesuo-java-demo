package net.qiyuesuo.sample.contract;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.qiyuesuo.sample.contract.param.CompanyReceiver;
import net.qiyuesuo.sample.contract.param.PersonalReceiver;
import net.qiyuesuo.sample.contract.param.Sender;
import net.qiyuesuo.sdk.SDKClient;
import net.qiyuesuo.sdk.bean.contract.ContractDetail;
import net.qiyuesuo.sdk.bean.contract.Document;

/**
 * 使用业务分类创建合同：创建合同时使用业务分类中配置的签署动作、印章、签署位置、模板等信息；
 * 适用场景：签署方类型、数量确定，合同文件有统一的格式；
 */
public class CategoryContractSample {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private CategoryContractService createContractService;
	private DocumentService documentService;
	private SignContractService signContractService;
	private Long contractId;
	private Sender sender;
	private CompanyReceiver companyReceiver;
	private PersonalReceiver personalReceiver;
	
	@Before
	public void init() {
		String serverUrl = "http://192.168.0.140:9182"; // 签章平台-开放平台地址，一般在 http://[host]:9182
		String accessKey = "DWjfxmAl1a"; // 接入token，在签章平台-控制台的 “集成中心-集成管理”的“应用接入”中创建、查看
		String accessSecret = "x7rzj4Rm3CGNol9FBJACPoB8uF7PWa"; // 接入密钥，在签章平台-控制台的 “集成中心-集成管理”的“应用接入”中创建、查看
		SDKClient sdkClient = new SDKClient(serverUrl, accessKey, accessSecret);
		createContractService = new CategoryContractService(sdkClient);
		documentService = new DocumentService(sdkClient);
		signContractService = new SignContractService(sdkClient);

		Long categoryId = 2628810253810672085L; // 业务分类ID
		String tenantName = "维森集团有限公司"; // 发起方公司名称（平台名称/内部企业名称）
		String creatorContact = "15000000001"; // 合同发起人联系方式
		String creatorName = "宋三"; // 合同发起人姓名
		String companyReceiverName = "测试公司"; // 公司接收方名称
		String receiverContact = "15000000002"; // 接收人联系方式
		String receiverName = "李四"; // 接收人人姓名
		this.sender = new Sender(categoryId, null, tenantName, creatorContact, creatorName);
		this.companyReceiver = new CompanyReceiver(companyReceiverName, receiverContact, receiverName);
		this.personalReceiver = new PersonalReceiver(receiverName, receiverContact);
	}
	
	/**
	 * 发送合同给公司、签署、下载；
	 */
	@Test
	public void toCompanyTest() throws Exception {
		createContractToCompany();
		senderSign();
		companySignUrl();
		downloadDocument();
	}
	
	/**
	 * 发送合同给个人、签署、下载；
	 */
	@Test
	public void toPersonalTest() throws Exception {
		createContractToPersonal();
		senderSign();
		personalSignUrl();
		downloadDocument();
	}

	@Test
	public void createContractToCompany() throws Exception {
		contractId = createContractService.createCompanyContract(sender, companyReceiver);
		logger.info("创建合同，合同ID:{}", contractId);
	}

	@Test
	public void createContractToPersonal() throws Exception {
		contractId = createContractService.createPersonalContract(sender, personalReceiver);
		logger.info("创建合同，合同ID:{}", contractId);
	}
	
	@Test
	public void senderSign() throws Exception {
		signContractService.senderSign(sender, contractId);
		logger.info("发起方签署合同");
	}
	
	@Test
	public void companySignUrl() throws Exception {
		String signUrl = signContractService.companySignUrl(companyReceiver, contractId);
		logger.info("公司接收方签署页面链接，url:{}", signUrl);
	}
	
	@Test
	public void personalSignUrl() throws Exception {
		String signUrl = signContractService.personalSignUrl(personalReceiver, contractId);
		logger.info("公司接收方签署页面链接，url:{}", signUrl);
	}
	
	@Test
	public void downloadDocument() throws Exception {
		ContractDetail detail = createContractService.detail(contractId);
		for(Document item : detail.getDocuments()) {
			documentService.download(item.getId(), "categoryDoc.pdf");
			logger.info("下载合同文档");
		}
	}

}

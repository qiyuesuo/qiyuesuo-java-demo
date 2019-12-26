package net.qiyuesuo.sample.contract;

import net.qiyuesuo.sample.contract.param.Sender;
import net.qiyuesuo.sdk.SDKClient;
import net.qiyuesuo.sdk.api.ContractService;
import net.qiyuesuo.sdk.bean.contract.CreateContractRequest;
import net.qiyuesuo.sdk.impl.ContractServiceImpl;

/**
 * 动态添加签署方的形式创建合同：创建合同时不指定签署方，在签署时再指定；
 * 注意：在所有签署方签署完成后，需要主动调用结束合同接口来结束合同；
 * 适用场景：创建合同时，签署方不确定，需要在具体签署时才确定签署方；
 * 业务分类配置，登录签章平台，进行如下操作：
 * 	1、进入选项“配置-业务流配置”，新增业务分类；
 *  2、在“签署方”选项中，选择“默认”签署方；
 */
public class DynamicContractService {
	private ContractService contractService;
	
	public DynamicContractService(SDKClient sdkClient) {
		this.contractService = new ContractServiceImpl(sdkClient);
	}
	
	/**
	 * 创建动态合同：指定合同文档，不指定签署方（签署方在签署时动态添加）
	 */
	public Long createCompanyContract(Sender sender, Long documentId) throws Exception {
		CreateContractRequest request = buildBasicRequest(sender, documentId);
		request.setSubject("动态合同-发给公司"); // 合同名称
		return contractService.createContractByCategory(request);
	}
	
	/**
	 * 创建动态合同：指定合同文档，不指定签署方（签署方在签署时动态添加）
	 */
	public Long createPersonalContract(Sender sender, Long documentId) throws Exception {
		CreateContractRequest request = buildBasicRequest(sender, documentId);
		request.setSubject("动态合同-发给个人"); // 合同名称
		return contractService.createContractByCategory(request);
	}
	
	private CreateContractRequest buildBasicRequest(Sender sender, Long documentId) {
		CreateContractRequest request = new CreateContractRequest();
		request.setTenantName(sender.getTenantName()); // 设置合同发起方
		request.setCategoryId(sender.getCategoryId()); // 业务分类ID，非必填（为空时使用默认业务分类），业务分类在签章平台“配置-业务流配置”中创建、查看
		request.setCreatorName(sender.getCreatorName()); // 创建人姓名，非必填
		request.setCreatorContact(sender.getCreatorContact()); // 创建人联系方式，非必填
		request.addDocument(documentId); // 合同文档
		request.setSend(true); // 是否发起合同，默认为true
		return request;
	}
	
	/**
	 * 结束签署，封存合同
	 */
	public void complete(Long contractId) throws Exception {
		contractService.complete(contractId);
	}
	
}

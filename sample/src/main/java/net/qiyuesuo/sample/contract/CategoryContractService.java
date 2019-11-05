package net.qiyuesuo.sample.contract;

import net.qiyuesuo.sample.contract.param.CompanyReceiver;
import net.qiyuesuo.sample.contract.param.PersonalReceiver;
import net.qiyuesuo.sample.contract.param.Sender;
import net.qiyuesuo.sdk.SDKClient;
import net.qiyuesuo.sdk.api.ContractService;
import net.qiyuesuo.sdk.bean.company.TenantType;
import net.qiyuesuo.sdk.bean.contract.ContractDetail;
import net.qiyuesuo.sdk.bean.contract.CreateContractRequest;
import net.qiyuesuo.sdk.bean.sign.Signatory;
import net.qiyuesuo.sdk.impl.ContractServiceImpl;

/**
 * 使用业务分类创建合同：创建合同时使用业务分类中配置的签署动作、印章、签署位置、模板等信息；
 * 适用场景：签署方类型、数量确定，合同文件有统一的格式；
 * 业务分类配置，登录签章平台，进行如下操作：
 * 	1、进入选项“配置-业务流配置”，新增业务分类；
 *  2、在“签署方”选项中，选择“预设”签署方，并维护好签署方信息（包括接收方、以及接收方的签署动作）；
 *  3、在“审批流”选项中，配置审批流（审批流即发起方的签署动作），以及其下的印章、签章人；
 *  4、在“模板”选项中，绑定模板、设置签署位置、设置参数填写方；
 *  5、在“其他”选项的“回调设置”中，添加“全局回调-文件全部签署完成回调”回调函数，以便合同签署完成后，签章平台回调通知对接方；
 */
public class CategoryContractService {
	private ContractService contractService;
	
	public CategoryContractService(SDKClient sdkClient) {
		this.contractService = new ContractServiceImpl(sdkClient);
	}
	
	/**
	 * 使用业务分类创建合同：包含一个合同文件，两个签署方（发起方、公司接收方）
	 */
	public Long createCompanyContract(Sender sender, CompanyReceiver receiver) throws Exception {
		CreateContractRequest request = buildBasicRequest(sender);
		request.setSubject("业务分类创建合同-发给公司"); // 合同名称
		Signatory sigSender = buildSender(sender); // 签署方-发起方，需要发起方签署时添加
		Signatory sigReceiver = buildCompanyReceiver(receiver); // 签署方-接收方
		request.addSignatory(sigSender);
		request.addSignatory(sigReceiver);
		return contractService.createContractByCategory(request);
	}
	
	/**
	 * 使用业务分类创建合同：包含一个合同文件，两个签署方（发起方、个人接收方）
	 */
	public Long createPersonalContract(Sender sender, PersonalReceiver receiver) throws Exception {
		CreateContractRequest request = buildBasicRequest(sender);
		request.setSubject("业务分类创建合同-发给个人"); // 合同名称
		Signatory sigSender = buildSender(sender); // 签署方-发起方，需要发起方签署时添加
		Signatory sigReceiver = buildPersonalReceiver(receiver); // 签署方-接收方
		request.addSignatory(sigSender);
		request.addSignatory(sigReceiver);
		return contractService.createContractByCategory(request);
	}
	
	/**
	 * 获取合同详情
	 */
	public ContractDetail detail(Long contractId) throws Exception {
		return contractService.detail(contractId);
	}
	
	private CreateContractRequest buildBasicRequest(Sender sender) {
		CreateContractRequest request = new CreateContractRequest();
		request.setCategoryId(sender.getCategoryId()); // 业务分类ID，非必填（为空时使用默认业务分类），业务分类在签章平台“配置-业务流配置”中创建、查看
		request.setCreatorName(sender.getCreatorName()); // 创建人姓名，非必填
		request.setCreatorContact(sender.getCreatorContact()); // 创建人联系方式，非必填
		request.setSend(true); // 是否发起合同，默认为true
		return request;
	}
	
	private Signatory buildSender(Sender sender) {
		Signatory signatory = new Signatory();
		signatory.setTenantType(TenantType.COMPANY); // 签署方类型，必填：COMPANY（企业），PERSONAL（个人）
		signatory.setTenantName(sender.getTenantName()); // 签署方名称，必填
		signatory.setSerialNo(1); // 签署顺序，从1开始
		return signatory;
	}
	
	private Signatory buildCompanyReceiver(CompanyReceiver receiver) {
		Signatory signatory = new Signatory();
		signatory.setTenantType(TenantType.COMPANY); // 签署方类型，必填：COMPANY（企业），PERSONAL（个人）
		signatory.setTenantName(receiver.getTenantName()); // 签署方名称，必填
		signatory.setContact(receiver.getReceiverContact()); // 经办人联系方式，非必填（接收方企业不存在时必填，需要经办人进行企业认证）
		signatory.setReceiverName(receiver.getReceiverName()); // 经办人姓名，非必填
		signatory.setSerialNo(2); // 签署顺序，从1开始
		return signatory;
	}
	
	private Signatory buildPersonalReceiver(PersonalReceiver receiver) {
		Signatory signatory = new Signatory();
		signatory.setTenantType(TenantType.PERSONAL); // 签署方类型，必填：COMPANY（企业），PERSONAL（个人）
		signatory.setTenantName(receiver.getTenantName()); // 签署方名称，必填
		signatory.setContact(receiver.getReceiverContact()); // 联系方式，必填
		signatory.setSerialNo(2); // 签署顺序，从1开始
		return signatory;
	}

}

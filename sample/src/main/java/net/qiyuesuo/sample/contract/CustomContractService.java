package net.qiyuesuo.sample.contract;

import net.qiyuesuo.sample.contract.param.CompanyReceiver;
import net.qiyuesuo.sample.contract.param.PersonalReceiver;
import net.qiyuesuo.sample.contract.param.Sender;
import net.qiyuesuo.sdk.SDKClient;
import net.qiyuesuo.sdk.api.ContractService;
import net.qiyuesuo.sdk.bean.company.TenantType;
import net.qiyuesuo.sdk.bean.contract.Action;
import net.qiyuesuo.sdk.bean.contract.ActionType;
import net.qiyuesuo.sdk.bean.contract.CreateContractRequest;
import net.qiyuesuo.sdk.bean.sign.Signatory;
import net.qiyuesuo.sdk.bean.sign.SignatoryRect;
import net.qiyuesuo.sdk.impl.ContractServiceImpl;

/**
 * 自定义创建合同：创建合同时使用参数中传入的签署动作、印章、签署位置信息；
 * 适用场景：签署方类型、数量不固定，合同文件无统一格式；
 * 业务分类配置，登录签章平台，进行如下操作：
 * 	1、进入选项“配置-业务流配置”，新增业务分类；
 *  2、在“签署方”选项中，选择“默认”签署方；
 */
public class CustomContractService {
	private ContractService contractService;
	
	public CustomContractService(SDKClient sdkClient) {
		this.contractService = new ContractServiceImpl(sdkClient);
	}
	
	/**
	 * 创建自定义合同：包含一个合同文件，两个签署方（发起方、公司接收方）
	 */
	public Long createCompanyContract(Sender sender, CompanyReceiver receiver, Long documentId) throws Exception {
		CreateContractRequest request = buildBasicRequest(sender, documentId);
		request.setSubject("自定义合同-发给公司"); // 合同名称
		Signatory sigSender = buildSender(sender, documentId); // 签署方-发起方，需要发起方签署时添加
		Signatory sigReceiver = buildCompanyReceiver(receiver, documentId); // 签署方-接收方
		request.addSignatory(sigSender);
		request.addSignatory(sigReceiver);
		return contractService.createContractByCategory(request);
	}
	
	/**
	 * 创建自定义合同：包含一个合同文件，两个签署方（发起方、个人接收方）
	 */
	public Long createPersonalContract(Sender sender, PersonalReceiver receiver, Long documentId) throws Exception {
		CreateContractRequest request = buildBasicRequest(sender, documentId);
		request.setSubject("自定义合同-发给个人"); // 合同名称
		Signatory sigSender = buildSender(sender, documentId); // 签署方-发起方，需要发起方签署时添加
		Signatory sigReceiver = buildPersonalReceiver(receiver, documentId); // 签署方-接收方
		request.addSignatory(sigSender);
		request.addSignatory(sigReceiver);
		return contractService.createContractByCategory(request);
	}
	
	private CreateContractRequest buildBasicRequest(Sender sender, Long documentId) {
		CreateContractRequest request = new CreateContractRequest();
		request.setCategoryId(sender.getCategoryId()); // 业务分类ID，非必填（为空时使用默认业务分类），业务分类在签章平台“配置-业务流配置”中创建、查看
		request.setCreatorName(sender.getCreatorName()); // 创建人姓名，非必填
		request.setCreatorContact(sender.getCreatorContact()); // 创建人联系方式，非必填
		request.addDocument(documentId); // 合同文档
		request.setSend(true); // 是否发起合同，默认为true
		return request;
	}
	
	private Signatory buildSender(Sender sender, Long documentId) {
		Signatory signatory = new Signatory();
		signatory.setTenantType(TenantType.COMPANY); // 签署方类型，必填：COMPANY（企业），PERSONAL（个人）
		signatory.setTenantName(sender.getTenantName()); // 签署方名称，必填
		signatory.setSerialNo(1); // 签署顺序，从1开始
		// 指定签署动作，一个签署方下可以有多个签署动作
		Action action = new Action();
		action.setType(ActionType.CORPORATE); // 签署动作类型，必填：CORPORATE（企业签章），LP（法定代表人签字），OPERATOR（经办人签字）
		action.setSerialNo(1); // 签署顺序，从1开始，必填
		action.setSealId(sender.getSealId()); // 签署印章，非必填
		signatory.addAction(action);
		// 指定签署位置
		SignatoryRect rect = new SignatoryRect();
		rect.setDocumentId(documentId);
		rect.setKeyword("甲方签字：");
		action.addLocation(rect);
		return signatory;
	}
	
	private Signatory buildCompanyReceiver(CompanyReceiver receiver, Long documentId) {
		Signatory signatory = new Signatory();
		signatory.setTenantType(TenantType.COMPANY); // 签署方类型，必填：COMPANY（企业），PERSONAL（个人）
		signatory.setTenantName(receiver.getTenantName()); // 签署方名称，必填
		signatory.setContact(receiver.getReceiverContact()); // 经办人联系方式，非必填（接收方企业不存在时必填，需要经办人进行企业认证）
		signatory.setReceiverName(receiver.getReceiverName()); // 经办人姓名，非必填
		signatory.setSerialNo(2); // 签署顺序，从1开始
		// 指定签署动作，一个签署方下可以有多个签署动作
		Action action = new Action();
		action.setType(ActionType.CORPORATE); // 签署动作类型：CORPORATE（企业签章），LP（法定代表人签字），OPERATOR（经办人签字）
		action.setSerialNo(1); // 签署顺序，从1开始
		signatory.addAction(action);
		// 指定签署位置
		SignatoryRect rect = new SignatoryRect();
		rect.setDocumentId(documentId);
		rect.setKeyword("乙方签字：");
		action.addLocation(rect);
		return signatory;
	}
	
	private Signatory buildPersonalReceiver(PersonalReceiver receiver, Long documentId) {
		Signatory signatory = new Signatory();
		signatory.setTenantType(TenantType.PERSONAL); // 签署方类型，必填：COMPANY（企业），PERSONAL（个人）
		signatory.setTenantName(receiver.getTenantName()); // 签署方名称，必填
		signatory.setContact(receiver.getReceiverContact()); // 联系方式，必填
		signatory.setSerialNo(2); // 签署顺序，从1开始
		// 指定签署动作，一个签署方下可以有多个签署动作
		Action action = new Action();
		action.setType(ActionType.PERSONAL); // 签署动作类型：CORPORATE（企业签章），LP（法定代表人签字），OPERATOR（经办人签字）
		action.setSerialNo(1); // 签署顺序，从1开始
		signatory.addAction(action);
		// 指定签署位置
		SignatoryRect rect = new SignatoryRect();
		rect.setDocumentId(documentId);
		rect.setKeyword("乙方签字：");
		action.addLocation(rect);
		return signatory;
	}

}

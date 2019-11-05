package net.qiyuesuo.sample.contract;

import net.qiyuesuo.sample.contract.param.CompanyReceiver;
import net.qiyuesuo.sample.contract.param.PersonalReceiver;
import net.qiyuesuo.sample.contract.param.Sender;
import net.qiyuesuo.sdk.SDKClient;
import net.qiyuesuo.sdk.api.SignService;
import net.qiyuesuo.sdk.bean.company.CompanySignRequest;
import net.qiyuesuo.sdk.bean.company.TenantType;
import net.qiyuesuo.sdk.bean.contract.Action;
import net.qiyuesuo.sdk.bean.contract.ActionType;
import net.qiyuesuo.sdk.bean.contract.Stamper;
import net.qiyuesuo.sdk.bean.contract.StamperType;
import net.qiyuesuo.sdk.bean.sign.SignUrlRequest;
import net.qiyuesuo.sdk.bean.sign.SignatoryRect;
import net.qiyuesuo.sdk.impl.SignServiceImpl;

public class SignContractService {
	private SignService signService;
	
	public SignContractService(SDKClient sdkClient) {
		this.signService = new SignServiceImpl(sdkClient);
	}
	
	/**
	 * 发起方签署公章
	 */
	public void senderSign(Sender sender, Long contractId) throws Exception {
		CompanySignRequest request = new CompanySignRequest();
		request.setContractId(contractId);
		request.setTenantName(sender.getTenantName());
		signService.signByCompany(request);
	}
	
	/**
	 * 获取公司接收方签署页面的链接
	 */
	public String companySignUrl(CompanyReceiver receiver, Long contractId) throws Exception {
		SignUrlRequest request = new SignUrlRequest();
		request.setContractId(contractId);
		request.setTenantType(TenantType.COMPANY);
		request.setTenantName(receiver.getTenantName());
		request.setContact(receiver.getReceiverContact());
		return signService.signUrl(request);
	}

	/**
	 * 获取个人接收方签署页面的链接
	 */
	public String personalSignUrl(PersonalReceiver receiver, Long contractId) throws Exception {
		SignUrlRequest request = new SignUrlRequest();
		request.setContractId(contractId);
		request.setTenantType(TenantType.PERSONAL);
		request.setTenantName(receiver.getTenantName());
		request.setContact(receiver.getReceiverContact());
		return signService.signUrl(request);
	}

	/**
	 * 发起方签署公章：使用参数中的签署位置
	 */
	public void senderSignWithLocation(Sender sender, Long contractId, Long documentId) throws Exception {
		CompanySignRequest request = new CompanySignRequest();
		request.setContractId(contractId);
		request.setTenantName(sender.getTenantName());
		// 指定签署位置
		Stamper stamper = new Stamper();
		stamper.setType(StamperType.SEAL_CORPORATE); // 签署类型：SEAL_CORPORATE（公章）,ACROSS_PAGE（骑缝章）,TIMESTAMP（时间戳）
		stamper.setDocumentId(documentId);
		stamper.setSealId(sender.getSealId()); // 印章
		stamper.setKeyword("甲方签字："); // 签署位置：使用关键字定位
		request.addStamper(stamper);
		signService.signByCompany(request);
	}
	
	/**
	 * 添加公司签署方，指定签署位置，并获取公司接收方签署页面的链接
	 */
	public String addAndGetCompanySignUrl(CompanyReceiver receiver, Long contractId, Long documentId) throws Exception {
		SignUrlRequest request = new SignUrlRequest();
		request.setContractId(contractId);
		request.setTenantType(TenantType.COMPANY);
		request.setTenantName(receiver.getTenantName());
		request.setContact(receiver.getReceiverContact());
		// 指定签署动作，一个签署方下可以有多个签署动作
		Action action = new Action();
		action.setType(ActionType.CORPORATE); // 签署动作类型：CORPORATE（企业签章），LP（法定代表人签字），OPERATOR（经办人签字）
		action.setSerialNo(1); // 签署顺序，从1开始
		request.addAction(action);
		// 指定签署位置
		SignatoryRect rect = new SignatoryRect();
		rect.setDocumentId(documentId);
		rect.setKeyword("乙方签字：");
		action.addLocation(rect);
		return signService.signUrl(request);
	}

	/**
	 * 添加个人签署方，指定签署位置，并获取个人接收方签署页面的链接
	 */
	public String addAndGetPersonalSignUrl(PersonalReceiver receiver, Long contractId, Long documentId) throws Exception {
		SignUrlRequest request = new SignUrlRequest();
		request.setContractId(contractId);
		request.setTenantType(TenantType.PERSONAL);
		request.setTenantName(receiver.getTenantName());
		request.setContact(receiver.getReceiverContact());
		// 指定签署动作，一个签署方下可以有多个签署动作
		Action action = new Action();
		action.setType(ActionType.PERSONAL); // 签署动作类型：CORPORATE（企业签章），LP（法定代表人签字），OPERATOR（经办人签字）
		action.setSerialNo(1); // 签署顺序，从1开始
		request.addAction(action);
		// 指定签署位置
		SignatoryRect rect = new SignatoryRect();
		rect.setDocumentId(documentId);
		rect.setKeyword("乙方签字：");
		action.addLocation(rect);
		return signService.signUrl(request);
	}
	
}

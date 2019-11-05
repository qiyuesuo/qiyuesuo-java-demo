package net.qiyuesuo.sample.contract.param;

/**
 * 个人接收方信息
 */
public class PersonalReceiver {
	private String tenantName; // 接收人姓名
	private String receiverContact; // 接收人联系方式
	public PersonalReceiver(String tenantName, String receiverContact) {
		this.tenantName = tenantName;
		this.receiverContact = receiverContact;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getReceiverContact() {
		return receiverContact;
	}
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}

}

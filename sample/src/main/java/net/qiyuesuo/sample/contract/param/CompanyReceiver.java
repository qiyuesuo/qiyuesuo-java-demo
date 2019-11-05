package net.qiyuesuo.sample.contract.param;

/**
 * 公司接收方信息
 */
public class CompanyReceiver {
	private String tenantName; // 接收方名称
	private String receiverContact; // 经办人联系方式
	private String receiverName; // 经办人姓名
	public CompanyReceiver(String tenantName, String receiverContact, String receiverName) {
		this.tenantName = tenantName;
		this.receiverContact = receiverContact;
		this.receiverName = receiverName;
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
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

}

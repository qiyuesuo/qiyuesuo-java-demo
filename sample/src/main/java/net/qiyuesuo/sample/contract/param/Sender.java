package net.qiyuesuo.sample.contract.param;

/**
 * 发起方信息
 */
public class Sender {
	private Long categoryId; // 业务分类ID
	private Long sealId; // 印章ID
	private String tenantName; // 发起方公司名称（平台名称/内部企业名称）
	private String creatorContact; // 合同发起人联系方式
	private String creatorName; // 合同发起人姓名
	public Sender(Long categoryId, Long sealId, String tenantName, String creatorContact, String creatorName) {
		this.categoryId = categoryId;
		this.sealId = sealId;
		this.tenantName = tenantName;
		this.creatorContact = creatorContact;
		this.creatorName = creatorName;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getSealId() {
		return sealId;
	}
	public void setSealId(Long sealId) {
		this.sealId = sealId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getCreatorContact() {
		return creatorContact;
	}
	public void setCreatorContact(String creatorContact) {
		this.creatorContact = creatorContact;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}

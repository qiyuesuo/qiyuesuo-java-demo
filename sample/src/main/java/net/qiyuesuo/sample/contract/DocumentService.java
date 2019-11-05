package net.qiyuesuo.sample.contract;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import net.qiyuesuo.sdk.SDKClient;
import net.qiyuesuo.sdk.api.ContractService;
import net.qiyuesuo.sdk.bean.document.CreateDocumentRequest;
import net.qiyuesuo.sdk.bean.document.CreateDocumentResult;
import net.qiyuesuo.sdk.common.http.StreamFile;
import net.qiyuesuo.sdk.impl.ContractServiceImpl;

public class DocumentService {
	private ContractService contractService;
	
	public DocumentService(SDKClient sdkClient) {
		this.contractService = new ContractServiceImpl(sdkClient);
	}
	
	/**
	 * 使用文件生成合同文档
	 */
	public Long createByFile() throws Exception {
		FileInputStream input = new FileInputStream("file/contract.pdf");
		CreateDocumentRequest request = new CreateDocumentRequest();
		request.setFile(new StreamFile(input));
		request.setTitle("测试合同文档");
		request.setFileType("pdf");
		CreateDocumentResult result = contractService.createByFile(request);
		return result.getDocumentId();
	}
	
	/**
	 * 使用模板创建合同文档
	 */
	public Long createByTemplate(Long templateId, Map<String, String> params) throws Exception {
		return contractService.createDocument(templateId, params, "参数模板文档", null);
	}
	
	/**
	 * 下载合同文档
	 */
	public void download(Long documentId, String fileNale) throws Exception {
		FileOutputStream fos = new FileOutputStream("file/" + fileNale);
		contractService.downloadDoc(documentId, fos);
	}

}

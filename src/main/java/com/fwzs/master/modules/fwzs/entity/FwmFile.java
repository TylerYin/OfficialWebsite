/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 防伪码文件列表Entity
 * @author ly
 * @version 2017-09-30
 */
public class FwmFile extends DataEntity<FwmFile> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// file_name
	private String fileUrl;		// file_url
	private String fileSize;		// file_size
	private BsProduct bsProduct;		// 产品id
	private String codeNumber;		// 码数量
	
	public FwmFile() {
		super();
	}

	public FwmFile(String id){
		super(id);
	}

	@Length(min=0, max=50, message="file_name长度必须介于 0 和 50 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=150, message="file_url长度必须介于 0 和 150 之间")
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	@Length(min=0, max=20, message="file_size长度必须介于 0 和 20 之间")
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	
	
	public BsProduct getBsProduct() {
		return bsProduct;
	}

	public void setBsProduct(BsProduct bsProduct) {
		this.bsProduct = bsProduct;
	}

	@Length(min=0, max=11, message="码数量长度必须介于 0 和 11 之间")
	public String getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}
	
}
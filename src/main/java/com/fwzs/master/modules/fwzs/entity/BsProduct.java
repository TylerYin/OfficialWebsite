/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 防伪查询记录表Entity
 * @author ly
 * @version 2017-09-30
 */
public class BsProduct extends DataEntity<BsProduct> {
	
	private static final long serialVersionUID = 1L;
	private String prodNo;		    // 产品编号
	private String regType;		    // 登记类别代码（1登记类别代码为PD 2登记类别代码为WP 3临时登记）
	private String regCode;		    // 登记证号
	private String pesticideName;   // 农药名
	private String prodName;		// 产品名称
	private String regCrop;		    // 证件所属公司
	private Office company;	        // 公司
	private FwmSpec prodSpec;		// 产品规格
	private String packRate;		// 包装比例
	private String scType;		    // 生产类型
	private String prodUnit;		// 单位
	private String prodManager;		// 产品经理
	private String isuser;		    // 是否启用
	private String imgUrl;		    // 图片路径
	private String remark;		    // 备注
	private String prodType;		// 产品分类
	private String prodContent;		// 产品内容
	private Date indate;		    // 有效期
	private String checkPlanUrl;    // 检验报告单
	private String checkPerson;     // 检验人
	private String batchNo;		    // 批号
	private String planNumber;		// 计划生产数量
	private String realNumber;		// 实际生产数量
	private String mappingId;		// 生产计划和产品关系表Id

    private List<ProductSpec> productSpecs = new ArrayList<>();

    private String boxCode;                                   //箱码
    private String boxCodeType;                               //箱码类型
    private Warehouse warehouse;                              //仓库
    private String outBound2ProductMappingId;		          //出库计划和产品关联关系表Id
    private String inBound2ProductMappingId;		          //入库计划和产品关联关系表Id
    private String status;		                              //发货状态

    private String fwmFileId;
    private String fwmFileName;

    private String prodParameter;                             //产品参数
    private String prodFeature;                               //产品特点
    private String prodConsideration;                         //产品注意事项

	public FwmSpec getProdSpec() {
		return prodSpec;
	}

	public void setProdSpec(FwmSpec prodSpec) {
		this.prodSpec = prodSpec;
	}

	public BsProduct() {
		super();
	}

	public BsProduct(String id){
		super(id);
	}

	@Length(min=0, max=20, message="产品编号长度必须介于 0 和 20 之间")
	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	
	@Length(min=0, max=1, message="登记类别代码（1登记类别代码为PD 2登记类别代码为WP 3临时登记）长度必须介于 0 和 1 之间")
	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}
	
	@Length(min=0, max=6, message="登记证号长度必须介于 0 和 6 之间")
	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
	
	@Length(min=0, max=150, message="农药名长度必须介于 0 和 150 之间")
	public String getPesticideName() {
		return pesticideName;
	}

	public void setPesticideName(String pesticideName) {
		this.pesticideName = pesticideName;
	}
	
	@Length(min=0, max=100, message="产品名称长度必须介于 0 和 100 之间")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	
	@Length(min=0, max=150, message="证件所属公司长度必须介于 0 和 150 之间")
	public String getRegCrop() {
		return regCrop;
	}

	public void setRegCrop(String regCrop) {
		this.regCrop = regCrop;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	@Length(min=0, max=50, message="包装比例长度必须介于 0 和 50 之间")
	public String getPackRate() {
		return packRate;
	}

	public void setPackRate(String packRate) {
		this.packRate = packRate;
	}
	
	@Length(min=0, max=1, message="生产类型长度必须介于 0 和 1 之间")
	public String getScType() {
		return scType;
	}

	public void setScType(String scType) {
		this.scType = scType;
	}
	
	@Length(min=0, max=3, message="单位长度必须介于 0 和 3 之间")
	public String getProdUnit() {
		return prodUnit;
	}

	public void setProdUnit(String prodUnit) {
		this.prodUnit = prodUnit;
	}
	
	@Length(min=0, max=100, message="产品经理长度必须介于 0 和 100 之间")
	public String getProdManager() {
		return prodManager;
	}

	public void setProdManager(String prodManager) {
		this.prodManager = prodManager;
	}
	
	@Length(min=0, max=1, message="是否启用长度必须介于 0 和 1 之间")
	public String getIsuser() {
		return isuser;
	}

	public void setIsuser(String isuser) {
		this.isuser = isuser;
	}
	
	@Length(min=0, max=200, message="图片路径长度必须介于 0 和 200 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=64, message="产品分类长度必须介于 0 和 64 之间")
	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	public String getProdContent() {
		return prodContent;
	}

	public void setProdContent(String prodContent) {
		this.prodContent = prodContent;
	}

	@Length(min=0, max=50, message="批号长度必须介于 0 和 50 之间")
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Length(min=0, max=11, message="计划生产数量长度必须介于 0 和 11 之间")
	public String getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public String getCheckPlanUrl() {
		return checkPlanUrl;
	}

	public void setCheckPlanUrl(String checkPlanUrl) {
		this.checkPlanUrl = checkPlanUrl;
	}

	public String getRealNumber() {
		return realNumber;
	}

	public void setRealNumber(String realNumber) {
		this.realNumber = realNumber;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getBoxCodeType() {
        return boxCodeType;
    }

    public void setBoxCodeType(String boxCodeType) {
        this.boxCodeType = boxCodeType;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getOutBound2ProductMappingId() {
        return outBound2ProductMappingId;
    }

    public void setOutBound2ProductMappingId(String outBound2ProductMappingId) {
        this.outBound2ProductMappingId = outBound2ProductMappingId;
    }

    public String getInBound2ProductMappingId() {
        return inBound2ProductMappingId;
    }

    public void setInBound2ProductMappingId(String inBound2ProductMappingId) {
        this.inBound2ProductMappingId = inBound2ProductMappingId;
    }

    public String getFwmFileId() {
        return fwmFileId;
    }

    public void setFwmFileId(String fwmFileId) {
        this.fwmFileId = fwmFileId;
    }

    public String getFwmFileName() {
        return fwmFileName;
    }

    public void setFwmFileName(String fwmFileName) {
        this.fwmFileName = fwmFileName;
    }

    public List<ProductSpec> getProductSpecs() {
        return productSpecs;
    }

    public void setProductSpecs(List<ProductSpec> productSpecs) {
        this.productSpecs = productSpecs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProdParameter() {
        return prodParameter;
    }

    public void setProdParameter(String prodParameter) {
        this.prodParameter = prodParameter;
    }

    public String getProdFeature() {
        return prodFeature;
    }

    public void setProdFeature(String prodFeature) {
        this.prodFeature = prodFeature;
    }

    public String getProdConsideration() {
        return prodConsideration;
    }

    public void setProdConsideration(String prodConsideration) {
        this.prodConsideration = prodConsideration;
    }
}
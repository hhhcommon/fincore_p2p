package com.zillionfortune.boss.web.controller.template.vo;

public class TemplateVo {
	/** 模板类型 */
	private Integer templateType;
	/** 模板编号 */
	private Integer templateNum;
	
	private Long templateId;
	
	/** 模板内容 */
	private String templateContent;
	
	
	private String templateName;


	public Integer getTemplateNum() {
		return templateNum;
	}

	public void setTemplateNum(Integer templateNum) {
		this.templateNum = templateNum;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}

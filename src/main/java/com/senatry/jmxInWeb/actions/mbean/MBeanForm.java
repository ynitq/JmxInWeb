package com.senatry.jmxInWeb.actions.mbean;

import com.senatry.jmxInWeb.exception.MissingParamException;
import com.senatry.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * MBean操作的form
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class MBeanForm {
	private String objectName;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void verifyOBjectName() throws MissingParamException {
		if (StringUtils.isBlank(this.objectName)) {
			MissingParamException ex = new MissingParamException();
			ex.addMissingParam("objectName", "missing.objectName.desc");
			throw ex;
		}

	}
}
